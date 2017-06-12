package com.speedata.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.CheckActivity;
import com.speedata.activity.LoginActivity;
import com.speedata.activity.MaintainInfomationActivity;
import com.speedata.activity.SystemSettingActivity;
import com.speedata.activity.allot.AllotActivity;
import com.speedata.activity.in.InActivity;
import com.speedata.activity.out.OutActivity;
import com.speedata.adapter.MetrailsGridViewAdapter;
import com.speedata.dreamdemo.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class OperatorsHomeFragment extends BaseFragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private GridView mGridView;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MetrailsGridViewAdapter adapter;
    private ImageView mBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();

    }
    public void initList() {
        list.add("");
        list.add("");
        list.add("");
        list.add("");//条码打印
        list.add("");//信息维护
        list.add("");//系统设置
        imgList.add(R.drawable.icon_in1);
        imgList.add(R.drawable.icon_out1);
        imgList.add(R.drawable.icon_check);
        imgList.add(R.drawable.icon_allot);
        imgList.add(R.drawable.icon_sheet);
        imgList.add(R.drawable.icon_phone_settings);
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_operators_home_activity;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("操作员主界面");
        mGridView = (GridView) view.findViewById(R.id.include_Gv);
        adapter = new MetrailsGridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
        mBack = mActivity.getBackBtn();
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBack) {
            new AlertDialog.Builder(mActivity).setMessage("是否注销当前登录?").setTitle("提示")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openFragment(new LoginFragment());
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                //进科登记
               openFragment(new InFragment());
                break;
            case 1:
                openFragment(new OutFragment());
                break;
            case 2:
                openFragment(new CheckFragment());
                break;
            case 3:
                openFragment(new AllotFragment());

                break;
            case 4:
                Intent intent4 = new Intent(mActivity,
                        MaintainInfomationActivity.class);
                startActivity(intent4);

                break;
            case 5:
                Intent intent5 = new Intent(mActivity, SystemSettingActivity
                        .class);
                startActivity(intent5);
                break;

        }
    }

}
