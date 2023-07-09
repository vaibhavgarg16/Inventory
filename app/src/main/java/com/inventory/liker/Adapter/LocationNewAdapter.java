package com.inventory.liker.Adapter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventory.liker.Api.SharePrefrancClass;
import com.inventory.liker.Model.Location;
import com.inventory.liker.R;

public class LocationNewAdapter extends FirebaseRecyclerAdapter<Location,LocationNewAdapter.ItemViewHolder> {

    private DatabaseReference myRef;
    FragmentActivity mcontext;
    EditText etLocation;

    public LocationNewAdapter(FirebaseRecyclerOptions<Location> options, FragmentActivity activity){
        super(options);
        this.mcontext = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Location location) {
        myRef = FirebaseDatabase.getInstance().getReference().child("Location");

        holder.location.setText(location.getLocation());

        holder.editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("Enter Location");
                View customLayout = LayoutInflater.from(mcontext).inflate(R.layout.layout_addnew_location, null);
                builder.setView(customLayout);
                etLocation = customLayout.findViewById(R.id.etLocation);
                etLocation.setText(location.getLocation());

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String locationStr = etLocation.getText().toString();
                        Location location = new Location();
                        location.setLocation(locationStr);
                        myRef.child(getRef(position).getKey()).setValue(location);
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

        holder.deleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Location")
                        .child(getRef(position).getKey())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
            }
        });

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
//                bundle.getString("firebasekey",getRef(position).getKey());
                SharePrefrancClass.getInstance(mcontext).savePref("firebasekey", getRef(position).getKey());
                Navigation.findNavController(mcontext, R.id.nav_host_fragment).navigate(R.id.nav_managebrand, bundle);
            }
        });

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recycler_item, parent, false);
        return new ItemViewHolder(view);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView location;
        ImageView deleteLocation, editLocation;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location);
            deleteLocation = itemView.findViewById(R.id.deleteLocation);
            editLocation = itemView.findViewById(R.id.editLocation);
        }
    }
}
