function submitConfirm(){
    
    for (var i = 1; i <= 5; i++){
        if(document.getElementById("entry" + i).value.trim() === ""){
            alert("You have not entered all 5 entries. Please Try again.");
            return false;
        }
    }
    
    return confirm("Are you ready to submit these entries?");
}