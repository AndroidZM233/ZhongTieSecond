package com.speedata.activity.in;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.PrinterListAdapter;
import com.speedata.application.CustomerApplication;
import com.speedata.bean.AllMetrailClass;
import com.speedata.bean.CommonForm;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StorageInfomation;
import com.speedata.bean.StoreBean;
import com.speedata.dao.AllMetrailClassDao;
import com.speedata.dao.CommonFormDao;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.StorageInfomationDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.MetrailTypeDialog;
import com.speedata.view.SelectDateDialog;
import com.zhy.bean.FileBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InRegisterActivity extends PrintActivity implements IBaseScanFragment, View.OnClickListener, View
        .OnTouchListener {

    private ImageView imageBack;
    private CommonSupplierDao commonSupplierDao;
    private CommonFormDao commonFormDao;
    InRegister inRegister = new InRegister();
    List<CommonForm> commonFormList;
    private CustomerApplication app;
    private CommonMaterialInfoDao commonMaterialInfoDao;
    private String InfoClassNodebh;
    private String quantity;
    private String bookPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeDao = new StoreDao(mContext);
        initUI();
        commonFormDao = new CommonFormDao(this);
        app = (CustomerApplication) getApplication();

    }

    private AutoCompleteTextView autoEditextSupper;

    private Spinner spinnerSupper, spMetrail, spCKNodebh;
    private ArrayAdapter<String> supperAdapter;//, adpaterInforName;//, CKNodebhAdapter,
    private String[] suppers, CKNodebhs, Metrails, inforNames;
    private EditText edv_Quantity, edvBookPrice, edvPrintNumber, edv_Remark;
    private TextView edv_CKNodebh, edv_Metrail, tv_RequisitionDate,
            tvProjectID, tvInfoCode, tvInfoModel, tvInfoUnit;
    private Button btnRegister;
    private List<CommonSupplier> supperList;
    private List<StorageInfomation> StorageList;
    private List<AllMetrailClass> allMetrailClassList;
    private ListView listView;
    private PrinterListAdapter adapter;
    private CommonMaterialInfo form;

    private void initUI() {
        setContentView(R.layout.activity_in_register);
        if (commonSupplierDao == null) {
            commonSupplierDao = new CommonSupplierDao(this);
        }
        tvInfoCode = (TextView) findViewById(R.id.tv_InfoCode);
        tvInfoModel = (TextView) findViewById(R.id.tv_InfoModel);
        tvInfoUnit = (TextView) findViewById(R.id.tv_InfoUnit);
        tvProjectID = (TextView) findViewById(R.id.tv_ProjectID);
        edv_Metrail = (TextView) findViewById(R.id.edv_Metrail);
        edvBookPrice = (EditText) findViewById(R.id.edv_BookPrice);
        edvPrintNumber = (EditText) findViewById(R.id.edv_printNumber);
        edv_Metrail.setOnClickListener(this);
        edv_Quantity = (EditText) findViewById(R.id.edv_Quantity);
        tv_RequisitionDate = (TextView) findViewById(R.id.edv_RequisitionDate);
        edv_Remark = (EditText) findViewById(R.id.edv_Remark);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        supperList = commonSupplierDao.imQueryList("ProjectID=?", new
                String[]{ProjectID});//查询所有供应商
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mContext, "无供应商信息，请先进行下载!", Toast.LENGTH_SHORT).show();
        }
        suppers = new String[supperList.size()];
        for (int i = 0; i < supperList.size(); i++) {
            suppers[i] = supperList.get(i).getSupplierName();
        }
        imageBack = getBackBtn();
        imageBack.setOnClickListener(this);
        spinnerSupper = (Spinner) findViewById(R.id.sp_supplier);
        supperAdapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_dropdown_item_1line, suppers);
        spinnerSupper.setAdapter(supperAdapter);
        spinnerSupper.setSelection(sharedPreferences.getInt("inSelection", 0));
        spinnerSupper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putInt("inSelection", position).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setTitle("进料登记");
        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
    }


    private void resetView() {
        edv_Quantity.setText("");
        edv_Remark.setText("");
    }

    private InRegisterDao inRegisterDao;
    private StoreDao storeDao;
    private StorageInfomationDao storageInfomationDao;
    StorageInfomation storageInfomation;
    private AllMetrailClassDao allMetrailClassDao;
    private AllMetrailClass allMetrailClass;
    private CommonMaterialInfo commonMaterialInfo;
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    @Override
    public void onClick(View v) {
        if (v == edv_Metrail) {
            MetrailTypeDialog metrailTypeDialog = new MetrailTypeDialog(mContext,mDatas,new
                    MetrailTypeDialog.CallBack() {
                        @Override
                        public void callBack(final String ClassNodebh) {

                            logger.d("===mater=" + ClassNodebh);
                            //TODO 查询条件应改为InfoClassNodebh
                            commonMaterialInfoDao = new CommonMaterialInfoDao(mContext);
                            final List<CommonMaterialInfo> materialList = commonMaterialInfoDao
                                    .imRawQuery("select * " +
                                            "from commonMaterialInfo where InfoClassName = ?"
                                            , new String[]{ClassNodebh}, CommonMaterialInfo.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    edv_Metrail.setText(ClassNodebh);
                                    logger.d("===materialList=" + materialList.size());
                                    if (materialList.size() <= 0) {
                                        Toast.makeText(mContext, "ERROR", Toast.LENGTH_SHORT)
                                                .show();
                                        return;
                                    }
                                    commonMaterialInfo = materialList.get(0);
                                    tvInfoUnit.setText(commonMaterialInfo.getInfoUnit());
                                    tvInfoCode.setText(commonMaterialInfo.getInfoCode());
                                    tvInfoModel.setText(commonMaterialInfo.getInfoModel());
                                    tvProjectID.setText(commonMaterialInfo.getProjectID());

                                }
                            });

                        }
                    });
            metrailTypeDialog.setTitle("请选择材料类别");
            metrailTypeDialog.show();
        }

        if (tv_RequisitionDate == v) {
            SelectDateDialog dateDialog = new SelectDateDialog(mContext, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tv_RequisitionDate.setText(time);
                }
            }, tv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == imageBack) {
            finish();
        } else if (v == btnRegister) {
            inRegister();
        }
    }

    private void inRegister() {
        String price = edvBookPrice.getText().toString();
        if (!TextUtils.isEmpty(price)) {
            bookPrice = String.format("%.8f", Double.parseDouble(price));
        }

        if (edv_Metrail == null) {
            Toast.makeText(mContext, "请选择登记物资", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edv_Quantity.getText().toString().equals("")) {
            Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
            return;
        }
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mContext, "无供应商信息，请先进行下载!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (commonMaterialInfo == null) {
            commonMaterialInfo = new CommonMaterialInfo();
        }
        if (inRegisterDao == null) {
            inRegisterDao = new InRegisterDao(this);
        }

        String barcode = MyDateAndTime.getBatchCode(this);
        UUID uuid = UUID.randomUUID();
        inRegister.setItemNM(String.valueOf(uuid));
        inRegister.setInfoModel(tvInfoModel.getText().toString());
        inRegister.setInfoUnit(tvInfoUnit.getText().toString());
        inRegister.setInfoName(edv_Metrail.getText().toString());
        inRegister.setProjectID(tvProjectID.getText().toString());
        inRegister.setRemark(edv_Remark.getText().toString());
        inRegister.setInfoCode(tvInfoCode.getText().toString());
        inRegister.setInfoClassName(commonMaterialInfo.getInfoClassName());
        inRegister.setInfoClassNodebh(commonMaterialInfo.getInfoClassNodebh());
        inRegister.setFirstClassName(commonMaterialInfo.getFirstClassName());
        inRegister.setSecondClassName(commonMaterialInfo.getSecondClassName());
        if (!TextUtils.isEmpty(bookPrice)) {
            inRegister.setBookPrice(Double.parseDouble(bookPrice));
        }
        inRegister.setBarCode(barcode);//批次码  时间戳
        inRegister.setRequisitionDate(tv_RequisitionDate.getText().toString());//TODO 进料日期
        inRegister.setSupplierName(supperList.get(spinnerSupper
                .getSelectedItemPosition()).getSupplierName());//供应商名称

        String SupplierNM = supperList.get
                (spinnerSupper
                        .getSelectedItemPosition()).getSupplierNM();
        inRegister.setSupplierNM(SupplierNM);//供应商编码
        logger.d("====IMEI====" + app.getIMEI());
        quantity = String.format("%.4f", Double.parseDouble(edv_Quantity.getText().toString()));
        double count = Double.parseDouble(quantity);
        inRegister.setQuantity(count);
        logger.d("===inRegister=" + inRegister);
        StoreBean storeBean = new StoreBean();
        storeBean.setMakerDate(MyDateAndTime.getMakerDate());
        storeBean.setBarCode(barcode);//批次码  时间戳
        storeBean.setRequisitionDate(tv_RequisitionDate.getText().toString());//TODO 进料日期
        storeBean.setSupplierName(supperList.get(spinnerSupper
                .getSelectedItemPosition()).getSupplierName());//供应商名称
        storeBean.setQuantity(count);
        storeBean.setSupplierNM(supperList.get(spinnerSupper
                .getSelectedItemPosition()).getSupplierNM());
        storeBean.setInfoCode(commonMaterialInfo.getInfoCode());
        storeBean.setInfoModel(commonMaterialInfo.getInfoModel());
        storeBean.setInfoUnit(commonMaterialInfo.getInfoUnit());
        storeBean.setInfoName(edv_Metrail.getText().toString());
        storeBean.setProjectID(commonMaterialInfo.getProjectID());
        storeBean.setBookPrice(inRegister.getBookPrice());
        storeBean.setInfoClassNodebh(commonMaterialInfo.getInfoClassNodebh());
        storeBean.setInfoClassName(commonMaterialInfo.getInfoClassName());
        storeBean.setFirstClassName(commonMaterialInfo.getFirstClassName());
        storeBean.setSecondClassName(commonMaterialInfo.getSecondClassName());
        storeBean.setRemark(inRegister.getRemark());
        Toast.makeText(mContext, "入库成功", Toast.LENGTH_SHORT).show();
        inRegisterDao.imInsert(inRegister, false);
        storeDao.imInsert(storeBean);
        printLable(barcode);
        clearUI();

    }

    private void clearUI() {
        tvInfoCode.setText("");
        tvInfoUnit.setText("");
        tvInfoModel.setText("");
        tvProjectID.setText("");
        edv_Remark.setText("");
        edvBookPrice.setText("");
        edv_Metrail.setText("");
        edv_Quantity.setText("");
    }

    private void printLable(String barcode) {
        //TODO 打印
        String temp = edvPrintNumber.getText().toString();
        int printCount = 1;
        if ("".equalsIgnoreCase(temp)) {
            return;
        } else {
            printCount = Integer.parseInt
                    (temp);
            if (printCount <= 0) {
                return;
            }
        }

        String name = edv_Metrail
                .getText().toString();
        String spec = tvInfoModel.getText().toString();
        String count = quantity;
        resetView();
        printInLable(ProjectName, spinnerSupper.getSelectedItem()
                .toString(), name, spec, count, barcode, printCount);

    }


    private MyLogger logger = MyLogger.jLog();

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void showConnectState(String state) {

    }

    @Override
    public void showPrintName(String name) {

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
    private List<StoreBean> tempStore;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
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
        edv_Metrail.setText(storeBean.getInfoName());
        tvInfoCode.setText(storeBean.getInfoCode());
        tvInfoModel.setText(storeBean.getInfoModel());
        tvInfoUnit.setText(storeBean.getInfoUnit());
        tvProjectID.setText(storeBean.getProjectID());
        edvBookPrice.setText(storeBean.getBookPrice() + "");

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        edvPrintNumber.setText("1");
        edv_Quantity.setText("");
        edv_Remark.setText("");
        return super.onKeyDown(keyCode, event);
    }


}
