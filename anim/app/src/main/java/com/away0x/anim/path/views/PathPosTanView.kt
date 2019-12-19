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
import kotlin.math.PI
import kotlin.math.atan2

class PathPosTanView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnClickListener {

    private var mPath = Path()
    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPathMeasure = PathMeasure()
    private var mAnimator = ValueAnimator.ofFloat(0f, 1f)

    private var mPos = floatArrayOf(0f, 0f)
    private var mTan = floatArrayOf(0f, 0f)

    private var mCurrentValue = 0f

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 5f

        mPath.addCircle(0f, 0f, 200f, Path.Direction.CW)

        mPathMeasure.setPath(mPath, false)

        setOnClickListener(this)

        mAnimator.duration = 3000
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.repeatCount = ValueAnimator.INFINITE
        mAnimator.addUpdateListener {
            mCurrentValue = it.animatedValue as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        mPathMeasure.getPosTan(mCurrentValue * mPathMeasure.length, mPos, mTan)

        val degree = atan2(mTan[1], mTan[0]) * 180 / PI

        canvas.run {
            save()
            translate(400f, 400f)
            drawPath(mPath, mPaint)
            drawCircle(mPos[0], mPos[1], 10f, mPaint)
            rotate(degree.toFloat())
            drawLine(0f, -200f, 300f, -200f, mPaint)
            restore()
        }
    }

    override fun onClick(view: View?) {
        mAnimator.start()
    }

}