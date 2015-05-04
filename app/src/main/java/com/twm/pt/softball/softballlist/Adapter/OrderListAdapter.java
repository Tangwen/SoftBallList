package com.twm.pt.softball.softballlist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Fragment.PositionsDialogFragment;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/4/23.
 */
public class OrderListAdapter extends ArrayAdapter<Player> {
    private Context mContext;
    private Activity mActivity;
    private FragmentManager mFragmentManager ;
    private ArrayList<Player> orderPlayerArrayList;
    private PositionsDialogFragment.OnDialogResultListener onDialogResultListener = null;

    public OrderListAdapter(Context mContext, Activity mActivity, FragmentManager mFragmentManager, ArrayList<Player> playerArrayList) {
        super(mContext, R.layout.order_row, playerArrayList);
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        this.orderPlayerArrayList = playerArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.order_row, parent, false);

            holder = new ViewHolder();

            holder.order_row = (LinearLayout) view.findViewById(R.id.order_row);
            holder.order_id = (TextView) view.findViewById(R.id.order_id);
            holder.positions = (Button) view.findViewById(R.id.person_positions);
            holder.name = (TextView) view.findViewById(R.id.person_name);
            holder.number = (TextView) view.findViewById(R.id.person_number);
            holder.average = (TextView) view.findViewById(R.id.person_average);
            holder.bp_row = (LinearLayout) view.findViewById(R.id.bp_row);
            holder.bp_name = (TextView) view.findViewById(R.id.bp_name);

            holder.positions.setOnClickListener(positionsClick);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Player mPlayer = orderPlayerArrayList.get(position);

        if(mPlayer.number.equals(PlayerDataManager.BP_number)) {
            holder.order_row.setVisibility(View.GONE);
            holder.bp_row.setVisibility(View.VISIBLE);
            holder.bp_name.setText(mPlayer.Name);
        } else {
            holder.order_row.setVisibility(View.VISIBLE);
            holder.bp_row.setVisibility(View.GONE);
            holder.order_id.setText(String.valueOf(position + 1));
            holder.positions.setText(mPlayer.position.getShortName());
            holder.positions.setTag(mPlayer);
            holder.name.setText(mPlayer.Name);
            holder.number.setText(mPlayer.number);
            mPlayer.order_id = position + 1;

            if(getShortNameCount(mPlayer.position.getShortName())>1 && !mPlayer.position.equals(Position.BenchPlayer)) {
                holder.positions.setBackgroundResource(R.drawable.btn_red);
            } else {
                holder.positions.setBackgroundResource(R.drawable.btn_black);
            }
        }

        return (view);
    }

    private int getShortNameCount(String shortName) {
        int count = 0;
        for(Player mPlayer : orderPlayerArrayList) {
            if(mPlayer.position.getShortName().equals(shortName)) {
                count++;
            }
        }
        return count;
    }
    private int[] getPositionCounArrayList() {
        int positionCounArrayList[] = new int[Position.values().length];
        for(Player player : orderPlayerArrayList) {
            int no = Integer.parseInt(player.position.getNO());
            int count = positionCounArrayList[no];
            positionCounArrayList[no] = count + 1;
        }
        return positionCounArrayList;
    }

    View.OnClickListener positionsClick =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Player mPlayer = (Player) view.getTag();
            showDialogFragment(mPlayer);
        }
    };

    private void showDialogFragment(Player mPlayer) {
        int[] positionCounArrayList = getPositionCounArrayList();
        PositionsDialogFragment positionsDialogFragment = new PositionsDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(Player.BundleKey, mPlayer);
        mBundle.putIntArray(Player.BundleKey_PositionCount, positionCounArrayList);
        positionsDialogFragment.setArguments(mBundle);
        positionsDialogFragment.setOnDialogResultListener(onDialogResultListener);
        positionsDialogFragment.show(mFragmentManager, "PositionsDialogFragment");
    }


    public ArrayList<Player> getOrderPlayerArrayList() {
        return orderPlayerArrayList;
    }

    public void setOnDialogResultListener(PositionsDialogFragment.OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }

    private class ViewHolder {
        LinearLayout order_row;
        TextView order_id;
        Button positions;
        TextView name;
        TextView number;
        TextView average;
        LinearLayout bp_row;
        TextView bp_name;
    };
}
