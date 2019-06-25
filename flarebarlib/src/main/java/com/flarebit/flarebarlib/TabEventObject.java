package com.flarebit.flarebarlib;
/*
 * Created by Vaibhav Patel on 20-06-2019.
 */


import android.widget.LinearLayout;

import java.util.EventListener;

public class TabEventObject{
    public interface TabChangedListener {
        public void onTabChanged(LinearLayout selectedTab, int selectedIndex,int oldIndex);
    }
    public TabChangedListener tabChangedListener;
    public TabEventObject(TabChangedListener tabChangedListener){
        this.tabChangedListener = tabChangedListener;
    }
}
