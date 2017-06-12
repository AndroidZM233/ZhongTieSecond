package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.CommonForm;
import com.speedata.bean.StoreBean;
import com.speedata.dreamdemo.R;

import java.util.List;

public class PrinterListAdapter extends BaseAdapter {

    private List<CommonForm> list;
    private Context context;

    public PrinterListAdapter(Context context, List<CommonForm> list) {
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
            convertView = View.inflate(context, R.layout.adapter_print_list, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCode = (TextView) convertView.findViewById(R.id.tv_code);
            holder.tvSpec = (TextView) convertView.findViewById(R.id.tv_spec);
//            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getInfoName());
        holder.tvCode.setText(list.get(position).getInfoCode() + "");
        holder.tvSpec.setText(list.get(position).getInfoModel());
//        holder.tvLocation.setText(list.get(position).getCKAbbName());//存放地址
        return convertView;
    }

    class ViewHolder {
        TextView tvName, tvCode, tvSpec, tvLocation;
    }
}
