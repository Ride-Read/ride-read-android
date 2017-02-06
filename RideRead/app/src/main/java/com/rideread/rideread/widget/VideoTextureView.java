package com.rideread.rideread.widget;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.Toast;

import com.rideread.rideread.R;

import java.io.File;

/**
 * Created by Jackbing on 2017/2/5.
 */

public class VideoTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private MediaPlayer mediaPlayer;
    private Surface surface;
    private ImageView ivTip;
    private boolean isPlaying = false;
    private boolean isSurfaceTextureAvailable = false;
    private String videoPath;

    public VideoTextureView(Context context) {

        this(context,null);
    }

    public VideoTextureView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setSurfaceTextureListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        surface = new Surface(surfaceTexture);
        isSurfaceTextureAvailable = true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {


    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface=null;
        onVideoTextureViewDestroy();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void startMediaPlayer(){
        if(isPlaying || !isSurfaceTextureAvailable)
            return;
//        if(TextUtils.isEmpty(videoPath)){
//            Toast.makeText(getContext(), "视频路径异常", Toast.LENGTH_SHORT).show();
//            return;
//        }
        try {
            if(true)
                mediaPlayer = MediaPlayer.create(getContext(), R.raw.test);
            else {
                final File file = new File(videoPath);
                if (!file.exists()) {//文件不存在
                    Toast.makeText(getContext(),"视频不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(file.getAbsolutePath());
            }
            mediaPlayer.setSurface(surface);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setVolume(1, 1); //设置左右音道的声音为0
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp){
                    mediaPlayer.start();
                    showIvTip(false);
                    isPlaying = true;
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopMediaPlayer();
                }
            });
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showIvTip(boolean show){
        if(ivTip != null){
            int visibility = show?VISIBLE : GONE;
            ivTip.setVisibility(visibility);
        }
    }

    public void stopMediaPlayer(){
        if(mediaPlayer != null && isPlaying) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        showIvTip(true);
        isPlaying = false;
    }

    public void setIvTip(ImageView ivTip){
        this.ivTip = ivTip;
    }

    public boolean getPlayStatus(){
        return isPlaying;
    }

    public void setVideoPath(String path ){
        videoPath = path;
    }
    public void onVideoTextureViewDestroy(){
        isPlaying = false;
        if(mediaPlayer != null){
            stopMediaPlayer();
            mediaPlayer.release();
        }
    }
}
