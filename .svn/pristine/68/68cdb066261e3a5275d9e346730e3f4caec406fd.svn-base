package com.speedata.fragment;

import android.os.Bundle;
import android.view.View;
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

/**
 * Created by Administrator on 2016/4/6.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener{
    private Button mLogin;//, mLink, mBack;
    private EditText edvUserName, edvUserPwd;
    private List<BaseInformation> list = new ArrayList<BaseInformation>();
    private String userName, userPass;
    private UserDao userDao;
    private TextView tvVersion;
    private CustomerApplication app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDao(mActivity);
        app = (CustomerApplication) mActivity.getApplication();
        app.getSharedPreferences();
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void findById(View view) {
        mLogin = (Button) view.findViewById(R.id.login_login_btn);
        mLogin.setOnClickListener(this);
        edvUserName = (EditText) view.findViewById(R.id.login_user_name);
        edvUserPwd = (EditText) view.findViewById(R.id.login_user_pass);
        edvUserName.setText(app.getSharedPreferences().getString(Constant.FIELD_USER_ID, "admin"));
        tvVersion = (TextView) view.findViewById(R.id.tv_version);
        tvVersion.setText("版本号：V" + getVersion());
    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        switch (ID) {
            case R.id.login_login_btn:
                String user = edvUserName.getText().toString();
                String pwd = edvUserPwd.getText().toString();
                if (user.equals("") || pwd.equals("")) {
                    Toast.makeText(mActivity, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<UserForm> userForms = userDao.imQueryList("userName=? and userPassword=?",
                        new String[]{user, pwd});
                if (userForms.size() == 0) {
                    Toast.makeText(mActivity, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserForm userForm = userForms.get(0);
                app.getEditor().putString(Constant.FIELD_USER_ID, user);
                app.getEditor().commit();
                if (userForm.isPermission()) {
                    openFragment(new ManagerFragment());
                } else {
                    openFragment(new OperatorsHomeFragment());
                }

        }
    }
}
