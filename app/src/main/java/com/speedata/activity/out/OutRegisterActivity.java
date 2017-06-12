package com.speedata.activity.out;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.OutListAdapter;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.bean.StoreBean;
import com.speedata.dao.CommonLabourDao;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.SelectDateDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OutRegisterActivity extends PrintActivity implements View.OnClickListener,
        IBaseScanFragment {
    private MyLogger logger = MyLogger.jLog();
    private List<StoreBean> storeTempList = new ArrayList<StoreBean>();
    private List<StoreBean> storeList = new ArrayList<StoreBean>();
    private CommonSupplierDao commonSupplierDao;
    private InRegisterDao inRegisterDao;
    private List<CommonLabour> supperList;
    private ListView listView;
    private OutListAdapter outListAdapter;
    private EditText edvProjectName, edvPrintNumber,
            edv_CKNodebh, edv_Quantity, edv_Remark, edvTime;
    private TextView tv_RequisitionDate, edv_InfoName, edv_InfoCode,
            edv_InfoModel, edv_InfoUnit, tvQuantity;
    private ArrayAdapter<String> supperAdapter;
    private String[] suppers;
    private Spinner spinnerSupper;
    private CommonLabour commonLabour;
    private CommonLabourDao commonLabourDao;
    private Button btnRegister;
    private EditText edvBarcode;
    private StoreDao storeDao;
    private OutRegisterDao outRegisterDao;

//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        public void onReceive(android.content.Context context,
//                              android.content.Intent intent) {
//            String action = intent.getAction();
//            if (action.equals(Constant.RECE_DATA_ACTION)) {
//                String data = intent.getStringExtra("se4500");
//                edv_InfoCode.setText(data);
//                edv_InfoCode.setEnabled(false);
//                StoreBean.setInfoCode(data);
//                Message msg = new Message();
//                msg.obj = data;
//                handlerShowData.sendMessage(msg);
//
//            }
//        }
//    };

    //    private Handler handlerShowData = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String data = (String) msg.obj;
////            String temp_supp_id = supperList.get(spinnerSupper.getSelectedItemPosition())
////                    .getSupplierID() + "";
//            storeList = storeDao.imRawQuery("select * from store where InfoCode=? and " +
//                    "ProjectID=? and Quantity>?", new String[]{data,
//                    app
//                            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
//                            .FIELD_DEFAULT_PROJETC), "0"}, com.speedata.bean
//                    .StoreBean.class);
//            outListAdapter = new OutListAdapter(mContext, storeList);
//            listView.setAdapter(outListAdapter);
//            if (storeList.size() != 0) {
//                StoreBean form = storeList.get(0);
//                edv_InfoModel.setText(form.getInfoModel());
//                edv_InfoUnit.setText(form.getInfoUnit());
//                edv_Remark.setText(form.getRemark());
//                edv_InfoName.setText(form.getInfoName());
////                    edvProjectName.setText(form.getProjectName());
//                StoreBean.setInfoModel(form.getInfoModel());
//                StoreBean.setInfoUnit(form.getInfoUnit());
//                StoreBean.setInfoName(form.getInfoName());
//                StoreBean.setProjectID(form.getProjectID());
//                StoreBean.setRemark(form.getRemark());
//                edv_Quantity.setFocusable(true);
//                edv_Quantity.requestFocus();
//            } else {
//                Toast.makeText(mContext, "没有库存", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
    private OutRegister outRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();
        IntentFilter iFilter = new IntentFilter();
        //注册系统广播  接受扫描到的数据
//        iFilter.addAction(Constant.RECE_DATA_ACTION);
//        registerReceiver(receiver, iFilter);

    }


    private void initDB() {
        commonSupplierDao = new CommonSupplierDao(mContext);
        storeDao = new StoreDao(mContext);
        inRegisterDao = new InRegisterDao(mContext);
        outRegister = new OutRegister();
        outRegisterDao = new OutRegisterDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_out_register);
        setTitle("发料");


        edvBarcode = (EditText) findViewById(R.id.edv_BarCode);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        edv_InfoName = (TextView) findViewById(R.id.edv_InfoName);
        edv_InfoCode = (TextView) findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (TextView) findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (TextView) findViewById(R.id.edv_InfoUnit);
        edv_Quantity = (EditText) findViewById(R.id.edv_Quantity);
        edvPrintNumber = (EditText) findViewById(R.id.edv_printNumber);
        tvQuantity = (TextView) findViewById(R.id.tv_Quantity);
        tv_RequisitionDate = (TextView) findViewById(R.id.edv_RequisitionDate);
        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
        edv_Remark = (EditText) findViewById(R.id.edv_Remark);
        commonLabour = new CommonLabour();
        commonLabourDao = new CommonLabourDao(mContext);
        supperList = commonLabourDao.imRawQuery("select distinct LabourName,LabourNM from commonLabour"
                , null, CommonLabour.class);//查询所有用料单位
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mContext, "无用料单位信息，请先进行下载!", Toast.LENGTH_SHORT).show();
        }
        suppers = new String[supperList.size()];
        for (int i = 0; i < supperList.size(); i++) {
            suppers[i] = supperList.get(i).getLabourName();
        }
        spinnerSupper = (Spinner) findViewById(R.id.sp_user);
        supperAdapter = new ArrayAdapter<String>(mContext, android.R.layout
                .simple_dropdown_item_1line, suppers);
        spinnerSupper.setAdapter(supperAdapter);
        spinnerSupper.setSelection(sharedPreferences.getInt("outSelection", 0));
        spinnerSupper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putInt("outSelection", position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        listView = (ListView) findViewById(R.id.list_supplier_reject);
//        outListAdapter = new OutListAdapter(mContext, storeList);
//        listView.setAdapter(outListAdapter);

//        spinnerSupper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String InfoCode = edv_InfoCode.getText().toString();
//                if (InfoCode.equals("")) {
//                    return;
//                }
//                storeList = storeDao.imQueryList("InfoCode=? and " +
//                        "SupplierID=?", new String[]{InfoCode,
//                        supperList.get(position).getSupplierID() + ""});
//                if (storeList.size() == 0) {
//                    Toast.makeText(mContext, "没有查询到数据", Toast.LENGTH_SHORT).show();
//                    storeList.clear();
//                    outListAdapter.refresh();
//                }
//                outListAdapter.refresh();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long
// id) {
//                //点击某一条数据   显示详细信息  并输入数量  进行供应商退料操作
//                if(supperList==null||supperList.size()==0){
//                    Toast.makeText(mContext, "无供应商信息，请先进行下载!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                final com.speedata.bean.StoreBean oldStore = storeList.get
//                        (position);
//                final double oldCount = oldStore.getQuantity();
//                SupplierRejectDialog dialog = new SupplierRejectDialog(mContext, new
//                        SupplierRejectDialog.CallBack() {
//                            @Override
//                            public void callBack(StoreBean storeBean) {
//                                double quantity;
//                                if (outRegisterDao == null) {
//                                    outRegisterDao = new OutRegisterDao(mContext);
//                                }
//                                OutRegister outRegister = new OutRegister();
//                                outRegister.setBarCode(storeBean.getBarCode());
////                                outRegister.setBigClassName(storeBean.getBigClassName());//TODO
////                                outRegister.setCKAbbName(storeBean.getCKAbbName());
////                                outRegister.setClassName(storeBean.getClassName());
////                                outRegister.setCKNodebh(storeBean.getCKNodebh());
////                                outRegister.setClassNodebh(storeBean.getClassNodebh());
//                                outRegister.setInfoCode(storeBean.getInfoCode());
//                                outRegister.setInfoName(storeBean.getInfoName());
//                                outRegister.setInfoUnit(storeBean.getInfoUnit());
//                                outRegister.setInfoModel(storeBean.getInfoModel());
////                                outRegister.setItemID(MyDateAndTime.GetItemId());
////                                outRegister.setLabourID(supperList.get(spinnerSupper
////                                        .getSelectedItemPosition()).getSupplierID());
//                                outRegister.setLabourName(supperList.get(spinnerSupper
//                                        .getSelectedItemPosition()).getLabourName());
////                                outRegister.setSupplierID(storeBean.getSupplierID());
////                                outRegister.setSupplierName(storeBean.getSupplierName());
//                                outRegister.setRemark(storeBean.getRemark());
//                                outRegister.setProjectID(storeBean.getProjectID());
//                                outRegister.setOrderDate(tv_RequisitionDate.getText()
//                                        .toString());//可选日期
////                                outRegister.setMaker(app.getSharedPreferences().getString
////                                        (Constant.FIELD_USER, "张三"));
////                                outRegister.setMakerDate(MyDateAndTime.getMakerDate());
////                                outRegister.setFlag(app.getIMEI());
////                                    if (m_RadioOut.isChecked()) {
//                                quantity = oldCount - storeBean.getQuantity();
//                                outRegister.setQuantity(storeBean.getQuantity());
////                                    } else {
////                                        quantity = oldCount + storeBean.getQuantity();
////                                        storeBean.setQuantity(-(storeBean.getQuantity()));
////                                    }
//                                outRegisterDao.imInsert(outRegister);
//                                storeDao.execSql("update store set Quantity=? where " +
//                                        "ItemID=?", new
//                                        String[]{quantity + "", oldStore.getItemID() + ""});
//                                if (quantity == 0) {
//                                    storeList.remove(position);
//                                } else {
//                                    storeBean.setQuantity(quantity);
//                                    storeList.set(position, storeBean);
//                                }
//                                outListAdapter.refresh();
//                            }
//
//                            @Override
//                            public void intCallBack(int count) {
//
//                            }
//                        }, oldStore);
//                dialog.setTitle("发料确认单");
//                dialog.show();
//            }
//        });
    }



    @Override
    public void onClick(View v) {
        if (tv_RequisitionDate == v) {
            SelectDateDialog dateDialog = new SelectDateDialog(mContext, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tv_RequisitionDate.setText(time);
                }
            }, tv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == btnRegister) {
            if (edv_Quantity.getText().toString().equals("")) {
                Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
                return;
            }

            double inputQuantity = Double.parseDouble(edv_Quantity.getText().toString());
            String result = String .format("%.4f", inputQuantity);
            double resultQuantity=Double.parseDouble(result);
            Double oldQuantity = Double.parseDouble(tvQuantity.getText().toString());
            if (resultQuantity > oldQuantity) {
                alertMessage("当前最大出库量为：" + oldQuantity);
                edv_Quantity.setText(oldQuantity+"");
                return;
            }
//            try {
//            String barcode = edvBarcode.getText().toString();
            if (storeBean == null) {
                showMessage("请先扫描条码");
                return;
            }
            String timeString = MyDateAndTime.getTimeString();
            outRegister.setOrderTime(timeString);
            outRegister.setInfoCode(edv_InfoCode.getText().toString());
            outRegister.setInfoModel(edv_InfoModel.getText().toString());
            outRegister.setInfoName(edv_InfoName.getText().toString());
            outRegister.setInfoUnit(edv_InfoUnit.getText().toString());
            String labourName = supperList.get(spinnerSupper
                    .getSelectedItemPosition()).getLabourName();
            outRegister.setLabourName(labourName);
            String labourNM=supperList.get(spinnerSupper
                    .getSelectedItemPosition()).getLabourNM();
            outRegister.setLabourNM(labourNM);
            outRegister.setRemark(edv_Remark.getText().toString());
            outRegister.setQuantity(resultQuantity);
            outRegister.setOrderDate(tv_RequisitionDate.getText().toString());
            outRegister.setBarCode(mBarcode);
            outRegister.setProjectID(ProjectID);
            outRegister.setInfoName(storeBean.getInfoName());
            outRegister.setInfoUnit(storeBean.getInfoUnit());
            outRegister.setInfoModel(storeBean.getInfoModel());
            UUID uuid=UUID.randomUUID();
            outRegister.setItemNM(String.valueOf(uuid));
            outRegister.setInfoClassNodebh(storeBean.getInfoClassNodebh());
            outRegister.setInfoClassName(storeBean.getInfoClassName());
            outRegister.setFirstClassName(storeBean.getFirstClassName());
            outRegister.setSecondClassName(storeBean.getSecondClassName());
            outRegisterDao.imInsert(outRegister,false);

            Double newQuantity = oldQuantity - resultQuantity;
            String newResult = String.format("%.4f", newQuantity);
            storeDao.execSql("update store set Quantity=? where BarCode=? "
                    , new String[]{newResult, mBarcode});
            Toast.makeText(mContext, "出库成功", Toast.LENGTH_SHORT).show();
            String printCountString = edvPrintNumber.getText().toString();
            if (!"".equals(printCountString)) {
                int printCount = Integer.parseInt(printCountString);
                if (printCount > 0) {
                    printOutLable(ProjectName, spinnerSupper.getSelectedItem().toString(),
                            edv_InfoName.getText().toString(), edv_InfoModel.getText().toString(),
                            String.valueOf(resultQuantity), mBarcode, timeString, printCount);
                }
            }
            clearUI();

        }
    }

    public CommonMaterialInfoDao commonMaterialInfom;

