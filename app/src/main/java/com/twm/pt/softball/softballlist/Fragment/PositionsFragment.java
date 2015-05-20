package com.twm.pt.softball.softballlist.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionsFragment extends Fragment {
    private Context mContext;
    private Activity mActivity;
    private PlayerDataManager mPlayerDataManager;
    private SwitchDisplay switch_id = SwitchDisplay.Name;

    private HashMap<String, TextView> positionViewMap = new HashMap<>();
    private ImageView buttonSwitch;



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
        mContext = getActivity().getBaseContext();
        mPlayerDataManager = PlayerDataManager.getInstance(mContext);
        mPlayerDataManager.setAllPlayersOnChangeListener(allPlayersOnChangeListener);
        mPlayerDataManager.setOrderPlayersOnChangeListener(orderPlayersOnChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        L.d( "TestFragment-----onCreateView");
        View view = inflater.inflate(R.layout.positions_fragment, container, false);
        initPositionView(view);
        initSwitchView(view);
        return view;

    }

    @Override
    public void onStart() {
    	L.d("TestFragment-----onStart");
    	super.onStart();
        setSwitchView();
        setPositionView();
    }
    
    @Override
    public void onResume() {
    	L.d("TestFragment-----onResume");
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
        L.d("TestFragment-----onDestroy");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }



    int PositionViewIdArray[] = {R.id.text_BP, R.id.text_P, R.id.text_C, R.id.text_1B, R.id.text_2B, R.id.text_3B, R.id.text_SS, R.id.text_LF, R.id.text_CF, R.id.text_RF, R.id.text_SF, R.id.text_EP};
    private void initPositionView(View view) {
        for (Position mPosition : Position.values()) {
            try {
                int no = Integer.parseInt(mPosition.getNO());
                TextView positionView = getView(view, PositionViewIdArray[no]);
                positionView.setOnTouchListener(positionViewOnTouchListener);
                positionViewMap.put(mPosition.getShortName(), positionView);
            } catch (Exception e) {
                L.e(e);
            }
        }
    }

    private void initSwitchView(View view) {
        buttonSwitch =  getView(view, R.id.button_switch);
        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_id = switch_id.next();
                setSwitchView();
                setPositionView();
            }
        });
    }

    private void clearPositionView() {
        for (Position mPosition : Position.values()) {
            try {
                TextView tempView = positionViewMap.get(mPosition.getShortName());
                if(tempView!=null) {
                    tempView.setText(mPosition.getShortName());
                }
            } catch (Exception e) {
                L.e(e);
            }
        }
    }
    private void setPositionView() {
        clearPositionView();

        ArrayList<Player> players = mPlayerDataManager.getOrderPlayers();
        for(Player mPlayer : players) {
            try {
                if(mPlayer.number.equals(PlayerDataManager.BP_number)) {
                } else {
                    TextView tempView = positionViewMap.get(mPlayer.position.getShortName());
                    if(tempView!=null) {
                        String sn = tempView.getText().toString();
                        if(Position.lookup(sn)!=null) {
                            tempView.setText(getTextContext(mPlayer));
                        } else {
                            if(mPlayer.position.equals(Position.BenchPlayer)) {
                                tempView.setText(sn+"\t" + getTextContext(mPlayer));
                            } else {
                                tempView.setText(sn+"\n" + getTextContext(mPlayer));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                L.e(e);
            }
        }
    }
    private String getTextContext(Player mPlayer) {
        switch (switch_id) {
            case Name:
                return mPlayer.Name;
            case NickName:
                return mPlayer.nickName;
            case Number:
                return "[" + mPlayer.number + "]";
            default:
                return mPlayer.Name;
        }
    }

    private void setSwitchView() {
        buttonSwitch.setImageResource(switch_id.getImageId());
    }



    private PlayerDataManager.onPlayerChangeListener allPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            setPositionView();
        }
    };

    private PlayerDataManager.onPlayerChangeListener orderPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            setPositionView();
        }
    };

    private View.OnTouchListener positionViewOnTouchListener =  new View.OnTouchListener() {
        int[] temp = new int[] { 0, 0 };

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int eventAction = motionEvent.getAction();

            int x = (int) motionEvent.getRawX();
            int y = (int) motionEvent.getRawY();
            int p = (int) motionEvent.getX();
            int q = (int) motionEvent.getY();
            L.d("eventAction="+ eventAction + ", x="+x+", y="+y+", p="+p+", q="+q);
            L.d("view.getWidth()=" + view.getWidth() + ", view.getHeight()=" + view.getHeight());

            switch (eventAction) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
                case MotionEvent.ACTION_DOWN:
                    temp[0] = (int) motionEvent.getX();
                    temp[1] = y - view.getTop();

                    break;
                case MotionEvent.ACTION_MOVE:
                    int l = x - temp[0];
                    int t = y - temp[1];
                    int r = x + view.getWidth() - temp[0];
                    int b = y - temp[1] + view.getHeight();

                    view.layout(l, t, r, b);
                    view.postInvalidate();

                    break;
            }
            return true;
        }

    };




    enum SwitchDisplay {
        Name(0),
        NickName(1),
        Number(2);

        int value = 0;
        int imageId[] = {R.mipmap.hat_baseball_blue_icon, R.mipmap.hat_baseball_green_icon, R.mipmap.hat_baseball_red_icon};
        SwitchDisplay(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public int getImageId() {
            return imageId[value];
        }
        public SwitchDisplay lookup(int val) {
            for(SwitchDisplay obj: SwitchDisplay.values()) {
                if(obj.getValue()  == val) {
                    return obj;
                }
            }
            return null;
        }

        public SwitchDisplay next() {
            for(SwitchDisplay obj: SwitchDisplay.values()) {
                if(obj.getValue()  == value+1) {
                    return obj;
                }
            }
            return Name;
        }
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
