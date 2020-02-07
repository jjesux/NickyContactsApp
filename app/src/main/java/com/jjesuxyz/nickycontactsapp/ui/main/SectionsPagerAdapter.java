package com.jjesuxyz.nickycontactsapp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jjesuxyz.nickycontactsapp.R;




/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_contact_lit, R.string.tab_add, R.string.tab_dial, R.string.tab_history};
    private final Context mContext;

    /**
     * Class constructor used to initialize global variables.
     *
     * @param context Context
     * @param fm FragmentManager
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }



    /**
     * This function is called to instantiate the fragment for the given tab, defined by its
     * position.
     *
     * @param position int
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:          // Fragment to build the Contact list.
                return FragCtcList.newInstance("Fragment Contact List");
            case 1:          // Fragment to add new contacts.
                return FragAdd.newInstance("Fragment Add Contact");
            case 2:          // Fragment to make a call.
                return FragDial.newInstance("Fragment Dial Contact");
            case 3:          // Fragement to build the list of made or received phone calls.
                return FragHistory.newInstance("Fragment Phone Call Log");
        }

        return null;
    }



    /**
     * This function return the name of the tabs according to its postion.
     *
     * It returns the name of the tab at position received as a parameter.
     *
     * @param position int
     * @return CharSequence
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }



    /**
     * This function returns the number of tabs.
     *
     * @return int
     */
    @Override
    public int getCount() {
        return 4;
    }


}   //  End of SectionPagerAdapter class


/********************************END OF FILE SectionPagerAdapter.java******************************/
