package com.twm.pt.softball.softballlist.Activity;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Adapter.MyFragmentPagerAdapter;
import com.twm.pt.softball.softballlist.Fragment.OrderFragment;
import com.twm.pt.softball.softballlist.Fragment.PersonFragment;
import com.twm.pt.softball.softballlist.Fragment.PositionsFragment;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    //UI
    private ImageView ivBottomLine;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private TextView tvTabPerson, tvTabOrder, tvTabPositions;

    //Parmeter
    private int bottomLineWidth;
    private int position_one;
    private int position_two;
    private int offset = 0;
    private int currIndex = 0;

    //System Resource
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        resources = getResources();

        InitWidth();
        InitTextView();
        InitViewPager();

//        L.d("ST_Rom_AppDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_Rom_AppDir));
//        L.d("ST_Rom_DataDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_Rom_DataDir));
//        L.d("ST_SDCard_RootDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_SDCard_RootDir));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void InitWidth() {
        ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        L.d("cursor imageview width=" + bottomLineWidth);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        L.d("screenW=" + screenW);
        offset = (int) ((screenW / 3.0 - bottomLineWidth) / 2);
        L.d("offset=" + offset);

        position_one = (int) (screenW / 3.0);
        position_two = position_one * 2;
    }

    private void InitTextView() {
        tvTabPerson = (TextView) findViewById(R.id.tv_tab_person);
        tvTabOrder = (TextView) findViewById(R.id.tv_tab_order);
        tvTabPositions = (TextView) findViewById(R.id.tv_tab_positions);

        tvTabPerson.setOnClickListener(new MyOnClickListener(0));
        tvTabOrder.setOnClickListener(new MyOnClickListener(1));
        tvTabPositions.setOnClickListener(new MyOnClickListener(2));
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }


    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentsList = new ArrayList<>();

        Fragment personFragment = PersonFragment.newInstance();
        Fragment orderFragment = OrderFragment.newInstance();
        Fragment positionsFragment= PositionsFragment.newInstance();

        fragmentsList.add(personFragment);
        fragmentsList.add(orderFragment);
        fragmentsList.add(positionsFragment);

        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, 0, 0, 0);
                        tvTabOrder.setTextColor(resources.getColor(R.color.light_white));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, 0, 0, 0);
                        tvTabPositions.setTextColor(resources.getColor(R.color.light_white));
                    }
                    tvTabPerson.setTextColor(resources.getColor(R.color.white));
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_one, 0, 0);
                        tvTabPerson.setTextColor(resources.getColor(R.color.light_white));
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(position_two, position_one, 0, 0);
                        tvTabPositions.setTextColor(resources.getColor(R.color.light_white));
                    }
                    tvTabOrder.setTextColor(resources.getColor(R.color.white));
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(0, position_two, 0, 0);
                        tvTabPerson.setTextColor(resources.getColor(R.color.light_white));
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(position_one, position_two, 0, 0);
                        tvTabOrder.setTextColor(resources.getColor(R.color.light_white));
                    }
                    tvTabPositions.setTextColor(resources.getColor(R.color.white));
                    break;
            }
            currIndex = arg0;
            if(animation!=null) {
                animation.setFillAfter(true);
                animation.setDuration(300);
                ivBottomLine.startAnimation(animation);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
