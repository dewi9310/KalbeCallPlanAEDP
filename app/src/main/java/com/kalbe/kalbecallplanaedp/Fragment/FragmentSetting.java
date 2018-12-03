package com.kalbe.kalbecallplanaedp.Fragment;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.BL.clsHelperBL;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.ChangePasswordActivity;
import com.kalbe.kalbecallplanaedp.Common.VMUploadFoto;
import com.kalbe.kalbecallplanaedp.Common.clsPhotoProfile;
import com.kalbe.kalbecallplanaedp.Common.clsPushData;
import com.kalbe.kalbecallplanaedp.Common.clsToken;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Data.VolleyResponseListener;
import com.kalbe.kalbecallplanaedp.Data.VolleyUtils;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.ImageViewerActivity;
import com.kalbe.kalbecallplanaedp.LoginActivity;
import com.kalbe.kalbecallplanaedp.MainMenu;
import com.kalbe.kalbecallplanaedp.R;

import com.kalbe.kalbecallplanaedp.Repo.clsPhotoProfilRepo;
import com.kalbe.kalbecallplanaedp.Repo.clsTokenRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.tLogErrorRepo;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.PushLogError.PushLogError;
import com.kalbe.kalbecallplanaedp.ResponseDataJson.loginMobileApps.LoginMobileApps;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.PermissionChecker.PermissionChecker;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_AUTH_TYPE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.PARAM_USER_PASS;


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
    List<clsToken> dataToken;
    clsTokenRepo tokenRepo;
    private Gson gson;
    mUserLoginRepo loginRepo;
    ProgressDialog pDialog;
    mUserLogin dtLogin;
    MainMenu mm;
    private String ZOOM_PROFILE = "photo profil";
    LinearLayout ln_error, ln_change_ps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);
        ivProfile = (CircularImageView) v.findViewById(R.id.image_setting);
        fab = (FloatingActionButton)v.findViewById(R.id.fab_add_img_setting);
        ln_error = (LinearLayout)v.findViewById(R.id.ln_push_error);
        ln_change_ps = (LinearLayout)v.findViewById(R.id.ln_change_ps);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageProfile();
            }
        });
        mm = (MainMenu)getActivity();
        dtLogin = new clsMainBL().getUserLogin(getContext());
        if (dtLogin.getBlobImg()!=null){
        Bitmap bitmap = PickImage.decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
        PickImage.previewCapturedImage(ivProfile, bitmap, 200, 200);
        }
        pDialog = new ProgressDialog(getContext());
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
                intent1.putExtra(ZOOM_PROFILE, dtLogin.getTxtGuID());
                startActivity(intent1);
            }
        });
        ln_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    pushDataError();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        ln_change_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
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
                    uriImage = getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderData, "tmp_act");
                    PickImage.CaptureImage(getActivity(), new clsHardCode().txtFolderData, "tmp_act",CAMERA_REQUEST_PROFILE );
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
//            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            phtProfile = output.toByteArray();
//            byte[] save = PickImage.getByteImageToSaveRotate(getContext(), uriImage);
//            dtLogin.setBlobImg(save);
//            dtLogin.setTxtFileName("tmp_act");
//            ivProfile.setImageBitmap(photo_view);
            dtLogin.setBlobImg(phtProfile);
            dtLogin.setTxtFileName("tmp_act");
            changeProfile(dtLogin);
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
        getActivity().startActivityForResult(pickPhoto , SELECT_FILE_PROFILE);//one can be replaced with any action code
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
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 200);
            cropIntent.putExtra("scale", true);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            getActivity().startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public Uri getOutputMediaImageUri(Context context, String folderName, String fileName) {
        return Uri.fromFile(getOutputMediaFile());
    }
    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new clsHardCode().txtFolderData + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_act" + ".png");
        return mediaFile;
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
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 200);
            cropIntent.putExtra("scale", true);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            getActivity().startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title",null);
        return Uri.parse(path);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_PROFILE) {
            if (resultCode == -1) {
                try {
                    //untuk mendapatkan bitmap bisa menggunakan decode stream
//                    Bitmap bitmap = PickImage.decodeStreamReturnBitmap(getContext(), uriImage);
                    //get byte array
//                    byte[] save = PickImage.getByteImageToSaveRotate(getContext(), uriImage);
//                    uriImage = getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderData, "tmp_act");
//                    PickImage.decodeByteArraytoImageFile(save, new clsHardCode().txtFolderData, "tmp_act");
//                    dtLogin.setBlobImg(save);
//                    dtLogin.setTxtFileName("tmp_act");
                    Bitmap bmCameraCapture = BitmapFactory.decodeFile(uriImage.getPath());
                    Bitmap rotateBitmap =  new PickImage().rotateBitmap(bmCameraCapture, uriImage.getPath());
                    uriImage = getImageUri(getContext(), rotateBitmap);
//                    String tes = "haha";
//                    Uri uri = data.getData();
                    performCropProfile();
//                    changeProfile(dtLogin);
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
//                byte[] save = PickImage.getByteImageToSaveRotate(getContext(), uriImage);
//                dtLogin.setBlobImg(save);
//                dtLogin.setTxtFileName("tmp_act");
//                changeProfile(dtLogin);
                previewCaptureImageProfile(thePic);
            } else if (resultCode == 0) {
                new clsMainActivity().showCustomToast(getContext(), "User cancel take image", false);
            }
        }
        else if (requestCode == SELECT_FILE_PROFILE) {
            if(resultCode == RESULT_OK){
                try {
//                    Bitmap bitmap;
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    selectedImage = data.getData();
//                    byte[] save = PickImage.getByteImageToSaveRotate2(getContext(), selectedImage);
//                    dtLogin.setBlobImg(save);
//                    dtLogin.setTxtFileName("tmp_act");
//                    changeProfile(dtLogin);
                    performCropGalleryProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    private byte[] getByteArrayImage(String url){
//        try {
//            URL imageUrl = new URL(url);
//            URLConnection ucon = imageUrl.openConnection();
//
//            InputStream is = ucon.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//
//            ByteArrayBuffer baf = new ByteArrayBuffer(500);
//            int current = 0;
//            while ((current = bis.read()) != -1) {
//                baf.append((byte) current);
//            }
//
//            return baf.toByteArray();
//        } catch (Exception e) {
//            Log.d("ImageManager", "Error: " + e.toString());
//        }
//        return null;
//    }

    private void changeProfile(final mUserLogin dataLogin) {
        pDialog.setMessage("Please wait....");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        String strLinkAPI = new clsHardCode().linkChangeProfil;
        JSONObject resJson = new JSONObject();

        try {
            tokenRepo = new clsTokenRepo(getContext());
            dataToken = (List<clsToken>) tokenRepo.findAll();
            VMUploadFoto dataUp = new VMUploadFoto();
            dataUp.setIntRoleId(dataLogin.getIntRoleID());
            dataUp.setIntUserId(dataLogin.getIntUserID());
            JSONObject userData = new JSONObject();
            userData.put("intUserId",dataLogin.getIntUserID());
            userData.put("intRoleId",dataLogin.getIntRoleID());
            resJson.put("data", userData);
            resJson.put("device_info", new clsHardCode().pDeviceInfo());
            resJson.put("txtRefreshToken", dataToken.get(0).txtRefreshToken.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();

        new VolleyUtils().changeProfile(getContext(), strLinkAPI, mRequestBody, pDialog,  dataLogin, new VolleyResponseListener() {
            @Override
            public void onError(String message) {
                ToastCustom.showToasty(getContext(),message,4);
                pDialog.dismiss();
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                Intent res = null;
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        LoginMobileApps model = gson.fromJson(jsonObject.toString(), LoginMobileApps.class);
                        boolean txtStatus = model.getResult().isStatus();
                        String txtMessage = model.getResult().getMessage();
                        String txtMethode_name = model.getResult().getMethodName();

                        String accessToken = "dummy_access_token";

                        if (txtStatus == true){
                            loginRepo = new mUserLoginRepo(getContext());
                            mUserLogin data = dataLogin;
                            loginRepo.createOrUpdate(data);
                            dtLogin = new clsMainBL().getUserLogin(getContext());
                            Bitmap bitmap = PickImage.decodeByteArrayReturnBitmap(dtLogin.getBlobImg());
                            PickImage.previewCapturedImage(ivProfile, bitmap, 200, 200);
                            PickImage.previewCapturedImage(mm.ivProfile, bitmap, 200, 200);
                            pDialog.dismiss();
                            ToastCustom.showToasty(getContext(),"Success Change photo profile",1);

                        } else {
                            ToastCustom.showToasty(getContext(),txtMessage,4);
                            pDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else {
                    ToastCustom.showToasty(getContext(),strErrorMsg,4);
                    pDialog.dismiss();
                }
            }
        });
    }

    private void pushDataError() throws JSONException {
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Pushing Data");
//        pDialog.setTitle("Pushing Your data");
        pDialog.setMessage("Push your data....");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        final clsPushData dtJson = new clsHelperBL().pushDataError(versionName, getContext());
        if (dtJson == null){
        }else {
            String linkPushData = new clsHardCode().linkPushDataError;
            new VolleyUtils().makeJsonObjectRequestPushError(getContext(), linkPushData, dtJson, pDialog, new VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    ToastCustom.showToasty(getContext(),message,4);
//                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response!=null){
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            PushLogError model = gson.fromJson(jsonObject.toString(), PushLogError.class);
                            boolean isStatus = model.getResult().isStatus();
                            String txtMessage = model.getResult().getMessage();
                            String txtMethod = model.getResult().getMethodName();
                            if (isStatus==true){
                                if (dtJson.getDataError().getListOfDatatLogError()!=null){
                                    if (dtJson.getDataError().getListOfDatatLogError().size()>0){
                                        for (int i = 0; i < dtJson.getDataError().getListOfDatatLogError().size(); i++){
                                            new tLogErrorRepo(getContext()).delete(dtJson.getDataError().getListOfDatatLogError().get(i));
                                        }
                                    }
                                }
//                                btn_push_error.setVisibility(View.GONE);
                                ToastCustom.showToasty(getContext(),"Success Push Data",1);

//                                if (myValue!=null){
//                                    if (myValue.equals("notMainMenu")){
//                                        //logout
//                                        logout();
//                                    }
//                                }
                            }else {
//                                btn_push_error.setVisibility(View.VISIBLE);
                                ToastCustom.showToasty(getContext(),txtMessage, 4);
                            }

                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else {
                        ToastCustom.showToasty(getContext(),strErrorMsg,4);
                        pDialog.dismiss();
                    }
                }
            });
        }
    }
}
