package ethanmcmike.lightring;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Renderer {

    protected Canvas canvas;
    protected Paint paint;

    protected int width, height;

    public Renderer(){
        paint = new Paint();
    }

    public void setPaint(int color, int width, Paint.Style style){
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStyle(style);
    }

    public void clear(){
        canvas.drawColor(Color.BLACK);
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
        width = canvas.getWidth();
        height = canvas.getHeight();
    }

    public abstract void render(Canvas canvas);
}
