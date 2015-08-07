package com.app.oncelaunch;

import java.util.List;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.app.oncelaunch.fragment.FragmentPageChoose;
import com.app.oncelaunch.fragment.FragmentPageChosen;
import com.app.oncelaunch.ui.IndicatorFragmentActivity;

public class MainActivity extends IndicatorFragmentActivity {
	
	public final static int FRAGMENT_CHOOSE = 0;
	public final static int FRAGMENT_CHOSEN = 1;
	
	public final static int CHOOSE = 0;
	public final static int CHOSEN = 1;
	
	private Vector<Intent> vectorIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Log.e("mydebug", "onCreate before super");
        super.onCreate(savedInstanceState);
        Log.e("mydebug", "onCreate after super");
    }

    @SuppressLint("NewApi")
	@Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_CHOOSE, getString(R.string.str_choose_app),
                FragmentPageChoose.class));
        
        tabs.add(new TabInfo(FRAGMENT_CHOSEN, getString(R.string.str_chosen_app),
        		FragmentPageChosen.class));

        return FRAGMENT_CHOSEN;
    }
    
    @Override
    protected void onRestart(){
    	super.onRestart();
    	
    	if(vectorIntent != null && vectorIntent.size() > 0){
    		Intent intent = vectorIntent.remove(0);
    		if(intent != null){
    			startActivity(intent);
//    			startActivities(intents); //intents为Intent[]
    			if (intent != null) {
    				startActivity(intent);
    				new Thread() {

    					@Override
    					public void run() {
    						try {
    							Thread.sleep(400);
    						} catch (InterruptedException e) {
    							e.printStackTrace();
    						}
    						onStop();
    					}

    				}.start();
    			}
    		}
    	}
    	
    	Log.e("mydebug", "----------  onRestart");
    }

    @Override
	protected void onStop() {
    	super.onStop();
    	
    	vectorIntent = (Vector<Intent>) FragmentPageChosen.getIntent();
    	if(vectorIntent != null && vectorIntent.size() > 0){
    		onRestart();
    	}
    	
    	Log.e("mydebug", "----------  onRestop");
    }
}
