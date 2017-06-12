package com.speedata.activity.out;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.speedata.activity.BaseActivity;
import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.OutListAdapter;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.CommonSupplier;
import com.speedata.dao.CommonLabourDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.utils.ScanUtil;
import com.speedata.view.SelectDateDialog;
import com.speedata.view.SetOneDateDialog;
import com.speedata.adapter.UserRejectListAdapter;
import com.speedata.bean.OutRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StoreBean;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.utils.MyLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRejectActivity extends PrintActivity implements View.OnClickListener, IBaseScanFragment {
    private MyLogger logger = MyLogger.jLog();
    private com.speedata.bean.StoreBean StoreBean = new StoreBean();
    private CommonSupplierDao commonSupplierDao;
    private StoreDao storeDao;
    private InRegisterDao inRegisterDao;
    private List<CommonLabour> supperList;
    private EditText edvPrintNumber,
            edv_Quantity, edv_Remark;
    private TextView tv_RequisitionDate, edv_InfoName, edv_InfoCode,
            edv_InfoModel, edv_InfoUnit, tvQuantity, tv_allOut;
    private ArrayAdapter<String> supperAdapter;
    private String[] suppers;
    private Spinner spinnerSupper;
    private CommonLabour commonLabour;
    private CommonLabourDao commonLabourDao;
    private Button btnRegister;
    private String labourName;
    private Double oldQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();
    }


    private void initDB() {
        commonSupplierDao = new CommonSupplierDao(mContext);
        storeDao = new StoreDao(mContext);
        storeBean = new StoreBean();
        inRegisterDao = new InRegisterDao(mContext);
        outRegister = new OutRegister();
        outRegisterDao = new OutRegisterDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_out_register);
        setTitle("用料单位退料");


        edvBarcode = (EditText) findViewById(R.id.edv_BarCode);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        edv_InfoName = (TextView) findViewById(R.id.edv_InfoName);
        edv_InfoCode = (TextView) findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (TextView) findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (TextView) findViewById(R.id.edv_InfoUnit);
        tv_allOut = (TextView) findViewById(R.id.tv_allOut);
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
        tv_allOut.setText("累计发料：");
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
            try {
                double inputQuantity = Double.parseDouble(edv_Quantity.getText().toString());
                String result = String .format("%.4f", inputQuantity);
                double resultQuantity=Double.parseDouble(result);
                if (resultQuantity > Quantity) {
                    alertMessage("当前最大退货量为：" + Quantity);
                    edv_Quantity.setText(Quantity+"");
                    return;
                }
                if (storeBean == null) {
                    showMessage("请先扫描条码");
                    return;
                }
                String timeString = MyDateAndTime.getTimeString();
                List<StoreBean> tStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
                if (tStore.size() > 0) {
                    oldQuantity = tStore.get(0).getQuantity();
                }
                outRegister.setOrderTime(timeString);
                outRegister.setInfoCode(edv_InfoCode.getText().toString());
                outRegister.setInfoModel(edv_InfoModel.getText().toString());
                outRegister.setInfoName(edv_InfoName.getText().toString());
                outRegister.setInfoUnit(edv_InfoUnit.getText().toString());
                outRegister.setProjectID(ProjectID);
                labourName = supperList.get(spinnerSupper
                        .getSelectedItemPosition()).getLabourName();
                outRegister.setLabourName(labourName);
                String labourNM=supperList.get(spinnerSupper
                        .getSelectedItemPosition()).getLabourNM();
                outRegister.setLabourNM(labourNM);
                outRegister.setRemark(edv_Remark.getText().toString());
                outRegister.setQuantity(-resultQuantity);
                outRegister.setOrderDate(tv_RequisitionDate.getText().toString());
                outRegister.setBarCode(mBarcode);
                outRegister.setInfoClassNodebh(tStore.get(0).getInfoClassNodebh());
                outRegister.setInfoClassName(tStore.get(0).getInfoClassName());
                outRegister.setFirstClassName(tStore.get(0).getFirstClassName());
                outRegister.setSecondClassName(tStore.get(0).getSecondClassName());
                UUID uuid = UUID.randomUUID();
                outRegister.setItemNM(String.valueOf(uuid));


                Double newQuantity = oldQuantity + resultQuantity;
                String newResult = String.format("%.4f", newQuantity);
                storeDao.execSql("update store set Quantity=? where BarCode=? "
                        , new String[]{newResult, mBarcode});
                Toast.makeText(mContext, "登记成功", Toast.LENGTH_SHORT).show();
                outRegisterDao.imInsert(outRegister, false);
                String printCountString = edvPrintNumber.getText().toString();
                if (!"".equals(printCountString)) {
                    int printCount = Integer.parseInt(printCountString);
                    if (printCount > 0) {
                        printOutLable(ProjectName, spinnerSupper.getSelectedItem().toString(),
                                edv_InfoName.getText().toString(), edv_InfoModel.getText().toString(),
                                "-" + String.valueOf(resultQuantity), mBarcode, timeString, printCount);
                    }
                }
                clearUI();
            } catch (Exception e) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            }
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

    private EditText edvBarcode;

    //TODO 获取条码
    private String mBarcode;
    private StoreBean storeBean;
    private OutRegister outRegister;
    private OutRegisterDao outRegisterDao;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
        labourName = supperList.get(spinnerSupper
                .getSelectedItemPosition()).getLabourName();
        try {
            List<OutRegister> tempStore = outRegisterDao.imQueryList("BarCode=? and LabourName=?"
                    , new String[]{mBarcode, labourName});
            edvBarcode.setText(mBarcode);
            Quantity = 0.0;
            for (int i = 0; i < tempStore.size(); i++) {
                outRegister = tempStore.get(i);
                Quantity = Quantity + outRegister.getQuantity();
            }
            setUI(outRegister);
        } catch (Exception e) {
            Toast.makeText(mContext, "条码不匹配", Toast.LENGTH_SHORT).show();
            edvBarcode.setText("");
        }


    }

    private double Quantity = 0.0;

    private void setUI(OutRegister outRegister) {
        edv_InfoName.setText(outRegister.getInfoName());
        edv_InfoCode.setText(outRegister.getInfoCode());
        edv_InfoModel.setText(outRegister.getInfoModel());
        edv_InfoUnit.setText(outRegister.getInfoUnit());

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
