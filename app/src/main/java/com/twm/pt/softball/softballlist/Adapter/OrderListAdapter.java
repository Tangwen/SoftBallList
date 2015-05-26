package com.twm.pt.softball.softballlist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Activity.PersonActivity;
import com.twm.pt.softball.softballlist.Fragment.PaidDialogFragment;
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
    private int playPos = 0;
    private TypedArray emotion_icons;

    public OrderListAdapter(Context mContext, Activity mActivity, FragmentManager mFragmentManager, ArrayList<Player> playerArrayList) {
        super(mContext, R.layout.order_row, playerArrayList);
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mFragmentManager = mFragmentManager;
        this.orderPlayerArrayList = playerArrayList;
        this.emotion_icons = mContext.getResources().obtainTypedArray(R.array.emotion_icons);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.order_row, parent, false);

            holder = new ViewHolder();

            holder.order_row = getView(view, R.id.order_row);
            holder.order_id = getView(view, R.id.order_id);
            holder.positions = getView(view, R.id.person_positions);
            holder.name = getView(view, R.id.person_name);
            holder.number = getView(view, R.id.person_number);
            holder.bat = getView(view, R.id.person_bat);
            holder.average = getView(view, R.id.person_average);
            holder.bp_row = getView(view, R.id.bp_row);
            holder.bp_name = getView(view, R.id.bp_name);

            holder.positions.setOnClickListener(positionsClick);
            holder.average.setOnClickListener(averageClick);
            holder.name.setOnClickListener(batPosClick);
            holder.number.setOnClickListener(batPosClick);
            holder.bat.setOnClickListener(batPosClick);

            holder.name.setOnLongClickListener(nameOnLongClickListener);
            holder.number.setOnLongClickListener(nameOnLongClickListener);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        Player mPlayer = orderPlayerArrayList.get(position);

        if(mPlayer.number.equals(PlayerDataManager.BP_number)) {
            holder.order_row.setVisibility(View.GONE);
            holder.bp_row.setVisibility(View.VISIBLE);
            holder.bp_name.setText(mPlayer.Name);
            mPlayer.order_id = position + 1;
        } else {
            holder.order_row.setVisibility(View.VISIBLE);
            holder.bp_row.setVisibility(View.GONE);
            if(position + 1 > findOrderid(PlayerDataManager.BP_number)) {
                holder.order_id.setText(String.valueOf(position));
            } else {
                holder.order_id.setText(String.valueOf(position + 1));
            }
            holder.positions.setText(mPlayer.position.getShortName());
            holder.name.setText(mPlayer.Name);
            holder.number.setText(mPlayer.number);

            //Set Tag
            holder.name.setTag(position);
            holder.number.setTag(position);
            holder.bat.setTag(position);
            holder.positions.setTag(mPlayer);

            //Set player
            mPlayer.order_id = position + 1;

            if(position==playPos) {
                holder.name.setTextColor(mContext.getResources().getColor(R.color.blue));
                long time = System.currentTimeMillis()% 10000000;
                //L.d("time=" + time + ", emotion_icons.length()="+emotion_icons.length()+", (int)time%emotion_icons.length()=" + (int)time%emotion_icons.length());
                holder.bat.setImageDrawable(emotion_icons.getDrawable((int)time%emotion_icons.length()));
                holder.bat.setVisibility(View.VISIBLE);
            } else {
                holder.name.setTextColor(mContext.getResources().getColor(R.color.yellow));
                holder.bat.setVisibility(View.INVISIBLE);
            }
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
    private int[] getPositionCountArrayList() {
        int positionCountArrayList[] = new int[Position.values().length];
        for(Player player : orderPlayerArrayList) {
            int no = Integer.parseInt(player.position.getNO());
            int count = positionCountArrayList[no];
            if(!player.number.equals(PlayerDataManager.BP_number)) {
                positionCountArrayList[no] = count + 1;
            }
        }
        return positionCountArrayList;
    }

    private int findOrderid(String number) {
        for(Player player : orderPlayerArrayList) {
            if(player.number.equals(number)) {
                return player.order_id;
            }
        }
        return 99;
    }

    View.OnClickListener positionsClick =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Player mPlayer = (Player) view.getTag();
            showPositionDialogFragment(mPlayer);
        }
    };

    View.OnClickListener averageClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            showPaidDialogFragment();
        }
    };

    View.OnClickListener batPosClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changePlayPos(view);
        }
    };

    View.OnLongClickListener nameOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            int pos =  (int) view.getTag();
            openPersonActivity(pos);
            return false;
        }
    };

    private void showPositionDialogFragment(Player mPlayer) {
        int[] positionCountArrayList = getPositionCountArrayList();
        PositionsDialogFragment positionsDialogFragment = new PositionsDialogFragment();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(Player.BundleKey, mPlayer);
        mBundle.putIntArray(Player.BundleKey_PositionCount, positionCountArrayList);
        positionsDialogFragment.setArguments(mBundle);
        positionsDialogFragment.setOnDialogResultListener(onDialogResultListener);
        positionsDialogFragment.show(mFragmentManager, "PositionsDialogFragment");
    }

    private void showPaidDialogFragment() {
        PaidDialogFragment paidDialogFragment = new PaidDialogFragment();
        paidDialogFragment.show(mFragmentManager, "PaidDialogFragment");
    }

    private void changePlayPos(View view) {
        playPos = (int) view.getTag();
        notifyDataSetChanged();
    }

    private void openPersonActivity(int pos) {
        Intent intent = new Intent(mContext, PersonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Player", orderPlayerArrayList.get(pos));
        mContext.startActivity(intent);
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
        ImageView bat;
        TextView average;
        LinearLayout bp_row;
        TextView bp_name;
    };

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
