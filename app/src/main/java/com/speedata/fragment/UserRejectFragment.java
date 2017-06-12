package com.speedata.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintTab;
import com.speedata.bean.CommonLabour;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.bean.StoreBean;
import com.speedata.dao.CommonLabourDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.SelectDateDialog;

import java.util.List;
import java.util.UUID;

import static com.speedata.dreamdemo.R.id.actv1;

/**
 * Created by Administrator on 2016/4/7.
 */
public class UserRejectFragment extends BaseFragment implements View.OnClickListener, IBaseScanFragment {
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
//    private Spinner spinnerSupper;
    private CommonLabour commonLabour;
    private CommonLabourDao commonLabourDao;
    private Button btnRegister;
    private String labourName;
    private Double oldQuantity;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }
    private void initDB() {
        commonSupplierDao = new CommonSupplierDao(mActivity);
        storeDao = new StoreDao(mActivity);
        storeBean = new StoreBean();
        inRegisterDao = new InRegisterDao(mActivity);
        outRegister = new OutRegister();
        outRegisterDao = new OutRegisterDao(mActivity);
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_out_register;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("用料单位退料");


        edvBarcode = (EditText) view.findViewById(R.id.edv_BarCode);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        edv_InfoName = (TextView) view.findViewById(R.id.edv_InfoName);
        edv_InfoCode = (TextView) view.findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (TextView) view.findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (TextView) view.findViewById(R.id.edv_InfoUnit);
        tv_allOut = (TextView) view.findViewById(R.id.tv_allOut);
        edv_Quantity = (EditText) view.findViewById(R.id.edv_Quantity);
        edv_Quantity.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        edv_Quantity.setFocusableInTouchMode(true);
        edv_Quantity.setOnKeyListener(onKeyListener);
        edvPrintNumber = (EditText) view.findViewById(R.id.edv_printNumber);
        tvQuantity = (TextView) view.findViewById(R.id.tv_Quantity);
        tv_RequisitionDate = (TextView) view.findViewById(R.id.edv_RequisitionDate);
        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
        edv_Remark = (EditText) view.findViewById(R.id.edv_Remark);
        commonLabour = new CommonLabour();
        commonLabourDao = new CommonLabourDao(mActivity);
        supperList = commonLabourDao.imRawQuery("select distinct LabourName,LabourNM from commonLabour"
                , null, CommonLabour.class);//查询所有用料单位
        if (supperList == null || supperList.size() == 0) {
            Toast.makeText(mActivity, "无用料单位信息，请先进行下载!", Toast.LENGTH_SHORT).show();
        }
        suppers = new String[supperList.size()];
        for (int i = 0; i < supperList.size(); i++) {
            suppers[i] = supperList.get(i).getLabourName();
        }
        autoCompleteTextView= (AutoCompleteTextView) view.findViewById(actv1);
        List<CommonLabour> commonLabours = commonLabourDao
                .imRawQuery("select LabourName from commonLabour"
                        , null, CommonLabour.class);
        String[] labourStr=new String[commonLabours.size()];
        for (int i = 0; i < commonLabours.size(); i++) {
            labourStr[i]=commonLabours.get(i).getLabourName();
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(mActivity,android.R.layout.simple_list_item_1,
                labourStr);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autoCompleteTextView.showDropDown();
                return false;
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edvBarcode.setFocusable(true);
                edvBarcode.setFocusableInTouchMode(true);
                edvBarcode.requestFocus();
            }
        });

//        spinnerSupper = (Spinner) view.findViewById(R.id.sp_user);
        supperAdapter = new ArrayAdapter<String>(mActivity, android.R.layout
                .simple_dropdown_item_1line, suppers);
