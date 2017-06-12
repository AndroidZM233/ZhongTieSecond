package com.speedata.activity.print;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.print.demo.BluetoothOperation;
import com.android.print.demo.IPrinterOpertion;
import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.bluetooth.BluetoothPort;
import com.speedata.activity.BaseActivity;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.bean.InRegister;
import com.speedata.dao.CommonMaterialInfoDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.Constant;
import com.speedata.utils.ProgressDialogUtils;
import com.sprt.sprtlabelprinter.BasePrinter;
import com.sprt.sprtlabelprinter.PrinterConstance;
import com.sprt.sprtlabelprinter.SPRTPrinter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class PrintActivity extends REGOPrint implements IBasePrint {


    private CommonMaterialInfoDao commonMaterialInfom;
    private CommonMaterialInfo commonMaterialInfo;


    //    private TextView tvTitle;
//    private ImageView imageBack;
////    public Context mContext;
//    public CustomerApplication app;
//    public String ProjectID = "";
//    public sharedPre sharedPre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        sharedPre = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBTAdapter == null) {
            Toast.makeText(this, "您的机器连蓝牙都没有，扔了吧！", Toast.LENGTH_LONG).show();
        } else if (mBTAdapter.isEnabled()) {
            prepareConnect();
        } else if (!mBTAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

//    public void alertMessage(String msg){
//        new AlertDialog.Builder(mContext).setMessage(msg).show();
//    }
//    public void showMessage(String msg){
//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//    }
    public void printOutLable(String projectName, String user,
                              String name, String
                                      spec, String count, String barcode, String time,int printCount) {
        if (isConnected) {
//            for
            MySPRTLabel1.doPrintOutRegister(iPrinter, projectName, user, name, spec, count,
                    barcode, time, printCount);
        } else {
            Toast.makeText(mContext,"未打印!请用户选择补打标签",Toast.LENGTH_LONG).show();
            isShowPro=true;
            prepareConnect();
        }
    }


    public String getProjectName() {
        commonMaterialInfom = new CommonMaterialInfoDao(mContext);
        List<CommonMaterialInfo> temp = commonMaterialInfom.imRawQuery("select distinct " +
                "ProjectName from " +
                "CommonMaterialInfo where" +
                " ProjectID=?", new String[]{ProjectID}, CommonMaterialInfo.class);
        if (temp.size() > 0) {
            return temp.get(0).getProjectName();
        } else {
            return "null";
        }
    }

    /**
     *
     * @param projectName 单位
     * @param user
     * @param name
     * @param spec
     * @param count
     * @param barcode
     * @param printCount
     */
    public void printInLable(String projectName, String user,
                              String name, String
                                      spec, String count, String barcode,int printCount) {
        if (isConnected) {
//            for
            MySPRTLabel1.doPrintInRegister(iPrinter, projectName, user, name, spec, count,
                    barcode, printCount);
        } else {
            isShowPro=true;
            prepareConnect();
            Toast.makeText(mContext,"未打印!请用户选择补打标签",Toast.LENGTH_LONG).show();
        }
    }
    //    private EditText edv_InfoName, edv_InfoCode, edv_InfoModel;
//    private TextView conn_state;
    //    private TextView conn_address, edv_CKNodebh;
    private TextView conn_name;
    private Button btnPrint;

//    private void initUI() {
//        conn_state = (TextView) findViewById(R.id.connect_state);
//        conn_name = (TextView) findViewById(R.id.connect_name);
//    }


    protected static IPrinterOpertion myOpertion;
    //    private PrinterInstance mPrinter;
    private ProgressDialog dialog;
    //    private static boolean isConnected;
    // 用于接受连接状态消息的 Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ProgressDialogUtils.dismissProgressDialog();
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    mPrinter = myOpertion.getPrinter();
                    break;
                case PrinterConstants.Connect.FAILED:
                    isConnected = false;
                    Toast.makeText(mContext, R.string.conn_failed,
                            Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.CLOSED:
                    isConnected = false;
                    Toast.makeText(mContext, R.string.conn_closed,
                            Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    Toast.makeText(mContext, R.string.conn_no, Toast.LENGTH_SHORT)
                            .show();
                    break;

                default:
                    break;
            }

//            updateButtonState();

            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    };

    private void showRemind() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.str_message)
                .setMessage(R.string.str_connlast)
                .setPositiveButton(R.string.yesconn,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                myOpertion = new BluetoothOperation(
                                        mContext, mHandler);
                                Context context = mContext;
                                myOpertion.btAutoConn(context, mHandler);

                            }
                        })
                .setNegativeButton(R.string.str_resel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                myOpertion = new BluetoothOperation(
                                        mContext, mHandler);
                                myOpertion.chooseDevice();
                            }

                        }).show();
    }



    protected void selectBT(int requestCode) {
        Intent connectBTIntent = new Intent(this, BTSelectActivity.class);
        startActivityForResult(connectBTIntent, requestCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myOpertion != null && mPrinter != null) {
            myOpertion.close();
            myOpertion = null;
            mPrinter = null;
        }
        if (iPrinter != null) {
            iPrinter.disconnect();
        }
    }

    public static final int CONNECT_DEVICE = 1;
    public static final int ENABLE_BT = 2;
    private String bt_mac;
    private String bt_name;


    public Context mContext;
    private BluetoothAdapter mBTAdapter;
    //    private Button connnect, print, clear, getStatus;
    private static String name, address;
    private SharedPreferences sharedPre;
    private static final String TAG = "MainActivity";
    protected static final int REQUEST_ENABLE_BT = 3;
    protected static final int REQUEST_CONNECT_BT = 4;
    protected static final int REQUEST_CONNECT_BT_RE = 5;
    protected SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
    protected BasePrinter iPrinter;
    private View v1;
    private TextView status;
    public PrinterInstance mPrinter;
    public static boolean isConnected = false;

    @Override
    protected void onActivityResult(final int requestCode, int resultCode,
                                    final Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                prepareConnect();
            } else {
                Toast.makeText(this, "蓝牙打开失败", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CONNECT_BT) {
            if (resultCode == RESULT_OK && data != null) {
                Map<String, String> resultMap = resolveExtra(data);
                String name = resultMap.get("name");
                String address = resultMap.get("address");
                doConnect(name, address);
            } else {
                Toast.makeText(this, "未选择打印机", Toast.LENGTH_LONG).show();
                showConnectState("连接打印机");
            }
        } else if (requestCode == REQUEST_CONNECT_BT_RE) {
            if (resultCode == RESULT_OK && data != null) {
                Map<String, String> resultMap = resolveExtra(data);
                String name = resultMap.get("name");
                String address = resultMap.get("address");
                doConnect(name, address);
            } else {
                Toast.makeText(this, "未选择打印机", Toast.LENGTH_LONG).show();
                showConnectState("连接打印机");
            }
        }
    }

    private Map<String, String> resolveExtra(Intent data) {
        Bundle connectExtra = data.getExtras();
        String name = connectExtra.getString(BTDevicesListActivity.DEVICE_NAME);
        String address = connectExtra
                .getString(BTDevicesListActivity.DEVICE_ADDRESS);
        Map<String, String> result = new HashMap<String, String>();
        result.put("name", name);
        result.put("address", address);
        return result;
    }

    private boolean isShowPro=false;//是否显示dialog提示正在连接

    protected Handler printerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PrinterConstance.Connect.CONNECT_START:
                    showConnectingDialog(isShowPro);
                    break;
                case PrinterConstance.Connect.CONNECT_FINISH:
                    dismissConnectingDialog();
                    isConnected = (Boolean) msg.obj;
                    Toast.makeText(PrintActivity.this,
                            "连接" + (isConnected ? "成功" : "失败"), Toast.LENGTH_SHORT)
                            .show();
                    if (isConnected) {
                        showConnectState("打印机状态：已连接（点击则关闭）");
                    } else {// 没有断开连接时候连接断开了
                        sharedPre.edit().clear().commit();
                        showConnectState("打印机状态：未连接（点击则连接）");
                        showPrintName(BluetoothPort.getmDeviceName());
                    }
                    break;
                case PrinterConstance.Connect.PRINT_START:
                    // saveAndPrint.setEnabled(false);
                    break;
                case PrinterConstance.Connect.PRINT_DONE:
                    // saveAndPrint.setEnabled(true);
                    break;
                case PrinterConstance.Connect.CONNECT_CLOSE:// 手动断开的连接
                    dismissConnectingDialog();
                    isConnected = (Boolean) msg.obj;
                    Toast.makeText(PrintActivity.this, "您已断开连接", Toast.LENGTH_SHORT)
                            .show();
                    if (isConnected) {
                        showConnectState("断开打印机");
//                        conn_state.setText("断开连接");
                    } else {// 点击了断开连接
                        showConnectState("连接打印机");
//                        conn_state.setText("打开连接");
                    }
                    break;
                case PrinterConstance.Connect.TOAST:
                    Toast.makeText(PrintActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();
                    break;
            }
            return false;
        }
    });

    public void prepareConnect() {

        name = sharedPre.getString(Constant.BT_DEVICE_NAME, "");
        address = sharedPre.getString(Constant.BT_DEVICE_ADDRESS, "");
        Log.i("fdh", "address:" + address);
        if (TextUtils.isEmpty(address)) {
            Log.i("fdh", "为空");
            selectBT(REQUEST_CONNECT_BT);// 连接蓝牙请求到页面
        } else
            doConnect(name, address);
    }

    private void doConnect(String name, String address) {
        new ConnectThread(name, address).start();
    }


    private class ConnectThread extends Thread {
        private String deviceName;
        private String deviceMac;

        public ConnectThread(String deviceName, String deviceMac) {
            this.deviceName = deviceName;
            this.deviceMac = deviceMac;
        }

        @Override
        public void run() {
            Looper.prepare();
            printerHandler.obtainMessage(PrinterConstance.Connect.CONNECT_START).sendToTarget();
            iPrinter = new SPRTPrinter(PrintActivity.this, printerHandler);
            iPrinter.connect(deviceMac);
            SharedPreferences.Editor editor = sharedPre.edit();
            editor.putString(Constant.BT_DEVICE_NAME, deviceName);
            editor.putString(Constant.BT_DEVICE_ADDRESS, deviceMac);
            editor.commit();
            Looper.loop();
        }
    }


    private ProgressDialog connectingDialog;

    private void showConnectingDialog(boolean isShowPro) {
        if(!isShowPro){
            showMessage("正在连接蓝牙打印机");
        }else{
            try {
                connectingDialog = new ProgressDialog(this);
                connectingDialog.setTitle("正在连接蓝牙打印机");
                connectingDialog.setMessage("正在连接...");
                connectingDialog.setCancelable(false);
                connectingDialog.setCanceledOnTouchOutside(false);
                connectingDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void dismissConnectingDialog() {
        try {
            if (connectingDialog != null && connectingDialog.isShowing()
                    && !isFinishing())
                connectingDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void openConn() {
        if (!isConnected) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.str_message)
                    .setMessage(R.string.str_connlast)
                    .setPositiveButton(R.string.yesconn,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    if (Constant.BT_DEVICE_ADDRESS != null
                                            | Constant.BT_DEVICE_NAME != null) {
                                        prepareConnect();

                                    } else if (TextUtils.isEmpty(address)) {
                                        Toast.makeText(mContext,
                                                R.string.conn_false,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton(R.string.str_resel,
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    selectBT(REQUEST_CONNECT_BT);
                                }

                            }).show();
        } else {
            if (iPrinter != null) {
                iPrinter.disconnect();
            }
        }
    }
}
