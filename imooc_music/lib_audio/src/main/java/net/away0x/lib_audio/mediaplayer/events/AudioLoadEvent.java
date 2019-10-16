package net.away0x.lib_audio.mediaplayer.events;

import net.away0x.lib_audio.mediaplayer.model.AudioBean;

public class AudioLoadEvent {
    public AudioBean mAudioBean;

    public AudioLoadEvent(AudioBean audioBean) {
        this.mAudioBean = audioBean;
    }
}
