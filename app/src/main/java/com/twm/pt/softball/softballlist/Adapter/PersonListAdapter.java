package com.twm.pt.softball.softballlist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twm.pt.softball.softballlist.Activity.PersonActivity;
import com.twm.pt.softball.softballlist.Manager.PlayerDataManager;
import com.twm.pt.softball.softballlist.R;
import com.twm.pt.softball.softballlist.component.Player;
import com.twm.pt.softball.softballlist.utility.StorageDirectory;

import java.util.ArrayList;

/**
 * Created by TangWen on 2015/3/23.
 */
public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder> {

    private ArrayList<Player> playerArrayList;
    private Context mContext;
    private final String picPath = "file://" + StorageDirectory.getStorageDirectory(mContext, StorageDirectory.StorageType.ST_SDCard_RootDir) + PlayerDataManager.picPath;;
    private ArrayList<View.OnClickListener> mListener = new ArrayList<View.OnClickListener> ();
    private ArrayList<View.OnClickListener> mPresentListener = new ArrayList<View.OnClickListener> ();
    private int setFullSpanPosition = -1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener  {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView person_name;
        public TextView person_nickName;
        public TextView person_number;
        public TextView person_habits;
        public TextView person_fielder;
        public CheckBox person_present;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            mImageView =getView(itemLayoutView, R.id.person_picture);
            person_name = getView(itemLayoutView, R.id.person_name);
            person_nickName = getView(itemLayoutView, R.id.person_nickName);
            person_number = getView(itemLayoutView, R.id.person_number);
            person_habits = getView(itemLayoutView, R.id.person_habits);
            person_fielder = getView(itemLayoutView, R.id.person_fielder);
            person_present = getView(itemLayoutView, R.id.person_present);
            mImageView.setOnClickListener(onImageViewClickListener);
            person_present.setOnClickListener(onPresentClickListener);
        }

        @Override
        public void onClick(View view) {
            if(setFullSpanPosition == getLayoutPosition()) {
                setFullSpanPosition = -1;
            } else {
                setFullSpanPosition = getLayoutPosition();
            }
//            notifyDataSetChanged();
            for (View.OnClickListener listener: mListener) {
                view.setTag(getLayoutPosition());
                listener.onClick(view);
            }
        }

        View.OnClickListener onImageViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPersonActivity();
            }
        };

        View.OnClickListener onPresentClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerArrayList.get(getLayoutPosition()).present = ((CheckBox)view).isChecked();
                PlayerDataManager.getInstance(mContext).setAllPlayers(playerArrayList);
                for (View.OnClickListener listener: mPresentListener) {
                    listener.onClick(view);
                }
            }
        };

        private void openPersonActivity() {
            Intent intent = new Intent(mContext, PersonActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Player", playerArrayList.get(getLayoutPosition()));
            mContext.startActivity(intent);
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
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
        //Picasso.with(mContext).load(mPlayer.picture).placeholder(R.drawable.progress_animation).error(R.mipmap.baseball_icon).into(holder.mImageView);
        //L.d("(" + position + ")" + "picPath=file://" + picPath + mPlayer.picture);
        Picasso.with(mContext).load(picPath + mPlayer.picture).placeholder(R.drawable.progress_animation).error(R.mipmap.baseball_icon).into(holder.mImageView);
        holder.person_number.setText("# " + mPlayer.number);
        holder.person_name.setText(mPlayer.Name);
        holder.person_nickName.setText(" (" + mPlayer.nickName + ")");
        holder.person_habits.setText(mPlayer.habits);
        holder.person_fielder.setText(mPlayer.fielder.getFielderName());
        holder.person_present.setChecked(mPlayer.isPresent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return playerArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener mListener) {
        this.mListener.add(mListener);
    }
    public void removeOnClickListener(View.OnClickListener mListener) {
        this.mListener.remove(mListener);
    }
    public void setPresentOnClickListener(View.OnClickListener mPresentListener) {
        this.mPresentListener.add(mPresentListener);
    }
    public void removePresentOnClickListener(View.OnClickListener mPresentListener) {
        this.mPresentListener.remove(mPresentListener);
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    public Player getItem(int pos) {
        if(pos>= playerArrayList.size()) return null;
        return playerArrayList.get(pos);
    }

    public final <E extends View> E
    getView(View view, int id) {
        return (E) view.findViewById(id);
    }
}
