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

import com.twm.pt.softball.softballlist.R;

public class PictureDialogFragment extends DialogFragment {

    private Button picture_dialog_takePhoto;
    private Button picture_dialog_galleryPhoto;
    private Button picture_dialog_cancel;
    private OnDialogResultListener onDialogResultListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.picture_dialog, container);
        initView(view);
        return view;
    }

    private void initView(View view) {
        picture_dialog_takePhoto = getView(view, R.id.picture_dialog_takePhoto);
        picture_dialog_galleryPhoto = getView(view, R.id.picture_dialog_galleryPhoto);
        picture_dialog_cancel = getView(view, R.id.picture_dialog_cancel);

        picture_dialog_takePhoto.setOnClickListener(buttonOnClickListener);
        picture_dialog_galleryPhoto.setOnClickListener(buttonOnClickListener);
        picture_dialog_cancel.setOnClickListener(buttonOnClickListener);
    }

    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int choose= -1;
            switch(view.getId()) {
                case R.id.picture_dialog_takePhoto:
                    choose=0;
                    break;
                case R.id.picture_dialog_galleryPhoto:
                    choose=1;
                    break;
                case R.id.picture_dialog_cancel:
                    choose=-1;
                    break;
            }
            if(onDialogResultListener!=null) {
                onDialogResultListener.onResult(choose);
            }
            dismiss();
        }
    };


    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }

    public interface OnDialogResultListener {
        public abstract void onResult(int choose);
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
