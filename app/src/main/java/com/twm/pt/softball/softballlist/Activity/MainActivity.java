package com.twm.pt.softball.softballlist.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Adapter.MyFragmentPagerAdapter;
import com.twm.pt.softball.softballlist.Fragment.OrderFragment;
import com.twm.pt.softball.softballlist.Fragment.PersonFragment;
import com.twm.pt.softball.softballlist.Fragment.PositionsFragment2;
import com.twm.pt.softball.softballlist.Fragment.TeamNameDialogFragment;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.utility.L;
import com.twm.pt.softball.softballlist.utility.PreferenceUtils;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    //UI
    private ImageView ivBottomLine;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private TextView tvTabPerson, tvTabOrder, tvTabPositions;
    private TextView etTeam_name;

    //Parmeter
    private int bottomLineWidth;
    private int position_one;
    private int position_two;
    private int offset = 0;
    private int currIndex = 0;

    //System Resource
    private Context mAppContext;
    private Resources resources;

    //Key
    private static String team_name_key = "team_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = getApplicationContext();

        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        resources = getResources();
        PlayerDataManager.getInstance(getApplicationContext()).LoadPlayers();

        InitWidth();
        InitTextView();
        InitViewPager();

//        L.d("ST_Rom_AppDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_Rom_AppDir));
//        L.d("ST_Rom_DataDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_Rom_DataDir));
//        L.d("ST_SDCard_RootDir=" + StorageDirectory.getStorageDirectory(getBaseContext(), StorageDirectory.StorageType.ST_SDCard_RootDir));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTeamNameTextView();
    }

    @Override
    protected void onDestroy() {
        PlayerDataManager.getInstance(getApplicationContext()).SavePlayer();
        super.onDestroy();
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
        ivBottomLine = getView(R.id.iv_bottom_line);
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
        tvTabPerson = getView(R.id.tv_tab_person);
        tvTabOrder = getView(R.id.tv_tab_order);
        tvTabPositions = getView(R.id.tv_tab_positions);
        etTeam_name = getView(R.id.etTeam_name);

        tvTabPerson.setOnClickListener(new MyOnClickListener(0));
        tvTabOrder.setOnClickListener(new MyOnClickListener(1));
        tvTabPositions.setOnClickListener(new MyOnClickListener(2));
        etTeam_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showTeamNameDialogFragment();
            }
        });
    }

    private void setTeamNameTextView() {
        String teamName = PreferenceUtils.getValue(mAppContext, team_name_key, resources.getString(R.string.team_name));
        if(teamName==null || teamName.isEmpty()) {
            teamName = resources.getString(R.string.team_name);
        }
        etTeam_name.setText(teamName);
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
        mPager = getView(R.id.vPager);
        fragmentsList = new ArrayList<>();

        Fragment personFragment = PersonFragment.newInstance();
        Fragment orderFragment = OrderFragment.newInstance();
        Fragment positionsFragment= PositionsFragment2.newInstance();

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

    private void showTeamNameDialogFragment() {
        TeamNameDialogFragment mTeamNameDialogFragment = new TeamNameDialogFragment();
        mTeamNameDialogFragment.setTeamName(etTeam_name.getText().toString());
        mTeamNameDialogFragment.setOnDialogResultListener(new TeamNameDialogFragment.OnDialogResultListener() {
            @Override
            public void onChangeString(String teamName) {
                PreferenceUtils.setValue(mAppContext, team_name_key, teamName);
                setTeamNameTextView();
            }
        });
        mTeamNameDialogFragment.show(getSupportFragmentManager(), "TeamNameDialogFragment");
    }

    public final <E extends View> E
    getView(int id) {
        return (E) findViewById(id);
    }
}
