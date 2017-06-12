package com.speedata.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.speedata.activity.check.CheckDownLoadActivity;
import com.speedata.activity.check.CheckRegisterActivity;
import com.speedata.activity.check.CheckUpLoadActivity;
import com.speedata.adapter.UploadGridViewAdapter;
import com.speedata.dao.CheckFormDao;
import com.speedata.dreamdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/7.
 */
public class CheckFragment extends BaseFragment  {
    private CheckFormDao checkFormDao;
    private GridView mGridView;
    private ImageView mBack;
    private UploadGridViewAdapter adapter;
    private List<String> list = new ArrayList<String>();
    private List<Integer> imgList = new ArrayList<Integer>();
    private HashMap<String, Object> properties = new HashMap<String, Object>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
        checkFormDao = new CheckFormDao(mActivity);
    }
    public void initList() {
        list.add("");//盘点
        list.add("");//上传
        list.add("");//下载
        imgList.add(R.drawable.icon_check);
        imgList.add(R.drawable.icon_arrow_up);
        imgList.add(R.drawable.icon_down);
    }

    @Override
    public int setFragmentLayout() {
        return R.layout.fragment_check_main;
    }

    @Override
    public void findById(View view) {
        mGridView = (GridView) view.findViewById(R.id.upload_grid);
        adapter = new UploadGridViewAdapter(list, mActivity, imgList);
        mGridView.setAdapter(adapter);
        mBack = mActivity.getBackBtn();
        mActivity.setTitle("盘点");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new OperatorsHomeFragment());
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
                        openFragment(new CheckRegisterFragment());
                        break;
                    case 1:
                        // 弹出提示框
                        startActivity(new Intent(mActivity, CheckUpLoadActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mActivity, CheckDownLoadActivity.class));
                        break;
                }
            }
        });
    }
}
