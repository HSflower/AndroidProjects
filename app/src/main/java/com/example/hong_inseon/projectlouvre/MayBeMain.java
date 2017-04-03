package com.example.hong_inseon.projectlouvre;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MayBeMain extends AppCompatActivity {

    private int[] tabIcons = {R.drawable.cart, R.drawable.heart, R.drawable.profile, R.drawable.search};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_may_be_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.rcvr_tl_tabs);
        tabLayout.addTab(tabLayout.newTab().setText("인기전시"));
        tabLayout.addTab(tabLayout.newTab().setText("좋아한전시"));
        tabLayout.addTab(tabLayout.newTab().setText("게시판"));
        tabLayout.addTab(tabLayout.newTab().setText("참여"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.rcvr_vp_pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        int _numOfTabs;

        public PagerAdapter(FragmentManager fm, int numOfTabs) {
            super(fm);
            this._numOfTabs = numOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    MyTabFragment1 tab1 = new MyTabFragment1(); // Fragment 는 알아서 만들자
                    return tab1;
                case 1:
                    MyTabFragment2 tab2 = new MyTabFragment2();
                    return tab2;
                case 2:
                    MyTabFragment3 tab3 = new MyTabFragment3();
                    return tab3;
                case 3:
                    MyTabFragment4 tab4 = new MyTabFragment4();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return _numOfTabs;
        }
    }
}
