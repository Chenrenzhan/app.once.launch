package com.app.oncelaunch.appinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;

//Model类 ，用来存储应用程序信息	
public class AppInfo {
	
	public final static Boolean CHOOSE = false;
	public final static Boolean CHOSEN = true;

	private String appLabel; // 应用程序标签
	private Drawable appIcon; // 应用程序图像
	private Intent intent; // 启动应用程序的Intent
							// ，一般是Action为Main和Category为Lancher的Activity
	private String pkgName; // 应用程序所对应的 包名
	private long appsize; //应用程序大小，单位为B
	private String appsizeInfo; //应用程序大小的字符串，比如1.5M
	
	private Boolean checked; //是否选中
	
	private Boolean blChoice; //是否已选择
	
	public AppInfo(){
		appsize = 0;
		checked = false;
		blChoice = CHOOSE;
	}
	
	public Boolean getChecked() {
		if(checked == null){
			checked = false;
		}
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public long getAppsize() {
		return appsize;
	}

	public void setAppsize(long appsize) {
		this.appsize = appsize;
	}

	@SuppressLint("NewApi")
	public String getAppsizeInfo() {
		if(appsizeInfo == null || appsizeInfo.isEmpty()){
			return "";
		}
		return appsizeInfo;
	}

	public void setAppsizeInfo(String appsizeInfo) {
		this.appsizeInfo = appsizeInfo;
	}

	

	public String getAppLabel() {
		return appLabel;
	}

	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public Boolean getBlChoice() {
		return blChoice;
	}

	public void setBlChoice(Boolean blChoice) {
		this.blChoice = blChoice;
	}
}
