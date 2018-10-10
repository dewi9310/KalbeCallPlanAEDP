package com.kalbe.kalbecallplanaedp;

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
import com.kalbe.kalbecallplanaedp.Data.clsHardCode;
import com.kalbe.kalbecallplanaedp.Model.Image;
import com.kalbe.kalbecallplanaedp.Utils.Tools;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImage;
import com.kalbe.mobiledevknlibs.PickImageAndFile.PickImageCustom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dewi Oktaviani on 10/5/2018.
 */

@SuppressLint("ValidFragment")
public class FragmentSubAkuisisi extends Fragment {
    View v;
    private ListView mListView;
    private String txtSubAkuisisi;
    String testing;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;

    public FragmentSubAkuisisi(String txtSubAkuisisi, String testing){
        this.txtSubAkuisisi = txtSubAkuisisi;
        this.testing = testing;
    }

    private static int[] array_image_product = {
            R.drawable.image_shop_9,
            R.drawable.image_shop_10,
            R.drawable.image_shop_11,
            R.drawable.image_shop_12,
            R.drawable.image_shop_13,
    };

        @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sub_akuisisi, container, false);
            layout_dots = (LinearLayout) v.findViewById(R.id.layout_dots);
            viewPager = (ViewPager) v.findViewById(R.id.view_pager_subakuisisi);
            adapterImageSlider = new AdapterImageSlider(getActivity(), new ArrayList<Image>());
            TextView tvTesting = (TextView) v.findViewById(R.id.title_subakuisisi);
            tvTesting.setText(testing);

//        mListView = (ListView) v.findViewById(R.id.lv_sub_akuisisi);
            List<Image> items = new ArrayList<>();
            for (int i : array_image_product) {
                Image obj = new Image();
                obj.image = i;
                obj.imageDrw = getResources().getDrawable(obj.image);
                items.add(obj);
            }

            adapterImageSlider.setItems(items);
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
                public void onItemClick(View view, Image obj) {
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = null;
                    Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), obj.image);
//                    ByteArrayOutputStream byteArrayOutStream = new ByteArrayOutputStream();
//                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutStream);
//                    byte[] b = byteArrayOutStream.toByteArray();
//                    File mediaStorageDir = PickImage.decodeByteArraytoImageFile(b, new clsHardCode().txtPathTempData);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
//                        uri =  FileProvider.getUriForFile(getContext().getApplicationContext(), getContext().getApplicationContext().getPackageName()+".provider", mediaStorageDir);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    } else {
//                        uri = Uri.fromFile(mediaStorageDir);
//                    }
//                    intent.setData(uri);
//                    intent.setDataAndType(uri, "image/*");
//                    startActivity(intent);
                    Intent intent1 = new Intent(getContext(), ImageViewerActivity.class);
                    intent1.putExtra("image", obj.image);
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
        private List<Image> items;

        private OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            Tools.displayImageOriginal(act, image, o.image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });
//            lyt_parent.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(v, o);
//                    }
//                }
//            });

            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }
}
