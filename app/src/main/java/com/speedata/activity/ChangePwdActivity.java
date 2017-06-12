package com.speedata.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

import java.util.List;

public class ChangePwdActivity extends BaseActivity {

    private EditText edvNewPwd, edvOldPwd;
    private Button btnSave;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        userDao = new UserDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_change_pwd);
        setTitle("更改当前密码");
        edvNewPwd = (EditText) findViewById(R.id.edv_new_pwd);
        edvOldPwd = (EditText) findViewById(R.id.edv_old_pwd);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ole = edvOldPwd.getText().toString();
                String newpwd = edvNewPwd.getText().toString();
                if (ole.equals("") || newpwd.equals("")) {
                    Toast.makeText(mContext, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserForm> users = userDao.imQueryList("userName=?", new String[]{app
                        .getSharedPreferences().getString(Constant.FIELD_USER_ID, "22")});
                if (users.size() < 0) {
                    return;
                }
                if (!users.get(0).getUserPassword().equals(ole)) {
                    Toast.makeText(mContext, "旧密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                userDao.execSql("update userForm set userPassword=? where userName=?", new
                        String[]{newpwd, app
                        .getSharedPreferences().getString(Constant.FIELD_USER_ID, "22")});
                finish();
//                Bundle bundle=new Bundle();
//                bundle.putString();
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }

}
