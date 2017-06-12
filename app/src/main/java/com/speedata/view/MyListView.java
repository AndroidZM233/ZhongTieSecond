package com.speedata.view;

import com.speedata.dreamdemo.R;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;


public class MyListView extends ListView implements OnScrollListener{

	private int state = 0;
	private final static int DONE = 0;
	private final static int PULL_REFRASH = 1;
	private final static int RELASE_REFRASH = 2;
	private final static int PULL_REFRASHING = 3;
	private final static int LOAD_REFRASH = 4;
	private final static int LOAD_RELASEREFRASH = 5;
	private final static int LOAD_REFRASHING = 6;
	private final static int RAIO = 3;
	private View headView;
	private View footervView;
	private TextView headTv;
	private TextView footerTv;
	private boolean PullFlag = false;
	private boolean LoadFlag = false;
	private int height = 0;
	PointF pf = new PointF();
	private int totalItemCount=0;
	private TouchCall call;

	public void setCall(TouchCall call){
		this.call = call;
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void measureView(){
		headView.measure(MeasureSpec.makeMeasureSpec(
						LinearLayout.LayoutParams.MATCH_PARENT,
						MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(
						LinearLayout.LayoutParams.WRAP_CONTENT
						, MeasureSpec.UNSPECIFIED));
		height = headView.getMeasuredHeight();
	}

	private void initView(){
		setOnScrollListener(this);
		headView = View.inflate(getContext(), R.layout.headview, null);
		footervView = View.inflate(getContext(), R.layout.headview, null);
		headTv = (TextView) headView.findViewById(R.id.Tv);
		footerTv = (TextView) footervView.findViewById(R.id.Tv);
		measureView();
		headView.setPadding(0, -height, 0, 0);
		footervView.setPadding(0, 0, 0, -height);
		addHeaderView(headView, null, false);
		addFooterView(footervView, null, false);
	}

	private void stateTv(){
		switch(state){
			case DONE:
				headTv.setText("下拉刷新");
				footerTv.setText("上拉更多");
				headView.setPadding(0,-height, 0, 0);
				footervView.setPadding(0, 0, 0, -height);
				break;
			case PULL_REFRASH:
				headTv.setText("下拉刷新");
				break;
			case RELASE_REFRASH:
				headTv.setText("释放刷新");
				break;
			case PULL_REFRASHING:
				headView.setPadding(0,0, 0, 0);
				headTv.setText("正在刷新");
				call.PullCall();
				break;
			case LOAD_REFRASH:
				footerTv.setText("上拉更多");
				break;
			case LOAD_RELASEREFRASH:
				footerTv.setText("释放立即加载");
				break;
			case LOAD_REFRASHING:
				footerTv.setText("正在加载");
				footervView.setPadding(0, 0, 0, 0);
				call.LoadCall();
				break;
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(ev.getAction() == MotionEvent.ACTION_DOWN){
			if(PullFlag||LoadFlag){
				pf.x = ev.getX();
				pf.y = ev.getY();
			}
		}else if(ev.getAction() == MotionEvent.ACTION_MOVE){
			float tem_x = ev.getX();
			float tem_y = ev.getY();
			if((Math.abs((tem_x-pf.x))/2-Math.abs(tem_y-pf.y))>10){
				return super.dispatchTouchEvent(ev);
			}else
			if((Math.abs((tem_x-pf.x))/2-Math.abs(tem_y-pf.y)<0)){
				if(PullFlag && !LoadFlag){
					if(state!=PULL_REFRASHING){
						MyListView.this.setSelection(0);
						if(state == PULL_REFRASH){
							if((tem_y-pf.y)<=0){
								state = DONE;
								stateTv();
							}
							if((tem_y-pf.y)/RAIO>height){
								state = RELASE_REFRASH;
								stateTv();
							}
						}
						if(state == RELASE_REFRASH){
							if((tem_y-pf.y)/RAIO<height){
								state = PULL_REFRASH;
								stateTv();
							}
						}
					}
					if(state == DONE){
						if((tem_y-pf.y)>0){
							state = PULL_REFRASH;
							stateTv();
						}
					}
					if(state == PULL_REFRASH){
						MyListView.this.setSelection(0);
						headView.setPadding(0, (int)((tem_y-pf.y)/RAIO-height), 0, 0);
					}
					if(state == RELASE_REFRASH){
						MyListView.this.setSelection(0);
						headView.setPadding(0, (int)((tem_y-pf.y)/RAIO-height), 0, 0);
					}
					return true;
				}
				else if(LoadFlag&&!PullFlag){
					if(state != LOAD_REFRASHING){
						if(state == LOAD_REFRASH){
							MyListView.this.setSelection(totalItemCount-1);
							if(tem_y-pf.y > 0){
								state = DONE;
								stateTv();
							}
							if((Math.abs((tem_y- pf.y))/RAIO)>=height){
								state = LOAD_RELASEREFRASH;
								stateTv();
							}
						}
						if(state == LOAD_RELASEREFRASH){
							if((Math.abs((tem_y- pf.y))/RAIO)<height){
								state = LOAD_REFRASH;
								stateTv();
							}
						}
					}
					if(state == DONE){
						if((tem_y-pf.y)/RAIO<=0){
							state = LOAD_REFRASH;
						}
					}
					if(state == LOAD_REFRASH){
						MyListView.this.setSelection(totalItemCount-1);
						footervView.setPadding(0, 0, 0, (int)(Math.abs((tem_y- pf.y))/RAIO)-height);
					}
					if(state == LOAD_RELASEREFRASH){
						MyListView.this.setSelection(totalItemCount-1);
						footervView.setPadding(0, 0, 0, (int)(Math.abs(tem_y- pf.y)/RAIO)-height);
					}
					return true;
				}
			}
		}else if(ev.getAction() == MotionEvent.ACTION_UP){
			PullFlag = false;
			LoadFlag = false;
			if(state ==PULL_REFRASH||state == LOAD_REFRASH)
				state = DONE;
			if(state ==RELASE_REFRASH)
				state = PULL_REFRASHING;
			if(state == LOAD_RELASEREFRASH)
				state = LOAD_REFRASHING;
			stateTv();
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem == 0)
			PullFlag = true;
		if((firstVisibleItem+visibleItemCount) == totalItemCount && (firstVisibleItem+visibleItemCount)!=0){
			LoadFlag = true;
		}
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	public interface TouchCall{
		public void PullCall();
		public void LoadCall();
	}

	public void complate(){
		state = DONE;
		PullFlag = false;
		LoadFlag = false;
		stateTv();
	}


}
