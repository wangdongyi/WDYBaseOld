package com.base.library.preview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.base.library.fragment.WDYBaseFragment;

import java.util.List;

/**
 * 作者：王东一
 * 创建时间：2018/1/23.
 */

public class TableViewAdapter extends FragmentPagerAdapter {
    private List<WDYBaseFragment> pagerList;
    private List<String> titleList;

    public TableViewAdapter(FragmentManager fm, List<WDYBaseFragment> pagerList, List<String> titleList) {
        super(fm);
        this.pagerList = pagerList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return pagerList != null ? pagerList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
