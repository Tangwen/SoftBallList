package com.twm.pt.softball.softballlist.Activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.squareup.picasso.Picasso;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.utility.StorageDirectory;


public class PersonActivity extends ActionBarActivity {

    private ActionBar supportActionBar;
    private Toolbar person_activity_toolbar;
    private DrawerLayout person_activity_MainDrawerLayout;
    private LinearLayout person_activity_picLinearLayout;
    private CardView person_activity_CardView;
    private ImageView person_activity_picImageView;
    private ImageView person_activity_plusIcon;
    private LinearLayout person_activity_nameLinearLayout;
    private TextView person_activity_nameTextView;
    private EditText person_activity_nameEditText;
    private LinearLayout person_activity_nickLinearLayout;
    private TextView person_activity_nickTextView;
    private EditText person_activity_nickEditText;
    private LinearLayout person_activity_numberLinearLayout;
    private TextView person_activity_numberTextView;
    private EditText person_activity_numberEditText;
    private LinearLayout person_activity_habitsLinearLayout;
    private TextView person_activity_habitsTextView;
    private EditText person_activity_habitsEditText;
    private LinearLayout person_activity_fielderLinearLayout;
    private TextView person_activity_fielderTextView;
    private EditText person_activity_fielderEditText;

    private boolean mEditMode = false;
    private com.twm.pt.softball.softballlist.component.Player mPlayer;
    private final String picPath = "file://" + StorageDirectory.getStorageDirectory(this, StorageDirectory.StorageType.ST_SDCard_RootDir) + PlayerDataManager.picPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayer = getPlayer(savedInstanceState);
        setContentView(R.layout.person_activity);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setToolbar();
        setPlayerData(mPlayer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.person_activity_menu, menu);
        return true;
    }


    private com.twm.pt.softball.softballlist.component.Player  getPlayer(Bundle savedInstanceState) {
        com.twm.pt.softball.softballlist.component.Player player = (com.twm.pt.softball.softballlist.component.Player) this.getIntent().getSerializableExtra("Player");
        //L.d(mPlayer.toString());
        return player;
    }

    private void initView() {
        person_activity_toolbar = getView(R.id.person_activity_toolbar);
        person_activity_MainDrawerLayout = getView(R.id.person_activity_MainDrawerLayout);
        person_activity_picLinearLayout = getView(R.id.person_activity_picLinearLayout);
        person_activity_CardView = getView(R.id.person_activity_CardView);
        person_activity_picImageView = getView(R.id.person_activity_picImageView);
        person_activity_plusIcon = getView(R.id.person_activity_plusIcon);
        person_activity_nameLinearLayout = getView(R.id.person_activity_nameLinearLayout);
        person_activity_nameTextView = getView(R.id.person_activity_nameTextView);
        person_activity_nameEditText = getView(R.id.person_activity_nameEditText);
        person_activity_nickLinearLayout = getView(R.id.person_activity_nickLinearLayout);
        person_activity_nickTextView = getView(R.id.person_activity_nickTextView);
        person_activity_nickEditText = getView(R.id.person_activity_nickEditText);
        person_activity_numberLinearLayout = getView(R.id.person_activity_numberLinearLayout);
        person_activity_numberTextView = getView(R.id.person_activity_numberTextView);
        person_activity_numberEditText = getView(R.id.person_activity_numberEditText);
        person_activity_habitsLinearLayout = getView(R.id.person_activity_habitsLinearLayout);
        person_activity_habitsTextView = getView(R.id.person_activity_habitsTextView);
        person_activity_habitsEditText = getView(R.id.person_activity_habitsEditText);
        person_activity_fielderLinearLayout = getView(R.id.person_activity_fielderLinearLayout);
        person_activity_fielderTextView = getView(R.id.person_activity_fielderTextView);
        person_activity_fielderEditText = getView(R.id.person_activity_fielderEditText);

        changeEditMode(mEditMode);

        animateSampleOne(person_activity_toolbar, person_activity_CardView, person_activity_nameLinearLayout, person_activity_nickLinearLayout, person_activity_numberLinearLayout, person_activity_habitsLinearLayout, person_activity_fielderLinearLayout);
    }


    private void changeEditMode(boolean isEditMode) {
        if(isEditMode) {
            person_activity_nameTextView.setVisibility(View.GONE);
            person_activity_nickTextView.setVisibility(View.GONE);
            person_activity_numberTextView.setVisibility(View.GONE);
            person_activity_habitsTextView.setVisibility(View.VISIBLE);
            person_activity_fielderTextView.setVisibility(View.VISIBLE);

            person_activity_nameEditText.setVisibility(View.VISIBLE);
            person_activity_nickEditText.setVisibility(View.VISIBLE);
            person_activity_numberEditText.setVisibility(View.VISIBLE);
            person_activity_habitsEditText.setVisibility(View.INVISIBLE);
            person_activity_fielderEditText.setVisibility(View.INVISIBLE);
        } else {
            person_activity_nameTextView.setVisibility(View.VISIBLE);
            person_activity_nickTextView.setVisibility(View.VISIBLE);
            person_activity_numberTextView.setVisibility(View.VISIBLE);
            person_activity_habitsTextView.setVisibility(View.VISIBLE);
            person_activity_fielderTextView.setVisibility(View.VISIBLE);

            person_activity_nameEditText.setVisibility(View.GONE);
            person_activity_nickEditText.setVisibility(View.GONE);
            person_activity_numberEditText.setVisibility(View.GONE);
            person_activity_habitsEditText.setVisibility(View.GONE);
            person_activity_fielderEditText.setVisibility(View.GONE);
        }
    }

    private void setToolbar() {
        setSupportActionBar(person_activity_toolbar);

        supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setTitle(R.string.person_bar_title);
    }

    private void setPlayerData(com.twm.pt.softball.softballlist.component.Player player) {
        if(player==null) return;
        Picasso.with(this).load(picPath + mPlayer.picture).placeholder(R.drawable.progress_animation).error(R.mipmap.baseball_icon).into(person_activity_picImageView);
        person_activity_nameTextView.setText(player.Name);
        person_activity_nickTextView.setText(player.nickName);
        person_activity_numberTextView.setText(player.number);
        person_activity_habitsTextView.setText(player.habits);
        person_activity_fielderTextView.setText(player.fielder.getFielderName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_edit:
                mEditMode = !mEditMode;
                changeEditMode(mEditMode);
            case R.id.action_share:
                return true;
            case R.id.action_discard:
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    private void animateSampleOne(View toolbar, View picView, View nameLinearLayout, View nickLinearLayout, View numberLinearLayout, View habitsLinearLayout, View fielderLinearLayout) {
        //https://github.com/geftimov/android-player
        final PropertyAction toolbarAction = PropertyAction.newPropertyAction(toolbar).interpolator(new DecelerateInterpolator()).translationY(-200).duration(300).alpha(0.4f).build();

        final PropertyAction picViewAction = PropertyAction.newPropertyAction(picView).rotation(-180).scaleX(0.1f).scaleY(0.1f).duration(300).build();

        final PropertyAction nameLinearLayoutAction = PropertyAction.newPropertyAction(nameLinearLayout).interpolator(new DecelerateInterpolator()).translationX(-400).duration(200).alpha(0.4f).build();
        final PropertyAction nickLinearLayoutAction = PropertyAction.newPropertyAction(nickLinearLayout).interpolator(new DecelerateInterpolator()).translationX(-400).duration(200).alpha(0.4f).build();
        final PropertyAction numberLinearLayoutAction = PropertyAction.newPropertyAction(numberLinearLayout).interpolator(new DecelerateInterpolator()).translationX(-400).duration(200).alpha(0.4f).build();
        final PropertyAction habitsLinearLayoutAction = PropertyAction.newPropertyAction(habitsLinearLayout).interpolator(new DecelerateInterpolator()).translationX(-400).duration(200).alpha(0.4f).build();
        final PropertyAction fieldLinearLayoutAction = PropertyAction.newPropertyAction(fielderLinearLayout).interpolator(new DecelerateInterpolator()).translationX(-400).duration(200).alpha(0.4f).build();
        Player.init().
                animate(toolbarAction).
                then().animate(picViewAction).
                then().animate(nameLinearLayoutAction).
                then().animate(nickLinearLayoutAction).
                then().animate(numberLinearLayoutAction).
                then().animate(habitsLinearLayoutAction).
                then().animate(fieldLinearLayoutAction).
                play();
    }


    public final <E extends View> E
    getView(int id) {
        return (E) findViewById(id);
    }
}

