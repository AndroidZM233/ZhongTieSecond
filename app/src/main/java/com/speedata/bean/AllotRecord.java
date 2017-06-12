package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

//调拨信息表

@Table(name = "allotRecord")
public class AllotRecord extends StoreBean{


    @Column(name = "OrderDate")
    // 发料日期
    private String OrderDate;

    @Column(name = "OrderTime")
    // 发料时间
    private String OrderTime;

    @Column(name = "LabourNM")
    // 施工队内码
    private String LabourNM;

    @Column(name = "LabourName")
    // 施工队名称
    private String LabourName;

    @Column(name = "Maker")
    // 制单人
    private String Maker;

    @Column(name = "Remark")
    // 备注
    private String Remark;


    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getLabourNM() {
        return LabourNM;
    }

    public void setLabourNM(String labourNM) {
        LabourNM = labourNM;
    }

    public String getLabourName() {
        return LabourName;
    }

    public void setLabourName(String labourName) {
        LabourName = labourName;
    }

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
