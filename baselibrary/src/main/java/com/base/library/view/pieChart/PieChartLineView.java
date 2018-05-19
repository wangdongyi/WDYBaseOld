package com.base.library.view.pieChart;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.base.library.util.CodeUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 作者：王东一
 * 创建时间：2017/10/25.
 */

public class PieChartLineView extends View {
    private Paint titlePaint;// 绘制文本的画笔
    private Paint paint;// 矩形画笔 柱状图的样式信息
    private ArrayList<PieChartItem> arrayList;
    private float width;
    private float height;
    private String textName = "";
    private int ChartWidth;
    private float radius;//半径
    private float padding;
    private float paddingX;
    private Bitmap bitMan, bitWoman;
    private float animatedValue;

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

    public PieChartLineView(Context context) {
        super(context);
        init();
    }

    public PieChartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        init();
    }

    public PieChartLineView(Context context, AttributeSet attrs, int defStyle) {
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
        titlePaint.setTextSize(sp2px(13));
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
        padding = CodeUtil.dip2px(getContext(), 20);
        radius = height / 2 - padding;
        paddingX = width / 2 - radius;
        paint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        @SuppressLint("DrawAllocation")
        RectF rect = new RectF(paddingX, padding, width - paddingX, height - padding);
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                changeColor(arrayList.get(i).getColor());
                canvas.drawArc(rect, //弧线所使用的矩形区域大小
                        arrayList.get(i).getStarAngle() - 1,  //开始角度
                        (animatedValue + 1)<=arrayList.get(i).getPercentage()?(animatedValue + 1):arrayList.get(i).getPercentage(), //扫过的角度
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
        drawLineLeft(canvas);
        drawLineRight(canvas);
        PieBitmapFactory(canvas);
        BitmapFactoryWoman(canvas);
    }

    private void drawLineLeft(Canvas canvas) {
        if (arrayList.size() > 0) {
            PieChartItem leftItem = arrayList.get(0);
            titlePaint.setColor(Color.parseColor(leftItem.getColor()));
            int start = (int) (leftItem.getStarAngle() + ((animatedValue<=leftItem.getPercentage())?animatedValue:leftItem.getPercentage()));
            float startX;
            float startY;
            float padding = CodeUtil.dip2px(getContext(), 25);
            float line = CodeUtil.dip2px(getContext(), 90);
            String text = "女" + new DecimalFormat("#0").format(leftItem.getPercentage() / 360f * 100) + "%";
//            String text = "女" + leftItem.getPercentage()  + "%";
            if (leftItem.getPercentage() >= 145) {
                //画弧上的线
                startX = (width / 2 - radius * Sin(67));
                startY = (height / 2 - radius * Cos(67));
                canvas.drawLine(startX, startY, startX - padding, startY - padding, titlePaint);
                canvas.drawLine(startX - padding, startY - padding, startX - line, startY - padding, titlePaint);
                canvas.drawText(text, startX - padding * 2.5f, startY - padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            } else if (leftItem.getPercentage() >= 5) {
                startX = (width / 2 - radius * Sin(start - 95) + CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(start - 95) + CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX - padding, startY - padding, titlePaint);
                canvas.drawLine(startX - padding, startY - padding, startX - line, startY - padding, titlePaint);
                canvas.drawText(text, startX - padding * 2.5f, startY - padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            } else {
                startX = (width / 2 - radius * Sin(start - 95) + CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(start - 95) + CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX - padding, startY - padding, titlePaint);
                canvas.drawLine(startX - padding, startY - padding, startX - line, startY - padding, titlePaint);
                canvas.drawText(text, startX - padding * 2.5f, startY - padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            }
        }

    }

    private void drawLineRight(Canvas canvas) {
        if (arrayList.size() > 1) {
            PieChartItem Item = arrayList.get(1);
            titlePaint.setColor(Color.parseColor(Item.getColor()));
            int start = (int) (Item.getStarAngle() + ((animatedValue<=Item.getPercentage())?animatedValue:Item.getPercentage()));
            float startX;
            float startY;
            float padding = CodeUtil.dip2px(getContext(), 25);
            float line = CodeUtil.dip2px(getContext(), 90);
            String text = "男" + new DecimalFormat("#0").format(Item.getPercentage() / 360f * 100) + "%";
            if (Item.getPercentage() >= 250) {
                //画弧上的线
                startX = (width / 2 - radius * Sin(Item.getStarAngle() + 145) - CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(Item.getStarAngle() + 145) - CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX + padding, startY + padding, titlePaint);
                canvas.drawLine(startX + padding, startY + padding, startX + line, startY + padding, titlePaint);
                canvas.drawText(text, startX + padding * 2.5f, startY + padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            } else if (Item.getPercentage() >= 90) {
                startX = (width / 2 - radius * Sin(start - 120) - CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(start - 120) - CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX + padding, startY + padding, titlePaint);
                canvas.drawLine(startX + padding, startY + padding, startX + line, startY + padding, titlePaint);
                canvas.drawText(text, startX + padding * 2.5f, startY + padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            } else if (Item.getPercentage() >= 5) {
                startX = (width / 2 - radius * Sin(start - 95) - CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(start - 95) - CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX + padding, startY + padding, titlePaint);
                canvas.drawLine(startX + padding, startY + padding, startX + line, startY + padding, titlePaint);
                canvas.drawText(text, startX + padding * 2.5f, startY + padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            } else {
                startX = (width / 2 - radius * Sin(start - 90) - CodeUtil.dip2px(getContext(), 5));
                startY = (height / 2 + radius * Cos(start - 90) - CodeUtil.dip2px(getContext(), 5));
                canvas.drawLine(startX, startY, startX + padding, startY + padding, titlePaint);
                canvas.drawLine(startX + padding, startY + padding, startX + line, startY + padding, titlePaint);
                canvas.drawText(text, startX + padding * 2.5f, startY + padding - CodeUtil.dip2px(getContext(), 5), titlePaint);
            }
        }

    }

    public void PieBitmapFactory(Canvas canvas) {
        if (bitMan == null) {
            // 设置图片
            bitMan = BitmapFactory.decodeResource(getResources(), R.mipmap.male);
            // 绘制图片
        }
        float padding = CodeUtil.dip2px(getContext(), 5);
        canvas.drawBitmap(bitMan, width - (radius - radius * Cos(36)) - paddingX - padding * 1.5f, radius - radius * Sin(36), paint);
    }

    public void BitmapFactoryWoman(Canvas canvas) {
        if (bitWoman == null) {
            // 定义画笔
            bitWoman = BitmapFactory.decodeResource(getResources(), R.mipmap.female);
            // 绘制图片
        }
        float padding = CodeUtil.dip2px(getContext(), 6);
        canvas.drawBitmap(bitWoman, width / 2 - Sin(54) * radius - bitWoman.getWidth() + padding, height / 2 + radius * Cos(54) - bitWoman.getHeight() / 2 + padding, paint);
    }

    private float Sin(int round) {
        long sin = 0;
        sin = Math.round(Math.sin(round * Math.PI / 180) * 100);
        return sin / 100f;
    }

    private float Cos(int round) {
        long sin = 0;
        sin = Math.round(Math.cos(round * Math.PI / 180) * 100);
        return sin / 100f;
    }

    private void initAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    public void startDraw() {
        initAnimator();

    }

    //获取颜色
    private void changeColor(String color) {
        paint.setColor(Color.parseColor((TextUtils.isEmpty(color) ? "#ffffff" : color)));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
