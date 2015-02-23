package com.anjar.smsspamblocker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT;
    final String PAGE_TITLE[];
    
    public MainFragmentPagerAdapter(FragmentActivity activity) {
        super(activity.getSupportFragmentManager());
        
        PAGE_TITLE = activity.getResources().getStringArray(R.array.string_array_title_view_pager); 
        PAGE_COUNT = PAGE_TITLE.length;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
    	switch(position) {
    	case 0 :
    		return new HomeFragment();
    	case 1 :
    		return new SenderFragment();
    	case 2 :
    		return new ContentFragment();
    	case 3 :
    		return new BlockedSmsFragment();
    	default:
    		return new HomeFragment();
    	}
    }

    public CharSequence getPageTitle(int position) {
        return PAGE_TITLE[position];
    }
}
