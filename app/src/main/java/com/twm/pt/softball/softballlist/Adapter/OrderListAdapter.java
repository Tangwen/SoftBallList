package com.twm.pt.softball.softballlist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/4/23.
 */
public class OrderListAdapter extends ArrayAdapter<Player> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<Player> orderPlayerArrayList;

    public OrderListAdapter(Context mContext, Activity mActivity, ArrayList<Player> playerArrayList) {
        super(mContext, R.layout.order_row, playerArrayList);
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.orderPlayerArrayList = playerArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.order_row, parent, false);

            holder = new ViewHolder();
            holder.order_id = (TextView) view.findViewById(R.id.order_id);
            holder.positions = (Button) view.findViewById(R.id.person_positions);
            holder.name = (TextView) view.findViewById(R.id.person_name);
            holder.number = (TextView) view.findViewById(R.id.person_number);
            holder.average = (TextView) view.findViewById(R.id.person_average);

            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.order_id.setText(String.valueOf(position+1));
        holder.positions.setText(orderPlayerArrayList.get(position).position.getShortName());
        holder.name.setText(orderPlayerArrayList.get(position).Name);
        holder.number.setText(orderPlayerArrayList.get(position).number);
        orderPlayerArrayList.get(position).order_id = position + 1;

        return (view);
    }

    public ArrayList<Player> getOrderPlayerArrayList() {
        return orderPlayerArrayList;
    }

    public void setOrderPlayerArrayList(ArrayList<Player> orderPlayerArrayList) {
        clear();
        for(Player player:orderPlayerArrayList) {
            add(player);
        }
        this.orderPlayerArrayList = orderPlayerArrayList;
    }

    private class ViewHolder {
        TextView order_id;
        Button positions;
        TextView name;
        TextView number;
        TextView average;
    };
}
