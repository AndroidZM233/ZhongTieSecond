package com.speedata.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.adapter.MetrailsGridViewAdapter;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.ResultEntity;
import com.speedata.dao.CommonLabourDao;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.speedata.utils.PageUtil;
import com.speedata.utils.ParseData;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.WebServiceUtils;
import com.speedata.utils.WebServiceUtils.WebServiceCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaintainInfomationActivity extends BaseActivity implements
        OnItemClickListener, OnClickListener {
    private List<String> provinceList = new ArrayList<String>();
    private GridView mGridView;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MetrailsGridViewAdapter adapter;
    private ImageView mBack;
    private WebServiceUtils utils;
    private String return_data;
    private int return_count = 0;
    private HashMap<String, Object> properties = new HashMap<String, Object>();
    private final static String TAG = "GetAllCommonSupplier";
    private Context mContext;
    private AlertDialog.Builder alertDialog;
    private ParseData parseData;
    private List parseDataList;
    private CommonSupplierDao commonSupplierDao;
    private CommonMaterialInfoDao commonMaterialInfoDao;
    private CommonLabourDao commonLabourDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_home_activity);
        mContext = this;
        properties.put("projectid", app.getSharedPreferences().getString(Constant
                .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
        initDB();
        initList();
        initView();
        setTitle("基础信息下载");
    }

    private void initDB() {
        commonSupplierDao = new CommonSupplierDao(mContext);
        commonMaterialInfoDao = new CommonMaterialInfoDao(mContext);
        commonLabourDao = new CommonLabourDao(mContext);


    }

    public void initList() {
        list.add("");//供应商信息下载
        list.add("");//用料单位下载
//        list.add("");//材料类别下载
        list.add("");//材料信息下载
        imgList.add(R.drawable.icon_down_supper);
        imgList.add(R.drawable.icon_down_user);
//        imgList.add(R.drawable.icon_merial_down);
        imgList.add(R.drawable.icon_down_information);
    }

    public void initView() {
        mGridView = (GridView) findViewById(R.id.include_Gv);
        adapter = new MetrailsGridViewAdapter(list, MaintainInfomationActivity.this, imgList);
//        adapter = new MaintainGridViewAdapter(list,
//                MaintainInfomationActivity.this);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MaintainInfomationActivity.this,
                OperatorsHomeActivity.class);
        startActivity(intent);
    }

    private MyLogger logger = MyLogger.jLog();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ResultEntity entity = (ResultEntity) msg.obj;
                    ProgressDialogUtils.dismissProgressDialog();
                    if (!entity.isSuccess()) {
//                new AlertDialog.Builder(mContext).setMessage("下载成功！").show();
//                logger.d(entity.getData());
                        new AlertDialog.Builder(mContext).setMessage("下载失败" + entity.getData()).show();
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
                    break;
                case STATE_NO_DATA:
                    new AlertDialog.Builder(mContext).setMessage("数据解析失败:" + ((String) msg
                            .obj)).show();
                    break;
                case STATE_PAGING:
//                    tempcount += ((int) msg.obj);
//                    ProgressDialogUtils.dismissProgressDialog();
//                    String data = (String) msg.obj;
                    parseData = new ParseData();
                    parseDataList = new ArrayList();
                    switch (CURENT_METHOD) {
                        case METHOD_SUPPLY:
                            //TODO 解析供应商数据 并插入数据库
                            logger.d("==================supply===" + return_data);

                            parseDataList = parseData.ParseDataList(return_data, getKey(),
                                    CommonSupplier.class);
                            if (commonSupplierDao == null) {
                                commonSupplierDao = new CommonSupplierDao(mContext);
                            }
                            if (parseDataList.size() > 0) {
                                commonSupplierDao.imInsertList(parseDataList);
                                logger.d("====insert" + parseDataList.size());
//                                msg = new Message();
//                                msg.what = STATE_SUCCESS;
//                                handler.sendMessage(msg);
                            } else {
                                msg = new Message();
                                msg.what = STATE_NO_DATA;
                                msg.obj = return_data;
                                handler.sendMessage(msg);
                            }
                            break;
                        case METHOD_MATERIAL:
                            //TODO 解析材料信息数据 并插入数据库
                            logger.d("====material===" + return_data);

//                            if (commonMaterialInfoDao == null) {
//                                commonMaterialInfoDao = new CommonMaterialInfoDao(mContext);
//                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    parseDataList = parseData.ParseDataList(return_data, getKey(),
                                            CommonMaterialInfo.class);
                                    if (commonMaterialInfoDao == null) {
                                        commonMaterialInfoDao = new CommonMaterialInfoDao(mContext);
                                    }
                                    Message msg = new Message();
                                    if (parseDataList.size() > 0) {
                                        commonMaterialInfoDao.imInsertList(parseDataList);
                                        logger.d("====insert" + parseDataList.size());
//                                        msg = new Message();
//                                        msg.what = STATE_SUCCESS;
//                                        msg.obj = return_count;
//                                        handler.sendMessage(msg);
                                    } else {

                                        msg.what = STATE_NO_DATA;
                                        msg.obj = return_data;
                                        handler.sendMessage(msg);
                                    }
                                }
                            }).start();

                            break;
                        case METHOD_LABOUR:
                            //TODO 解析施工队数据 并插入数据库
                            logger.d("==================labour===" + return_data);
                            parseDataList = parseData.ParseDataList(return_data, getKey(),
                                    CommonLabour.class);
                            if (commonLabourDao == null) {
                                commonLabourDao = new CommonLabourDao(mContext);
                            }
                            if (parseDataList.size() > 0) {
                                commonLabourDao.imInsertList(parseDataList);
                                logger.d("====insert" + parseDataList.size());
//                                msg = new Message();
//                                msg.what = STATE_SUCCESS;
//                                msg.obj = return_count;
//                                handler.sendMessage(msg);
                            } else {
                                msg = new Message();
                                msg.what = STATE_NO_DATA;
                                msg.obj = return_data;
                                handler.sendMessage(msg);
                            }
                            break;
                    }
                    break;
            }
