package com.speedata.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.speedata.activity.print.REGOPrint;
import com.speedata.application.CustomerApplication;
import com.speedata.dreamdemo.R;
import com.speedata.fragment.LoginFragment;
import com.speedata.utils.SharedXmlUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends REGOPrint {
    private TextView tv_state;
    public CustomerApplication contextAPP;
    ArrayList<String> getbtNM = new ArrayList<String>();
    ArrayList<String> getbtMax = new ArrayList<String>();
    List<String> getbtName = new ArrayList<String>();
    private String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextAPP = (CustomerApplication) getApplicationContext();
        initUi();
        setDefaultFragment();
        IntentFilter connectedFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        IntentFilter connectedFilter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(stateChangeReceiver,connectedFilter);
        registerReceiver(stateChangeReceiver,connectedFilter2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(stateChangeReceiver);
    }

    private BroadcastReceiver stateChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
                if (!sureToCloseDevices){
                    tv_state.setText("打印机断开，正在尝试重连...");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String printName = SharedXmlUtil.getInstance(mContext).read("PrintName", "");
                            String max = SharedXmlUtil.getInstance(mContext).read("max", "");
                            if (TextUtils.isEmpty(printName)||TextUtils.isEmpty(max)){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_state.setText("连接打印机");
                                    }
                                });
                            }else {
                                state = contextAPP.getObject().CON_ConnectDevices(printName, max, 200);
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = state;
                                handler.sendMessage(msg);
                            }

                        }
                    }).start();
                }
                sureToCloseDevices=false;
            }
        }
    };

    private boolean sureToCloseDevices=false;
    private void initUi() {
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_state.setText("正在连接....");
                if (mBconnect) {
                    sureToCloseDevices=true;
                    Toast.makeText(MainActivity.this, "已与打印机断开连接",
                            Toast.LENGTH_SHORT).show();
                    contextAPP.getObject().CON_CloseDevices(contextAPP.getState());
                    tv_state.setText("连接打印机");
                    mBconnect = false;
                } else {
                    getbtNM = (ArrayList<String>) contextAPP.getObject()
                            .CON_GetWirelessDevices(0);
                    if (getbtNM.size() < 1) {
                        showMessage("无已配对蓝牙，请去设置中配对。");
                        tv_state.setText("连接打印机");
                        return;
                    }
                    // 对获得的蓝牙地址和名称进行拆分以逗号进行拆分
                    for (int i = 0; i < getbtNM.size(); i++) {
                        getbtName.add(getbtNM.get(i).split(",")[0]);
                        getbtMax.add(getbtNM.get(i).split(",")[1].substring(0,
                                17));
                    }


                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setIcon(R.drawable.icon_launcher);
                    builder.setTitle("选择连接蓝牙");
                    builder.setItems((String[]) getbtName.toArray(new String[getbtName.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            max = getbtMax.get(which);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    SharedXmlUtil.getInstance(mContext).write("PrintName", PrintName);
                                    SharedXmlUtil.getInstance(mContext).write("max", max);
                                    state = contextAPP.getObject().CON_ConnectDevices(PrintName, max, 200);
                                    Message msg = new Message();
                                    msg.what = 0;
                                    msg.obj = state;
                                    handler.sendMessage(msg);
                                    getbtName.clear();
                                    getbtMax.clear();
                                    getbtNM.clear();
                                }
                            }).start();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getbtName.clear();
                            getbtMax.clear();
                            getbtNM.clear();
                            tv_state.setText("打印机状态：未连接（点击连接）");
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();


                }

            }
        });

    }


    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int result= (int) msg.obj;
                    if (result > 0) {
                        Toast.makeText(MainActivity.this, "已与打印机连接",
                                Toast.LENGTH_SHORT).show();
                        mBconnect = true;
                        tv_state.setText("打印机状态：已连接（点击关闭）");
                        contextAPP.setState(state);
                        contextAPP.setName(PrintName);
                        contextAPP.setPrintway(0);
                    } else {
                        Toast.makeText(MainActivity.this, "未与打印机连接",
                                Toast.LENGTH_SHORT).show();
                        mBconnect = false;
                        tv_state.setText("打印机状态：未连接（点击连接）");
                    }
                    break;
            }
        }
    };

    /**
     * 设置默认的Fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, new LoginFragment());
        transaction.commit();
    }


}
