package com.speedata.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.speedata.adapter.UserListAdapter;
import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class DelectUserActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private UserListAdapter adapter;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDao(mContext);
        initUI();
    }

    private List<UserForm> userFormList = new ArrayList<UserForm>();

    private void initUI() {
        setContentView(R.layout.activity_delect_user);
        setTitle("删除用户");
        listView = (ListView) findViewById(R.id.list_user);
        userFormList = userDao.imQueryList();
        //"userName!=", new String[]{app.getSharedPreferences()
//        .getString(Constant.FIELD_USER_ID, "22")}
        ;
        adapter = new UserListAdapter(mContext, userFormList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final String username = userFormList.get(position)
                .getUserName();
        if (username.equals(app.getSharedPreferences()
                .getString(Constant.FIELD_USER_ID, "22"))) {
            Toast.makeText(mContext, "不能删除当前登录账户！", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(mContext).setTitle("删除用户").setMessage("是否删除" + userFormList.get
                (position).getUserName() + "该用户？").setPositiveButton("确定", new DialogInterface
                .OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDao.imDelete("userName=?", new String[]{username});
                userFormList.remove(position);
                adapter.refresh();
                ;
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
