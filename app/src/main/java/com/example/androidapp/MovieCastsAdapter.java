package com.example.androidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class MovieCastsAdapter  extends RecyclerView.Adapter<MovieCastsAdapter.CastViewHolder> {

    private Context mContext;
    private List<MovieCast> mCasts;

    MovieCastsAdapter(Context mContext, List<MovieCast> mCasts) {
        this.mContext = mContext;
        this.mCasts = mCasts;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CastViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {

        Glide.with(mContext.getApplicationContext()).load(BuildConfig.IMAGE_URL + mCasts.get(position).getProfilePath())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.castImageView);

        if (mCasts.get(position).getName() != null)
            holder.nameTextView.setText(mCasts.get(position).getName());
        else
            holder.nameTextView.setText("");

        if (mCasts.get(position).getCharacter() != null)
            holder.characterTextView.setText(mCasts.get(position).getCharacter());
        else
            holder.characterTextView.setText("");

    }

    @Override
    public int getItemCount() {
        return mCasts.size();
    }


    static class CastViewHolder extends RecyclerView.ViewHolder {

        ImageView castImageView;
        TextView nameTextView;
        TextView characterTextView;

        CastViewHolder(View itemView) {
            super(itemView);
            castImageView = itemView.findViewById(R.id.image_view_cast);
            nameTextView = itemView.findViewById(R.id.text_view_cast_name);
            characterTextView = itemView.findViewById(R.id.text_view_cast_as);
        }
    }

}