//    public String getProjectName() {
//        commonMaterialInfom = new CommonMaterialInfoDao(mContext);
//        List<CommonMaterialInfo> temp = commonMaterialInfom.imRawQuery("select distinct " +
//                "ProjectName from " +
//                "CommonMaterialInfo where" +
//                " ProjectID=?", new String[]{storeBean.getProjectID()}, CommonMaterialInfo.class);
//        if (temp.size() > 0) {
//            return temp.get(0).getProjectName();
//        } else {
//            return "null";
//        }
//    }

    private void clearUI() {
        edv_InfoCode.setText("");
        edv_InfoModel.setText("");
        edv_InfoName.setText("");
        edv_InfoUnit.setText("");
        tvQuantity.setText("");
        edv_Remark.setText("");
        edv_Quantity.setText("");
        edvBarcode.setText("");
    }


    private ScanUtil scanUtil;

    @Override
    protected void onResume() {
        super.onResume();
        scanUtil = new ScanUtil(this);
        scanUtil.setOnScanListener(new ScanUtil.OnScanListener() {

            @Override
            public void getBarcode(String barcode) {
                onGetBarcode(barcode);
            }
        });
    }

    @Override
    public void onPause() {
        scanUtil.stopScan();
        super.onPause();
    }


    //TODO 获取条码
    private String mBarcode;


    private StoreBean storeBean;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n","");
        mBarcode = mBarcode.replace("\r","");

        try {
            List<StoreBean> tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            edvBarcode.setText(mBarcode);
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "条码不匹配", Toast.LENGTH_SHORT).show();
            edvBarcode.setText("");
        }


    }

    private double Quantity = 0.0;

    private void setUI(StoreBean storeBean) {
        edv_InfoName.setText(storeBean.getInfoName());
        edv_InfoCode.setText(storeBean.getInfoCode());
        edv_InfoModel.setText(storeBean.getInfoModel());
        edv_InfoUnit.setText(storeBean.getInfoUnit());
        edv_Remark.setText(storeBean.getRemark());
        Quantity = storeBean.getQuantity();
        tvQuantity.setText(Quantity + "");
    }

    @Override
    public void showConnectState(String state) {

    }

    @Override
    public void showPrintName(String name) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        edvPrintNumber.setText("1");
        edv_Quantity.setText("");
        edv_Remark.setText("");
        return super.onKeyDown(keyCode, event);
    }
}
