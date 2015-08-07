package com.app.oncelaunch.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oncelaunch.MainActivity;
import com.app.oncelaunch.R;
import com.app.oncelaunch.appinfo.AppInfo;
import com.app.oncelaunch.appinfo.QueryAppInfo;
import com.app.oncelaunch.appinfo.AppInfoLocation;
import com.app.oncelaunch.ui.IndicatorFragmentActivity;

public class FragmentPageChosen extends Fragment{

private String TAG = FragmentPageChoose.class.getName();  
	
	private final static int VIEW_LAYOUT_ID = R.layout.layout_app_list;
	private final static int LIST_VIEW__ID = android.R.id.list;
	
	private Context context;
	private Activity activity;
	
	private View rootView;
    private ListView listView ;  
    private Adapter adapter;  
//    private QueryAppInfo queryAppInfo;
    private QueryAppInfo appInfoList;
    private List<AppInfo> listAppInfo;
    
    private ProgressBar progressBar;  
    private LinearLayout ll_loading;
    
    private TextView tvOperator;
    private CheckBox checkedAll;
    
    private int selectCount;
    
    private static Vector<Intent> vectorIntent;
    
    private CallBack callback;
    
    public FragmentPageChosen(){
    	super();
    	selectCount = 0;
    	
    	callback = new CheckCallBack();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
        this.activity = activity;
        
        if(listAppInfo == null){
    		listAppInfo = new ArrayList<AppInfo>();
    		
    		LoadAppInfoTask asyncTask = new LoadAppInfoTask();  
            asyncTask.execute(); 
        	
    	}
    }
    
    private class LoadAppInfoTask extends AsyncTask<String, Integer, String> {  
        //onPreExecute方法用于在执行后台任务前做一些UI操作  
        @Override  
        protected void onPreExecute() {  
//            ll_loading.setVisibility(View.VISIBLE);
        }  
          
        //doInBackground方法内部执行后台任务,不可在此方法内修改UI  
        @Override  
        protected String doInBackground(String... params) {  
        	appInfoList = QueryAppInfo.instance();
			  
            return null;  
        }  
          
        //onProgressUpdate方法用于更新进度信息  
        @Override  
        protected void onProgressUpdate(Integer... progresses) {  
            Log.i(TAG, "onProgressUpdate(Progress... progresses) called");  
//            progressBar.setProgress(progresses[0]);  
            Log.e("mydebug", "*********************onProgressUpdate " + progresses[0]);
//            textView.setText("loading..." + progresses[0] + "%");  
        }  
          
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果  
        @Override  
        protected void onPostExecute(String result) {  
//        	progressBar.setVisibility(View.GONE);
        	ll_loading.setVisibility(View.INVISIBLE);
        	initListView(); //用ListView显示数据
        }  
          
        //onCancelled方法用于在取消执行中的任务时更改UI  
        @Override  
        protected void onCancelled() {  
            
        }  
    }  
    
	  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		  
		  
		  if (null != rootView) {
	            ViewGroup parent = (ViewGroup) rootView.getParent();
	            if (null != parent) {
	                parent.removeView(rootView);
	            }
	        } else {
	        	rootView = inflater.inflate(R.layout.layout_app_list, null);
	        	initView(rootView);// 控件初始化
	        }
		  
