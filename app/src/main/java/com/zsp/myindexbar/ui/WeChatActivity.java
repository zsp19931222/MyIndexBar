package com.zsp.myindexbar.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zsp.indexlib.IndexBar.widget.IndexBar;
import com.zsp.indexlib.suspension.SuspensionDecoration;
import com.zsp.myindexbar.R;
import com.zsp.myindexbar.adapter.CityAdapter;
import com.zsp.myindexbar.decoration.DividerItemDecoration;
import com.zsp.myindexbar.decoration.HeaderDecoration;
import com.zsp.myindexbar.model.CityBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 介绍：高仿微信通讯录界面
 * 头部不是HeaderView 因为头部也需要快速导航，"↑"
 * 作者：zsp
 * 邮箱：zsp872126510@gmail.com
 * 时间： 2016/11/7.
 */

public class WeChatActivity extends AppCompatActivity {
    private static final String TAG = "zsp";
    private static final String INDEX_STRING_TOP = "↑";
    private RecyclerView mRv;
    private CityAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<CityBean> mDatas = new ArrayList<>();
    private TextView ll_search;
    private SuspensionDecoration mDecoration;
    /**
     * 右侧边栏导航区域
     */
    private IndexBar mIndexBar;

    /**
     * 显示指示器DialogText
     */
    private TextView mTvSideBarHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRv = findViewById(R.id.rv);
        ll_search = findViewById(R.id.tv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mAdapter = new CityAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new HeaderDecoration(WeChatActivity.this, DividerItemDecoration.VERTICAL_LIST,R.layout.header));
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        //如果add两个，那么按照先后顺序，依次渲染。
        //使用indexBar
        mIndexBar = findViewById(R.id.indexBar);//IndexBar
        //indexbar初始化
        mIndexBar
//                .setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        //模拟线上加载数据
        initDatas(getResources().getStringArray(R.array.provinces));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    Log.d("firstItemPosition",mDatas.get(firstItemPosition).getBaseIndexTag());
                    mIndexBar.setBaseIndexTag(mDatas.get(firstItemPosition).getBaseIndexTag());
                }
                if (distance < -50 && !visible) {
                    //显示fab
                    showFABAnimation(ll_search);
                    distance = 0;
                    visible = true;

                } else if (distance > 50 && visible) {
                    //隐藏
                    hideFABAnimation(ll_search);
                    distance = 0;
                    visible = false;
                }
                if ((dy > 0 && visible) || (dy < 0 && !visible))//向下滑并且可见  或者  向上滑并且不可见
                    distance += dy;
            }
        });
    }

    private int distance;

    private boolean visible = true;

    /**
     * by moos on 2017.8.21
     * func:显示fab动画
     */
    RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
    public void showFABAnimation(View view) {
        layoutParams.topMargin=SizeUtils.dp2px(30);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", -view.getHeight(), 0);
        PropertyValuesHolder rvY = PropertyValuesHolder.ofFloat("translationY", 0, -view.getHeight());
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY).setDuration(400).start();
        ObjectAnimator.ofPropertyValuesHolder(mRv, pvhY).setDuration(400).start();
        mRv.setLayoutParams(layoutParams);

    }

    /**
     * by moos on 2017.8.21
     * func:隐藏fab的动画
     */

    public void hideFABAnimation(final View view) {
        layoutParams.topMargin=SizeUtils.dp2px(0);

        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 0, -view.getHeight());
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY).setDuration(400).start();
//        ObjectAnimator.ofPropertyValuesHolder(mRv, pvhY).setDuration(400).start();
        mRv.setLayoutParams(layoutParams);
    }

    /**
     * 组织数据源
     *
     * @param data
     * @return
     */
    private void initDatas(final String[] data) {
        //延迟两秒 模拟加载数据中....
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas = new ArrayList<>();
                //微信的头部 也是可以右侧IndexBar导航索引的，
                // 但是它不需要被ItemDecoration设一个标题titile
//                mDatas.add((CityBean) new CityBean("新的朋友").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("群聊").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("标签").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
//                mDatas.add((CityBean) new CityBean("公众号").setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                for (int i = 0; i < data.length; i++) {
                    CityBean cityBean = new CityBean();
                    cityBean.setCity(data[i]);//设置城市名称
                    mDatas.add(i,cityBean);
                }
                mAdapter.setDatas(mDatas);
                mAdapter.notifyDataSetChanged();

                mIndexBar.setmSourceDatas(mDatas)//设置数据
                        .invalidate();
                mIndexBar.requestLayout();
                mDecoration.setmDatas(mDatas);
            }
        }, 500);
    }

    /**
     * 更新数据源
     *
     * @param view
     */
    public void updateDatas() {
        for (int i = 0; i < 5; i++) {
            mDatas.add(new CityBean("东京"));
            mDatas.add(new CityBean("大阪"));
        }

        mIndexBar.setmSourceDatas(mDatas)
                .invalidate();
        mAdapter.notifyDataSetChanged();
    }

    public interface onScrollChangeListener{
        void offset(float offset);
    }
    private onScrollChangeListener onScrollChangeListener;

    public void setOnScrollChangeListener(WeChatActivity.onScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }
}
