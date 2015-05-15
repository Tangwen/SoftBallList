package com.twm.pt.softball.softballlist.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.twm.pt.softball.softballlist.R;

/**
 * Created by TangWen on 2015/5/4.
 */
public class TeamNameDialogFragment extends DialogFragment {
    private EditText et_team_name;
    private OnDialogResultListener onDialogResultListener;
    private String teamName = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.team_name_dialog, null);
        et_team_name = getView(view, R.id.et_team_name);

        et_team_name.setText(teamName);
        builder.setView(view)
                .setTitle(R.string.input_team_name)
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if (onDialogResultListener != null) {
                                    onDialogResultListener.onChangeString(et_team_name.getText().toString());
                                }
                            }
                        })
                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }
    public interface OnDialogResultListener {
        public abstract void onChangeString(String teamName);
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
