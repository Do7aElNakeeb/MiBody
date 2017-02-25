package ch.philopateer.mibody.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NakeebMac on 10/11/16.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ImageView imageView;


    public RecyclerViewHolder(View itemView) {
        super(itemView);

        this.title = (TextView) itemView.findViewById(ch.philopateer.mibody.R.id.expandedListItemTxt);
        this.imageView = (ImageView) itemView.findViewById(ch.philopateer.mibody.R.id.expandedListItemImg);

    }

}
