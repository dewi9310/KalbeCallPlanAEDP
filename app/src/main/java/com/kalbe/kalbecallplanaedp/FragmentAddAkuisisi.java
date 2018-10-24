package com.kalbe.kalbecallplanaedp;


import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.kalbe.kalbecallplanaedp.BL.clsActivity;
import com.kalbe.kalbecallplanaedp.BL.clsMainBL;
import com.kalbe.kalbecallplanaedp.Common.mActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mSubSubActivity;
import com.kalbe.kalbecallplanaedp.Common.mUserLogin;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsListImageAdapter;
import com.kalbe.kalbecallplanaedp.Repo.mActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mSubSubActivityRepo;
import com.kalbe.kalbecallplanaedp.Repo.mUserLoginRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.SpacingItemDecoration;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.kalbecallplanaedp.adapter.RecyclerGridImageAdapter;
import com.kalbe.mobiledevknlibs.AlertDialog.CustomDatePicker;
import com.kalbe.mobiledevknlibs.AlertDialog.clsDatePicker;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.Spinner.SpinnerCustom;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by Dewi Oktaviani on 10/9/2018.
 */

public class FragmentAddAkuisisi extends Fragment implements IOBackPressed{

    private RecyclerView lv_akuisisi;
    private RecyclerGridImageAdapter adapter;
    private AppCompatSpinner spnAddSubAkuisisi;
    List<clsListImageAdapter> listImage = new ArrayList<>();
    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private String fileName, selectedSubAkuisisi;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_DIRECTORY = "zoom directory";
    private String SUB_SUB_ACTIVITY = "sub sub activity";
    View v;
    public List<String> NamaTab = new ArrayList<>();
    public HashMap<String, Integer> MapTab = new HashMap<>();
    String txtSubSubActivity;
    TextInputEditText etDtExpired, etNoDoc;
    tAkuisisiDetailRepo dtDetailRepo;
    tAkuisisiHeaderRepo dtHeaderRepo;
    mUserLogin dtUserLogin;
    tAkuisisiHeader dtHeader = new tAkuisisiHeader();
    tAkuisisiDetail dtDetail = new tAkuisisiDetail();
    List<tAkuisisiDetail> listDetail = new ArrayList<>();
    mSubActivity _mSubActivity;
    List<mSubSubActivity> _mSubSubActivity;
    mActivity _mActivity;
    mSubSubActivityRepo subSubActivityRepo;
    mSubActivityRepo subActivityRepo;
    mActivityRepo activityRepo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_akuisisi, container, false);
        lv_akuisisi = (RecyclerView) v.findViewById(R.id.lv_akuisisi);
        spnAddSubAkuisisi = (AppCompatSpinner) v.findViewById(R.id.spn_add_sub_akuisisi);
        etDtExpired = (TextInputEditText) v.findViewById(R.id.et_exp_date);

        etNoDoc = (TextInputEditText) v.findViewById(R.id.et_no_doc);

        dtDetailRepo = new tAkuisisiDetailRepo(getContext());
        dtHeaderRepo = new tAkuisisiHeaderRepo(getContext());
        dtUserLogin = new clsMainBL().getUserLogin(getContext());
        lv_akuisisi.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        lv_akuisisi.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getActivity(), 3), true));
        lv_akuisisi.setHasFixedSize(true);


        subSubActivityRepo = new mSubSubActivityRepo(getContext());
        try {
            _mSubSubActivity  = (List<mSubSubActivity>) subSubActivityRepo.findBySubActivityIdAndTypeId(1, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (_mSubSubActivity!=null&&_mSubSubActivity.size()>0){
            NamaTab.add("Select One");
            MapTab.put("Select One", 0);
            for (int i = 0; i < _mSubSubActivity.size(); i++){
                NamaTab.add(_mSubSubActivity.get(i).getTxtName());
                MapTab.put(_mSubSubActivity.get(i).getTxtName(), _mSubSubActivity.get(i).getIntSubSubActivityid());
            }
        }

        // Initializing an ArrayAdapter with initial text like select one
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_spinner_dropdown_item, NamaTab){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spnAddSubAkuisisi.setAdapter(spinnerArrayAdapter);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            txtSubSubActivity = bundle.getString(SUB_SUB_ACTIVITY);
            int position = spinnerArrayAdapter.getPosition(txtSubSubActivity);
            spnAddSubAkuisisi.setSelection(position);
            setAdapterAkusisi();
        }

        // attaching data adapter to spinner
        spnAddSubAkuisisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubAkuisisi = spnAddSubAkuisisi.getSelectedItem().toString();
                txtSubSubActivity = selectedSubAkuisisi;
                onItemSpinnerSelected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ToastCustom.showToasty(getActivity(),"Please select Sub Akuisisi",4);
                // put code here
            }
        });
        final FabSpeedDial fabSpeedDial = (FabSpeedDial) v.findViewById(R.id.fabView);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch(menuItem.getItemId()){

                    case R.id.action_add:
                        if (MapTab.get(spnAddSubAkuisisi.getSelectedItem())==0){
                            ToastCustom.showToasty(getContext(), "Please select type of akuisisi", 4);
                        }else {
                            if (dtHeader==null){

                                tAkuisisiHeader dt = new tAkuisisiHeader();
                                dt.setTxtHeaderId(new clsActivity().GenerateGuid());
                                dt.setDtExpiredDate(parseDateTime(etDtExpired.getText().toString()));
                                dt.setTxtNoDoc(etNoDoc.getText().toString());
                                dt.setIntFlagPush(new clsHardCode().Draft);
                                dt.setIntSubSubActivityId(MapTab.get(txtSubSubActivity));
                                dt.setIntUserId(dtUserLogin.getIntUserID());
                                try {
                                    dtHeaderRepo.createOrUpdate(dt);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
//                            dt.setIntSubSubActivityTypeId();
                            }
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            fileName = "temp_akuisisi" + timeStamp;
                            PickImage.CaptureImage(getActivity(), new clsHardCode().txtFolderAkuisisi, fileName,CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
                        }
                        return true;

                    case R.id.action_save:
                        if (lv_akuisisi.getChildCount()==0){
                            ToastCustom.showToasty(getContext(), "Please take at least one picture", 4);
                        }else if (etDtExpired.getText().toString().equals("")){
                        ToastCustom.showToasty(getContext(), "Please select the date", 4);
                        }else if (etNoDoc.getText().toString().equals("")){
                        ToastCustom.showToasty(getContext(), "Please fill number of document", 4);
                        }else if (MapTab.get(spnAddSubAkuisisi.getSelectedItem())==0){
                            ToastCustom.showToasty(getContext(), "Please select type of akuisisi", 4);
                        }else {
                            tAkuisisiHeader dt = new tAkuisisiHeader();
                            dt.setTxtHeaderId(dtHeader.getTxtHeaderId());
//                            dt.setDtExpiredDate(etDtExpired.getText().toString());
                            dt.setDtExpiredDate(parseDateTime(etDtExpired.getText().toString()));
                            dt.setTxtNoDoc(etNoDoc.getText().toString());
                            dt.setIntFlagPush(new clsHardCode().Save);
                            dt.setIntSubSubActivityId(MapTab.get(txtSubSubActivity));
                            dt.setIntUserId(dtUserLogin.getIntUserID());
                            try {
                                dtHeaderRepo.createOrUpdate(dt);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        ToastCustom.showToasty(getContext(), "Saved", 1);
                            Bundle bundle = new Bundle();
                            bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
                            Tools.intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), bundle);

                        }

                        return true;

                    default:
                        return false;
                }
            }
        });

