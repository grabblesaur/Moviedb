package com.example.bayar.moviedb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bayar.moviedb.R;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindViews({R.id.detail_title, R.id.detail_year, R.id.detail_rating, R.id.detail_popularity})
    List<TextView> mTextViewList;
    @BindView(R.id.detail_poster)
    ImageView mPoster;
    @BindView(R.id.detail_overview)
    TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setUpViews();
    }

    private void setUpViews() {
        Intent intent = getIntent();
        mTextViewList.get(0).setText(intent.getStringExtra("title"));
        mTextViewList.get(1).setText(intent.getStringExtra("year"));
        mTextViewList.get(2).setText(intent.getStringExtra("rating"));
        mTextViewList.get(3).setText(intent.getStringExtra("popularity"));
        mOverview.setText(intent.getStringExtra("overview"));
        setUpPoster(intent.getStringExtra("poster_path"));
    }

    private void setUpPoster(String posterPath) {
        String imageUrl = "http://image.tmdb.org/t/p/w500" + posterPath;
        Glide.with(this).load(imageUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mPoster);
    }
}
