//------------------------------------------------------------------------------------------------------
function critical_level_indicater_div_clr_change() {
    var tank_capacity_val = document.getElementById("td_wl").innerHTML;
    var critical_water_level_val = document.getElementById("td_cwl").innerHTML;

    if (tank_capacity_val <= critical_water_level_val) {
        document.getElementById("critical_level_indicater").style.backgroundColor = "tomato";
    } else {
        document.getElementById("critical_level_indicater").style.backgroundColor = "MediumSeaGreen";
    }
}

function switch_to_overview() {
    document.getElementById("Overview_div").style.display = "block";
    document.getElementById("Water_div").style.display = "none";
    document.getElementById("Pirith_div").style.display = "none";
    document.getElementById("lights_div").style.display = "none";

    document.getElementById("db_overview").classList.add("w3-blue");
    document.getElementById("db_water").classList.remove("w3-blue");
    document.getElementById("db_pirith").classList.remove("w3-blue");
    document.getElementById("db_lights").classList.remove("w3-blue");
}

function switch_to_water() {
    document.getElementById("Overview_div").style.display = "none";
    document.getElementById("Water_div").style.display = "block";
    document.getElementById("Pirith_div").style.display = "none";
    document.getElementById("lights_div").style.display = "none";

    document.getElementById("db_overview").classList.remove("w3-blue");
    document.getElementById("db_water").classList.add("w3-blue");
    document.getElementById("db_pirith").classList.remove("w3-blue");
    document.getElementById("db_lights").classList.remove("w3-blue");
}

function switch_to_pirith() {
    document.getElementById("Overview_div").style.display = "none";
    document.getElementById("Water_div").style.display = "none";
    document.getElementById("Pirith_div").style.display = "block";
    document.getElementById("lights_div").style.display = "none";

    document.getElementById("db_overview").classList.remove("w3-blue");
    document.getElementById("db_water").classList.remove("w3-blue");
    document.getElementById("db_pirith").classList.add("w3-blue");
    document.getElementById("db_lights").classList.remove("w3-blue");
}

function switch_to_lights() {
    document.getElementById("Overview_div").style.display = "none";
    document.getElementById("Water_div").style.display = "none";
    document.getElementById("Pirith_div").style.display = "none";
    document.getElementById("lights_div").style.display = "block";

    document.getElementById("db_overview").classList.remove("w3-blue");
    document.getElementById("db_water").classList.remove("w3-blue");
    document.getElementById("db_pirith").classList.remove("w3-blue");
    document.getElementById("db_lights").classList.add("w3-blue");
}

function switch_to_save() {
    document.getElementById("mwl_input").style.display = "block";
    document.getElementById("cwl_input").style.display = "block";
    document.getElementById("mwl_view").style.display = "none";
    document.getElementById("cwl_view").style.display = "none";
    document.getElementById("data_edit_btn").style.display = "none";
    document.getElementById("data_save_btn").style.display = "block";
}

function switch_to_edit() {
    document.getElementById("mwl_input").style.display = "none";
    document.getElementById("cwl_input").style.display = "none";
    document.getElementById("mwl_view").style.display = "block";
    document.getElementById("cwl_view").style.display = "block";
    document.getElementById("data_edit_btn").style.display = "block";
    document.getElementById("data_save_btn").style.display = "none";
}

function show_hide_settings(sh) {
    if (sh == 1) {
        var pp = document.getElementById("tb_container_div1").style.display;
        if (pp == "none") {
            document.getElementById("tb_container_div1").style.display = "block";
            document.getElementById("settings_icon1").classList.add("fa-chevron-up");
            document.getElementById("settings_icon1").classList.remove("fa-chevron-down");
        } else {
            document.getElementById("tb_container_div1").style.display = "none";
            document.getElementById("settings_icon1").classList.remove("fa-chevron-up");
            document.getElementById("settings_icon1").classList.add("fa-chevron-down");
        }
    } else if (sh == 2) {
        var pp = document.getElementById("tb_container_div2").style.display;
        if (pp == "none") {
            document.getElementById("tb_container_div2").style.display = "block";
            document.getElementById("settings_icon2").classList.add("fa-chevron-up");
            document.getElementById("settings_icon2").classList.remove("fa-chevron-down");
        } else {
            document.getElementById("tb_container_div2").style.display = "none";
            document.getElementById("settings_icon2").classList.remove("fa-chevron-up");
            document.getElementById("settings_icon2").classList.add("fa-chevron-down");
        }
    } else {

    }

}

