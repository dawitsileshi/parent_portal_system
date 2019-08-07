let mongoose = require("mongoose");

let attendanceSchema = new mongoose.Schema({
    year: Number,
    dailyAttendance: {
        grade: Number,
        section: String,
        date: Date,
        courseName: String,
        teacherId: mongoose.Schema.Types.ObjectId,
        period: Number,
        students: [{
            studentId: mongoose.Schema.Types.ObjectId,
            value: String,
            excused: Boolean
        }]
        // attendance:[{
        //     courseName: String,
        //     teacherId: mongoose.Schema.Types.ObjectId,
        //     period: String,
        //     students: [{
        //         studentId: mongoose.Schema.Types.ObjectId,
        //         value: String,
        //         excused: Boolean
        //     }]
        // }],
   },

});

let attendanceModel = new mongoose.model("attendance", attendanceSchema);

module.exports = attendanceModel;