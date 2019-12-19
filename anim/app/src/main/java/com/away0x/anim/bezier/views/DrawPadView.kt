package com.away0x.anim.bezier.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

// 两点连线的圆滑处理
class DrawPadView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mX = 0f
    private var mY = 0f

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mPath.reset()
                mX = event.x
                mY = event.y
                mPath.moveTo(mX, mY)
            }

            MotionEvent.ACTION_MOVE -> {
                val x1 = event.x
                val y1 = event.y
                val cx = (x1 + mX) / 2
                val cy = (y1 + mY) / 2
                mPath.quadTo(mX, mY, cx, cy)
                mX = x1
                mY = y1
            }
        }

        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(mPath, mPaint)
    }

}