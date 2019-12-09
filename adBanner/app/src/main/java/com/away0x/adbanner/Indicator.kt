package com.away0x.adbanner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


class Indicator(context: Context, attr: AttributeSet) : View(context, attr) {

    // 前景色的画笔
    private var mForePaint = Paint()
    // 背景色的画笔
    private var mBgPaint = Paint()
    // Indicator 数量
    private var mNumber = 4
    // Indicator 半径
    private val mRadius = 10f
    // 移动的偏移量
    private var mOffset = 0f
    // Indicator 的背景色画笔颜色
    private val mBgColor = Color.RED
    // Indicator 的前景色画笔颜色
    private val mForeColor = Color.BLUE

    init {
        mForePaint.isAntiAlias = true
        mForePaint.style = Paint.Style.FILL
        mForePaint.color = mForeColor
        mForePaint.strokeWidth = 2f

        mBgPaint.isAntiAlias = true
        mBgPaint.style = Paint.Style.STROKE
        mBgPaint.color = mBgColor
        mBgPaint.strokeWidth = 2f

        val typedArray = context.obtainStyledAttributes(attr, R.styleable.Indicator)
        mNumber = typedArray.getInteger(R.styleable.Indicator_setNumber, mNumber)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (i in 0..mNumber) {
            canvas?.drawCircle((60 + i * mRadius * 3).toFloat(), 60f, mRadius, mBgPaint)
        }
        canvas?.drawCircle(60 + mOffset, 60f, mRadius, mForePaint)
    }

    fun setOffset(position: Int, positionOffset: Float) {
        val newPos = position % mNumber
        mOffset = newPos * 3 * mRadius + positionOffset * 3 * mRadius

        // 重绘
        invalidate()
    }

}
