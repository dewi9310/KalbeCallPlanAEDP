package com.kalbe.kalbecallplanaedp;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Dewi Oktaviani on 10/9/2018.
 */

public class FragmentAddAkuisisi extends DialogFragment {

    public CallBackResult callBackResult;

    public void setOnCallBackResult(final  CallBackResult callBackResult){
        this.callBackResult = callBackResult;
    }

    private int request_code = 0;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_akuisisi, container, false);

        ((ImageButton) v.findViewById(R.id.btn_close_add_akuisisi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((Button) v.findViewById(R.id.btn_save_add_akuisisi)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        AppBarLayout appBarLayout = (AppBarLayout) v.findViewById(R.id.appbar_add_akuisisi);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green_300));
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }

        return v;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(dialog.getWindow().getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        AppBarLayout appBarLayout = (AppBarLayout) dialog.findViewById(R.id.appbar_add_akuisisi);
//        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green_300));
//
//        dialog.getWindow().setAttributes(lp);
//        return dialog;
//    }


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.FullScreenDialogStyle);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            dialog.getWindow().setLayout(width, height);
//        }
//    }

    public interface CallBackResult{
        void sendResult(int requestCode, Object obj);
    }

    public void setRequestCode(int request_code){
        this.request_code = request_code;
    }
}
