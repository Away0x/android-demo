package com.away0x.anim.bezier.utils

import android.animation.TypeEvaluator
import android.graphics.PointF

class BezierEvaluator(
    val mFlagPoint: PointF
) : TypeEvaluator<PointF> {

    override fun evaluate(p0: Float, p1: PointF?, p2: PointF?): PointF {
        return BezierUtil.calculateBezierPointForQuadratic(p0, p1!!, mFlagPoint, p2!!)
    }

}