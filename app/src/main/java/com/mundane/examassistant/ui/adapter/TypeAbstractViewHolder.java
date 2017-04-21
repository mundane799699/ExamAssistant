package com.mundane.examassistant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 24540 on 2017/3/13.
 */

public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder {
    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(T t, int position);

}
