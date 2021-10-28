#include <SoftwareSerial.h>
#include <SD.h>
#include <TMRpcm.h>

SoftwareSerial ArduinoMega(11, 12);
TMRpcm playerM;

#define lightbulb_1_relay 22
#define lightbulb_2_relay 23
#define lightbulb_3_relay 24
#define lightbulb_4_relay 25

#define Water_pump_relay_light 28
#define Speker_relay_light 29

#define LED_R 2
#define LED_G 3
#define LED_B 4

#define LED_L1 30
#define LED_L2 31
#define LED_L3 32
#define LED_L4 33

#define Water_pump_relay 26
#define Speker_relay 27

String pervious_Water_system_status = "0";
String pervious_Pirith_system_status = "0";

int PlayerM_status;

int Lights_bulb_1_status = 0;
int Lights_bulb_2_status = 0;
int Lights_bulb_3_status = 0;
int Lights_bulb_4_status = 0;

void setup()
{
  Serial.begin(115200);
  ArduinoMega.begin(115200);
  Serial.println(" ");
  Serial.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
  Serial.println("/////System booted/////");

  //----------------------------------------------------------------SET PIN PORTS
  pinMode(Water_pump_relay_light, OUTPUT);
  pinMode(Speker_relay_light, OUTPUT);
  
  pinMode(LED_R, OUTPUT);
  pinMode(LED_G, OUTPUT);
  pinMode(LED_B, OUTPUT);

  pinMode(LED_L1, OUTPUT);
  pinMode(LED_L2, OUTPUT);
  pinMode(LED_L3, OUTPUT);
  pinMode(LED_L4, OUTPUT);
  
  pinMode(Water_pump_relay, OUTPUT);
  digitalWrite(Water_pump_relay, HIGH);
  
  pinMode(Speker_relay, OUTPUT);
  digitalWrite(Speker_relay, HIGH);

  pinMode(lightbulb_1_relay, OUTPUT);
  digitalWrite(lightbulb_1_relay, HIGH);
  pinMode(lightbulb_2_relay, OUTPUT);
  digitalWrite(lightbulb_2_relay, HIGH);
  pinMode(lightbulb_3_relay, OUTPUT);
  digitalWrite(lightbulb_3_relay, HIGH);
  pinMode(lightbulb_4_relay, OUTPUT);
  digitalWrite(lightbulb_4_relay, HIGH);

  playerM.speakerPin = 5;

  //----------------------------------------------------------------

  //----------------------------------------------------------------sd card begin
  digitalWrite(LED_B, HIGH);
  while (!SD.begin(53)) //If the card is available.
  {
    digitalWrite(LED_R, HIGH);
    Serial.println("SD fail"); //Write in the serial port "SD fail".
    delay(1000);
    digitalWrite(LED_R, LOW);
  }
  digitalWrite(LED_B, LOW);
  digitalWrite(LED_G, HIGH);
  Serial.println("SD Suceess");
  delay(100);
  digitalWrite(LED_G, LOW);
  
  //----------------------------------------------------------------
  
  Serial.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
}

void loop()
{
  while (ArduinoMega.available() > 0)
  {
    digitalWrite(LED_R, HIGH);
    digitalWrite(LED_G, HIGH);
    int Retrived_val = ArduinoMega.parseFloat();
    if (ArduinoMega.read() == '\n')
    {
      Serial.println("************************************************************");

      Serial.print("Retrived_val : ");
      Serial.println(Retrived_val);
      String Retrived_val_str = String(Retrived_val);

      String Water_pump_status = Retrived_val_str.substring(1, 2); /// substring 1
      Serial.print("Water_pump_status :");
      Serial.println(Water_pump_status);

      String Pirith_system_status = Retrived_val_str.substring(2, 3); /// substring 2
      Serial.print("Pirith_system_status :");
      Serial.println(Pirith_system_status);

      String Lights_bulb_1_2_status = Retrived_val_str.substring(3, 4); /// substring 3
      Serial.print("Lights_bulb_1_2_status :");
      Serial.println(Lights_bulb_1_2_status);
      
      String Lights_bulb_3_4_status = Retrived_val_str.substring(4, 5); /// substring 4
      Serial.print("Lights_bulb_3_4_status :");
      Serial.println(Lights_bulb_3_4_status);

      //WATER----------------------------------------------------------------------------------->
      if (pervious_Water_system_status == Water_pump_status)
      {
        /* Data not Changed */
        if (Water_pump_status == "1")
        {
          Serial.println("Water Refilling in progress....");
        }
      }
      else
      {
        /* Data Chnaged */
        pervious_Water_system_status = Water_pump_status;
        RESPONSE_WATER_SYSTEM(Water_pump_status);
      }
      //----------------------------------------------------------------------------------->

      //PIRITH----------------------------------------------------------------------------------->
      if (pervious_Pirith_system_status == Pirith_system_status)
      {
        /* Data not Changed */
        if (Pirith_system_status == "1")
        {
          int isPlaying = playerM.isPlaying();
          if (isPlaying == 0)
          {
            Speker_relay_fun(0);
            playerM.stopPlayback();
          }
          else
          {
            Serial.println("Evening pirith chanting....");
          }
        }
        else if (Pirith_system_status == "2")
        {
          player_repeat();
        }
        else
        {
        }
      }
      else
      {
        /* Data Chnaged */
        pervious_Pirith_system_status = Pirith_system_status;
        RESPONSE_PIRITH_SYSTEM(Pirith_system_status);
      }
      //----------------------------------------------------------------------------------->

      //LIGHTS----------------------------------------------------------------------------------->
      if(Lights_bulb_1_2_status == "1"){
        Lights_bulb_1_status = 1;
        Lights_bulb_2_status = 0;
      }else if(Lights_bulb_1_2_status == "2"){
        Lights_bulb_1_status = 0;
        Lights_bulb_2_status = 1;
      }else if(Lights_bulb_1_2_status == "3"){
        Lights_bulb_1_status = 1;
        Lights_bulb_2_status = 1;
      }else if(Lights_bulb_1_2_status == "4"){
        Lights_bulb_1_status = 0;
        Lights_bulb_2_status = 0;
      }
      else{}

      if(Lights_bulb_3_4_status == "1"){
        Lights_bulb_3_status = 1;
        Lights_bulb_4_status = 0;
      }else if(Lights_bulb_3_4_status == "2"){
        Lights_bulb_3_status = 0;
        Lights_bulb_4_status = 1;
      }else if(Lights_bulb_3_4_status == "3"){
        Lights_bulb_3_status = 1;
        Lights_bulb_4_status = 1;
      }else if(Lights_bulb_3_4_status == "4"){
        Lights_bulb_3_status = 0;
        Lights_bulb_4_status = 0;
      }
      else{}
      switch_lights(Lights_bulb_1_status,Lights_bulb_2_status,Lights_bulb_3_status,Lights_bulb_4_status);
      
      //----------------------------------------------------------------------------------->

      Serial.println("************************************************************");
    }
    digitalWrite(LED_R, LOW);
    digitalWrite(LED_G, LOW);
  }
}

