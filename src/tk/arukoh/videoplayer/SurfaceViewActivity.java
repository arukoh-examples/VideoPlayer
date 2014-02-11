package tk.arukoh.videoplayer;

import tk.arukoh.videoplayer.view.VideoSurfaceView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SurfaceViewActivity extends Activity {

	VideoSurfaceView videoSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_surface_view);

		final String mediaPath = getIntent().getStringExtra("mediaPath");
		videoSurfaceView = (VideoSurfaceView) findViewById(R.id.videoSurfaceView);
		videoSurfaceView.setVideoPath(mediaPath);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.surface_view, menu);
		return true;
	}

}
