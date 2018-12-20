package ethanmcmike.lightring.Simon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ethanmcmike.lightring.Renderer;

public class SimonRenderer extends Renderer {

    public SimonRenderer(){
        super();
    }

    public void render(Canvas canvas){
        setCanvas(canvas);

        float centerX = width / 2f;
        float centerY = height / 2f;

        setPaint(Color.RED, 0, Paint.Style.FILL);
        canvas.drawRect(0, 0, centerX, centerY, paint);
        setPaint(Color.GREEN, 0, Paint.Style.FILL);
        canvas.drawRect(centerX, 0, width, centerY, paint);
        setPaint(Color.BLUE, 0, Paint.Style.FILL);
        canvas.drawRect(centerX, centerY, width, height, paint);
        setPaint(Color.YELLOW, 0, Paint.Style.FILL);
        canvas.drawRect(0, centerY, centerX, height, paint);
    }
}
