package com.speedata.activity.print;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.BaseActivity;
import com.speedata.activity.MainActivity;
import com.speedata.application.CustomerApplication;
import com.speedata.dreamdemo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/25.
 */
public class REGOPrint extends BaseActivity{
    public int state;
    public String PrintName="RG-MLP80A(CPCL)";
    public Button con;
    public Spinner type;
    public Spinner usb;
    public Spinner com;
    public Spinner combaud;
    public Spinner porttype;
    public Spinner printway;
    public Spinner btName;
    private ArrayAdapter<String> mAdapter;
    public LinearLayout show;
    public View view1;
    public TextView link;
    public TextView version;
    public Button linktest;
    public EditText wifiport;
    public EditText wifiip;
    public EditText Adress;
    public CustomerApplication context;
    public String error;
    public boolean mBconnect = false;
    ArrayList<String> getbtName = new ArrayList<String>();
    ArrayList<String> getbtNM = new ArrayList<String>();
    ArrayList<String> getbtMax = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (android.os.Build.VERSION.SDK_INT > 9) {
        //	  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //	   StrictMode.setThreadPolicy(policy);
        //	    }
        setContentView(R.layout.activity_connect);

        // 多个页面之间数据共享
        context = (CustomerApplication) getApplicationContext();
        context.setObject();

        linktest = (Button) findViewById(R.id.TextView_linktest);
        link = (TextView) findViewById(R.id.TextView_link);
        version = (TextView) findViewById(R.id.text_version);
        version.setText("V " + context.getObject().CON_QueryVersion());
        type = (Spinner) findViewById(R.id.spinner_type);
        porttype = (Spinner) findViewById(R.id.spinner_porttype);
        printway = (Spinner) findViewById(R.id.spinner_printway);
        show = (LinearLayout) findViewById(R.id.linearLayout0);

        ArrayList<String> printName = new ArrayList<String>();
        printName = (ArrayList<String>) context.getObject()
                .CON_GetSupportPrinters();

        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, printName);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(mAdapter);

        String[] printInterface = context.getObject().CON_GetSupportPageMode();
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, printInterface);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        printway.setAdapter(mAdapter);

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                PrintName = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
//        linktest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                if (mBconnect) {
//                    Intent intent = new Intent(REGOPrint.this,
//                            MainActivity.class);
//
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(REGOPrint.this, R.string.mes_nextpage,
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        porttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0:// bt
                        view1 = getLayoutInflater().inflate(
                                R.layout.activity_bluetooth, null); // 下半部分改变后的Layout
                        show.removeAllViews();
                        show.addView(view1);

                        Adress = (EditText) findViewById(R.id.edit_btmax);
                        btName = (Spinner) findViewById(R.id.spinner_btname);

                        getbtName.clear();
                        mAdapter = new ArrayAdapter<String>(REGOPrint.this,
                                android.R.layout.simple_spinner_item, getbtName);
                        btName.setAdapter(mAdapter);

                        getbtNM = (ArrayList<String>) context.getObject()
                                .CON_GetWirelessDevices(0);
                        // 对获得的蓝牙地址和名称进行拆分以逗号进行拆分
                        for (int i = 0; i < getbtNM.size(); i++) {
                            getbtName.add(getbtNM.get(i).split(",")[0]);
                            getbtMax.add(getbtNM.get(i).split(",")[1].substring(0,
                                    17));
                        }

                        mAdapter = new ArrayAdapter<String>(REGOPrint.this,
                                android.R.layout.simple_spinner_item, getbtName);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        btName.setAdapter(mAdapter);
                        btName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> arg0,
                                                       View arg1, int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                Adress.setText(getbtMax.get(arg2));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }
                        });

                        // btName后期要添加监听事件用于连接
                        con = (Button) findViewById(R.id.button_btcon);
                        con.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                connect();
                            }
                        });
                        break;

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

    }

    public void connect() {

        if (mBconnect) {
            context.getObject().CON_CloseDevices(context.getState());

            con.setText(R.string.button_btcon);// "连接"
            mBconnect = false;
        } else {

            state = context.getObject().CON_ConnectDevices(PrintName, "00:02:5B:B4:65:34", 200);

            if (state > 0) {
                Toast.makeText(REGOPrint.this, R.string.mes_consuccess,
                        Toast.LENGTH_SHORT).show();
                mBconnect = true;
                con.setText(R.string.TextView_close);// "关闭"
                Intent intent = new Intent(REGOPrint.this,
                        MainActivity.class);
                context.setState(state);
                context.setName(PrintName);
                context.setPrintway(0);
                startActivity(intent);
            } else {
                Toast.makeText(REGOPrint.this, R.string.mes_confail,
                        Toast.LENGTH_SHORT).show();
                mBconnect = false;
                con.setText(R.string.button_btcon);// "连接"
            }
        }
    }

}
