
let todayId = document.getElementById("todayId");

let today = new Date();
let tmp = new Date(Date.now());

let submitButton = document.getElementById("submitBehaviorButtonId");
console.log(submitButton)
// tmp now like: "2018-08-21T11:54:50.580Z"

let dateInputFormatted = tmp.toISOString().split('T')[0];

// 0, as split produces: ["2018-08-21", "11:54:50.580Z"]
let year = today.getFullYear();
let month = today.getMonth();
let day = today.getDay();

let studentId = document.getElementById("studentId");
let when = document.getElementById("whenId");
let fault = document.getElementsByName("fault");
// todayId.value = year + "-" + month + "-" + day;
todayId.value = dateInputFormatted;
console.log(dateInputFormatted)

submitButton.onclick = () => {

    console.log("clicked")
    let http = new XMLHttpRequest();
    let url = "http://localhost:3000/api/behaviorReport/";

    http.open("post", url, true);
    http.setRequestHeader("Content-Type", "application/json");
    http.send(JSON.stringify({today: todayId.value,
                        when: when.value,
                        studentId: studentId,
                        fault: fault[0].value}));

}