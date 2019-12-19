package com.away0x.anim.bezier.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// 三阶贝塞尔曲线
class ThirdBezierView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mStartPointX = 0f
    private var mStartPointY = 0f

    private var mEndPointX = 0f
    private var mEndPointY = 0f

    private var mFlagPointOneX = 0f
    private var mFlagPointOneY = 0f
    private var mFlagPointTwoX = 0f
    private var mFlagPointTwoY = 0f

    private var mPath = Path()

    private var mPaintBezier = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlag = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlagText = Paint(Paint.ANTI_ALIAS_FLAG)

    private var isSecondPoint = false

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

        mFlagPointOneX = (w / 2 - 100).toFloat()
        mFlagPointOneY = (h / 2 - 300).toFloat()
        mFlagPointTwoX = (w / 2 + 100).toFloat()
        mFlagPointTwoY = (h / 2 - 300).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        mPath.reset()
        mPath.moveTo(mStartPointX, mStartPointY)
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY)

        canvas.run {
            drawPoint(mStartPointX, mStartPointY, mPaintFlag)
            drawText("起点", mStartPointX, mStartPointY, mPaintFlagText)
            drawPoint(mEndPointX, mEndPointY, mPaintFlag)
            drawText("终点", mEndPointX, mEndPointY, mPaintFlagText)
            drawPoint(mFlagPointOneX, mFlagPointOneY, mPaintFlag)
            drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mPaintFlagText)
            drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintFlagText)
            drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintFlag)
            drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag)
            drawLine(mEndPointX, mEndPointY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag)

            drawPath(mPath, mPaintBezier)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                isSecondPoint = true
            }

            MotionEvent.ACTION_POINTER_UP -> {
                isSecondPoint = false
            }

            MotionEvent.ACTION_MOVE -> {
                mFlagPointOneX = event.getX(0)
                mFlagPointOneY = event.getY(0)
                if (isSecondPoint) {
                    mFlagPointTwoX = event.getX(1)
                    mFlagPointTwoY = event.getY(1)
                }

                invalidate()
            }
        }

        return true
    }
}