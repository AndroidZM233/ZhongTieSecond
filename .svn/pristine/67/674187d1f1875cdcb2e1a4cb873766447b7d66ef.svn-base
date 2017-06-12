package com.speedata.activity.in;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.speedata.activity.BaseActivity;
import com.speedata.adapter.InUploadListAdapter;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.ResultEntity;
import com.speedata.dao.InRegisterDao;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.SetBillTimeUtils;
import com.speedata.utils.WebServiceUtils;
import com.speedata.view.SelectDateDialog;

import java.util.HashMap;
import java.util.List;

public class InUpLoadActivity extends BaseActivity implements View.OnClickListener {
    private InRegisterDao inRegisterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        inRegisterDao = new InRegisterDao(mContext);
    }

    private Button btnSure;
    private TextView tvStart, tvEnd;
    private Button btnSelect;
    private ListView listView;

    private void initUI() {
        setContentView(R.layout.activity_in_up_load);
        setTitle("上传进料单");
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        MyDateAndTime myDateAndTime = MyDateAndTime.praseDateAndTime(MyDateAndTime.getTimeString
                ("yyyy-MM-dd " +
                        "HH:mm:ss"));
        SetBillTimeUtils setBillTimeUtils=new SetBillTimeUtils();
        //判断账单日
        if (myDateAndTime.getDay() > Constant.ACCOUNT_DAY) {
            tvStart.setText(setBillTimeUtils.billtimeSub1());
            tvEnd.setText(setBillTimeUtils.billtimeAdd1());
        } else {
            tvStart.setText(setBillTimeUtils.billtimeSub1());
            tvEnd.setText(setBillTimeUtils.billtime());
        }
        tvStart.setOnClickListener(this);
        tvEnd.setOnClickListener(this);
        btnSure = (Button) findViewById(R.id.btn_save);
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSure.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
    }

    private MyLogger logger = MyLogger.jLog();

    @Override

    public void onClick(View v) {
        if (v == tvStart) {
            SelectDateDialog dateDialog = new SelectDateDialog(mContext, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tvStart.setText(time);
                }
            }, tvStart.getText().toString());
            dateDialog.show();
        } else if (v == tvEnd) {
            SelectDateDialog dateDialog = new SelectDateDialog(mContext, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tvEnd.setText(time);
                }
            }, tvEnd.getText().toString());
            dateDialog.show();
        } else if (v == btnSure) {
            if (inRegisterList == null || inRegisterList.size() == 0) {
                Toast.makeText(mContext, "无数据", Toast.LENGTH_SHORT).show();
                return;
            }
            ProgressDialogUtils.showProgressDialog(mContext, "正在上传" +
                    inRegisterList.size() + "条数据");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, Object> properties = new HashMap<String,
                            Object>();

                    String json = JSON.toJSONString(inRegisterList);
                    logger.d("=====upload=" + json);
                    properties.put("result", json);
                    WebServiceUtils utils = new WebServiceUtils
                            (HttpInterfaceModel
                                    .InsertReceiveRecordList, mContext);
                    utils.callWebService(mContext, properties, new
                            WebServiceUtils
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
        } else if (v == btnSelect) {
            String start = tvStart.getText().toString();
            String end = tvEnd.getText().toString();
            inRegisterList = inRegisterDao
                    .imRawQuery("select * from inRegister where ProjectID=\"" + app
                            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                                    .FIELD_DEFAULT_PROJETC) +
                            "\" and RequisitionDate between " + "\"" + start + "\""
                            + " " +
                            "and " +
                            "\"" + end + "\"", null, InRegister
                            .class);
            if (inRegisterList.size() == 0) {
//                                    Toast.makeText(mContext, "无数据!", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(mContext).setMessage(start + "到" +
                        end + "内无数据").show().setTitle("错误");
                return;
            }
            inUploadListAdapter = new InUploadListAdapter(mContext, inRegisterList);
            listView.setAdapter(inUploadListAdapter);
        }
    }

    private InUploadListAdapter inUploadListAdapter;
    List<InRegister> inRegisterList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResultEntity entity = (ResultEntity) msg.obj;
            if (entity.isSuccess()) {

                if (entity.getData().contains("false")) {
                    ProgressDialogUtils.dismissProgressDialog();
                    new AlertDialog.Builder(mContext).setMessage("失败，返回信息！" + entity.getData()
                    ).show();
                }
                if (entity.getData().contains("true")) {
                    ProgressDialogUtils.dismissProgressDialog();
                    new AlertDialog.Builder(mContext).setMessage("成功！"
                    ).show();
                }
            } else {
                ProgressDialogUtils.dismissProgressDialog();
                new AlertDialog.Builder(mContext).setMessage("失败，返回信息！" + entity.getData()
                ).show();
            }

        }
    };

}
