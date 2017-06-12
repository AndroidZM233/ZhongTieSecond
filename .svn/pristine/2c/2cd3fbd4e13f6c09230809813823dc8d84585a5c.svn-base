package com.speedata.activity.allot;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.AllotAgainBarCodeAdapter;
import com.speedata.adapter.OutAgainBarCodeAdapter;
import com.speedata.bean.AllotRecord;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.dao.AllotRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.view.SelectDateDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class AllotAgainBarCodeActivity extends PrintActivity implements View.OnClickListener {
    private Button btnPrint,btnSelect;
    private TextView tvTime;
    private EditText edvPrintNumber;
    private ListView listView;
    private AllotRecord allotRecord;
    private AllotRegisterDao allotRegisterDao;
    private AllotAgainBarCodeAdapter allotAgainBarCodeAdapter;
    List<AllotRecord> allotRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();

    }

    private void initDB() {
        allotRecord=new AllotRecord();
        allotRegisterDao=new AllotRegisterDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_out_againbc);
        setTitle("条码补打");
        btnPrint= (Button) findViewById(R.id.btn_print);
        btnPrint.setOnClickListener(this);
        btnSelect= (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(this);
        tvTime= (TextView) findViewById(R.id.tv_time);
        tvTime.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
        tvTime.setOnClickListener(this);
        edvPrintNumber= (EditText) findViewById(R.id.tv_printNumber);
        listView= (ListView) findViewById(R.id.listview);
        edvPrintNumber.setText("1");
    }


    @Override
    public void onClick(View v) {
        if (v == tvTime) {
            SelectDateDialog dateDialog = new SelectDateDialog(mContext, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tvTime.setText(time);
                }
            }, tvTime.getText().toString());
            dateDialog.show();
        }else if (v==btnSelect){
            String time=tvTime.getText().toString();
            allotRecordList = allotRegisterDao
                    .imRawQuery("select * from allotRecord where ProjectID=\"" + app
                            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                                    .FIELD_DEFAULT_PROJETC) +
                            "\" and OrderDate = " + "\"" + time + "\"", null, AllotRecord
                            .class);
            if (allotRecordList.size() == 0) {
                new AlertDialog.Builder(mContext).setMessage(time+"内无数据").show().setTitle("错误");
                return;
            }
            allotAgainBarCodeAdapter = new AllotAgainBarCodeAdapter(mContext, allotRecordList);
            listView.setAdapter(allotAgainBarCodeAdapter);
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    AllotAgainBarCodeAdapter.ViewHolder viewHolder=
                            (AllotAgainBarCodeAdapter.ViewHolder) view.getTag();
                    viewHolder.checkBox.toggle();
                    AllotAgainBarCodeAdapter.isSelected.put(position,viewHolder.checkBox.isChecked());
                }
            });
        }else if (v==btnPrint){
            String temp = edvPrintNumber.getText().toString();
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
            for (int i = 0; i <allotRecordList.size() ; i++) {
                if (AllotAgainBarCodeAdapter.isSelected.get(i)){
                    String name=allotRecordList.get(i).getInfoName();
                    String spec =allotRecordList.get(i).getInfoModel();
                    String count= String.valueOf(allotRecordList.get(i).getQuantity());
                    String barcode=allotRecordList.get(i).getBarCode();
                    String labourName=allotRecordList.get(i).getLabourName();
                    String timeString=allotRecordList.get(i).getOrderTime();
                    printOutLable(ProjectName, labourName, name, spec,
                            count, barcode, timeString, printCount);
                }
            }
        }
    }

    @Override
    public void showConnectState(String state) {

    }

    @Override
    public void showPrintName(String name) {

    }
}
