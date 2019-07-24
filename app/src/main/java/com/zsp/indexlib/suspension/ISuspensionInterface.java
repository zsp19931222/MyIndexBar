package com.zsp.indexlib.suspension;

/**
 * 介绍：分类悬停的接口
 * 作者：zsp
 * 邮箱：zsp872126510@gmail.com
 *
 * 时间： 2016/11/7.
 */

public interface ISuspensionInterface {
    //是否需要显示悬停title
    boolean isShowSuspension();

    //悬停的title
    String getSuspensionTag();

}