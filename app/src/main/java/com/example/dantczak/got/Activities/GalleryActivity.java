package com.example.dantczak.got.Activities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dantczak.got.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;
import java.util.Locale;

public class GalleryActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupView();
        setupPager();
    }

    private void setupPager()
    {
        PhotoPagerAdapter photoPagerAdapter =
                new PhotoPagerAdapter(this, VerificationActivity.getPathToVerifyInstance().getPics());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(photoPagerAdapter);
    }

    private void setupView()
    {
        boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
        if(isLandscape)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(isLandscape)
        {
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class PhotoPagerAdapter extends PagerAdapter
    {
        private Context context;
        private List<byte[]> imagesBytes;
        private Bitmap[] bitmaps;

        public PhotoPagerAdapter(Context context, List<byte[]> imagesBytes)
        {
            this.context = context;
            this.imagesBytes = imagesBytes;
            bitmaps = new Bitmap[imagesBytes.size()];
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup parent, int position)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.ver_photo_layout, parent, false);

            ImageView imageView = layout.findViewById(R.id.image_view);
            Bitmap photo = GetPhoto(position);
            Glide.with(context).load(photo).into(imageView);

            TextView counterText = layout.findViewById(R.id.counter_text);
            counterText.setText(String.format(Locale.GERMANY, "%d/%d", position + 1, bitmaps.length));

            parent.addView(layout);
            return layout;
        }

        @Override
        public int getCount()
        {
            return bitmaps.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
        {
            return view == object;
        }

        private @NonNull Bitmap GetPhoto(int position)
        {
            Bitmap photo = bitmaps[position];
            if(photo == null)
            {
                photo = BitmapFactory.decodeByteArray(imagesBytes.get(position), 0, imagesBytes.get(position).length);
                bitmaps[position] = photo;
            }
            return photo;
        }
    }
}
