package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityTweetDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ActivityTweetDetailsBinding binding = ActivityTweetDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("clicked tweet"));

        int radius = 30;
        int margin = 10;
        Glide.with(this)
                .load(tweet.getUser().profileImageUrl)
                .transform(new RoundedCornersTransformation(radius, margin))
                .into(binding.ivProfileImage);
        binding.tvName.setText(tweet.getUser().name);
        binding.tvScreenName.setText(tweet.getUser().screenName);
        binding.tvBody.setText(tweet.getBody());
        if (tweet.getMediaUrl() == null) {
            binding.ivImageMedia.setVisibility(View.GONE);
        } else {
            Glide.with(this)
                    .load(tweet.getMediaUrl())
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(binding.ivImageMedia);
        }
    }
}