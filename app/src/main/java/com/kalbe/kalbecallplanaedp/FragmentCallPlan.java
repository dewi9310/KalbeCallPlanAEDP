package com.kalbe.kalbecallplanaedp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.clsListItemAdapter;
import com.kalbe.kalbecallplanaedp.Utils.IOBackPressed;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.Helper.clsMainActivity;
import com.kalbe.mobiledevknlibs.Maps.PopUpMaps;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.UriData;
import com.kalbe.mobiledevknlibs.ToastAndSnackBar.ToastCustom;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by Dewi Oktaviani on 10/3/2018.
 */

public class FragmentCallPlan extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, IOBackPressed{
    View v;
    private Button btnCheckin, btnViewMap, btnRefreshMap;
    private ImageView imgCamera1, imgCamera2;
    private TextView tvLongUser, tvLongOutlet, tvLatUser, tvLatOutlet,tvAcc, tvDistance;
    private EditText etDesc, etOutlet, etBranch, etDate;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private double mlongitude = 0;
    private double mlatitude = 0;
    Options options;
    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE2_REQUEST_CODE = 130;
    private static final String IMAGE_DIRECTORY_NAME = "Image Activity";
    private String DT_CALL_PLAN = "dtCallPlan";
    Bundle dataHeader;
    private String fileName;
    private Toolbar toolbar;
    clsListItemAdapter dtTesting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.call_plan_fragment, container, false);

        btnCheckin = (Button) v.findViewById(R.id.buttonCheckIn);
        btnViewMap = (Button) v.findViewById(R.id.btnViewMap);
        btnRefreshMap = (Button) v.findViewById(R.id.btnRefreshMaps);
        imgCamera1 = (ImageView) v.findViewById(R.id.imageViewCamera1);
        imgCamera2 = (ImageView) v.findViewById(R.id.imageViewCamera2);
        tvLongUser = (TextView) v.findViewById(R.id.tvLong);
        tvLatUser = (TextView) v.findViewById(R.id.tvLat);
        tvLongOutlet = (TextView) v.findViewById(R.id.tvLongOutlet);
        tvLatOutlet = (TextView) v.findViewById(R.id.tvlatOutlet);
        tvAcc = (TextView) v.findViewById(R.id.tvAcc);
        tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        etBranch = (EditText) v.findViewById(R.id.etBranch);
        etOutlet = (EditText) v.findViewById(R.id.etOutlet);
        etDesc = (EditText) v.findViewById(R.id.etDesc);
        etDate = (EditText) v.findViewById(R.id.etDate);
        toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);

        dataHeader = getArguments();

        dtTesting = (clsListItemAdapter) dataHeader.getSerializable(DT_CALL_PLAN);
        etDate.setText(dtTesting.getTxtDate());
        etDesc.setText(dtTesting.getTxtSubTittle());
        options = new Options();
        options.inSampleSize = 2;

        tvLongUser.setText("");
        tvLatUser.setText("");
        tvLongOutlet.setText("");
        tvLatOutlet.setText("");
        tvAcc.setText("");
        tvDistance.setText("");
        tvLongOutlet.setText("106.814095");
        tvLatOutlet.setText("-6.300641");

        getLocation();

        if (mLastLocation!=null){
            displayLocation(mLastLocation);
        }

        if (checkPlayServices()){
            buildGoogleApiClient();
        }

        btnRefreshMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                if (mLastLocation == null){
                    displayLocation(mLastLocation);
                }
            }
        });
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopUpMaps().popUpMapsTwoCoordinates(getContext(), R.layout.popup_map, tvLatOutlet.getText().toString(), tvLongOutlet.getText().toString());
//                new PopUpMaps().popUpMapsTwoCoordinates(getActivity(), R.layout.popup_map, "-6.300641", "106.814095");


            }
        });

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileName = "temp_absen" + timeStamp;
        imgCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImage.CaptureImage(getActivity(), new clsHardCode().txtFolderCheckIn, fileName,CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
            }
        });
        imgCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImage.CaptureImage(getActivity(), new clsHardCode().txtFolderCheckIn, fileName,CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA_CAPTURE_IMAGE1_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = UriData.getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderCheckIn, fileName);
                //untuk mendapatkan bitmap bisa menggunakan decode stream
                Bitmap bitmap = PickImage.decodeStreamReturnBitmap(getContext(), uri);
                //get byte array
                byte[] save = PickImage.getByteImageToSave(getContext(), uri);
