package edu.engagement.application;

import java.text.DecimalFormat;
import java.util.Locale;
import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import edu.engagement.application.DataPointSource;
import edu.engagement.application.NewConnectedListener;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.neurosky.thinkgear.TGRawMulti;

import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import zephyr.android.HxMBT.*;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import edu.engagement.thrift.EegAttention;
import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.EegRaw;
import edu.engagement.thrift.EngagementInformation;
import edu.engagement.thrift.EngagementQuery;
import edu.engagement.thrift.EngagementService;
import edu.engagement.thrift.EngagementServiceUnavailable;
import edu.engagement.thrift.Event;
import edu.engagement.thrift.HeartRate;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	BluetoothAdapter adapter = null;
	BTClient _bt = null;
	ZephyrProtocol _protocol = null;
	NewConnectedListener _NConnListener = null;
	TGDevice tgDevice = null;
	DataPointSource dataSource = null;
	ActionMode mActionMode = null;
	boolean HRConnected = false;
	boolean EEGConnected = false;

	private final int HEART_RATE = 0x100;
	private static final int PORT = 7911;
	final boolean rawEnabled = true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		/**** DATABASE STUFF ****/
		dataSource = new DataPointSource(this.getApplicationContext());
		dataSource.open();

		/**** ACTION BAR UI STUFF ****/
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);

		// Create the adapter that will return a fragment for each of the two
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
		{
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

		/**** BLUETOOTH STUFF ****/
		// Sending a message to android that we are going to initiate a pairing
		// request
		IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");

		/*
		 * Registering a new BTBroadcast receiver from the Main Activity context
		 * with pairing request event
		 */
		this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);

		// Registering the BTBondReceiver in the application that the status of
		// the receiver has changed to Paired
		IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
		this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);

		// Creates a contextual action bar that allows the user to connect
		mActionMode = startActionMode(mActionModeCallback);
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback()
	{

		// Called when the action mode is created; startActionMode() was called
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu)
		{
			// Inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return true;
		}

		// Called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu)
		{
			return false; // Return false if nothing is done
		}

		// Called when the user selects a contextual menu item
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item)
		{
			switch (item.getItemId())
			{
			case R.id.action_connect:
				String BhMacID = "00:07:80:9D:8A:E8";
				adapter = BluetoothAdapter.getDefaultAdapter();

				if (!EEGConnected)
				{
					if (adapter != null)
					{
						if (tgDevice == null)
						{
							tgDevice = new TGDevice(adapter, handler);
						}
						if (tgDevice.getState() != TGDevice.STATE_CONNECTING
								&& tgDevice.getState() != TGDevice.STATE_CONNECTED)
							tgDevice.connect(rawEnabled);
					}
				}

				if (!HRConnected)
				{
					Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

					if (pairedDevices.size() > 0)
					{
						for (BluetoothDevice device : pairedDevices)
						{
							if (device.getName().startsWith("HXM"))
							{
								BluetoothDevice btDevice = device;
								BhMacID = btDevice.getAddress();
								break;

							}
						}

					}

					BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
					String DeviceName = Device.getName();
					_bt = new BTClient(adapter, BhMacID);
					_NConnListener = new NewConnectedListener(Newhandler, Newhandler);
					_bt.addConnectedEventListener(_NConnListener);

					if (_bt.IsConnected())
					{
						_bt.start();
						String ErrorText = "Connected to HxM " + DeviceName;
						Toast.makeText(getApplicationContext(), ErrorText, Toast.LENGTH_SHORT).show();
						HRConnected = true;
						if (HRConnected && EEGConnected)
						{
							mActionMode.finish();
						}

					} else
					{
						String ErrorText = "Unable to Connect to HxM!";
						Toast.makeText(getApplicationContext(), ErrorText, Toast.LENGTH_SHORT).show();
					}
				}
				return true;
			default:
				return false;
			}
		}

		// Called when the user exits the action mode
		@Override
		public void onDestroyActionMode(ActionMode mode)
		{
			mActionMode = null;
		}
	};

	@Override
	public void onDestroy()
	{
		tgDevice.close();

		// Sync data
		try
		{
			TTransport transport = new TSocket("localhost", PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			EngagementService.Client client = new EngagementService.Client(protocol);
			transport.open();

			EngagementInformation info = new EngagementInformation();
			info.setGoogleId("code.mr.black");
			info.setEegPowerMessages(dataSource.getAllDataPointsEEG());
			info.setHeartRateMessages(dataSource.getAllDataPointsHR());
			info.setEegAttentionMessages(dataSource.getAllDataPointsAttention());
			info.setEegRawMessages(dataSource.getAllDataPointsRaw());
			client.syncEngagementInformation(info);
			transport.close();
			dataSource.clearDatabse();
			dataSource.close();
		} catch (TTransportException e)
		{
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (EngagementServiceUnavailable e)
		{
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (TException e)
		{
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			if (position == 0)
			{
				return new RealTimeDataFragment();
			} else
			{
				return new OverallFragment();

			}
		}

		@Override
		public int getCount()
		{
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			Locale l = Locale.getDefault();
			switch (position)
			{
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	private class BTBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Log.d("BTIntent", intent.getAction());
			Bundle b = intent.getExtras();
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
			Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
			try
			{
				BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE")
						.toString());
				Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[]
				{ String.class });
				byte[] pin = (byte[]) m.invoke(device, "1234");
				m = device.getClass().getMethod("setPin", new Class[]
				{ pin.getClass() });
				Object result = m.invoke(device, pin);
				Log.d("BTTest", result.toString());
			} catch (SecurityException e1)
			{
				// Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchMethodException e1)
			{
				// Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				// Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class BTBondReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Bundle b = intent.getExtras();
			BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
			Log.d("Bond state", "BOND_STATED = " + device.getBondState());
		}
	}

	final Handler Newhandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case HEART_RATE:
				int HeartRate = msg.getData().getInt("HeartRate");
				String HeartRatetext = "" + HeartRate;
				System.out.println("Heart Rate Info is " + HeartRatetext);
				TextView tv = (TextView) findViewById(R.id.HeartRateText);
				tv.setText(HeartRatetext);
				dataSource.createDataPointHR(System.currentTimeMillis(), HeartRate);
				break;
			}
		}
	};

	private final Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case TGDevice.MSG_STATE_CHANGE:
				switch (msg.arg1)
				{
				case TGDevice.STATE_IDLE:
					break;

				case TGDevice.STATE_CONNECTING:
					Toast.makeText(getApplicationContext(), "MindWave Connecting...", Toast.LENGTH_SHORT).show();
					break;

				case TGDevice.STATE_CONNECTED:
					Toast.makeText(getApplicationContext(), "MindWave Connected", Toast.LENGTH_SHORT).show();
					EEGConnected = true;
					if (HRConnected && EEGConnected)
					{
						mActionMode.finish();
					}
					tgDevice.start();
					break;

				case TGDevice.STATE_NOT_PAIRED:
					Toast.makeText(getApplicationContext(), "MindWave Not Paired", Toast.LENGTH_SHORT).show();
					break;

				case TGDevice.STATE_DISCONNECTED:
					EEGConnected = false;
					mActionMode = startActionMode(mActionModeCallback);
					break;
				}

				break;

			case TGDevice.MSG_POOR_SIGNAL:

				break;
			case TGDevice.MSG_RAW_DATA:

				break;

			case TGDevice.MSG_ATTENTION:
				int att = msg.arg1;
				dataSource.createDataPointAttention(System.currentTimeMillis(), att);
				System.out.println("Gathereed Attention Data");
				break;

			// Standard Brain Waves
			case TGDevice.MSG_EEG_POWER:
				TGEegPower eegPow = (TGEegPower) msg.obj;
				double alpha = eegPow.highAlpha;
				double beta = eegPow.highBeta;
				double theta = eegPow.theta;

				dataSource.createDataPointEEG(System.currentTimeMillis(), alpha, beta, theta);

				TextView tv = (TextView) findViewById(R.id.EEGText);
				DecimalFormat df = new DecimalFormat("#.##");
				tv.setText(df.format((beta / (alpha + theta))));
				System.out.println("Engagement is " + (beta / (alpha + theta)));
				break;

			case TGDevice.MSG_RAW_MULTI:
				TGRawMulti eegRaw = (TGRawMulti) msg.obj;
				double ch1 = eegRaw.ch1;
				double ch2 = eegRaw.ch2;
				double ch3 = eegRaw.ch3;
				double ch4 = eegRaw.ch4;
				double ch5 = eegRaw.ch5;
				double ch6 = eegRaw.ch6;
				double ch7 = eegRaw.ch7;
				double ch8 = eegRaw.ch8;

				dataSource.createDataPointRaw(System.currentTimeMillis(), ch1, ch2, ch3, ch4, ch5, ch6, ch7, ch8);

				System.out.println("Gathereed Raw Data");
				break;

			default:
				break;
			}
		}
	};

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment()
		{

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
}
