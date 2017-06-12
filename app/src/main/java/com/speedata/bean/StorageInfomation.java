package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

@Table(name = "storageInfomation")
public class StorageInfomation {

    @Column(name = "IsLeaf")
    // 是否为子节点
    private boolean IsLeaf;

    @Column(name = "CKNodebh")
    // 仓库编号
    private String CKNodebh;

    @Column(name = "ProjectID")
    // 项目编号
    private String ProjectID;

    @Column(name = "CKName")
    //层级名称
    private String CKName;

    @Column(name = "CKAbbName")
    //层级全称
    private String CKAbbName;

    @Column(name = "StatusName")
    // 状态名称
    private String StatusName;

    @Column(name = "Grade")
    // 级别
    private String Grade;

    @Column(name = "Remark")
    // 备注
    private String Remark;

    public boolean isLeaf() {
        return IsLeaf;
    }

    public void setIsLeaf(boolean isLeaf) {
        IsLeaf = isLeaf;
    }

    public String getCKNodebh() {
        return CKNodebh;
    }

    public void setCKNodebh(String cKNodebh) {
        CKNodebh = cKNodebh;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getCKName() {
        return CKName;
    }

    public void setCKName(String cKName) {
        CKName = cKName;
    }

    public String getCKAbbName() {
        return CKAbbName;
    }

    public void setCKAbbName(String cKAbbName) {
        CKAbbName = cKAbbName;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
