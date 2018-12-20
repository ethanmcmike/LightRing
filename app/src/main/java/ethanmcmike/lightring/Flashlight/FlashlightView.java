package ethanmcmike.lightring.Flashlight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ethanmcmike.lightring.RingController;

public class FlashlightView extends View {

    RingController ring;
    FlashlightModel model;

    public FlashlightView(Context context) {
        super(context);
    }

    public FlashlightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlashlightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setModel(FlashlightModel model){
        this.model = model;
    }

    public void setRing(RingController ring){
        this.ring = ring;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(model.on)
            canvas.drawColor(Color.WHITE);
        else
            canvas.drawColor(Color.BLACK);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                model.on = !model.on;

                invalidate();

                if(model.on) {
                    ring.setLED(250, 250, 250);
                    ring.render();
                }
                else {
                    ring.clear();
                }

                break;
        }

        return true;
    }
}
