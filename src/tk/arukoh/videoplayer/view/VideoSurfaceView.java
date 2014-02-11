package tk.arukoh.videoplayer.view;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;

public class VideoSurfaceView extends SurfaceView implements Callback,
		OnPreparedListener {

	private static final String TAG = "VideoSurfaceView";

	private Uri mediaUri;
	private SurfaceHolder holder;
	private MediaPlayer mediaPlayer;
	private MediaController mediaController;

	public VideoSurfaceView(Context context) {
		super(context);
		initSurface();
	}

	public VideoSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSurface();
	}

	public VideoSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSurface();
	}

	public void setVideoPath(String path) {
		mediaUri = Uri.parse(path);
	}

	public void setMediaController(MediaController controller) {
		mediaController = controller;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mediaController != null) {
			mediaController.show();
		}
		return false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(getContext(), mediaUri);
			mediaPlayer.setDisplay(holder);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.prepareAsync();

			mediaController = new MediaController(getContext());
			mediaController.setMediaPlayer(new Control(mediaPlayer));
			mediaController.setAnchorView(this);
			mediaController.setEnabled(true);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, e.getClass().getName(), e);
		} catch (SecurityException e) {
			Log.e(TAG, e.getClass().getName(), e);
		} catch (IllegalStateException e) {
			Log.e(TAG, e.getClass().getName(), e);
		} catch (IOException e) {
			Log.e(TAG, e.getClass().getName(), e);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mediaPlayer) {
		setVideoSize();
		mediaPlayer.start();
	}

	private void initSurface() {
		holder = this.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(this);
	}

	private void setVideoSize() {
		int videoWidth = mediaPlayer.getVideoWidth();
		int videoHeight = mediaPlayer.getVideoHeight();
		float videoProportion = (float) videoWidth / (float) videoHeight;

		int screenWidth = getWidth();
		int screenHeight = getHeight();
		float screenProportion = (float) screenWidth / (float) screenHeight;

		ViewGroup.LayoutParams lp = getLayoutParams();
		if (videoProportion > screenProportion) {
			lp.width = screenWidth;
			lp.height = (int) ((float) screenWidth / videoProportion);
		} else {
			lp.width = (int) (videoProportion * (float) screenHeight);
			lp.height = screenHeight;
		}
		setLayoutParams(lp);
	}

	private static class Control implements MediaPlayerControl {

		private final MediaPlayer mediaPlayer;

		Control(MediaPlayer mediaPlayer) {
			this.mediaPlayer = mediaPlayer;
		}

		@Override
		public boolean canPause() {
			return true;
		}

		@Override
		public boolean canSeekBackward() {
			return true;
		}

		@Override
		public boolean canSeekForward() {
			return true;
		}

		@Override
		public int getBufferPercentage() {
			return 0;
		}

		@Override
		public int getCurrentPosition() {
			return mediaPlayer.getCurrentPosition();
		}

		@Override
		public int getDuration() {
			return mediaPlayer.getDuration();
		}

		@Override
		public boolean isPlaying() {
			return mediaPlayer.isPlaying();
		}

		@Override
		public void pause() {
			mediaPlayer.pause();
		}

		@Override
		public void seekTo(int pos) {
			mediaPlayer.seekTo(pos);
		}

		@Override
		public void start() {
			mediaPlayer.start();
		}
	}

}
