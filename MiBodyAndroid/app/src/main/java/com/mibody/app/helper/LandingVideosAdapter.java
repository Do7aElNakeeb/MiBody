package com.mibody.app.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class LandingVideosAdapter extends RecyclerView.Adapter<LandingVideosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrayList;
    public LandingVideosAdapter(Context context, ArrayList<String> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) mInflater.inflate(R.layout.landing_videos_item, parent, false);

        return new ViewHolder(viewGroup);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.videoThumbnail.setDrawingCacheEnabled(true);

        new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.VideoThumbnail + arrayList.get(position) + "/mqdefault.jpg").into(holder.videoThumbnail);

        new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.VideoThumbnail + arrayList.get(position) + "/mqdefault.jpg")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Bitmap imageRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                        Canvas canvas = new Canvas(imageRounded);
                        Paint mpaint = new Paint();
                        mpaint.setAntiAlias(true);
                        mpaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                        canvas.drawRoundRect((new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100
                        holder.videoThumbnail.setImageBitmap(imageRounded);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


        holder.videoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + arrayList.get(holder.getAdapterPosition())));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoThumbnail;
        ImageView videoPlay;

        ViewHolder(View itemView) {
            super(itemView);

            videoThumbnail = (ImageView) itemView.findViewById(R.id.videoThumbnail);
            videoPlay = (ImageView) itemView.findViewById(R.id.videoPlayBtn);
        }
    }
}
