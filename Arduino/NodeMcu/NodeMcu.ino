#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <ezTime.h>

SoftwareSerial NodeMCU(D2, D3);

Timezone myLocalTime ;

#define LED_G D0
#define LED_R D1
#define LED_B D5

int device_type = 1;
int Select_database = 1;
int Select_wifi = 1;
int Set_default_val = 0;

int maxHeightOfTank = 0;

String FIREBASE_HOST = "";
String FIREBASE_AUTH = "";
String WIFI_SSID = "";
String WIFI_PASSWORD = "";

const int trigPin = D7;
const int echoPin = D8;

long duration;
int distance;
float tank_capacity;

String Time = "time";
String Date = "date";
String str_hours = "str_hours";
String str_minutes = "str_minutes";
String str_seconds = "str_seconds";

int Val_WATER_waterpump_status;
int Val_Pirith_system_status;
int Val_Lights_bulb_1_status;
int Val_Lights_bulb_2_status;
int Val_Lights_bulb_3_status;
int Val_Lights_bulb_4_status;
int Val_Lights_bulb_1_2_status;
int Val_Lights_bulb_3_4_status;

void setup()
{
  Serial.begin(9600);
  NodeMCU.begin(9600);
  Serial.println(" ");
  Serial.println(" ");
  Serial.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
  Serial.println("/////System booted/////");

  //----------------------------------------------------------------SET PIN PORTS
    pinMode(LED_G, OUTPUT);
    pinMode(LED_R, OUTPUT);
    pinMode(LED_B, OUTPUT);
    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
  //----------------------------------------------------------------

  //----------------------------------------------------------------WIFI
  
    //Select Wifi--------------
    if(Select_wifi == 1){
      WIFI_SSID = "/<4V!Y4___" ;
      WIFI_PASSWORD = ",N5Ea;n%F-v9+nd:" ;
    }else if(Select_wifi == 2){
      WIFI_SSID = "kk" ;
      WIFI_PASSWORD = "1234567890" ;
    }else if(Select_wifi == 3){
      WIFI_SSID = "NGuest" ;
      WIFI_PASSWORD = "N$bM@123" ;
    }else{}
    //--------------
    
    //Conecting to Wifi--------------
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print("Connecting to ");
    Serial.print(WIFI_SSID);
    while (WiFi.status() != WL_CONNECTED){
      Serial.print(".");
      digitalWrite(LED_R, HIGH);
      delay(100);
      digitalWrite(LED_R, LOW);
      delay(100);
    }
    //--------------

    //Connected to Wifi--------------
    Serial.println();
    Serial.println("WIFI Connected");
    Serial.println(WiFi.localIP());
    digitalWrite(LED_G, HIGH);
    delay(500);
    digitalWrite(LED_G, LOW);
    //--------------
    
  //----------------------------------------------------------------

  //----------------------------------------------------------------FIREBASE
  
    //Select database--------------
    if(Select_database == 1){
      FIREBASE_HOST = "smarthome-ad51f.firebaseio.com";
      FIREBASE_AUTH = "9xFPR5ajjpQyzribPnB6GFBGH7rI5fRLL7MkoxGv";
    }
    else if(Select_database == 2){
      FIREBASE_HOST = "testpro-d48db.firebaseio.com";
      FIREBASE_AUTH = "NITXzvtzxCFNqBAL4eh4wVdpktcVWHr33dykhOJ6";
    }else{}
    //--------------
    
    //firebase connection--------------
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    Serial.print("FIREBASE_HOST : ");
    Serial.println(FIREBASE_HOST);
    //--------------
    
  //----------------------------------------------------------------

  //----------------------------------------------------------------Ez Time
    Serial.println("Synchronize with an internet time server ....");
    myLocalTime.setLocation(F("lk"));

    digitalWrite(LED_B, HIGH);
    while (!waitForSync(3))
    {
      Serial.println("timeSync FAILED!!");
      sys_restart();
    }
    digitalWrite(LED_B, LOW);
    digitalWrite(LED_G, HIGH);
    Serial.println("Got timeSync");
    digitalWrite(LED_G, LOW);
  //----------------------------------------------------------------

  Serial.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
}

