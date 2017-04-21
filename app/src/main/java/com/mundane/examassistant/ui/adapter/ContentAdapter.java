package com.mundane.examassistant.ui.adapter;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mundane.examassistant.R;
import com.mundane.examassistant.bean.ContentItem;
import com.mundane.examassistant.global.Constant;
import com.mundane.examassistant.ui.activity.SectionPracticeActivity;
import com.mundane.examassistant.widget.view.SimpleDotIndicater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : mundane
 * @time : 2017/4/11 19:24
 * @description :
 * @file : ContentAdapter.java
 */

public class ContentAdapter extends RecyclerView.Adapter<TypeAbstractViewHolder> {

	private List<ContentItem> mList;

	public interface OnItemClickListener {
		void onItemClicked(Class clazz);
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	public ContentAdapter() {
		mList = new ArrayList<>();
		mList.add(new ContentItem(Constant.TYPE_HEADER, null, 0, null));
		mList.add(new ContentItem(Constant.TYPE_SECTION_PRACTICE, "章节练习", R.drawable.home_section_test, SectionPracticeActivity.class));
		mList.add(new ContentItem(Constant.TYPE_PRACTICE_HISTORY, "练习记录", R.drawable.home_history, null));
		mList.add(new ContentItem(Constant.TYPE_EXAM, "模拟考试", R.drawable.home_exam, null));
		mList.add(new ContentItem(Constant.TYPE_MY_FAVORITATE, "我的收藏", R.drawable.home_collection, null));
		mList.add(new ContentItem(Constant.TYPE_MY_MISTAKE, "我的错题", R.drawable.home_wrong, null));
	}

	@Override
	public void onViewAttachedToWindow(TypeAbstractViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		int position = holder.getLayoutPosition();
		if (mList.get(position).type == Constant.TYPE_HEADER) {
			ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
			if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
				StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
				p.setFullSpan(true);
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		return mList.get(position).type;
	}

	@Override
	public TypeAbstractViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		switch (viewType) {
			case Constant.TYPE_HEADER:
				return new HeaderViewHolder(inflater.inflate(R.layout.item_header, parent, false));
			case Constant.TYPE_SECTION_PRACTICE:
				return new ContentViewHolder(inflater.inflate(R.layout.item_section_practice, parent, false));
			case Constant.TYPE_PRACTICE_HISTORY:
				return new ContentViewHolder(inflater.inflate(R.layout.item_practice_history, parent, false));
			case Constant.TYPE_EXAM:
				return new ContentViewHolder(inflater.inflate(R.layout.item_exam, parent, false));
			case Constant.TYPE_MY_FAVORITATE:
				return new ContentViewHolder(inflater.inflate(R.layout.item_my_favoritate, parent, false));
			case Constant.TYPE_MY_MISTAKE:
				return new ContentViewHolder(inflater.inflate(R.layout.item_my_mistake, parent, false));
			default:
				break;
		}
		return null;
	}

	@Override
	public void onBindViewHolder(TypeAbstractViewHolder holder, final int position) {
		ContentItem item = mList.get(position);
		final Class clazz = item.clazz;
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClicked(clazz);
				}
			}
		});
		holder.bindHolder(item, position);
	}

	@Override
	public int getItemCount() {
		return mList == null || mList.isEmpty() ? 0 : mList.size();
	}

	static class ContentViewHolder extends TypeAbstractViewHolder<ContentItem> {

		@BindView(R.id.iv)
		ImageView mIv;
		@BindView(R.id.tv)
		TextView mTv;

		public ContentViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		@Override
		public void bindHolder(ContentItem contentItem, int position) {
			mIv.setImageResource(contentItem.resId);
			mTv.setText(contentItem.name);
		}

	}

	static class HeaderViewHolder extends TypeAbstractViewHolder<ContentItem> {
		@BindView(R.id.view_pager)
		ViewPager mViewPager;
		@BindView(R.id.simple_indicater)
		SimpleDotIndicater mSimpleIndicater;
		private BannerAdapter mBannerAdapter;
		private Runnable mRunnable;
		private Handler mHandler;

		public HeaderViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
				@Override
				public void onViewAttachedToWindow(View v) {

				}

				@Override
				public void onViewDetachedFromWindow(View v) {
					if (mHandler != null) {
						mHandler.removeCallbacksAndMessages(null);
						mHandler = null;
					}
				}
			});
		}

		@Override
		public void bindHolder(ContentItem contentItem, int position) {
			mBannerAdapter = new BannerAdapter(mViewPager);
			mViewPager.setAdapter(mBannerAdapter);
			mSimpleIndicater.setDotsCount(mBannerAdapter.getDrawableCount());
			mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

				}

				@Override
				public void onPageSelected(int position) {
//					Log.d(TAG, "position = " + position);
//					Log.d(TAG, "mViewPager.getCurrentItem() = " + mViewPager.getCurrentItem());
					if (position >= mBannerAdapter.getDrawableCount()) {
						//	mBannerAdapter.getDrawableCount() = 2
						position = position % mBannerAdapter.getDrawableCount();
					}
					mSimpleIndicater.UpdateDotsState(position);
				}

				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});
			mHandler = new Handler();
			mRunnable = new Runnable() {
				@Override
				public void run() {
					//	todo 这个bug以后再处理
					int position = mViewPager.getCurrentItem() + 1;
					mViewPager.setCurrentItem(position % 2, false);
					mHandler.postDelayed(mRunnable, 5000);
				}
			};
			mHandler.post(mRunnable);
			mViewPager.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							mHandler.removeCallbacksAndMessages(null);
							break;
						case MotionEvent.ACTION_UP:
						case MotionEvent.ACTION_CANCEL:
							mHandler.postDelayed(mRunnable, 3000);
							break;
						default:
							break;
					}
					return false;
				}
			});
		}
	}
}
