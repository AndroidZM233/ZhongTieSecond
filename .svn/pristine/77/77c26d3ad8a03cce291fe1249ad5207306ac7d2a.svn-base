package com.speedata.activity.allot;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.BaseActivity;
import com.speedata.activity.out.OutAgainBarCodeActivity;
import com.speedata.activity.out.OutDownLoadActivity;
import com.speedata.activity.out.OutRegisterActivity;
import com.speedata.activity.out.OutUploadActivity;
import com.speedata.activity.out.UserRejectActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.bean.ResultEntity;
import com.speedata.bean.StoreBean;
import com.speedata.dao.InRegisterDao;
import com.speedata.dao.OutRegisterDao;
import com.speedata.dao.StoreDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.speedata.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

public class AllotActivity extends BaseActivity implements OnClickListener {

    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    //	private myAdapter adapter = new myAdapter(mContext , listStr);
    private InRegisterDao inRegisterDao;
    private StoreDao storeDao;
    private OutRegisterDao outRegisterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        initList();
        initView();
        inRegisterDao = new InRegisterDao(mContext);
        storeDao = new StoreDao(mContext);
        outRegisterDao = new OutRegisterDao(mContext);
    }

    public void initList() {
        list.add("");//发料登记
        list.add("");//上传
        list.add("");//下载
        list.add("");
        imgList.add(R.drawable.regist);
        imgList.add(R.drawable.icon_arrow_up);
        imgList.add(R.drawable.icon_down);
        imgList.add(R.drawable.icon_computer);
    }

    public void initView() {
        mGridView = (GridView) findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mContext, imgList);
        mGridView.setAdapter(adapter);
//        mBack = (ImageView) findViewById(R.id.upload_back_btn);
        mBack = getBackBtn();
        setTitle("调拨");
        mBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                switch (position) {
                    case 0:
                        // 进入发料登记界面：
                        startActivity(new Intent(mContext, AllotRegisterActivity.class));
                        break;
                    case 1:
                        // 弹出提示框
                        startActivity(new Intent(mContext, AllotUploadActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mContext, AllotDownLoadActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext,AllotAgainBarCodeActivity.class));
                        break;
                }
            }
        });
    }

    private MyLogger logger = MyLogger.jLog();
    private List<StoreBean> storeBeanList = new ArrayList<StoreBean>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ResultEntity entity = (ResultEntity) msg.obj;
            if (entity.isSuccess()) {
                ProgressDialogUtils.dismissProgressDialog();
                new AlertDialog.Builder(mContext).setMessage("成功！"
                ).show();
            } else {
                ProgressDialogUtils.dismissProgressDialog();
                new AlertDialog.Builder(mContext).setMessage("失败，返回信息！" + entity.getData()
                ).show();
            }

        }
    };

    @Override
    public void onClick(View v) {
//        int ID = v.getId();
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.manager_project);
//        final EditText editText = (EditText) dialog
//                .findViewById(R.id.manager_project_edit);
//        final EditText editText1 = (EditText) dialog
//                .findViewById(R.id.manager_project_edit2);
//        final TextView topTv = (TextView) dialog
//                .findViewById(R.id.manager_project_Top);
//        final TextView oneTv = (TextView) dialog
//                .findViewById(R.id.manager_project_text);
//        final TextView twoTv = (TextView) dialog
//                .findViewById(R.id.manager_project_text2);
//        final Button buttonConfirm = (Button) dialog
//                .findViewById(R.id.manager_project_confirm);
//        final Button buttonCancel = (Button) dialog
//                .findViewById(R.id.manager_project_cancel);
//        final LinearLayout linear = (LinearLayout) dialog
//                .findViewById(R.id.manager_project_linear);
//
//        switch (ID) {
//            case R.id.out_linear_center:
//                // viewPager显示一个提示框
//                linear.setVisibility(View.VISIBLE);
//                topTv.setVisibility(View.GONE);
//                oneTv.setText("起始日期");
//                twoTv.setText("终止日期");
//                buttonConfirm.setText("上传");
//                buttonConfirm.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                buttonCancel.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//                break;
//            case R.id.out_linear_right:
//                // viewPager显示一个提示框'
//                linear.setVisibility(View.VISIBLE);
//                topTv.setVisibility(View.GONE);
//                oneTv.setText("起始日期");
//                twoTv.setText("终止日期");
//                buttonConfirm.setText("下载");
//                buttonConfirm.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                buttonCancel.setOnClickListener(new OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//                break;
//            case R.id.out_linear_left:
//                // fragment显示发料表,且textView的字体颜色变红
//                break;
//        }
    }

}

