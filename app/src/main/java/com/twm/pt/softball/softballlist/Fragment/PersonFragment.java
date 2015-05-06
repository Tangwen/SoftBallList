package com.twm.pt.softball.softballlist.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Adapter.PersonListAdapter;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;

public class PersonFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PersonListAdapter mAdapter;
    private ImageView plus_person;
    private ImageView present_plus_image;
    private TextView present_text_count1;
    private TextView present_text_count2;
    private TextView person_text_total;
    private ArrayList<Player> players = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private PlayerDataManager mPlayerDataManager;
    private int presentCount = 0;

    private static PersonFragment newFragment;
    public static PersonFragment newInstance() {
        if(newFragment==null) {
            newFragment = new PersonFragment();
        }
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("TestFragment-----onCreate");
        mContext = getActivity().getBaseContext();
        mPlayerDataManager = PlayerDataManager.getInstance(mContext);
        mPlayerDataManager.setAllPlayersOnChangeListener(allPlayersOnChangeListener);
        mPlayerDataManager.setOrderPlayersOnChangeListener(orderPlayersOnChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        L.d( "TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.person_fragment, container, false);

        mRecyclerView = getView(view, R.id.person_list_recycler_view);
        plus_person = getView(view, R.id.plus_person);
        present_plus_image = getView(view, R.id.present_plus_image);
        present_text_count1 = getView(view, R.id.present_text_count1);
        present_text_count2 = getView(view, R.id.present_text_count2);
        person_text_total = getView(view, R.id.person_text_total);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager =new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        players = mPlayerDataManager.getAllPlayers();
        mAdapter = new PersonListAdapter(mContext, players);
        mAdapter.setPresentOnClickListener(onPresentClickListener);
        mRecyclerView.setAdapter(mAdapter);
        ArrayList<Player> orderPlayer = mPlayerDataManager.getOrderPlayers();
        presentCount = orderPlayer.size()-1;
        present_text_count1.setText(String.valueOf(presentCount));
        present_text_count2.setText(String.valueOf(presentCount));
        person_text_total.setText("/" + String.valueOf(players.size()));

        plus_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaidDialogFragment paidDialogFragment = new PaidDialogFragment();
                paidDialogFragment.show(getFragmentManager(), "PaidDialogFragment");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
    	L.d( "TestFragment-----onStart");
    	super.onStart();
        setPlayersDataChange();
    }
    
    @Override
    public void onResume() {
    	L.d( "TestFragment-----onResume");
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	L.d( "TestFragment-----onPause");
    	super.onPause();
    }
    @Override
    public void onStop() {
    	L.d( "TestFragment-----onStop");
    	super.onStop();
    }
    @Override
    public void onDestroy() {
        mPlayerDataManager.removeAllPlayersOnChangeListener(allPlayersOnChangeListener);
        mPlayerDataManager.removeOrderPlayersOnChangeListener(orderPlayersOnChangeListener);
        super.onDestroy();
        L.d( "TestFragment-----onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void setPlayersDataChange() {
        players = mPlayerDataManager.getAllPlayers();
        mAdapter.setPlayerArrayList(players);
        mAdapter.notifyDataSetChanged();
    }

    private void plusPresentCount() {
        try {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate);
            Animation animationDown1 = AnimationUtils.loadAnimation(mContext, R.anim.translate_down1);
            Animation animationDown2 = AnimationUtils.loadAnimation(mContext, R.anim.translate_down2);
            presentCount++;
            present_text_count2.setText(String.valueOf(presentCount));
            present_text_count2.setVisibility(View.VISIBLE);
            present_plus_image.startAnimation(animation);
            present_text_count1.startAnimation(animationDown2);
            present_text_count2.startAnimation(animationDown1);

            animationDown2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    present_text_count1.setText(String.valueOf(presentCount));
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void minusPresentCount() {
        try {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.rotate_r);
            Animation animationUp1 = AnimationUtils.loadAnimation(mContext, R.anim.translate_up1);
            Animation animationUp2 = AnimationUtils.loadAnimation(mContext, R.anim.translate_up2);
            presentCount--;
            present_text_count2.setText(String.valueOf(presentCount));
            present_text_count2.setVisibility(View.VISIBLE);
            present_plus_image.startAnimation(animation);
            present_text_count1.startAnimation(animationUp1);
            present_text_count2.startAnimation(animationUp2);

            animationUp1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    present_text_count1.setText(String.valueOf(presentCount));
                }
                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    View.OnClickListener onPresentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(((CheckBox)view).isChecked()) {
                plusPresentCount();
            } else {
                minusPresentCount();
            }
        }
    };

    private PlayerDataManager.onPlayerChangeListener allPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            mAdapter.setPlayerArrayList(mPlayerDataManager.getAllPlayers());
            mAdapter.notifyDataSetChanged();
        }
    };

    private PlayerDataManager.onPlayerChangeListener orderPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {

        }
    };

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
