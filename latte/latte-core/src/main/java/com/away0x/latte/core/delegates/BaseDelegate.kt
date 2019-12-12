package com.away0x.latte.core.delegates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment

/**
 * 由于单 activity 架构，fragment 不再是本来的作用，所以该项目称之为 delegate
 */
abstract class BaseDelegate : SwipeBackFragment() {

    private lateinit var mRootView: View

    abstract fun setLayout(): Any

    abstract fun onBindView(savedInstanceState: Bundle?, rootView: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = when {
            setLayout() is Int -> inflater.inflate(setLayout() as Int, container, false)
            setLayout() is View -> setLayout() as View
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }

        mRootView = rootView
        return rootView
    }

    /** 在该生命周期中才可通过 kotlin ext 方式获取控件 */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onBindView(savedInstanceState, mRootView)
    }

}