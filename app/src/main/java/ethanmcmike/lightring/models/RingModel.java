package ethanmcmike.lightring.models;

public class RingModel {

    public LED[] leds;
    public int size;

    public RingModel(int size){
        this.size = size;

        leds = new LED[size];
        for(int i=0; i<size; i++){
            leds[i] = new LED();
        }
    }

    public void setLED(int i, int r, int g, int b){
        leds[i].r = r;
        leds[i].g = g;
        leds[i].b = b;
    }

    public void setLED(int r, int g, int b){
        for(int i=0; i<leds.length; i++){
            setLED(i, r, g, b);
        }
    }
}
