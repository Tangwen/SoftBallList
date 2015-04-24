package com.twm.pt.softball.softballlist.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Data.PlayerData;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionsFragment extends Fragment {
    private static PositionsFragment newFragment;
    public static PositionsFragment newInstance() {
        if(newFragment==null) {
            newFragment = new PositionsFragment();
        }
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
        View view = inflater.inflate(R.layout.positions_fragment, container, false);
        initPositionView(view);
        return view;

    }

    @Override
    public void onStart() {
    	L.d( "TestFragment-----onStart");
    	super.onStart();
        setPositionView();
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

    HashMap<String, TextView> PositionViewMap = new HashMap<>();
    int PositionViewIdArray[] = {R.id.text_P, R.id.text_C, R.id.text_1B, R.id.text_2B, R.id.text_3B, R.id.text_SS, R.id.text_LF, R.id.text_CF, R.id.text_RF, R.id.text_SF, R.id.text_EP};
    private void initPositionView(View view) {
        for (Position mPosition : Position.values()) {
            try {
                int no = Integer.parseInt(mPosition.getNO());
                if(no==0) {
                    //TODO add BP array
                    L.d("TODO add BP array");
                } else {
                    TextView tempView = (TextView) view.findViewById(PositionViewIdArray[no-1]);
                    PositionViewMap.put(mPosition.getShortName(), tempView);
                }
            } catch (Exception e) {
                L.e(e);
            }
        }
    }

    private void setPositionView() {
        ArrayList<Player> players = PlayerData.getInstance().getAllPlayers();  //待修改 使用 OrderFragment Adapter 的 Array
        for(Player mPlayer : players) {
            try {
                TextView tempView = PositionViewMap.get(mPlayer.position.getShortName());
                if(tempView!=null) {
                    tempView.setText(mPlayer.Name);
                }
            } catch (Exception e) {
                L.e(e);
            }
        }
    }
}
