let studentRouter = require("express").Router();

let studentModel = require("../../model/student");
let scheduleModel = require("../../model/schedule");

// for letting the director open the page that helps him search for students
studentRouter.get("/director/manageStudents", (req, res, next) => {

    res.render("director/manageStudents");

});

// for helping the registrar open the student registration page
studentRouter.get("/addStudents", (req, res, next) => {

    let student = {fname: "",
                    lname: "",
        grade: "",
        age: "",
        gender: "",
        section: "",
        inSchool: "",
        familyContact:[{email: "",
                        tel: ""}]};

        res.render("registrar/studentRegister", {student: student})

});

// lists all the students saved in the db
studentRouter.get("/students", (req, res, next) => {

    studentModel.listAllStudents().then(foundStudents => {

        res.json(foundStudents)

    }).catch(err => {

        console.log(err);
        next(err);

    })

});

// lists a student with the specified id we received via the param in the url
studentRouter.get("/student/:id", (req, res, next) => {

    let id = req.params.id;
    console.log("arrived");

    studentModel.listSpecificStudent(id).then(foundStudent => {

        console.log(foundStudent);
        res.json(foundStudent)

    }).catch(err => {

        console.log(err);
        next(err)

    })

});

// for listing the attendance of the student. First it finds the student with an id received from the url
// as a param, then it will take the section and grade of the retrieved student and use those info to
// retrieve attendance info from the attendance collectoin
studentRouter.get("/student/attendance/:id", (req, res, next) => {

    let id = req.params.id;

    studentModel.getStudentAttendance(id).then(foundAttendance => {
        let dataToBeSent = [];
        console.log("The found attendance", foundAttendance)
        for (let i = 0; i < foundAttendance.length; i++) {
            let dailyAttendance = foundAttendance[i].dailyAttendance;
            let students = dailyAttendance.students;
            console.log("The students", students)
            for (let j = 0; j < students.length; j++) {
                if(String(id) === String(students[j].studentId)) {
                    dataToBeSent.push({date: foundAttendance[i].dailyAttendance.date,
                                    courseName: foundAttendance[i].dailyAttendance.courseName,
                                    period: foundAttendance[i].dailyAttendance.period,
                                    value: students[j].value,
                                    excused: students[j].excused})
                }
            }
        }
        console.log("Tge data to be sent", dataToBeSent);
       res.json(dataToBeSent)
    }).catch(err => {

    })

});

// lists student based on the email their parents added during registration
studentRouter.get("/student/email/:email", (req, res, next) => {

    let email = req.params.email;
    console.log("arrived");

    studentModel.listByFamContact(email).then(foundStudent => {

        // console.log(foundStudent);
        res.json(foundStudent)

    }).catch(err => {

        console.log(err);
        next(err)

    })

});

// lists students based on their section
studentRouter.get("/students/section/:section", (req, res, next) => {

    let section = req.params.section;

    studentModel.listBySection(section).then(foundStudents => {

        res.json(foundStudents)

    }).catch(err => {

        console.log(err);
        next(err)

    })

});

// lists students based on their grade
studentRouter.get("/students/grade/:grade", (req, res, next) => {

    let grade = req.params.grade;
    studentModel.listBygrade(grade).then(foundStudents => {

        res.json(foundStudents)

    }).catch(err => {

        console.log(err);
        next(err)

    });

    // let students = req.students;
    //
    // res.json(students);

});

// retrieves schedule id with the student id we received via the url as a param
studentRouter.get("/student/schedule/:id", (req, res, next) => {

    let id = req.params.id;

    studentModel.showStudentSchedule(id).then(foundSchedule => {
        console.log(JSON.stringify(foundSchedule));
        res.json(foundSchedule);
    }).catch(err => {
        console.log(err);
        next(err)
    })

});

// retrieves the grade of the student based on the _id received as a param in teh id
studentRouter.get("/student/grade/:id", (req, res, next) => {

    let id = req.params.id;

    studentModel.showGrade(id).then(foundGrade => {
        console.log(foundGrade);
        res.json(foundGrade)
    }).catch(err => {
        console.log(err);
        next(err)
    })
    // studentModel.showStudentGrade(id).then(foundSchedule => {
    //     res.json(foundSchedule);
    // }).catch(err => {
    //     console.log(err);
    //     next(err)
    // })

});

