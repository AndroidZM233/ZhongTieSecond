package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.OutRegister;
import com.speedata.bean.OutRegister;
import com.speedata.dreamdemo.R;

import java.util.List;

public class UserRejectListAdapter extends BaseAdapter {

    private List<OutRegister> list;
    private Context context;

    public UserRejectListAdapter(Context context, List<OutRegister> list) {
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
            convertView = View.inflate(context, R.layout.item_out_lv, null);
            holder = new ViewHolder();
            holder.tvBatch = (TextView) convertView.findViewById(R.id.tv_batch);
            holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
            holder.tvSupper = (TextView) convertView.findViewById(R.id.tv_supper);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvBatch.setText(list.get(position).getBarCode());
        holder.tvCount.setText(list.get(position).getQuantity() + "");
//        holder.tvSupper.setText(list.get(position).getSupplierName());
//        holder.tvLocation.setText(list.get(position).getCKAbbName());//存放地址
        return convertView;
    }

    class ViewHolder {
        TextView tvBatch, tvCount, tvSupper, tvLocation;
    }
}
