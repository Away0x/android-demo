package com.away0x.videoplayer.viewModel

import android.view.View
import androidx.lifecycle.*
import com.away0x.videoplayer.model.MyMediaPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PlayerStatus {
    Playing, Paused, Completed, NotReady
}

class PlayerViewModel : ViewModel() {

    private var controllerShowTime = 0L
    val mediaPlayer = MyMediaPlayer()

    // 播放状态
    private val _playerStatus = MutableLiveData(PlayerStatus.NotReady)
    val playerStatus: LiveData<PlayerStatus> = _playerStatus

    // 缓冲进度
    private val _bufferPercent = MutableLiveData(0)
    val bufferPercent: LiveData<Int> = _bufferPercent

    // 是否显示播放器控制组件
    private val _controllerFrameVisibility = MutableLiveData(View.INVISIBLE)
    val controllerFrameVisibility: LiveData<Int> = _controllerFrameVisibility

    // 是否显示 loading
    private val _progressBarVisibility = MutableLiveData(View.VISIBLE)
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    // 分辨率
    private val _videoResolution = MutableLiveData(Pair(0, 0))
    val videoResolution: LiveData<Pair<Int, Int>> = _videoResolution

    init {
        loadVideo()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release() // 释放 mediaPlayer
    }

    private fun loadVideo() {
        mediaPlayer.apply {
            // 该例为一个固定的视频 url，所以不用每次 reset
            // reset()

            _progressBarVisibility.value = View.VISIBLE
            _playerStatus.value = PlayerStatus.NotReady
            setDataSource("https://stream7.iqilu.com/10339/upload_transcode/202002/17/20200217021133Eggh6zdlAO.mp4")

            // 缓冲完成
            setOnPreparedListener {
                _progressBarVisibility.value = View.INVISIBLE
                it.start()
                _playerStatus.value = PlayerStatus.Playing
            }

            // setOnErrorListener { mediaPlayer, i, i2 ->  }

            // 获取视频信息后会进入该回调，可获取视频分辨率
            setOnVideoSizeChangedListener { _, width, height -> _videoResolution.value = Pair(width, height) }

            // 缓冲进度
            setOnBufferingUpdateListener { _, percent -> _bufferPercent.value = percent }

            // 播放完成
            setOnCompletionListener { _playerStatus.value = PlayerStatus.Completed }

            // 切换进度条加载完成
            setOnSeekCompleteListener {
                mediaPlayer.start()
                _progressBarVisibility.value = View.INVISIBLE
            }

            prepareAsync()
        }
    }

    /**
     * 切换播放状态
     */
    fun togglePlayerStatus() {
        when(_playerStatus.value) {
            PlayerStatus.Playing -> {
                mediaPlayer.pause()
                _playerStatus.value = PlayerStatus.Paused
            }
            PlayerStatus.Paused -> {
                mediaPlayer.start()
                _playerStatus.value = PlayerStatus.Playing
            }
            PlayerStatus.Completed -> {
                mediaPlayer.start()
                _playerStatus.value = PlayerStatus.Playing
            }
            else -> return
        }
    }

    /**
     * 切换控制条的显示
     */
    fun toggleControllerVisibility() {
        if (_controllerFrameVisibility.value == View.INVISIBLE) {
            _controllerFrameVisibility.value = View.VISIBLE
            controllerShowTime = System.currentTimeMillis()
            // 3s 后隐藏控制条
            viewModelScope.launch {
                delay(3000)
                if (System.currentTimeMillis() - controllerShowTime > 3000) {
                    _controllerFrameVisibility.value = View.INVISIBLE
                }
            }
        } else {
            _controllerFrameVisibility.value = View.INVISIBLE
        }
    }

    /**
     * 重新调整播放器宽高
     */
    fun emmitVideoResolution() {
        _videoResolution.value = _videoResolution.value // 这样也会触发 livedata 的观察
    }

    /**
     * 修改进度
     */
    fun playerSeekToProgress(progress: Int) {
        _progressBarVisibility.value = View.VISIBLE
        mediaPlayer.seekTo(progress)
    }

}

class PlayerViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlayerViewModel() as T
    }
}