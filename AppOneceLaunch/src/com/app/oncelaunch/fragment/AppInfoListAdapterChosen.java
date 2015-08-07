package com.app.oncelaunch.fragment;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.oncelaunch.MainActivity;
import com.app.oncelaunch.appinfo.AppInfo;
import com.app.oncelaunch.appinfo.AppInfoLocation;
import com.app.oncelaunch.fragment.AppInfoListAdapterBase.ViewHolder;
import com.app.oncelaunch.ui.IndicatorFragmentActivity;

public class AppInfoListAdapterChosen extends AppInfoListAdapterBase{
	
//	private Context context;
	private AppInfoLocation appInfoSet;

	public AppInfoListAdapterChosen(Context context, Activity activity, List<AppInfo> apps,
			int itemLayoutId, CallBack callback) {
		super(context, activity, apps, itemLayoutId, callback);
		// TODO Auto-generated constructor stub
		
//		this.context = context;
		appInfoSet = AppInfoLocation.instance();
	}

	@Override
	public void btnOperatorClick(ImageButton btnOperator, final AppInfo appInfo) {
		// TODO Auto-generated method stub
		btnOperator.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
//				mlistAppInfo.remove(appInfo);
//				notifyDataSetChanged();
				AdapterAgent.notifyDataCHange(MainActivity.CHOSEN, appInfo);
			}
		});
	}
	
	public void setTabAppSize(int size) {
     	((IndicatorFragmentActivity) activity).setTabTitle(MainActivity.CHOSEN, "已选择应用(" + size + ")");
     	
	}

	@Override
	public boolean remove(AppInfo appInfo){
		if(appInfo == null){
			return false;
		}
		if(mlistAppInfo.remove(appInfo) == true){
			setTabAppSize(mlistAppInfo.size() );
			notifyDataSetChanged();
			appInfoSet.remove(appInfo.getPkgName());
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void removeAll(List<AppInfo> apps){
		mlistAppInfo.removeAll(apps);
		appInfoSet.removeSets(apps);
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
			appInfoSet.add(appInfo.getPkgName(), appInfo.getChecked());
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public void addAll(List<AppInfo> apps){
		mlistAppInfo.addAll(apps);
		appInfoSet.addSets(apps);
		notifyDataSetChanged();
		setTabAppSize(mlistAppInfo.size());
	}
	
//	@Override
	public void storeCheckChanged(int postion, Boolean checked){
		AppInfoLocation aisc = AppInfoLocation.instance();
		Map<String, Boolean> pSet = aisc.getAllData();
		String pkgName = mlistAppInfo.get(postion).getPkgName();
		AppInfoLocation.instance().setValus(pkgName, checked);
	}

	@Override
	public void checkBoxChanged(CheckBox checkBox) {
		// TODO Auto-generated method stub
		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				View convertview = (View) buttonView.getParent();
				ViewHolder holder = (ViewHolder) convertview.getTag();
				int position = holder.position;
				storeCheckChanged(position, isChecked);
			}
		});
	}
}
