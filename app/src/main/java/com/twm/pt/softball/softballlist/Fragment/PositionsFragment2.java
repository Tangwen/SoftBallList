package com.twm.pt.softball.softballlist.Fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionsFragment2 extends Fragment {
    private Context mContext;
    private Activity mActivity;
    private PlayerDataManager mPlayerDataManager;
    private SwitchDisplay switch_id = SwitchDisplay.Name;

    private ArrayList<Player> orderPlayers;
    private HashMap<Position, TextView> positionViewMap = new HashMap<>();
    private HashMap<Position, int[]> positionViewPos = new HashMap<>();
    private FrameLayout positions_FrameLayout;
    private ImageView buttonSwitch;
    private int layoutWidth =0, layoutHeight =0;
    private int viewWidth=0, viewHeight=0;
    private int margin = 0;
    private int viewSpace = 150;
    private int BPcount = 0;
    private boolean isMove = false;
    private VelocityTracker mVelocityTracker = null;

    private static PositionsFragment2 newFragment;
    public static PositionsFragment2 newInstance() {
        if(newFragment==null) {
            newFragment = new PositionsFragment2();
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
        View view = inflater.inflate(R.layout.positions_fragment2, container, false);
        positions_FrameLayout = getView(view, R.id.positions_FrameLayout);

        getLayoutSize(view);
        calPositionView(layoutWidth, layoutHeight);
        initSwitchView(view);
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
        setAllView(positions_FrameLayout);
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




    private void getLayoutSize(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        layoutWidth =view.getMeasuredWidth();
        layoutHeight =view.getMeasuredHeight();
        viewWidth = layoutWidth * 144/532;
        viewHeight = layoutHeight * 65/690;
        margin = layoutWidth * 10/100;
        L.d("layoutWidth=" + layoutWidth + ", layoutHeight=" + layoutHeight + ", viewWidth=" + viewWidth + ", viewHeight=" + viewHeight + ", margin=" + margin + ",  viewSpace=" + viewSpace);
    }

    private void calPositionView(int width, int height) {
        positionViewPos = new HashMap<>();

        int pos[] = {0, 0};
        positionViewPos.put(Position.BenchPlayer, pos);

        pos = new int[]{margin, layoutHeight+viewSpace};
        positionViewPos.put(Position.ExtraPlayer, pos);
        pos = new int[]{layoutWidth/2, layoutHeight+viewSpace};
        positionViewPos.put(Position.Catcher, pos);

        pos = new int[]{margin, layoutHeight};
        positionViewPos.put(Position.ThirdBaseMan, pos);
        pos = new int[]{layoutWidth/2, layoutHeight};
        positionViewPos.put(Position.Pitcher, pos);
        pos = new int[]{layoutWidth-margin, layoutHeight};
        positionViewPos.put(Position.FirstBaseMan, pos);

        pos = new int[]{layoutWidth/3, layoutHeight-viewSpace};
        positionViewPos.put(Position.ShortShop, pos);
        pos = new int[]{layoutWidth*2/3, layoutHeight-viewSpace};
        positionViewPos.put(Position.SecondBaseMan, pos);

        pos = new int[]{margin, layoutHeight-viewSpace*3/2};
        positionViewPos.put(Position.LeftFielder, pos);
        pos = new int[]{layoutWidth-margin, layoutHeight-viewSpace*3/2};
        positionViewPos.put(Position.RightFielder, pos);

        pos = new int[]{layoutWidth/3, layoutHeight-viewSpace*2};
        positionViewPos.put(Position.CenterFielder, pos);
        pos = new int[]{layoutWidth*2/3, layoutHeight-viewSpace*2};
        positionViewPos.put(Position.ShortFielder, pos);
    }


    private void initSwitchView(View view) {
        buttonSwitch =  getView(view, R.id.button_switch);
        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch_id = switch_id.next();
                setAllView(positions_FrameLayout);
            }
        });
    }


    private void setAllView(FrameLayout frameLayout) {
        orderPlayers = mPlayerDataManager.getOrderPlayers();
        clearPositionView(frameLayout);
        addPositionView(frameLayout);
        addSwitchView(frameLayout);
        isMove = false;
    }

    private void clearPositionView(FrameLayout frameLayout) {
        BPcount = 0;
        frameLayout.removeAllViews();
    }

    private void addPositionView(FrameLayout frameLayout) {
        for(Player mPlayer : orderPlayers) {
            try {
                if(mPlayer.number.equals(PlayerDataManager.BP_number)) {
                } else {
                    ViewHolder viewHolder = getPositionViewHolder(mPlayer.position);
                    viewHolder.positionView.setText(getTextContext(mPlayer));
                    viewHolder.positionView.setTag(mPlayer);
                    frameLayout.addView(viewHolder.positionView, viewHolder.fLayoutParams);
                }
            } catch (Exception e) {
                L.e(e);
            }
        }
    }

    private void addSwitchView(FrameLayout frameLayout) {
        frameLayout.addView(buttonSwitch);
        buttonSwitch.setImageResource(switch_id.getImageId());
    }


    private ViewHolder getPositionViewHolder(Position mPosition) {
        int pos[] = positionViewPos.get(mPosition);

        ViewHolder positionViewHolder =  new ViewHolder();

        positionViewHolder.positionView = new TextView(mContext);
        positionViewHolder.positionView.setTextAppearance(mContext, R.style.PositionsText2);
        positionViewHolder.positionView.setOnTouchListener(positionViewOnTouchListener);

        positionViewHolder.fLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        positionViewHolder.fLayoutParams.gravity = Gravity.LEFT| Gravity.TOP;
        positionViewHolder.fLayoutParams.leftMargin = pos[0]+20;
        positionViewHolder.fLayoutParams.topMargin = pos[1];

        if(mPosition.equals(Position.BenchPlayer)) {
            positionViewHolder.fLayoutParams.leftMargin = pos[0]+20 + ((BPcount%4)*(viewWidth+20));
            positionViewHolder.fLayoutParams.topMargin = pos[1] + ((BPcount/4)*viewHeight) + 20 ;
            BPcount ++;
        }
        return positionViewHolder;
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





    private PlayerDataManager.onPlayerChangeListener allPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            setAllView(positions_FrameLayout);
        }
    };

    private PlayerDataManager.onPlayerChangeListener orderPlayersOnChangeListener = new PlayerDataManager.onPlayerChangeListener() {
        @Override
        public void onChange(ArrayList<Player> players) {
            setAllView(positions_FrameLayout);
        }
    };

    private View.OnTouchListener positionViewOnTouchListener =  new View.OnTouchListener() {
        int[] temp = new int[] { 0, 0 };

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int eventAction = motionEvent.getAction();
            int index = motionEvent.getActionIndex();
            int pointerId = motionEvent.getPointerId(index);

            int x = (int) motionEvent.getRawX();
            int y = (int) motionEvent.getRawY();
            int p = (int) motionEvent.getX();
            int q = (int) motionEvent.getY();
            //L.d("eventAction="+ eventAction + ", x="+x+", y="+y+", p="+p+", q="+q);
            //L.d("view.getWidth()=" + view.getWidth() + ", view.getHeight()=" + view.getHeight() + ", eventAction=" + eventAction);

            switch (eventAction) {
                case MotionEvent.ACTION_CANCEL:
                    recycleVelocityTracker();
                case MotionEvent.ACTION_UP:
                    moveNearPosition(view, x-temp[0], y-temp[1]);
                    break;
                case MotionEvent.ACTION_DOWN:
                    temp[0] = (int) motionEvent.getX();
                    temp[1] = y - view.getTop();

                    initVelocityTracker();
                    addMovementToVelocityTracker(motionEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    setViewXY(view, x - temp[0], y - temp[1]);

                    addMovementToVelocityTracker(motionEvent);
                    getVelocity(pointerId);
                    break;
            }
            return true;
        }
    };

    private void setViewXY(View view, int newX, int newY) {
        int l = newX;
        int t = newY;
        int r = newX + view.getWidth();
        int b = newY + view.getHeight();

        view.layout(l, t, r, b);
        view.postInvalidate();
    }
    private void moveView(View view, int oldX, int oldY, int newX, int newY) {
        try {
            int step=50;
            int disX = (newX-oldX);
            int disY = (newY-oldY);
            for(int i=0;i<step;i++) {
                int tempX = oldX + (i*disX)/step;
                int tempY = oldY + (i*disY)/step;
                //L.d("[" + oldX + "," + oldY + "," + newX + "," + newY + "," + tempX + "," + tempY + "]");
                setViewXY(view, tempX, tempY);
            }
            setViewXY(view, newX, newY);
        } catch (Exception e) {

        }
    }

    private void moveNearPosition(View view, int x, int y) {
        try {
            if(isMove) {
                return;
            } else {
                isMove = true;
            }
            Position newPosition = getNearPosition(x, y);
            int newPositionXY[] = positionViewPos.get(newPosition);
            moveView(view, x, y, newPositionXY[0], newPositionXY[1]);

            Player mPlayer = (Player)view.getTag();
            if(newPosition==null) {
                newPosition = mPlayer.position;
            } else {
                mPlayer.position = newPosition;
            }
            //L.d("newPosition=" + newPosition);
            setAllView(positions_FrameLayout);

            orderPlayers.set(orderPlayers.indexOf(mPlayer), mPlayer);
            mPlayerDataManager.setOrderPlayers(orderPlayers);
        } catch (Exception e) {
            L.e(e);
        }
    }

    private Position getNearPosition(int x, int y) {
        Position nearPosition = null;
        double minDistance = 1000;
        double tempDistance;
        int pos[];
        for (Position mPosition : Position.values()) {
            pos = positionViewPos.get(mPosition);
            if(pos[1]==0) {
                tempDistance = y;
            } else {
                tempDistance = calDistance(x, y, pos[0], pos[1]);
            }
            //L.d("x=" + x + ", y=" + y + ", pos[0]=" + pos[0] + ", pos[1]=" + pos[1]);
            if(tempDistance!=-1 && minDistance > tempDistance) {
                minDistance = tempDistance;
                nearPosition = mPosition;
            }
            //L.d("mPosition=" + mPosition.getShortName() + ", nearPosition=" + nearPosition.getShortName() + ", tempDistance=" + tempDistance + ", minDistance=" + minDistance);
        }
        return nearPosition;
    }

    private double calDistance(int x1, int y1, int x2, int y2) {
        try {
            return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        } catch (Exception e) {
            L.e(e);
        }
        return -1;
    }


    //VelocityTracker
    private void initVelocityTracker() {
        if (mVelocityTracker == null) {
            // Retrieve a new VelocityTracker object to watch the velocity
            // of a motion.
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            // Reset the velocity tracker back to its initial state.
            mVelocityTracker.clear();
        }
    }
    private void addMovementToVelocityTracker(MotionEvent event) {
        // Add a user's movement to the tracker.
        mVelocityTracker.addMovement(event);
    }
    private void recycleVelocityTracker() {
        // Return a VelocityTracker object back to be re-used by others.
        //mVelocityTracker.recycle();
        mVelocityTracker.clear();
    }
    private float[] getVelocity(int pointerId) {
        float v[] = new float[2];
        mVelocityTracker.computeCurrentVelocity(1000);
        v[0] = VelocityTrackerCompat.getXVelocity(mVelocityTracker, pointerId);
        v[1] = VelocityTrackerCompat.getYVelocity(mVelocityTracker, pointerId);
        //L.d("X velocity:" + v[0] + "Y velocity:" + v[1]);
        return v;
    }



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

    private class ViewHolder {
        FrameLayout.LayoutParams fLayoutParams;
        TextView positionView;
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
