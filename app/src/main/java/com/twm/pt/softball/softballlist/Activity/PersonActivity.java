package com.twm.pt.softball.softballlist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.squareup.picasso.Picasso;
import com.twm.pt.softball.softballlist.Fragment.ButtonListDialogFragment;
import com.twm.pt.softball.softballlist.Fragment.DeleteDialogFragment;
import com.twm.pt.softball.softballlist.Manager.PictureManager;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.utility.L;
import com.twm.pt.softball.softballlist.utility.StorageDirectory;

import java.io.File;


public class PersonActivity extends ActionBarActivity {

    private ActionBar supportActionBar;
    private Toolbar person_activity_toolbar;
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
    private Button person_activity_habitsButton;
    private LinearLayout person_activity_fielderLinearLayout;
    private TextView person_activity_fielderTextView;
    private Button person_activity_fielderButton;

    private Activity mActivity;
    private PictureManager mPictureManager;
    private PlayerDataManager mPlayerDataManager;
    private boolean mEditMode = false;
    private com.twm.pt.softball.softballlist.component.Player mPlayer;
    private final String picPath = StorageDirectory.getStorageDirectory(this, StorageDirectory.StorageType.ST_SDCard_RootDir) + PlayerDataManager.picPath;
    private final String picPathUri = "file://" + picPath;

