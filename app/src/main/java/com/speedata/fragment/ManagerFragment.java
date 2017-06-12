package com.speedata.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.speedata.activity.AddUserActivity;
import com.speedata.activity.ChangeProjectIdActivity;
import com.speedata.activity.ChangePwdActivity;
import com.speedata.activity.DelectUserActivity;
import com.speedata.activity.SetAutoUpdateActivity;
import com.speedata.activity.SetServiceURLActivity;
import com.speedata.adapter.GridViewAdapter;
import com.speedata.dreamdemo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class ManagerFragment extends BaseFragment implements AdapterView.OnItemClickListener,
        View.OnClickListener {

    private GridView mGridView;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private GridViewAdapter adapter;
    private ImageView mBack;
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_operators_home_activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();

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
    @Override
    public void findById(View view) {
        mGridView = (GridView) view.findViewById(R.id.include_Gv);
        adapter = new GridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        mActivity.setTitle("操作员管理");
        mBack = mActivity.getBackBtn();
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            openFragment(new LoginFragment());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.updateProject();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.manager_project);
        switch (position) {
            case 0:
                startActivity(new Intent(mActivity, ChangeProjectIdActivity.class));
                break;

            case 1:
                startActivity(new Intent(mActivity, SetServiceURLActivity.class));
                break;

            case 2:
                startActivity(new Intent(mActivity, SetAutoUpdateActivity.class));
                break;
            case 3:
                startActivity(new Intent(mActivity, AddUserActivity.class));
                break;

            case 4:
                startActivity(new Intent(mActivity, DelectUserActivity.class));
                break;
            case 5:
                startActivity(new Intent(mActivity, ChangePwdActivity.class));
                break;
        }
    }
}
