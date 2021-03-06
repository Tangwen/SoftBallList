package com.twm.pt.softball.softballlist.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twm.pt.softball.softballlist.Adapter.OrderListAdapter;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.ui.TouchListView;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private OrderListAdapter mOrderListAdapter = null;

    private Context mContext;
    private Activity mActivity;
    private FragmentManager mFragmentManager;
    private PlayerDataManager mPlayerDataManager;
    private TouchListView mTouchListView;
    private boolean isMove = false;

    private static OrderFragment newFragment;
    public static OrderFragment newInstance() {
        if(newFragment==null) {
            newFragment = new OrderFragment();
        }
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("TestFragment-----onCreate");
        mContext = getActivity().getBaseContext();
        mFragmentManager = getFragmentManager();
        mPlayerDataManager = PlayerDataManager.getInstance(mContext);
        mPlayerDataManager.setAllPlayersOnChangeListener(allPlayersOnChangeListener);
        mPlayerDataManager.setOrderPlayersOnChangeListener(orderPlayersOnChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d("TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.order_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        L.d("TestFragment-----onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        L.d("TestFragment-----onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        L.d("TestFragment-----onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        L.d("TestFragment-----onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mPlayerDataManager.removeAllPlayersOnChangeListener(allPlayersOnChangeListener);
        mPlayerDataManager.removeOrderPlayersOnChangeListener(orderPlayersOnChangeListener);
        super.onDestroy();
        L.d("TestFragment-----onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private void initView(View view) {
        mTouchListView = getView(view, R.id.order_list);
        mTouchListView.setDropListener(onDrop);
        mTouchListView.setRemoveListener(onRemove);
        initOrderListAdapter();
    }

    private void initOrderListAdapter() {
        isMove = true;
        mOrderListAdapter = new OrderListAdapter(mContext, mActivity, mFragmentManager, mPlayerDataManager.getOrderPlayers());
        mOrderListAdapter.setOnDialogResultListener(onDialogResultListener);
        mTouchListView.setAdapter(mOrderListAdapter);
        isMove = false;
    }



    private TouchListView.DropListener onDrop = new TouchListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            Player item = mOrderListAdapter.getItem(from);
            //L.d(item.toString());
            item.order_id = to;
            mOrderListAdapter.remove(item);
            mOrderListAdapter.insert(item, to);
            mPlayerDataManager.setOrderPlayers(mOrderListAdapter.getOrderPlayerArrayList());
            isMove = true;
        }
    };

    private TouchListView.RemoveListener onRemove = new TouchListView.RemoveListener() {
        @Override
        public void remove(int which) {
            mOrderListAdapter.remove(mOrderListAdapter.getItem(which));
            mPlayerDataManager.setOrderPlayers(mOrderListAdapter.getOrderPlayerArrayList());
            isMove = true;
        }
    };

    private PlayerDataManager.onPlayerChangeListener allPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            initOrderListAdapter();
        }
    };

    private PlayerDataManager.onPlayerChangeListener orderPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            if(isMove) {
                isMove = false;
            } else {
                initOrderListAdapter();
            }
        }
    };

    private PositionsDialogFragment.OnDialogResultListener onDialogResultListener = new PositionsDialogFragment.OnDialogResultListener() {
        @Override
        public void onChangePosition(Player player) {
            int change = mOrderListAdapter.getPosition(player);
            mOrderListAdapter.remove(player);
            mOrderListAdapter.insert(player, change);
            L.d("change=" + change + ", Name=" + player.Name);
            mPlayerDataManager.setOrderPlayers(mOrderListAdapter.getOrderPlayerArrayList());
        }
    };

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
