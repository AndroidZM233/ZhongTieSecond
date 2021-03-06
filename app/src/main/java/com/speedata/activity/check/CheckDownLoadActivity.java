package com.speedata.activity.check;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.speedata.activity.BaseActivity;
import com.speedata.adapter.CheckUploadListAdapter;
import com.speedata.bean.CheckForm;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.ResultEntity;
import com.speedata.dao.CheckFormDao;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.speedata.utils.PageUtil;
import com.speedata.utils.ParseData;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.SetBillTimeUtils;
import com.speedata.utils.WebServiceUtils;
import com.speedata.view.SelectDateDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckDownLoadActivity extends BaseActivity implements View.OnClickListener {

    private CheckForm checkForm;
    private CheckFormDao checkFormDao;
    private HashMap<String, Object> properties = new HashMap<String, Object>();
    private String start;
    private String end;
    private int return_count;
    private WebServiceUtils utils;
    private String return_data;
    private AlertDialog.Builder alertDialog;
    private ParseData parseData;
    private List parseDataList;
    private final int STATE_PAGING = 3;
    private final int STATE_SUCCESS = 1;
    private final int STATE_NO_DATA = 2;
    private final int FAILED = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        checkForm=new CheckForm();
        checkFormDao=new CheckFormDao(mContext);
    }

    private Button btnSure;
    private TextView tvStart, tvEnd;
    private ListView listView;
    private CheckUploadListAdapter adapter;

    private void initUI() {
        setContentView(R.layout.activity_check_down_load);
        setTitle("下载盘点单");
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
        btnSure.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);
    }


    private MyLogger logger = MyLogger.jLog();
    int getCount = 0;

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
            start = tvStart.getText().toString().substring(0,7);
            end = tvEnd.getText().toString().substring(0, 7);
            getCount = 0;
            ProgressDialogUtils.showProgressDialog(mContext, "正在下载");
            downLoadList.clear();
            downLoadInfors(HttpInterfaceModel.DownLoadStoreMonthCheckItemCount,
                    HttpInterfaceModel.DownLoadStoreMonthCheckItem);
        }
    }

    private String getKey() {
        return utils.getMETHOD() + "Result";
    }

    private boolean cancleProdialog(ResultEntity result) {
        Message msg = new Message();
        msg.obj = result;
        msg.what = 0;
        handler.sendMessage(msg);

        if (!result.isSuccess()) {
            return true;
        }
        return false;
    }

    private int pageSize = 100;

    private void downLoadInfors(String getCountMethod, final String getInfor) {
        utils = new WebServiceUtils(getCountMethod, mContext);
        properties.clear();
        properties.put("ProjectID", app.getSharedPreferences().getString(Constant
                .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
        properties.put("BeginMonth", start);
        properties.put("EndMonth", end);
        new Thread(new Runnable() {
            @Override
            public void run() {
                utils.callWebService(mContext, properties, new WebServiceUtils.WebServiceCallBack
                        () {
                    @Override
                    public void callBack(ResultEntity result) {
                        if(!result.isSuccess()){
                            Message msg=new Message();
                            msg.what=FAILED;
                            msg.obj=result.getData();
                            handler.sendMessage(msg);
                            return;
                        }
                        return_count = Integer.parseInt(result.getData());
                        logger.d("===count=" + return_count);
                        final PageUtil pageUtil = new PageUtil(pageSize, return_count);
                        utils = new WebServiceUtils(getInfor, mContext);
                        int pageCount = pageUtil.getPageCount();
                        for (int i = 0; i < pageCount; i++) {
                            properties.clear();
                            pageUtil.setCurrentPage(i+1);
                            properties.put("pageSize", pageSize);
                            properties.put("currentPage", pageUtil.getCurrentPage());
                            properties.put("ProjectID", app.getSharedPreferences().getString
                                    (Constant
                                            .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
                            properties.put("BeginMonth", start);
                            properties.put("EndMonth", end);
                            utils.callWebService2(mContext, properties, new WebServiceUtils
                                    .WebServiceCallBack() {
                                @Override
                                public void callBack(ResultEntity result) {
                                    if(!result.isSuccess()){
                                        Message msg=new Message();
                                        msg.what=FAILED;
                                        msg.obj=result.getData();
                                        handler.sendMessage(msg);
                                        return;
                                    }
                                    return_data = result.getData();
                                    logger.d("====return_data===" + return_data);
//                                    Message msg = new Message();
//                                    msg.obj = return_data;
//                                    msg.what = STATE_PAGING;
//                                    logger.d("====STATE_PAGING===");
//                                    handler.sendMessage(msg);
                                    parseData = new ParseData();
                                    parseDataList = new ArrayList();
                                    logger.d("==================supply===" + return_data);

                                    parseDataList = parseData.ParseDataList(return_data, getKey(),
                                            CheckForm.class);
                                    if (checkFormDao == null) {
                                        checkFormDao = new CheckFormDao(mContext);
                                    }
                                    if (parseDataList.size() > 0) {
                                        downLoadList.addAll(parseDataList);
                                        logger.d("====insert==downLoadList" + downLoadList.size());
//                        checkFormDao.imInsertList(parseDataList);
                                        logger.d("====insert" + parseDataList.size());
                                    }else {
                                        Message msg = new Message();
                                        msg.what = STATE_NO_DATA;
                                        msg.obj = return_data;
                                        handler.sendMessage(msg);
                                    }
                                }
                            });
                        }

                        if (downLoadList.size()==return_count){
                            Message msg_success = new Message();
                            msg_success.what = STATE_SUCCESS;
                            msg_success.obj = return_count;
                            handler.sendMessage(msg_success);
                        }
                        if (return_count==0){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showMessage("无下载数据");
                                }
                            });
                        }
                        // 关闭进度条
                        if (cancleProdialog(result)) return;
                    }
                });
            }
        }).start();

    }


    private List<CheckForm> downLoadList = new ArrayList<CheckForm>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ResultEntity entity = (ResultEntity) msg.obj;
                    ProgressDialogUtils.dismissProgressDialog();
                    if (!entity.isSuccess()) {
                        new AlertDialog.Builder(mContext).setMessage("下载失败" + entity.getData())
                                .show();
                    }
                    break;
                case FAILED:
                    ProgressDialogUtils.dismissProgressDialog();
                    new AlertDialog.Builder(mContext).setMessage((String) msg.obj).show();
                    break;
                case STATE_SUCCESS:
                    alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setMessage("下载成功！" + "共计" + return_count
                            + "条数据").show();
                    logger.d("====insert==downLoadList=" + downLoadList.size());
                    adapter = new CheckUploadListAdapter(mContext, downLoadList);
                    listView.setAdapter(adapter);
                    updateCheckForm();
                    break;
                case STATE_NO_DATA:
                    new AlertDialog.Builder(mContext).setMessage("数据解析失败:" + ((String) msg
                            .obj)).show();
                    break;
                case STATE_PAGING:
                    parseData = new ParseData();
                    parseDataList = new ArrayList();
                    logger.d("==================supply===" + return_data);

                    parseDataList = parseData.ParseDataList(return_data, getKey(),
                            CheckForm.class);
                    if (checkFormDao == null) {
                        checkFormDao = new CheckFormDao(mContext);
                    }
                    if (parseDataList.size() > 0) {
                        downLoadList.addAll(parseDataList);
                        logger.d("====insert==downLoadList" + downLoadList.size());
//                        checkFormDao.imInsertList(parseDataList);
                        logger.d("====insert" + parseDataList.size());
                    } else {
                        msg = new Message();
                        msg.what = STATE_NO_DATA;
                        msg.obj = return_data;
                        handler.sendMessage(msg);
                    }
                    break;
            }
        }

    };


    private void updateCheckForm() {
//        checkFormDao.execSql("delete from " +
//                "checkForm where ProjectID=" + "\"" + ProjectID
//                + "\"" +
//                " and CheckMonth between " + "\""
//                + start + "\""
//                + " and" + "\"" + end + "\"", null);
        checkFormDao.imDeleteAll();
        checkFormDao.imInsertList(downLoadList,false);
    }
}