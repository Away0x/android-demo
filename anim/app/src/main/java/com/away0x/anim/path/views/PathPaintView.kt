package com.away0x.anim.path.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class PathPaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mEffect: PathEffect? = null
    private var mPathMeasure = PathMeasure()

    private var mLength = 0f
    private var mAnimValue = 0f

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f

        mPath.run {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(400f, 300f)
            close()
        }

        mPathMeasure.setPath(mPath, true)
        mLength = mPathMeasure.length

        val animator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 2000
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
        }
        animator.addUpdateListener {
            mAnimValue = it.animatedValue as Float
            mEffect = DashPathEffect(floatArrayOf(mLength, mLength), mLength * mAnimValue)
            mPaint.pathEffect = mEffect
            invalidate()
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawPath(mPath, mPaint)
    }

}