//                ToastCustom.showToastDefault(this, "data yang tersimpan : " + save);
                PickImage.previewCapturedImage(imgCamera1, bitmap, 150, 150);
            }else if (resultCode == 0) {
               new  clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
              new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }else if (requestCode== CAMERA_CAPTURE_IMAGE2_REQUEST_CODE){
            if (resultCode==-1){
                Uri uri = UriData.getOutputMediaImageUri(getContext(), new clsHardCode().txtFolderCheckIn, fileName);
                //untuk mendapatkan bitmap bisa menggunakan decode stream
                Bitmap bitmap = PickImage.decodeStreamReturnBitmap(getContext(), uri);
                //get byte array
                byte[] save = PickImage.getByteImageToSave(getContext(), uri);
//                ToastCustom.showToastDefault(this, "data yang tersimpan : " + save);
                PickImage.previewCapturedImage(imgCamera2, bitmap, 150, 150);
            }else if (resultCode == 0) {
                new  clsMainActivity().showCustomToast(getContext(), "User canceled photo", false);
            } else {
                new clsMainActivity().showCustomToast(getContext(), "Something error", false);
            }
        }

    }

    // get location GPS
    private boolean earlyState = true;
    boolean mockStatus = false;
    public Location getLocation() {
        try {
//            Drawable icon = getResources().getDrawable(R.mipmap.ic_error_outline);
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                ToastCustom.showToasty(getContext(),"Please turn on GPS or check your internet connection",4);
//                new clsMainActivity().showCustomToast(getContext(), "Please turn on GPS or check your internet connection", false);
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ToastCustom.showToasty(getContext(),"Please check application permissions",4);
//                        _clsMainActivity.showCustomToast(getContext(), "Please check application permissions", false);
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                        mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                } else {
                    ToastCustom.showToasty(getContext(),"Please check your connection",4);
//                    _clsMainActivity.showCustomToast(getContext(), "Please check your connection", false);
                }

                if (isGPSEnabled && mLastLocation==null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
                    mLastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                } else if(!isGPSEnabled){
                    ToastCustom.showToasty(getContext(),"Please check your connection",4);
//                    _clsMainActivity.showCustomToast(getContext(), "Please check your connection", false);
                }
            }

            if (mLastLocation != null) {
                int intOs = Integer.valueOf(android.os.Build.VERSION.SDK);
                if (intOs >= 18) {
                    mockStatus = mLastLocation.isFromMockProvider();
                }
            }
            if (mockStatus){
                ToastCustom.showToastyCustom(getContext(), Html.fromHtml("<b>" + "Fake GPS detected ! " + "</b> "+ "<br/>" + "<br/>" +"Please Turn Off Fake Location, And Restart Your Phone"), getResources().getDrawable(R.mipmap.ic_error_outline), getResources().getColor(R.color.red_600), 10, false, true);
//                ToastCustom.showToasty(getContext(),"Fake GPS detected ! " + "\n" + "Please Turn Off Fake Location, And Restart Your Phone",2);
                Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(5000);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if(mLastLocation!=null&&!earlyState){
            new clsMainActivity().showCustomToast(getContext(), "Location Updated", true);
        }
        earlyState = false;
        return mLastLocation;
    }


    @SuppressWarnings("deprecation")
    //set text view long lat
    private void displayLocation(Location mLastLocation) {
        DecimalFormat df = new DecimalFormat("#.##");

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            double accurate = mLastLocation.getAccuracy();

            tvLongUser.setText(String.format("%s", longitude));
            tvLatUser.setText(String.format("%s", latitude));
            tvAcc.setText(String.format("%s", df.format(accurate)));

            try {
                float distance = countDistance(latitude, longitude);
                tvDistance.setText(String.format("%s meters", String.valueOf((int) Math.ceil(distance))));
            } catch (Exception ignored) {

            }

            mlongitude = longitude;
            mlatitude = latitude;

        } else {
            tvLatUser.setText("");
            tvLongUser.setText("");
            tvAcc.setText("");
            tvDistance.setText("");
        }

    }

    // count distance
    private float countDistance(double latitude, double longitude) {
        float distance;

//        double latitudeOutlet = Double.parseDouble(HMoutletLat.get(spnOutlet.getSelectedItem().toString()));
//        double longitudeOutlet = Double.parseDouble(HMoutletLang.get(spnOutlet.getSelectedItem().toString()));

        double latitudeOutlet = Double.parseDouble(tvLatOutlet.getText().toString());
        double longitudeOutlet = Double.parseDouble(tvLongOutlet.getText().toString());

        Location locationA = new Location("point user");

        locationA.setLatitude(latitude);
        locationA.setLongitude(longitude);

        Location locationB = new Location("point outlet");

        locationB.setLatitude(latitudeOutlet);
        locationB.setLongitude(longitudeOutlet);

        distance = locationA.distanceTo(locationB);

        tvDistance.setText(String.format("%s meters", String.valueOf((int) Math.ceil(distance))));

        return distance;
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @SuppressWarnings("deprecation")
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation(mLastLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onBackPressed() {
        Tools.intentFragment(FragmentListCallPlan.class, "Call Plan", getContext());
        return true;
    }
}
