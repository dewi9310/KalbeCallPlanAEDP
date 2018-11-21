package com.kalbe.kalbecallplanaedp.Fragment;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.Common.clsPhotoProfile;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.MainMenu;
import com.kalbe.kalbecallplanaedp.R;

import com.kalbe.kalbecallplanaedp.Repo.clsPhotoProfilRepo;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Dewi Oktaviani on 11/21/2018.
 */

public class FragmentSetting extends Fragment{
    View v;
    CircularImageView ivProfile;
    FloatingActionButton fab;
    private static final int CAMERA_REQUEST_PROFILE = 100;
    private static final String IMAGE_DIRECTORY_NAME = "Image Personal";
    final int SELECT_FILE_PROFILE = 150;
    private static Bitmap photoProfile, mybitmapImageProfile;
    private static byte[] phtProfile;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    final int PIC_CROP_PROFILE = 130;
    private Uri uriImage,  selectedImage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        ivProfile = (CircularImageView) v.findViewById(R.id.image_setting);
        fab = (FloatingActionButton)v.findViewById(R.id.fab_add_img_setting);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageProfile();
            }
        });

        return v;
    }


    private void selectImageProfile() {
        final CharSequence[] items = { "Ambil Foto", "Pilih dari Galeri",
                "Batal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= PermissionChecker.Utility.checkPermission(getContext());
                if (items[item].equals("Ambil Foto")) {
                    uriImage = UriData.getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderData, "tmp_act");
                    PickImage.CaptureImage(getContext(), new clsHardCode().txtFolderData, "tmp_act",CAMERA_REQUEST_PROFILE );
//                    if(result)
//                        captureImageProfile();
                } else if (items[item].equals("Pilih dari Galeri")) {
                    if(result)
                        galleryIntentProfile();
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

//    protected void viewImageProfile() {
//        try {
//            repoUserImageProfile = new clsPhotoProfilRepo(getApplicationContext());
//            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/data/data/KalbeFamily/tempdata/Foto_Profil");
//        folder.mkdir();
//
//        for (clsPhotoProfile imgDt : dataImageProfile){
//            final byte[] imgFile = imgDt.getTxtImg();
//            if (imgFile != null) {
//                mybitmapImageProfile = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
//                Bitmap bitmap = Bitmap.createScaledBitmap(mybitmapImageProfile, 150, 150, true);
//                ivProfile.setImageBitmap(bitmap);
//            }
//        }
//    }

    // preview image profile
    private void previewCaptureImageProfile(Bitmap photo){
        try {
            Bitmap bitmap = new clsActivity().resizeImageForBlob(photo);
            ivProfile.setVisibility(View.VISIBLE);
            output = null;
            try {
                output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null){
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            phtProfile = output.toByteArray();
            ivProfile.setImageBitmap(photo_view);

//            saveImageProfile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

//    protected void saveImageProfile() {
//        try {
//            repoUserImageProfile = new clsPhotoProfilRepo(getApplicationContext());
//            dataImageProfile = (List<clsPhotoProfile>) repoUserImageProfile.findAll();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        clsPhotoProfile data = new clsPhotoProfile();
//        data.setTxtGuiId("1");
//        data.setTxtDescription("Profile");
//        data.setTxtImg(phtProfile);
//
//        repoUserImageProfile.createOrUpdate(data);
//        Toast.makeText(getApplicationContext(), "Image Profile Saved", Toast.LENGTH_SHORT).show();
//    }

    private void galleryIntentProfile() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , SELECT_FILE_PROFILE);//one can be replaced with any action code
    }

    private void performCropProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void performCropGalleryProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(selectedImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_PROFILE) {
            if (resultCode == -1) {
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    Uri uri = UriData.getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderAkuisisi, "tmp_act");
//                    String uri = uriImage.getPath().toString();

//                    bitmap = PickImage.decodeStreamReturnBitmap(getContext(), uri);
//                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropProfile();

//                    previewCaptureImage2(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == 0) {
                new clsMainActivity().showCustomToast(getContext(), "User cancel take image", false);
            }  else {
                try {
                    photoProfile = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == PIC_CROP_PROFILE) {
            if (resultCode == -1) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");

                previewCaptureImageProfile(thePic);
            } else if (resultCode == 0) {
                new clsMainActivity().showCustomToast(getContext(), "User cancel take image", false);
            }
        }
        else if (requestCode == SELECT_FILE_PROFILE) {
            if(resultCode == RESULT_OK){
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    selectedImage = data.getData();
                    String uri = selectedImage.getPath().toString();
//                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropGalleryProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
