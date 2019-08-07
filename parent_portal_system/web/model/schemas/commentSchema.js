let mongoose = require("mongoose");

let commentSchema = new mongoose.Schema({
    comment: String,
    today: {
        type: Date,
        default: Date.now()
    }

});

let commentModel = new mongoose.model("comment", commentSchema);

module.exports = commentModel;