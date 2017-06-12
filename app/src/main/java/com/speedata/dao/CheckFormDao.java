package com.speedata.dao;

import android.content.Context;

import com.speedata.bean.CheckForm;
import com.speedata.helper.DBInsideHelper;
import com.elsw.base.db.orm.dao.ABaseDao;
public class CheckFormDao extends ABaseDao<CheckForm> {
	public CheckFormDao(Context context) {
		super(new DBInsideHelper(context),  CheckForm.class);
	}
}