//        spinnerSupper.setAdapter(supperAdapter);
//        spinnerSupper.setSelection(mActivity.sharedPreferences.getInt("outSelection", 0));
//        spinnerSupper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mActivity.sharedPreferences.edit().putInt("outSelection", position).commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        tv_allOut.setText("累计发料：");
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_F4){
                    edv_Quantity.setText("");
                    return true;
                }
            }
            return false;
        }
    };
    @Override
    public void onClick(View v) {
        if (tv_RequisitionDate == v) {
            SelectDateDialog dateDialog = new SelectDateDialog(mActivity, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tv_RequisitionDate.setText(time);
                }
            }, tv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == btnRegister) {
            if (edv_Quantity.getText().toString().equals("")) {
                Toast.makeText(mActivity, "请输入数量", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double inputQuantity = Double.parseDouble(edv_Quantity.getText().toString());
                String result = String .format("%.4f", inputQuantity);
                double resultQuantity=Double.parseDouble(result);
                if (resultQuantity > Quantity) {
                    mActivity.alertMessage("当前最大退货量为：" + Quantity);
                    edv_Quantity.setText(Quantity+"");
                    return;
                }
                if (storeBean == null) {
                    mActivity.showMessage("请先扫描条码");
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
                String infoUnit = edv_InfoUnit.getText().toString();
                outRegister.setInfoUnit(infoUnit);
                outRegister.setProjectID(mActivity.ProjectID);
//                labourName = supperList.get(spinnerSupper
//                        .getSelectedItemPosition()).getLabourName();
//                outRegister.setLabourName(labourName);
//                String labourNM=supperList.get(spinnerSupper
//                        .getSelectedItemPosition()).getLabourNM();
//                outRegister.setLabourNM(labourNM);
                String labourName = autoCompleteTextView.getText().toString();
                if (TextUtils.isEmpty(labourName)){
                    showToast("请选择用料单位");
                    return;
                }
                outRegister.setLabourName(labourName);
                List<CommonLabour> commonLabours = commonLabourDao
                        .imRawQuery("select LabourNM from commonLabour where LabourName = ?"
                                , new String[]{labourName}, CommonLabour.class);
                if (commonLabours.size()==0){
                    showToast("用料单位信息有误");
                    return;
                }
                String labourNM = null;
                try {
                    labourNM = commonLabours.get(0).getLabourNM();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                Toast.makeText(mActivity, "登记成功", Toast.LENGTH_LONG).show();
                outRegisterDao.imInsert(outRegister, false);
                String printCountString = edvPrintNumber.getText().toString();
                if (!"".equals(printCountString)) {
                    int printCount = Integer.parseInt(printCountString);
                    if (printCount > 0) {
//                        mActivity.printOutLable(mActivity.ProjectName, spinnerSupper.getSelectedItem().toString(),
//                                edv_InfoName.getText().toString(), edv_InfoModel.getText().toString(),
//                                "-" + String.valueOf(resultQuantity)+" 单位："+infoUnit, mBarcode, timeString, printCount);
                        PrintTab printTab=new PrintTab();
                        printTab.printOutTabCPCL(mActivity.context,mActivity.ProjectName, autoCompleteTextView.getText().toString(),
                                edv_InfoName.getText().toString(), edv_InfoModel.getText().toString(),
                                "-" + String.valueOf(resultQuantity)+" 单位："+infoUnit, mBarcode, timeString, printCount);
                    }
                }
                clearUI();
                edvBarcode.setFocusable(true);
                edvBarcode.setFocusableInTouchMode(true);
                edvBarcode.requestFocus();
            } catch (Exception e) {
                Toast.makeText(mActivity, "", Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        scanUtil = new ScanUtil(mActivity);
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
        mBarcode = mBarcode.replace("\u0000","");
        labourName = autoCompleteTextView.getText().toString();
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
            Toast.makeText(mActivity, "条码不匹配", Toast.LENGTH_SHORT).show();
            edvBarcode.setText("");
        }
        edv_Quantity.setText("");
        edv_Remark.setText("");
        edv_Quantity.setFocusable(true);
        edv_Quantity.setFocusableInTouchMode(true);
        edv_Quantity.requestFocus();

    }

    private double Quantity = 0.0;

    private void setUI(OutRegister outRegister) {
        edv_InfoName.setText(outRegister.getInfoName());
        edv_InfoCode.setText(outRegister.getInfoCode());
        edv_InfoModel.setText(outRegister.getInfoModel());
        edv_InfoUnit.setText(outRegister.getInfoUnit());

        tvQuantity.setText(Quantity + "");
    }
}
