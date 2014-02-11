package tk.arukoh.videoplayer;

import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends ActivityBase {

	VideoView videoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_view);

		final String mediaPath = getIntent().getStringExtra("mediaPath");
		videoView = (VideoView) findViewById(R.id.videoView);
		videoView.setVideoPath(mediaPath);
		videoView.setMediaController(new MediaController(this));
		videoView.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_view, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		videoView.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		videoView.resume();
	}

}
