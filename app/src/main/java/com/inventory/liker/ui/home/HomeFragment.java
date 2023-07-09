package com.inventory.liker.ui.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventory.liker.Adapter.LocationNewAdapter;
import com.inventory.liker.Model.Location;
import com.inventory.liker.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    FloatingActionButton fab;
    EditText etLocation;
    FrameLayout homeFragRootLayout;
    LinearLayout locatontxtlayout;
    ArrayList<Location> locationArrayList;
    LocationNewAdapter locationNewAdapter;
    RecyclerView recyclerId;

    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        locationArrayList = new ArrayList<>();

        homeFragRootLayout = root.findViewById(R.id.homeFragRootLayout);
        locatontxtlayout = root.findViewById(R.id.locatontxtlayout);
        recyclerId = root.findViewById(R.id.recyclerId);
        fab = root.findViewById(R.id.fab);

        myRef = FirebaseDatabase.getInstance().getReference().child("Location");

        FirebaseRecyclerOptions<Location> options = new FirebaseRecyclerOptions.Builder<Location>()
                .setQuery(myRef,Location.class)
                .build();

        locationNewAdapter = new LocationNewAdapter(options,getActivity());
        recyclerId.setAdapter(locationNewAdapter);


        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Location location = dataSnapshot.getValue(Location.class);
                    locationArrayList.add(location);
                }
                locationListAdapter = new LocationListAdapter(locationArrayList, getActivity());
                recyclerId.setAdapter(locationListAdapter);
                locationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter Location");

                final View customLayout = LayoutInflater.from(getActivity()).inflate(R.layout.layout_addnew_location, null);
                builder.setView(customLayout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etLocation = customLayout.findViewById(R.id.etLocation);

                        String locationStr = etLocation.getText().toString();

                        Location location = new Location();
                        location.setLocation(locationStr);

                        myRef.push().setValue(location);

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

        return root;
    }

    /*private void showItemsFromDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                locationArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Location location = dataSnapshot.getValue(Location.class);
                    locationArrayList.add(location);
                }
                locationListAdapter = new LocationListAdapter(locationArrayList, getActivity());
                recyclerId.setAdapter(locationListAdapter);
                locationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    @Override
    public void onStart() {
        super.onStart();
        locationNewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationNewAdapter.stopListening();
    }
}