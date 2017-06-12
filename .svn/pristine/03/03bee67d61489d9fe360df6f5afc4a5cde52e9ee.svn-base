package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.CommonForm;
import com.speedata.bean.CommonForm;
import com.speedata.dreamdemo.R;

import java.util.List;

public class InforSelectAdapter extends BaseAdapter {

    private List<CommonForm> list;
    private Context context;

    public InforSelectAdapter(Context context, List<CommonForm> list) {
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
            convertView = View.inflate(context, R.layout.adapter_infromation_select, null);
            holder = new ViewHolder();
            holder.tvInforModle = (TextView) convertView.findViewById(R.id.tv_infor_model);
            holder.tvInforCode = (TextView) convertView.findViewById(R.id.tv_infor_code);
            holder.tvInForName = (TextView) convertView.findViewById(R.id.tv_infor_name);
            holder.tvInForUnti = (TextView) convertView.findViewById(R.id.tv_infor_unti);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvInForUnti.setText(list.get(position).getInfoUnit());
        holder.tvInForName.setText(list.get(position).getInfoName());
        holder.tvInforCode.setText(list.get(position).getInfoCode());
        holder.tvInforModle.setText(list.get(position).getInfoModel());
        return convertView;
    }

    class ViewHolder {
        TextView tvInForName, tvInForUnti, tvInforCode, tvInforModle;
    }
}
