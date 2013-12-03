package com.example.braindrain;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		TabSpec overallTab = tabHost.newTabSpec("Overall");
		TabSpec realTimeTab = tabHost.newTabSpec("Real-Time");
		
		overallTab.setIndicator("Overall");
		overallTab.setContent(new Intent(this, OverallActivity.class));
		
		realTimeTab.setIndicator("Real-Time");
		realTimeTab.setContent(new Intent(this, RealTimeActivity.class));
		
		tabHost.addTab(overallTab);
		tabHost.addTab(realTimeTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
