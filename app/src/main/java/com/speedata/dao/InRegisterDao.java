package com.speedata.dao;

import android.content.Context;

import com.speedata.bean.InRegister;
import com.speedata.helper.DBInsideHelper;
import com.elsw.base.db.orm.dao.ABaseDao;


public class InRegisterDao extends ABaseDao<InRegister> {
	public InRegisterDao(Context context) {
		super(new DBInsideHelper(context),   InRegister.class);
	}
}
