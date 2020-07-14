// Initialize Firebase
var config = {
    apiKey: "AIzaSyD19ndH02Obg8kNftMiRk7EL5euhwiDq3Q",
    authDomain: "smarthome-ad51f.firebaseapp.com",
    databaseURL: "https://smarthome-ad51f.firebaseio.com",
    projectId: "smarthome-ad51f",
    storageBucket: "smarthome-ad51f.appspot.com",
    messagingSenderId: "21004874174"
};
firebase.initializeApp(config);

//--------------------------------------------------------------------------------------------------------------------------------------


(function($) {
    "use strict";

    /*==================================================================
    [ Focus Contact2 ]*/
    $('.input100').each(function() {
        $(this).on('blur', function() {
            if ($(this).val().trim() != "") {
                $(this).addClass('has-val');
            } else {
                $(this).removeClass('has-val');
            }
        })
    })
})(jQuery);



firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.
        var user = firebase.auth().currentUser;

        if (user != null) {
            var email_id = user.email;
            window.location.replace("Home.html");
        }
    } else {

    }
});

function login() {
    document.getElementById('login_loading_bar').style.display = 'block';
    var userEmail = document.getElementById("inputEmail").value;
    var userPass = document.getElementById("inputPassword").value;

    firebase.auth().signInWithEmailAndPassword(userEmail, userPass).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;

        //window.alert("Error : " + errorMessage);
        document.getElementById("error_login").innerHTML = errorMessage;
        document.getElementById('login_loading_bar').style.display = 'none';
        // ...
    });

}



function err_clr() {
    document.getElementById("error_login").innerHTML = "";
}