var timerInv = null;
var touchStartLocation;
var audio = new Audio('bell.mp3');
var doneUpdating = true;

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
        audio.volume = 1.0;
    };
}

delete tempXmlHttp;
delete tempResults;

if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {

    window.ontouchstart = function (event) {
        touchStartLocation = event.touches[0];
    };

    window.ontouchend = function (event) {
        if (((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) > 0.15) {
            lastPhrase();
        }
    };

} else {

    window.onkeyup = function (event) {
        if (event.keyCode === 37) {
            lastPhrase();
        }
    };

}

function timerUpdate() {

    if (doneUpdating) {

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
            document.getElementById("phrase").innerHTML = "Time is up. Press resume or reload the page to continue.";

            document.getElementById("resumeBtn").style.visibility = "visible";

            setClick(false);
            document.getElementById("resumeBtn").onclick = advancePhase;
        }

        setTimerText(timeRemain);

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
        setClick(false);
        document.getElementById("resumeBtn").onclick = advancePhase;

    } else {
        document.getElementById("phrase").innerHTML = results[0];
    }

    updatePage(results[1], results[2], results[3]);
}

function lastPhrase() {
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
            setClick(true);
        }, 25);

        document.getElementById("phrase").style.color = "black";
        document.getElementById("resumeBtn").style.visibility = "hidden";
    }
}

function advancePhase() {

    window.setTimeout(function () {
        setClick(true);
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

function setClick(enabled) {
    if (enabled) {

        if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
            window.ontouchend = function (event) {

                if (((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) > 0.15) {
                    lastPhrase();
                } else if (Math.abs((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) < 0.01
                        && Math.abs((event.changedTouches[0].clientY - touchStartLocation.clientY) / screen.height) < 0.01) {
                    nextPhrase(true);
                }

            };

        } else {

            window.onclick = function () {
                nextPhrase(true);
            };
        }

    } else {

        if (/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent)) {
            window.ontouchend = function (event) {

                if (((event.changedTouches[0].clientX - touchStartLocation.clientX) / screen.width) > 0.15) {
                    lastPhrase();
                }

            };

        } else {
            window.onclick = null;
        }
    }
}