//        //set bundle
        Calendar c = Calendar.getInstance();
        final Bundle args = new Bundle();
        args.putInt(CustomDatePicker.YEAR, c.get(Calendar.YEAR));
        args.putInt(CustomDatePicker.MONTH, c.get(Calendar.MONTH));
        args.putInt(CustomDatePicker.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
        args.putLong(CustomDatePicker.DATE_MIN, c.getTimeInMillis());
        //set hint for date
        CustomDatePicker.showHint(etDtExpired, args, CustomDatePicker.format.standard1);

        etDtExpired.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etDtExpired) {
            @Override
            public boolean onDrawableClick() {
//                clsDatePicker.showDatePicker(getContext(), etDtExpired, "Select Date", args, clsDatePicker.format.standard1, AlertDialog.THEME_HOLO_LIGHT);
                CustomDatePicker.showDatePicker(getContext(), etDtExpired, "Expired Date", CustomDatePicker.format.standard1, args);
//                dialogDatePickerLight();
                return false;
            }
        });

        return v;
    }

    private String parseDateTime(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            if (dateExp!=null&& dateExp!="")
            date = dateFormat.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return sdf.format(date);
        }else {
            return "";
        }
    }

    private String parseDate(String dateExp){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            if (dateExp!=null&& dateExp!="")
            date = sdf.parse(dateExp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return dateFormat.format(date);
        }else {
            return "";
        }
    }

    private void dialogDatePickerLight() {
        Calendar cur_calender = Calendar.getInstance();
        DatePickerDialog datePicker = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        long date_ship_millis = calendar.getTimeInMillis();
                        etDtExpired.setText(Tools.getFormattedDateSimple(date_ship_millis));
                    }
                },
                cur_calender.get(Calendar.YEAR),
                cur_calender.get(Calendar.MONTH),
                cur_calender.get(Calendar.DAY_OF_MONTH)
        );
        //set dark light
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.green_300));
        datePicker.setMinDate(cur_calender);
        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    private void setAdapterAkusisi(){
        List<tAkuisisiDetail> listDetail = null;
        try {
            dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
            if (dtHeader!=null){
                etDtExpired.setText(parseDate(dtHeader.getDtExpiredDate()));
                etNoDoc.setText(dtHeader.getTxtNoDoc());
                listDetail =  (List<tAkuisisiDetail>) dtDetailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
            }else {
                etDtExpired.setText("");
                etNoDoc.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listImage.clear();
        if (listDetail !=null){
            if (listDetail.size()>0){
                for (tAkuisisiDetail dt : listDetail){
                    clsListImageAdapter data = new clsListImageAdapter();
                    data.setTxtId(dt.getTxtDetailId());
                    data.setBlobImg(dt.getTxtImg());
                    data.setTxtImgName(dt.getTxtImgName());
                    listImage.add(data);
                }
            }
        }
        adapter = new RecyclerGridImageAdapter(getActivity(), listImage);
        lv_akuisisi.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final clsListImageAdapter obj, int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirm");
                builder.setMessage("Are You sure to delete ?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            tAkuisisiDetail dt = (tAkuisisiDetail) dtDetailRepo.findByDetailId(obj.getTxtId());
                            dtDetailRepo.delete(dt);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        listImage.remove(obj);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        adapter.setOnImageClickListener(new RecyclerGridImageAdapter.OnImageClickListener() {
            @Override
            public void onItemClick(View view, clsListImageAdapter obj, int position) {
                Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
                intent1.putExtra(ZOOM_DIRECTORY, new clsHardCode().txtFolderAkuisisi);
                intent1.putExtra(ZOOM_IMAGE, obj.getTxtId());
                startActivity(intent1);
            }
        });
    }

    private void onItemSpinnerSelected(){
        List<tAkuisisiDetail> listDetail = null;
        try {
            dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
            if (dtHeader!=null){
//                etDtExpired.setText(dtHeader.getDtExpiredDate());
                etDtExpired.setText(parseDate(dtHeader.getDtExpiredDate()));
                etNoDoc.setText(dtHeader.getTxtNoDoc());
                listDetail =  (List<tAkuisisiDetail>) dtDetailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
            }else {
                etDtExpired.setText("");
                etNoDoc.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listImage.clear();
        if (listDetail !=null){
            if (listDetail.size()>0){
                for (tAkuisisiDetail dt : listDetail){
                    clsListImageAdapter data = new clsListImageAdapter();
                    data.setTxtId(dt.getTxtDetailId());
                    data.setBlobImg(dt.getTxtImg());
                    data.setTxtImgName(dt.getTxtImgName());
                    listImage.add(data);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_CAPTURE_IMAGE1_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = UriData.getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderAkuisisi, fileName);
                //get byte array
                byte[] save = getByteImageToSave(getContext(), uri);
                PickImage.decodeByteArraytoImageFile(save, new clsHardCode().txtPathTempData);
                try {
                    dtHeader = (tAkuisisiHeader) dtHeaderRepo.findBySubSubId(MapTab.get(txtSubSubActivity), new clsHardCode().Draft);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tAkuisisiDetail dt = new tAkuisisiDetail();
                dt.setTxtDetailId(new clsActivity().GenerateGuid());
                dt.setTxtHeaderId(dtHeader.getTxtHeaderId());
                dt.setTxtImg(save);
                dt.setTxtImgName(fileName);
                try {
                    dtDetailRepo.createOrUpdate(dt);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clsListImageAdapter dtImg = new clsListImageAdapter();
                dtImg.setTxtId(dt.getTxtDetailId());
                dtImg.setBlobImg(dt.getTxtImg());
                dtImg.setTxtImgName(fileName);
                listImage.add(dtImg);
                adapter.notifyDataSetChanged();
            }else if (resultCode == 0) {
                new  clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
                new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString(SUB_SUB_ACTIVITY, txtSubSubActivity);
        Tools.intentFragmentSetArgument(FragmentAkuisisi.class, "Akuisisi", getContext(), bundle);
        return true;
    }

    public static byte[] getByteImageToSave(Context context, Uri uri) {
        byte[] imgPhoto = null;
        try {
            Bitmap bitmap = PickImage.decodeStreamReturnBitmap(context, uri);
            ExifInterface exif = null;
            String path = uri.toString();
            if (path.startsWith("file://")) {
                exif = new ExifInterface(path);
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (path.startsWith("content://")) {
                    InputStream inputStream = context.getContentResolver().openInputStream(uri);
                    exif = new ExifInterface(inputStream);
                }
            }

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);

            ByteArrayOutputStream output = null;

            try {
                output = new ByteArrayOutputStream();
                rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            } catch (Exception var15) {
                var15.printStackTrace();
            } finally {
                try {
                    if(output != null) {
                        output.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

            }

            imgPhoto = output.toByteArray();
        } catch (NullPointerException var17) {
            var17.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imgPhoto;
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
}