void loop()
{
  Serial.println("");
  Serial.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

  //----------------------------------------------------------------system_check
  digitalWrite(LED_G, HIGH);
  system_check();
  Get_dateNtime();
  digitalWrite(LED_G, LOW);
  delay(100);
  //----------------------------------------------------------------

  //----------------------------------------------------------------Water_system
  int WATER_sub_system_status = Firebase.getInt("WATER_sub_system_status");
  if(WATER_sub_system_status == 1){
    Serial.println("-----------------------Water_system");
    digitalWrite(LED_G, HIGH);
    Water_system();
    digitalWrite(LED_G, LOW);
    delay(100);
    Serial.println("-----------------------");
  }
  else{
    Serial.println("Skipped Water sub system /-> ");
  }
  //----------------------------------------------------------------

  //----------------------------------------------------------------Pirith_system
  int Pirith_sub_system_status = Firebase.getInt("Pirith_sub_system_status");
  if(Pirith_sub_system_status == 1){
    Serial.println("-----------------------Pirith_system");
    digitalWrite(LED_G, HIGH);
    Pirith_system();
    digitalWrite(LED_G, LOW);
    delay(100);
    Serial.println("-----------------------");
  }
  else{
    Serial.println("Skipped Pirith sub system /-> ");
  }
  //----------------------------------------------------------------

  //----------------------------------------------------------------Lights_system
  int Lights_sub_system_status = Firebase.getInt("Lights_sub_system_status");
  if(Lights_sub_system_status == 1){
    Serial.println("-----------------------Lights_system");
    digitalWrite(LED_G, HIGH);
    Lights_system();
    digitalWrite(LED_G, LOW);
    delay(100);
    Serial.println("-----------------------");
  }
  else{
    Serial.println("Skipped Lights sub system /-> ");
  }
  //----------------------------------------------------------------

  //----------------------------------------------------------------send data
  digitalWrite(LED_G, HIGH);
 
  String SendValue = "1" + String(Val_WATER_waterpump_status) + String(Val_Pirith_system_status)+ String(Val_Lights_bulb_1_2_status)+ String(Val_Lights_bulb_3_4_status);

  Serial.print("SendValue : ");
  Serial.println(SendValue);

  NodeMCU.print(SendValue);
  NodeMCU.println("\n");

  Get_dateNtime();
  Firebase.setString("Device_online_time", Time);

  digitalWrite(LED_G, LOW);
  //----------------------------------------------------------------

  Serial.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
}

int Water_system()
{
  // Clears the trigPin
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);

  // Sets the trigPin on HIGH state for 10 micro seconds
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);

  // Reads the echoPin, returns the sound wave travel time in microseconds
  duration = pulseIn(echoPin, HIGH);

  // Calculating the distance
  distance = duration * 0.034 / 2;
  Serial.print("distance :");
  Serial.println(distance);

  if(device_type == 1){
    Serial.println("Device type  :  Home");
    maxHeightOfTank = 119;
    if(Set_default_val == 1){
      distance = 53;
    }
  }
  else if(device_type == 2){
    Serial.println("Device type  : Model");
    maxHeightOfTank = 27;
    if(Set_default_val == 1){
      distance = 8;
    }
  }
  else{}

  if (distance == 0)
  {
    Serial.println("Sonar Sensor disconnected");
    Firebase.setString("WATER_system_status_message", "Warning ,Sonar Sensor disconnected!!");
  }
  else if (distance <= maxHeightOfTank)
  {
    if(device_type == 1){
      tank_capacity = 119 - distance ;
    }
    else if(device_type == 2){
      tank_capacity = 100 - (((distance - 6) * 100) / 20);
    }
    else{}
    
    
    // set tank_free_capacity
    Firebase.setFloat("WATER_watertank_capacity", tank_capacity);
    Serial.print("WATER_watertank_capacity  : ");
    Serial.println(tank_capacity);

    int critical_water_level = Firebase.getInt("WATER_watertank_critical_water_level");
    int end_water_level = Firebase.getInt("WATER_watertank_end_water_level");
    int WATER_waterpump_status = Firebase.getInt("WATER_waterpump_status");
    Serial.print("WATER_waterpump_status  : ");
    Serial.println(WATER_waterpump_status);

    if (tank_capacity <= critical_water_level)
    {
      Val_WATER_waterpump_status = 1;
      Firebase.setInt("WATER_waterpump_status", 1);
      Firebase.setString("WATER_system_status_message", "Auto filling........");
    }
    else
    {

      if (WATER_waterpump_status == 1)
      {
        if (tank_capacity >= end_water_level)
        {
          Val_WATER_waterpump_status = 0;
          Firebase.setInt("WATER_waterpump_status", 0);
          Firebase.setString("WATER_system_status_message", "Automaticaly Water pump Switched OFF !!");
        }
        else
        {
          Val_WATER_waterpump_status = 1;
          Firebase.setString("WATER_system_status_message", "Auto filling........");
        }
      }
      else if (WATER_waterpump_status == 0)
      {
        Val_WATER_waterpump_status = 0;
        Firebase.setString("WATER_system_status_message", "Automaticaly Water pump Switched OFF !!");
      }
      else
      {
      }
    }
  }
  else
  {
    Val_WATER_waterpump_status = 0;
    Firebase.setInt("WATER_waterpump_status", 0);
    Firebase.setString("WATER_system_status_message", "Warning Overflow!!!!!");
  }
}

