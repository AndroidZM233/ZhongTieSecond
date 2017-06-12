package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.StoreBean;
import com.speedata.dreamdemo.R;

import java.util.List;

public class RejectSupplierListAdapter extends BaseAdapter {

    private List<StoreBean> list;
    private Context context;

    public RejectSupplierListAdapter(Context context, List<StoreBean> list) {
        this.context = context;
        this.list = list;
    }
    public void refresh(){
        this.notifyDataSetChanged();
    }
    public void clear(){
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
            convertView = View.inflate(context, R.layout.adapter_reject_supplier, null);
            holder = new ViewHolder();
            holder.tvBatch = (TextView) convertView.findViewById(R.id.tv_batch);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
//            holder.tvInForName = (TextView) convertView.findViewById(R.id.tv_supper);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvBatch.setText(list.get(position).getBarCode());
        holder.tvCount.setText(list.get(position).getQuantity() + "");
//        holder.tvInForName.setText(list.get(position).getSupplierName());
//        holder.tvLocation.setText(list.get(position).getCKAbbName());//存放地址
        return convertView;
    }

    class ViewHolder {
        TextView tvBatch, tvCount, tvSupper, tvLocation;
    }
}
