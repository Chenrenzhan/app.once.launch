package com.app.oncelaunch.appinfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.app.oncelaunch.util.ContextUtil;

import android.content.Context;
import android.content.SharedPreferences;

public class AppInfoLocation {
//	public final static String Shared_Preferences_Name = "AppInfoSetChosen";
	
	private Context context;
	
	private String SharedPreferencesName;
	
	private static AppInfoLocation appInfoSet = new AppInfoLocation();
	
	private SharedPreferences preferences;  
    private SharedPreferences.Editor editor;  
    
//    private  List<AppInfo> listAppInfo;
    
    private Map<String, Boolean> pSet;
    
    private AppInfoLocation(){
    	this.context = ContextUtil.getInstance();
    	
    	SharedPreferencesName = context.getPackageName();
    	
    	preferences = context.getSharedPreferences(SharedPreferencesName, context.MODE_PRIVATE);
    	editor = preferences.edit();
    	
    	if(pSet == null){
    		pSet = new HashMap<String, Boolean>();
    	}
//    	pSet = (Map<String, Boolean>) preferences.getAll();
    }
	
    public static AppInfoLocation instance(){
    	
    	return appInfoSet;
    }
    
    public Map<String, Boolean> getAllData(){
    	
    	if(pSet.isEmpty()){
    		pSet = (Map<String, Boolean>) preferences.getAll();
    	}
    	return pSet;
    }
    
    public Boolean getStatus(String pkgName){
    	return preferences.getBoolean(pkgName, false);
    }
    
    public void addSets(Map<String, Boolean> infoMap){
    	for(Entry<String, Boolean> entry : infoMap.entrySet()){
    		editor.putBoolean(entry.getKey(), entry.getValue());
    	}
    	editor.commit();
    }
    public void addSets(List<AppInfo> listAppInfo){
    	for(AppInfo appInfo : listAppInfo){
    		editor.putBoolean(appInfo.getPkgName(), true);
    	}
    	editor.commit();
    }
    public void add(String pkgName, Boolean status){
    	editor.putBoolean(pkgName, status);
    	editor.commit();
    }
    
    public void add(String pkgName){
    	add(pkgName, true);
    }
    
    public void remove(String pkgName ){
    	editor.remove(pkgName);
    	editor.commit();
    }
    
    public void removeSets(List<AppInfo> listAppInfo){
    	for(AppInfo appInfo : listAppInfo){
    		remove(appInfo.getPkgName());
    	}
    }
    
    public int getCount(){
    	if(pSet.isEmpty()){
    		pSet = (Map<String, Boolean>) preferences.getAll();
    	}
    	return pSet.size();
    }

	public String getSharedPreferencesName() {
		return SharedPreferencesName;
	}
	
	public Boolean setValus(String pkgName, Boolean status){
		
		if(preferences.contains(pkgName)){
			editor.putBoolean(pkgName, status);
			editor.commit();
			return pSet.put(pkgName, status);
		}
		else{
			return false;
		}
			
	}
}