    private final String habits[] = {"R/R", "R/L", "L/R", "L/L"};
    private final String plusIcons[] = {"Take Photo", "Gallery Photo", "Cancel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mPictureManager = PictureManager.getInstance(mActivity);
        mPlayerDataManager = PlayerDataManager.getInstance(getApplicationContext());
        mPlayer = getPlayer(savedInstanceState);
        setContentView(R.layout.person_activity);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setToolbar();
        setPlayerData(mPlayer);
        addTextChangedListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.person_activity_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setPlayerPicture(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



    private com.twm.pt.softball.softballlist.component.Player  getPlayer(Bundle savedInstanceState) {
        com.twm.pt.softball.softballlist.component.Player player = (com.twm.pt.softball.softballlist.component.Player) this.getIntent().getSerializableExtra("Player");
        //L.d(mPlayer.toString());
        return player;
    }

    private void initView() {
        person_activity_toolbar = getView(R.id.person_activity_toolbar);
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
        person_activity_habitsButton = getView(R.id.person_activity_habitsButton);
        person_activity_fielderLinearLayout = getView(R.id.person_activity_fielderLinearLayout);
        person_activity_fielderTextView = getView(R.id.person_activity_fielderTextView);
        person_activity_fielderButton = getView(R.id.person_activity_fielderButton);


        person_activity_nameTextView.setOnClickListener(textOnClickListener);
        person_activity_nickTextView.setOnClickListener(textOnClickListener);
        person_activity_numberTextView.setOnClickListener(textOnClickListener);
        person_activity_habitsTextView.setOnClickListener(textOnClickListener);
        person_activity_fielderTextView.setOnClickListener(textOnClickListener);


        person_activity_habitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openButtonListDialogFragment("habits", habits);
            }
        });
        person_activity_fielderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int len = com.twm.pt.softball.softballlist.component.Player.Fielder.values().length;
                String[] fielder = new String[len];
                int i=0;
                for(com.twm.pt.softball.softballlist.component.Player.Fielder mFielder: com.twm.pt.softball.softballlist.component.Player.Fielder.values()) {
                    fielder[i++] = mFielder.getFielderName();
                }
                openButtonListDialogFragment("fielder", fielder);
            }
        });
        person_activity_plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusIcons[0] = getResources().getString(R.string.picture_dialog_TakePhotoText);
                plusIcons[1] = getResources().getString(R.string.picture_dialog_GalleryPhotoText);
                plusIcons[2] = getResources().getString(R.string.picture_dialog_CancelText);
                openButtonListDialogFragment("plusIcon", plusIcons);
            }
        });

        changeEditMode(mEditMode);

        animateSampleOne(person_activity_toolbar, person_activity_CardView, person_activity_nameLinearLayout, person_activity_nickLinearLayout, person_activity_numberLinearLayout, person_activity_habitsLinearLayout, person_activity_fielderLinearLayout);
    }

    View.OnClickListener textOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mEditMode=!mEditMode;
            changeEditMode(mEditMode);
        }
    };

    private void addTextChangedListener() {
        person_activity_nameEditText.addTextChangedListener(new TextWatcherData(0));
        person_activity_nickEditText.addTextChangedListener(new TextWatcherData(1));
        person_activity_numberEditText.addTextChangedListener(new TextWatcherData(2));
    }

    class TextWatcherData implements TextWatcher {
        int id;
        String oldNumber;
        public TextWatcherData(int id) {
            this.id = id;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            switch(id) {
                case 0:
                    mPlayer.Name = person_activity_nameEditText.getText().toString();
                    person_activity_nameTextView.setText(mPlayer.Name);
                    break;
                case 1:
                    mPlayer.nickName = person_activity_nickEditText.getText().toString();
                    person_activity_nickTextView.setText(mPlayer.nickName);
                    break;
                case 2:
                    String oldNumber = mPlayer.number;
                    mPlayerDataManager.remove_AllPlayers(mPlayer);
                    mPlayer.number = person_activity_numberEditText.getText().toString();
                    mPlayerDataManager.add_AllPlayers(mPlayer);
                    person_activity_numberTextView.setText(mPlayer.number);
                    break;
            }
            if(id!=2){
                mPlayerDataManager.modify_AllPlayers(mPlayer);
            }
        }
    }


    private void changeEditMode(boolean isEditMode) {
        if(isEditMode) {
            person_activity_nameTextView.setVisibility(View.GONE);
            person_activity_nickTextView.setVisibility(View.GONE);
            person_activity_numberTextView.setVisibility(View.GONE);
            person_activity_habitsTextView.setVisibility(View.GONE);
            person_activity_fielderTextView.setVisibility(View.GONE);

            person_activity_nameEditText.setVisibility(View.VISIBLE);
            person_activity_nickEditText.setVisibility(View.VISIBLE);
            person_activity_numberEditText.setVisibility(View.VISIBLE);
            person_activity_habitsButton.setVisibility(View.VISIBLE);
            person_activity_fielderButton.setVisibility(View.VISIBLE);
        } else {
            person_activity_nameTextView.setVisibility(View.VISIBLE);
            person_activity_nickTextView.setVisibility(View.VISIBLE);
            person_activity_numberTextView.setVisibility(View.VISIBLE);
            person_activity_habitsTextView.setVisibility(View.VISIBLE);
            person_activity_fielderTextView.setVisibility(View.VISIBLE);

            person_activity_nameEditText.setVisibility(View.GONE);
            person_activity_nickEditText.setVisibility(View.GONE);
            person_activity_numberEditText.setVisibility(View.GONE);
            person_activity_habitsButton.setVisibility(View.GONE);
            person_activity_fielderButton.setVisibility(View.GONE);
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
        Picasso.with(this).load(picPathUri + mPlayer.picture).placeholder(R.drawable.progress_animation).error(R.mipmap.baseball_icon).into(person_activity_picImageView);
        person_activity_nameTextView.setText(player.Name);
        person_activity_nickTextView.setText(player.nickName);
        person_activity_numberTextView.setText(player.number);
        person_activity_habitsTextView.setText(player.habits);
        person_activity_fielderTextView.setText(player.fielder.getFielderName());

        person_activity_nameEditText.setText(player.Name);
        person_activity_nickEditText.setText(player.nickName);
        person_activity_numberEditText.setText(player.number);
        person_activity_habitsButton.setText(player.habits);
        person_activity_fielderButton.setText(player.fielder.getFielderName());


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
                return true;
            case R.id.action_share:
                shotScreenAndsaveAndShare(getWindow().getDecorView().getRootView());
                return true;
            case R.id.action_discard:
                openDeleteDialogFragment();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
            1. shot screen
            2.checkPath
            3.save Bitmap
            4.share
     */
    private void shotScreenAndsaveAndShare(View view) {
        try {
            String path = StorageDirectory.getStorageDirectory(this, StorageDirectory.StorageType.ST_SDCard_RootDir) + PlayerDataManager.picPath;
            File shotScreenFile = new File(path + "tmp.jpg");

            Bitmap shotScreen = PictureManager.screenShot(view);
            StorageDirectory.checkPath(path);
            PictureManager.saveBitmapToFile(shotScreen, shotScreenFile);
            PictureManager.shareURI(this, Uri.fromFile(shotScreenFile));
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    private void openDeleteDialogFragment() {
        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.setOnDialogResultListener(new DeleteDialogFragment.OnDialogResultListener() {
            @Override
            public void onClickPositiveButton() {
                mPlayerDataManager.remove_AllPlayers(mPlayer);
                finish();
            }
            @Override
            public void onClickNegativeButton() {
            }
        });
        deleteDialogFragment.show(getSupportFragmentManager(), "DeleteDialogFragment");
    }


    private void openButtonListDialogFragment(String tag, String textData[]) {
        Bundle mBundle =  new Bundle();
        mBundle.putStringArray("textData", textData);
        ButtonListDialogFragment buttonListDialogFragment = new ButtonListDialogFragment();
        buttonListDialogFragment.setArguments(mBundle);
        buttonListDialogFragment.setOnDialogResultListener(new ButtonListDialogFragment.OnDialogResultListener() {
            @Override
            public void onResult(String tag, int index) {
                try {
                    if(tag.equals("habits")) {
                        mPlayer.habits = habits[index];
                        person_activity_habitsTextView.setText(mPlayer.habits);
                        person_activity_habitsButton.setText(mPlayer.habits);
                        mPlayerDataManager.modify_AllPlayers(mPlayer);
                    } else if(tag.equals("fielder")) {
                        com.twm.pt.softball.softballlist.component.Player.Fielder fielders[] = com.twm.pt.softball.softballlist.component.Player.Fielder.values();
                        mPlayer.fielder = fielders[index];
                        person_activity_fielderTextView.setText(mPlayer.fielder.getFielderName());
                        person_activity_fielderButton.setText(mPlayer.fielder.getFielderName());
                        mPlayerDataManager.modify_AllPlayers(mPlayer);
                    } else if (tag.equals("plusIcon")) {
                        switch(index) {
                            case 0:
                                mPictureManager.setPhotoFilePath(picPath).setPhotoFileName("image_" + mPlayer.number + ".jpg").takePhoto();
                                break;
                            case 1:
                                mPictureManager.setPhotoFilePath(picPath).setPhotoFileName("image_" + mPlayer.number + ".jpg").galleryPhoto();
                                break;
                            case 2:
                                break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        buttonListDialogFragment.show(getSupportFragmentManager(), tag);
    }

    private void setPlayerPicture(int requestCode, int resultCode, Intent data) {
        Bitmap photo = mPictureManager.onActivityResult(requestCode, resultCode, data);
        if(photo!=null) {
            mPlayer.picture = mPictureManager.getPhotoFileName();
            Picasso.with(this).load(picPathUri + mPlayer.picture).placeholder(R.drawable.progress_animation).error(R.mipmap.baseball_icon).into(person_activity_picImageView);
            PlayerDataManager.getInstance(getApplicationContext()).modify_AllPlayers(mPlayer);
        }
    }

    //https://github.com/geftimov/android-player
    private void animateSampleOne(View toolbar, View picView, View nameLinearLayout, View nickLinearLayout, View numberLinearLayout, View habitsLinearLayout, View fielderLinearLayout) {
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


