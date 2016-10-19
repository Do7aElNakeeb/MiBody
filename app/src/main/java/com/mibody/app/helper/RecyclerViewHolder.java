package com.mibody.app.helper;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mibody.app.R;

/**
 * Created by NakeebMac on 10/11/16.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView imageView;


    public RecyclerViewHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(R.id.expandedListItemTxt);
        this.imageView = (ImageView) itemView.findViewById(R.id.expandedListItemImg);

    }

}
