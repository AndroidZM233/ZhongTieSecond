package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

/**常用材料信息
 * Created by Administrator on 2016/3/3.
 */
@Table(name = "commonMaterialInfo")
public class CommonMaterialInfo {
    @Column(name = "InfoNM")
    // 内码
    private String InfoNM;

    @Column(name = "ProjectID")
    // 项目编号
    private String ProjectID;

    @Column(name = "ProjectName")
    //项目名称
    private String ProjectName;

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

    @Column(name = "InfoClassNodebh")
    // 类别编码
    private String InfoClassNodebh;

    @Column(name = "InfoClassName")
    // 三级类别名称
    private String InfoClassName;

    @Column(name = "SecondClassName")
    // 二级类别名称
    private String SecondClassName;

    @Column(name = "FirstClassName")
    // 一级类别名称
    private String FirstClassName;

    public String getInfoNM() {
        return InfoNM;
    }

    public void setInfoNM(String infoNM) {
        InfoNM = infoNM;
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
}