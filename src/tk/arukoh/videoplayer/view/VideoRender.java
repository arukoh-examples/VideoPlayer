package tk.arukoh.videoplayer.view;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;

public class VideoRender implements GLSurfaceView.Renderer,
		SurfaceTexture.OnFrameAvailableListener {

	private static String TAG = "VideoRender";

	private TextureRender textureRender;
	private SurfaceTexture surfaceTexture;
	private boolean updateSurface = false;

	private MediaPlayer mediaPlayer;

	public VideoRender(Context context) {
		textureRender = new TextureRender();
	}

	public void setMediaPlayer(MediaPlayer player) {
		mediaPlayer = player;
	}

	@Override
	public void onFrameAvailable(SurfaceTexture surfaceTexture) {
		updateSurface = true;
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		synchronized (this) {
			if (updateSurface) {
				surfaceTexture.updateTexImage();
				updateSurface = false;
			}
		}
		textureRender.drawFrame(surfaceTexture);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		int videoWidth = mediaPlayer.getVideoWidth();
		int videoHeight = mediaPlayer.getVideoHeight();
		float videoProportion = (float) videoWidth / (float) videoHeight;
		float screenProportion = (float) width / (float) height;

		int newWidth = width;
		int newHeight = height;
		if (videoProportion > screenProportion) {
			newHeight = (int) ((float) width / videoProportion);
		} else {
			newWidth = (int) (videoProportion * (float) height);
		}
        gl.glViewport(width-newWidth, height-newHeight, newWidth, newHeight);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		textureRender.surfaceCreated();

		surfaceTexture = new SurfaceTexture(textureRender.getTextureId());
		surfaceTexture.setOnFrameAvailableListener(this);

		Surface surface = new Surface(surfaceTexture);
		mediaPlayer.setSurface(surface);
		surface.release();

		try {
			mediaPlayer.prepare();
		} catch (IOException t) {
			Log.e(TAG, "media player prepare failed");
		}

		synchronized (this) {
			updateSurface = false;
		}
	}

}
