package com.away0x.anim.bezier.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

// 路径变换动画
class PathMorphingBezierView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnClickListener {

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

    private var mValueAnimator: ValueAnimator? = null

    init {
        mPaintBezier.strokeWidth = 8f
        mPaintBezier.style = Paint.Style.STROKE

        mPaintFlag.strokeWidth = 3f
        mPaintFlag.style = Paint.Style.STROKE

        mPaintFlagText.textSize = 20f
        mPaintFlagText.style = Paint.Style.STROKE

        setOnClickListener(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mStartPointX = (w / 4).toFloat()
        mStartPointY = (h / 2 - 200).toFloat()

        mEndPointX = (w * 3 / 4).toFloat()
        mEndPointY = (h / 2 - 200).toFloat()

        mFlagPointOneX = mStartPointX
        mFlagPointOneY = mStartPointY
        mFlagPointTwoX = mEndPointX
        mFlagPointTwoY = mEndPointY

        mValueAnimator = ValueAnimator.ofFloat(mStartPointY, h.toFloat())
        mValueAnimator?.duration = 3000
        mValueAnimator?.addUpdateListener {
            mFlagPointOneX = it.animatedValue as Float
            mFlagPointTwoY = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPath.run {
            reset()
            moveTo(mStartPointX, mStartPointY)
            cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY);
        }

        canvas?.run {
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

    override fun onClick(v: View?) {
        mValueAnimator?.start()
    }

}