package com.speedata.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Id;
import com.elsw.base.db.orm.annotation.Table;

@Table(name = "checkForm")
public class CheckForm {


    @Id
    @Column(name = "CheckNM")
    // 内码
    private String CheckNM;

    @Column(name = "ProjectID")
    // 项目编码
    private String ProjectID;

    @Column(name = "CheckMonth")
    // 盘点月份
    private String CheckMonth;

    @Column(name = "InfoCode")
    // 材料编码
    private String InfoCode;

    @Column(name = "InfoName")
    // 材料名称
    private String InfoName;

    @Column(name = "InfoModel")
    // 规格型号
    private String InfoModel;

    @Column(name = "InfoUnit")
    // 单位
    private String InfoUnit;

    @Column(name = "Quantity")
    // 盘点数量
    private double Quantity;

    @Column(name = "BarCode")
    // 条码
    private String BarCode;

    @Column(name = "InfoClassNodebh")
    // 类别编码
    private String InfoClassNodebh;

    @Column(name = "InfoClassName")
    // 三级类别
    private String InfoClassName;

    @Column(name = "SecondClassName")
    // 二级类别
    private String SecondClassName;

    @Column(name = "FirstClassName")
    // 一级类别
    private String FirstClassName;

    @Column(name = "Remark")
    //
    private String Remark;

    public String getCheckNM() {
        return CheckNM;
    }

    public void setCheckNM(String checkNM) {
        CheckNM = checkNM;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getCheckMonth() {
        return CheckMonth;
    }

    public void setCheckMonth(String checkMonth) {
        CheckMonth = checkMonth;
    }

    public String getInfoCode() {
        return InfoCode;
    }

    public void setInfoCode(String infoCode) {
        InfoCode = infoCode;
    }

    public String getInfoName() {
        return InfoName;
    }

    public void setInfoName(String infoName) {
        InfoName = infoName;
    }

    public String getInfoModel() {
        return InfoModel;
    }

    public void setInfoModel(String infoModel) {
        InfoModel = infoModel;
    }

    public String getInfoUnit() {
        return InfoUnit;
    }

    public void setInfoUnit(String infoUnit) {
        InfoUnit = infoUnit;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getInfoClassNodebh() {
        return InfoClassNodebh;
    }

    public void setInfoClassNodebh(String infoClassNodebh) {
        InfoClassNodebh = infoClassNodebh;
    }

    public String getInfoClassName() {
        return InfoClassName;
    }

    public void setInfoClassName(String infoClassName) {
        InfoClassName = infoClassName;
    }

    public String getSecondClassName() {
        return SecondClassName;
    }

    public void setSecondClassName(String secondClassName) {
        SecondClassName = secondClassName;
    }

    public String getFirstClassName() {
        return FirstClassName;
    }

    public void setFirstClassName(String firstClassName) {
        FirstClassName = firstClassName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
