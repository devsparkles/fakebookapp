package com.devsparkle.fakebookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.devsparkle.fakebookapp.R;
import com.devsparkle.fakebookapp.models.Recipient;
import java.util.ArrayList;

/**
 * Created by xmaximin on 8/20/17.
 */

public class RecipientAdapter extends BaseAdapter {

  private Context mContext;
  private LayoutInflater mInflater;
  private ArrayList<Recipient> mDataSource;

  public RecipientAdapter(Context context, ArrayList<Recipient> items) {
    mContext = context;
    mDataSource = items;
    mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  //1
  @Override public int getCount() {
    return mDataSource.size();
  }

  //2
  @Override public Object getItem(int position) {
    return mDataSource.get(position);
  }

  //3
  @Override public long getItemId(int position) {
    return position;
  }

  //4
  @Override public View getView(int position, View convertView, ViewGroup parent) {
    // Get view for row item
    View rowView = mInflater.inflate(R.layout.list_item_recipient, parent, false);


    TextView titleTextView =
        (TextView) rowView.findViewById(com.devsparkle.fakebookapp.R.id.textViewRecipientName);


    Recipient recipient = (Recipient) getItem(position);

    titleTextView.setText(recipient.getName());

    return rowView;
  }
}