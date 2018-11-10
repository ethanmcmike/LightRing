#include <SoftwareSerial.h>
#include <FastLED.h>
#include <Timer.h>
#include <Adafruit_NeoPixel.h>
#include <String.h>

#define NUM_LEDS    24
#define LED_PIN     11    //Data pin to LED ring
#define STATE_PIN   14    //A0 - Bluetooth connection state
#define TX_PIN      2
#define RX_PIN      3

#define MAX_DATA    32
#define START       254
#define END         255

SoftwareSerial mySerial(TX_PIN, RX_PIN);
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, LED_PIN);
Timer timer;

int r, g, b;

int data[MAX_DATA], dataIndex = 0;
bool newData = false, reading = false;

void setup() {
  pinMode(STATE_PIN, INPUT);
  
  mySerial.begin(115200);
  Serial.begin(115200);
  
  strip.begin();
}

void loop() {
//    if(mySerial.available() > 0 && !newData){
//    char c  = mySerial.read();
//    int in = (c+256)%256;
//    Serial.println(in);
//    }
  recieve();
  showNewData();
}

int connected(){
  return analogRead(STATE_PIN);
}

void setLED(int r, int g, int b){
  for(int i=0; i<NUM_LEDS; i++)
    strip.setPixelColor(i, r, g, b);
}

void recieve(){
  if(mySerial.available() > 0 && !newData){
    char c  = mySerial.read();
    int in = (c+256)%256;

    if(reading){
      if(in == END){
        reading = false;
        newData = true;
        
      } else {
        data[dataIndex] = in;
        dataIndex++;
      }
      
    } else {
      if(in == START){
        reading = true;
        dataIndex = 0;
      }
    }

//    if(reading){
//      if(in == DIV || in == END){
////        Serial.println("DIV");
//        data[dataIndex] = chunk.toInt();
//        dataIndex++;
//        chunk = "";
//
//        if(in == END){
////          Serial.println("END");
//          reading = false;
//          newData = true;
//        }
//      }
//      else if(47 < in && in < 58){    //is number
////        Serial.println(in);
//        chunk += in;
//      }
//    }
//    else if(in == START){         //START
////      Serial.println("START");
//      dataIndex = 0;
//      reading = true;
//    }
  }
}

void showNewData(){
  if(newData){
    
    Serial.println("DATA:");

    for(int i=0; i<dataIndex; i++){
      Serial.println(data[i]);
    }      
    newData = false;

    r = data[0];
    g = data[1];
    b = data[2];
    setLED(r, g, b);
    strip.show();
  }
}