function find_duration() {
    var dur_mid = document.getElementById("dur_mid");
    var mid_start_val = parseInt(document.getElementById("set_mid_start_time_view").innerHTML);
    var mid_end_val = parseInt(document.getElementById("set_mid_end_time_view").innerHTML);

    if (mid_start_val > mid_end_val) {
        dur_mid.innerHTML = ((mid_end_val + 24) - mid_start_val);
    } else if (mid_start_val < mid_end_val) {
        dur_mid.innerHTML = (mid_end_val - mid_start_val);
    } else {

    }
}
//------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------ firebase

//get tank_capacity
const tank_capacity = firebase.database().ref().child('WATER_watertank_capacity');
tank_capacity.on('value', snap => {
    var tank_capacity = JSON.stringify(snap.val());

    if (tank_capacity <= 100) {
        document.getElementById("td_wl").innerHTML = tank_capacity;
        document.getElementById("precentage").innerHTML = tank_capacity + "%";
        document.getElementById("tank-water-level").style.height = tank_capacity + "%";
        document.getElementById("overview_water_precnt_view").innerHTML = "" + tank_capacity + "%";
        critical_level_indicater_div_clr_change();
    } else {}

});
//get upper_water_level
const upper_water_level = firebase.database().ref().child('WATER_watertank_end_water_level');
upper_water_level.on('value', snap => {
    var upper_water_level = JSON.stringify(snap.val());

    document.getElementById("td_mwl").innerHTML = upper_water_level;
    document.getElementById("mwl_input").value = upper_water_level;
    document.getElementById("mwl_view").innerHTML = upper_water_level + "%";
    document.getElementById("max_level_indicater").style.display = "block";
    document.getElementById("max_level_indicater_div").style.height = upper_water_level + "%";
    document.getElementById("max_level_indicater").innerHTML = upper_water_level + "%";
});

//get critical_water_level
const critical_water_level = firebase.database().ref().child('WATER_watertank_critical_water_level');
critical_water_level.on('value', snap => {
    var critical_water_level = JSON.stringify(snap.val());

    document.getElementById("td_cwl").innerHTML = critical_water_level;
    document.getElementById("critical_level_indicater").style.display = "block";
    document.getElementById("critical_level_indicater_div").style.height = critical_water_level + "%";
    document.getElementById("critical_level_indicater").innerHTML = critical_water_level + "%";
    document.getElementById("cwl_input").value = critical_water_level;
    document.getElementById("cwl_view").innerHTML = critical_water_level + "%";
    critical_level_indicater_div_clr_change();
});

//get water_pump_status
const water_pump_status = firebase.database().ref().child('WATER_waterpump_status');
water_pump_status.on('value', snap => {
    var water_pump_status = JSON.stringify(snap.val());
    document.getElementById("td_wps").innerHTML = water_pump_status;
    if (water_pump_status == 0) {

        document.getElementById("wps_change_btn").innerHTML = "Turn On";
        document.getElementById("wps_change_btn").classList.add("btn-green");
        document.getElementById("wps_change_btn").classList.remove("btn-red");

    } else if (water_pump_status == 1) {

        document.getElementById("wps_change_btn").innerHTML = "Turn Off";
        document.getElementById("wps_change_btn").classList.add("btn-red");
        document.getElementById("wps_change_btn").classList.remove("btn-green");

    } else {}
});

//get WATER_system_status_message
const WATER_system_status_message = firebase.database().ref().child('WATER_system_status_message');
WATER_system_status_message.on('value', snap => {
    var WATER_system_status_message = snap.val();
    document.getElementById("wsm").innerHTML = WATER_system_status_message;
    document.getElementById("overview_water_msg_view").innerHTML = WATER_system_status_message;
});


