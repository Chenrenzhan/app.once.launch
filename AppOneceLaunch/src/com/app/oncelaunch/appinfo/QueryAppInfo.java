package com.app.oncelaunch.appinfo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;

import com.app.oncelaunch.util.ContextUtil;

//单例类
public class QueryAppInfo {
	private Context context;
	
	private AppInfoLocation appInfoSet;
	
	private  List<AppInfo> listAppInfo;
	private List<AppInfo> listAppInfoChoose;
	private List<AppInfo> listAppInfoChosen;
	
	private static QueryAppInfo appInfoList = new QueryAppInfo();;
	
	//私有构造函数
	private QueryAppInfo(){
		this.context = ContextUtil.getInstance();
		this.listAppInfo = null;
		this.listAppInfo = new ArrayList<AppInfo>();
		
		
		
		queryAppInfo();
		
//		sortInfo();
	}

	public static QueryAppInfo instance(){
//		appInfoList = new AppInfoList();
		return appInfoList;
	}
	
	public  List<AppInfo> update(){
//		new AppInfoList();
		listAppInfo = null;
		
		listAppInfo = new ArrayList<AppInfo>();
		
		queryAppInfo();
		
		return listAppInfo;
	}
	
	public void sortInfo(){
		listAppInfoChoose = null;
		listAppInfoChoose = new ArrayList<AppInfo>();
		
		listAppInfoChosen = null;
		listAppInfoChosen = new ArrayList<AppInfo>();
		
		Map<String, Boolean> pSet = AppInfoLocation.instance().getAllData();
		for(AppInfo appInfo : listAppInfo){
			String pkgName = appInfo.getPkgName();
			if(pSet.containsKey(pkgName)){
				Boolean checked = pSet.get(pkgName);
				appInfo.setChecked(checked);
				appInfo.setBlChoice(AppInfo.CHOSEN);
				listAppInfoChosen.add(appInfo);
				continue;
			}
			else{
				listAppInfoChoose.add(appInfo);
			}
		}
	}
	
	// 获得所有启动Activity的信息
    public void queryAppInfo() {  
    	
        PackageManager pm = context.getPackageManager(); // 获得PackageManager对象  
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);  
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
        
        // 通过查询，获得所有ResolveInfo对象.  
        List<ResolveInfo> resolveInfos = pm  
                .queryIntentActivities(mainIntent, PackageManager.GET_ACTIVITIES);  
        
        
        // 调用系统排序 ， 根据name排序  
        // 该排序很重要，否则只能显示系统应用，而不能列出第三方应用程序  
        Collections.sort(resolveInfos,new ResolveInfo.DisplayNameComparator(pm));  
        if (listAppInfo != null) {  
            listAppInfo.clear();  
            
            for (ResolveInfo reInfo : resolveInfos) {  
                String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name  
               final  String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名  
                String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label  
                Drawable icon = reInfo.loadIcon(pm); // 获得应用程序图标  
                
                try {
					queryPacakgeSize(pkgName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                // 为应用程序的启动Activity 准备Intent  
//                Intent launchIntent = new Intent();  
                Intent launchIntent = new Intent(Intent.ACTION_VIEW);  
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
                launchIntent.setComponent(new ComponentName(pkgName,  
                        activityName));  
                
                // 创建一个AppInfo对象，并赋值  
                AppInfo appInfo = new AppInfo();  
                appInfo.setAppLabel(appLabel);  
                appInfo.setPkgName(pkgName);  
                appInfo.setAppIcon(icon);  
                appInfo.setIntent(launchIntent);  
                listAppInfo.add(appInfo); // 添加至列表中  
            }  
        }  
    }
    public synchronized void  queryPacakgeSize(String pkgName) throws Exception{
    	if ( pkgName != null){
    		//使用反射机制得到PackageManager类的隐藏函数getPackageSizeInfo
    		PackageManager pm = context.getPackageManager();  //得到pm对象
    		try {
    			//通过反射机制获得该隐藏函数
				Method getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
			    //调用该函数，并且给其分配参数 ，待调用流程完成后会回调PkgSizeObserver类的函数
				//注意：此次为异步回调，即PkgSizeObserver.onGetStatsCompleted在多线程里执行
			    getPackageSizeInfo.invoke(pm, pkgName, new PkgSizeObserver());
			} 
        	catch(Exception ex){
        		ex.printStackTrace() ;
        		throw ex ;  // 抛出异常
        	} 
    	}
    }
    //aidl文件形成的Bindler机制服务类
    public class PkgSizeObserver extends IPackageStatsObserver.Stub{
        /*** 回调函数，
         * @param pStatus ,返回数据封装在PackageStats对象中
         * @param succeeded  代表回调成功
         */ 
		@Override
		public synchronized void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			addAppSize(pStats);
		}

		/**
		 * @param pStats
		 */
		public synchronized void addAppSize(PackageStats pStats) {
			AppInfo appInfo = findAppInfo(pStats.packageName);
			if(appInfo != null){
				long cachesize = pStats.cacheSize  ; //缓存大小
			    long datasize = pStats.dataSize  ;  //数据大小 
			    long codesize =	pStats.codeSize  ;  //应用程序大小
			    long appTotalSize = cachesize + datasize + codesize ;
			    appInfo.setAppsize(appTotalSize);
			    appInfo.setAppsizeInfo(formateFileSize(appTotalSize));
			}
		}
    }
    //系统函数，字符串转换 long -String (kb)
    @SuppressLint("NewApi")
	private String formateFileSize(long size){
    	return Formatter.formatFileSize(context, size); 
    }
    
    public synchronized AppInfo findAppInfo(String pkgName){
    	for(AppInfo appInfo : listAppInfo){
    		if(appInfo.getAppsize() == 0 && appInfo.getPkgName().equals(pkgName)){
    			return appInfo;
    		}
    	}
    	return null;
    }

	public List<AppInfo> getListAppInfo() {
		if(listAppInfo == null){
			listAppInfo = new ArrayList<AppInfo>();
		}
		return listAppInfo;
	}

	public void setListAppInfo(List<AppInfo> listAppInfo) {
		this.listAppInfo = listAppInfo;
	}
	
	public List<AppInfo> getListAppInfoChoose() {
		return listAppInfoChoose;
	}

	public List<AppInfo> getListAppInfoChosen() {
		return listAppInfoChosen;
	}
	
	public void setListAppInfoChoose(List<AppInfo> listAppInfoChoose) {
		this.listAppInfoChoose = listAppInfoChoose;
	}

	public void setListAppInfoChosen(List<AppInfo> listAppInfoChosen) {
		this.listAppInfoChosen = listAppInfoChosen;
	}
}
