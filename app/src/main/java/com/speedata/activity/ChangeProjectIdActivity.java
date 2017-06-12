package com.speedata.activity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.speedata.bean.ResultEntity;
import com.speedata.dreamdemo.R;
import com.speedata.model.HttpInterfaceModel;
import com.speedata.utils.Constant;
import com.speedata.utils.ProgressDialogUtils;
import com.speedata.utils.WebServiceUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ChangeProjectIdActivity extends BaseActivity implements View.OnClickListener {
    private HashMap<String, Object> properties = new HashMap<String, Object>();
    private String projectid;
    private String phoneid;
    public String departmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private EditText edvProjectId , edvPhoneId;
    private Button btnSave;

    private void initUI() {
        setContentView(R.layout.activity_change_project_id);
        setTitle("设置项目编号");
        edvProjectId = (EditText) findViewById(R.id.edv_port);
        edvPhoneId= (EditText) findViewById(R.id.edv_phoneNumber);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        edvProjectId.setText(ProjectID);
        edvPhoneId.setText(PhoneID);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            projectid = edvProjectId.getText().toString();
            if (projectid.equals("")) {
                Toast.makeText(mContext, "不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            phoneid = edvPhoneId.getText().toString();
            if (phoneid.equals("")) {
                Toast.makeText(mContext, "不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            app.getEditor().putString(Constant.FIELD_PROJETC, projectid).commit();
            app.getEditor().putString(Constant.PHONE_ID,phoneid).commit();
            ProgressDialogUtils.showProgressDialog(this, "数据配置中...");
            downLoadInfors(HttpInterfaceModel.GetDepartment);

        }

    }


    private void downLoadInfors(String getProjectName) {
        final WebServiceUtils utils = new WebServiceUtils(getProjectName, mContext);
        properties.clear();
        properties.put("ProjectID", projectid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                utils.callWebService(mContext, properties, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(ResultEntity result) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result.getData());
                            departmentName = jsonObject.getString("DepartmentAbbreviation");
                            Log.d("====departmentName", departmentName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        app.getEditor().putString(Constant.PROJECT_NAME, departmentName).commit();

                        if (cancleProdialog(result)) return;
                        Message msg=new Message();
                        msg.what=1;
                        handler.sendMessage(msg);
                        finish();
                    }
                });

            }
        }).start();
    }

    private boolean cancleProdialog(ResultEntity result) {
        Message msg = new Message();
        msg.obj = result;
        msg.what = 0;
        handler.sendMessage(msg);

        if (!result.isSuccess()) {
            return true;
        }
        return false;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ResultEntity entity = (ResultEntity) msg.obj;
                    ProgressDialogUtils.dismissProgressDialog();
                    if (!entity.isSuccess()) {
                        new AlertDialog.Builder(mContext).setMessage("配置失败" + entity.getData()).show();
                    }
                    break;
                case 1:
                    Toast.makeText(mContext,"配置成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}