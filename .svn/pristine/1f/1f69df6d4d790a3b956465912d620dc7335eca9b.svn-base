package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.CheckForm;
import com.speedata.dreamdemo.R;

import java.util.List;

public class CheckUploadListAdapter extends BaseAdapter {

    private List<CheckForm> list;
    private Context context;

    public CheckUploadListAdapter(Context context, List<CheckForm> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_check_upload, null);
            holder = new ViewHolder();
            holder.tvInForName = (TextView) convertView.findViewById(R.id.tv_check_name);
            holder.tvInForModel = (TextView) convertView.findViewById(R.id.tv_check_model);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_qunantity);
            holder.tvOrderDate = (TextView) convertView.findViewById(R.id.tv_order_date);
            holder.tvInforCode = (TextView) convertView.findViewById(R.id.tv_infor_code);
            holder.tvInForUtil = (TextView) convertView.findViewById(R.id.tv_infor_unti);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvInForName.setText(list.get(position).getInfoName());
        holder.tvInForModel.setText(list.get(position).getInfoModel() + "");
        holder.tvQuantity.setText(list.get(position).getQuantity() + "");
        holder.tvOrderDate.setText(list.get(position).getCheckMonth());//存放地址
        holder.tvInforCode.setText(list.get(position).getInfoCode());
        holder.tvInForUtil.setText(list.get(position).getInfoUnit());
        return convertView;
    }

    class ViewHolder {
        TextView tvInForName, tvInForModel, tvQuantity, tvOrderDate;
        TextView tvInforCode, tvInForUtil;
    }
}
