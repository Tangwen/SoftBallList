package com.twm.pt.softball.softballlist.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    private ArrayList<Player> playerArrayList;

    public OrderListAdapter(Context mContext, Activity mActivity, ArrayList<Player> playerArrayList) {
        super(mContext, R.layout.order_row, playerArrayList);
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.playerArrayList = playerArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            row = inflater.inflate(R.layout.order_row, parent, false);
        }

        TextView label = (TextView) row.findViewById(R.id.person_name);
        TextView order_id = (TextView) row.findViewById(R.id.order_id);

        label.setText(playerArrayList.get(position).Name);
        order_id.setText(String.valueOf(position+1));

        return (row);
    }

    private class ViewHolder {
        TextView label;
        TextView order_id;
    };
}