int RESPONSE_WATER_SYSTEM(String W_val)
{
  if (W_val == "1")
  {
    Serial.println("Water pump is switched ON !!!!!!!");
    Water_pump_relay_fun(1);
    if (pervious_Pirith_system_status == "0")
    {
      Speker_relay_fun(1);
      playerM.play("5.wav");
      delay(4000);
      playerM.stopPlayback();
      Speker_relay_fun(0);
    }
    else
    {
    }
  }
  else if (W_val == "0")
  {
    Serial.println("Water pump is switched OFF !!!!!!!");
    Water_pump_relay_fun(0);
    if (pervious_Pirith_system_status == "0")
    {
      Speker_relay_fun(1);
      playerM.play("6.wav");
      delay(4000);
      playerM.stopPlayback();
      Speker_relay_fun(0);
    }
    else
    {
    }
  }
  else
  {
  }
}

int RESPONSE_PIRITH_SYSTEM(String P_val)
{
  if (P_val == "1")
  {
    Speker_relay_fun(1);
    playerM.play("7.wav");
    delay(4000);
    playerM.play("4.wav");
  }
  else if (P_val == "2")
  {
    Speker_relay_fun(1);
    playerM.play("8.wav");
    delay(4000);
    playerM.play("1.wav");
    PlayerM_status = 1;
  }
  else
  {
    Speker_relay_fun(0);
    playerM.stopPlayback();
  }
}

int player_repeat()
{
  int isPlaying = playerM.isPlaying();
  if (isPlaying == 0)
  {
    Serial.print("Switched to next part....");
    if (PlayerM_status == 1)
    {
      playerM.play("2.wav");
      PlayerM_status = 2;
    }
    else if (PlayerM_status == 2)
    {
      playerM.play("3.wav");
      PlayerM_status = 3;
    }
    else if (PlayerM_status == 3)
    {
      playerM.play("1.wav");
      PlayerM_status = 1;
    }
  }
  else
  {
    Serial.print("Midnight Pirith Chanting part ");
    Serial.println(PlayerM_status);
  }
}

int Speker_relay_fun(int xx)
{

  if (xx == 1)
  {
    digitalWrite(Speker_relay_light, HIGH);
    digitalWrite(Speker_relay, LOW);
  }
  else if (xx == 0)
  {
    digitalWrite(Speker_relay_light, LOW);
    digitalWrite(Speker_relay, HIGH);
  }
  else
  {
  }
}

int Water_pump_relay_fun(int yy)
{
  if (yy == 1)
  {
    digitalWrite(Water_pump_relay_light, HIGH);
    digitalWrite(Water_pump_relay, LOW);
  }
  else if (yy == 0)
  {
    digitalWrite(Water_pump_relay_light, LOW);
    digitalWrite(Water_pump_relay, HIGH);
  }
  else
  {
  }
}

int switch_lights(int x1, int x2, int x3, int x4){
  if(x1 == 1){
    digitalWrite(lightbulb_1_relay, LOW);
    digitalWrite(LED_L1, HIGH);
  }
  else{
    digitalWrite(lightbulb_1_relay, HIGH);
    digitalWrite(LED_L1, LOW);
  }
  if(x2 == 1){
    digitalWrite(lightbulb_2_relay, LOW);
    digitalWrite(LED_L2, HIGH);
  }
  else{
    digitalWrite(lightbulb_2_relay, HIGH);
    digitalWrite(LED_L2, LOW);
  }
  if(x3 == 1){
    digitalWrite(lightbulb_3_relay, LOW);
    digitalWrite(LED_L3, HIGH);
  }
  else{
    digitalWrite(lightbulb_3_relay, HIGH);
    digitalWrite(LED_L3, LOW);
  }
  if(x4 == 1){
    digitalWrite(lightbulb_4_relay, LOW);
    digitalWrite(LED_L4, HIGH);
  }
  else{
    digitalWrite(lightbulb_4_relay, HIGH);
    digitalWrite(LED_L4, LOW);
  }
}
