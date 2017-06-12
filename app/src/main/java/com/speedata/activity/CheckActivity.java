package com.speedata.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.speedata.activity.UpAndDown.SetDateDialog;
import com.speedata.activity.UpAndDown.ShowDate;
import com.speedata.activity.check.CheckDownLoadActivity;
import com.speedata.activity.check.CheckRegisterActivity;
import com.speedata.activity.check.CheckUpLoadActivity;
import com.speedata.activity.out.OutRegisterActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.bean.CheckForm;
import com.speedata.bean.CheckForm;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.bean.ResultEntity;
import com.speedata.bean.StoreBean;
import com.speedata.dao.CheckFormDao;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.FileUtil;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.WebServiceUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivity extends BaseActivity implements OnClickListener {

    private CheckFormDao checkFormDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_main);
        initView();
        initList();
        checkFormDao = new CheckFormDao(mContext);
    }


    @Override
    public void onClick(View v) {

    }

    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private HashMap<String, Object> properties = new HashMap<String, Object>();

    public void initList() {
        list.add("");//盘点
        list.add("");//上传
        list.add("");//下载
        imgList.add(R.drawable.icon_check);
        imgList.add(R.drawable.icon_arrow_up);
        imgList.add(R.drawable.icon_down);
    }

    private int sumCount = 0;

    public void initView() {
        mGridView = (GridView) findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mContext, imgList);
        mGridView.setAdapter(adapter);
//        mBack = (ImageView) findViewById(R.id.upload_back_btn);
        mBack = getBackBtn();
        setTitle("盘点");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        // 进入发料登记界面：
                        startActivity(new Intent(mContext, CheckRegisterActivity.class));
                        break;
                    case 1:
                        // 弹出提示框
                        startActivity(new Intent(mContext, CheckUpLoadActivity.class));
//                        SetDateDialog setDateDialog = new SetDateDialog(mContext, new ShowDate() {
//                            @Override
//                            public void showDate(final String start, final String end) {
//                                Toast.makeText(mContext, "" + start + "到" + end, Toast
//                                        .LENGTH_SHORT).show();
//                                final List<CheckForm> CheckFormList = checkFormDao
//                                        .imRawQuery("select * from checkForm where " +
//                                                "MakerDate between " + "\"" + start.substring(0,7) + "\""
//                                                + " " +
//                                                "and " +
//                                                "\"" + end.substring(0,7) + "\"", new String[]{}, CheckForm
//                                                .class);
//                                if (CheckFormList.size() == 0) {
////                                    Toast.makeText(mContext, "无数据!", Toast.LENGTH_SHORT).show();
//                                    new AlertDialog.Builder(mContext).setMessage(start + "到" +
//                                            end + "内无数据").show().setTitle("错误");
//                                    return;
//                                }
//                                ProgressDialogUtils.showProgressDialog(mContext, "正在上传" +
//                                        CheckFormList.size() + "条数据");
//
//                            }
//                        }, MyDateAndTime.getTimeString("yyyy-MM-dd"));
//                        setDateDialog.setTitle("选择上传时间段");
//                        setDateDialog.show();

                        break;
                    case 2:
                        startActivity(new Intent(mContext, CheckDownLoadActivity.class));
//                        SetDateDialog setDateDialog2 = new SetDateDialog(mContext, new ShowDate() {
//                            @Override
//                            public void showDate(final String start, final String end) {
////                                Toast.makeText(mContext, "" + start + "到" + end, Toast
////                                        .LENGTH_SHORT).show();
//                                ProgressDialogUtils.showProgressDialog(mContext, "正在下载");
//                            }
//                        }, MyDateAndTime.getTimeString("yyyy-MM-dd"));
//                        setDateDialog2.setTitle("选择下载时间段");
//                        setDateDialog2.show();
                        break;
                }
            }
        });
    }


}