// lists student by searching it with an _id property
studentRouter.get("/singleStudent/:id", (req, res, next) => {

    let id = req.params.id;

    studentModel.listById(id).then(foundStudent => {

        // console.log(foundStudent);
        res.render("registrar/studentRegister", {student: foundStudent});

    }).catch(err => {

        console.log(err);
        next(err)

    })
});

studentRouter.get("/familyContact", (req, res, next) => {



})

// lists students by searching it with the idNumber property
studentRouter.post("/student/id", (req, res, next) => {

  let passedData = req.body;
  console.log(passedData);

    studentModel.listByIdNumber(passedData.id).then(foundStudent => {

        res.json(foundStudent)

    }).catch(err => {

        console.log(err);
        next(err)

    })

});

// updates the student info
studentRouter.post("/existingStudent", (req, res, next) => {

    let passedData = req.body;

    // res.json(passedData);
    studentModel.updateStudentInfo(passedData, passedData.idNumber).then(updatedStudent => {

        res.json(updatedStudent)

    }).catch(err => {

        console.log(err);
        next(err)
    })

});

// used when the teacher searches for students to fill attendance. First it retrieves schedules using the
// section, grade and day attribute. Then it will check if the found teacher is found in the schedule and also will match
// if the period inputted by the teacher is the same period the teacher is assigned with in the schedule and will provide the students
studentRouter.post("/students/secgradeday", (req, res, next) => {

    let passedData = req.body;

    let grade = passedData.grade;
    let section = passedData.section;
    let day = passedData.day;
    let teacherId = passedData.teacherId;
    let period = passedData.period;
    // let semester = passedDa

    console.log("The passed data", passedData)

    // let periods = [1, 2, 3];

    let teacherFound = false;
    scheduleModel.findByGradeSectionDay(grade, section, day).then(foundSchedule => {

        if(foundSchedule !== null) {

            let program = foundSchedule.program;
            for (let i = 0; i < program.length; i++) {
                let singleProgram = program[i];
                    // console.log("The period is", period)
                console.log(String(singleProgram.teacherId) === String(teacherId), typeof singleProgram.period + " " + typeof Number(period));
                if(String(singleProgram.teacherId) === String(teacherId) && singleProgram.period === Number(period)) {
                    teacherFound = true;
                }
            }

            if(teacherFound) {
                studentModel.listBySectiongrade(grade, section).then(foundStudents => {

                    if(foundStudents.length === 0) {

                        // res.json("Sorry, there are no students available")
                        res.json(1)

                    } else {


                        // console.log(foundStudents);
                        res.json(foundStudents)
                        // res.render("teacher/attendance", {students: foundStudents, periods: periods})
                    }
                }).catch(err => {

                    next(err);
                    console.log(err)

                });
            } else {
                // res.json("Sorry, this is not the right schedule")
                res.json(2)
            }

        } else {
            res.json(3)
            // res.json("Sorry, there is no such schedule in the data store")
        }

    }).catch(err => {
        next(err)
        console.log(err)
    })

    // console.log(req.body)

});

// this route is used to retrieve students based on their section and grade. Used when the director searches for student,
// and for the grade and attendance part
studentRouter.post("/students/secGrade", (req, res, next) => {

    let passedData = req.body;

    let grade = passedData.grade;
    let section = passedData.section;
    console.log("The passed data", passedData)

    studentModel.listBySectiongrade(grade, section).then(foundStudents => {

        res.json(foundStudents)

    }).catch(err => {

        console.log(err)

    })

});

