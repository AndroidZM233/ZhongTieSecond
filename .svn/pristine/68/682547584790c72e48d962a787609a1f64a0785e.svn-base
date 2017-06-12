package com.speedata.activity.allot;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
import com.speedata.bean.AllotRecord;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.bean.StoreBean;
import com.speedata.dao.AllotRegisterDao;
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

public class AllotRegisterActivity extends PrintActivity implements View.OnClickListener,
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
    private AllotRegisterDao allotRegisterDao;
    private AllotRecord allotRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();
    }


    private void initDB() {
        commonSupplierDao = new CommonSupplierDao(mContext);
        storeDao = new StoreDao(mContext);
        inRegisterDao = new InRegisterDao(mContext);
        allotRecord = new AllotRecord();
        allotRegisterDao = new AllotRegisterDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_out_register);
        setTitle("调拨");
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
            String result = String.format("%.4f", inputQuantity);
            double resultQuantity = Double.parseDouble(result);
            Double oldQuantity = Double.parseDouble(tvQuantity.getText().toString());
            if (inputQuantity > oldQuantity) {
                alertMessage("当前最大出库量为：" + oldQuantity);
                edv_Quantity.setText(oldQuantity + "");
                return;
            }
            if (storeBean == null) {
                showMessage("请先扫描条码");
                return;
            }
            String timeString = MyDateAndTime.getTimeString();
            allotRecord.setOrderTime(timeString);
            allotRecord.setInfoCode(edv_InfoCode.getText().toString());
            allotRecord.setInfoModel(edv_InfoModel.getText().toString());
            allotRecord.setInfoName(edv_InfoName.getText().toString());
            allotRecord.setInfoUnit(edv_InfoUnit.getText().toString());
            String labourName = supperList.get(spinnerSupper
                    .getSelectedItemPosition()).getLabourName();
            String labourNM = supperList.get(spinnerSupper
                    .getSelectedItemPosition()).getLabourNM();
            allotRecord.setLabourName(labourName);
            Log.d("====NM", String.valueOf(spinnerSupper.getSelectedItemPosition()));
            allotRecord.setLabourNM(labourNM);
            allotRecord.setRemark(edv_Remark.getText().toString());
            allotRecord.setQuantity(resultQuantity);
            allotRecord.setOrderDate(tv_RequisitionDate.getText().toString());
            allotRecord.setBarCode(mBarcode);
            allotRecord.setProjectID(ProjectID);
            allotRecord.setInfoName(storeBean.getInfoName());
            allotRecord.setInfoUnit(storeBean.getInfoUnit());
            allotRecord.setInfoModel(storeBean.getInfoModel());
            allotRecord.setInfoClassNodebh(storeBean.getInfoClassNodebh());
            allotRecord.setInfoClassName(storeBean.getInfoClassName());
            allotRecord.setFirstClassName(storeBean.getFirstClassName());
            allotRecord.setSecondClassName(storeBean.getSecondClassName());
            UUID uuid = UUID.randomUUID();
            allotRecord.setItemNM(String.valueOf(uuid));
            allotRegisterDao.imInsert(allotRecord, false);


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

    public String getProjectName() {
        commonMaterialInfom = new CommonMaterialInfoDao(mContext);
        List<CommonMaterialInfo> temp = commonMaterialInfom.imRawQuery("select distinct " +
                "ProjectName from " +
                "CommonMaterialInfo where" +
                " ProjectID=?", new String[]{storeBean.getProjectID()}, CommonMaterialInfo.class);
        if (temp.size() > 0) {
            return temp.get(0).getProjectName();
        } else {
            return "null";
        }
    }

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
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");

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
