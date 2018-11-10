#include <SoftwareSerial.h>
#include <FastLED.h>
#include <Timer.h>
#include <Adafruit_NeoPixel.h>

#define NUM_LEDS    24
#define LED_PIN     11    //Data pin to LED ring
#define TONE_PIN    9
#define STATE_PIN   6
#define TX_PIN      2
#define RX_PIN      3

#define MAX_DATA    32
#define START_LED   253
#define START_TONE  254
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
  recieve();
  if(newData)
    useData();
}

int connected(){
  return digitalRead(STATE_PIN);
}

void setLED(int i, int r, int g, int b){
  strip.setPixelColor(i, r, g, b);
}

void setLED(int r, int g, int b){
  for(int i=0; i<NUM_LEDS; i++)
    setLED(i, r, g, b);
}

void recieve(){
  if(mySerial.available() > 0 && !newData){
    char c  = mySerial.read();
    int in = (c+256)%256;

    if(in == START_LED || in == START_TONE){
      reading = true;
      data[0] = in;
      dataIndex = 1;
    }
    else if(reading){
      if(in == END){
        reading = false;
        newData = true;
        
      } else {
        data[dataIndex] = in;
        dataIndex++;
      }
    }
  }
}

void useData(){

  switch(data[0]){

    case START_LED:
      setLED(data[1], data[2], data[3], data[4]);
      strip.show();
      break;

    case START_TONE:
      tone(TONE_PIN, data[1]*100, data[2]*10);
      break;

    default:
      break;
  }
  
  newData = false;
}

