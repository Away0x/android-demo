package com.away0x.anim.path.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.abs

class PathTracingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mDst = Path()
    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mLength = 0f
    private var mAnimValue = 0f

    private var mPathMeasure = PathMeasure()

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f

        mPath.addCircle(400f, 400f, 100f, Path.Direction.CW)

        mPathMeasure.setPath(mPath, true)

        mLength = mPathMeasure.length

        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener {
            mAnimValue = it.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mDst.reset()
        mDst.lineTo(0f, 0f)

        val stop = mLength * mAnimValue
        val start = (stop - ((0.5 - abs(mAnimValue - 0.5)) * mLength))

        mPathMeasure.getSegment(start.toFloat(), stop, mDst, true)
        canvas?.drawPath(mDst, mPaint)

    }

}