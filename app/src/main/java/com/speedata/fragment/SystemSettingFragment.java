package com.speedata.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.ChangePwdActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.ResultEntity;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.SystemDateTime;
import com.speedata.utils.WebServiceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class SystemSettingFragment extends BaseFragment implements View.OnClickListener {
    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MyLogger logger = MyLogger.jLog();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    MyDateAndTime myDateAndTime = MyDateAndTime.praseDateAndTime(str);
                    if (myDateAndTime != null) {
                        SystemDateTime.setDateTime(myDateAndTime.getYear(), myDateAndTime
                                        .getMonth(), myDateAndTime.getDay(), myDateAndTime
                                        .getHour(),
                                myDateAndTime.getMin());
                    } else {
                        new AlertDialog.Builder(mActivity).setMessage("时间格式异常！" + entity.getData()
                        ).show();
                        return;
                    }
                    new AlertDialog.Builder(mActivity).setMessage("同步成功！" + entity.getData()).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    new AlertDialog.Builder(mActivity).setMessage("IOException:" + e.getMessage())
                            .show();
                } catch (InterruptedException e) {
                    new AlertDialog.Builder(mActivity).setMessage("InterruptedException:" + e
                            .getMessage()).show();
                    e.printStackTrace();
                }
            } else {
                new AlertDialog.Builder(mActivity).setMessage(entity.getData()).show();
            }

        }
    };
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_check_main;
    }

    @Override
    public void findById(View view) {
        mGridView = (GridView) view.findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mBack = mActivity.getBackBtn();
        mActivity.setTitle("系统设置");
        mBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFragment(new OperatorsHomeFragment());
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
                        ProgressDialogUtils.showProgressDialog(mActivity, "时间同步中...");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap<String, Object> properties = new HashMap<String, Object>();
                                properties.put("projectid", "00001");
                                WebServiceUtils utils = new WebServiceUtils(HttpInterfaceModel
                                        .GetServiceTime, mActivity);
                                utils.callWebService(mActivity, properties, new WebServiceUtils
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
                        openFragment(new ChangePwdFragment());
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
