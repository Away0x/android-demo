package com.away0x.anim.bezier.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// 二阶贝塞尔曲线
class SecondBezierView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mStartPointX = 0f
    private var mStartPointY = 0f

    private var mEndPointX = 0f
    private var mEndPointY = 0f

    private var mFlagPointX = 0f
    private var mFlagPointY = 0f

    private var mPath = Path()

    private var mPaintBezier = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlag = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlagText = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaintBezier.strokeWidth = 8f
        mPaintBezier.style = Paint.Style.STROKE

        mPaintFlag.strokeWidth = 3f
        mPaintFlag.style = Paint.Style.STROKE

        mPaintFlagText.style = Paint.Style.STROKE
        mPaintFlagText.textSize = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mStartPointX = (w / 4).toFloat()
        mStartPointY = (h / 2 - 200).toFloat()

        mEndPointX = (w * 3 / 4).toFloat()
        mEndPointY = (h / 2 - 200).toFloat()

        mFlagPointX = (w / 2).toFloat()
        mFlagPointY = (h / 2 - 300).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        mPath.reset()
        mPath.moveTo(mStartPointX, mStartPointY)
        mPath.quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY)

        canvas.run {
            drawPoint(mStartPointX, mStartPointY, mPaintFlag)
            drawText("起点", mStartPointX, mStartPointY, mPaintFlagText)
            drawPoint(mEndPointX, mEndPointY, mPaintFlag)
            drawText("终点", mEndPointX, mEndPointY, mPaintFlagText)
            drawPoint(mFlagPointX, mFlagPointY, mPaintFlag)
            drawText("控制点", mFlagPointX, mFlagPointY, mPaintFlagText)
            drawLine(mStartPointX, mStartPointY, mFlagPointX, mFlagPointY, mPaintFlag)
            drawLine(mEndPointX, mEndPointY, mFlagPointX, mFlagPointY, mPaintFlag)

            drawPath(mPath, mPaintBezier)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_MOVE -> {
                mFlagPointX = event.x
                mFlagPointY = event.y
                invalidate()
            }
        }

        return true
    }

}