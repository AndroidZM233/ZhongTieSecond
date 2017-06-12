package com.speedata.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.speedata.bean.AllMetrailClass;
import com.speedata.bean.CommonForm;
import com.speedata.bean.CommonInformation;
import com.speedata.bean.CommonSupplier;

public class JSONUtils {

	private final String TAG = "JSONUtils";

	// 材料类别解析
	public List<AllMetrailClass> getSupperList(String result) {
		List<AllMetrailClass> list = new ArrayList<AllMetrailClass>();
		if (result == null)
			return list;
		JSONObject parse1 = JSON.parseObject(result);
		String Results = parse1.getString("GetAllMaterialClassResult");
		Log.d(TAG, "------JSONUtils" + Results);
		// JSONObject jobs = new JSONObject(result.toString());
//		JSON jsonString = JSON.parseObject(result.toString());
		list = JSON.parseArray(Results, AllMetrailClass.class);
		return list;
	}

	// 供应商信息解析
	public List<CommonSupplier> getSupperFactory(String result) {
		List<CommonSupplier> list = new ArrayList<CommonSupplier>();
		if (result == null) {
			return list;
		} else {
			// 怎样使用JSON将字符串解析成JSON？parseObject(String)
			JSON josnString = (JSON) JSON.parse(result);

			// 将JSON强转成Bean对象
			CommonSupplier javaObject = (CommonSupplier) JSON.toJavaObject(
					josnString, CommonSupplier.class);
			list.add(javaObject);
			// 将bean类对象转成String:当我向数据库提交数据时使用
			// String str = JSON.toJSONString(javaObject);

		}
		return list;
	}

	// 材料类别信息解析
	public List<CommonForm> getMetrailCategory(String result) {
		List<CommonForm> list = new ArrayList<CommonForm>();
		if (result == null) {
			return list;
		} else {
			// 怎样使用JSON将字符串解析成JSON？parseObject(String)
			JSON jsonString = JSON.parseObject(result);
			// 将JSON强转成Bean对象
			CommonForm javaObject = (CommonForm) JSON.toJavaObject(jsonString,
					CommonForm.class);
			list.add(javaObject);
			// 将bean类对象转成String:当我想数据库提交数据时使用
			// String str = JSON.toJSONString(javaObject);

		}
		return list;
	}

	// 材料信息解析
	public List<CommonInformation> getMetrailInfomation(String result) {
		List<CommonInformation> list = new ArrayList<CommonInformation>();
		if (result == null) {
			return list;
		} else {
			// 怎样使用JSON将字符串解析成JSON？parseObject(String)
			JSON jsonString = JSON.parseObject(result);
			// 将JSON强转成Bean对象
			CommonInformation javaObject = (CommonInformation) JSON
					.toJavaObject(jsonString, CommonInformation.class);
			list.add(javaObject);
			// 将bean类对象转成String:当我想数据库提交数据时使用
			// String str = JSON.toJSONString(javaObject);

		}
		return list;
	}

}
