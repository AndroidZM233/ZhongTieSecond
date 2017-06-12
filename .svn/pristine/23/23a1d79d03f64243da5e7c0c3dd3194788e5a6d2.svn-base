package com.speedata.activity.print;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.dreamdemo.R;
import com.speedata.view.ScrollListView;
import com.speedata.view.SimpleViewHolder;

public class BTDevicesListActivity extends Activity {
    private BluetoothAdapter btAdapter;

    private ScrollListView bondedDeviceList;
    private ScrollListView availableDeviceList;
    private TextView availableLabel;

    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";

    private List<String> bonded = new ArrayList<String>();
    private List<String> available = new ArrayList<String>();

    private BTDeviceAdapter bondedAdapter;
    private BTDeviceAdapter availableAdapter;

    private DiscoverBTReceiver disReceiver = new DiscoverBTReceiver();

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_select);

        btAdapter = BluetoothMsg.adapter;
        this.inflater = getLayoutInflater();

        findViews();
    }

    private void findViews() {
        bondedDeviceList = (ScrollListView) findViewById(R.id.bounded_device_list);
        availableDeviceList = (ScrollListView) findViewById(R.id.available_device_list);
        availableLabel = (TextView) findViewById(R.id.available_label_text);

        bondedDeviceList.setOnItemClickListener(listItemClick);
        availableDeviceList.setOnItemClickListener(listItemClick);

        getBondedDevices();
        bondedAdapter = new BTDeviceAdapter(bonded);
        availableAdapter = new BTDeviceAdapter(available);
        bondedDeviceList.setAdapter(bondedAdapter);
        availableDeviceList.setAdapter(availableAdapter);
    }

    private void getBondedDevices() {
        Set<BluetoothDevice> bondedDevices = btAdapter.getBondedDevices();
        for (BluetoothDevice device : bondedDevices) {
            String name = device.getName();
            String address = device.getAddress();
            bonded.add(name + "\n" + address);
        }
    }

    private AdapterView.OnItemClickListener listItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            try {
                if (btAdapter.isDiscovering())
                    btAdapter.cancelDiscovery();

                String result = "";
                if (parent == bondedDeviceList)
                    result = bonded.get(position);
                else if (parent == availableDeviceList)
                    result = available.get(position);

                if (TextUtils.isEmpty(result)) {
                    selectResult(RESULT_CANCELED, "", "");
                    return;
                }

                String[] temp = result.split("\n");
                if (temp.length < 2) {
                    selectResult(RESULT_CANCELED, "", "");
                    return;
                }

                String address = temp[temp.length - 1];
                StringBuilder nameBuilder = new StringBuilder(temp[0]);
                if (temp.length > 2) {
                    for (int i = 1; i < temp.length - 1; i++) {
                        nameBuilder.append("\n" + temp[i]);
                    }
                }
                String name = nameBuilder.toString();
                BluetoothMsg.bluetoothAddress = address;
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

        if (btAdapter != null) {
            btAdapter.startDiscovery();
            availableLabel.setText(R.string.available_devices_searching);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (btAdapter.isDiscovering())
            btAdapter.cancelDiscovery();
        unregisterReceiver(disReceiver);
    }

    private class DiscoverBTReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                try {
                    // 搜索到新设备
                    BluetoothDevice btDevice = intent
                            .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 搜索没有配对过的蓝牙设备
                    if (btDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
                        String deviceName = btDevice.getName();
                        String deviceMac = btDevice.getAddress();
                        if (TextUtils.isEmpty(deviceName)) {
                            BluetoothDevice remote = btAdapter
                                    .getRemoteDevice(deviceMac);
                            deviceName = remote.getName();
                        }
                        String content = deviceName + "\n" + deviceMac;
                        if (!available.contains(content)) {
                            available.add(content);
                            availableAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                availableLabel.setText(R.string.available_devices_done);
            }
        }
    }

    private class BTDeviceAdapter extends BaseAdapter {
        private List<String> data = new ArrayList<String>();

        public BTDeviceAdapter(List<String> data) {
            this.data = data;
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
                convertView = inflater.inflate(
                        android.R.layout.simple_list_item_1, null);

            String content = data.get(position);
            TextView textview = SimpleViewHolder.get(convertView,
                    android.R.id.text1);
            textview.setText(content);

            return convertView;
        }
    }
}
