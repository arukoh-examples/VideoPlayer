package tk.arukoh.videoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActivityBase implements
		AdapterView.OnItemClickListener {

	private static final String[] ITEMS = { "VideoView", "SurfaceView",
			"GLSurfaceView" };
	private static final Class<?>[] ACTIVITIES = { VideoViewActivity.class,
			SurfaceViewActivity.class, GLSurfaceViewActivity.class };
	private String mediaPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.itemListView);
		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ITEMS));
		listView.setOnItemClickListener(this);

		mediaPath = getResoucePath(R.raw.sample);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final Intent intent = new Intent(this, ACTIVITIES[position]);
		intent.putExtra("mediaPath", mediaPath);
		startActivity(intent);
	}

}
