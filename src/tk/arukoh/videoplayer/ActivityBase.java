package tk.arukoh.videoplayer;

import android.app.Activity;

abstract class ActivityBase extends Activity {

	String getResoucePath(int id) {
		return "android.resource://" + this.getPackageName() + "/" + id;
	}

}