//                    if (tempcount == sumCount) {
//                        ProgressDialogUtils.dismissProgressDialog();
//                        new AlertDialog.Builder(mContext).setMessage("下载成功！" + "共计" + sumCount
//                                + "条数据").show();
//                    } else {
//                        ProgressDialogUtils.showProgressDialog(mContext, "正在下载数据" + tempcount +
//                                "/" +
//                                sumCount);
//                    }

//                case STATE_START_PAGING:
////                    ProgressDialogUtils.dismissProgressDialog();
//                    ProgressDialogUtils.showProgressDialog(mContext, "正在下载数据" + "0/" + (int) msg
//                            .obj);
//                    break;
//            }

//            else {
//
//            }
        }
    };


    private final int STATE_SUCCESS = 1;
    private final int STATE_START_PAGING = 4;
    private final int STATE_PAGING = 3;
    private final int STATE_NO_DATA = 2;
    private final int FAILED = 5;
    private int sumCount = 0;
    private int tempcount = 0;

    public final int METHOD_SUPPLY = 0;
    private final int METHOD_MATERIAL = 2;//材料信息
    private final int METHOD_LABOUR = 1;//用料单位  施工队
    private int CURENT_METHOD = -1;

    // 根据点击的Item不同，下载不同的数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 显示进度条
        ProgressDialogUtils.showProgressDialog(this, "数据下载中...");
        switch (position) {
            case METHOD_SUPPLY:
                commonSupplierDao.imDeleteAll();
                //TODO 供应商信息下载
                CURENT_METHOD = METHOD_SUPPLY;
                downLoadInfors(HttpInterfaceModel.SUPER_FACTORY_METHOD_COUNT,
                        HttpInterfaceModel.SUPER_FACTORY_METHOD);

                break;
            case METHOD_LABOUR:
                commonLabourDao.imDeleteAll();
                // TODO 施工队信息下载
                CURENT_METHOD = METHOD_LABOUR;

                downLoadInfors(HttpInterfaceModel.GetCommonLabourServiceCount,
                        HttpInterfaceModel.GetCommonLabourByPage);

                break;
//            case 2:
//                // TODO 材料类别下载
//
//                break;
            case METHOD_MATERIAL:
                commonMaterialInfoDao.imDeleteAll();
                // TODO 材料信息下载  下载全部
                CURENT_METHOD = METHOD_MATERIAL;
                downLoadInfors(HttpInterfaceModel.GetCommonMaterialInfoCount,
                        HttpInterfaceModel.GetCommonMaterialInfo);
                break;
//            case 4:
//                // TODO 仓库信息下载
//                break;
        }
    }

    private int pageSize = 100;