function change_wps() {
    var wps_val = document.getElementById("td_wps").innerHTML;
    var firebaseRef = firebase.database().ref();
    if (wps_val == 0) {
        firebaseRef.child("WATER_waterpump_status").set(1);
    } else if (wps_val == 1) {
        firebaseRef.child("WATER_waterpump_status").set(0);
    } else {}

}

function save_settings() {
    var input_upper_water_level_str = document.getElementById("mwl_input").value;
    var input_upper_water_level_str = document.getElementById("mwl_input").value;

    var input_upper_water_level = parseInt(document.getElementById("mwl_input").value);
    var input_critical_water_level = parseInt(document.getElementById("cwl_input").value);

    if (input_upper_water_level_str != '' && input_upper_water_level_str != '') {

        if (input_upper_water_level > 100) {
            document.getElementById("error_lbl").innerHTML = "Max water level can not be grater than <b>100%</b>";
            rld_data();
        } else if (input_upper_water_level < 10) {
            document.getElementById("error_lbl").innerHTML = "Max water level can not be less than <b>10%</b>";
            rld_data();
        } else if (input_critical_water_level > 90) {
            document.getElementById("error_lbl").innerHTML = "Critical water level can not be grater than <b>90%</b>";
            rld_data();
        } else if (input_critical_water_level < 10) {
            document.getElementById("error_lbl").innerHTML = "Critical water level can not be less than <b>10%</b>";
            rld_data();
        } else if (input_critical_water_level >= input_upper_water_level_str) {
            document.getElementById("error_lbl").innerHTML = "<b>Max water level</b> can not be less than or equal <b>Critical water level</b>";
            rld_data();
        } else {
            var firebaseRef = firebase.database().ref();
            firebaseRef.child("WATER_watertank_end_water_level").set(input_upper_water_level);
            var firebaseRef = firebase.database().ref();
            firebaseRef.child("WATER_watertank_critical_water_level").set(input_critical_water_level);
            document.getElementById("error_lbl").innerHTML = "<b style='color:LimeGreen;'>Settings have been saved</b>";

        }


    } else {
        rld_data();
        document.getElementById("error_lbl").innerHTML = "Fields cannot be null !!";
    }

}

function rld_data() {
    document.getElementById("cwl_input").value = document.getElementById("td_cwl").innerHTML;
    document.getElementById("mwl_input").value = document.getElementById("td_mwl").innerHTML;
}

function err_clr() {
    document.getElementById("error_lbl").innerHTML = "";
    document.getElementById("P_eve_err_lbl").style.display = "none";
    document.getElementById("P_eve_err_br").style.display = "none";
    document.getElementById("P_mid_err_lbl").style.display = "none";
    document.getElementById("P_mid_err_br").style.display = "none";
}

//get Pirith_system_remote_control
const Pirith_system_remote_control = firebase.database().ref().child('Pirith_system_remote_control');
Pirith_system_remote_control.on('value', snap => {
    var P_s_r_c = snap.val();
    if (P_s_r_c == 0) {
        document.getElementById("overview_pirith_system_status").innerHTML = "Auto";
    } else if (P_s_r_c == 1 || P_s_r_c == 2) {
        document.getElementById("overview_pirith_system_status").innerHTML = "Manual";
    } else {}

});