	        return rootView;
	} 
	  
	  public void initView(View rootView){
//		  progressBar = (ProgressBar)rootView.findViewById(R.id.pb);
		  ll_loading = (LinearLayout) rootView.findViewById(R.id.ll_loading);
		ll_loading.setVisibility(View.VISIBLE);

		tvOperator = (TextView) rootView
				.findViewById(R.id.operator_text);
//		tvOperator.setText("一键开启");
//		selectCount = 0;
//		for(AppInfo appInfo : listAppInfo){
//			Log.e("mydebug", "************* app info checked is " + appInfo.getChecked());
//			if(appInfo.getChecked()){
//				Log.e("mydebug", "************* app info checked is true");
//				selectCount++;
//			}
//		}
		setSelectCountText(selectCount);

	}

	public void initListView() {
		listView = (ListView) rootView.findViewById(R.id.list);
		listView.setVisibility(View.VISIBLE);
		
		appInfoList.sortInfo();
		listAppInfo = appInfoList.getListAppInfoChosen();
		adapter = AdapterAgent.createAdapter(MainActivity.CHOSEN,
				context, activity, listAppInfo, R.layout.layout_app_item_chosen, callback);
		listView.setAdapter((ListAdapter) adapter);
		setTabAppSize(listAppInfo.size());
		((BaseAdapter) adapter).notifyDataSetChanged();
		
		selectCount = 0;
		for(AppInfo appInfo : listAppInfo){
			if(appInfo.getChecked()){
				selectCount++;
			}
		}
		setSelectCountText(selectCount);

		listviewOnItemClick(listView);

		onStartLayoutClick(rootView);

		onCheckedAllClick(rootView);
	}
	
	public void setTabAppSize(int size) {
     	((IndicatorFragmentActivity) activity).setTabTitle(MainActivity.CHOSEN, "已选择(" + size + ")");
     	
	}

	/**
	 * @param listview
	 */
	public void listviewOnItemClick(ListView listview) {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				CheckBox checkbox = (CheckBox)view.findViewById(R.id.check_box);
//				checkbox.set
				Boolean checked = checkbox.isChecked();
				listAppInfo.get(position).setChecked(!checked);
				checkbox.setChecked( !checked);
//				storeCheckChanged(position, !checked);
				Log.e("mydebug", "========  item click check " + checked);
				if(!checked){
					selectCount++;
					selectCount = (selectCount > listAppInfo.size()) ? listAppInfo.size() : selectCount;
				}
				else{
					selectCount--;
					selectCount = (selectCount < 0) ? 0 : selectCount;
				}
				selectCountChanged();
			}
		});
	}
	public void setSelectCountText(int count){
		tvOperator.setText("一键启动(" + count + ")");
	}
	public void selectCountChanged() {
		if(selectCount > listAppInfo.size()){
			selectCount = listAppInfo.size();
		}
		else if(selectCount < 0){
			selectCount = 0;
		}
		if(selectCount == listAppInfo.size()){
			checkedAll.setChecked(true);
		}
		else{
			checkedAll.setChecked(false);
		}
		setSelectCountText(selectCount);
	}

	public void onStartLayoutClick(View rootView) {
		RelativeLayout layoutOperator = (RelativeLayout) rootView
				.findViewById(R.id.operator_layout);
		layoutOperator.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if(selectCount <= 0){
					Toast.makeText(context, "您未选择应用程序！", Toast.LENGTH_LONG).show();
					return ;
				}
				
				vectorIntent = new Vector<Intent>();
				for(AppInfo appInfo : listAppInfo){
					if(appInfo.getChecked()){
						Log.e("mydebug", "---- " + appInfo.getAppLabel());
						vectorIntent.add(appInfo.getIntent());
					}
				}
				if(vectorIntent.size() > 0){
					Intent intent = vectorIntent.remove(0);
					if(intent != null){
						context.startActivity(intent); 
					}
				}
			}
		});
	}
	
	public void onCheckedAllClick(View rootView){
		checkedAll = (CheckBox)rootView.findViewById(R.id.checkbox_all);
		Boolean checked = checkedAll.isChecked();
		checkedAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox checkbox = (CheckBox)v;
				Boolean isChecked = checkbox.isChecked();
				
//				for(AppInfo appInfo : listAppInfo){
//					appInfo.setChecked(isChecked);
//				}
				for(int i = 0; i < listAppInfo.size(); ++i){
					listAppInfo.get(i).setChecked(isChecked);
					storeCheckChanged(i, isChecked);
				}
				
				((BaseAdapter) adapter).notifyDataSetChanged();
				selectCount = isChecked ? listAppInfo.size() : 0;
				selectCountChanged();
			}
		});
		
	}
	
	@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  

	    }  
	      
	    @Override  
	    public void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);  
	        Log.i(TAG, "--------onActivityCreated");  
	  
	    }  
	    
	    public class CheckCallBack implements CallBack{
	    	

			@Override
			public void checkedChanged(Boolean checked) {
				// TODO Auto-generated method stub
				if(checked){
	    			selectCount++;
	    		}
	    		else{
	    			selectCount--;
	    		}
				selectCountChanged();
			}
	    }
	    
	    public void storeCheckChanged(int postion, Boolean checked){
			AppInfoLocation aisc = AppInfoLocation.instance();
			Map<String, Boolean> pSet = aisc.getAllData();
			String pkgName = listAppInfo.get(postion).getPkgName();
			AppInfoLocation.instance().setValus(pkgName, checked);
		}
	    
	    public static List<Intent> getIntent(){
	    	
	    	return vectorIntent;
	    }
}