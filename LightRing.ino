//#include <toneAC2.h>
//#include <toneAC.h>             //Uses Timer #1, tone() uses Timer #2
//#include <SoftwareSerial.h>     //Uses Timer #2
#include <TimerFreeTone.h>
#include <AltSoftSerial.h>        //Uses Timer #1
#include <FastLED.h>
#include <Timer.h>
#include <Adafruit_NeoPixel.h>
#include <SimplexNoise.h>
#include <LightRing.h>

#define NUM_LEDS    24
#define LED_PIN     10
#define STATE_PIN   4
#define BUTTON_PIN  3
#define TONE_PIN    A2
#define TX_PIN      9
#define RX_PIN      8

#define MAX_DATA    6
#define BUTTON      251
#define START_LED   252
#define START_TONE  253
#define RENDER      254
#define END         255

//SoftwareSerial bt(TX_PIN, RX_PIN);
AltSoftSerial bt(TX_PIN, RX_PIN);
Adafruit_NeoPixel strip = Adafruit_NeoPixel(NUM_LEDS, LED_PIN);
Timer timer, stripTimer, debounceTimer, noiseTimer;
SimplexNoise sn;
LightRing ring(24);

//Serial data
int data[MAX_DATA];
int dataIndex = 0;
bool reading = false;

//Timer data
const int led_pin = PB5;
const uint8_t t2_load = 0;
const uint8_t t2_comp = 43;

//Tone data
boolean singing = false;
long period, duty, duration;
long toneStart;

//Simplex data
float noiseInc = 0.01;
int noiseDiv = 12;
int noiseBrightness = 50;
float noiseTime;

void setup() {
  pinMode(STATE_PIN, INPUT);
  pinMode(BUTTON_PIN, INPUT_PULLUP);
  pinMode(TONE_PIN, OUTPUT);
  attachInterrupt(digitalPinToInterrupt(BUTTON_PIN), click, LOW);

  bt.begin(38400);
  Serial.begin(38400);

  ring.setState(false);
  
  strip.begin();
}

int out = 0;

void loop() {  
  if(connected()){
    receive();
  } else {
    standby();
  }
}

int connected(){
  return digitalRead(STATE_PIN);
}

void setLED(int i, int r, int g, int b){
  if(i == NUM_LEDS){    //Set all to a single color
    setLED(r, g, b);
    return;
    
  } else if(0<=i && i < NUM_LEDS){
    strip.setPixelColor(i, r, g, b);
  }
}

void setLED(int r, int g, int b){
  for(int i=0; i<NUM_LEDS; i++)
    setLED(i, r, g, b);
}

void receive(){
  if(bt.available() > 0){
    char c  = bt.read();
    int in = (c+256)%256;   //Converts (-128,128) to (0,256)

    if(reading){

      data[dataIndex] = in;
      dataIndex++;

      Serial.print(in);
      Serial.print(" ");
      
      if(in == END || dataIndex >= MAX_DATA){   //End of command
        reading = false;
        useData();
//        readbackData();
        Serial.println();
      }
    }
    else if(in == START_LED || in == RENDER){    //Start of command
      reading = true;
      data[0] = in;
      dataIndex = 1;
      Serial.print(in);
      Serial.print(" ");
    }
    
  }
}

void useData(){

  switch(data[0]){

    case START_LED:
      if(dataIndex == 6){
        setLED(data[1], data[2], data[3], data[4]);
      }
      else if(dataIndex == 7){    //Set a range of LEDs to a color
        int off = data[1];
        int len = data[2];
        for(int i=off; i<off+len; i++){
          setLED(i, data[3], data[4], data[5]);
        }
      }
      
      break;

    case START_TONE:
//      tone(TONE_PIN, data[1]*100, data[2]*10);
      break;

    case RENDER:

      if(data[1] == 0)
        setLED(0, 0, 0);
      
      strip.show();
      break;

    default:
      break;
  }
}

void readbackData(){
  Serial.print("READBACK: ");
  for(int i=0; i<dataIndex; i++){
    Serial.print(data[i]);
    Serial.print(" ");
    bt.write(data[i]);
  }
  Serial.println();
  bt.write(END);
}

void click(){

  if(debounceTimer.getMillis() > 100){

    debounceTimer.reset();

    bt.write(BUTTON);
    bt.write(END);
  }
}

int posR = 0, posG = 8, posB = 12;

void standby(){

  int dt = timer.getMillis();

  if(dt > 60){
    timer.reset();
  
    ring.add(posR, 150, 0, 0);
//    ring.add(posG, 0, 255, 0);
    ring.add(posB, 0, 100, 0);
  
    posR--;
    if(posR < 0)
      posR = NUM_LEDS;

    posG++;
    if(posG >= NUM_LEDS)
      posG = 0;

    posB++;
    if(posB >= NUM_LEDS)
      posB = 0;
  
    ring.update(dt);
    sync();
    strip.show();
  }
}

void sync(){
  for(int i=0; i<NUM_LEDS; i++){
    LED led = ring.getLED(i);
    setLED(i, led.getR(), led.getG(), led.getB());
  }
}

double fade(int t){
  return 6*t*t*t*t*t - 15*t*t*t*t + 10*t*t*t;
}

