package com.speedata.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.speedata.adapter.MetrailsGridViewAdapter;
import com.speedata.bean.AllMetrailClass;
import com.speedata.bean.CommonForm;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.ResultEntity;
import com.speedata.bean.StorageInfomation;
import com.speedata.dao.AllMetrailClassDao;
import com.speedata.dao.CommonFormDao;
import com.speedata.dao.CommonInformationDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.StorageInfomationDao;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.WebServiceUtils;
import com.speedata.utils.WebServiceUtils.WebServiceCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MaintainInfomationActivityBackUp extends BaseActivity implements
        OnItemClickListener, OnClickListener {
    private List<String> provinceList = new ArrayList<String>();
    private GridView mGridView;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private MetrailsGridViewAdapter adapter;
    private ImageView mBack;
    private WebServiceUtils utils;
    //    private JSONUtils json;
    //    private List jsonList;
    private String return_data;
    private int return_count;
    private HashMap<String, Object> properties = new HashMap<String, Object>();
    private final static String TAG = "GetAllCommonSupplier";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operators_home_activity);
        mContext = this;
        properties.put("projectid", app.getSharedPreferences().getString(Constant
                .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
        initList();
        initView();
        setTitle("基础信息下载");
    }

    public void initList() {
        list.add("");//供应商信息下载
        list.add("");//用料单位下载
        list.add("");//材料类别下载
        list.add("");//材料信息下载
        list.add("");//仓库信息下载
        imgList.add(R.drawable.icon_down_supper);
        imgList.add(R.drawable.icon_down_user);
        imgList.add(R.drawable.icon_merial_down);
        imgList.add(R.drawable.icon_down_information);
        imgList.add(R.drawable.icon_down_store);
    }

    public void initView() {
        mGridView = (GridView) findViewById(R.id.include_Gv);
        adapter = new MetrailsGridViewAdapter(list, MaintainInfomationActivityBackUp.this, imgList);
//        adapter = new MaintainGridViewAdapter(list,
//                MaintainInfomationActivity.this);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MaintainInfomationActivityBackUp.this,
                OperatorsHomeActivity.class);
        startActivity(intent);
    }

    private AllMetrailClassDao allMetrailClassDao;//材料类别
    private CommonInformationDao commonInformationDao;//用料单位
    private CommonSupplierDao commonSupplierDao;
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
                        new AlertDialog.Builder(mContext).setMessage(entity.getData()).show();
                    }
                    break;
                case STATE_SUCCESS:
                    new AlertDialog.Builder(mContext).setMessage("下载成功！" + "共计" + ((int) msg.obj)
                            + "条数据").show();
                    break;
                case STATE_NO_DATA:
                    new AlertDialog.Builder(mContext).setMessage("数据解析失败:" + ((String) msg
                            .obj)).show();
                    break;
                case STATE_PAGING:
                    tempcount += ((int) msg.obj);
//                    ProgressDialogUtils.dismissProgressDialog();
                    if (tempcount == sumCount) {
                        ProgressDialogUtils.dismissProgressDialog();
                        new AlertDialog.Builder(mContext).setMessage("下载成功！" + "共计" + sumCount
                                + "条数据").show();
                    } else {
                        ProgressDialogUtils.showProgressDialog(mContext, "正在下载数据" + tempcount +
                                "/" +
                                sumCount);
                    }
                    break;
                case STATE_START_PAGING:
//                    ProgressDialogUtils.dismissProgressDialog();
                    ProgressDialogUtils.showProgressDialog(mContext, "正在下载数据" + "0/" + (int) msg
                            .obj);
                    break;
            }

