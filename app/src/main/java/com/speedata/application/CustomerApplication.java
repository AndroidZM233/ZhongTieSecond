package com.speedata.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.utils.MyExceptionHandler;
import com.speedata.utils.preDefiniation;

import rego.printlib.export.regoPrinter;


public class CustomerApplication extends Application {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String IMEI = "";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this
                .getApplicationContext());
        editor = sharedPreferences.edit();
        // 错误日志־
        MyExceptionHandler.getInstanceMyExceptionHandler()
                .init(this);
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        IMEI = tm.getDeviceId();
//        if (sharedPreferences.getBoolean("isFirst", true)) {
        UserDao userDao = new UserDao(this);
        UserForm userForm = new UserForm();
        userForm.setPermission(true);
        userForm.setRealName("admin");
        userForm.setUserName("admin");
        userForm.setUserPassword("0000");
        userForm.setId("1");
        userDao.imInsert(userForm);

        userForm = new UserForm();
        userForm.setPermission(false);
        userForm.setRealName("user");
        userForm.setId("2");
        userForm.setUserName("user");
        userForm.setUserPassword("0000");
        userDao.imInsert(userForm);

        editor.putBoolean("isFirst", false);
        editor.commit();
//        }
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    private regoPrinter printer;
    private int myState = 0;
    private String printName = "RG-MLP80A";
    private preDefiniation.TransferMode printmode = preDefiniation.TransferMode.TM_NONE;
    private boolean labelmark = true;

    public regoPrinter getObject() {
        return printer;
    }

    public void setObject() {
        printer = new regoPrinter(this);
    }

    public String getName() {
        return printName;
    }

    public void setName(String name) {
        printName = name;
    }

    public void setState(int state) {
        myState = state;
    }

    public int getState() {
        return myState;
    }

    public void setPrintway(int printway) {

        switch (printway) {
            case 0:
                printmode = preDefiniation.TransferMode.TM_NONE;
                break;
            case 1:
                printmode = preDefiniation.TransferMode.TM_DT_V1;
                break;
            default:
                printmode = preDefiniation.TransferMode.TM_DT_V2;
                break;
        }

    }

    public int getPrintway() {
        return printmode.getValue();
    }


    public boolean getlabel() {
        return labelmark;
    }

    public void setlabel(boolean labelprint) {
        labelmark = labelprint;
    }
}
