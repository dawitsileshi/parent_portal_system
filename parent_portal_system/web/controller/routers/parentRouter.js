let parentRouter = require("express").Router();

let parentModel = require("../../model/parent");

let methods = require("../../api");

parentRouter.post("/parent/register", (req, res, next) => {

    let passedData = req.body;

    // let fname = passedData.fname;
    // let lname = passedData.lname;
    // let uname = passedData.uname;
    // let relation = passedData.relation;
    // let tel = parentModel.tel;
    let email = passedData.email;
    let token = passedData.tokens;
    // let address = passedData.address;
    // let password = passedData.password;
    // let token = passedData.token;
    //
    // let parent = { fname: fname,
    //             lname: lname,
    //             uname: uname,
    //             email: email,
    //             tel: tel,
    //             password: password
    // };
    console.log("The passed data", passedData);
   parentModel.registerParent(passedData, email, token).then(savedParent => {
       res.json(savedParent);
       console.log("arrived")
   }).catch(err => {
       console.log(err);
       res.status(404);
       res.json(err);
       next(err)
   })

});

parentRouter.post("/parent/writeComment", (req, res, next) => {

    let passedData = req.body;
    parentModel.writeComment(passedData.comment).then(savedComment => {
        res.json("Successfully commented")
    }).catch(err => {
        console.log(err)
        res.json(err)
    })
    console.log(passedData)

})

parentRouter.post("/parent/login", (req, res, next) => {

    let passedData = req.body;
    let email = passedData.email;
    let password = passedData.password;

    parentModel.loginParent(email, password).then(foundParent => {
        res.json(foundParent)
    }).catch(err => {
        console.log(err);
        next(err)
    })

});

parentRouter.post("/parent/email", (req, res, next) => {

   parentModel.sendEmail().then(info => {

       res.json(info)

   }).catch(err => {

       console.log(err);
       res.json(err);
   })

});

parentRouter.post("/newparent", (req, res, next) => {

    let passedData = req.body;

    let email = passedData.email;
    let tel = passedData.tel;
    let id = passedData.studentIds;

    console.log(id);
    // let familyContact = {email: passedData.email,
    //                     token: passedData.token,
    //                     tel: passedData.tel};

    parentModel.addParent(email, tel, id).then(student => {

        res.json(student)

    }).catch(err => {

        console.log(err);
        next(err)

    })

})

parentRouter.put("/parent", (req, res, next) => {

   let passedData = req.body;

   let parent = {fname: passedData.fname,
                lname: passedData.lname,
                relation: passedData.relation,
                email: passedData.email,
                password: passedData.password}

});

parentRouter.get("/parents", (req, res, next) => {

    parentModel.listAllParents().then(parents => {
        res.json(parents);
    }).catch(err => {
        console.log(err)
        next(err)
    })

})

parentRouter.post("/parent/resetPassword", (req, res, next) => {

    let email = req.body.email;

    parentModel.parentByEmail(email).then(parent => {

        let password = parent.password;

        console.log(password);
        methods.sendEmail(email, "Here is your password " + password).then(info => {
            res.json("Check your email for your password");
        }).catch(err => {
            console.log(err)
        });

    })

})
module.exports = parentRouter;