package com.speedata.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.bean.StoreBean;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;

/**
 * Created by Administrator on 2015/11/16.
 */
public class SupplierRejectDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private StoreBean storeBean;
    private Context mContext;
    private MyLogger logger = MyLogger.jLog();

    public SupplierRejectDialog(Context context, CallBack callBack, StoreBean storeBean) {
        super(context);
        this.callBack = callBack;
        this.storeBean = storeBean;
        logger.d("===" + storeBean.toString());
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private EditText edv_InfoName, edv_InfoCode, edv_InfoModel,
            edv_InfoUnit, edv_CKNodebh, edv_Quantity, edv_Remark, edvOldRemark;
    private Button btnRegister;

    private double maxCount = 0;

    private void initUI() {
        setContentView(R.layout.dialog_confirm_supplier_reject);
        edv_InfoName = (EditText) findViewById(R.id.edv_InfoName);
        edv_InfoCode = (EditText) findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (EditText) findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (EditText) findViewById(R.id.edv_InfoUnit);
        edv_CKNodebh = (EditText) findViewById(R.id.edv_CKNodebh);
        edv_Quantity = (EditText) findViewById(R.id.edv_Quantity);
        edv_Remark = (EditText) findViewById(R.id.edv_Remark);
        edvOldRemark = (EditText) findViewById(R.id.edv_inRemark);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

//        edv_CKNodebh.setText(storeBean.getCKAbbName());
        edv_InfoName.setText(storeBean.getInfoName());
        edv_InfoUnit.setText(storeBean.getInfoUnit());
//        edv_Remark.setText(storeBean.getRemark());
        edvOldRemark.setText(storeBean.getRemark());
        edv_Quantity.setText(storeBean.getQuantity() + "");
        edv_InfoCode.setText(storeBean.getInfoCode());
        edv_InfoUnit.setText(storeBean.getInfoUnit());
//        edv_Remark.setText(storeBean.getRemark());
        edv_InfoModel.setText(storeBean.getInfoModel());
        maxCount = storeBean.getQuantity();
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
            String temp = edv_Quantity.getText().toString();
            if (temp.equals("")) {
                Toast.makeText(mContext, "数量不能为空!", Toast.LENGTH_SHORT).show();
                return;
            }
            double count = Double.parseDouble(temp);
            if (count > maxCount) {
                Toast.makeText(mContext, "数量超过库存数量", Toast.LENGTH_SHORT).show();
                edv_Quantity.setText(maxCount + "");
                return;
            }
            logger.d("=====new Quantity=" + count);
            logger.d("===" + storeBean.getQuantity());
            storeBean.setQuantity(count);
            storeBean.setRemark(edv_Remark.getText().toString());
            logger.d("===" + storeBean.getQuantity());
            callBack.callBack(storeBean);
            callBack.intCallBack((int) count);
            dismiss();
        }
    }

    public interface CallBack {
        public void callBack(StoreBean storeBean);

        public void intCallBack(int count);
    }
}
