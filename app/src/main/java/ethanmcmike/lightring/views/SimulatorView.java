package ethanmcmike.lightring.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import ethanmcmike.lightring.models.LED;
import ethanmcmike.lightring.models.RingModel;

public class SimulatorView extends View {

    private final int RADIUS = 300;
    private final int LED_SIZE = 45;
    private final int BUTTON_RADIUS = 100;
    private final int BUTTON_WIDTH = 5;

    RingModel model;

    Paint paint;

    public SimulatorView(Context context, RingModel model) {
        super(context);
        this.model = model;
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //Background
        canvas.drawColor(Color.DKGRAY);

        canvas.save();
        canvas.translate(canvas.getWidth()/2f, canvas.getHeight()/2f);

        //Part
        paint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, RADIUS + 30, paint);

        //Button
        paint.setColor(Color.DKGRAY);
        canvas.drawCircle(0, 0, BUTTON_RADIUS, paint);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(0, 0, BUTTON_RADIUS - BUTTON_WIDTH, paint);

        for(int i=0; i<model.size; i++){
            LED led = model.leds[i];
//            paint.setColor(Color.rgb(led.r, led.g, led.b));
            paint.setColor(Color.YELLOW);
            canvas.drawRect(-LED_SIZE/2f, -RADIUS, LED_SIZE/2f, -RADIUS + LED_SIZE, paint);
            canvas.rotate(360 / model.size);
        }

        canvas.restore();

        invalidate();
    }
}
