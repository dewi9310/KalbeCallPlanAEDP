package com.kalbe.kalbecallplanaedp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

public class FragmentAddUnplan extends Fragment implements IOBackPressed{
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_unplan, container, false);
        return v;
    }

    @Override
    public boolean onBackPressed() {
        Tools.intentFragment(FragmentListCallPlan.class, "Call Plan", getContext());
        return true;
    }
}
