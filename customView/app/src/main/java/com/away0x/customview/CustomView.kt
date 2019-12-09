package com.away0x.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class CustomView(context: Context, attr: AttributeSet) : View(context, attr) {

    private lateinit var mPaint: Paint

    init {
        initPaint()
    }

    private fun initPaint(): Paint {
        // 画笔
        mPaint = Paint()
        // 设置抗锯齿
        mPaint.isAntiAlias = true
        // 设置画笔的宽度
        mPaint.strokeWidth = 5f
        // 设置画笔的颜色
        mPaint.color = Color.BLUE
        // 设置画笔的样式
        mPaint.style = Paint.Style.STROKE
        // 设置文本字体的大小
        mPaint.textSize = 50f

        return mPaint
    }

    // 测量 View 控件
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    // 绘制控件
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) { return; }

        //画布
        //绘制一个圆
        canvas.drawCircle(60f, 60f, 60f, mPaint)
        //绘制文本
        canvas.drawText("自定义 view", 100f, 100f, mPaint)
        //绘制直线
        canvas.drawLine(100f, 100f, 150f, 150f, mPaint)
        //绘制路径
        val path = Path().apply {
            //移动到一个点
            moveTo(60f, 60f)
            //绘制直线
            lineTo(60f, 60f)
            lineTo(120f, 0f)
            lineTo(180f, 120f)
            moveTo(120f, 0f)
            moveTo(180f, 120f)
            //图形闭合
            close()
        }

        canvas.drawPath(path, mPaint)
        //绘制点
        //绘制椭圆,扇形,环形
        //绘制Bitmap
    }

}