package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

//用料方信息表

@Table(name = "outRegister")
public class OutRegister extends StoreBean{



    @Column(name = "Maker")
    // 制单人
    private String Maker;

    @Column(name = "Remark")
    // 备注
    private String Remark;



    public String getMaker() {
        return Maker;
    }

    public void setMaker(String maker) {
        Maker = maker;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
