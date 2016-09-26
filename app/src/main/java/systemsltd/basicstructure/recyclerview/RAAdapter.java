package systemsltd.basicstructure.recyclerview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.List;

import systemsltd.basicstructure.R;
import systemsltd.basicstructure.user.UserModel;

public class RAAdapter extends RecyclerView.Adapter<RAAdapter.ViewHolder> {

    private static final String TAG = RAAdapter.class.getSimpleName();

    private List<UserModel> mItems;

    Activity act;

    private int position;

    public RAAdapter(Activity act, List<UserModel> items) {

        mItems = items;
        this.act = act;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int view_type) {

        View v = null;

        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ra_row, viewGroup, false);

    return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final UserModel newsFeed = mItems.get(position);

        viewHolder.text_name.setText(newsFeed.name);
        viewHolder.text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {

       return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_name;

        View rootView;

        ViewHolder(View v) {
            super(v);

            text_name = (TextView) v.findViewById(R.id.text_name);

            rootView = v;
        }
    }

    public UserModel getItem(int position) {
        return mItems.get(position);
    }

    public void addData(UserModel newModelData, int position) {
        mItems.add(position, newModelData);
        notifyItemInserted(position);
    }

    public void addData(UserModel newModelData) {
        mItems.add(newModelData);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void clearData() {
        mItems.clear(); //clear list
        this.notifyDataSetChanged();

    }
}