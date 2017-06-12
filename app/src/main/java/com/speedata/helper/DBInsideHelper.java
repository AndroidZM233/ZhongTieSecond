package com.speedata.helper;

import android.content.Context;

import com.elsw.base.db.orm.AbDBHelper;
import com.speedata.bean.AllotRecord;
import com.speedata.bean.CheckForm;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.InRegister;
import com.speedata.bean.OutRegister;
import com.speedata.bean.StoreBean;
import com.speedata.bean.UserForm;

/**
 * Copyright (c) 2012 All rights reserved 名称：DBInsideHelper.java
 * 描述：手机data/data下面的数据库
 *
 * @author zhaoqp
 * @version v1.0
 * @date�?013-7-31 下午3:50:18O
 */
public class DBInsideHelper extends AbDBHelper {
    // 数据库名
    private static final String DBNAME = "zhongtiesecond.db";

    // 当前数据库的版本
    private static final int DBVERSION = 5;
    // 要初始化的表
    private static final Class<?>[] clazz = {OutRegister.class,
            InRegister.class, CheckForm.class,
            UserForm.class, AllotRecord.class,
            StoreBean.class, CommonSupplier.class, CommonLabour.class, CommonMaterialInfo.class};

    public DBInsideHelper(Context context) {
        super(context, DBNAME, null, DBVERSION, clazz);
    }

}
