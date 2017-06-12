package com.speedata.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.application.CustomerApplication;
import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.dreamdemo.R;
import com.speedata.info.BaseInformation;
import com.speedata.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements OnClickListener {

    private Button mLogin;//, mLink, mBack;
    private EditText edvUserName, edvUserPwd;
    private List<BaseInformation> list = new ArrayList<BaseInformation>();
    private String userName, userPass;
    private UserDao userDao;
    private TextView tvVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
        userDao = new UserDao(this);
        CustomerApplication app= (CustomerApplication) getApplication();
        app.getSharedPreferences();
    }


    public void initView() {
        mLogin = (Button) findViewById(R.id.login_login_btn);
//        mLink = (Button) findViewById(R.id.login_link_btn);
//        mBack = (Button) findViewById(R.id.login_back_btn);
        mLogin.setOnClickListener(this);
//        mLink.setOnClickListener(this);
//        mBack.setOnClickListener(this);
        edvUserName = (EditText) findViewById(R.id.login_user_name);

        edvUserPwd = (EditText) findViewById(R.id.login_user_pass);
//        edvUserPwd.setText("22");
        edvUserName.setText(app.getSharedPreferences().getString(Constant.FIELD_USER_ID, "admin"));
        tvVersion = (TextView) findViewById(R.id.tv_version);
//        edvUserPwd.setText("Administrator");
//        edvUserName.setText("Administrator");
        tvVersion.setText("V" + getVersion());
//        tvVersion.setText("V1.2");
    }

    @Override
    public void onClick(View v) {

        int ID = v.getId();
        switch (ID) {
            case R.id.login_login_btn:
                String user = edvUserName.getText().toString();
                String pwd = edvUserPwd.getText().toString();
                if (user.equals("") || pwd.equals("")) {
                    Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserForm> userForms = userDao.imQueryList("userName=? and userPassword=?",
                        new String[]{user, pwd});
                if (userForms.size() == 0) {
                    Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserForm userForm = userForms.get(0);
                app.getEditor().putString(Constant.FIELD_USER_ID, user);
                app.getEditor().commit();
                if (userForm.isPermission()) {
                    Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, OperatorsHomeActivity.class);
                    startActivity(intent);
                }

        }
    }

    /**
     * 获取当前应用程序的版本号
     */
    private String getVersion() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }
}
