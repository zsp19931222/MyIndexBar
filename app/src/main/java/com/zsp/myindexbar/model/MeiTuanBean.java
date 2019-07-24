package com.zsp.myindexbar.model;

import com.zsp.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * 介绍：美团里的城市bean
 * 作者：zsp
 * 邮箱：zsp872126510@gmail.com
 *
 * 时间： 2016/11/28.
 */

public class MeiTuanBean extends BaseIndexPinyinBean {
    private String city;//城市名字

    public MeiTuanBean() {
    }

    public MeiTuanBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public MeiTuanBean setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getTarget() {
        return city;
    }
}
