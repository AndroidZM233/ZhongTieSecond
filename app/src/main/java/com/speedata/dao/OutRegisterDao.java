package com.speedata.dao;

import android.content.Context;
import com.speedata.bean.OutRegister;
import com.speedata.helper.DBInsideHelper;
import com.elsw.base.db.orm.dao.ABaseDao;


public class OutRegisterDao extends ABaseDao<OutRegister> {
	public OutRegisterDao(Context context) {
		super(new DBInsideHelper(context),  OutRegister.class);
	}
}
