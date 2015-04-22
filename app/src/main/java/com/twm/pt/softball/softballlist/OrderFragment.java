package com.twm.pt.softball.softballlist;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.ui.TouchListView;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderFragment extends Fragment {

    private static String[] items = {
            "潘威倫", "林泓育",
            "林益全", "彭政閔", "林智勝", "郭嚴文",
            "陽岱鋼", "林哲瑄", "周思齊", "張建銘",
            "陳金峰"};
    private IconicAdapter mIconicAdapter = null;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<String> array = new ArrayList<String>(Arrays.asList(items));

    static OrderFragment newInstance() {
        OrderFragment newFragment = new OrderFragment();
        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("TestFragment-----onCreate");
        mContext = getActivity().getBaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d("TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.order_fragment, container, false);

        TouchListView tlv = (TouchListView) view.findViewById(R.id.order_list);
        mIconicAdapter = new IconicAdapter();
        tlv.setAdapter(mIconicAdapter);

        tlv.setDropListener(onDrop);
        tlv.setRemoveListener(onRemove);

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
        super.onDestroy();
        L.d("TestFragment-----onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    private TouchListView.DropListener onDrop = new TouchListView.DropListener() {
        @Override
        public void drop(int from, int to) {
            String item = mIconicAdapter.getItem(from);
            mIconicAdapter.remove(item);
            mIconicAdapter.insert(item, to);
        }
    };

    private TouchListView.RemoveListener onRemove = new TouchListView.RemoveListener() {
        @Override
        public void remove(int which) {
            mIconicAdapter.remove(mIconicAdapter.getItem(which));
        }
    };

    class IconicAdapter extends ArrayAdapter<String> {
        IconicAdapter() {
            super(mContext, R.layout.order_row, array);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row = convertView;

            if (row == null) {
                LayoutInflater inflater = mActivity.getLayoutInflater();
                row = inflater.inflate(R.layout.order_row, parent, false);
            }

            TextView label = (TextView) row.findViewById(R.id.person_name);
            TextView order_id = (TextView) row.findViewById(R.id.order_id);


            label.setText(array.get(position));
            order_id.setText(String.valueOf(position+1));

            return (row);
        }
    }
}
