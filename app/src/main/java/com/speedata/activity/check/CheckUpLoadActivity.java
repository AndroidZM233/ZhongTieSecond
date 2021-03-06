package com.speedata.activity.check;

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
import com.speedata.utils.SetBillTimeUtils;
import com.speedata.view.SelectDateDialog;
import com.speedata.adapter.CheckUploadListAdapter;
import com.speedata.bean.CheckForm;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.ResultEntity;
import com.speedata.dao.CheckFormDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.WebServiceUtils;

import java.util.HashMap;
import java.util.List;

public class CheckUpLoadActivity extends BaseActivity implements View.OnClickListener {
    private CheckFormDao checkFormDao;
    private HashMap<String, Object> properties = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        checkFormDao = new CheckFormDao(mContext);
    }

    private Button btnSure;
    private TextView tvStart, tvEnd;
    private Button btnSelect;
    private ListView listView;

    private void initUI() {
        setContentView(R.layout.activity_check_up_load);
        setTitle("上传盘点单");
        tvStart = (TextView) findViewById(R.id.tv_start);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        MyDateAndTime myDateAndTime = MyDateAndTime.praseDateAndTime(MyDateAndTime.getTimeString
                ("yyyy-MM-dd " +
                        "HH:mm:ss"));
        SetBillTimeUtils setBillTimeUtils = new SetBillTimeUtils();
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
        if (v == btnSelect) {
            String start = tvStart.getText().toString();
            String end = tvEnd.getText().toString();

            /// ProjectID="" + app
//            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
//                    .FIELD_DEFAULT_PROJETC) +
//                    ""
            CheckFormList = checkFormDao
                    .imRawQuery("select * from checkForm where ProjectID=\"" + ProjectID + "\"" +
                            " and CheckMonth between " + "\"" + start + "\""
                            + " " +
                            "and " +
                            "\"" + end + "\"", null, CheckForm
                            .class);
            if (CheckFormList.size() == 0) {
//                                    Toast.makeText(mContext, "无数据!", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(mContext).setMessage(start + "到" +
                        end + "内无数据").show().setTitle("错误");
                return;
            }
            CheckUploadListAdapter = new CheckUploadListAdapter(mContext,
                    CheckFormList);
            listView.setAdapter(CheckUploadListAdapter);
        } else if (v == tvStart) {
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
            },tvEnd.getText().toString());
            dateDialog.show();
        } else if (v == btnSure) {
            ProgressDialogUtils.showProgressDialog(mContext, "正在上传");
            if (CheckFormList == null || CheckFormList.size() == 0) {
                Toast.makeText(mContext, "无数据", Toast.LENGTH_SHORT).show();
                return;
            }
            ProgressDialogUtils.showProgressDialog(mContext, "正在上传" +
                    CheckFormList.size() + "条数据");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, Object> properties = new HashMap<String,
                            Object>();

                    String json = JSON.toJSONString(CheckFormList);
                    properties.put("result", json);
                    logger.d("====" + json);
                    WebServiceUtils utils = new WebServiceUtils
                            ("InsertStoreCheckList ", mContext);
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
        }
    }

    private com.speedata.adapter.CheckUploadListAdapter CheckUploadListAdapter;
    List<CheckForm> CheckFormList;

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
