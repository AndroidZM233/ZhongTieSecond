package com.speedata.activity.print;

import java.util.regex.Pattern;

import android.bluetooth.BluetoothAdapter;

public class BluetoothMsg {
    /**
     * 蓝牙连接类型
     */
    public enum ServerOrClient {
        NONE, SERVER, CLIENT;
    }

    // 蓝牙
    public static BluetoothAdapter adapter = null;

    // 蓝牙连接方式
    public static ServerOrClient serverOrClient = ServerOrClient.NONE;
    // 连接蓝牙地址
    public static String bluetoothName = "";
    public static String bluetoothAddress = "";
    public static String lastBluetoothAddress = "";
    // 通信线程是否开启
    public static boolean isOpen = false;

    private static final String MAC_REGEX = "^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2}){5}$";

    public static boolean isMacAddressMatched(String address) {
        if (address == null)
            return false;
        return Pattern.matches(MAC_REGEX, address);
    }
}
