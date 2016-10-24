package com.gentrio.zhrb.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Gentrio on 2016/10/21.
 */
public class TopPagerAdapter extends PagerAdapter {

    private List<View> topViews;

    public TopPagerAdapter(List<View> topViews) {
        this.topViews = topViews;
    }

    @Override
    public int getCount() {
        return topViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = topViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(topViews.get(position));
    }
}