//get Pirith_system_status
const Pirith_system_status = firebase.database().ref().child('Pirith_system_status');
Pirith_system_status.on('value', snap => {
    var Pirith_system_status_val = JSON.stringify(snap.val());

    document.getElementById("td_pss").innerHTML = Pirith_system_status_val;


    if (Pirith_system_status_val == 0) {
        document.getElementById("eve_p_p_btn").style.display = "block";
        document.getElementById("midn_p_p_btn").style.display = "block";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "none";
        document.getElementById("wait_btn_2").style.display = "none";
        document.getElementById("p_text_div").style.display = "none";
        document.getElementById("overview_pirith_msg_view").innerHTML = "";

    } else if (Pirith_system_status_val == 1) {
        document.getElementById("eve_p_p_btn").style.display = "none";
        document.getElementById("midn_p_p_btn").style.display = "block";
        document.getElementById("eve_p_s_btn").style.display = "block";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "none";
        document.getElementById("wait_btn_2").style.display = "none";
        document.getElementById("p_text_div").style.display = "block";
        document.getElementById("p_text").innerHTML = "Evening pirith chanting has begun ...";
        document.getElementById("overview_pirith_msg_view").innerHTML = "Evening pirith chanting has begun ...";

    } else if (Pirith_system_status_val == 2) {
        document.getElementById("eve_p_p_btn").style.display = "block";
        document.getElementById("midn_p_p_btn").style.display = "none";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "block";
        document.getElementById("wait_btn_1").style.display = "none";
        document.getElementById("wait_btn_2").style.display = "none";
        document.getElementById("p_text_div").style.display = "block";
        document.getElementById("p_text").innerHTML = "Midnight pirith chanting has begun ...";
        document.getElementById("overview_pirith_msg_view").innerHTML = "Midnight pirith chanting has begun ...";
    } else {}
});



function pirith_change(x) {
    var firebaseRef = firebase.database().ref();
    if (x == 10) {
        firebaseRef.child("Pirith_system_remote_control").set(0);
        document.getElementById("eve_p_p_btn").style.display = "none";
        document.getElementById("midn_p_p_btn").style.display = "block";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "block";
        document.getElementById("wait_btn_2").style.display = "none";
    } else if (x == 20) {
        firebaseRef.child("Pirith_system_remote_control").set(0);
        document.getElementById("eve_p_p_btn").style.display = "block";
        document.getElementById("midn_p_p_btn").style.display = "none";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "none";
        document.getElementById("wait_btn_2").style.display = "block";
    } else if (x == 1) {
        firebaseRef.child("Pirith_system_remote_control").set(1);
        document.getElementById("eve_p_p_btn").style.display = "none";
        document.getElementById("midn_p_p_btn").style.display = "none";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "block";
        document.getElementById("wait_btn_2").style.display = "block";
    } else if (x == 2) {
        firebaseRef.child("Pirith_system_remote_control").set(2);
        document.getElementById("eve_p_p_btn").style.display = "none";
        document.getElementById("midn_p_p_btn").style.display = "none";
        document.getElementById("eve_p_s_btn").style.display = "none";
        document.getElementById("midn_p_s_btn").style.display = "none";
        document.getElementById("wait_btn_1").style.display = "block";
        document.getElementById("wait_btn_2").style.display = "block";
    } else {

    }
}


//get Device_restart
const Device_restart = firebase.database().ref().child('Device_restart');
Device_restart.on('value', snap => {
    var Device_restart = JSON.stringify(snap.val());
    if (Device_restart == 0) {
        document.getElementById("reboot_btn").innerHTML = "Reboot";
        document.getElementById("reboot_btn").classList.add("btn-green");
    } else if (Device_restart == 1) {
        document.getElementById("reboot_btn").innerHTML = "Rebooting  <i class='fa fa-refresh fa-spin'></i>";
        document.getElementById("reboot_btn").classList.remove("btn-green");
    } else {}
});


function reboot() {
    var firebaseRef = firebase.database().ref();
    firebaseRef.child("Device_restart").set(1);
}


//get Pirith_eve_str_time
const Pirith_eve_str_time = firebase.database().ref().child('Pirith_eve_str_time');
Pirith_eve_str_time.on('value', snap => {
    var Pirith_eve_str_time = JSON.stringify(snap.val());
    document.getElementById("td_pest").innerHTML = Pirith_eve_str_time;
    document.getElementById("set_eve_start_time_view").innerHTML = Pirith_eve_str_time;
});

//get Pirith_eve_end_time
const Pirith_eve_end_time = firebase.database().ref().child('Pirith_eve_end_time');
Pirith_eve_end_time.on('value', snap => {
    var Pirith_eve_end_time = JSON.stringify(snap.val());
    document.getElementById("td_peet").innerHTML = Pirith_eve_end_time;
    document.getElementById("set_eve_end_time_view").innerHTML = Pirith_eve_end_time;

});

