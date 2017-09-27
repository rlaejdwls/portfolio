package com.example.coresample.widget.imageview.multitouchzoom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.core.manage.Binder;
import com.example.core.manage.annotation.Bind;
import com.example.coresample.fragment.ImageViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigris on 2017-09-21.
 */
public class ImageViewActivity extends AppCompatActivity {
    private ViewPagerAdapter adapter;

    @Bind private ViewPager pager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binder.bind(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("URL", "http://treegames.co.kr/test/uploads/temp.jpg");
        adapter.addFrag(ImageViewFragment.create(ImageViewFragment.class, bundle), "PAGE_01");
        bundle = new Bundle();
        bundle.putString("URL", "http://treegames.co.kr/test/uploads/temp2.jpg");
        adapter.addFrag(ImageViewFragment.create(ImageViewFragment.class, bundle), "PAGE_02");
        bundle = new Bundle();
        bundle.putString("URL", "http://treegames.co.kr/test/uploads/temp3.jpg");
        adapter.addFrag(ImageViewFragment.create(ImageViewFragment.class, bundle), "PAGE_03");
        pager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
