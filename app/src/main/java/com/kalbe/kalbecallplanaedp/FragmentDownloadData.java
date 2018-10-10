package com.kalbe.kalbecallplanaedp;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;

/**
 * Created by Dewi Oktaviani on 9/26/2018.
 */

public class FragmentDownloadData extends Fragment{
    View v;

    private LinearLayout ln_branch_downlaod, ln_outlet_download;
    private  TextView tv_download_branch, tv_download_outlet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_download_data, container, false);
        ln_branch_downlaod = (LinearLayout) v.findViewById(R.id.ln_branch_download);
        ln_outlet_download = (LinearLayout) v.findViewById(R.id.ln_outlet_download);
        tv_download_branch = (TextView) v.findViewById(R.id.tv_download_branch);
        tv_download_outlet = (TextView) v.findViewById(R.id.tv_download_outlet);
        ln_branch_downlaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onButtonOnClick(ln_branch_downlaod, tv_download_branch);
//                Toast.makeText(getContext(), "bisa di klik", Toast.LENGTH_SHORT).show();
            }
        });

        ln_outlet_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonOnClick(ln_outlet_download, tv_download_outlet);
            }
        });
        return v;
    }

    private  void onButtonOnClick(LinearLayout ln_click, TextView tv_click){
        RelativeLayout rl_download = (RelativeLayout) ln_click.getChildAt(0);
        ImageView img_download = (ImageView) rl_download.getChildAt(0);
        int color = ImageViewCompat.getImageTintList(img_download).getDefaultColor();
        String item =  tv_click.getText().toString();
        showCustomDialog(item, color);
    }
    private void showCustomDialog(String item, int color) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        AppBarLayout appBarLayout = (AppBarLayout) dialog.findViewById(R.id.appbar_download);
        final AppCompatSpinner spnDownload = (AppCompatSpinner) dialog.findViewById(R.id.spnDownload);
        final  TextView tv_download_title = (TextView) dialog.findViewById(R.id.tv_title_download);
        final  Button btn_download = (Button) dialog.findViewById(R.id.btn_download);
        Drawable drawable = getResources().getDrawable(R.drawable.btn_rounded_green_300);
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//        btn_download.setBackground(drawable);
        btn_download.setBackgroundColor(color);
        appBarLayout.setBackgroundColor(getResources().getColor(R.color.green_300));
        tv_download_title.setText(item);

        String[] timezones = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
        ArrayAdapter<String> array = new ArrayAdapter<>(getContext(), R.layout.simple_spinner_item, timezones);
        array.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spnDownload.setAdapter(array);
        spnDownload.setSelection(0);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Event event = new Event();
//                event.email = tv_email.getText().toString();
//                event.name = et_name.getText().toString();
//                event.location = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//                event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//                event.is_allday = cb_allday.isChecked();
//                event.timezone = spn_timezone.getSelectedItem().toString();
//                displayDataResult(event);
//
//                dialog.dismiss();
//            }
//        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
