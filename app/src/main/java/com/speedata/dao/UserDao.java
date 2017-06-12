package com.speedata.dao;

import android.content.Context;


import com.speedata.bean.UserForm;
import com.speedata.helper.DBInsideHelper;
import com.elsw.base.db.orm.dao.ABaseDao;

public class UserDao extends ABaseDao< UserForm> {
	public UserDao(Context context) {
		super(new DBInsideHelper(context), UserForm.class);
	}
}
