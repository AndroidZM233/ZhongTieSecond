package com.speedata.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 存取xml->用户id以及是否第一次进入
 * */
public class ShareUtils {

	private Context ctx;
	private String name = "my";

	//login->用户是否第一次进入key
	public ShareUtils( Context ctx){
		this.ctx = ctx;
	}

	//xml中存入方法
	public void writeXml(String Key,String Value){
		SharedPreferences share = ctx.getSharedPreferences(name, 0);
		Editor edt = share.edit();//获取编辑器
		edt.putString(Key, Value);
		edt.commit();//提交
	}
	//xml中获取方法
	public String readXml(String key){
		SharedPreferences share = ctx.getSharedPreferences(name, 0);
		return share.getString(key, "-1");
	}
}
