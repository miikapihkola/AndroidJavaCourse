package com.example.androidjavacourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//public class YtjAdapter extends RecyclerView.Adapter<YtjAdapter.ViewHolder> implements Filterable {
public class YtjAdapter extends RecyclerView.Adapter<YtjAdapter.ViewHolder>{
    public static final String TAG ="Ytj Adapter";
    private final ArrayList<Item> courseModelArrayList;

    // Constructor
    public YtjAdapter(ArrayList<Item> courseModelArrayList) {
        this.courseModelArrayList = courseModelArrayList;
    }

    @NonNull
    @Override
    public YtjAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_ytj_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YtjAdapter.ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Item model = courseModelArrayList.get(position);
        holder.companyName.setText(model.name);
        holder.businessId.setText("BusinessId: " + model.businessId);
        holder.companyform.setText("Companyform: " + "OY");
        holder.registrationDate.setText("Registration date: " + model.registrationDate);
        // Log.d(TAG, model.name);
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return courseModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView companyName;
        private final TextView businessId;
        private final TextView companyform;
        private final TextView registrationDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = (TextView) itemView.findViewById(R.id.title);
            businessId = (TextView) itemView.findViewById(R.id.businessId);
            companyform = (TextView) itemView.findViewById(R.id.companyForm);
            registrationDate = (TextView) itemView.findViewById(R.id.registrationDate);

        }
    }
}