int Pirith_system()
{
  int Pirith_system_remote_control = Firebase.getInt("Pirith_system_remote_control");
  int Pirith_system_status = Firebase.getInt("Pirith_system_status");
  int int_str_hours = str_hours.toInt();
  int Pirith_eve_str_time = Firebase.getInt("Pirith_eve_str_time");
  int Pirith_eve_end_time = Firebase.getInt("Pirith_eve_end_time");
  int Pirith_mid_str_time = Firebase.getInt("Pirith_mid_str_time");
  int Pirith_mid_end_time = Firebase.getInt("Pirith_mid_end_time");

  /*Serial.print("int_str_hours :");
  Serial.println(int_str_hours);
  Serial.print("Pirith_eve_str_time :");
  Serial.println(Pirith_eve_str_time);
  Serial.print("Pirith_eve_end_time :");
  Serial.println(Pirith_eve_end_time);
  Serial.print("Pirith_mid_str_time :");
  Serial.println(Pirith_mid_str_time);
  Serial.print("Pirith_mid_end_time :");
  Serial.println(Pirith_mid_end_time);*/
  

  if (Pirith_system_remote_control == 0)
  {
    if (Pirith_eve_str_time <= int_str_hours && int_str_hours < Pirith_eve_end_time)
    {
      Val_Pirith_system_status = 1;
      Firebase.setInt("Pirith_system_status", 1);
      Serial.print("Pirith_system_status : ");
      Serial.println("1");
    }
    else if (Pirith_mid_str_time <= int_str_hours && int_str_hours < Pirith_mid_end_time)
    {
      Val_Pirith_system_status = 2;
      Firebase.setInt("Pirith_system_status", 2);
      Serial.print("Pirith_system_status : ");
      Serial.println("2");
    }
    else
    {
      Val_Pirith_system_status = 0;
      Firebase.setInt("Pirith_system_status", 0);
      Serial.print("Pirith_system_status : ");
      Serial.println("0");
    }
  }
  else if (Pirith_system_remote_control == 1)
  {
    Val_Pirith_system_status = 1;
    Firebase.setInt("Pirith_system_status", 1);
    Serial.print("Pirith_system_status : ");
    Serial.println("1");
  }
  else if (Pirith_system_remote_control == 2)
  {
    Val_Pirith_system_status = 2;
    Firebase.setInt("Pirith_system_status", 2);
    Serial.print("Pirith_system_status : ");
    Serial.println("2");
  }
  else
  {
  }
}

int Lights_system()
{
  int int_str_hours = str_hours.toInt();
  int Lights_bulb_1 = Firebase.getInt("Lights_bulb_1_status");
  int Lights_bulb_2 = Firebase.getInt("Lights_bulb_2_status");
  int Lights_bulb_3 = Firebase.getInt("Lights_bulb_3_status");
  int Lights_bulb_4 = Firebase.getInt("Lights_bulb_4_status");
  int Lights_system_mode = Firebase.getInt("Lights_system_mode");

  if(Lights_system_mode == 0){
    Serial.println("Lights_system_mode : Auto");
    if(19 <= int_str_hours && int_str_hours < 22){
      Firebase.setInt("Lights_bulb_1_status", 1);
      Firebase.setInt("Lights_bulb_2_status", 1);
      Firebase.setInt("Lights_bulb_3_status", 1);
      Firebase.setInt("Lights_bulb_4_status", 1);
      Val_Lights_bulb_1_status = 1;
      Val_Lights_bulb_2_status = 1;
      Val_Lights_bulb_3_status = 1;
      Val_Lights_bulb_4_status = 1;
    }
    else{
      Val_Lights_bulb_1_status = 0;
      Val_Lights_bulb_2_status = 0;
      Val_Lights_bulb_3_status = 0;
      Val_Lights_bulb_4_status = 0;
    }
  }
  else {
    
    if(Lights_bulb_1 == 0 && Lights_bulb_2 == 0 && Lights_bulb_3 == 0 && Lights_bulb_4 == 0){
      Firebase.setInt("Lights_system_mode", 0);
      Serial.println("Lights_system_mode : Auto");
      
      Val_Lights_bulb_1_status = 0;
      Val_Lights_bulb_2_status = 0;
      Val_Lights_bulb_3_status = 0;
      Val_Lights_bulb_4_status = 0;
    }
    else{
      Firebase.setInt("Lights_system_mode", 1);
      Serial.println("Lights_system_mode : Manual");
      Val_Lights_bulb_1_status = Lights_bulb_1;
      Val_Lights_bulb_2_status = Lights_bulb_2;
      Val_Lights_bulb_3_status = Lights_bulb_3;
      Val_Lights_bulb_4_status = Lights_bulb_4;
    }
  }

  if(Val_Lights_bulb_1_status == 1 && Val_Lights_bulb_2_status == 0){
    Val_Lights_bulb_1_2_status = 1;
  }else if(Val_Lights_bulb_1_status == 0 && Val_Lights_bulb_2_status == 1){
    Val_Lights_bulb_1_2_status = 2;
  }else if(Val_Lights_bulb_1_status == 1 && Val_Lights_bulb_2_status == 1){
    Val_Lights_bulb_1_2_status = 3;
  }else if(Val_Lights_bulb_1_status == 0 && Val_Lights_bulb_2_status == 0){
    Val_Lights_bulb_1_2_status = 4;
  }else{}

  if(Val_Lights_bulb_3_status == 1 && Val_Lights_bulb_4_status == 0){
    Val_Lights_bulb_3_4_status = 1;
  }else if(Val_Lights_bulb_3_status == 0 && Val_Lights_bulb_4_status == 1){
    Val_Lights_bulb_3_4_status = 2;
  }else if(Val_Lights_bulb_3_status == 1 && Val_Lights_bulb_4_status == 1){
    Val_Lights_bulb_3_4_status = 3;
  }else if(Val_Lights_bulb_3_status == 0 && Val_Lights_bulb_4_status == 0){
    Val_Lights_bulb_3_4_status = 4;
  }else{}
  String Lights_system_status = String(Val_Lights_bulb_1_status) + String(Val_Lights_bulb_2_status) + String(Val_Lights_bulb_3_status) + String(Val_Lights_bulb_4_status);
  Serial.println("Lights_system_status : " + Lights_system_status);
}

