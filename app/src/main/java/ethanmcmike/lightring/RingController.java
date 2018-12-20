package ethanmcmike.lightring;

import ethanmcmike.lightring.models.LED;
import ethanmcmike.lightring.models.RingModel;

public class RingController implements Runnable{

    private static final int MAX_DATA = 6;

    public static final int BUTTON = 251;           //<>
    private static final int START_LED = 252;       //<r,g,b>
    private static final int START_TONE = 253;
    private static final int END = 255;
    public static final int RENDER = 254;           //<x>   0-clear, 1-render

    int maxBrightness = 250;

    BTController bt = BTController.instance;
    int data[] = new int[MAX_DATA];
    int dataIndex = 0;
    boolean reading;

    boolean dirty[];

    Thread thread;
    boolean running;

    public RingModel model;

    int val = 0;
    boolean up = true;

    public RingController(int size){
        model = new RingModel(size);
        dirty = new boolean[model.size];
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    long lastTime;

    @Override
    public void run() {

        running = true;

        while(running){



//            long now = System.currentTimeMillis();
//            if(now - lastTime > 20) {
//
//                val++;
//                if(val>=24)
//                    val = 0;
//
////                System.out.println("RING: " + (now - lastTime));
//                lastTime = now;
//
////                for(int i=0; i<24; i++){
////                    setLED(i, 0, 0, 250 - 10*(val-i));
////                }
//
//                clear();
//                setLED(val, 0, 0, map(250, 0, 250, 0, maxBrightness));
//                render();
//            }

            int bytes = bt.read();      //Number of bytes read into BT buffer

            if(bytes > 0){

                for(int i=0; i<bytes; i++) {
                    int in = bt.byteToInt(bt.buffer[i]);
                    parse(in);
                    useData();
                }
            }


//
//            for(int i=0; i<dirty.length; i++){
////                if(dirty[i])
////                    setLED(i, ring.leds[i]);
//            }
        }
    }

    private void parse(int in){
        if(in == START_LED){
            reading = true;
            data[0] = in;
            dataIndex = 1;
        }
        else if(reading){
            if(in == END || dataIndex >= MAX_DATA){

                //Print received command
                System.out.print("RING: received ");
                for (int j = 0; j<data.length; j++) {
                    System.out.print(data[j] + ",");
                }
                System.out.println();

//                LED guess = new LED(data[2], data[3], data[4]);
//                LED correct = ring.leds[data[1]];
//                if(guess.equals(correct)){
//                    dirty[data[1]] = false;
//                }

                reading = false;
            }
            else{
                data[dataIndex] = in;
                dataIndex++;
            }
        }
    }

    private void useData(){
        switch(data[0]){
            case BUTTON:

        }
    }

    public void stop(){
        running = false;
    }

    public void setLED(int i, int r, int g, int b){
//        System.out.println("RING: sent " + i + "," + r + "," + g + "," + b);
        int msg[] = {START_LED, i, r, g, b, END};
        bt.send(msg);
//        model.setLED(i, r, g, b);
//        dirty[i] = true;
    }

    public void setLED(int i, LED led){
        setLED(i, led.r, led.g, led.b);
    }

    public void setLED(int r, int g, int b){
        setLED(model.size, r, g, b);
    }

    public void setLED(int off, int len, int r, int g, int b){
        int[] msg = {START_LED, off, len, r, g, b, END};
        bt.send(msg);

//        for(int j=off; j<off+len; j++){
//            model.setLED(j, r, g, b);
//        }
    }

    public void clear(){
        int[] msg = {RENDER, 0, END};
        bt.send(msg);
    }

    public void render(){
        int[] msg = {RENDER, 1, END};
        bt.send(msg);
    }

    public void setBrightness(int brightness){
        maxBrightness = brightness;
    }

    public void playTone(int freq, int per){
        bt.send(START_TONE);
        bt.send(freq / 10);
        bt.send(per / 10);
        bt.send(END);
    }

    private int map(int val, int a, int b, int c, int d){
        return (val-a)/(a-b)*(c-d)+c;
    }
}
