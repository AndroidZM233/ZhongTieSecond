package com.speedata.activity.print;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class PrinterService extends Service {
    public PrinterService() {
    }
    private final IBinder mBinder = new LocalBinder();
    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }
    public class LocalBinder extends Binder {
        //ceshisss
        public PrinterService getService() {
            return PrinterService.this;
        }
    }
}
