package com.twm.pt.softball.softballlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PositionsFragment extends Fragment {
    private static final String TAG = "TestFragment";
    private String hello;// = "hello android";
    private String defaultHello = "default value";

    static PositionsFragment newInstance(String s) {
        PositionsFragment newFragment = new PositionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TestFragment-----onCreate");
        Bundle args = getArguments();
        hello = args != null ? args.getString("hello") : defaultHello;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView TestFragment=" + hello);
        View view = inflater.inflate(R.layout.positions_fragment, container, false);
        TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
        viewhello.setText(hello);
        return view;

    }

    @Override
    public void onStart() {
    	Log.d(TAG, "TestFragment-----onStart TestFragment=" + hello);
    	super.onStart();
    }
    
    @Override
    public void onResume() {
    	Log.d(TAG, "TestFragment-----onResume TestFragment=" + hello);
    	super.onResume();
    }
    
    @Override
    public void onPause() {
    	Log.d(TAG, "TestFragment-----onPause TestFragment=" + hello);
    	super.onPause();
    }
    @Override
    public void onStop() {
    	Log.d(TAG, "TestFragment-----onStop TestFragment=" + hello);
    	super.onStop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy TestFragment=" + hello);
    }

}
