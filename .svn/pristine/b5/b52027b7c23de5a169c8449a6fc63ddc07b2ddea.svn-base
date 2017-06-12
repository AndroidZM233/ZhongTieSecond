package com.speedata.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.bean.CheckForm;
import com.speedata.bean.CommonSupplier;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StoreBean;
import com.speedata.dao.CheckFormDao;
import com.speedata.dao.CommonSupplierDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ScanUtil;
import com.speedata.view.SelectDateDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CheckRegisterFragment extends BaseFragment implements View.OnClickListener
        , IBaseScanFragment {
    private MyLogger logger = MyLogger.jLog();
    private com.speedata.bean.StoreBean StoreBean = new StoreBean();
    private List<StoreBean> storeList = new ArrayList<StoreBean>();
    private CommonSupplierDao commonSupplierDao;
    private StoreDao storeDao;
    private List<CommonSupplier> supperList;
    private EditText edvProjectID, edv_InfoName, edv_InfoCode, edv_InfoModel,
            edv_InfoUnit, edv_Quantity, edvCheckQuantity;
    private TextView edv_RequisitionDate;
    private Button btnSave;
    private CheckForm checkForm;
    private CheckFormDao checkFormDao;
    private String time;
    private StoreBean storeBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }
    private void initDB() {
        storeBean = new StoreBean();
        storeDao = new StoreDao(mActivity);
        checkForm = new CheckForm();
        checkFormDao = new CheckFormDao(mActivity);
    }
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_check_register;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("盘点");
        edv_RequisitionDate = (TextView) view.findViewById(R.id.edv_RequisitionDate);
        edv_RequisitionDate.setOnClickListener(this);
        edv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
        edv_InfoName = (EditText) view.findViewById(R.id.edv_InfoName);
        edv_InfoCode = (EditText) view.findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (EditText) view.findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (EditText) view.findViewById(R.id.edv_InfoUnit);
        edv_Quantity = (EditText) view.findViewById(R.id.edv_Quantity);
        edvProjectID = (EditText) view.findViewById(R.id.edv_ProjectID);
        edvCheckQuantity = (EditText) view.findViewById(R.id.edv_check_quantity);
        edvCheckQuantity.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        edvCheckQuantity.setFocusableInTouchMode(true);
        edvCheckQuantity.setOnKeyListener(onKeyListener);
        btnSave = (Button) view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (edv_RequisitionDate == v) {
            SelectDateDialog dateDialog = new SelectDateDialog(mActivity, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    edv_RequisitionDate.setText(time);
                }
            }, edv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == btnSave) {
            if (checkFormDao == null) {
                checkFormDao = new CheckFormDao(mActivity);
            }
            try {
                final String temp = edvCheckQuantity.getText().toString();
                if (temp.equals("")) {
                    Toast.makeText(mActivity, "数量不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double qunanty = Double.parseDouble(temp);
                String result = String .format("%.4f", qunanty);
                Double resultQunanty=Double.parseDouble(result);
//                final String checkMonth = edv_RequisitionDate.getText().toString().substring(0, 7);
                final String checkMonth = edv_RequisitionDate.getText().toString();
                List<CheckForm> checkFormList = checkFormDao.imRawQuery("select * from checkForm where BarCode=? and CheckMonth=?"
                        , new String[]{mBarcode, checkMonth},CheckForm.class);

                if (checkFormList.size() > 0) {
                    new AlertDialog.Builder(mActivity).setTitle("该月份该批次已有盘点记录")
                            .setMessage("是否覆盖？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkFormDao.execSql("update checkForm set Quantity=? where BarCode=? and CheckMonth=?"
                                            ,new String[]{temp,mBarcode,checkMonth});
                                    Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();

                } else {
                    checkForm.setCheckMonth(checkMonth);//年月
                    checkForm.setInfoCode(edv_InfoCode.getText().toString());
                    checkForm.setQuantity(resultQunanty);
                    checkForm.setInfoName(edv_InfoName.getText().toString());
                    checkForm.setInfoModel(edv_InfoModel.getText().toString());
                    checkForm.setInfoUnit(edv_InfoUnit.getText().toString());
                    checkForm.setProjectID(mActivity.ProjectID);
                    checkForm.setBarCode(mBarcode);
                    UUID uuid=UUID.randomUUID();
                    checkForm.setCheckNM(String.valueOf(uuid));
                    Double oldQuantity = Double.parseDouble(edv_Quantity.getText().toString());
                    if (oldQuantity > resultQunanty || oldQuantity < resultQunanty) {
                        Toast.makeText(mActivity, "数量不一致，盘点保存成功", Toast.LENGTH_LONG).show();
                    }
                    checkFormDao.imInsert(checkForm,false);
                }
                resetView();
            } catch (Exception e) {
                Toast.makeText(mActivity, "请按要求操作", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetView() {
        edv_InfoName.setText("");
        edv_InfoCode.setText("");
        edv_InfoModel.setText("");
        edv_InfoUnit.setText("");
        edv_Quantity.setText("");
        edvCheckQuantity.setText("");
        edvProjectID.setText("");
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


    //TODO 获取条码
    private String mBarcode;


    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n", "");
        mBarcode = mBarcode.replace("\r", "");
        mBarcode = mBarcode.replace("\u0000","");
        try {
            List<StoreBean> tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, "条码不匹配", Toast.LENGTH_SHORT).show();
        }


    }

    private double Quantity = 0.0;

    private void setUI(StoreBean storeBean) {
        edv_InfoName.setText(storeBean.getInfoName());
        edv_InfoCode.setText(storeBean.getInfoCode());
        edv_InfoModel.setText(storeBean.getInfoModel());
        edv_InfoUnit.setText(storeBean.getInfoUnit());
        edvProjectID.setText(storeBean.getProjectID());
        edvCheckQuantity.setText("");
        Quantity = storeBean.getQuantity();
        edv_Quantity.setText(Quantity + "");
    }

    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction()==KeyEvent.ACTION_DOWN){
                if (keyCode==KeyEvent.KEYCODE_F4){
                    edvCheckQuantity.setText("");
                    return true;
                }
            }
            return false;
        }
    };
}