//    public class AnsyDownTask extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(final Object[] params) {
//            utils = new WebServiceUtils((String) params[0], mContext);
//            properties.clear();
//            properties.put("projectid", app.getSharedPreferences().getString(Constant
//                    .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
//            utils.callWebService(mContext, properties, new WebServiceCallBack() {
//                @Override
//                public void callBack(ResultEntity result) {
//                    if (cancleProdialog(result)) return;
//                    return_count = Integer.parseInt(result.getData());
//                    logger.d("====return_count===" + return_count);
//                    PageUtil pageUtil = new PageUtil(pageSize, return_count);
//                    String strWhere = null;
//                    utils = new WebServiceUtils((String) params[1],
//                            mContext);
//                    for (int i = 1; i <= pageUtil.getPageCount(); i++) {
//                        properties.clear();
//                        pageUtil.setCurrentPage(i);
//                        properties.put("pageSize", pageSize);
//                        properties.put("currentPage", pageUtil.getCurrentPage());
//                        properties.put("strWhere", strWhere);
//                        properties.put("projectid", app.getSharedPreferences().getString
//                                (Constant
//                                        .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
//                        String temp = utils.callWebService(mContext, properties, new
//                                WebServiceCallBack() {
//                                    @Override
//                                    public void callBack(ResultEntity result) {
////                                return result.getData();
//                                    }
//                                });
//                        return temp;
//                    }
//                }
//            });
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//        }
//    }

    private String getKey() {
        return utils.getMETHOD() + "Result";
    }


    private void downLoadInfors(String getCountMethod, final String getInfor) {
        utils = new WebServiceUtils(getCountMethod, mContext);
        properties.clear();
        properties.put("projectid", app.getSharedPreferences().getString(Constant
                .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
        new Thread(new Runnable() {
            @Override
            public void run() {
                utils.callWebService(mContext, properties, new WebServiceCallBack() {
                    @Override
                    public void callBack(ResultEntity result) {
                        if (!result.isSuccess()) {
                            Message msg = new Message();
                            msg.what = FAILED;
                            msg.obj = result.getData();
                            handler.sendMessage(msg);
                            return;

                        }
                        return_count = Integer.parseInt(result.getData());
                        logger.d("====return_count===" + return_count);
                        final PageUtil pageUtil = new PageUtil(pageSize, return_count);
                        String strWhere = null;
                        utils = new WebServiceUtils(getInfor,
                                mContext);
                        for (int i = 1; i <= pageUtil.getPageCount(); i++) {
                            properties.clear();
                            pageUtil.setCurrentPage(i);
                            properties.put("pageSize", pageSize);
                            properties.put("currentPage", pageUtil.getCurrentPage());
                            properties.put("strWhere", strWhere);
                            properties.put("projectid", app.getSharedPreferences().getString
                                    (Constant.FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
                            utils.callWebService2(mContext, properties, new WebServiceCallBack() {
                                @Override
                                public void callBack(ResultEntity result) {
                                    return_data = result.getData();
                                    logger.d("====return_data===" + return_data);
                                    Message msg = new Message();
                                    msg.obj = return_data;
                                    msg.what = STATE_PAGING;
                                    handler.sendMessage(msg);
//                                    return return_data;
//                                    List<CommonSupper> listCommonInformation = JSON.parseArray
//                                            (return_data, CommonSupper.class);
//                                    if (commonSupperDao == null) {
//                                        commonSupperDao = new CommonSupperDao(mContext);
//                                    }
//                                    if (listCommonInformation.size() > 0) {
//                                        commonSupperDao.imDelete("SupplierTypeBh=?", new
//                                                String[]{"1"});
//                                        commonSupperDao.imInsertList(listCommonInformation);
//                                        logger.d("====insert" + listCommonInformation.size());
//                                        Message msg = new Message();
//                                        msg.what = STATE_SUCCESS;
//                                        msg.obj = listCommonInformation.size();
//                                        handler.sendMessage(msg);
//                                    } else {
//                                        Message msg = new Message();
//                                        msg.what = STATE_NO_DATA;
//                                        msg.obj = result.getData();
//                                        handler.sendMessage(msg);
//                                    }
                                }
                            });

                        }
                        // 关闭进度条
                        if (cancleProdialog(result)) return;
                        if (return_count > 0) {
                            Message msg = new Message();
                            msg.what = STATE_SUCCESS;
                            msg.obj = return_count;
                            handler.sendMessage(msg);
                        }
                    }
                });

            }
        }).start();
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

//    try {
//        FileUtil.write(MaintainInfomationActivity.this, "0",
//                return_data);
//    } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
}
