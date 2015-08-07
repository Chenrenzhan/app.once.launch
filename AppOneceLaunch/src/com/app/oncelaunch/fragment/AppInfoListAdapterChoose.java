package com.app.oncelaunch.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.oncelaunch.MainActivity;
import com.app.oncelaunch.appinfo.AppInfo;
import com.app.oncelaunch.ui.IndicatorFragmentActivity;

public class AppInfoListAdapterChoose extends AppInfoListAdapterBase{
	
	private Context context;

	public AppInfoListAdapterChoose(Context context, Activity activity, List<AppInfo> apps,
			int itemLayoutId, CallBack callback) {
		super(context, activity, apps, itemLayoutId, callback);
		// TODO Auto-generated constructor stub
		
		this.context = context;
	}

	@Override
	public void btnOperatorClick(ImageButton btnOperator, final AppInfo appInfo) {
		// TODO Auto-generated method stub
		btnOperator.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show();
				AdapterAgent.notifyDataCHange(MainActivity.CHOOSE, appInfo);
//				((IndicatorFragmentActivity) activity).setTabTitle(MainActivity.CHOOSE, "选择应用(" + mlistAppInfo.size() + ")");
			}
		});
	}
	@Override
	public void setTabAppSize(int size) {
     	((IndicatorFragmentActivity) activity).setTabTitle(MainActivity.CHOOSE, "选择应用(" + size + ")");
     	
	}
	
	@Override
	public boolean remove(AppInfo appInfo){
		if(appInfo == null){
			return false;
		}
		if(mlistAppInfo.remove(appInfo) == true){
			setTabAppSize(mlistAppInfo.size() );
			notifyDataSetChanged();
			
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void removeAll(List<AppInfo> apps){
		Log.e("mydebug", "==== before remove  all app'counts " + apps.size());
		Log.e("mydebug", "==== before remove all " + mlistAppInfo.size());
		mlistAppInfo.removeAll(apps);
		Log.e("mydebug", "==== after remove all " + mlistAppInfo.size());
		notifyDataSetChanged();
		setTabAppSize(mlistAppInfo.size());
	}
	
	@Override
	public boolean add(AppInfo appInfo){
		if(appInfo == null){
			return false;
		}
		if(mlistAppInfo.add(appInfo) == true){
			setTabAppSize(mlistAppInfo.size() );
			notifyDataSetChanged();
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void addAll(List<AppInfo> apps){
		mlistAppInfo.addAll(apps);
		notifyDataSetChanged();
		setTabAppSize(mlistAppInfo.size());
	}

	@Override
	public void checkBoxChanged(CheckBox checkBox) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void storeCheckChanged(int postion, Boolean checked){
//		
//	}
	
}
