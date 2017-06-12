package com.speedata.activity.in;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.speedata.activity.print.PrintActivity;
import com.speedata.adapter.InAgainBarCodeAdapter;
import com.speedata.adapter.InUploadListAdapter;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.view.SelectDateDialog;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public class AgainBarCodeActivity extends PrintActivity implements View.OnClickListener {
    private Button btnPrint,btnSelect;
    private TextView tvTime;
    private EditText edvPrintNumber;
    private ListView listView;
    private InRegister inRegister;
    private InRegisterDao inRegisterDao;
    private InAgainBarCodeAdapter inAgainBarCodeAdapter;
    List<InRegister> inRegisterList;
    private CommonMaterialInfo commonMaterialInfo;
    private CommonMaterialInfoDao commonMaterialInfoDao;
    private int selectPosition;
    private List<Integer> positionList=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
        initUI();

    }

    private void initDB() {
        inRegister=new InRegister();
        inRegisterDao=new InRegisterDao(mContext);
        commonMaterialInfo=new CommonMaterialInfo();
        commonMaterialInfoDao=new CommonMaterialInfoDao(mContext);
    }

    private void initUI() {
        setContentView(R.layout.activity_in_againbc);
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
            inRegisterList = inRegisterDao
                    .imRawQuery("select * from inRegister where ProjectID=\"" + app
                            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                                    .FIELD_DEFAULT_PROJETC) +
                            "\" and RequisitionDate = " + "\"" + time + "\"", null, InRegister
                            .class);
            if (inRegisterList.size() == 0) {
                new AlertDialog.Builder(mContext).setMessage(time+"内无数据").show().setTitle("错误");
                return;
            }
            inAgainBarCodeAdapter = new InAgainBarCodeAdapter(mContext, inRegisterList);
            listView.setAdapter(inAgainBarCodeAdapter);
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    InAgainBarCodeAdapter.ViewHolder viewHolder=
                            (InAgainBarCodeAdapter.ViewHolder) view.getTag();
                    viewHolder.checkBox.toggle();
                    InAgainBarCodeAdapter.isSelected.put(position,viewHolder.checkBox.isChecked());
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
            for (int i = 0; i <inRegisterList.size() ; i++) {
                if (InAgainBarCodeAdapter.isSelected.get(i)){
                    String name=inRegisterList.get(i).getInfoName();
                    String spec =inRegisterList.get(i).getInfoModel();
                    String count= String.valueOf(inRegisterList.get(i).getQuantity());
                    String barcode=inRegisterList.get(i).getBarCode();
                    String supperName=inRegisterList.get(i).getSupplierName();
                    printInLable(ProjectName, supperName, name, spec,
                            count, barcode, printCount);
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

