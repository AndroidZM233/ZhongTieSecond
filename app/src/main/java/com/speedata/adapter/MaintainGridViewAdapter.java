package com.speedata.adapter;

import java.util.List;
import com.speedata.dreamdemo.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MaintainGridViewAdapter extends BaseAdapter {

	private List<String> list;
	private Context context;

	public MaintainGridViewAdapter(List<String> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView mTv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_miantain_grid,
					null);
			holder.mTv = (TextView) convertView
					.findViewById(R.id.item_maintain_grid_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTv.setText(list.get(position));
		return convertView;
	}

}
