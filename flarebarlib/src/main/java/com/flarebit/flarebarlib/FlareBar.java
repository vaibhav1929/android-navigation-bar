package com.flarebit.flarebarlib;
/*
 * Created by Vaibhav Patel on 19-06-2019.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import java.util.ArrayList;

public class FlareBar extends LinearLayout {
    private boolean barVisible = true;
    Context context;
    private ArrayList<Flaretab> tabList;
    private int selectedIndex = 0;
    TabEventObject.TabChangedListener tabChangedListener;
    ArrayList<LinearLayout> tabLayouts = new ArrayList<>();
    ArrayList<ImageButton> tabImages = new ArrayList<>();
    ArrayList<TextView> tabTexts = new ArrayList<>();
    Drawable tabDrawable;

    public FlareBar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FlareBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FlareBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlareBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        FlareBar.this.setGravity(Gravity.BOTTOM);
    }

    public void setTabList(ArrayList<Flaretab> tabs) {
        this.tabList = tabs;
        init();
    }
    public ArrayList<Flaretab> getTabList() {
        return this.tabList;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void attachTabs(Context ctx) {
        if (tabList != null && tabList.size() > 0) {

            for (int i = 0; i < tabList.size(); i++) {
                final LinearLayout tab1Layout = new LinearLayout(ctx);
                tab1Layout.setOrientation(HORIZONTAL);
                LayoutParams tabParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tabDrawable = ctx.getResources().getDrawable(R.drawable.tabs_corner_bg);
                tabParams.weight = 1;
                if (i == selectedIndex) {
                    tabDrawable.setColorFilter((new PorterDuffColorFilter(Color.parseColor(tabList.get(i).getTabColorString()),
                            PorterDuff.Mode.SRC_ATOP)));
                    tab1Layout.setBackground(tabDrawable);
                }
                tab1Layout.setPadding(15, 0, 15, 0);
                tabParams.setMargins(25, 25, 10, 25);
                tab1Layout.setLayoutParams(tabParams);

                final ImageButton b1 = new ImageButton(ctx);
                b1.setImageDrawable(tabList.get(i).getTabImage());
                b1.setBackgroundColor(Color.TRANSPARENT);
                LayoutParams params = (LayoutParams) new LayoutParams(130, 130);
                //params.gravity = Gravity.CENTER;
                params.weight = 1;
                b1.setLayoutParams(params);

                int btnColor;
                if (!isColorDark(Color.parseColor(tabList.get(i).getTabColorString()))) {
                    btnColor = ColorUtils.blendARGB(Color.parseColor(tabList.get(i).getTabColorString()), Color.BLACK, 0.5f);
                } else {
                    btnColor = ColorUtils.blendARGB(Color.parseColor(tabList.get(i).getTabColorString()), Color.WHITE, 0.8f);
                }
                if (i == selectedIndex) {
                    b1.setColorFilter(new PorterDuffColorFilter(btnColor, PorterDuff.Mode.SRC_ATOP));
                }
                b1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                final TextView tabTxt1 = new TextView(ctx);
                tabTxt1.setText(tabList.get(i).getTabText());
                tabTxt1.setTextColor(btnColor);
                tabTxt1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                tabParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tabParams.setMargins(1, 0, 25, 0);
                tabParams.gravity = Gravity.CENTER;
                tabParams.weight = 1;
                if (tabList.size() > 4) {
                    tabParams.weight = 0;
                }
                tabTxt1.setLayoutParams(tabParams);
                if (i != selectedIndex) {
                    tabTxt1.setVisibility(GONE);
                }

                tab1Layout.addView(b1);
                tab1Layout.addView(tabTxt1);
                if (tabList.get(i).isBadgeGiven()) {
                    final TextView badge = new TextView(ctx);
                    badge.setText(tabList.get(i).getBadge());
                    badge.setPadding(2, 2, 2, 2);
                    badge.setTextColor(Color.WHITE);
                    badge.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    badge.setBackground(ctx.getResources().getDrawable(R.drawable.badge_round_bg));
                    tabParams = new LayoutParams(60, 60);
                    tabParams.setMargins(0, 0, 5, 0);
                    tabParams.gravity = Gravity.CENTER_VERTICAL;
                    badge.setLayoutParams(tabParams);
                    tab1Layout.addView(badge);
                }
                this.addView(tab1Layout);
                final int finalI = i;

                tabLayouts.add(tab1Layout);
                tabImages.add(b1);
                tabTexts.add(tabTxt1);

                b1.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleTabTouch(tab1Layout, finalI);
                    }
                });
                tab1Layout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleTabTouch(tab1Layout, finalI);
                    }
                });
            }
            // ViewAnimator.animate(tabLayouts.get(tabLayouts.size()-1)).translationX(5f).duration(300).start();
        }
    }

    private void handleTabTouch(LinearLayout tab1Layout, int finalI) {
        new TabEventObject(tabChangedListener).tabChangedListener.onTabChanged(tab1Layout, finalI, selectedIndex);

        tabTexts.get(selectedIndex).setVisibility(GONE);
        tabLayouts.get(selectedIndex).getBackground().clearColorFilter();
        tabImages.get(selectedIndex).clearColorFilter();
        tabLayouts.get(selectedIndex).setBackgroundColor(Color.TRANSPARENT);

        tabDrawable = getResources().getDrawable(R.drawable.tabs_corner_bg);
        tabDrawable.setColorFilter((new PorterDuffColorFilter(Color.parseColor(tabList.get(finalI).getTabColorString()),
                PorterDuff.Mode.SRC_ATOP)));
        tab1Layout.setBackground(tabDrawable);

        int btnColor;
        if (!isColorDark(Color.parseColor(tabList.get(finalI).getTabColorString()))) {
            btnColor = ColorUtils.blendARGB(Color.parseColor(tabList.get(finalI).getTabColorString()), Color.BLACK, 0.5f);
        } else {
            btnColor = ColorUtils.blendARGB(Color.parseColor(tabList.get(finalI).getTabColorString()), Color.WHITE, 0.8f);
        }
        tabImages.get(finalI).setColorFilter(new PorterDuffColorFilter(btnColor, PorterDuff.Mode.SRC_ATOP));
        tabTexts.get(finalI).setVisibility(VISIBLE);

        selectedIndex = finalI;
        ViewAnimator.animate(tabImages.get(finalI)).scale(0f, 1f).duration(250).start();
        ViewAnimator.animate(tabLayouts.get(finalI)).translationX(45f, 0f).duration(300).start();
    }

    public void removeBadge(int tabIndex) {
        tabLayouts.get(tabIndex).removeViewAt(2);
    }

    public void setTabsTextColor(int textColor){
        for(TextView txt : tabTexts){
            txt.setTextColor(textColor);
        }
    }
    public void selectTab(int index) {
        selectedIndex = index;
        this.removeAllViews();
        tabLayouts.clear();
        tabTexts.clear();
        tabImages.clear();
        attachTabs(context);
    }

    public void setBarBackgroundColor(int color) {
        this.setBackgroundColor(color);
    }

    public int getBarBackgroundColor() {
        ColorDrawable viewColor = (ColorDrawable) this.getBackground();
        int colorId = viewColor.getColor();
        return colorId;
    }

    public void setTabChangedListener(TabEventObject.TabChangedListener tabChangedListener) {
        this.tabChangedListener = tabChangedListener;
        new TabEventObject(tabChangedListener);
    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // light color
        } else {
            return true; // dark color
        }
    }

    public void hideBar() {
        ViewAnimator.animate(this).translationY(0, 300).duration(500).start().onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                FlareBar.this.setVisibility(GONE);
                barVisible = false;
            }
        });
    }

    public void showBar() {
        FlareBar.this.setVisibility(VISIBLE);
        ViewAnimator.animate(this).translationY(300,0).duration(500).start();
        barVisible = true;
    }

    public boolean isBarVisible(){return barVisible;}
}
