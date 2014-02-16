package tk.arukoh.videoplayer;

import java.io.IOException;

import tk.arukoh.videoplayer.view.VideoGLSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class GLSurfaceViewActivity extends Activity {

	private static final String TAG = "VideoSurfaceView";

	VideoGLSurfaceView videoGLSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_glsurface_view);

		final String mediaPath = getIntent().getStringExtra("mediaPath");
		videoGLSurfaceView = (VideoGLSurfaceView) findViewById(R.id.videoGLSurfaceView);
		try {
			videoGLSurfaceView.setVideoPath(mediaPath);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glsurface_view, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		videoGLSurfaceView.onDestroy();
		super.onDestroy();
	}

	

}
