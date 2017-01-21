package com.rideread.rideread.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;



/**
 * Created by Jackbing on 2017/1/21.
 * 自定义的输入框
 */

public class LineEditText extends EditText {

    private Paint paint;
    private String defaultColor="#808080";

    public LineEditText(Context context) {
        this(context,null);

    }

    public LineEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public LineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
       super(context, attrs, defStyleAttr);
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor(defaultColor));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w=getWidth();
        int h=getHeight();
        int padB=getPaddingBottom();
        int padL=getPaddingLeft();
        int padR=getPaddingRight();
        float size=getTextSize();

        int lines=(int)(h/size);
        for (int i=0;i<lines;i++){
            canvas.drawLine(padL,getHeight()-padB-size*i,getWidth()-padR,getHeight()-padB-size*i,paint);
        }
    }
}
