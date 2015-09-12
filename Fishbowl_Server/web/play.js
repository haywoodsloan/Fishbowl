var timerInv = null;
var touchStartLocation;
var audio = new Audio('bell.mp3');
var doneUpdating = true;
var updateCount = 0;
var lastUpdateTime;
var doneLastPhrase = true;
var doneNextPhrase = true;

tempXmlHttp = new XMLHttpRequest();
tempXmlHttp.open("GET", "GetEntryServlet", false);
tempXmlHttp.send();

tempResults = tempXmlHttp.responseText.split("\n");

updatePage(tempResults[0], tempResults[1], tempResults[2]);
timeRemain = tempResults[3];
document.getElementById("phrase").innerHTML = "Team " + tempResults[2] + " is up. Press resume to start.";

setTimerText(timeRemain);

if (tempResults[4].indexOf("false") !== -1) {
    advancePhase();
} else {
    document.getElementById("resumeBtn").onclick = function () {
        advancePhase();
        audio.load();
    };
}

delete tempXmlHttp;
delete tempResults;

function timerUpdate() {

    if (doneUpdating) {

        if (updateCount === 0) {
            doneUpdating = false;

            xmlHttp = new XMLHttpRequest();
            xmlHttp.open("GET", "GetEntryServlet", false);
            xmlHttp.send();

            results = xmlHttp.responseText.split("\n");
            updatePage(results[0], results[1], results[2]);
            timeRemain = results[3];

            if (results[4].indexOf("true") !== -1) {
                clearInterval(timerInv);
                timerInv = null;

                audio.play();

                document.getElementById("phrase").style.color = "red";
                document.getElementById("phrase").innerHTML = "Time is up. Press resume to continue.";

                document.getElementById("resumeBtn").style.visibility = "visible";

                setClick(3);
                document.getElementById("resumeBtn").onclick = advancePhase;
            }

            lastUpdateTime = new Date().getTime();
            setTimerText(timeRemain);

        } else {
            setTimerText(timeRemain - new Date().getTime() + lastUpdateTime);
        }

        updateCount = (updateCount + 1) % 5;
        doneUpdating = true;
    }

}

function setTimerText(tempTime) {

    if (tempTime <= 0) {
        tempTime = 60000;
    }

    minutes = Math.floor(tempTime / 60000).toString();
    seconds = Math.floor(tempTime % 60000 / 1000).toString();
    miliseconds = Math.floor(tempTime % 60000 % 1000 * 60 / 1000).toString();

    if (seconds.length < 2) {
        seconds = "0" + seconds;
    }

    if (miliseconds.length < 2) {
        miliseconds = "0" + miliseconds;
    }

    if (minutes < 1) {
        document.getElementById("timer").innerHTML = seconds + ":" + miliseconds;
    } else {
        document.getElementById("timer").innerHTML = minutes + ":" + seconds + ":" + miliseconds;
    }
}

function nextPhrase(increasePoint) {

    if (doneNextPhrase) {

        doneNextPhrase = false;

        xmlHttp = new XMLHttpRequest();

        xmlHttp.open("POST", "GetEntryServlet", false);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("increasePoints=" + increasePoint);

        results = xmlHttp.responseText.split("\n");

        if (results[0].indexOf("&&!!**$$") !== -1) {
            clearInterval(timerInv);
            timerInv = null;

            document.getElementById("phrase").style.color = "red";
            document.getElementById("phrase").innerHTML = "You have reached the end of the list. Press resume to move to the next phase.";

            document.getElementById("resumeBtn").style.visibility = "visible";
            setClick(2);
            document.getElementById("resumeBtn").onclick = advancePhase;

        } else {
            document.getElementById("phrase").innerHTML = results[0];
        }

        updatePage(results[1], results[2], results[3]);
        doneNextPhrase = true;
    }
}

function lastPhrase() {

    if (doneLastPhrase) {

        doneLastPhrase = false;

        xmlHttp = new XMLHttpRequest();

        xmlHttp.open("POST", "GetEntryServlet", false);
        xmlHttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlHttp.send("decreasePoints=true");

        results = xmlHttp.responseText.split("\n");

        if (results[4].indexOf("false") !== -1) {

            document.getElementById("phrase").innerHTML = results[0];

            updatePage(results[1], results[2], results[3]);

            if (timerInv === null) {
                timerInv = window.setInterval(timerUpdate, 100);
            }

            window.setTimeout(function () {
                setClick(1);
            }, 25);

            document.getElementById("phrase").style.color = "black";
            document.getElementById("resumeBtn").style.visibility = "hidden";
        }

        doneLastPhrase = true;
    }
}

function advancePhase() {

    window.setTimeout(function () {
        setClick(1);
    }, 25);

    nextPhrase(false);

    timerInv = window.setInterval(timerUpdate, 100);

    document.getElementById("phrase").style.color = "black";
    document.getElementById("resumeBtn").style.visibility = "hidden";
}

function updatePage(t1Points, t2Points, activeTeam) {
    document.getElementById("t1PointsLabel").innerHTML = t1Points + " points";
    document.getElementById("t2PointsLabel").innerHTML = t2Points + " points";

    if (parseInt(activeTeam) === 1) {
        document.getElementById("t1NameLabel").style.color = "black";
        document.getElementById("t1PointsLabel").style.color = "black";

        document.getElementById("t2NameLabel").style.color = "grey";
        document.getElementById("t2PointsLabel").style.color = "grey";
    } else {
        document.getElementById("t1NameLabel").style.color = "grey";
        document.getElementById("t1PointsLabel").style.color = "grey";

        document.getElementById("t2NameLabel").style.color = "black";
        document.getElementById("t2PointsLabel").style.color = "black";
    }
}

function setClick(option) {
    switch (option) {
        case 1:
            if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {

                window.ontouchstart = function (event) {
                    touchStartLocation = event.touches[0];
                };

                window.ontouchend = function (event) {

                    if (((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) > 0.10
                            && Math.abs((event.changedTouches[0].clientY - touchStartLocation.clientY) / screen.height) < 0.10) {
                        lastPhrase();
                    } else if (Math.abs((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) < 0.03
                            && Math.abs((event.changedTouches[0].clientY - touchStartLocation.clientY) / screen.height) < 0.03) {
                        nextPhrase(true);
                    }
                };
            } else {
                window.onclick = function () {
                    nextPhrase(true);
                };

                window.onkeyup = function (event) {
                    if (event.keyCode === 37) {
                        lastPhrase();
                    }
                };
            }
            break;
        case 2:
            if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {

                window.ontouchstart = function (event) {
                    touchStartLocation = event.touches[0];
                };

                window.ontouchend = function (event) {

                    if (((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) > 0.15
                            && Math.abs((event.changedTouches[0].clientY - touchStartLocation.clientY) / screen.height) < 0.10) {
                        lastPhrase();
                    }
                };
            } else {
                window.onclick = null;

                window.onkeyup = function (event) {
                    if (event.keyCode === 37) {
                        lastPhrase();
                    }
                };
            }
            break;
        case 3:
            if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
                window.ontouchend = null;
                window.ontouchstart = null;
            } else {
                window.onclick = null;
                window.onkeyup = null;
            }
            break;
    }
}
