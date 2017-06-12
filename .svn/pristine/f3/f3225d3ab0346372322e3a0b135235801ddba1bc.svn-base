package com.speedata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.speedata.bean.UserForm;
import com.speedata.dreamdemo.R;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private List<UserForm> list;
    private Context context;

    public UserListAdapter(Context context, List<UserForm> list) {
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
            convertView = View.inflate(context, R.layout.adapter_user, null);
            holder = new ViewHolder();
            holder.tvUserId = (TextView) convertView.findViewById(R.id.tv_user_id);
            holder.tvUserRealName = (TextView) convertView.findViewById(R.id.tv_real_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvUserId.setText(list.get(position).getUserName());
        holder.tvUserRealName.setText(list.get(position).getRealName() + "");
        return convertView;
    }

    class ViewHolder {
        TextView tvUserId, tvUserRealName;
    }
}
