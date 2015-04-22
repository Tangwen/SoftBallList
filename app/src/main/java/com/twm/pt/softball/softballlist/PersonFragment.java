package com.twm.pt.softball.softballlist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.utility.L;

public class PersonFragment extends Fragment {
    RecyclerView person_list_recycler_view;
    static PersonFragment newInstance(String s) {
        PersonFragment newFragment = new PersonFragment();
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("TestFragment-----onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        L.d( "TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.person_fragment, container, false);

        person_list_recycler_view = (RecyclerView) view.findViewById(R.id.person_list_recycler_view);

        return view;

    }

    @Override
    public void onStart() {
    	L.d( "TestFragment-----onStart");
    	super.onStart();
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
        super.onDestroy();
        L.d( "TestFragment-----onDestroy");
    }

}
