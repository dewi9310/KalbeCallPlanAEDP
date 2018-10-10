package com.kalbe.kalbecallplanaedp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.kalbe.kalbecallplanaedp.Utils.TouchImageView;

public class ImageViewerActivity extends AppCompatActivity {

    private TouchImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.green_300));
        }
        imageView = (TouchImageView) findViewById(R.id.img_viewer);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), bundle.getInt("image"));
            imageView.setImageResource(bundle.getInt("image"));
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(parentIntent);
        finish();
    }
}
