package com.twm.pt.softball.softballlist.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.twm.pt.softball.softballlist.R;

public class ButtonListDialogFragment extends DialogFragment {
    String textData[] = null;
    private OnDialogResultListener onDialogResultListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        textData = getArguments().getStringArray("textData");
        View view = inflater.inflate(R.layout.button_list_dialog, container);
        initView((LinearLayout) view);
        return view;
    }

    private void initView(LinearLayout mLinearLayout) {
        Resources res = getResources();
        final float scale = res.getDisplayMetrics().density;
        LinearLayout.LayoutParams lLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lLayoutParams.setMargins(10, 15, 10, 15);
        lLayoutParams.width = (int) (200*scale);
        if(textData!=null) {
            int len = textData.length;
            for(int i=0;i<len;i++) {
                Button button =  new Button(getActivity());
                button.setLayoutParams(lLayoutParams);
                button.setTextSize(20);
                button.setId(i);
                button.setBackgroundResource(R.drawable.btn_blue);
                button.setTextColor(res.getColor(R.color.white));
                button.setText(textData[i]);
                button.setOnClickListener(buttonOnClickListener);
                mLinearLayout.addView(button);
            }
        }
    }

    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                int index = view.getId();
                if(onDialogResultListener!=null) {
                    onDialogResultListener.onResult(getTag(), index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dismiss();
        }
    };


    public void setOnDialogResultListener(OnDialogResultListener listener) {
        this.onDialogResultListener = listener;
    }

    public interface OnDialogResultListener {
        public abstract void onResult(String tag, int index);
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
