package com.example.coresample.activities.widget.listview.sectionheader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.event.OnSingleClickListener;
import com.example.core.manage.Binder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigris on 2017-08-23.
 * 작성자 : 황의택
 * 내용 : 리스트뷰의 사용자가 나눈 섹션(Section)마다 헤더를 명시 할 수있는 리사이클러뷰 아답터
 * 버전 : v1.0.0
 * @param <T> 자식을 포함한 SectionHeaderObj를 상속받은 객체
 */
public abstract class SectionHeaderAdapter<T extends SectionHeaderObj>
        extends RecyclerView.Adapter<SectionHeaderAdapter.ViewHolder>
        implements StickyHeaderAdapter<SectionHeaderAdapter.HeaderViewHolder> {
    protected final int TYPE_HEADER = 1000;
    protected final int TYPE_ITEM = 1001;

    protected Context context;
    protected List<T> allItems = new ArrayList<>();
    protected List<Object> items = new ArrayList<>();
    protected List<Integer> indexList = new ArrayList<>();

    public SectionHeaderAdapter(Context context) {
        this.context = context;
    }

    public void setItems(List<T> items) {
        setItems(items, true);
    }
    public void setItems(List<T> items, boolean isReformation) {
        allItems = items;
        List<Object> visibleItems = new ArrayList<>();
        indexList.clear();

        int index = 0;
        for (int i = 0; i < items.size(); i++) {
            if (isReformation) reformation(index, items.get(i));
            List<T> child = items.get(i).getChild();
            int j = 0;
            for (; j < child.size(); j++) {
                indexList.add(i);
                visibleItems.add(child.get(j));
            }
            index += j;
        }

        this.items = visibleItems;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public int getItemCount() {
        return this.items == null ? 0 : this.items.size();
    }
    @Override
    public int getHeaderId(int position) {
        return indexList.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (allItems.contains(items.get(position))) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(resourceID, viewGroup, false);
    }
    public void reformation(int index, T item) {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
            Binder.bind(view, this);
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onItemClick(v);
                }
            });
        }
        protected void onItemClick(View v) {
        }
    }
    public class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
            view.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    onHeaderClick(v);
                }
            });
        }
        protected void onHeaderClick(View v) {
        }
    }
}
