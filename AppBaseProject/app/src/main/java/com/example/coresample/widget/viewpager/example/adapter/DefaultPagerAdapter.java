package com.example.coresample.widget.viewpager.example.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coresample.R;

import java.util.List;

/**
 * Created by tigris on 2017-10-18.
 */
public class DefaultPagerAdapter<T extends Parcelable> extends PagerAdapter {
    protected Context context;
    protected List<T> items;

    public DefaultPagerAdapter(Context context) {
        this.context = context;
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false);
    }
    @Override
    public int getCount() {
        return (items == null ? 0 : items.size());
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = null;
        if(position==0){
            v = inflate(R.layout.item_pager_example_page_one, null);
//            v.findViewById(R.id.iv_one);
//            v.findViewById(R.id.btn_click).setOnClickListener(mPagerListener);
        }
        else if(position==1){
            v = inflate(R.layout.item_pager_example_page_two, null);
//            v.findViewById(R.id.iv_two);
//            v.findViewById(R.id.btn_click_2).setOnClickListener(mPagerListener);
        }else{
            v = inflate(R.layout.item_pager_example_page_three, null);
//            v.findViewById(R.id.iv_three);
//            v.findViewById(R.id.btn_click_3).setOnClickListener(mPagerListener);
        }

        container.addView(v, 0);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
