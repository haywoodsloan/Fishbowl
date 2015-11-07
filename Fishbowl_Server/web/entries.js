function submitConfirm() {

    for (var i = 1; i <= 5; i++) {
        if (document.getElementById("entry" + i).value.trim() === "") {
            document.getElementById("noticeLabel").innerHTML = "You have not entered 5 entries. Please try again.";
            document.getElementById("noticeLabel").style.visibility = "visible";

            document.getElementById("cancelButton").style.visiblity = "hidden";
            return false;
        }
    }

    if (document.getElementById("cancelButton").style.visibility === "visible") {
        return true;
    } else {
        document.getElementById("cancelButton").style.visibility = "visible";
        document.getElementById("noticeLabel").style.visibility = "visible";

        document.getElementById("noticeLabel").innerHTML = "Press submit again to confirm submitting these entries.";
        return false;
    }
}

function cancelSubmit() {
    document.getElementById("noticeLabel").style.visibility = "hidden";
    document.getElementById("cancelButton").style.visibility = "hidden";
}