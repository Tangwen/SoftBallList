package com.twm.pt.softball.softballlist.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.HashMap;

/**
 * Created by TangWen on 2015/4/30.
 */
public class PositionsDialogFragment extends DialogFragment {

    private HashMap<String, Button> positionButtonMap = new HashMap<>();
    private Player mPlayer;
    private int[] positionCountArrayList;
    private OnDialogResultListener onDialogResultListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mPlayer = (Player)getArguments().getSerializable(Player.BundleKey);
        positionCountArrayList = getArguments().getIntArray(Player.BundleKey_PositionCount);

        View view = inflater.inflate(R.layout.positions_dialog, container);
        initPositionView(view);
        return view;
    }

    int PositionViewIdArray[] = {R.id.button_BP, R.id.button_P, R.id.button_C, R.id.button_1B, R.id.button_2B, R.id.button_3B, R.id.button_SS, R.id.button_LF, R.id.button_CF, R.id.button_RF, R.id.button_SF, R.id.button_EP};
    private void initPositionView(View view) {
        for (Position mPosition : Position.values()) {
            try {
                int no = Integer.parseInt(mPosition.getNO());
                Button tempButton = getView(view, PositionViewIdArray[no]);
                tempButton.setTag(no);
                tempButton.setOnClickListener(positionOnClickListener);
                positionButtonMap.put(mPosition.getShortName(), tempButton);

                if(positionCountArrayList[no]!=1) {
                    tempButton.setText(mPosition.getShortName() + "(" + positionCountArrayList[no] + ")");
                } else {
                    tempButton.setText(mPosition.getShortName());
                }

            } catch (Exception e) {
                L.e(e);
            }
        }

        Button close_button = getView(view, R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView assign_name = getView(view, R.id.assign_name);
        assign_name.setText("指派 " + mPlayer.Name + " 守備位置");
    }

    View.OnClickListener positionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String no = String.valueOf((int) view.getTag());
            mPlayer.position = Position.lookup(no);
            if(onDialogResultListener!=null) {
                onDialogResultListener.onChangePosition(mPlayer);
            }
            dismiss();
        }
    };



    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }

    public interface OnDialogResultListener {
        public abstract void onChangePosition(Player player);
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
