package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Table;

/** 供应商信息
 * Created by Administrator on 2016/3/3.
 */

@Table(name = "commonSupplier")
public class CommonSupplier {
        @Column(name = "SupplierNM")
        // 内码
        private String SupplierNM;

        @Column(name = "ProjectName")
        // 项目部名称
        private String ProjectName;

        @Column(name = "ProjectID")
        // 项目编号
        private String ProjectID;

        @Column(name = "SupplierName")
        //供应商名称
        private String SupplierName;

        @Column(name = "SupplierYYZZ")
        // 营业执照号
        private String SupplierYYZZ;

        @Column(name = "SupplierLxr")
        // 联系人
        private String SupplierLxr;

        @Column(name = "SupplierPhone")
        // 联系电话
        private String SupplierPhone;

        @Column(name = "SupplierOrgCode")
        // 组织机构代码
        private String SupplierOrgCode;

        @Column(name = "Remark")
        // 备注
        private String Remark;

    public String getSupplierNM() {
        return SupplierNM;
    }

    public void setSupplierNM(String supplierNM) {
        SupplierNM = supplierNM;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getSupplierYYZZ() {
        return SupplierYYZZ;
    }

    public void setSupplierYYZZ(String supplierYYZZ) {
        SupplierYYZZ = supplierYYZZ;
    }

    public String getSupplierLxr() {
        return SupplierLxr;
    }

    public void setSupplierLxr(String supplierLxr) {
        SupplierLxr = supplierLxr;
    }

    public String getSupplierPhone() {
        return SupplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        SupplierPhone = supplierPhone;
    }

    public String getSupplierOrgCode() {
        return SupplierOrgCode;
    }

    public void setSupplierOrgCode(String supplierOrgCode) {
        SupplierOrgCode = supplierOrgCode;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
