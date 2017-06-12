package com.speedata.activity;

import java.util.ArrayList;
import java.util.List;

import com.speedata.activity.in.SupplierRejectActivity;
import com.speedata.adapter.GridViewAdapter;
import com.speedata.bean.UserForm;
import com.speedata.dao.UserDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerActivity extends BaseActivity implements OnItemClickListener,
        OnClickListener {

    private GridView mGridView;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private GridViewAdapter adapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_home_activity);
        initList();
        initView();
    }

    public void initList() {
        list.add("");//项目部
        list.add("");//服务器
        list.add("");//自动更新
        list.add("");//增加用户
        list.add("");//删除用户
        list.add("");//修改密码
        imgList.add(R.drawable.icon_project_id);
        imgList.add(R.drawable.icon_service);
        imgList.add(R.drawable.icon_update);
        imgList.add(R.drawable.icon_add_user);
        imgList.add(R.drawable.icon_delete_user);
        imgList.add(R.drawable.icon_change_pwd);
    }

    public void initView() {
        mGridView = (GridView) findViewById(R.id.include_Gv);
        adapter = new GridViewAdapter(list, ManagerActivity.this, imgList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        setTitle("操作员管理");
        mBack = getBackBtn();
        mBack.setOnClickListener(this);

    }

    private UserDao userDao;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        final Dialog dialog = new Dialog(ManagerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.manager_project);

        final EditText editText = (EditText) dialog
                .findViewById(R.id.manager_project_edit);
        final EditText editText1 = (EditText) dialog
                .findViewById(R.id.manager_project_edit2);
        final TextView topTv = (TextView) dialog
                .findViewById(R.id.manager_project_Top);
        final TextView oneTv = (TextView) dialog
                .findViewById(R.id.manager_project_text);
        final TextView twoTv = (TextView) dialog
                .findViewById(R.id.manager_project_text2);
        final Button buttonConfirm = (Button) dialog
                .findViewById(R.id.manager_project_confirm);
        final Button buttonCancel = (Button) dialog
                .findViewById(R.id.manager_project_cancel);
        final LinearLayout linear = (LinearLayout) dialog
                .findViewById(R.id.manager_project_linear);
        final LinearLayout linear_real_name = (LinearLayout) dialog
                .findViewById(R.id.rasl_name_linear);

        switch (position) {
            case 0:
                startActivity(new Intent(mContext, ChangeProjectIdActivity.class));
                //ChangeProjectIdActivity
//                new AlertDialog.Builder(mContext).setMessage("").setTitle("项目部编号").setView()
//                editText.setText(app.getSharedPreferences().getString(Constant.FIELD_PROJETC,
//                        "00001"));
//                editText.setHint("项目部编号");
//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // 获取输入内容，传递到数据库
//                        String str = editText.getText().toString();
//                        if (str.equals("")) {
//                            Toast.makeText(mContext, "请输入完整信息！", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        app.getEditor().putString(Constant.FIELD_PROJETC, str);
//                        app.getEditor().commit();
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
                break;

            case 1:
                startActivity(new Intent(mContext, SetServiceURLActivity.class));
//                topTv.setText("服务器地址");
//                oneTv.setText("地址");
//                editText.setText(app.getSharedPreferences().getString(Constant.FIELD_IP,
//                        "http://36.44" +
//                                ".88.117:8010/service/barcodeservice.asmx?op"));
//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        String url = editText.getText().toString();
//                        app.getEditor().putString(Constant.FIELD_IP, url);
//                        app.getEditor().commit();
//                        dialog.dismiss();
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;

            case 2:
                startActivity(new Intent(mContext, SetAutoUpdateActivity.class));
//                topTv.setText("自动更新");
//                oneTv.setText("地址");
//                // 不能改变，空指针异常:给一个吐司，提醒用户输入192.87.892.com.cn.update
//                Toast.makeText(ManagerActivity.this, "地址：192.87.892.com.cn.update",
//                        Toast.LENGTH_SHORT).show();
//                buttonConfirm.setText("下载更新");
//                buttonCancel.setText("保存设置");
//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // editText.getText().toString();
//                        dialog.dismiss();
//                    }
//                });
//                buttonCancel.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // editText.getText().toString();
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;
            case 3:
                startActivity(new Intent(mContext, AddUserActivity.class));
//                linear.setVisibility(View.VISIBLE);
//                linear_real_name.setVisibility(View.VISIBLE);
//                topTv.setText("添加用户");
//                oneTv.setText("用户名");
//                twoTv.setText("密    码");
//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // editText.getText().toString();
//                        String user = editText.getText().toString();
//                        String pwd = editText1.getText().toString();
//                        String realName = editText.getText().toString();
//                        if (user.equals("") || pwd.equals("") || realName.equals("")) {
//                            Toast.makeText(mContext, "请填写完整信息", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        UserForm form = new UserForm();
//                        form.setUserPassword(pwd);
//                        form.setRealName(realName);
//                        form.setPermission(false);
//                        form.setUserName(user);
//                        if (userDao == null) {
//                            userDao = new UserDao(mContext);
//                        }
//                        if (userDao.imQueryList("userName=?", new String[]{user}).size() == 0) {
//                            userDao.imInsert(form);
//                        }else{
//                            Toast.makeText(mContext, "用户名已存在", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;

            case 4:
                startActivity(new Intent(mContext, DelectUserActivity.class));
                break;
            case 5:
                startActivity(new Intent(mContext, ChangePwdActivity.class));
//                topTv.setText("修改密码");
//                oneTv.setText("新密码");
//                buttonConfirm.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // editText.getText().toString();
//                        dialog.dismiss();
//                    }
//                });
//                buttonCancel.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        // editText.getText().toString();
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            Intent intent = new Intent(ManagerActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
