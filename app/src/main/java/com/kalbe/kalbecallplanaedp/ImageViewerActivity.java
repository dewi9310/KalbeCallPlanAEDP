package com.kalbe.kalbecallplanaedp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Utils.TouchImageView;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickFile;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImageCustom;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class ImageViewerActivity extends AppCompatActivity {

    private TouchImageView imageView;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_DIRECTORY = "zoom directory";
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
           tAkuisisiDetailRepo detailRepo = new tAkuisisiDetailRepo(getApplicationContext());
            tAkuisisiDetail data = null;
            try {
                data = (tAkuisisiDetail) detailRepo.findByDetailId(bundle.getString(ZOOM_IMAGE));
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            Uri uri = UriData.getOutputMediaImageUri(ImageViewerActivity.this, bundle.getString(ZOOM_DIRECTORY), bundle.getString(ZOOM_IMAGE));
            Bitmap bitmap = PickImage.decodeByteArrayReturnBitmap(data.getTxtImg());
//            Bitmap bitmap = PickImage.decodeStreamReturnBitmap(getApplicationContext(), uri);
            ExifInterface exif = null;
//            String path = uri.toString();
//            try {
//                if (path.startsWith("file://")) {
//                    exif = new ExifInterface(path);
//                }
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    if (path.startsWith("content://")) {
//                        InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
//                        exif = new ExifInterface(inputStream);
//                    }
//                }
//
//                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//                Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);
//                imageView.setImageBitmap(rotatedBitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        Intent parentIntent = NavUtils.getParentActivityIntent(this);
        parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(parentIntent);
        finish();
    }
}
