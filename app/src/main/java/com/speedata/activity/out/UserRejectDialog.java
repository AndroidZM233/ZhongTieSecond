package com.speedata.activity.out;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.bean.OutRegister;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;

/**
 * Created by Administrator on 2015/11/16.
 */
public class UserRejectDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private OutRegister OutRegister;
    private Context mContext;
    private MyLogger logger = MyLogger.jLog();

    public UserRejectDialog(Context context, CallBack callBack, OutRegister OutRegister) {
        super(context);
        this.callBack = callBack;
        this.OutRegister = OutRegister;
        logger.d("==="+OutRegister.toString());
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private EditText edv_InfoName, edv_InfoCode, edv_InfoModel,
            edv_InfoUnit, edv_CKNodebh, edv_Quantity, edv_Remark,edvOldRemark;
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
        btnRegister = (Button) findViewById(R.id.btn_register);
        edvOldRemark = (EditText) findViewById(R.id.edv_inRemark);
        btnRegister.setOnClickListener(this);

//        edv_CKNodebh.setText(OutRegister.getCKAbbName());
        edv_InfoName.setText(OutRegister.getInfoName());
        edv_InfoUnit.setText(OutRegister.getInfoUnit());
        edvOldRemark.setText(OutRegister.getRemark());
        edv_Quantity.setText(OutRegister.getQuantity() + "");
        edv_InfoCode.setText(OutRegister.getInfoCode());
        edv_InfoUnit.setText(OutRegister.getInfoUnit());
        edv_InfoModel.setText(OutRegister.getInfoModel());
        maxCount = OutRegister.getQuantity();
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
                Toast.makeText(mContext, "数量超过入库数量", Toast.LENGTH_SHORT).show();
                edv_Quantity.setText(maxCount + "");
                return;
            }
            logger.d("=====new Quantity=" + count);
            logger.d("==="+OutRegister.getQuantity());
            OutRegister.setQuantity(count);
            OutRegister.setRemark(edv_Remark.getText().toString());
            logger.d("==="+OutRegister.getQuantity());
            callBack.callBack(OutRegister);
            callBack.intCallBack((int)count);
            dismiss();
        }
    }

    public interface CallBack {
        public void callBack(OutRegister OutRegister);
        public void intCallBack(int count);
    }
}
