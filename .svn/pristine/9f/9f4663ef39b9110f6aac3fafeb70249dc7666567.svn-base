package com.speedata.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.dreamdemo.R;
import com.speedata.updateversion.UpdateVersion;
import com.speedata.utils.Constant;

public class SetAutoUpdateActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private EditText edvPort, edvIP;
    private Button btnSave;
    private Button btnUpdate;

    private void initUI() {
        setContentView(R.layout.activity_set_auto_update);
        setTitle("自动更新");
        edvPort = (EditText) findViewById(R.id.edv_port);
        edvIP = (EditText) findViewById(R.id.edv_ip);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        edvPort.setText(app.getSharedPreferences().getString(Constant.FIELD_UPDATE_PORT, Constant
                .FIELD_UPDATE_PORT_DEFAUT));
        edvIP.setText(app.getSharedPreferences().getString(Constant.FIELD_UPDATE_IP, Constant
                .FIELD_UPDATE_IP_DEFAUT));
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            String ip = edvIP.getText().toString();
            String port = edvPort.getText().toString();
            if (ip.equals("") || port.equals("")) {
                Toast.makeText(mContext, "不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            app.getEditor().putString(Constant.FIELD_IP, ip);
            app.getEditor().putString(Constant.FIELD_PORT, port);
            app.getEditor().commit();
            finish();
        } else if (v == btnUpdate) {
            UpdateVersion updateVersion = new UpdateVersion(this);
            updateVersion.startUpdate();
        }
    }
}