// used to retrieve schedules based on grade and section, but this one is used for the attendance and schedule part
studentRouter.post("/students/secgrade", (req, res, next) => {

    let passedData = req.body;

    let grade = passedData.grade;
    let section = passedData.section;
    let day = passedData.day;
    let teacherId = passedData.teacherId;
    // let semester = passedDa

    console.log("The passed data", passedData)

    // let periods = [1, 2, 3];

    let teacherFound = false;
    scheduleModel.findByGradeSection(grade, section).then(foundSchedule => {

        if(foundSchedule.length !== 0) {

            console.log("The found schedules", foundSchedule)
            for (let i = 0; i < foundSchedule.length; i++) {
                let singleSchedule = foundSchedule[i]

                let program = singleSchedule.program;
                for (let i = 0; i < program.length; i++) {
                    let singleProgram = program[i];
                    if (String(singleProgram.teacherId) === String(teacherId)) {
                        teacherFound = true;
                    }
                }

            }
            if(teacherFound) {
                studentModel.listBySectiongrade(grade, section).then(foundStudents => {

                    if(foundStudents.length === 0) {

                        // res.json("Sorry, there are no students available")
                        res.json(1)

                    } else {


                        // console.log(foundStudents);
                        res.json(foundStudents)
                        // res.render("teacher/attendance", {students: foundStudents, periods: periods})
                    }
                }).catch(err => {

                    next(err);
                    console.log(err)

                });
            } else {
                // res.json("Sorry, this is not the right schedule")
                res.json(2)
            }

            } else {
            res.json(3)
            // res.json("Sorry, there is no such schedule in the data store")
        }

    }).catch(err => {
        next(err)
        console.log(err)
    })

});

// for registering students
studentRouter.post("/student", (req, res, next) => {

    let passedData = req.body;
    console.log(passedData);
    // res.json(passedData);

    let token = cryptoo.randomBytes(2).toString("hex");
    let idNumber = cryptoo.randomBytes(2).toString("hex");

    let twoLetters = passedData.fname[0] + passedData.fname[1];
    // let imageBuff = new Buffer(passedData.pic.replace(/^data:image\/(png|gif|jpeg);base64,/,''), 'base64');
    // let imageBuff = new Buffer(passedData.pic, 'base64');

    let result = {grade: passedData.grade};

    let student = {
        // id: passedData.id,
        idNumber: twoLetters + "/" + idNumber + "/" + new Date().getFullYear(),
        fname: passedData.fname,
        lname: passedData.lname,
        pic: {
                data: passedData.pic,
            contentType: "image/jpg"
        },
        grade: passedData.grade,
        age: passedData.age,
        gender: passedData.gender,
        section: passedData.section,
        inSchool: true,
        // semester: passedData.semester,
        familyContact: [{
            email: passedData.familyEmail,
            token: token,
            tel: passedData.familyTel.toString().replace(" ", ""),
        }],
        schedule:[],
        result: result

    };

    studentModel.registerStudent(student).then(savedStudent => {

        // res.json(savedStudent)
        //
        res.render("registrar/index", {message: {email: ""}});

    }).catch(err => {
        //
        console.log(err);
        next(err)
    //
    })

});

// for retrieving a student based on the _id
studentRouter.post("/singleStudent", (req, res, next) => {

    let id = req.params.id;

    studentModel.listById(id).then(foundStudent => {

        res.render("registrar/studentRegister", {student: foundStudent});

    }).catch(err => {

        console.log(err);
        next(err)

    })

})

// for updating student info based on his or her _id
studentRouter.put("/student/:id", (req, res, next) => {

    let passedData = req.body;

    let id = req.params.id;

    studentModel.updateStudentInfo(passedData, id).then(updatedStudent => {



    }).catch(err => {



    })

});

// for making the student pass grade(year)
studentRouter.put("/student/promote/:id", (req, res, next) => {

    let id = req.params.id;

    studentModel.promoteStudent(id).then(promotedStudent => {

        res.json(promotedStudent)

    }).catch(err => {



    })

})

// for deleting student based on an _id received as a query on the url
studentRouter.delete("/student", (req, res, next) => {

    let id = req.query.id;

    studentModel.deleteStudent(id).then(deletedStudent => {
        res.json(deletedStudent);
    }).catch(err => {
        console.log(err);
        next(err)
    })

});

// for deleting student based on an _id received as a body on the url
studentRouter.delete("/student", (req, res, next) => {

    let id = req.body.id;
   studentModel.deleteStudent(id).then(deletedStudent => {

       res.json(deletedStudent)

   }).catch(err => {

       console.log(err);
       next(err)

   })

});

// for removing schedule from a student
studentRouter.delete("/student/schedule", (req, res, next) => {

    let dayNumber = req.query.dayNumber;
    let section = req.query.section;
    let semester = req.query.semester;
    let grade = req.query.grade;

    console.log(req.query);

    studentModel.removeSchedule(grade, section, Number(semester), Number(dayNumber)).then(foundStudent => {
        res.json(foundStudent)
    }).catch(err => {
        console.log(err);
        next(err)
    })

});

module.exports = studentRouter;