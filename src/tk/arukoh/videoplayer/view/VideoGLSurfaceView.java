package tk.arukoh.videoplayer.view;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;

public class VideoGLSurfaceView extends GLSurfaceView implements
		MediaPlayer.OnPreparedListener {

	private static final String TAG = "VideoSurfaceView";

	private VideoRender videoRenderer;
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;

	public VideoGLSurfaceView(Context context) {
		this(context, null);
	}

	public VideoGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setEGLContextClientVersion(2);
	}

	public void setVideoPath(String path) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(getContext(), Uri.parse(path));
		mediaPlayer.setOnPreparedListener(this);

		mediaController = new MediaController(getContext());
		mediaController.setMediaPlayer(new MediaPlayerControl(mediaPlayer));
		mediaController.setAnchorView(this);
		mediaController.setEnabled(true);

		videoRenderer = new VideoRender(getContext());
		videoRenderer.setMediaPlayer(mediaPlayer);
		setRenderer(videoRenderer);
	}

	@Override
	public void onPause() {
		this.queueEvent(new Runnable() {
			public final void run() {
				mediaPlayer.pause();
			}
		});
		super.onPause();
	}

	@Override
	public void onResume() {
		queueEvent(new Runnable() {
			public void run() {
				videoRenderer.setMediaPlayer(mediaPlayer);
			}
		});

		super.onResume();
	}

	public void onDestroy() {
		this.queueEvent(new Runnable() {
			public final void run() {
				Log.d(TAG, "onDestroy");
				mediaPlayer.release();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mediaController != null) {
			mediaController.show();
		}
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mp.start();
	}

}