//get Pirith_mid_str_time
const Pirith_mid_str_time = firebase.database().ref().child('Pirith_mid_str_time');
Pirith_mid_str_time.on('value', snap => {
    var Pirith_mid_str_time = JSON.stringify(snap.val());
    document.getElementById("td_pmst").innerHTML = Pirith_mid_str_time;
    set_mid_start_time_view
    document.getElementById("set_mid_start_time_view").innerHTML = Pirith_mid_str_time;
    find_duration();
});

//get Pirith_mid_end_time
const Pirith_mid_end_time = firebase.database().ref().child('Pirith_mid_end_time');
Pirith_mid_end_time.on('value', snap => {
    var Pirith_mid_end_time = JSON.stringify(snap.val());
    document.getElementById("td_pmet").innerHTML = Pirith_mid_end_time;
    document.getElementById("set_mid_end_time_view").innerHTML = Pirith_mid_end_time;
    find_duration();

});

function change_value_eve(xx) {
    var doc_eve_str = document.getElementById("set_eve_start_time_view");
    var doc_eve_end = document.getElementById("set_eve_end_time_view");
    var str_val = parseInt(doc_eve_str.innerHTML);
    var end_val = parseInt(doc_eve_end.innerHTML);

    if (xx == -1) {
        if (str_val == 0) {
            doc_eve_str.innerHTML = 23;
            doc_eve_end.innerHTML = 0;
        } else if (str_val == 22) {
            doc_eve_str.innerHTML = 23;
            doc_eve_end.innerHTML = 0;
        } else if (str_val == 23) {
            doc_eve_str.innerHTML = 22;
            doc_eve_end.innerHTML = 23;
        } else {
            doc_eve_str.innerHTML = str_val + xx;
            doc_eve_end.innerHTML = end_val + xx;
        }
    } else if (xx == 1) {
        if (str_val == 0) {
            doc_eve_str.innerHTML = 1;
            doc_eve_end.innerHTML = 2;
        } else if (str_val == 23) {
            doc_eve_str.innerHTML = 0;
            doc_eve_end.innerHTML = 1;
        } else {
            doc_eve_str.innerHTML = str_val + xx;
            doc_eve_end.innerHTML = end_val + xx;
        }
    } else {}
    save_p_eve_settings();
}

function change_value_mid(xx) {

    var doc_mid_str = document.getElementById("set_mid_start_time_view");
    var doc_mid_end = document.getElementById("set_mid_end_time_view");
    var dur_mid = document.getElementById("dur_mid");
    var dur_mid_val = parseInt(dur_mid.innerHTML);
    var str_val = parseInt(doc_mid_str.innerHTML);
    var end_val = parseInt(doc_mid_end.innerHTML);

    if (xx == 1) {
        find_duration();
        var dur_mid_val = parseInt(dur_mid.innerHTML);
        if (1 <= dur_mid_val && dur_mid_val <= 7) {
            if ((str_val + 1) == 24) {
                doc_mid_str.innerHTML = 0;
                doc_mid_end.innerHTML = 7;
            } else {
                doc_mid_str.innerHTML = str_val + 1;
                if ((str_val + 1) >= 17) {
                    doc_mid_end.innerHTML = ((str_val + 1) + 7) - 24;
                } else {
                    doc_mid_end.innerHTML = (str_val + 1) + 7;
                }

            }
        } else {

        }


    } else if (xx == -1) {
        find_duration();
        var dur_mid_val = parseInt(dur_mid.innerHTML);
        if (1 <= dur_mid_val && dur_mid_val <= 7) {
            if ((str_val - 1) == -1) {
                doc_mid_str.innerHTML = 23;
                doc_mid_end.innerHTML = 6;
            } else {
                doc_mid_str.innerHTML = str_val - 1;
                if ((str_val - 1) >= 17) {
                    doc_mid_end.innerHTML = ((str_val - 1) + 7) - 24;
                } else {
                    doc_mid_end.innerHTML = (str_val - 1) + 7;
                }

            }
        } else {

        }

    } else if (xx == 2) {
        find_duration();
        var dur_mid_val = parseInt(dur_mid.innerHTML);

        if (1 <= dur_mid_val && dur_mid_val < 7) {

            if ((end_val + 1) == 24) {
                doc_mid_end.innerHTML = 0;
            } else {
                doc_mid_end.innerHTML = end_val + 1;
            }
        } else {

        }

    } else if (xx == -2) {
        find_duration();
        var dur_mid_val = parseInt(dur_mid.innerHTML);
        if (1 < dur_mid_val && dur_mid_val <= 7) {

            if ((end_val - 1) == -1) {
                doc_mid_end.innerHTML = 23;
            } else {
                doc_mid_end.innerHTML = end_val - 1;
            }
        } else {

        }

    } else {}
    find_duration();
    save_p_mid_settings();
}

