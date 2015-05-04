package com.twm.pt.softball.softballlist.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twm.pt.softball.softballlist.R;

/**
 * Created by TangWen on 2015/5/4.
 */
public class PaidDialogFragment extends DialogFragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.paid_dialog, container, false);
//        getDialog().setTitle("DialogFragment Tutorial");
//        return rootView;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.mipmap.ic_launcher)
                        // Set Dialog Title
                .setTitle(R.string.app_name)
                        // Set Dialog Message
                .setMessage(R.string.paid_text)

                        // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                })
//                        // Negative Button
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,	int which) {
//                        // Do something else
//                    }
//                })
                .create();
    }
}
