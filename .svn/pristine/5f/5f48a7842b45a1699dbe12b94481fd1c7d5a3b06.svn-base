package com.speedata.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.speedata.activity.allot.AllotActivity;
import com.speedata.activity.in.InActivity;
import com.speedata.activity.out.OutActivity;
import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.MetrailsGridViewAdapter;
import com.speedata.dreamdemo.R;

import java.util.ArrayList;
import java.util.List;

public class OperatorsHomeActivity extends BaseActivity implements OnClickListener,
        OnItemClickListener {

    private GridView mGridView;
    private TextView mTv;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MetrailsGridViewAdapter adapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_home_activity);
        initList();
        initView();
        setTitle("操作员主界面");
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

    public void initView() {
        mGridView = (GridView) findViewById(R.id.include_Gv);
        adapter = new MetrailsGridViewAdapter(list, OperatorsHomeActivity.this, imgList);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
//        mBack = (ImageView) findViewById(R.id.include_back_btn);
//        mBack.setOnClickListener(this);
        setTitle("操作员界面");
        mBack = getBackBtn();
        mBack.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        //不同的position，选择进入不同的界面
        switch (position) {
            case 0:
                //进科登记
                Intent intent = new Intent(OperatorsHomeActivity.this, InActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(OperatorsHomeActivity.this, OutActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(OperatorsHomeActivity.this, CheckActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(OperatorsHomeActivity.this, AllotActivity.class);
                startActivity(intent3);

                break;
            case 4:
                Intent intent4 = new Intent(OperatorsHomeActivity.this,
                        MaintainInfomationActivity.class);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5 = new Intent(OperatorsHomeActivity.this, SystemSettingActivity
                        .class);
                startActivity(intent5);
                break;

        }


    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v == mBack) {
            Intent intent = new Intent(OperatorsHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        new AlertDialog.Builder(mContext).setMessage("是否注销当前登录?").setTitle("提示")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }
}
