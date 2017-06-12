package com.speedata.helper;

import java.io.File;
import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelpdemo extends SQLiteOpenHelper{
	private SQLiteDatabase db = null;
	/**
	 * 数据库名称
	 **/
	public static String DATABASE_NAME = "SMS.db";

	/**
	 * 数据库版本
	 **/
	public static int DATABASE_VERSION = 1;
	/*
	 * 创建语句
	 */
	// 创建用户表
	String createUserTable = "create table user_info(_id int auto_increment,userid char(20),username char(20),"
			+ "password char(20), property int,primary key('userid'));";
	// 创建商品表
	String creatProductsTable = "create Table products(_id int auto_increment,"+
			"product char(40),barcode char(50),spec char(20),unit char(20)," +
			"reserve1 char(20),reserve2 char(20),reserve3 char(20)," +
			"primary key('barcode'));";

	//创建出库表
	String creatInstoreTable = "create table instore(_id int auto_increment,"+
			"product char(40),barcode char(50),spec char(20), count int," +
			"unit char(20),date char(40),reserve1 char(20),reserve2 char(20)," +
			"reserve3 char(20),primary key('_id'));";
	//创建入库表
	String creatOutstoreTable = "create table outstore(_id int auto_increment,"+
			"product char(40),barcode char(50),spec char(20), count int," +
			"unit char(20),date char(40),reserve1 char(20),reserve2 char(20)," +
			"reserve3 char(20),primary key('_id'));";

	//创建库存表
	String createStore = "create Table store(_id int auto_increment,"+
			"product char(40),barcode char(50),spec char(20), count int," +
			"unit char(20),reserve1 char(20),reserve2 char(20)," +
			"reserve3 char(20),primary key('barcode'));";
	// 定义用户表插入语句
	String insertUser = "insert into user_info(_id,userid,username,password,property) values(?,?,?,?,?)";
	// 定义商品表插入语句
	String insertProducts = "insert into products(product,barcode,spec,unit) values(?,?,?,?);";
	//定义入库表插入语句
	String insertInstore = "insert into instore(product,barcode,spec,count,unit,date) values(?,?,?,?,?,?);";
	//定义出库表插入语句
	String insertOutstore = "insert into outstore(product,barcode,spec,count,unit,date) values(?,?,?,?,?,?);";
	//定义库存表插入语句
	String insertStore = "insert into store(product,barcode,spec,count,unit) values(?,?,?,?,?);";
	//创建Product的触发器，新增商品属性时候，自动插入到库存中
	/*String creatTriggerInsertProduct = "create trigger trg_insert_product " +
			"before insert on products " +
			"for each row " +
			"begin " +
			//"print'ceshi ok!'" +
			"declare @productName char(10) " +
			"declare @barcode char(10) " +
			"declare @spec char(10) " +
			"declare @unit char(10) " +
			"select @product=product,@barcode=barcode,@spec=spec,@unit=unit from inserted " +
			"insert Store (product,barcode,spec,unit) " +
			"values(@product,@barcode,@spec,@unit) " +
			"end";*/
	java.util.Date temp=new java.util.Date();

	public SqlHelpdemo(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		// 设置ID
		int _id = 0;
		// 创建用户表，用商品表，顾客表，入库表。出库表
		arg0.execSQL(createUserTable);
		arg0.execSQL(creatProductsTable);
		arg0.execSQL(creatInstoreTable);
		arg0.execSQL(creatOutstoreTable);
		arg0.execSQL(createStore);

		// 插入测试data
	/*	String[] insertValueProduct = { "商品名字", "1234567891", "规格", "单位", };
		//String[] insertValueProduct1 = { "test", "123456", "规格1", "单位1", };
		String[] insertValueUser = { "007", "123456" };
		String[] insertValueIOStore={"名称","条码","规格","数量","单位",temp.getDate()+""};
		String[] insertValueStore={"名称","条码","规格","数量","单位"};
		arg0.execSQL(insertUser, insertValueUser);
		arg0.execSQL(insertProducts, insertValueProduct);
		//arg0.execSQL(insertProducts, insertValueProduct1);
		arg0.execSQL(insertInstore, insertValueIOStore);
		arg0.execSQL(insertOutstore, insertValueIOStore);
		arg0.execSQL(insertStore, insertValueStore);
		//arg0.execSQL(creatTriggerInsertProduct);*/
		String[] insertValueUser = { "1","123", "Administrator","123456","0" };
		String[] insertValueUser1 = { "1","1", "Administrator1","1","0" };
		arg0.execSQL(insertUser, insertValueUser);
		arg0.execSQL(insertUser, insertValueUser1);
	}

	@Override
	public synchronized SQLiteDatabase getReadableDatabase() {
		// TODO Auto-generated method stub
		/*try {
			return super.getReadableDatabase();
		} catch (Exception e) {
			// TODO: handle exception
			new AlertDialog.Builder(this).setTitle(R.string.DIA_ALERT).
			setMessage(R.string.DEV_OPEN_ERR).setPositiveButton(R.string.DIA_CHECK, 
					new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).show();
        	return;
		}*/
		return super.getReadableDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub
		String[] insertValueProduct1 = { "test", "123456", "规格1", "单位1", };
		arg0.execSQL(insertProducts, insertValueProduct1);

	}

}