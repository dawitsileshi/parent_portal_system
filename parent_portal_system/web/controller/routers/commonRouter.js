let commonRouter = require("express").Router();

let teacherModel = require("../../model/teacher");
// let nurseModel = require("../../model/nurse");
let directorModel = require("../../model/director");
let registrarModel = require("../../model/registrar");
let commonModel = require("../../model/schemas/commonSchema");
let methods = require("../../api");

commonRouter.get("/aboutUs", (req, res, next) => {

    res.render("about-us")

});

commonRouter.get("/contactUs", (req, res, next) => {

    res.render("contact-us")

});

commonRouter.get("/home", (req, res, next)=> {

    res.render("index")

});

commonRouter.post("/common", (req, res, next) => {

    let passedData = req.body;

    commonModel(passedData).save((err, savedCommon) => {

        if(err) {
            console.log("The error is", err)
        } else {
            res.render("director/index")
        }

    })

});

commonRouter.post("/login", (req, res, next) => {

    console.log("into login")
    let passedData = req.body;

    let userName = passedData.uname;
    let password = passedData.password;
    let email = passedData.email;

    if(userName.substring(0, 4) === "tea/") {

        teacherModel.loginTeacher(userName, email, password).then((message) => {

            if(typeof message === "string") {
                res.render("login", {message: message})
            } else {
                res.render("teacher/index", {teacher: message});
            }
            // console.log(foundTeacher);
            // res.render("teacher/index", {teacher: foundTeacher})
        }).catch((err) => {

            console.log("unsuccessful");
            // res.render("login", {message: "invalid info"})

        })

    }else if(userName.substring(0, 4) === "dir/") {

        directorModel.loginDirector(userName, email, password).then((message) => {
            console.log("the director");
            if(typeof message === "string") {
                res.render("login", {message: message})
            } else {
                res.render("director/index");
            }
            res.render("director/index");
        }).catch(err => {
            console.log(err)
        })

    }else if(userName.substring(0, 4) === "reg/") {

        console.log("into registrar");
        // if(userName.length === 0 || email.length === 0 || password === 0) {
        //     res.render("login", {message: "Please "})
        // }
        registrarModel.loginRegistrar(userName, email, password).then((message) => {

            if(typeof message === "string") {
                res.render("login", {message: message})
            } else {
                console.log("The nessage", message);
                res.render("registrar/index", {message: message});
            }

        }).catch(err => {

        })
    }
    // console.log("login");

});

commonRouter.post("/director", (req, res, next) => {


});

commonRouter.get("/updateRegistrar/:email", (req, res, next) => {

    let email = req.params.email;

    console.log("The id", email);

    registrarModel.findByEmail(email).then(foundRegistrar => {

        res.render("registrar/updateAccount", {registrar: foundRegistrar, message: ""});

    }).catch(err => {

        console.log(err)

    })
    // commonModel.findOne({email: email}, (err, foundRegistrar) => {
    //
    //     if(err) {
    //         console.log("The error", err)
    //     } else {
    //
    //         console.log("The found registrar is " + foundRegistrar)
    //         res.render("registrar/updateAccount", {registrar: foundRegistrar});
    //
    //     }

    // })

})

commonRouter.post("/resetPassword", (req, res, next) => {

    let email = req.body.email;
    commonModel.findOne({email: email}, (err, foundUsers) => {

        if(foundUsers === null) {
            teacherModel.teacherByEmail(email).then(foundTeachers => {
                if(foundTeachers === null) {
                    res.render("forgotpassword")
                } else {
                    let password = foundTeachers.password;
                    methods.sendEmail(email, "Here is you password " + password).then(info => {
                        res.render("resetSuccessful")
                    }).catch(err => {
                        res.render("forgotpassword")
                    });
                }
            })
        } else {
            let password = foundUsers.password;
            methods.sendEmail(email, "Here is your password " + password).then(info => {
                res.render("resetSuccessful")
            }).catch(err => {
                res.render("forgotpassword")
            });
        }

    });
    console.log(email)

});

commonRouter.post("/register", (req, res, next) => {

    let passedData = req.body;

    let director = {fname: passedData.fname,
        lname: passedData.lname,
        email: passedData.email,
        gender: passedData.gender,
        uname: passedData.uname,
        password: passedData.password};

    directorModel.registerDirector(director).then(registeredDirector => {

        // if(uname.substring(0, 4) === "reg/") {
        //
        // }
        res.render("login", {message: ""});

    }).catch(err => {

        next(err)
        console.log(err)

    })


})

commonRouter.post("/updateAccount", (req, res, next) => {

    console.log(req.body)
    registrarModel.updateRegistrar(req.body).then(updatedRegistrar => {
        console.log("The updated registrar", updatedRegistrar);
        res.render("registrar/index", {registrar: updatedRegistrar, message: ""})
    }).catch(err => {
        console.log(err)
    })

})

module.exports = commonRouter;