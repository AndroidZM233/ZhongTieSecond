package com.speedata.dao;

import android.content.Context;

import com.elsw.base.db.orm.dao.ABaseDao;
import com.speedata.bean.CommonLabour;
import com.speedata.helper.DBInsideHelper;

/** 施工队DAO
 * Created by Administrator on 2016/3/3.
 */
public class CommonLabourDao extends ABaseDao<CommonLabour> {
    public CommonLabourDao(Context context) {
        super(new DBInsideHelper(context),   CommonLabour.class);
    }
}
