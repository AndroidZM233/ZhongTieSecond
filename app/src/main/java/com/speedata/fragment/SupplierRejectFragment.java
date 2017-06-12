package com.speedata.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.base.BaseScanFragment;
import com.speedata.activity.base.IBaseScanFragment;
import com.speedata.activity.print.PrintTab;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.StoreBean;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.view.SelectDateDialog;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/4/6.
 */
public class SupplierRejectFragment extends BaseScanFragment implements IBaseScanFragment,
        View.OnClickListener{
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

    public SupplierRejectFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }
    private void initDB() {
        storeBean = new StoreBean();
        storeDao = new StoreDao(mActivity);
        inRegister = new InRegister();
        inRegisterDao = new InRegisterDao(mActivity);
    }
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_supplier_reject;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("供应商退料");
        storeDao = new StoreDao(mActivity);
        edv_InfoName = (EditText) view.findViewById(R.id.edv_InfoName);
        edv_InfoCode = (EditText) view.findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (EditText) view.findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (EditText) view.findViewById(R.id.edv_InfoUnit);
        edv_Quantity = (EditText) view.findViewById(R.id.edv_Quantity);
        edv_Quantity.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。
        edv_Quantity.setFocusableInTouchMode(true);
        edv_Quantity.setOnKeyListener(onKeyListener);
        edv_Supplier = (EditText) view.findViewById(R.id.edv_Supplier);
        edv_inventory = (EditText) view.findViewById(R.id.edv_inventory);
        edv_PrintNumber = (EditText) view.findViewById(R.id.edv_PrintNumber);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        tv_RequisitionDate = (TextView) view.findViewById(R.id.edv_RequisitionDate);
        edv_Remark = (EditText) view.findViewById(R.id.edv_Remark);
        tv_RequisitionDate.setOnClickListener(this);
        tv_RequisitionDate.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
    }





    //TODO 获取条码
    private String mBarcode;
    private StoreBean storeBean;

    @Override
    public void onGetBarcode(String barcode) {
        mBarcode = barcode.replace("\n","");
        mBarcode = mBarcode.replace("\r","");
        mBarcode = mBarcode.replace("\u0000","");
        try {
            tempStore = storeDao.imQueryList("BarCode=?", new String[]{mBarcode});
            if (tempStore.size() > 0) {
                storeBean = tempStore.get(0);
                setUI(storeBean);
            }
        } catch (Exception e) {
            Toast.makeText(mActivity, "条码不匹配", Toast.LENGTH_SHORT).show();
        }
        edv_Quantity.setText("");
        edv_Quantity.setFocusable(true);
        edv_Quantity.setFocusableInTouchMode(true);
        edv_Quantity.requestFocus();

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
            SelectDateDialog dateDialog = new SelectDateDialog(mActivity, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tv_RequisitionDate.setText(time);
                }
            }, tv_RequisitionDate.getText().toString());
            dateDialog.show();
        } else if (v == btn_register) {
            if (edv_Quantity.getText().toString().equals("")) {
                Toast.makeText(mActivity, "请填写数量", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                double inputQuantity = Double.parseDouble(edv_Quantity.getText().toString());
                if (inputQuantity > Quantity) {
                    mActivity.alertMessage("当前最大出库量为：" + Quantity);
                    edv_Quantity.setText(Quantity+"");
                    return;
                }

                if (storeBean == null) {
                    mActivity.showMessage("请先扫描条码");
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
                Toast.makeText(mActivity, "登记成功", Toast.LENGTH_LONG).show();
                inRegisterDao.imInsert(inRegister,false);
                printLable(mBarcode);
                clearUI();
            } catch (Exception e) {
                Toast.makeText(mActivity, "请先扫描条码", Toast.LENGTH_SHORT).show();
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
        String unit=edv_InfoUnit.getText().toString();
        String count = "-"+format+" 单位："+unit;
//        mActivity.printInLable(mActivity.ProjectName, edv_Supplier.getText().toString(), name,
//                spec, count, barcode, printCount);
        PrintTab printTab=new PrintTab();
        printTab.printInTabCPCL(mActivity.context,mActivity.ProjectName, edv_Supplier.getText().toString(), name,
                spec, count, barcode, printCount);

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
}
