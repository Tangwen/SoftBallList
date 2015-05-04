package com.twm.pt.softball.softballlist.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Position;
import com.twm.pt.softball.softballlist.utility.L;

import java.util.HashMap;

/**
 * Created by TangWen on 2015/4/30.
 */
public class PositionsDialogFragment extends DialogFragment {

    private HashMap<String, Button> positionButtonMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.positions_dialog, container);
        initPositionView(view);
        return view;
    }

    int PositionViewIdArray[] = {R.id.button_BP, R.id.button_P, R.id.button_C, R.id.button_1B, R.id.button_2B, R.id.button_3B, R.id.button_SS, R.id.button_LF, R.id.button_CF, R.id.button_RF, R.id.button_SF, R.id.button_EP};
    private void initPositionView(View view) {
        for (Position mPosition : Position.values()) {
            try {
                int no = Integer.parseInt(mPosition.getNO());
                Button tempButton = (Button) view.findViewById(PositionViewIdArray[no]);
                positionButtonMap.put(mPosition.getShortName(), tempButton);
            } catch (Exception e) {
                L.e(e);
            }
        }

        Button close_button = (Button) view.findViewById(R.id.close_button);
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
