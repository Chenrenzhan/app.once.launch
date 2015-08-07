package com.app.oncelaunch.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.app.oncelaunch.MainActivity;
import com.app.oncelaunch.appinfo.AppInfo;

public class AdapterAgent {
	private static AppInfoListAdapterBase chooseAdapter;
	private static AppInfoListAdapterBase chosenAdapter;
	
	public static AppInfoListAdapterBase createAdapter(int tabIndex,
			Context context, Activity activity, List<AppInfo> apps, int itemLayoutId, CallBack callback) {
		switch (tabIndex) {
		case MainActivity.CHOOSE:
			chooseAdapter =  new AppInfoListAdapterChoose(context, activity, apps, itemLayoutId, callback);
			return chooseAdapter;
		case MainActivity.CHOSEN:
			chosenAdapter = new AppInfoListAdapterChosen(context, activity, apps, itemLayoutId, callback);
			return chosenAdapter;
		default:
			return null;
		}
	}
	
	public static void notifyDataCHange(int tabIndex, AppInfo appInfo){
		switch(tabIndex){
		case MainActivity.CHOOSE:
			chooseAdapter.remove(appInfo);
			chosenAdapter.add(appInfo);
			break;
		case MainActivity.CHOSEN:
			chosenAdapter.remove(appInfo);
			chooseAdapter.add(appInfo);
			break;
		}
	}
	
	public static void notifyAddAll(List<AppInfo> listAppInfo){
		chooseAdapter.removeAll(listAppInfo);
		chosenAdapter.addAll(listAppInfo);
	}
	

}
