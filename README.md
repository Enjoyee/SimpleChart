> SimpleChart 一个简单的图表控件，暂时只有柱状图，后面慢慢完善...

---

最近项目准备用到图表的一些东西，大概搜了一下[android的图表开源控件](http://www.jcodecraeer.com/plus/list.php?tid=31&codecategory=13500)，好像用不到那么多东西，而且用起来不习惯，打算自己写一个练习一下，一方面是练习一下自定义控件，一方面自己写的，用起来也习惯，改起来也方便。先看一下大概的样子先，上图：
<img src="http://ww4.sinaimg.cn/large/872c0b14gw1ez1kfmxii0j20u01hc0w0.jpg" width="270" height="480">


### 全部的属性值：
```xml
<declare-styleable name="histogram_view">
    <attr name="x_shaft_unit" format="reference|string" />
    <attr name="y_shaft_unit" format="reference|string" />
    <attr name="shaft_color" format="color" />
    <attr name="histogram_color" format="color" />
    <attr name="x_shaft_min_value" format="integer" />
    <attr name="x_shaft_max_value" format="integer" />
    <attr name="x_shaft_interval" format="integer" />
    <attr name="y_shaft_min_value" format="integer" />
    <attr name="y_shaft_max_value" format="integer" />
    <attr name="y_shaft_interval" format="integer" />
</declare-styleable>
```
### 属性值解析：
* x_shaft_unit：x轴的坐标单位（例如本例子中x轴单位就是：个）
* y_shaft_unit：y轴的坐标单位（例如本例子中y轴单位就是：时）
* shaft_color：坐标轴的颜色
* histogram_color：柱状图的颜色
* x_shaft_min_value：x轴最小值
* x_shaft_max_value：x轴最大值
* x_shaft_interval：x轴间隔单位（1--》2间隔就是1，1-－》3间隔就是2）
* y_shaft_min_value：y轴最小值
* y_shaft_max_value：y轴最大值
* y_shaft_interval：y轴间隔单位（1--》2间隔就是1，1-－》3间隔就是2）

### 用法：
#### xml中布局：
```xml
<com.grimmer.simpleChart.HistogramView xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/hvOrderStatistics"
    android:layout_width="match_parent"
    android:layout_height="300dp"
    android:layout_gravity="center"
    android:layout_marginEnd="10dp"
    android:layout_marginStart="10dp"
    android:layout_marginTop="50dp"
    app:histogram_color="@color/colorAccent"
    app:shaft_color="@color/darkGray"
    app:x_shaft_interval="1"
    app:x_shaft_max_value="18"
    app:x_shaft_min_value="9"
    app:x_shaft_unit="@string/text_order_statistics_x_unit"
    app:y_shaft_interval="1"
    app:y_shaft_max_value="5"
    app:y_shaft_min_value="0"
    app:y_shaft_unit="@string/text_order_statistics_y_unit" />
```

#### 代码中填充数据：
```java
// 初始化柱状图
HistogramView hvOrderStatistics = (HistogramView) findViewById(R.id.hvOrderStatistics);
// 设置数据
List<HistogramView.ColumnData> columnDataList = new ArrayList<>();
HistogramView.ColumnData columnData = new HistogramView.ColumnData();
columnData.setXColumn(9);
columnData.setYColumn(5);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(10);
columnData.setYColumn(3);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(11);
columnData.setYColumn(1);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(12);
columnData.setYColumn(4);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(13);
columnData.setYColumn(2);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(14);
columnData.setYColumn(3);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(15);
columnData.setYColumn(5);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(16);
columnData.setYColumn(1);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(17);
columnData.setYColumn(4);
columnDataList.add(columnData);

columnData = new HistogramView.ColumnData();
columnData.setXColumn(18);
columnData.setYColumn(3);
columnDataList.add(columnData);

hvOrderStatistics.setColumnData(columnDataList);
```

---

### 图表中填充用到的数据model：
```java
/**
 * 图表数据
 */
public static class ColumnData {
    private int xColumn; // x坐标
    private int yColumn; // y坐标

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
```

### 获取自定义属性值：
```java
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
```

### 设置画笔熟悉：
```java
mShaftPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
mShaftPaint.setColor(shaftPaintColor);
mShaftPaint.setStyle(Paint.Style.FILL);
mShaftPaint.setTextAlign(Paint.Align.CENTER);
mShaftPaint.setTextSize(30);

mHistogramPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
mHistogramPaint.setColor(histogramPaintColor);
mHistogramPaint.setStyle(Paint.Style.FILL);
```

### 设置view的测量宽高：
```java
@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(
            measureWidth(widthMeasureSpec),
            measureHeight(heightMeasureSpec));
}
```

### 测量宽和高：
```java
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
    
```

```java
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
```

### 开始画图：
```java
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
```

个人博客：http://enjoyee.github.io