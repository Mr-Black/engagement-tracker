package edu.engagement.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.engagement.thrift.EngagementResponse;
import edu.engagement.thrift.EventInfo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;


public class OverallFragment extends Fragment
{
	private boolean isHeld = false;
	private List<EventInfo> selectedCircles;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        selectedCircles = new ArrayList<EventInfo>();
        
        // create hardcoded data for testing
        EngagementResponse testResponse = new EngagementResponse();
        EventInfo test = new EventInfo();
        test.setAverageEngagement(0.30);
        test.setHeartRateStandardDeviation(10);
        test.setName("algorithms");
        
        EventInfo test2 = new EventInfo();
        test2.setAverageEngagement(0.45);
        test2.setHeartRateStandardDeviation(16);
        test2.setName("gaming");
        
        EventInfo test3 = new EventInfo();
        test3.setAverageEngagement(0.10);
        test3.setHeartRateStandardDeviation(8);
        test3.setName("class");
        
        EventInfo test4 = new EventInfo();
        test4.setAverageEngagement(0.50);
        test4.setHeartRateStandardDeviation(18);
        test4.setName("ninja warriorz");
        
        EventInfo test5 = new EventInfo();
        test5.setAverageEngagement(0.25);
        test5.setHeartRateStandardDeviation(3);
        test5.setName("rain");
        
        testResponse.addToEventInformation(test);
        testResponse.addToEventInformation(test2);
        testResponse.addToEventInformation(test3);
        testResponse.addToEventInformation(test4);
        testResponse.addToEventInformation(test5);
        
        View view = inflater.inflate(R.layout.overall, container, false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widthofScreen = size.x;
        int heightofScreen = size.y;

        GridLayout gridLayout = (GridLayout) view.findViewById(R.id.gridlayout);
        
        List<EventInfo> events = testResponse.getEventInformation();
        for(int i = 0; i < events.size(); i++)
        {
        	final EventInfo evnt = events.get(i);
        	final double vol = evnt.getHeartRateStandardDeviation();
        	double avg_eng = evnt.getAverageEngagement();
        	final String name = evnt.getName();
        	final RelativeLayout btn = new RelativeLayout(getActivity());
        	if(vol < 5)
        	{
        		btn.setBackgroundResource(R.drawable.btn_gray);
        	}
        	else if(vol < 15)
        	{
        		btn.setBackgroundResource(R.drawable.btn_yellow);
        	}
        	else
        	{
        		btn.setBackgroundResource(R.drawable.btn_red);
        	}
    		GridLayout.LayoutParams param = new GridLayout.LayoutParams();
    		param.height = (int) (widthofScreen * avg_eng);
    		param.width = (int) (widthofScreen * avg_eng);
    		param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, (int) (avg_eng * 10));
    		param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, (int) (avg_eng * 10));
    		btn.setLayoutParams(param);
    		btn.setClickable(true);
    		btn.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// new intent? new activity?
				}
    		});
    		btn.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View arg0) {

					boolean isListed = false;
					for(int i = 0; i < selectedCircles.size(); i++)
					{
						EventInfo ref = selectedCircles.get(i);
						if(ref.getName().equals(name))
						{
							isListed = true;
							selectedCircles.remove(i);
							break;
						}
					}
					
					if(vol < 5)
					{
						if(isListed)
						{
							btn.setBackgroundResource(R.drawable.btn_gray_unsel);
						}
						else
						{
							selectedCircles.add(evnt);
							btn.setBackgroundResource(R.drawable.btn_gray_sel);
						}
					}
					else if(vol < 15)
					{
						if(isListed)
						{
							btn.setBackgroundResource(R.drawable.btn_yellow_unsel);
						}
						else
						{
							selectedCircles.add(evnt);
							btn.setBackgroundResource(R.drawable.btn_yellow_sel);
						}
					}
					else
					{
						if(isListed)
						{
							btn.setBackgroundResource(R.drawable.btn_red_unsel);
						}
						else
						{
							selectedCircles.add(evnt);
							btn.setBackgroundResource(R.drawable.btn_red_sel);
						}
					}
					
					return false;
				}
    			
    		});
    		
    		TextView title = new TextView(getActivity());
    		title.setTextSize(14);
    		title.setText(name);
    		RelativeLayout.LayoutParams rel_param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    		rel_param.addRule(RelativeLayout.CENTER_HORIZONTAL);
    		rel_param.addRule(RelativeLayout.CENTER_VERTICAL);
    		title.setLayoutParams(rel_param);
    		btn.addView(title);
    		
    		gridLayout.addView(btn);
        }
        
        return view;
        
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

}
