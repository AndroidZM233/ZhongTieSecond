package com.speedata.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.speedata.activity.print.PrintTab;
import com.speedata.adapter.OutAgainBarCodeAdapter;
import com.speedata.bean.MyDateAndTime;
import com.speedata.bean.OutRegister;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.view.SelectDateDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class OutAgainBarCodeFragment extends BaseFragment implements View.OnClickListener{
    private Button btnPrint,btnSelect;
    private TextView tvTime;
    private EditText edvPrintNumber;
    private ListView listView;
    private OutRegister outRegister;
    private OutRegisterDao outRegisterDao;
    private OutAgainBarCodeAdapter outAgainBarCodeAdapter;
    List<OutRegister> outRegisterList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDB();
    }
    private void initDB() {
        outRegister=new OutRegister();
        outRegisterDao=new OutRegisterDao(mActivity);
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_out_againbc;
    }

    @Override
    public void findById(View view) {
        mActivity.setTitle("条码补打");
        btnPrint= (Button) view.findViewById(R.id.btn_print);
        btnPrint.setOnClickListener(this);
        btnSelect= (Button) view.findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(this);
        tvTime= (TextView) view.findViewById(R.id.tv_time);
        tvTime.setText(MyDateAndTime.getTimeString("yyyy-MM-dd"));
        tvTime.setOnClickListener(this);
        edvPrintNumber= (EditText) view.findViewById(R.id.tv_printNumber);
        listView= (ListView) view.findViewById(R.id.listview);
        edvPrintNumber.setText("1");
    }

    @Override
    public void onClick(View v) {
        if (v == tvTime) {
            SelectDateDialog dateDialog = new SelectDateDialog(mActivity, new SelectDateDialog
                    .ShowDate() {
                @Override
                public void showDate(String time) {
                    tvTime.setText(time);
                }
            }, tvTime.getText().toString());
            dateDialog.show();
        }else if (v==btnSelect){
            String time=tvTime.getText().toString();
            outRegisterList = outRegisterDao
                    .imRawQuery("select * from outRegister where ProjectID=\"" + mActivity.app
                            .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                                    .FIELD_DEFAULT_PROJETC) +
                            "\" and OrderDate = " + "\"" + time + "\"", null, OutRegister
                            .class);
            if (outRegisterList.size() == 0) {
                new AlertDialog.Builder(mActivity).setMessage(time+"内无数据").show().setTitle("错误");
                return;
            }
            outAgainBarCodeAdapter = new OutAgainBarCodeAdapter(mActivity, outRegisterList);
            listView.setAdapter(outAgainBarCodeAdapter);
            listView.setItemsCanFocus(false);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    OutAgainBarCodeAdapter.ViewHolder viewHolder=
                            (OutAgainBarCodeAdapter.ViewHolder) view.getTag();
                    viewHolder.checkBox.toggle();
                    OutAgainBarCodeAdapter.isSelected.put(position,viewHolder.checkBox.isChecked());
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
            for (int i = 0; i <outRegisterList.size() ; i++) {
                if (OutAgainBarCodeAdapter.isSelected.get(i)){
                    String name=outRegisterList.get(i).getInfoName();
                    String spec =outRegisterList.get(i).getInfoModel();
                    String count= String.valueOf(outRegisterList.get(i).getQuantity());
                    String barcode=outRegisterList.get(i).getBarCode();
                    String infoUnit=outRegisterList.get(i).getInfoUnit();
                    String labourName=outRegisterList.get(i).getLabourName();
                    String timeString=outRegisterList.get(i).getOrderTime();
//                    mActivity.printOutLable(mActivity.ProjectName, labourName, name, spec,
//                            count+" 单位："+infoUnit, barcode,timeString, printCount);
                    PrintTab printTab=new PrintTab();
                    printTab.printOutTabCPCL(mActivity.context,mActivity.ProjectName, labourName, name, spec,
                            count+" 单位："+infoUnit, barcode,timeString, printCount);
                }
            }
        }
    }
}
