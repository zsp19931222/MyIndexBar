package com.zsp.myindexbar.decoration;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HeaderDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = HeaderDecoration.class.getSimpleName();
    private final int mOrientation;
    private View header;
    private Context mContext;
    private int mLayout;

    public HeaderDecoration(Context context, int orientation, int layout){
        mContext = context;
        mOrientation = orientation;
        mLayout = layout;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

        if (parent.getChildCount() > 0) {
            final View child = parent.getChildAt(0);
            // 如果第一个item已经被回收,没有显示在recycler view上,则不需要draw header
            if (parent.getChildAdapterPosition(child) != 0) {
                return;
            }
            // 使header始终画在第一个item上方
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                // 垂直平移
                canvas.translate(0, child.getTop() - header.getHeight());
            } else {
                // 水平平移
                canvas.translate(child.getLeft() - header.getWidth(), 0);
            }
        }else{
            // 如果recycler view中没有item, getItemOffsets不会执行
            // 需要在此初始化header view
            if(header == null) {
                initHeader(parent);
            }
        }
        header.draw(canvas);
    }

    // 初始化并计算header view的宽高以及位置
    public void initHeader(RecyclerView parent) {
        if (header == null) {
            //TODO - recycle views
            header = LayoutInflater.from(mContext).inflate(mLayout, parent, false);
            if (header.getLayoutParams() == null) {
                header.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            int widthSpec;
            int heightSpec;

            if (mOrientation == LinearLayoutManager.VERTICAL) {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
            } else {
                widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
                heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
            }

            int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                    parent.getPaddingLeft() + parent.getPaddingRight(), header.getLayoutParams().width);
            int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                    parent.getPaddingTop() + parent.getPaddingBottom(), header.getLayoutParams().height);

            header.measure(childWidth, childHeight);
            header.layout(0, 0, header.getMeasuredWidth(), header.getMeasuredHeight());
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        // 初始化header view
        if(header == null) {
            initHeader(parent);
        }
        // 对第一个item最偏移
        if(parent.getChildAdapterPosition(view) == 0) {
            // 垂直布局则把item自顶部向下偏移,偏移距离为header的高度
            // 横向布局则把item自左向右偏移,偏移距离为header的宽度
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(0, header.getMeasuredHeight(), 0, 0);
            }else {
                outRect.set(header.getMeasuredWidth(), 0, 0, 0);
            }
        }
    }
}