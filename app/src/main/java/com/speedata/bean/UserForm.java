package com.speedata.bean;

import com.elsw.base.db.orm.annotation.Column;
import com.elsw.base.db.orm.annotation.Id;
import com.elsw.base.db.orm.annotation.Table;

@Table(name = "userForm")
public class UserForm {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "userName")//用户名
    private String userName;

    @Column(name = "userPassword")
    private String userPassword = "";//密码

    @Column(name = "RealName")
    private String RealName = "";//真实姓名

    @Column(name = "permission")
    private boolean permission = false;//最高权限

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserPassword() {
        return userPassword;
    }


    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    @Override
    public String toString() {
        return "UserFactoryForm{" +
                "userName=" + userName +
                ", userPassword=" + userPassword +
                '}';
    }

}
