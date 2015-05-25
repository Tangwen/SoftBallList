package com.twm.pt.softball.softballlist.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.twm.pt.softball.softballlist.R;

public class DeleteDialogFragment extends DialogFragment {

    private OnDialogResultListener onDialogResultListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return showAlertDialog();
    }

    private AlertDialog showAlertDialog() {
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
//        View view = mInflater.inflate(R.layout.delete_dialog, null);
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.action_discard)
                .setMessage(R.string.alert_dialog_delete)
//                .setView(view)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(onDialogResultListener!=null) {
                                    onDialogResultListener.onClickPositiveButton();
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if(onDialogResultListener!=null) {
                                    onDialogResultListener.onClickNegativeButton();
                                }
                            }
                        }
                )
                .create();
    }

    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }

    public interface OnDialogResultListener {
        public abstract void onClickPositiveButton();
        public abstract void onClickNegativeButton();
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
