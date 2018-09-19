package com.droid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.droid.model.PieChart;

import java.util.ArrayList;
import java.util.List;

public class PieChartView extends View {

    static final String title = "消费分析";
    int x, y; // 屏幕宽高
    float sum = 0; // 饼图扇形值总和

    List<PieChart> pies = new ArrayList<>();
    {
        pies.add(new PieChart("通讯", 50, Color.RED));
        pies.add(new PieChart("饮食", 30, Color.YELLOW));
        pies.add(new PieChart("交通出行", 10, Color.GREEN));
        pies.add(new PieChart("生活日用", 5, Color.BLUE));
        pies.add(new PieChart("理财", 15, Color.CYAN));
        pies.add(new PieChart("人情往来", 35, Color.MAGENTA));
        pies.add(new PieChart("其他", 40, Color.LTGRAY));

        // 计算饼图所有扇形值的总和
        for (PieChart pieChart: pies) {
            sum += pieChart.getNum();
        }
    }

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 绘制调度方法
     *
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * 主体绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPieChartView(canvas);
    }

    /**
     * 绘制子view
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * 绘制前景色
     *
     * 注意：背景色不能重写，只能在layout文件中设置
     * android:background="@android:color/darker_gray"
     *
     * @param canvas
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    private void drawPieChartView(Canvas canvas) {
        if (null == canvas) {
            return;
        }

        x = getWidth(); // 获取屏幕宽度
        y = getHeight(); // 获取屏幕高度

        canvas.drawColor(Color.WHITE);

        drawTitle(canvas);

        drawArcs(canvas);

        // 开启下面代码，绘制圆环图
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        canvas.drawCircle(x/2, y/2, 200, paint);
    }

    private void drawTitle(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(66);
        canvas.drawText(title, x/2, y-100, paint);
    }

    private void drawArcs(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);

        int radius = 350; //半径
        int lineLength1 = 50;   //线1
        int lineLength2 = 80;   //线2——水平线
        int textOffset = 20;

        //选中的扇形的偏移
        int selectedOffset = 10;

        float startAngle = -180;
        float halfAngle = 0;

        boolean isSelected;

        for (int i = 0; i < pies.size(); i++) {
            PieChart model = pies.get(i);

            if (i == 5) {
                isSelected = true;
            } else
                isSelected = false;

            int offset = isSelected ? selectedOffset : 0;

            // 根据所占比例，计算出每个扇形圆弧的角度
            float scale = model.getNum() / sum;
            float angle = scale * 360;

            // 计算每个扇形圆弧中心的角度
            halfAngle = startAngle + angle / 2;

            //圆弧中心的位置
            float half_x = (float) (x / 2 + (radius + offset) * Math.cos(halfAngle * Math.PI / 180));
            float half_y = (float) (y / 2 + (radius + offset) * Math.sin(halfAngle * Math.PI / 180));

            //圆弧引出的线1的结束点
            float line_1_end_x = (float) (x / 2 + (radius + lineLength1 + offset) * Math.cos(halfAngle * Math.PI /
                    180));
            float line_1_end_y = (float) (y / 2 + (radius + lineLength1 + offset) * Math.sin(halfAngle * Math.PI /
                    180));

            //圆弧引出的线2的结束点
            float line_2_end_x = Math.abs(halfAngle) <= 90 ? line_1_end_x + lineLength2 : line_1_end_x - lineLength2;
            float line_2_end_y = line_1_end_y;

            //文字中心点
            float text_x = Math.abs(halfAngle) <= 90 ? line_2_end_x + textOffset : line_2_end_x - textOffset;
            float text_y = line_1_end_y;

            //椭圆四边的位置
            float rectLeft = (float) (x / 2 - radius + offset * Math.cos(halfAngle * Math.PI / 180));
            float rectRight = (float) (x / 2 + radius + offset * Math.cos(halfAngle * Math.PI / 180));
            float rectTop = (float) (y / 2 - radius + offset * Math.sin(halfAngle * Math.PI / 180));
            float rectBottom = (float) (y / 2 + radius + offset * Math.sin(halfAngle * Math.PI / 180));

            paint.setColor(model.getColor());
            paint.setStyle(Paint.Style.FILL);
            canvas.drawArc(rectLeft, rectTop, rectRight, rectBottom, startAngle, angle-2, true, paint); // 绘制扇形

            //绘制折线
            Path path = new Path();
            path.moveTo(half_x, half_y);
            path.lineTo(line_1_end_x, line_1_end_y);
            path.lineTo(line_2_end_x, line_2_end_y);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);

            // 绘制文字
            if (Math.abs(halfAngle) <= 90) {
                paint.setTextAlign(Paint.Align.LEFT);
            } else {
                paint.setTextAlign(Paint.Align.RIGHT);
            }

            paint.setTextSize(36);
            canvas.drawText(model.getTip(), text_x, text_y, paint);

            startAngle += angle;
        }
    }
}
