package com.twm.pt.softball.softballlist.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/3/23.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private ArrayList<Player> playerArrayList;
    private Context mContext;
    private ArrayList<View.OnClickListener> mListener = new ArrayList<View.OnClickListener> ();
    private int setFullSpanPosition = -1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener  {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView person_name;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            mImageView = (ImageView) itemLayoutView.findViewById(R.id.person_picture);
            person_name = (TextView) itemLayoutView.findViewById(R.id.person_name);
        }

        @Override
        public void onClick(View view) {
            if(setFullSpanPosition == getLayoutPosition()) {
                setFullSpanPosition = -1;
            } else {
                setFullSpanPosition = getLayoutPosition();
            }
            notifyDataSetChanged();
//            Toast.makeText(view.getContext(), "position = " + getPosition() + ", " + getLayoutPosition() + ", " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            for (View.OnClickListener listener: mListener) {
                view.setTag(getLayoutPosition());
                listener.onClick(view);
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PersonListAdapter(Context context, ArrayList<Player> playerArrayList) {
        mContext = context;
        this.playerArrayList = playerArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PersonListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PersonListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Player mPlayer = playerArrayList.get(position);
        Picasso.with(mContext).load(mPlayer.picture).placeholder(R.drawable.progress_animation).into(holder.mImageView);
        holder.person_name.setText(mPlayer.Name);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public void addOnClickListener(View.OnClickListener mListener) {
        this.mListener.add(mListener);
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    public Player getItem(int pos) {
        if(pos>= playerArrayList.size()) return null;
        return playerArrayList.get(pos);
    }
}
