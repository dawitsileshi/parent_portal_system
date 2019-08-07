let mongoose = require("mongoose");
let lessonPlanSchema = new mongoose.Schema({
    teacherId: mongoose.Schema.Types.ObjectId,
    title: String,
    numberOfPages: Number,
    objective: String,
    date: {
    	type: Date,
    	default: Date.now()
    }
});

let lessonPlanModel = new mongoose.model("lessonPlan", lessonPlanSchema);

module.exports = lessonPlanModel;