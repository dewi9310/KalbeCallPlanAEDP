package com.kalbe.kalbecallplanaedp.Fragment;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kalbe.kalbecallplanaedp.Common.mApotek;
import com.kalbe.kalbecallplanaedp.Common.mDokter;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiDetail;
import com.kalbe.kalbecallplanaedp.Common.tAkuisisiHeader;
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.ImageViewerActivity;
import com.kalbe.kalbecallplanaedp.Model.Image;
import com.kalbe.kalbecallplanaedp.R;
import com.kalbe.kalbecallplanaedp.Repo.mApotekRepo;
import com.kalbe.kalbecallplanaedp.Repo.mDokterRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiDetailRepo;
import com.kalbe.kalbecallplanaedp.Repo.tAkuisisiHeaderRepo;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImageCustom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubAkuisisi extends Fragment {
    View v;
    private ListView mListView;
//    private String txtSubAkuisisi;
    private int intTypeSubSubId;
//    int intSubSubId;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private String ZOOM_IMAGE = "zoom image";
    private String ZOOM_DIRECTORY = "zoom directory";
    private List<tAkuisisiDetail> dtDetail = new ArrayList<>();
    private tAkuisisiHeader dtHeader;
//    private tAkuisisiHeader dtHeader = new tAkuisisiHeader();
    tAkuisisiDetailRepo detailRepo;
    tAkuisisiHeaderRepo headerRepo;
    private TextView tvNoDoc, tvExpDate, tvOutlet, tvUserName;
    LinearLayout ln_resgistrasi, ln_image;
    FloatingActionButton fab;
    mDokterRepo dokterRepo;
    mDokter dokter;
    mApotek apotek;
    mApotekRepo apotekRepo;

    public FragmentSubAkuisisi(tAkuisisiHeader dtHeader, int intTypeSubSubId, FloatingActionButton fab){
        this.dtHeader = dtHeader;
        this.intTypeSubSubId = intTypeSubSubId;
        this.fab = fab;
    }

        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_akuisisi, container, false);
            layout_dots = (LinearLayout) v.findViewById(R.id.layout_dots);
            viewPager = (ViewPager) v.findViewById(R.id.view_pager_subakuisisi);
            tvNoDoc = (TextView) v.findViewById(R.id.title_subakuisisi);
            tvExpDate = (TextView) v.findViewById(R.id.tv_exp_date_sub);
            ln_image = (LinearLayout) v.findViewById(R.id.ln_image_sub_akuisisi);
            ln_resgistrasi = (LinearLayout) v.findViewById(R.id.ln_resgistrasi_sub_akuisisi);
            tvOutlet = (TextView)v.findViewById(R.id.tv_nama_outlet_akuisisi);
            tvUserName = (TextView)v.findViewById(R.id.tv_username_akuisisi);


            headerRepo = new tAkuisisiHeaderRepo(getContext());
            detailRepo = new tAkuisisiDetailRepo(getContext());
            dokterRepo = new mDokterRepo(getContext());
            apotekRepo = new mApotekRepo(getContext());

            try {
                if (intTypeSubSubId ==new clsHardCode().TypeFoto){
                    ln_resgistrasi.setVisibility(View.GONE);
                    if (dtHeader!=null){

                        tvNoDoc.setText(dtHeader.getTxtNoDoc());
                        tvExpDate.setText(String.valueOf(dtHeader.getDtExpiredDate()));
                        dtDetail = (List<tAkuisisiDetail>) detailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
                    }
                }else if (intTypeSubSubId==new clsHardCode().TypeText){
                    ln_image.setVisibility(View.GONE);
                    if (dtHeader!=null){
                        if (dtHeader.getIntApotekID()!=null){
                            apotek = (mApotek) apotekRepo.findBytxtId(dtHeader.getIntApotekID());
                            tvOutlet.setText("Pharmacy : "+apotek.getTxtName());
                        }else if (dtHeader.getIntDokterId()!=null){
                            dokter = (mDokter) dokterRepo.findBytxtId(dtHeader.getIntDokterId());
                            if (dokter.getTxtLastName()!=null){
                                tvOutlet.setText("Doctor : "+ dokter.getTxtFirstName() + " " + dokter.getTxtLastName());
                            }else {
                                tvOutlet.setText("Doctor : "+ dokter.getTxtFirstName());
                            }
                        }
                        tvUserName.setText(dtHeader.getTxtUserName());
                        dtDetail = (List<tAkuisisiDetail>) detailRepo.findByHeaderId(dtHeader.getTxtHeaderId());
                    }
                }
//                dtHeader = (tAkuisisiHeader) headerRepo.findBySubSubId(intSubSubId, new clsHardCode().Save);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            adapterImageSlider = new AdapterImageSlider(getActivity(), dtDetail);
            adapterImageSlider.setItems(dtDetail);
            viewPager.setAdapter(adapterImageSlider);

            // displaying selected image first
            viewPager.setCurrentItem(0);
            addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int pos) {
                    addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });



            adapterImageSlider.setOnItemClickListener(new AdapterImageSlider.OnItemClickListener() {
                @Override
                public void onItemClick(View view, tAkuisisiDetail obj) {
                    Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
//                    intent1.putExtra(ZOOM_DIRECTORY, new clsHardCode().txtFolderAkuisisi);
                    intent1.putExtra(ZOOM_IMAGE, obj.getTxtDetailId());
                    startActivity(intent1);
                }
            });
        return v;
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getActivity());
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(ContextCompat.getColor(getActivity(), R.color.green_300), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setColorFilter(ContextCompat.getColor(getActivity(), R.color.grey_20), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<tAkuisisiDetail> items;

        private OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, tAkuisisiDetail obj);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<tAkuisisiDetail> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public tAkuisisiDetail getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<tAkuisisiDetail> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final tAkuisisiDetail dt = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, dt.getTxtImg());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, dt);
                    }
                }
            });
            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }
}
