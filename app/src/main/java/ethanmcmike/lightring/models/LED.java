package ethanmcmike.lightring.models;

public class LED {

    public int r, g, b;

    public LED(){
        r = 0;
        g = 0;
        b = 0;
    }

    public LED(int r, int g, int b){
        this.r = r;
        this.b = b;
        this.g = g;
    }

    public boolean equals(LED led){
        return r==led.r && b==led.b && g==led.g;
    }
}
