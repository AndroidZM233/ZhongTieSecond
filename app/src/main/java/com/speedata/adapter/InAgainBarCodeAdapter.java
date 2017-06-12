package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.speedata.bean.InRegister;
import com.speedata.dreamdemo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InAgainBarCodeAdapter extends BaseAdapter {

    private List<InRegister> list;
    private Context context;
    public static Map<Integer, Boolean> isSelected;

    public InAgainBarCodeAdapter(Context context, List<InRegister> list) {
        this.context = context;
        this.list = list;
        init();
    }

    private void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < list.size(); i++) {
            isSelected.put(i, false);
        }
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
            convertView = View.inflate(context, R.layout.adapter_in_again, null);
            holder = new ViewHolder();
            holder.tvSupper = (TextView) convertView.findViewById(R.id.tv_supper);
            holder.tvBarcode = (TextView) convertView.findViewById(R.id.tv_barcode);
            holder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_qunantity);
            holder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
            holder.tvInforModle = (TextView) convertView.findViewById(R.id.tv_infor_model);
            holder.tvInforCode = (TextView) convertView.findViewById(R.id.tv_infor_code);
            holder.tvInForName = (TextView) convertView.findViewById(R.id.tv_infor_name);
            holder.tvInForUnti = (TextView) convertView.findViewById(R.id.tv_infor_unti);
            holder.checkBox= (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSupper.setText(list.get(position).getSupplierName());
        holder.tvBarcode.setText(list.get(position).getBarCode() + "");
        holder.tvQuantity.setText(list.get(position).getQuantity() + "");
        holder.tvInForUnti.setText(list.get(position).getInfoUnit());
        holder.tvInForName.setText(list.get(position).getInfoName());
        holder.tvInforCode.setText(list.get(position).getInfoCode());
        holder.tvInforModle.setText(list.get(position).getInfoModel());
        holder.checkBox.setChecked(isSelected.get(position));
        return convertView;
    }

    public class ViewHolder {
        public TextView tvSupper, tvBarcode, tvQuantity, tvLocation;
        public TextView tvInForName, tvInForUnti, tvInforCode, tvInforModle;
        public CheckBox checkBox;
    }
}
