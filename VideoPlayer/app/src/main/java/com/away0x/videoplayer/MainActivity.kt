package com.away0x.videoplayer

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.away0x.videoplayer.viewModel.PlayerStatus
import com.away0x.videoplayer.viewModel.PlayerViewModel
import com.away0x.videoplayer.viewModel.PlayerViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.controller_layout.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val playerViewModel by viewModels<PlayerViewModel> { PlayerViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        viewModelObserve()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 横屏时播放器全屏
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
            playerViewModel.emmitVideoResolution() // 重新调整一下播放器宽高
        }
    }

    /**
     * 初始化 view
     */
    private fun initView() {
        updatePlayerProgress()

        // 播放暂停
        controlButton.setOnClickListener { playerViewModel.togglePlayerStatus() }
        // 播放器控制条显示隐藏
        playerFrame.setOnClickListener { playerViewModel.toggleControllerVisibility() }

        // 修改播放进度条
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) playerViewModel.playerSeekToProgress(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // video surfaceView 的状态监听
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                playerViewModel.mediaPlayer.setDisplay(holder)
                playerViewModel.mediaPlayer.setScreenOnWhilePlaying(true)
            }
            override fun surfaceDestroyed(p0: SurfaceHolder) {}
            override fun surfaceCreated(p0: SurfaceHolder) {}
        })
    }

    /**
     * 监听 viewModel
     */
    private fun viewModelObserve() {
        playerViewModel.apply {
            // 视频 loading 显示
            progressBarVisibility.observe(this@MainActivity, Observer { progressBar.visibility = it })
            // 视频控制条显示
            controllerFrameVisibility.observe(this@MainActivity, Observer { controllerFrame.visibility = it })
            // 视频缓存
            bufferPercent.observe(this@MainActivity, Observer { seekBar.secondaryProgress = seekBar.max * it / 100 })

            // 分辨率
            videoResolution.observe(this@MainActivity, Observer {
                seekBar.max = mediaPlayer.duration
                // 不能直接设置，需要通过 post 方法，放入消息队列中，当 layout 完成后再修改播放器的大小
                playerFrame.post {
                    resizePlayer(it.first, it.second)
                }
            })

            // 播放状态
            playerStatus.observe(this@MainActivity, Observer {
                controlButton.isClickable = true
                when(it) {
                    PlayerStatus.Paused -> controlButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    PlayerStatus.Completed -> controlButton.setImageResource(R.drawable.ic_baseline_replay_24)
                    PlayerStatus.NotReady -> controlButton.isClickable = false
                    else -> controlButton.setImageResource(R.drawable.ic_baseline_pause_24)
                }
            })
        }

        // 为 mediaPlayer 添加 activity 生命周期的观察
        lifecycle.addObserver(playerViewModel.mediaPlayer)
    }

    /**
     * 修改播放器大小
     */
    private fun resizePlayer(width: Int, height: Int) {
        if (width == 0 || height == 0) return
        surfaceView.layoutParams = FrameLayout.LayoutParams(playerFrame.height * width / height, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER)
    }

    /**
     * 更新进度条
     */
    private fun updatePlayerProgress() {
        lifecycleScope.launch {
            while (true) {
                delay(500)
                seekBar.progress = playerViewModel.mediaPlayer.currentPosition
            }
        }
    }

    /**
     * 隐藏系统状态栏 (全屏效果)
     */
    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

}