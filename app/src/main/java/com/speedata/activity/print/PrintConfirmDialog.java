package com.speedata.activity.print;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.speedata.bean.CommonForm;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.sprt.sprtlabelprinter.BasePrinter;

/**
 * Created by Administrator on 2015/11/16.
 */
public class PrintConfirmDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private CommonForm CommonForm;
    private Context mContext;
    private MyLogger logger = MyLogger.jLog();
    private BasePrinter mPrinter;


    public PrintConfirmDialog(Context context, BasePrinter mPrinter, CommonForm CommonForm) {
        super(context);
//        this.callBack = callBack;
        this.mPrinter = mPrinter;
        this.CommonForm = CommonForm;
        logger.d("===" + CommonForm.toString());
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private EditText edv_InfoName, edv_InfoCode, edv_InfoModel,
            edv_InfoUnit, edv_CKNodebh, edv_origin, edvMax, edvMin, edv_price;
    private Button btnRegister;

    private double maxCount = 0;

    private void initUI() {
        setContentView(R.layout.dialog_confirm_print);
        edv_InfoName = (EditText) findViewById(R.id.edv_InfoName);
        edv_InfoCode = (EditText) findViewById(R.id.edv_InfoCode);
        edv_InfoModel = (EditText) findViewById(R.id.edv_InfoModel);
        edv_InfoUnit = (EditText) findViewById(R.id.edv_InfoUnit);
        edv_CKNodebh = (EditText) findViewById(R.id.edv_CKNodebh);
        edv_origin = (EditText) findViewById(R.id.edv_origin);
        edvMax = (EditText) findViewById(R.id.edv_max);
        edvMin = (EditText) findViewById(R.id.edv_min);
        edv_price = (EditText) findViewById(R.id.edv_price);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        edv_CKNodebh.setText(CommonForm.getCKAbbName());
        edv_InfoName.setText(CommonForm.getInfoName());
        edv_InfoUnit.setText(CommonForm.getInfoUnit());
//        edv_Remark.setText(CommonForm.getRemark());
//        edvMin.setText(CommonForm.getRemark());
//        edv_origin.setText(CommonForm.getQuantity() + "");
        edv_InfoCode.setText(CommonForm.getInfoCode());
        edv_InfoUnit.setText(CommonForm.getInfoUnit());
//        edv_Remark.setText(CommonForm.getRemark());
        edv_InfoModel.setText(CommonForm.getInfoModel());
//        maxCount = CommonForm.getQuantity();
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegister) {
//            callBack.callBack(CommonForm);
//            callBack.intCallBack((int) count);
            //PrinterInstance mPrinter, String location,
//            String id, String unit, String name, String price, String spec,
//                    String orgin, String max, String lower, String barcode
            String barcode = edv_InfoCode.getText().toString();
            String location = edv_CKNodebh.getText().toString();
            String id = edv_InfoCode.getText().toString();
            String unit = edv_InfoUnit.getText().toString();
            String name = edv_InfoName.getText().toString();
            String price = edv_price.getText().toString();
            String spec = edv_InfoModel.getText().toString();
            String orgin = edv_origin.getText().toString();
            String max = edvMax.getText().toString();
            String min = edvMin.getText().toString();
            MySPRTLabel1.doPrint(mPrinter, location, id, unit, name, price, spec,
                    orgin, max, min, barcode);
//            new LabelUtils().printAssignTable(mPrinter, location, id, unit, name, price, spec,
//                    orgin, max, min, barcode);
            dismiss();
        }
    }

    public interface CallBack {
        public void callBack(CommonForm CommonForm);

        public void intCallBack(int count);
    }
}
