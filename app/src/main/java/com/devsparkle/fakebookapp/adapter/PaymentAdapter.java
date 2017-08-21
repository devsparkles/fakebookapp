package com.devsparkle.fakebookapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.devsparkle.fakebookapp.R;
import com.devsparkle.fakebookapp.models.Payment;
import java.util.List;

/**
 * Created by xmaximin on 8/21/17.
 */

public class PaymentAdapter extends BaseAdapter {

  private static final String TAG = "PaymentAdapter";


  private Context mContext;
  private LayoutInflater mInflater;
  private List<Payment> mDataSource;

  public PaymentAdapter(Context context, List<Payment> items) {
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
    View rowView = mInflater.inflate(R.layout.list_item_payment, parent, false);

    TextView textViewRecipientName =
        (TextView) rowView.findViewById(com.devsparkle.fakebookapp.R.id.textViewRecipientName);

    TextView textViewAmount =
        (TextView) rowView.findViewById(com.devsparkle.fakebookapp.R.id.textViewAmount);

    TextView textViewStatus =
        (TextView) rowView.findViewById(com.devsparkle.fakebookapp.R.id.textViewStatus);

    Payment payment = (Payment) getItem(position);

    // search the recipient name by recipient id

    textViewRecipientName.setText(payment.getRecipient_name());
    textViewAmount.setText(payment.getAmount() + " GBP");
    textViewStatus.setText(payment.getStatus());
    return rowView;
  }


}
