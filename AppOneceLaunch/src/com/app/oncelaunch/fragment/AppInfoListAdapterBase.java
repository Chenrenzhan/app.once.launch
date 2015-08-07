package com.app.oncelaunch.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oncelaunch.R;
import com.app.oncelaunch.appinfo.AppInfo;
import com.app.oncelaunch.fragment.FragmentPageChoose.CheckCallBack;

//自定义适配器类，提供给listView的自定义view
abstract public class  AppInfoListAdapterBase extends BaseAdapter {
	
	protected Context context;
	protected Activity activity;
	
	protected int itemLayoutId;
	
	protected List<AppInfo> mlistAppInfo = null;
	
	protected LayoutInflater infater = null;
	
	protected CallBack callback;
    
	public AppInfoListAdapterBase(Context context,  Activity activity, 
			List<AppInfo> apps, int itemLayoutId, CallBack callback) {
		this.context = context;
		this.activity = activity;
		infater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mlistAppInfo = apps ;
		
		this.itemLayoutId = itemLayoutId;
		
		this.callback = callback;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("size" + mlistAppInfo.size());
		return mlistAppInfo.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlistAppInfo.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		System.out.println("getView at " + position);
		View view = null;
		ViewHolder holder = null;
		if (convertview == null || convertview.getTag() == null) {
			view = infater.inflate(itemLayoutId, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} 
		else{
			view = convertview ;
			holder = (ViewHolder) convertview.getTag() ;
		}
		final AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.tvAppLabel.setText(appInfo.getAppLabel());
		holder.appsizeInfo.setText(appInfo.getAppsizeInfo());
		holder.checkBox.setChecked(appInfo.getChecked());
		
		holder.position = position;
		
		btnStartClick(holder.btnStart, appInfo);
		
		btnOperatorClick(holder.btnOperator, appInfo);
		
		checkBoxClick(holder.checkBox);
		
		checkBoxChanged(holder.checkBox);
		
		return view;
	}
	
	public abstract void btnOperatorClick(ImageButton btnOperator, final AppInfo appInfo);
	
	public abstract void checkBoxChanged(CheckBox checkBox);
	
	/**
	 * @param btnSart
	 * @param appInfo
	 */
	public void btnStartClick(ImageButton btnSart, final AppInfo appInfo) {
		btnSart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = appInfo.getIntent();  
				if(intent != null){
					context.startActivity(intent); 
				}
				else{
					Toast.makeText(context, "应用不可启动", Toast.LENGTH_LONG);
				}
				
			}
		});
	}
	
	public void checkBoxClick(CheckBox checkBox){
		checkBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean isChecked = ((CheckBox) v).isChecked();
				callback.checkedChanged(isChecked);
				View convertview = (View) v.getParent();
				ViewHolder holder = (ViewHolder) convertview.getTag();
				int position = holder.position;
				Log.e("mydebug", "++++++++++  check boc click position" + position ) ;
				mlistAppInfo.get(position).setChecked(isChecked);
//				storeCheckChanged(position, isChecked);
			}
		});
	}
	
//	public abstract void storeCheckChanged(int possotion, Boolean checked);

	public abstract void setTabAppSize(int size);
	public abstract boolean remove(AppInfo appInfo);
	public abstract void removeAll(List<AppInfo> apps);
	public abstract boolean add(AppInfo appInfo);
	public abstract void addAll(List<AppInfo> apps);

	class ViewHolder {
		ImageView appIcon;
		TextView tvAppLabel;
		TextView appsizeInfo;
		CheckBox checkBox;
		ImageButton btnStart;
		ImageButton btnOperator;
		
		int position;

		public ViewHolder(View view) {
			this.appIcon = (ImageView) view.findViewById(R.id.app_icon);
			this.tvAppLabel = (TextView) view.findViewById(R.id.app_label);
			this.appsizeInfo = (TextView) view.findViewById(R.id.app_size_info);
			this.btnStart = (ImageButton)view.findViewById(R.id.btn_start);
			this.checkBox = (CheckBox)view.findViewById(R.id.check_box);
			this.btnOperator = (ImageButton)view.findViewById(R.id.btn_operator);
		}
	}
}