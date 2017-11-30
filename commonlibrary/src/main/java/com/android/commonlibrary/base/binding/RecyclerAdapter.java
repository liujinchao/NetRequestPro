package com.android.commonlibrary.base.binding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：RecyclerAdapter
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/10/11 21:35
 * 描述：RecyclerView 基类列表适配器
 */
public abstract class RecyclerAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<T> mList;

    public RecyclerAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
    }

    public RecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void refreshData(List<T> list){
        if (mList == null){
            mList = new ArrayList<>();
        }
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(viewType), parent, false);
        return new RecyclerHolder(bing.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        final T t = mList.get(position);
        onBindItem(binding, t, position);
    }

    protected abstract @LayoutRes int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, T t, int position);

    static class RecyclerHolder extends RecyclerView.ViewHolder {

        public RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

}
