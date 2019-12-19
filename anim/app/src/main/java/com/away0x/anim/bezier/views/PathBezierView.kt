package com.away0x.anim.bezier.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.away0x.anim.bezier.utils.BezierEvaluator

// 轨迹动画
class PathBezierView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), View.OnClickListener {

    private var mStartPointX = 100f
    private var mStartPointY = 100f

    private var mEndPointX = 600f
    private var mEndPointY = 600f

    private var mFlagPointX = mStartPointX
    private var mFlagPointY = mStartPointY

    private var mMovePointX = 0f
    private var mMovePointY = 0f

    private var mPath = Path()
    private var mPaintPath = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mPaintCircle = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mPaintPath.style = Paint.Style.STROKE
        mPaintPath.strokeWidth = 8f

        setOnClickListener(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawCircle(mStartPointX, mStartPointY, 20f, mPaintCircle)
            drawCircle(mEndPointX, mEndPointY, 20f, mPaintCircle)
            drawCircle(mMovePointX, mMovePointY, 20f, mPaintCircle)
        }

        mPath.run {
            reset()
            moveTo(mStartPointX, mStartPointY)
            quadTo(mFlagPointX, mFlagPointY, mEndPointX, mEndPointY)
        }

        canvas?.drawPath(mPath, mPaintPath)
    }

    override fun onClick(view: View?) {
        val evaluator = BezierEvaluator(PointF(mFlagPointX, mFlagPointY))
        val animator = ValueAnimator.ofObject(evaluator,
            PointF(mStartPointX, mStartPointY),
            PointF(mEndPointX, mEndPointY))

        animator.duration = 600
        animator.addUpdateListener {
            val pointF = it.animatedValue as PointF
            mMovePointX = pointF.x
            mMovePointY = pointF.y

            invalidate()
        }

        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

}