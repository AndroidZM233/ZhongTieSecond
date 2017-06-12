package com.speedata.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.speedata.activity.print.PrintTab;
import com.speedata.adapter.InAgainBarCodeAdapter;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.InRegister;
import com.speedata.bean.MyDateAndTime;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dao.InRegisterDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.view.SelectDateDialog;

import java.util.List;

/**
 * Created by Administrator on 2016/4/6.
 */
public class AgainBarCodeFragment extends BaseFragment implements View.OnClickListener {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initDB();
    }
    private void initDB() {
        inRegister=new InRegister();
        inRegisterDao=new InRegisterDao(mActivity);
        commonMaterialInfo=new CommonMaterialInfo();
        commonMaterialInfoDao=new CommonMaterialInfoDao(mActivity);
    }
    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_in_againbc;
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
            String newProjectID = mActivity.app
                    .getSharedPreferences().getString(Constant.FIELD_PROJETC, Constant
                            .FIELD_DEFAULT_PROJETC);
            try {
                inRegisterList = inRegisterDao
                        .imRawQuery("select * from inRegister where ProjectID=\"" + newProjectID +
                                "\" and RequisitionDate = " + "\"" + time + "\"", null, InRegister
                                .class);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("无数据");
                return;
            }
            if (inRegisterList.size() == 0) {
                new AlertDialog.Builder(mActivity).setMessage(time+"内无数据").show().setTitle("错误");
                return;
            }
            inAgainBarCodeAdapter = new InAgainBarCodeAdapter(mActivity, inRegisterList);
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
            try {
                for (int i = 0; i <inRegisterList.size() ; i++) {
                    if (InAgainBarCodeAdapter.isSelected.get(i)){
                        String name=inRegisterList.get(i).getInfoName();
                        String spec =inRegisterList.get(i).getInfoModel();
                        String count= String.valueOf(inRegisterList.get(i).getQuantity());
                        String barcode=inRegisterList.get(i).getBarCode();
                        String infoUnit=inRegisterList.get(i).getInfoUnit();
                        String supperName=inRegisterList.get(i).getSupplierName();
    //                    mActivity.printInLable(mActivity.ProjectName, supperName, name, spec,
    //                            count+" 单位："+infoUnit, barcode, printCount);
                        PrintTab printTab=new PrintTab();
                        printTab.printInTabCPCL(mActivity.context,mActivity.ProjectName, supperName, name, spec,
                                count+" 单位："+infoUnit, barcode, printCount);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("发生错误！");
            }
        }
    }
}
