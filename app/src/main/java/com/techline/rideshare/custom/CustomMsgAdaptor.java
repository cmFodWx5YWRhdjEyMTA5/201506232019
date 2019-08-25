package com.techline.rideshare.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.techline.rideshare.R;
import com.techline.rideshare.RequestDetailActivity;

import java.util.ArrayList;

public class CustomMsgAdaptor extends RecyclerView.Adapter<CustomMsgAdaptor.MyViewHolder> {

    private static String TAG = "CUSTOM_ADAPTOR";
    ArrayList<String> pickUp;
    ArrayList<String> whereTo;
    ArrayList<String> passenger_name;
    ArrayList<String> request_id;
    ArrayList<String> requested_time;
    Context context;

    public CustomMsgAdaptor(Context context, ArrayList<String> pickUp,
                            ArrayList<String> whereTo,
                            ArrayList<String> passenger_name, ArrayList<String> request_id, ArrayList<String> requested_time) {
        this.context = context;
        this.pickUp = pickUp;
        this.whereTo = whereTo;
        this.passenger_name = passenger_name;
        this.request_id = request_id;
        this.requested_time = requested_time;
    }

    @Override
    public CustomMsgAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
        CustomMsgAdaptor.MyViewHolder vh = new CustomMsgAdaptor.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(CustomMsgAdaptor.MyViewHolder holder, final int position) {

        // set the data in items
        holder.tvPickUp.setText(pickUp.get(position));
        holder.tvWhereTo.setText(whereTo.get(position));
        holder.tvPassenger_name.setText(passenger_name.get(position));
        holder.tvRequest_id.setText(request_id.get(position));
        holder.tvRequested_time.setText(requested_time.toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person tvRequester_name on item click
                Bundle bundle = new Bundle();
                bundle.putString("pickUp", pickUp.get(position));
                bundle.putString("request_id", request_id.get(position));
                //bundle.putString("requested_time", requested_time.get(position));
                Log.d(TAG, "pickUp.get(position) is :" + pickUp.get(position));
                Log.d(TAG, "my_own_request_id.toString is :" + request_id.get(position));

                Intent intent = new Intent(context.getApplicationContext(), RequestDetailActivity.class);
                intent.putExtra("pickUp", pickUp.get(position));
                intent.putExtra("request_id", request_id.get(position));
                intent.putExtra("whereTo", whereTo.get(position));
                intent.putExtra("passenger_name", passenger_name.get(position));
                intent.putExtra("requested_time", requested_time.get(position));

                context.startActivity(intent);
                Toast.makeText(context, pickUp.get(position), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, requested_time.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return pickUp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPickUp, tvWhereTo, tvPassenger_name,
                tvRequest_id, tvRequested_time;
        // init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            tvPickUp = itemView.findViewById(R.id.pickUp);
            tvWhereTo = itemView.findViewById(R.id.whereTo);
            tvPassenger_name = itemView.findViewById(R.id.passenger_name);
            tvRequest_id = itemView.findViewById(R.id.request_id);
            tvRequested_time = itemView.findViewById(R.id.requested_time);
        }
    }
}
