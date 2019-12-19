package com.away0x.anim.bezier.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.round

// 波浪效果
class WaveBezierView @JvmOverloads constructor(
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

    private var mWaveLength = 0
    private var mScreenHeight = 0
    private var mScreenWidth = 0
    private var mCenterY = 0
    private var mWaveCount = 0
    private var mOffset = 0

    private var mPath = Path()
    private var mPaintBezier = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlag = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintFlagText = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mValueAnimator: ValueAnimator? = null

    init {
        mPaintBezier.color = Color.LTGRAY
        mPaintBezier.strokeWidth = 8f
        mPaintBezier.style = Paint.Style.FILL_AND_STROKE

        mWaveLength = 800

        setOnClickListener(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mScreenHeight = h
        mScreenWidth = w
        mCenterY = h / 2

        mWaveCount = round(mScreenWidth / mWaveLength + 1.5).toInt()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPath.run {
            reset()
            moveTo((-mWaveLength + mOffset).toFloat(), mCenterY.toFloat())

            for (i in 0..mWaveCount) {
                quadTo((-mWaveLength * 3 / 4 + i * mWaveLength + mOffset).toFloat(), (mCenterY + 60).toFloat(), (-mWaveLength / 2 + i * mWaveLength + mOffset).toFloat(), mCenterY.toFloat())
                quadTo((-mWaveLength / 4 + i * mWaveLength + mOffset).toFloat(), (mCenterY - 60).toFloat(), (i * mWaveLength + mOffset).toFloat(), mCenterY.toFloat())
            }

            lineTo(mScreenWidth.toFloat(), mScreenHeight.toFloat())
            lineTo(0f, mScreenHeight.toFloat())
            close()
        }

        canvas?.drawPath(mPath, mPaintBezier)
    }

    override fun onClick(p0: View?) {
        mValueAnimator = ValueAnimator.ofInt(0, mWaveLength)
        mValueAnimator?.duration = 1000
        mValueAnimator?.repeatCount = ValueAnimator.INFINITE
        mValueAnimator?.interpolator = LinearInterpolator()
        mValueAnimator?.addUpdateListener {
            mOffset = it.animatedValue as Int
            invalidate()
        }
        mValueAnimator?.start()
    }

}