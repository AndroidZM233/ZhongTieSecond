package com.speedata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.activity.LoginActivity;
import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ChangePwdFragment extends BaseFragment {

    private EditText edvNewPwd, edvOldPwd;
    private Button btnSave;
    private UserDao userDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDao(mActivity);
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_change_pwd;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("更改当前密码");
        edvNewPwd = (EditText) view.findViewById(R.id.edv_new_pwd);
        edvOldPwd = (EditText) view.findViewById(R.id.edv_old_pwd);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ole = edvOldPwd.getText().toString();
                String newpwd = edvNewPwd.getText().toString();
                if (ole.equals("") || newpwd.equals("")) {
                    Toast.makeText(mActivity, "不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserForm> users = userDao.imQueryList("userName=?", new String[]{mActivity.app
                        .getSharedPreferences().getString(Constant.FIELD_USER_ID, "22")});
                if (users.size() < 0) {
                    return;
                }
                if (!users.get(0).getUserPassword().equals(ole)) {
                    Toast.makeText(mActivity, "旧密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                userDao.execSql("update userForm set userPassword=? where userName=?", new
                        String[]{newpwd, mActivity.app
                        .getSharedPreferences().getString(Constant.FIELD_USER_ID, "22")});
                openFragment(new LoginFragment());
            }
        });
    }
}

