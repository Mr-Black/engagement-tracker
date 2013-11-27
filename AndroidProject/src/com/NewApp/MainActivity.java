package com.NewApp;

import android.app.Activity;
import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
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
import android.view.View.OnClickListener;
import android.widget.*;
import zephyr.android.HxMBT.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import edu.engagement.thrift.EngagementInformation;
import edu.engagement.thrift.EngagementService;
import edu.engagement.thrift.EngagementServiceUnavailable;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	BluetoothAdapter adapter = null;
	BTClient _bt;
	ZephyrProtocol _protocol;
	NewConnectedListener _NConnListener;
	TGDevice tgDevice;
	DataPointSource dataSource;
	
	private final int HEART_RATE = 0x100;
	private static final int PORT = 7911;
	final boolean rawEnabled = true;
	
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataSource = new DataPointSource(this.getApplicationContext());
        dataSource.open();
        
        setContentView(R.layout.main);
        /*Sending a message to android that we are going to initiate a pairing request*/
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        /*Registering a new BTBroadcast receiver from the Main Activity context with pairing request event*/
       this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        // Registering the BTBondReceiver in the application that the status of the receiver has changed to Paired
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
       this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);
        
      //Obtaining the handle to act on the CONNECT button
        TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
		String ErrorText  = "Not Connected to HxM ! !";
		 tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(R.id.ButtonConnect);
        if (btnConnect != null)
        {
        	btnConnect.setOnClickListener(new OnClickListener()
        	{
        		public void onClick(View v)
        		{
        			String BhMacID = "00:07:80:9D:8A:E8";
        			adapter = BluetoothAdapter.getDefaultAdapter();
        			if(adapter != null)
        			{
        				tgDevice = new TGDevice(adapter, handler);
        			}
        			
        			if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
        	    		tgDevice.connect(rawEnabled);   
        			
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
        			_NConnListener = new NewConnectedListener(Newhandler,Newhandler);
        			_bt.addConnectedEventListener(_NConnListener);
        			
        			TextView tv1 = (EditText)findViewById(R.id.labelHeartRate);
        			tv1.setText("000");
        			
        			 
        			if(_bt.IsConnected())
        			{
        				_bt.start();
        				TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
        				String ErrorText  = "Connected to HxM "+DeviceName;
						 tv.setText(ErrorText);
						 
						 //Reset all the values to 0s

        			}
        			else
        			{
        				TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
        				String ErrorText  = "Unable to Connect !";
						 tv.setText(ErrorText);
        			}
        		}
        	});
        }
        /*Obtaining the handle to act on the DISCONNECT button*/
        Button btnDisconnect = (Button) findViewById(R.id.ButtonDisconnect);
        if (btnDisconnect != null)
        {
        	btnDisconnect.setOnClickListener(new OnClickListener() {
				@Override
				/*Functionality to act if the button DISCONNECT is touched*/
				public void onClick(View v) {
					//  Auto-generated method stub
					/*Reset the global variables*/
					TextView tv = (TextView) findViewById(R.id.labelStatusMsg);
    				String ErrorText  = "Disconnected from HxM!";
					 tv.setText(ErrorText);

					/*This disconnects listener from acting on received messages*/	
					_bt.removeConnectedEventListener(_NConnListener);
					/*Close the communication with the device & throw an exception if failure*/
					_bt.Close();
				
				}
        	});
        }
    }
    @Override
    protected void onResume() {
      dataSource.open();
      super.onResume();
    }

    @Override
    protected void onPause() {
      dataSource.close();
      super.onPause();
    }

    
    @Override
    public void onDestroy()
    {
    	tgDevice.close();
    	
    	//Sync data
    	try {
			TTransport transport = new TSocket("localhost", PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			EngagementService.Client client = 
					new EngagementService.Client(protocol);
			transport.open();
			EngagementInformation info = new EngagementInformation();
			info.setGoogleId("code.mr.black");
			info.setEegPowerMessages(dataSource.getAllDataPointsEEG());
			info.setHeartRateMessages(dataSource.getAllDataPointsHR());
			client.syncEngagementInformation(info);
			transport.close();
			dataSource.clearDatabse();
		} catch(TTransportException e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (EngagementServiceUnavailable e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (TException e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		}
        super.onDestroy();
    }
    
    private class BTBondReceiver extends BroadcastReceiver
    {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle b = intent.getExtras();
			BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
			Log.d("Bond state", "BOND_STATED = " + device.getBondState());
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
			try {
				BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
				Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[] {String.class} );
				byte[] pin = (byte[])m.invoke(device, "1234");
				m = device.getClass().getMethod("setPin", new Class [] {pin.getClass()});
				Object result = m.invoke(device, pin);
				Log.d("BTTest", result.toString());
			} catch (SecurityException e1) {
				//  Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				//  Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
        	TextView tv;
        	
        	switch (msg.what)
        	{
            case TGDevice.MSG_STATE_CHANGE:

            	tv = (EditText)findViewById(R.id.labelEEGStatusMsg);
                switch (msg.arg1)
                {
	                case TGDevice.STATE_IDLE:
	                    break;
	                    
	                case TGDevice.STATE_CONNECTING:
	                	tv.append("Connecting...\n");
	                	break;	
	                	
	                case TGDevice.STATE_CONNECTED:
	                	tv.append("Connected.\n");
	                	tgDevice.start();
	                    break;
	                    
	                case TGDevice.STATE_NOT_FOUND:
	                	tv.append("Can't find\n");
	                	break;
	                	
	                case TGDevice.STATE_NOT_PAIRED:
	                	tv.append("not paired\n");
	                	break;
	                	
	                case TGDevice.STATE_DISCONNECTED:
	                	tv.append("Disconnected mang\n");
                }

                break;
                
            case TGDevice.MSG_POOR_SIGNAL:
            		//signal = msg.arg1;
            	    tv = (EditText)findViewById(R.id.labelEEGStatusMsg);
            		tv.append("PoorSignal: " + msg.arg1 + "\n");
                break;
            case TGDevice.MSG_RAW_DATA:	  
            		//TODO: Add to DB
            		//raw1 = msg.arg1;
            		//tv.append("Got raw: " + msg.arg1 + "\n");
            	break;
            	/*
            case TGDevice.MSG_HEART_RATE:
        		tv.append("Heart rate: " + msg.arg1 + "\n");
                break;
                */
            case TGDevice.MSG_ATTENTION:
            		//TODO: Add to DB
            		//att = msg.arg1;
            		//tv.append("Attention: " + msg.arg1 + "\n");
            		//Log.v("HelloA", "Attention: " + att + "\n");
            	break;
            	
            	/*
            case TGDevice.MSG_MEDITATION:

            	break;
            case TGDevice.MSG_BLINK:
            		tv.append("Blink: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_RAW_COUNT:
            		//tv.append("Raw Count: " + msg.arg1 + "\n");
            	break;
            case TGDevice.MSG_LOW_BATTERY:
            	Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
            	break;
            	*/
            case TGDevice.MSG_EEG_POWER:
            	TGEegPower eegPow = (TGEegPower)msg.obj;
            	double alpha = eegPow.highAlpha;
            	double beta = eegPow.highBeta;
            	double theta = eegPow.theta;
            	
            	dataSource.createDataPointEEG(System.currentTimeMillis(), alpha, beta, theta);
            	
            	tv = (EditText)findViewById(R.id.labelEEG);
            	tv.setText("" + (beta + ( alpha / theta )));
            	 
            default:
            	break;
        }
        }
    };
    

    final  Handler Newhandler = new Handler(){
    	public void handleMessage(Message msg)
    	{
    		TextView tv;
    		switch (msg.what)
    		{
    		case HEART_RATE:
    			int HeartRate = msg.getData().getInt("HeartRate");
    			String HeartRatetext = "" + HeartRate;
    			tv = (EditText)findViewById(R.id.labelHeartRate);
    			System.out.println("Heart Rate Info is "+ HeartRatetext);
    			if (tv != null)tv.setText(HeartRatetext);
    			dataSource.createDataPointHR(System.currentTimeMillis(), HeartRate);
    		break;
    		
    		/*
    		case TIME_STAMP:
    			String TimeStamptext = msg.getData().getString("TimeStamp");
    			tv = (EditText)findViewById(R.id.labelTimeStamp);
    			if (tv != null)tv.setText(TimeStamptext);
    			if(TimeStamptext.substring(TimeStamptext.length()-5) == "0000");
    		
    		break;*/
    		
    		}
    	}

    };
    
}