function save_p_eve_settings() {
    var eve_str_val = parseInt(document.getElementById("set_eve_start_time_view").innerHTML);
    var eve_end_val = parseInt(document.getElementById("set_eve_end_time_view").innerHTML);

    var firebaseRef = firebase.database().ref();
    firebaseRef.child("Pirith_eve_str_time").set(eve_str_val);
    firebaseRef.child("Pirith_eve_end_time").set(eve_end_val);

    document.getElementById("P_eve_err_lbl").style.display = "";
    document.getElementById("P_eve_err_br").style.display = "";
}

function save_p_mid_settings() {
    var mid_str_val = parseInt(document.getElementById("set_mid_start_time_view").innerHTML);
    var mid_end_val = parseInt(document.getElementById("set_mid_end_time_view").innerHTML);

    var firebaseRef = firebase.database().ref();
    firebaseRef.child("Pirith_mid_str_time").set(mid_str_val);
    firebaseRef.child("Pirith_mid_end_time").set(mid_end_val);

    document.getElementById("P_mid_err_lbl").style.display = "";
    document.getElementById("P_mid_err_br").style.display = "";
}


for (let index = 1; index <= 4; index++) {

    //get Lights_bulb_status
    const Lights_bulb_status = firebase.database().ref().child('Lights_bulb_' + index + '_status');
    Lights_bulb_status.on('value', snap => {
        var Lights_bulb_status = JSON.stringify(snap.val());
        if (Lights_bulb_status == 1) {
            document.getElementById("on_lights_" + index + "").style.display = "block";
            document.getElementById("off_lights_" + index + "").style.display = "none";
        } else {
            document.getElementById("on_lights_" + index + "").style.display = "none";
            document.getElementById("off_lights_" + index + "").style.display = "block";
        }
        countOnLights();
    });
}

function toggle_lights(xx, yy) {
    var firebaseRef = firebase.database().ref();
    firebaseRef.child("Lights_bulb_" + xx + "_status").set(yy);

    firebaseRef.child("Lights_system_mode").set(1);
}

function countOnLights() {
    var xcount = 0;
    for (let index = 1; index <= 4; index++) {
        var lightDivStyle = document.getElementById("on_lights_" + index + "").style.display;
        if (lightDivStyle == "block") {
            xcount++;
        }
    }
    document.getElementById("onLightsCounter").innerHTML = xcount + "/4";
    document.getElementById("onLightsCounterOverview").innerHTML = xcount + "/4";
}

//get Lights_system_mode
const Lights_system_mode = firebase.database().ref().child('Lights_system_mode');
Lights_system_mode.on('value', snap => {
    var Lights_system_mode = JSON.stringify(snap.val());
    if (Lights_system_mode == 1) {
        document.getElementById("lightsMode").innerHTML = "Manual Mode";
        document.getElementById("lightsModeOverview").innerHTML = "Manual Mode";
    } else {
        document.getElementById("lightsMode").innerHTML = "Auto Mode";
        document.getElementById("lightsModeOverview").innerHTML = "Auto Mode";
    }
});


//------------------------------------------------------------------------------------------------------ firebase******