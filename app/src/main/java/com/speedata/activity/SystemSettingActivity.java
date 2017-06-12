package com.speedata.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.speedata.activity.in.InRegisterActivity;
import com.speedata.activity.in.SupplierRejectActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.ResultEntity;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.SystemDateTime;
import com.speedata.utils.WebServiceUtils;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class SystemSettingActivity extends BaseActivity implements OnClickListener {

    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MyLogger logger = MyLogger.jLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_main);
        initView();
        initList();
    }

    public void initList() {
        list.add("");//同步时间
        list.add("");
        list.add("");
        imgList.add(R.drawable.icon_sync_time);
        imgList.add(R.drawable.icon_change_pwd);
        imgList.add(R.drawable.icon_wifi);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ProgressDialogUtils.dismissProgressDialog();
            ResultEntity entity = (ResultEntity) msg.obj;
            ProgressDialogUtils.dismissProgressDialog();
            if (entity.isSuccess()) {
                try {
                    String str = entity.getData();
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    try {
//                        Date date = sdf.parse(str);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    Calendar calendar = Calendar.getInstance();
                    MyDateAndTime myDateAndTime = MyDateAndTime.praseDateAndTime(str);
                    if (myDateAndTime != null) {
                        SystemDateTime.setDateTime(myDateAndTime.getYear(), myDateAndTime
                                        .getMonth(), myDateAndTime.getDay(), myDateAndTime
                                        .getHour(),
                                myDateAndTime.getMin());
                    } else {
                        new AlertDialog.Builder(mContext).setMessage("时间格式异常！" + entity.getData()
                        ).show();
                        return;
                    }
                    new AlertDialog.Builder(mContext).setMessage("同步成功！" + entity.getData()).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    new AlertDialog.Builder(mContext).setMessage("IOException:" + e.getMessage())
                            .show();
                } catch (InterruptedException e) {
                    new AlertDialog.Builder(mContext).setMessage("InterruptedException:" + e
                            .getMessage()).show();
                    e.printStackTrace();
                }
            } else {
                new AlertDialog.Builder(mContext).setMessage(entity.getData()).show();
            }

//            switch (msg.what) {
//                case 0:
//                    break;
//            }
        }
    };

    public void initView() {
        mGridView = (GridView) findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mContext, imgList);
        mGridView.setAdapter(adapter);
//        mBack = (ImageView) findViewById(R.id.upload_back_btn);
        mBack = getBackBtn();
        setTitle("系统设置");
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
                        // 同步时间
                        ProgressDialogUtils.showProgressDialog(mContext, "时间同步中...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap<String, Object> properties = new HashMap<String, Object>();
                                properties.put("projectid", "00001");
                                WebServiceUtils utils = new WebServiceUtils(HttpInterfaceModel
                                        .GetServiceTime, mContext);
                                utils.callWebService(mContext, properties, new WebServiceUtils
                                        .WebServiceCallBack() {
                                    @Override
                                    public void callBack(ResultEntity result) {
                                        Message msg = new Message();
                                        msg.obj = result;
                                        if (result.isSuccess()) {
                                            msg.what = 0;
                                        } else {
                                            msg.what = 1;
                                        }
                                        handler.sendMessage(msg);
                                    }
                                });
                            }
                        }).start();

                        break;
                    case 1:
                        // 修改密码
                        startActivity(new Intent(mContext, ChangePwdActivity.class));
                        break;
                    case 2:
                        // 网络连接
                        Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");
                        startActivity(wifiSettingsIntent);
                        break;
                    case 3:


                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
