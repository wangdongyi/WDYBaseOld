package com.base.library.view.pieChart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.base.library.R;

import java.util.ArrayList;

/**
 * 作者：王东一 on 2016/6/4 08:25
 **/
public class PieChartView extends View {
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息
    private ArrayList<PieChartItem> arrayList;
    private float width;
    private float height;
    private String textName = "";
    private int ChartWidth;

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public ArrayList<PieChartItem> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PieChartItem> arrayList) {
        this.arrayList = arrayList;
    }

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        init();
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttributes(context, attrs);
        init();
    }

    private void init() {

        arrayList = new ArrayList<>();
        titlePaint = new Paint();
        paint = new Paint();
        // 给画笔设置颜色
        titlePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_black));
        titlePaint.setTextSize(sp2px(16));
        titlePaint.setAntiAlias(true);
        titlePaint.setStyle(Paint.Style.FILL);
        titlePaint.setTextAlign(Paint.Align.CENTER);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.PieChartSexView);
        if (attr == null) {
            return;
        }
        try {
            ChartWidth = (int) attr.getDimension(R.styleable.PieChartSexView_ChartWidth, (width - dp2px(10)) / 5);
        } finally {
            attr.recycle();
        }
    }

    private int dp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * value + 0.5f);
    }

    private int sp2px(int value) {
        float v = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (v * value + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        RectF rect = new RectF(0f, 0f, width, height);
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                changeColor(arrayList.get(i).getColor());
                canvas.drawArc(rect, //弧线所使用的矩形区域大小
                        arrayList.get(i).getStarAngle() - 1,  //开始角度
                        arrayList.get(i).getPercentage() + 1, //扫过的角度
                        true, //是否使用中心
                        paint);
            }
        } else {
            paint.setColor(Color.rgb(158, 158, 174));
            canvas.drawArc(rect, //弧线所使用的矩形区域大小
                    -90,  //开始角度
                    360, //扫过的角度
                    true, //是否使用中心
                    paint);
        }
        paint.setColor(Color.WHITE);
        canvas.drawCircle(width / 2, height / 2, ChartWidth, paint);// 大圆
        canvas.drawText(getTextName(), width / 2, height / 2 + dp2px(5), titlePaint);


    }

    private void initAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(10000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    //获取颜色
    private void changeColor(String color) {
        paint.setColor(Color.parseColor((TextUtils.isEmpty(color) ? "#ffffff" : color)));
    }
}
