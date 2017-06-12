package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Id;
import com.elsw.base.db.orm.annotation.Table;

//库存


@Table(name = "store")
public class StoreBean {

    @Id
    @Column(name = "ItemNM")
    // 编号
    private String ItemNM;

    @Column(name = "ProjectID")
    // 项目编码
    private String ProjectID;

    @Column(name = "SupplierNM")
    // 供应商编码
    private String SupplierNM;

    @Column(name = "SupplierName")
    //供应商明曾
    private String SupplierName;

    @Column(name = "RequisitionDate")
    //进料日期
    private String RequisitionDate;

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

    @Column(name = "BarCode")
    // 批次号
    private String BarCode;

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

    @Column(name = "MakerDate")
    // 制单日期
    private String MakerDate;

    @Column(name = "Quantity")
    // 收料数量
    private double Quantity;

//    @Column(name = "flag")
//    // 手持机标识码
//    private String flag;

    @Column(name = "Remark")
    // 备注
    private String Remark;

    @Column(name = "BookPrice")
    // 单价
    private double BookPrice;

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

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
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

    public double getBookPrice() {
        return BookPrice;
    }

    public void setBookPrice(double bookPrice) {
        BookPrice = bookPrice;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getMakerDate() {
        return MakerDate;
    }

    public void setMakerDate(String makerDate) {
        MakerDate = makerDate;
    }

    public String getItemNM() {
        return ItemNM;
    }

    public void setItemNM(String itemNM) {
        ItemNM = itemNM;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getSupplierNM() {
        return SupplierNM;
    }

    public void setSupplierNM(String supplierNM) {
        SupplierNM = supplierNM;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getRequisitionDate() {
        return RequisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        RequisitionDate = requisitionDate;
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

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getInfoClassNodebh() {
        return InfoClassNodebh;
    }

    public void setInfoClassNodebh(String classNodebh) {
        InfoClassNodebh = classNodebh;
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


    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }


//    public String getFlag() {
//        return flag;
//    }
//
//    public void setFlag(String flag) {
//        this.flag = flag;
//    }

    @Override
    public String toString() {
        return "StoreBean{" +
                "BarCode='" + BarCode + '\'' +
                ", ItemNM='" + ItemNM + '\'' +
                ", ProjectID='" + ProjectID + '\'' +
                ", SupplierNM='" + SupplierNM + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", RequisitionDate='" + RequisitionDate + '\'' +
                ", InfoCode='" + InfoCode + '\'' +
                ", InfoName='" + InfoName + '\'' +
                ", InfoModel='" + InfoModel + '\'' +
                ", InfoUnit='" + InfoUnit + '\'' +
                ", InfoClassNodebh='" + InfoClassNodebh + '\'' +
                ", InfoClassName='" + InfoClassName + '\'' +
                ", SecondClassName='" + SecondClassName + '\'' +
                ", FirstClassName='" + FirstClassName + '\'' +
                ", Storeroom='"  +
                ", Quantity=" + Quantity +
                '}';
    }
}
