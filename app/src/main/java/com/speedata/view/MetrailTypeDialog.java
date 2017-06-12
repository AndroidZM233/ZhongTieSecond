package com.speedata.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.speedata.adapter.SimpleTreeAdapter;
import com.speedata.bean.CommonMaterialInfo;
import com.speedata.dao.CommonMaterialInfoDao;
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
public class MetrailTypeDialog extends Dialog implements View.OnClickListener {
    private CallBack callBack;
    private Context mContext;
    private String type;
    private CommonMaterialInfoDao commonMaterialInfoDao;
    private MyLogger logger = MyLogger.jLog();
    private String FirstClassName;
    private String SecondClassName;
    private String InfoClassName;
    private List<FileBean> mDatas= new ArrayList<FileBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private static final int Success=0;
    private LinearLayout linearLayout;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Success:

                    break;
            }
        }
    };


    public MetrailTypeDialog(Context context, List<FileBean> mDatas,CallBack callBack) {
        super(context);
        this.callBack = callBack;
        mContext = context;
        this.mDatas=mDatas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    public void initUI() {
        setContentView(R.layout.dialog_location);
        linearLayout= (LinearLayout) findViewById(R.id.dialog_ll);
        mTree = (ListView) findViewById(R.id.id_tree);
//        Message message=new Message();
//        message.what=Success;
//        handler.sendMessage(message);
        linearLayout.setVisibility(View.GONE);
        mTree.setVisibility(View.VISIBLE);
        mProgressDialog = new ProgressDialog(mContext);
        try {
            mAdapter = new SimpleTreeAdapter<FileBean>(mTree, mContext, mDatas, 0);

            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if (node.isLeaf()) {
                        callBack.callBack(node.getName());
                        MetrailTypeDialog.this.dismiss();
                    }
                }

            });
            mTree.setAdapter(mAdapter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



//    //data 为上级编码   len为上级编码长度
//    private List<AllMetrailClass> query(String data, int parentId, int offset) {
//        List<AllMetrailClass> infor;
//        offset += _offset;
//        infor = allMetrailClassDao.imRawQuery("select * from AllMetrailClass where length " +
//                "(ClassNodebh) = " + (data.length() + 1) +
//                " and ClassCode= " + data, null, AllMetrailClass
//                .class);
//        if (infor.size() == 0) {
//            return null;
//        }
//        logger.d("=======infor.size()=" + infor.size());
//        for (int i = 0; i < infor.size(); i++) {
//            int id = i + parentId + 1 + offset;
//            mDatas.add(new FileBean(id, parentId, infor.get(i).getClassName(), infor
//                    .get(i)
//                    .getClassNodebh() + ""));
//            logger.d("=======id=" + id + "  parentid=" + parentId + " CKNode" + infor.get(i)
//                    .getClassCode());
////            if (!infor.get(i).isLeaf()) {
////                query(infor.get(i).getClassCode(), id, infor.size());
////            }
//        }
//        return infor;
//    }


    @Override
    public void onClick(View v) {
    }

    public interface CallBack {
        public void callBack(String ClassNodebh);
    }
}
