package com.zsp.myindexbar.adapter;

import android.content.Context;

import com.zsp.myindexbar.R;
import com.zsp.myindexbar.model.MeiTuanBean;
import com.zsp.myindexbar.utils.CommonAdapter;
import com.zsp.myindexbar.utils.ViewHolder;

import java.util.List;


/**
 * Created by zsp .
 * Date: 16/08/28
 */

public class MeituanAdapter extends CommonAdapter<MeiTuanBean> {
    public MeituanAdapter(Context context, int layoutId, List<MeiTuanBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());
    }
}