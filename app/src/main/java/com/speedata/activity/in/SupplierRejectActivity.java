package com.speedata.activity.in;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.speedata.activity.BaseActivity;
import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintActivity;
import com.speedata.application.CustomerApplication;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StoreBean;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.SelectDateDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SupplierRejectActivity extends PrintActivity implements IBaseScanFragment, View.OnClickListener {
    private MyLogger logger = MyLogger.jLog();
    private List<StoreBean> storeList = new ArrayList<StoreBean>();
    private StoreDao storeDao;
    private TextView tv_RequisitionDate;
    private EditText edvProjectName, edv_InfoName, edv_InfoCode, edv_InfoModel,
            edv_InfoUnit, edv_Quantity, edv_PrintNumber,
            edv_Remark, edvTime, edv_Supplier, edv_inventory;
    private InRegister inRegister;
    private InRegisterDao inRegisterDao;
    private Button btn_register;
    private double quantity;
    private List<StoreBean> tempStore;
    private Double rQuantity;
    private String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();
    }

    private void initDB() {
        storeBean = new StoreBean();
        storeDao = new StoreDao(mContext);
        inRegister = new InRegister();
        inRegisterDao = new InRegisterDao(mContext);
    }


    private void initUI() {
        setContentView(R.layout.activity_supplier_reject);
        setTitle("供应商退料");
        storeDao = new StoreDao(this);
        edv_InfoName = (EditText) findViewById(R.id.edv_InfoName);
        edv_InfoCode = (EditText) findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (EditText) findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (EditText) findViewById(R.id.edv_InfoUnit);
        edv_Quantity = (EditText) findViewById(R.id.edv_Quantity);
        edv_Supplier = (EditText) findViewById(R.id.edv_Supplier);
        edv_inventory = (EditText) findViewById(R.id.edv_inventory);
        edv_PrintNumber = (EditText) findViewById(R.id.edv_PrintNumber);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        tv_RequisitionDate = (TextView) findViewById(R.id.edv_RequisitionDate);
        edv_Remark = (EditText) findViewById(R.id.edv_Remark);
        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));


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
            tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "条码不匹配", Toast.LENGTH_SHORT).show();
        }


    }

    private Double Quantity = 0.0;

    private void setUI(StoreBean storeBean) {
        edv_InfoName.setText(storeBean.getInfoName());
        edv_InfoCode.setText(storeBean.getInfoCode());
        edv_InfoModel.setText(storeBean.getInfoModel());
        edv_InfoUnit.setText(storeBean.getInfoUnit());
        edv_Supplier.setText(storeBean.getSupplierName());
        Quantity = storeBean.getQuantity();
        edv_inventory.setText(Quantity + "");
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
        } else if (v == btn_register) {
            if (edv_Quantity.getText().toString().equals("")) {
                Toast.makeText(mContext, "请填写数量", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double inputQuantity = Double.parseDouble(edv_Quantity.getText().toString());
                if (inputQuantity > Quantity) {
                    alertMessage("当前最大出库量为：" + Quantity);
                    edv_Quantity.setText(Quantity+"");
                    return;
                }

                if (storeBean == null) {
                    showMessage("请先扫描条码");
                    return;
                }
                UUID uuid=UUID.randomUUID();
                inRegister.setInfoModel(edv_InfoModel.getText().toString());
                inRegister.setInfoUnit(edv_InfoUnit.getText().toString());
                inRegister.setInfoName(edv_InfoName.getText().toString());
                inRegister.setInfoCode(edv_InfoCode.getText().toString());
                inRegister.setBarCode(mBarcode);
                inRegister.setProjectID(storeBean.getProjectID());
                inRegister.setSupplierNM(storeBean.getSupplierNM());
                inRegister.setSupplierName(storeBean.getSupplierName());
                inRegister.setItemNM(String.valueOf(uuid));
                inRegister.setFirstClassName(storeBean.getFirstClassName());
                inRegister.setSecondClassName(storeBean.getSecondClassName());
                inRegister.setInfoClassName(storeBean.getInfoClassName());
                inRegister.setInfoClassNodebh(storeBean.getInfoClassNodebh());
                inRegister.setBookPrice(storeBean.getBookPrice());
                inRegister.setRemark(storeBean.getRemark());
                quantity = Double.parseDouble(edv_Quantity.getText().toString());
                format = String .format("%.4f",quantity);
                rQuantity = Double.parseDouble(format);
                inRegister.setQuantity(-rQuantity);
                inRegister.setRequisitionDate(tv_RequisitionDate.getText().toString());
                Double oldQuantity = Double.parseDouble(edv_inventory.getText().toString());
                Double newQuantity = oldQuantity - rQuantity;
                String newResult = String .format("%.4f",newQuantity);
                storeDao.execSql("update store set Quantity=? where BarCode=? "
                        , new String[]{newResult, mBarcode});
                Toast.makeText(mContext, "登记成功", Toast.LENGTH_SHORT).show();
                inRegisterDao.imInsert(inRegister,false);
                printLable(mBarcode);
                clearUI();
            } catch (Exception e) {
                Toast.makeText(mContext, "请先扫描条码", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void clearUI() {
        edv_InfoCode.setText("");
        edv_InfoModel.setText("");
        edv_InfoName.setText("");
        edv_InfoUnit.setText("");
        edv_Quantity.setText("");
        edv_Supplier.setText("");
        edv_inventory.setText("");
    }
    private void printLable(String barcode) {
        //TODO 打印
        String temp = edv_PrintNumber.getText().toString();
        int printCount = 1;
        if ("".equalsIgnoreCase(temp)) {
            return;
        } else {
            printCount = Integer.parseInt
                    (temp);
            if(printCount<=0){
                return;
            }
        }

        String name = edv_InfoName
                .getText().toString();
        String spec = edv_InfoModel.getText().toString();
        String count = "-"+format;
        printInLable(ProjectName, edv_Supplier.getText().toString(), name,
                spec, count, barcode, printCount);

    }

    @Override
    public void showConnectState(String state) {

    }

    @Override
    public void showPrintName(String name) {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        edv_PrintNumber.setText("1");
        edv_Quantity.setText("");
        return super.onKeyDown(keyCode, event);
    }
}
