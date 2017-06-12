package com.speedata.dao;

import android.content.Context;

import com.speedata.bean.CheckForm;
import com.speedata.helper.DBInsideHelper;
import com.speedata.info.BaseInformation;
import com.elsw.base.db.orm.dao.ABaseDao;


public class BaseInformationDao extends ABaseDao<BaseInformation> {
	public BaseInformationDao(Context context) {
		super(new DBInsideHelper(context),  BaseInformation.class);
	}
}
