package com.jdsports.com.mysample.homescreen;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jdsports.com.mysample.R;
import com.jdsports.com.mysample.modal.pojo.Image;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {

    private List<Image> imageList;
    private MainContract.ImageClickListener listener;

    public ImageListAdapter(List<Image> imageList, MainContract.ImageClickListener listener) {
        this.imageList = imageList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Image image = imageList.get(position);
        holder.userName.setText(image.getUser().toString());
        holder.like.setText(image.getLikes().toString());
        holder.comments.setText(image.getComments().toString());

        Picasso.get()
                .load(image.getLargeImageURL())
                .placeholder(R.drawable.shimmer_background)
                .into(holder.image);

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(1)
                .cornerRadiusDp(30)
                .oval(false)
                .build();

        Picasso.get()
                .load(image.getUserImageURL())
                .fit()
                .transform(transformation)
                .into(holder.userImage);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageClick(image.getLargeImageURL());
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    @Override
    public long getItemId(int position) {
        return imageList.get(position).getId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image, userImage;
        public TextView userName, like, comments;

        public MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            userImage = view.findViewById(R.id.user_image);
            userName = view.findViewById(R.id.user);
            like = view.findViewById(R.id.like);
            comments = view.findViewById(R.id.comments);
        }
    }
}