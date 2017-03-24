package com.jafir.testdagger.utils;

/**
 * Created by jafir on 2017/3/24.
 */

public interface ReyclerViewTouchAdapter {


    /**
     * 用于上下拖拽
     * @param fromposition
     * @param toposition
     */
    void onItemDragDone(int fromposition,int toposition);

    /**
     * 用于左右滑动
     * @param position
     */
    void onItemSwapDone(int position);
}