int system_check()
{

  int restart_check = Firebase.getInt("Device_restart");

  if (restart_check == 1)
  {
    Firebase.setInt("Device_restart", 0);
    Serial.println("//////User requested to Reboot the device//////");
    sys_restart();
  }
  else
  {
  }

  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.println("//////WIFI Disconnected//////");
    sys_restart();
  }

  delay(1000);

  if (Firebase.failed())
  {
    Serial.println("//////Firebase ERROR//////");
    Serial.println(Firebase.error());
    sys_restart();
  }
}

int sys_restart()
{
  Serial.println("////////////Rebooting Device////////////");
  Serial.println("************************************************************");
  Serial.println("");
  digitalWrite(LED_G, HIGH);
  digitalWrite(LED_R, HIGH);
  delay(500);
  digitalWrite(LED_G, LOW);
  digitalWrite(LED_R, LOW);
  delay(500);
  digitalWrite(LED_G, HIGH);
  digitalWrite(LED_R, HIGH);
  delay(500);
  digitalWrite(LED_G, LOW);
  digitalWrite(LED_R, LOW);
  ESP.reset();
}

int Get_dateNtime(){

  Serial.println(myLocalTime.dateTime());

  int timeStatus_val = timeStatus();
  String errorString_val = errorString();
  int error_val = error();

  /*
  Serial.println("");
  Serial.println("//////////////////////////////////");
  
  Serial.print("timeStatus :  ");
  Serial.println(timeStatus_val);
  
  Serial.print("Status :  ");
  Serial.print(errorString_val);
  Serial.print(" ( ");
  Serial.print(error_val);
  Serial.println(" ) ");

  Serial.println("//////////////////////////////////");
  Serial.println("");
  */

  if(timeStatus_val != 2 || error_val != 0){
    Serial.print("Status :  ");
    Serial.println(errorString_val);
    
    Serial.print("lastNtpUpdateTime :  ");
    Serial.println(lastNtpUpdateTime());
  
    sys_restart();
  }

  Time = myLocalTime.dateTime("H:i:s");
  Date = myLocalTime.dateTime("d:m:Y");
  str_hours = myLocalTime.dateTime("H");
  str_minutes = myLocalTime.dateTime("i");
  str_seconds = myLocalTime.dateTime("s");

  /*
  Serial.println("");
  Serial.println("//////////////////////////////////");
  Serial.print("Time :  ");
  Serial.println(Time);
  Serial.print("Date :  ");
  Serial.println(Date);
  Serial.print("Hours :  ");
  Serial.println(str_hours);
  Serial.print("Minutes :  ");
  Serial.println(str_minutes);
  Serial.print("Seconds :  ");
  Serial.println(str_seconds);
  Serial.println("//////////////////////////////////");
  Serial.println("");
  */
}
