package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Context context;
    private List<Tweet> tweets;
    private OnTweetListener onTweetListener;

    // Pass in context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets, OnTweetListener onTweetListener) {
        this.context = context;
        this.tweets = tweets;
        this.onTweetListener = onTweetListener;
    }

    // For each row, inflate layout
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view, onTweetListener);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bind the tweet with view holder
        holder.bind(tweet);
    }

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Define a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvRelativeTime;
        ImageView ivImageMedia;
        OnTweetListener onTweetListener;

        public ViewHolder(@NonNull @NotNull View itemView, OnTweetListener onTweetListener) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTime = itemView.findViewById(R.id.tvRelativeTime);
            ivImageMedia = itemView.findViewById(R.id.ivImageMedia);
            this.onTweetListener = onTweetListener;
            itemView.setOnClickListener(this);
        }

        int radius = 30;
        int margin = 10;
        public void bind(Tweet tweet) {
            tvBody.setText(tweet.getBody());
            tvName.setText(tweet.getUser().name);
            tvScreenName.setText(String.format("@%s", tweet.getUser().screenName));
            tvRelativeTime.setText(String.format("· %s", tweet.getRelativeTime()));
            Glide.with(context).load(tweet.getUser().profileImageUrl).into(ivProfileImage);
            if (tweet.getMediaUrl() != null) {
                ivImageMedia.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(tweet.getMediaUrl())
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivImageMedia);
            } else {
                ivImageMedia.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "clicked position " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            onTweetListener.onTweetClick(getAdapterPosition());
        }
    }

    public interface OnTweetListener {
        void onTweetClick(int position);
    }

}
