package systemsltd.basicstructure.viewpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import systemsltd.basicstructure.R;
import systemsltd.basicstructure.recyclerview.RecyclerViewSample;
import systemsltd.basicstructure.webservices.WebService_Fragment;


/**
 * Created by razi on 4/11/2016.
 */
public class ViewPagerSample extends Fragment {

    SectionsPagerAdapter mSectionsPagerAdapter;

    String[] iconList = {"Web Services","RecyclerView","Map"};

    TabLayout mSlidingTabLayout;
    ViewPager mViewPager;
    ImageView dummySearchIcon, btn_search;
    View mView;
    RelativeLayout ll_search;
    EditText et_search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = getActivity().getLayoutInflater().inflate(R.layout.viewpager, null);

        mSlidingTabLayout = (TabLayout) mView.findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                mSectionsPagerAdapter.getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), iconList);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);

        mViewPager.setOffscreenPageLimit(iconList.length+1);

        mSlidingTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < mSlidingTabLayout.getTabCount(); i++) {
            //  mSlidingTabLayout.getTabAt(i).setIcon(iconList[i]);

            // mSlidingTabLayout.getTabAt(i).setCustomView(R.layout.);

        }

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        dummySearchIcon.setVisibility(View.VISIBLE);
//        btn_search.setVisibility(View.GONE);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm, String[] iconList) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {

            Fragment frag = new WebService_Fragment();

             if(position == 0) {

                 frag = new WebService_Fragment();
             }
            else if(position == 1) {

                 frag = new RecyclerViewSample();
             }
             else if(position == 2) {

                 frag = new WebService_Fragment();
             }

            return  frag;
        }

        @Override
        public int getCount() {
            return iconList.length;
        }

        @Override
        public int getItemPosition(Object object) {

            mSectionsPagerAdapter.notifyDataSetChanged();
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return iconList[position].toString();
        }
    }


}
