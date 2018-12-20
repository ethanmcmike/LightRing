package ethanmcmike.lightring.Simon;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ethanmcmike.lightring.RingController;

public class SimonView extends View {

    RingController ring;
    SimonRenderer renderer;
    SimonModel model;

    public SimonView(Context context) {
        super(context);
        init();
    }

    public SimonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        renderer = new SimonRenderer();
    }

    public void setModel(SimonModel model){
        this.model = model;
    }

    public void setRing(RingController ring){
        this.ring = ring;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int w = getWidth();
        int h = getHeight();

        float x = event.getX();
        float y = event.getY();

        int pos;

        if(x < w/2f){
            if(y < h/2f){

                pos = 0;

            } else {
                pos = 4;
            }
        } else {
            if(y < h/2f){

                pos = 1;

            } else {
                pos = 2;
            }
        }

        if(pos == 0) {
            ring.setLED(0, 6, 250, 0, 0);
            ring.render();
        }
        else {
            ring.clear();
        }

//        model.check(pos);

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderer.render(canvas);
    }
}
