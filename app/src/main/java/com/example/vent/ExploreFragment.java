package com.example.vent;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }
        @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        android.content.SharedPreferences prefs = getActivity().getSharedPreferences("public", getContext().MODE_PRIVATE);
        ArrayList<String> vents = retrieveEntries(prefs);
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recycler2);
        RantAdapter adapter = new RantAdapter(vents);
        rv.setAdapter(adapter);
        // Set layout manager to position the items
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<String> retrieveEntries(SharedPreferences prefs)  {
        Set<String> rants = prefs.getStringSet("vents", new HashSet<>());
        ArrayList<String> vents = new ArrayList<>(rants);
        Log.d("OKKKKKK", vents.size() + "");
        Collections.sort(vents, (s1, s2) -> {
            String d1 = s1.split("|")[0];
            String d2 = s2.split("|")[1];

            try {
                return -1 * new SimpleDateFormat("dd/MM/yyyy").parse(d1)
                        .compareTo(new SimpleDateFormat("dd/MM/yyyy").parse(d2));
            } catch (ParseException e) {
                return 0;
            }
        });

        return vents;
    }
}