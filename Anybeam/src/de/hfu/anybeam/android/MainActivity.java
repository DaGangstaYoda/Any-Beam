package de.hfu.anybeam.android;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hfu.anybeam.android.R;
import de.hfu.anybeam.networkCore.Client;
import de.hfu.anybeam.networkCore.DeviceType;
import de.hfu.anybeam.networkCore.EncryptionType;
import de.hfu.anybeam.networkCore.EncryptionUtils;
import de.hfu.anybeam.networkCore.NetworkCoreUtils;
import de.hfu.anybeam.networkCore.NetworkEnvironmentListener;
import de.hfu.anybeam.networkCore.NetworkEnvironmentSettings;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements NetworkEnvironmentListener {


	private final String GROUP_NAME = "my_group";
	private NetworkEnvironmentSettings SETTINGS = new NetworkEnvironmentSettings(this.GROUP_NAME, "MacBook Pro", DeviceType.TYPE_LAPTOP, 
			EncryptionType.AES128, 1338, 1337, EncryptionUtils.generateSecretKeyFromPassword("anybeamRockt1137", EncryptionType.AES128));;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		TextView tv = (TextView) this.findViewById(R.id.textView2);
		CharSequence text = this.getResources().getString(R.string.main_instruction);
		text = this.addSmileySpans(text);
		tv.setText(text);
		
	}

	@Override
	protected void onResume() {
		super.onResume();

		try {
			NetworkCoreUtils.createNetworkEnvironment(this.SETTINGS).addNetworkEnvironmentListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == R.id.action_settings) {
			Toast.makeText(this, "Setting", Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void shareClipboard(View v) {
		Intent clipboardIntent = new Intent(this, de.hfu.anybeam.android.SendActivity.class);
		clipboardIntent.setType("text/plain");
		clipboardIntent.setAction(Intent.ACTION_SEND);
		clipboardIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Clipboard");
		clipboardIntent.putExtra(android.content.Intent.EXTRA_TEXT, "todo");
		startActivity(clipboardIntent);
	}

	private CharSequence addSmileySpans(CharSequence text) {
		//smilyRegexMap = new HashMap<Integer, String>();

		String indicator = "shareicon";
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		Bitmap smiley = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_action_share);
		int start = text.toString().indexOf(indicator);
		builder.setSpan(new ImageSpan(smiley, ImageSpan.ALIGN_BASELINE), start,start+indicator.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		return builder;
	}

	@Override
	public void clientFound(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientUpdated(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientLost(Client c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientListCleared() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientSearchStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientSearchDone() {
		// TODO Auto-generated method stub

	}
}