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


firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
        // User is signed in.
        var user = firebase.auth().currentUser;

        if (user != null) {
            window.location.replace("Home.html");
        }
    } else {
        window.location.replace("login.html");
    }
});