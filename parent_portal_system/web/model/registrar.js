let commonUserSchema = require("./schemas/commonUserSchema");
// let registrarModel = require("../");
// let studentModel = require("./schemas/studentSchema");
// let student = require("./student");
// let mongoose = require("mongoose");

exports.registerParent = (registrar) => {

    return new Promise((resolve, reject) => {

        commonUserSchema(registrar).save((err, savedRegistrar) => {
            if (err) {
                reject(err)
            } else {
                resolve(savedRegistrar);
            }
        })

    })
};

exports.loginRegistrar = (uname, email, password) => {

    console.log(uname, email + " " + password);
    return new Promise((resolve, reject) => {

        commonUserSchema.findOne({uname: uname, email: email, password: password}, (err, foundRegistrar) => {

            if(err) {
                reject(err)
            } else {
                if(foundRegistrar === null) {
                    resolve("Wrong username or password")
                } else {
                    // console.log(foundTeacher);
                    resolve(foundRegistrar);
                }
            }


        })

    })

}

exports.findByEmail = email => {

    return new Promise((resolve, reject) => {

        commonUserSchema.findOne({email: email}, (err, foundRegistrar) => {

            if(err) {
                reject(err)
            } else {
                resolve(foundRegistrar)
            }

        })


    })

}

exports.updateRegistrar = registrar => {

    return new Promise((resolve, reject) => {

        commonUserSchema.update({email: registrar.email}, registrar, {upsert: true}, (err, updatedAccount) => {

            if(err) {
                reject(err)
            } else {
                commonUserSchema.findOne({email: registrar.email}, (err, foundRegistrar) => {
                    if(err) {
                        console.log(err)
                    } else {
                        resolve(foundRegistrar)
                    }
                })
                // resolve(updatedAccount)
            }

        })

    })

}