package com.grimmer.simpleChart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * 柱状图
 * Created by keweiquan on 15/12/13.
 */
public class HistogramView extends View {
    final int MARGIN = 50;
    private String xShaftUnit;
    private String yShaftUnit;
    private int shaftPaintColor;
    private int histogramPaintColor;
    private int xShaftMinValue;
    private int xShaftMaxValue;
    private int xShaftInterval;
    private int yShaftMinValue;
    private int yShaftMaxValue;
    private int yShaftInterval;

    private Paint mShaftPaint; // x／y轴画笔
    private Paint mHistogramPaint; // 柱状画笔

    private List<ColumnData> mColumnDataList;

    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ty = context.obtainStyledAttributes(attrs, R.styleable.histogram_view);
        xShaftUnit = ty.getString(R.styleable.histogram_view_x_shaft_unit);
        xShaftUnit = xShaftUnit == null ? "" : xShaftUnit;
        yShaftUnit = ty.getString(R.styleable.histogram_view_y_shaft_unit);
        yShaftUnit = yShaftUnit == null ? "" : yShaftUnit;
        shaftPaintColor = ty.getColor(R.styleable.histogram_view_shaft_color, Color.parseColor("#90000000"));
        histogramPaintColor = ty.getColor(R.styleable.histogram_view_histogram_color, Color.parseColor("#FF4081"));
        xShaftMinValue = ty.getInteger(R.styleable.histogram_view_x_shaft_min_value, 0);
        xShaftMaxValue = ty.getInteger(R.styleable.histogram_view_x_shaft_max_value, 10);
        xShaftInterval = ty.getInteger(R.styleable.histogram_view_x_shaft_interval, 1);
        yShaftMinValue = ty.getInteger(R.styleable.histogram_view_y_shaft_min_value, 0);
        yShaftMaxValue = ty.getInteger(R.styleable.histogram_view_y_shaft_max_value, 30);
        yShaftInterval = ty.getInteger(R.styleable.histogram_view_y_shaft_interval, 1);
        ty.recycle();
        init();
    }

    private void init() {
        mShaftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShaftPaint.setColor(shaftPaintColor);
        mShaftPaint.setStyle(Paint.Style.FILL);
        mShaftPaint.setTextAlign(Paint.Align.CENTER);
        mShaftPaint.setTextSize(30);

        mHistogramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHistogramPaint.setColor(histogramPaintColor);
        mHistogramPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // x轴
        canvas.drawLine(
                MARGIN,
                getMeasuredHeight() - MARGIN,
                getMeasuredWidth() - MARGIN,
                getMeasuredHeight() - MARGIN,
                mShaftPaint
        );
        // y轴
        canvas.drawLine(
                MARGIN,
                MARGIN,
                MARGIN,
                getMeasuredHeight() - MARGIN,
                mShaftPaint
        );

        int yIntervalCount = ((yShaftMaxValue - yShaftMinValue + 1)) / yShaftInterval;
        int yInterval = (getMeasuredHeight() - 2 * MARGIN) / yIntervalCount;
        int[] yPositionArr = new int[yIntervalCount];

        int xIntervalCount = ((xShaftMaxValue - xShaftMinValue + 1)) / xShaftInterval + 1;
        int xInterval = (getMeasuredWidth() - 2 * MARGIN) / xIntervalCount;
        int[] xPositionArr = new int[xIntervalCount];


        for (int i = 0; i < xIntervalCount; i++) {
            xPositionArr[i] = xInterval * (i + 1);
        }
        for (int i = 0; i < xPositionArr.length; i++) {
            // x轴上的单位
            if (i != xPositionArr.length - 1)
                canvas.drawText(
                        (xShaftMinValue + i * xShaftInterval) + xShaftUnit,
                        MARGIN + xPositionArr[i],
                        getMeasuredHeight() - MARGIN / 3,
                        mShaftPaint
                );
        }

        for (int i = 0; i < yIntervalCount; i++) {
            yPositionArr[i] = yInterval * i;
        }
        for (int i = 0; i <= yPositionArr.length; i++) {
            // x轴平衡线
            if (i != yPositionArr.length - 1)
                canvas.drawLine(
                        MARGIN,
                        getMeasuredHeight() - MARGIN - yInterval * (i + 1),
                        getMeasuredWidth() - MARGIN,
                        getMeasuredHeight() - MARGIN - yInterval * (i + 1),
                        mShaftPaint
                );

            // y轴上的单位
            String text;
            if (i != yPositionArr.length) {
                text = "" + (yShaftMinValue + i * yShaftInterval);
            } else {
                text = yShaftUnit;
            }
            canvas.drawText(
                    text,
                    MARGIN / 2,
                    getMeasuredHeight() - MARGIN * 2 / 3 - yInterval * (i),
                    mShaftPaint
            );
        }

        // 画柱状
        if (mColumnDataList != null) {
            int left, top, right, bottom;
            for (ColumnData columnData : mColumnDataList) {
                left = columnData.getXColumn();
                left = left > xShaftMaxValue ? xShaftMaxValue : left;
                left = left < xShaftMinValue ? xShaftMinValue : left;
                left = left + 1 - xShaftMinValue;
                left = left * xInterval + MARGIN - xInterval / 3;

                top = columnData.getYColumn();
                top = top > yShaftMaxValue ? yShaftMaxValue : top;
                top = top < yShaftMinValue ? yShaftMinValue : top;
                top = yShaftMaxValue + 1 - top;
                top = top * yInterval + MARGIN;

                right = columnData.getXColumn();
                right = right > xShaftMaxValue ? xShaftMaxValue : right;
                right = right < xShaftMinValue ? xShaftMinValue : right;
                right = right + 1 - xShaftMinValue;
                right = right * xInterval + MARGIN + xInterval / 3;

                bottom = columnData.getYColumn();
                bottom = bottom > yShaftMaxValue ? yShaftMaxValue : bottom;
                bottom = bottom < yShaftMinValue ? yShaftMinValue : bottom;
                bottom = (yShaftMaxValue + 1 - bottom) * yInterval
                        + (bottom - yShaftMinValue) * yInterval
                        + MARGIN;

                canvas.drawRect(
                        left,
                        top,
                        right,
                        bottom,
                        mHistogramPaint
                );
            }
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * 测量宽
     *
     * @param widthMeasureSpec 宽的测量
     * @return 宽
     */
    private int measureWidth(int widthMeasureSpec) {
        int size;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // 精准模式
            size = specSize;
        } else {
            size = 200; // 给定最小值
            if (specMode == MeasureSpec.AT_MOST) {
                // wrap_content
                size = Math.min(size, specSize);
            }
        }
        return size;
    }

    /**
     * 测量高
     *
     * @param heightMeasureSpec 高的测量
     * @return 高
     */
    private int measureHeight(int heightMeasureSpec) {
        int size;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // 精准模式
            size = specSize;
        } else {
            size = 200; // 给定最小值
            if (specMode == MeasureSpec.AT_MOST) {
                // wrap_content
                size = Math.min(size, specSize);
            }
        }
        return size;
    }

    /**
     * 设置填充数据
     *
     * @param columnDataList 数据
     */
    public void setColumnData(List<ColumnData> columnDataList) {
        if (mColumnDataList != null)
            mColumnDataList.clear();
        mColumnDataList = columnDataList;
        invalidate();
    }

    /**
     * 数据
     */
    public static class ColumnData {
        private int xColumn;
        private int yColumn;

        public int getXColumn() {
            return xColumn;
        }

        public void setXColumn(int xColumn) {
            this.xColumn = xColumn;
        }

        public int getYColumn() {
            return yColumn;
        }

        public void setYColumn(int yColumn) {
            this.yColumn = yColumn;
        }

    }

}
