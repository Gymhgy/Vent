package com.example.vent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RantAdapter extends RecyclerView.Adapter<RantAdapter.ViewHolder> {

    private List<String> rants;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView rantText;
        public TextView rantDate;

        public ViewHolder(View view) {
            super(view);
            rantText = (TextView) view.findViewById(R.id.rantText);
            rantDate = (TextView) view.findViewById(R.id.rantDate);
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    private boolean check = false;
    private List<Integer> indices = new ArrayList<>();
    public RantAdapter(List<String> dataSet) {
        rants = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.rant_layout, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        String rant = rants.get(position);
        String date = rant.split("\\|")[0];
        rant = rant.split("\\|", 2)[1];
        Log.d("OKKKKKK", rant + " " + date);

        viewHolder.rantDate.setText(date);
        viewHolder.rantText.setText(rant);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rants.size();
    }
}