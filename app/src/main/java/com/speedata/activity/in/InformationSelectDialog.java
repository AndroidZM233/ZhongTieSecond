package com.speedata.activity.in;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.speedata.adapter.InforSelectAdapter;
import com.speedata.adapter.SimpleTreeAdapter;
import com.speedata.bean.AllMetrailClass;
import com.speedata.bean.CommonForm;
import com.speedata.dao.AllMetrailClassDao;
import com.speedata.dreamdemo.R;
import com.speedata.utils.MyLogger;
import com.zhy.bean.FileBean;
import com.zhy.tree.bean.Node;
import com.zhy.tree.bean.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class InformationSelectDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private Context mContext;
    private String type;
    private AllMetrailClassDao metrailClassDao;
    private MyLogger logger = MyLogger.jLog();
    private List<CommonForm> commonFormList;

    public InformationSelectDialog(Context context, CallBack callBack, List<CommonForm>
            commonFormList) {
        super(context);
        this.callBack = callBack;
        mContext = context;
        this.commonFormList = commonFormList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private ListView mTree;
    private InforSelectAdapter inforSelectAdapter;

    private void initUI() {
        setContentView(R.layout.dialog_information_select);
        mTree = (ListView) findViewById(R.id.id_tree);
        inforSelectAdapter=new InforSelectAdapter(mContext,commonFormList);
        mTree.setAdapter(inforSelectAdapter);
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callBack.callBack(commonFormList.get(position));
                dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
    }

    public interface CallBack {
        public void callBack(CommonForm commonForm);
    }
}
