package com.warden.lib.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {
    public List<T> mDatas;
    protected final int mItemLayoutId;

    public BaseListAdapter(List<T> datas, int itemLayoutId) {
        mDatas = datas;
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item, int i);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.get(convertView, parent, mItemLayoutId, position);
    }

    public void remove(int i) {
        mDatas.remove(i);
        notifyDataSetChanged();
    }

    public void remove(T data) {
        mDatas.remove(data);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        mDatas.add(data);
        notifyDataSetChanged();
    }

    public void setData(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        private final SparseArray<View> mViews;
        private final View mConvertView;
        private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
            this.mViews = new SparseArray<View>();
            mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            //setTag
            mConvertView.setTag(this);
        }

        /**
         * 拿到一个ViewHolder对象
         */
        public static ViewHolder get(View convertView, ViewGroup parent, int layoutId, int position) {
            if (convertView == null) {
                return new ViewHolder(parent.getContext(), parent, layoutId, position);
            }
            return (ViewHolder) convertView.getTag();
        }

        /**
         * 通过控件的Id获取对于的控件，如果没有则加入views
         */
        public <T extends View> T getView(int viewId) {

            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public ViewHolder setText(int viewId, CharSequence value) {
            TextView view = getView(viewId);
            view.setText(value);
            return this;
        }

        public View getConvertView() {
            return mConvertView;
        }
    }
}