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

import com.twm.pt.softball.softballlist.Adapter.PersonListAdapter;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;

public class PersonFragment extends Fragment {
    RecyclerView mRecyclerView;
    PersonListAdapter mAdapter;
    ArrayList<Player> players = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private PlayerDataManager mPlayerDataManager;

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

        mRecyclerView = (RecyclerView) view.findViewById(R.id.person_list_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager =new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        players = mPlayerDataManager.getAllPlayers();
        mAdapter = new PersonListAdapter(mContext, players);
        mRecyclerView.setAdapter(mAdapter);

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
}
