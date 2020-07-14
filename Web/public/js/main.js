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

// Get the Sidebar
var mySidebar = document.getElementById("mySidebar");

// Get the DIV with overlay effect
var overlayBg = document.getElementById("myOverlay");

// Toggle between showing and hiding the sidebar, and add overlay effect
function w3_open() {
    if (mySidebar.style.display === 'block') {
        mySidebar.style.display = 'none';
        overlayBg.style.display = "none";
    } else {
        mySidebar.style.display = 'block';
        overlayBg.style.display = "block";
    }
}

// Close the sidebar with the close button
function w3_close() {
    mySidebar.style.display = "none";
    overlayBg.style.display = "none";
}

firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.
        var user = firebase.auth().currentUser;

        if (user != null) {

            var email_id = user.email;
            var name = email_id.split("@")[0];
            document.getElementById("user_email").innerHTML = name;
        }
    } else {
        window.location.replace("login.html");
    }
});

function logout() {
    var r = confirm("Are you sre you want to Sign out !");
    if (r == true) {
        firebase.auth().signOut();
    } else {

    }

}
//--------------------------------------------------------------------------------------------------------------------------------------

var dotv_class = document.getElementsByClassName("dotv");
var dscv_class = document.getElementsByClassName("dscv");

//--------------------------------------------------------------------------------------------------------------------------------------

//get WATER_system_last_online_time
const Device_online_time = firebase.database().ref().child('Device_online_time');
Device_online_time.on('value', snap => {
    var Device_online_time = (snap.val());
    document.getElementById("td_lot").innerHTML = Device_online_time;
    dotv_class[0].innerHTML = Device_online_time;
    dotv_class[0].style.backgroundColor = "orange";
});

function change_color() {
    dotv_class[0].style.backgroundColor = "transparent";
}

setInterval(change_color, 500);

function device_online_ofline_check() {

    var lst_time = dotv_class[0].innerHTML;
    var lst_time_hours = parseInt(lst_time.split(":")[0]);
    var lst_time_minutes = parseInt(lst_time.split(":")[1]);
    var lst_time_sec = parseInt(lst_time.split(":")[2]);

    var liveDate = new Date();
    var livehours = liveDate.getHours();
    var liveminutes = liveDate.getMinutes();
    var livesec = liveDate.getSeconds();



    if (livehours == lst_time_hours) {

        var difMinutes = liveminutes - lst_time_minutes;

        if (difMinutes == 0) {
            var difsec = livesec - lst_time_sec;

            if (difsec >= 12) {
                var i;
                for (i = 0; i < dscv_class.length; i++) {
                    dscv_class[i].innerHTML = "Offline";
                    dscv_class[i].style.backgroundColor = "Tomato";
                }
            } else {
                var i;
                for (i = 0; i < dscv_class.length; i++) {
                    dscv_class[i].innerHTML = "Online";
                    dscv_class[i].style.backgroundColor = "MediumSeaGreen";
                }
            }

        } else if (difMinutes == 1) {
            var difsec = (60 + livesec) - lst_time_sec;

            if (difsec >= 12) {
                var i;
                for (i = 0; i < dscv_class.length; i++) {
                    dscv_class[i].innerHTML = "Offline";
                    dscv_class[i].style.backgroundColor = "Tomato";
                }
            } else {
                var i;
                for (i = 0; i < dscv_class.length; i++) {
                    dscv_class[i].innerHTML = "Online";
                    dscv_class[i].style.backgroundColor = "MediumSeaGreen";
                }
            }
        } else {
            var i;
            for (i = 0; i < dscv_class.length; i++) {
                dscv_class[i].innerHTML = "Offline";
                dscv_class[i].style.backgroundColor = "Tomato";
            }
        }

    } else {
        var i;
        for (i = 0; i < dscv_class.length; i++) {
            dscv_class[i].innerHTML = "Offline";
            dscv_class[i].style.backgroundColor = "Tomato";
        }
    }

}

setInterval(device_online_ofline_check, 1000);