//            else {
//
//            }
        }
    };

    private final int STATE_SUCCESS = 1;
    private final int STATE_START_PAGING = 4;
    private final int STATE_PAGING = 3;
    private final int STATE_NO_DATA = 2;
    private int sumCount = 0;
    private int tempcount = 0;

    // 根据点击的Item不同，下载不同的数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 显示进度条
        ProgressDialogUtils.showProgressDialog(this, "数据下载中...");
        switch (position) {
            case 0:
                // 供应商信息下载
//                getCommonSupplierCount();


                break;
            case 1:
                // 用料单位下载
//                properties.clear();
//                properties.put("projectid", app.getSharedPreferences().getString(Constant
//                        .FIELD_PROJETC, "00001"));
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        utils = new WebServiceUtils(HttpInterfaceModel.USER_COMPANY_METHOD,
//                                mContext);
//                        utils.callWebService(mContext, properties, new WebServiceCallBack() {
//
//                            @Override
//                            public void callBack(ResultEntity resultEntity) {
//                                // 关闭进度条
//                                if (cancleProdialog(resultEntity)) return;
//                                return_data = resultEntity.getData();
//                                List<CommonSupper> listCommonInformation = JSON.parseArray
//                                        (return_data, CommonSupper.class);
//                                if (commonSupperDao == null) {
//                                    commonSupperDao = new CommonSupperDao(mContext);
//                                }
//                                if (listCommonInformation.size() > 0) {
//                                    commonSupperDao.imDelete("SupplierTypeBh=?", new String[]{"1"});
//                                    commonSupperDao.imInsertList(listCommonInformation);
//                                    logger.d("====insert" + listCommonInformation.size());
//                                    Message msg = new Message();
//                                    msg.what = STATE_SUCCESS;
//                                    msg.obj = listCommonInformation.size();
//                                    handler.sendMessage(msg);
//                                } else {
//                                    Message msg = new Message();
//                                    msg.what = STATE_NO_DATA;
//                                    msg.obj = resultEntity.getData();
//                                    handler.sendMessage(msg);
//                                }
//
//                            }
//                        });
//                    }
//                }).start();

                break;
            case 2:
                // 材料类别下载
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        utils = new WebServiceUtils(
                                HttpInterfaceModel.METRAIL_CATEGORY_METHOD, mContext);
                        utils.callWebService(mContext, null, new WebServiceCallBack() {
                            @Override
                            public void callBack(ResultEntity result) {
                                // 关闭进度条
                                if (cancleProdialog(result)) return;
                                return_data = result.getData();
                                Log.e(TAG, result.getData());
//                                json = new JSONUtils();
//                                return_data = return_data
//                                        .replace("GetAllMaterialClassResponse", "");
//                                return_data = return_data.replace("=", ":");
//                                return_data = return_data.replace(";", "");
//                                return_data = return_data.replace("GetAllMaterialClassResult",
//                                        "\"GetAllMaterialClassResult\"");
//                                try {
//                                    FileUtil.write(MaintainInfomationActivity.this, "json",
//                                            return_data);
//                                } catch (IOException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                }
//                                List<CommonInformation> listCommonInformation = JSON
//                                        .parseArray(return_data, CommonInformation.class);
                                List<AllMetrailClass> list = JSON
                                        .parseArray(return_data, AllMetrailClass.class);//json
                                // .getSupperList(return_data);
                                if (list == null) {
                                    Log.e(TAG, "数组为空");
                                    return;
                                }
                                if (allMetrailClassDao == null) {
                                    allMetrailClassDao = new AllMetrailClassDao(mContext);
                                }
                                if (list.size() > 0) {
                                    allMetrailClassDao.imDeleteAll();
                                    Message msg = new Message();
                                    msg.what = STATE_SUCCESS;
                                    msg.obj = list.size();
                                    handler.sendMessage(msg);
                                    allMetrailClassDao.imInsertList(list);
                                } else {
                                    Message msg = new Message();
                                    msg.what = STATE_NO_DATA;
                                    msg.obj = result.getData();
                                    handler.sendMessage(msg);
                                }

                            }
                        });
                    }
                }).start();

                break;
            case 3:
                // 材料信息下载  下载全部
                utils = new WebServiceUtils(
                        HttpInterfaceModel.GetCommonMaterialInfoCount, mContext);
                tempcount = 0;
                sumCount = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        utils.callWebService(mContext, properties, new WebServiceCallBack() {
                            @Override
                            public void callBack(ResultEntity result) {
                                // 对字符串进行JSON解析
//                                if (cancleProdialog(result)) return;
                                if (!result.isSuccess()) {
                                    Message msg = new Message();
                                    msg.obj = result;
                                    msg.what = 0;
                                    handler.sendMessage(msg);
                                    return;
                                }
                                Log.e(TAG, result.toString());
                                return_data = result.getData();
                                int count = Integer.parseInt(result.getData().replace("[", "")
                                        .replace("]", "").replace(",", "").replace(" ", ""));
                                sumCount = count;
                                logger.d("======count=" + count);
                                Message msg = new Message();
                                msg.obj = sumCount;
                                msg.what = STATE_START_PAGING;
                                handler.sendMessage(msg);
                                final CommonFormDao commonFormDao = new CommonFormDao(mContext);
                                commonFormDao.imDeleteAll();
                                int temp_count = 0;
                                int j = 1;
                                for (int i = 0; i < sumCount; i = i + 100) {
                                    properties.clear();
                                    properties.put("pageSize", 100);
                                    properties.put("currentPage", j);
                                    j++;
                                    properties.put("projectid", app.getSharedPreferences()
                                            .getString(Constant.FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
                                    utils = new WebServiceUtils(
                                            HttpInterfaceModel.GetCommonMaterialInfoByPage,
                                            mContext);
                                    utils.callWebService2(mContext, properties, new
                                            WebServiceCallBack() {
                                                @Override
                                                public void callBack(ResultEntity result) {
                                                    logger.d("");
                                                    return_data = result.getData();
                                                    logger.d(result.getData());
                                                    JSONObject parse1 = JSON.parseObject
                                                            (return_data);
                                                    String Results = parse1.getString
                                                            ("GetCommonMaterialInfoByPageResult");
                                                    String temp = parse1.getString
                                                            ("Count");
//                                                    logger.d("======tempcount=" + temp);
//                                                    int getCount = Integer.parseInt(temp);
                                                    List<CommonForm> list = JSON
                                                            .parseArray(Results,
                                                                    CommonForm
                                                                            .class);
                                                    if (list.size() > 0) {
                                                        Message msg = new Message();
                                                        msg.what = STATE_PAGING;
                                                        msg.obj = list.size();
                                                        handler.sendMessage(msg);
                                                        logger.d("======handler.sendMessage=" +
                                                                list.size());
                                                        commonFormDao.imInsertList(list);
                                                    } else {
                                                        Message msg = new Message();
                                                        msg.what = STATE_NO_DATA;
                                                        msg.obj = result.getData();
                                                        handler.sendMessage(msg);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                    }
                }).start();

                break;
            case 4:
                // 仓库信息下载
                utils = new WebServiceUtils(HttpInterfaceModel.GetCKInformationInfo, mContext);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        utils.callWebService(mContext, properties, new WebServiceCallBack() {

                            @Override
                            public void callBack(ResultEntity result) {
                                // 关闭进度条
                                if (cancleProdialog(result)) return;
                                Log.e(TAG, result.toString());
                                return_data = result.getData();
                                List<StorageInfomation> list = JSON
                                        .parseArray(return_data, StorageInfomation.class);
//                                List<StorageInfomation> list = JSON.parseArray(return_data,
//                                        StorageInfomation.class);
                                StorageInfomationDao storageInfomationDao = new
                                        StorageInfomationDao(mContext);
                                if (list.size() > 0) {
                                    storageInfomationDao.imDeleteAll();
                                    storageInfomationDao.imInsertList(list);
                                    Message msg = new Message();
                                    msg.what = STATE_SUCCESS;
                                    msg.obj = list.size();
                                    handler.sendMessage(msg);
//                                    handler.sendEmptyMessage(STATE_SUCCESS);
                                } else {
                                    Message msg = new Message();
                                    msg.what = STATE_NO_DATA;
                                    msg.obj = result.getData();
                                    handler.sendMessage(msg);

                                }

                            }
                        });
                    }
                }).start();
                break;
        }
    }

    private void getCommonSupplierCount() {
        utils = new WebServiceUtils(HttpInterfaceModel.SUPER_FACTORY_METHOD_COUNT, mContext);
        properties.clear();
        properties.put("projectid", app.getSharedPreferences().getString(Constant
                .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
        new Thread(new Runnable() {
            @Override
            public void run() {
                utils.callWebService(mContext, properties, new WebServiceCallBack() {
                    @Override
                    public void callBack(ResultEntity result) {
                        // 关闭进度条
                        if (cancleProdialog(result)) return;
                        return_count = Integer.parseInt(result.getData());
                        logger.d("====return_count==="+return_count);

                        int pageSize=1;
                        int currentPage=0;
                        String strWhere=null;
                        utils = new WebServiceUtils(HttpInterfaceModel.SUPER_FACTORY_METHOD, mContext);
                        for (int i = 1; i <=return_count/pageSize ; i++) {
                            properties.clear();
                            currentPage = i;
                            properties.put("pageSize", pageSize);
                            properties.put("currentPage", currentPage);
                            properties.put("strWhere", strWhere);
                            properties.put("projectid", app.getSharedPreferences().getString(Constant
                                    .FIELD_PROJETC, Constant.FIELD_DEFAULT_PROJETC));
                            return_data = result.getData();

                            logger.d("====return_data===" + return_data);
                            List<CommonSupplier> listCommonInformation = JSON.parseArray
                                    (return_data, CommonSupplier.class);
                            if (commonSupplierDao == null) {
                                commonSupplierDao = new CommonSupplierDao(mContext);
                            }
                            if (listCommonInformation.size() > 0) {
                                commonSupplierDao.imDelete("SupplierTypeBh=?", new String[]{"1"});
                                commonSupplierDao.imInsertList(listCommonInformation);
                                logger.d("====insert" + listCommonInformation.size());
                                Message msg = new Message();
                                msg.what = STATE_SUCCESS;
                                msg.obj = listCommonInformation.size();
                                handler.sendMessage(msg);
                            } else {
                                Message msg = new Message();
                                msg.what = STATE_NO_DATA;
                                msg.obj = result.getData();
                                handler.sendMessage(msg);
                            }
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
