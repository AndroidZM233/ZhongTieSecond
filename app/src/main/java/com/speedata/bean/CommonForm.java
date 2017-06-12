package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

@Table(name = "commonForm")
public class CommonForm {

    @Column(name = "InfoID")
    // 材料ID
    private int InfoID;

    @Column(name = "ProjectID")
    // 项目编号
    private String ProjectID;

    @Column(name = "ProjectName")
    // 项目名称
    private String ProjectName;

    @Column(name = "InfoCode")
    //材料编码
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

    @Column(name = "Remark")
    // 备注信息
    private String Remark;

    public String getCKAbbName() {
        return CKAbbName;
    }

    public void setCKAbbName(String CKAbbName) {
        this.CKAbbName = CKAbbName;
    }

    private String CKAbbName;
    private String ClassNodebh;
    private String ClassName;
    private String IsFlag;
    private String DeleteFlag;
    private String IsAudit;
    private String Auditor;
    private String AuditDate;
    private String StoreRoom;
    private String IsCollection;
    private String IsTotal;
    private String AddName;//":"超级管理员",
    private String AddDate;//":"2015/11/10 10:34:43",
    private String UppdataName;
    private String UppdataDate;
    private String DelName;
    private String DelDate;
    private String ConvertParams;
    private String ConvertRemark;//":null


    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getAddName() {
        return AddName;
    }

    public void setAddName(String addName) {
        AddName = addName;
    }

    public String getAuditDate() {
        return AuditDate;
    }

    public void setAuditDate(String auditDate) {
        AuditDate = auditDate;
    }

    public String getAuditor() {
        return Auditor;
    }

    public void setAuditor(String auditor) {
        Auditor = auditor;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getClassNodebh() {
        return ClassNodebh;
    }

    public void setClassNodebh(String classNodebh) {
        ClassNodebh = classNodebh;
    }

    public String getConvertParams() {
        return ConvertParams;
    }

    public void setConvertParams(String convertParams) {
        ConvertParams = convertParams;
    }

    public String getConvertRemark() {
        return ConvertRemark;
    }

    public void setConvertRemark(String convertRemark) {
        ConvertRemark = convertRemark;
    }

    public String getDelDate() {
        return DelDate;
    }

    public void setDelDate(String delDate) {
        DelDate = delDate;
    }

    public String getDeleteFlag() {
        return DeleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        DeleteFlag = deleteFlag;
    }

    public String getDelName() {
        return DelName;
    }

    public void setDelName(String delName) {
        DelName = delName;
    }

    public String getIsAudit() {
        return IsAudit;
    }

    public void setIsAudit(String isAudit) {
        IsAudit = isAudit;
    }

    public String getIsCollection() {
        return IsCollection;
    }

    public void setIsCollection(String isCollection) {
        IsCollection = isCollection;
    }

    public String getIsFlag() {
        return IsFlag;
    }

    public void setIsFlag(String isFlag) {
        IsFlag = isFlag;
    }

    public String getIsTotal() {
        return IsTotal;
    }

    public void setIsTotal(String isTotal) {
        IsTotal = isTotal;
    }

    public String getStoreRoom() {
        return StoreRoom;
    }

    public void setStoreRoom(String storeRoom) {
        StoreRoom = storeRoom;
    }

    public String getUppdataDate() {
        return UppdataDate;
    }

    public void setUppdataDate(String uppdataDate) {
        UppdataDate = uppdataDate;
    }

    public String getUppdataName() {
        return UppdataName;
    }

    public void setUppdataName(String uppdataName) {
        UppdataName = uppdataName;
    }

    public int getInfoID() {
        return InfoID;
    }

    public void setInfoID(int infoID) {
        InfoID = infoID;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
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

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

}
