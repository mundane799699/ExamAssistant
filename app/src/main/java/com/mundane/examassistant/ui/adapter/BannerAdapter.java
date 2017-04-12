package com.mundane.examassistant.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mundane.examassistant.R;


/**
 * Created by mundane on 2017/2/28 19:23
 */

public class BannerAdapter extends PagerAdapter {



	private int[] mDrawableRes;

	private ViewPager mBanner;

	public BannerAdapter(ViewPager banner) {
		int[] drawables = {R.drawable.home_scroll1, R.drawable.home_scroll2};
		mDrawableRes = drawables;
		mBanner = banner;
	}

	public int getDrawableCount() {
		return mDrawableRes.length;
	}

	@Override
	public int getCount() {
		return mDrawableRes.length + 2;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		/**
		 *    这个position是当前页面的下一个页面的缓存，若是想做滑动，position=当前item-1反之则加一
		 *    举个例子这个viewpager总共包含了imagesize+2=7个页面
		 *    positio=5时 position % ImageSize=0，切换到了第一个position
		 */
		position = position % mDrawableRes.length;
		View view = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_banner, container, false);
		ImageView iv = (ImageView) view.findViewById(R.id.iv);
		iv.setImageResource(mDrawableRes[position]);
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		int position = mBanner.getCurrentItem();
		/**
		 *  第五这里获得当前的positon然后对其setCurrentItem进行变换
		 *  这里设置当position=0时把position设置为图片列表的最大值
		 *  是为了position=0时左滑显示最后一张，我举个例子这里ImageSize是5
		 *  当position==0时设置为5，左滑就是position=4，也就是第五张图片，
		 *
		 *  if (position == (ImageSize+2) - 1)
		 *  这个判断 (ImageSize+2)这个是给viewpager设置的页面数，这里是7
		 *  当position==7-1=6时，这时viewpager就滑到头了，所以把currentItem设置为1
		 *  这里设置为1还是为了能够左滑，这时左滑position=0又执行了第一个判断又设置为5，
		 *  这样就实现了无限轮播的效果
		 *  setCurrentItem(position,false);
		 *  这里第二个参数false是消除viewpager设置item时的滑动动画，不理解的去掉它运行下就知道啥意思了
		 *
		 */
		if (position == 0) {
			position = mDrawableRes.length;
			mBanner.setCurrentItem(position, false);
		} else if (position == (mDrawableRes.length + 2) - 1) {
			position = 1;
			mBanner.setCurrentItem(position, false);
		}
	}
}
