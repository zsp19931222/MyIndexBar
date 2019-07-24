package com.zsp.myindexbar.model;

/**
 * 介绍：美团最顶部Header
 * 作者：zsp
 * 邮箱：zsp872126510@gmail.com
 * 时间： 16/11/28.
 */

public class MeituanTopHeaderBean {
    private String txt;

    public MeituanTopHeaderBean(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public MeituanTopHeaderBean setTxt(String txt) {
        this.txt = txt;
        return this;
    }

}
