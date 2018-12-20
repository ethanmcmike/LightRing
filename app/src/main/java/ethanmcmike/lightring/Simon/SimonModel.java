package ethanmcmike.lightring.Simon;

import java.util.ArrayList;

public class SimonModel {

    int size;
    ArrayList<Integer> sequence;

    boolean playing;
    int index;

    public SimonModel(int size){
        this.size = size;
        sequence = new ArrayList();
        levelUp();
    }

    public void levelUp(){
        int next = (int)(Math.random() * (size+1));
        sequence.add(next);
        index = 0;
    }

    public void play(int pos){
        if(check(pos, index)){
            index++;
        }
    }

    public int get(int pos){
        return sequence.get(pos);
    }

    public boolean check(int guess, int pos){
        return sequence.get(pos) == guess;
    }

//    private void beginGame(){
//        for(int i=0; i<4; i++){
//            spaces[i] = false;
//        }
//        playing = false;
//        score = 0;
//        pos = 0;
//
//        spaces[0] = true;
//        spaces[1] = true;
//        spaces[2] = false;
//        spaces[3] = false;
//
//        drawSpaces();
//
////        levelUp();
//    }
//
//    private void showSeq(){
//
//        playing = false;
//
//        pos = 0;
//        lastTime = System.currentTimeMillis();
//
//        while(pos < score){
//
//            if(System.currentTimeMillis() - lastTime > 750){
//                setSpace(seq[pos]);
//                drawSpaces();
//                pos++;
//            }
//        }
//
//        pos = 0;
//
//        playing = true;
//    }
//
//    private void setSpace(int space){
//        for(int i=0; i<4; i++){
//            if(i == space)
//                spaces[i] = true;
//            else
//                spaces[i] = false;
//        }
//    }
//
//    private void drawSpaces(){
//        for(int i=0; i<6; i++){
//            if(spaces[0])
//                setLED(i, 252, 0, 0);
//            else
//                setLED(i, 0, 0, 0);
//        }
//        for(int i=6; i<12; i++){
//            if(spaces[1])
//                setLED(i, 0, 255, 0);
//            else
//                setLED(i, 0, 0, 0);
//        }
//        for(int i=12; i<18; i++){
//            if(spaces[2])
//                setLED(i, 0, 0, 252);
//            else
//                setLED(i, 0, 0, 0);
//        }
//        for(int i=18; i<24; i++){
//            if(spaces[3])
//                setLED(i, 252, 0, 252);
//            else
//                setLED(i, 0, 0, 0);
//        }
//    }
//
//    private void levelUp(){
//        seq[score] = (int)(Math.random() * 5);
//        showSeq();
//    }
//
//    private void play(int space){
//
//        spaces[space] = !spaces[space];
//        drawSpaces();
//
////        if(!playing)
////            return;
////
////        else if(space == seq[pos]){
////            playTone(3000, 100);
////            score++;
////            levelUp();
////        }
////        else{                       //GAME OVER
////            playTone(2000, 100);
////            pos = 0;
////        }
//    }
}
