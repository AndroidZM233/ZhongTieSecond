package com.speedata.activity.print;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.dreamdemo.R;
import com.speedata.view.ScrollListView;
import com.speedata.view.SimpleViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BTSelectActivity extends Activity {
    private BluetoothAdapter mBTAdapter;
    private static final int REQUEST_ENABLE_BT = 3;

    private ScrollListView bondedDeviceList;
    private ScrollListView availableDeviceList;
    private TextView availableLabel;

    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";

    private Map<String, String> bondedMap = new HashMap<String, String>();
    private Map<String, String> availableMap = new HashMap<String, String>();

    private BTDeviceAdapter bondedAdapter;
    private BTDeviceAdapter availableAdapter;

    private DiscoverBTReceiver disReceiver = new DiscoverBTReceiver();

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_select);
        this.inflater = getLayoutInflater();
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        findViews();
        refreshList();
    }

    private void refreshList() {
        getBondedDevices();
        bondedAdapter = new BTDeviceAdapter(bondedMap);
        availableAdapter = new BTDeviceAdapter(availableMap);
        bondedDeviceList.setAdapter(bondedAdapter);
        availableDeviceList.setAdapter(availableAdapter);
    }

    private void findViews() {
        bondedDeviceList = (ScrollListView) findViewById(R.id.bounded_device_list);
        availableDeviceList = (ScrollListView) findViewById(R.id.available_device_list);
        availableLabel = (TextView) findViewById(R.id.available_label_text);

        bondedDeviceList.setOnItemClickListener(listItemClick);
        availableDeviceList.setOnItemClickListener(listItemClick);
    }

    private void getBondedDevices() {
        Set<BluetoothDevice> bondedDevices = mBTAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            String name = device.getName();
            String address = device.getAddress();
            bondedMap.put(address, name);
        }
    }

    private AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (mBTAdapter.isDiscovering())
                    mBTAdapter.cancelDiscovery();

                String name = "";
                String address = "";
                if (parent == bondedDeviceList) {
                    String[] bondedKeys = bondedMap.keySet().toArray(new String[0]);
                    address = bondedKeys[position];
                    name = bondedMap.get(address);
                } else if (parent == availableDeviceList) {
                    String[] availableKeys = availableMap.keySet().toArray(
                            new String[0]);
                    address = availableKeys[position];
                    name = availableMap.get(address);
                }
                // BluetoothMsg.bluetoothAddress = address;
                selectResult(RESULT_OK, name, address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void selectResult(int resultCode, String name, String address) {
        Intent data = null;
        if (resultCode == RESULT_OK) {
            data = new Intent();
            data.putExtra(DEVICE_NAME, name);
            data.putExtra(DEVICE_ADDRESS, address);
        }
        setResult(resultCode, data);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(disReceiver, filter);

        if (mBTAdapter != null) {
            mBTAdapter.startDiscovery();
            availableLabel.setText(R.string.available_devices_searching);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBTAdapter == null) {
            Log.i("sprt", "mBTAdapter为空");
        }
        if (mBTAdapter.isDiscovering())
            mBTAdapter.cancelDiscovery();
        unregisterReceiver(disReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK)
                refreshList();
        }
    }

    private class DiscoverBTReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND))//当搜索设备中发现设备调用此方法
            {
                try {
                    // 搜索到新设备
                    BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice
                            .EXTRA_DEVICE);
                    // 搜索没有配对过的蓝牙设备
                    if (btDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                        String deviceName = btDevice.getName();
                        String deviceMac = btDevice.getAddress();
                        Log.i("sprt", "deviceName：" + deviceName + "\ndeviceMac:" + deviceMac);
                        if (TextUtils.isEmpty(deviceName)) {
                            BluetoothDevice remote = mBTAdapter.getRemoteDevice(deviceMac);
                            deviceName = remote.getName();
                        }
                        availableAdapter.insert(deviceMac, deviceName);
                        availableAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                availableLabel.setText(R.string.available_devices_done);//查找设备完毕
            }
        }
    }

    private class BTDeviceAdapter extends BaseAdapter {
        private Map<String, String> data;
        private List<String> keys = new ArrayList<String>();

        public BTDeviceAdapter(Map<String, String> data) {
            this.data = data;
            if (this.data != null) {
                String[] keyArray = this.data.keySet().toArray(new String[0]);
                for (int i = 0; i < keyArray.length; i++) {
                    keys.add(keyArray[i]);
                }
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
            String key = keys.get(position);
            String value = data.get(key);
            String content = value + "\n" + key;
            Log.i("sprt", "content:" + content);
            TextView textview = SimpleViewHolder.get(convertView, android.R.id.text1);
            textview.setTextColor(Color.parseColor("#ffffff"));
            textview.setText(content);
            return convertView;
        }

        public void insert(String address, String name) {
            if (!this.data.containsKey(address)) {
                this.data.put(address, name);
                this.keys.add(address);
            }
        }
    }
}
