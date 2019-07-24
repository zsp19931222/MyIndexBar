package com.zsp.indexlib.stopapplication;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zsp.myindexbar.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTextView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void bind(String label) {
        mTextView.setText(label);
    }
}
