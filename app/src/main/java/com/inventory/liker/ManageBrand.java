package com.inventory.liker;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inventory.liker.Adapter.ItemListAdapter;
import com.inventory.liker.Api.SharePrefrancClass;
import com.inventory.liker.Model.Inventory;

import java.util.ArrayList;

public class ManageBrand extends Fragment {

    ArrayList<Inventory> recordsArrayList;

    RecyclerView recyclerView;

    ItemListAdapter itemListAdapter;

    private DatabaseReference myRef;

//    EditText searchView;
    SearchView searchview;
    TableRow table_row;
    TextView name,size,polnih,odprtih,nabavna,enota,popisano,knjizno,dodatna,dodatkov,teza;
    TextView namet,sizet,polniht,odprtiht,nabavnat,enotat,popisanot,knjiznot,dodatnat,dodatkovt,tezat;

    FloatingActionButton fab, fabExportId;
    String firebasekey;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_manage_brand, container, false);
        //Creating an Instance of Firebase Database

        firebasekey = SharePrefrancClass.getInstance(getActivity()).getPref("firebasekey");

        myRef = FirebaseDatabase.getInstance().getReference().child("Location").child(firebasekey).child("Records");

        recordsArrayList = new ArrayList<Inventory>();


        recyclerView = root.findViewById(R.id.table_contents);

//        searchView = root.findViewById(R.id.searchView);
        searchview = root.findViewById(R.id.searchview);
        fab = root.findViewById(R.id.fab);
        fabExportId = root.findViewById(R.id.fabExportId);
        table_row = root.findViewById(R.id.table_row);

        name = root.findViewById(R.id.name);
        size = root.findViewById(R.id.size);
        polnih = root.findViewById(R.id.polnih);
        odprtih = root.findViewById(R.id.odprtih);
        teza = root.findViewById(R.id.teza);
        dodatkov = root.findViewById(R.id.dodatkov);
        dodatna = root.findViewById(R.id.dodatna);
        knjizno = root.findViewById(R.id.knjizno);
        popisano = root.findViewById(R.id.popisano);
        enota = root.findViewById(R.id.enota);
        nabavna = root.findViewById(R.id.nabavna);

        namet = root.findViewById(R.id.namet);
        sizet = root.findViewById(R.id.sizet);
        polniht = root.findViewById(R.id.polniht);
        odprtiht = root.findViewById(R.id.odprtiht);
        tezat = root.findViewById(R.id.tezat);
        dodatkovt = root.findViewById(R.id.dodatkovt);
        dodatnat = root.findViewById(R.id.dodatnat);
        knjiznot = root.findViewById(R.id.knjiznot);
        popisanot = root.findViewById(R.id.popisanot);
        enotat = root.findViewById(R.id.enotat);
        nabavnat = root.findViewById(R.id.nabavnat);

        String artikelCheck = SharePrefrancClass.getInstance(getActivity()).getPref("artikelCheck");
        String dodatekCheck = SharePrefrancClass.getInstance(getActivity()).getPref("dodatekCheck");
        String polnihCheck = SharePrefrancClass.getInstance(getActivity()).getPref("polnihCheck");
        String odprtihCheck = SharePrefrancClass.getInstance(getActivity()).getPref("odprtihCheck");
        String tezaCheck = SharePrefrancClass.getInstance(getActivity()).getPref("tezaCheck");
        String dodatkovCheck = SharePrefrancClass.getInstance(getActivity()).getPref("dodatkovCheck");
        String dodatnaCheck = SharePrefrancClass.getInstance(getActivity()).getPref("dodatnaCheck");
        String knjiznoCheck = SharePrefrancClass.getInstance(getActivity()).getPref("knjiznoCheck");
        String popisanoCheck = SharePrefrancClass.getInstance(getActivity()).getPref("popisanoCheck");
        String enotaCheck = SharePrefrancClass.getInstance(getActivity()).getPref("enotaCheck");
        String nabavnaCheck = SharePrefrancClass.getInstance(getActivity()).getPref("nabavnaCheck");


        if(artikelCheck!=null) {

            if (artikelCheck.equals("1")) {
                name.setVisibility(View.VISIBLE);
            } else {
                name.setVisibility(View.GONE);
            }
            if (dodatekCheck.equals("1")) {
                size.setVisibility(View.VISIBLE);
            } else {
                size.setVisibility(View.GONE);
            }
            if (polnihCheck.equals("1")) {
                polnih.setVisibility(View.VISIBLE);
            } else {
                polnih.setVisibility(View.GONE);
            }
            if (odprtihCheck.equals("1")) {
                odprtih.setVisibility(View.VISIBLE);
            } else {
                odprtih.setVisibility(View.GONE);
            }
            if (tezaCheck.equals("1")) {
                teza.setVisibility(View.VISIBLE);
            } else {
                teza.setVisibility(View.GONE);
            }
            if (dodatkovCheck.equals("1")) {
                dodatkov.setVisibility(View.VISIBLE);
            } else {
                dodatkov.setVisibility(View.GONE);
            }
            if (dodatnaCheck.equals("1")) {
                dodatna.setVisibility(View.VISIBLE);
            } else {
                dodatna.setVisibility(View.GONE);
            }
            if (knjiznoCheck.equals("1")) {
                knjizno.setVisibility(View.VISIBLE);
            } else {
                knjizno.setVisibility(View.GONE);
            }
            if (popisanoCheck.equals("1")) {
                popisano.setVisibility(View.VISIBLE);
            } else {
                popisano.setVisibility(View.GONE);
            }
            if (enotaCheck.equals("1")) {
                enota.setVisibility(View.VISIBLE);
            } else {
                enota.setVisibility(View.GONE);
            }
            if (nabavnaCheck.equals("1")) {
                nabavna.setVisibility(View.VISIBLE);
            } else {
                nabavna.setVisibility(View.GONE);
            }
        }

      /*  if(getArguments().getString("status") != null) {
            String status = getArguments().getString("status");
            if (status.equals("csv")) {
                table_row.setVisibility(View.VISIBLE);
            } else {
                table_row.setVisibility(View.GONE);
            }
        }*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_import);

            }
        });

        fabExportId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("InventoryList", recordsArrayList);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_slideshow, bundle);
            }
        });

        /*searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // filter your list from your input
                filter(s.toString());
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        });*/

        /*int total1 = 0,total2 = 0,total3 = 0,total4 = 0,total5 = 0,total6 = 0,total7 = 0,total8 = 0,total9 = 0,total10 = 0;
        for (int i=1;i<=recordsArrayList.size();i++){
            if(recordsArrayList.get(i).getDodatek().contains(",")){
                String record1   = recordsArrayList.get(i).getArtikel().replace(",", ".");
                total1 = Integer.parseInt(total1 + record1);
            }else {
                total1 = Integer.parseInt(total1 + recordsArrayList.get(i).getArtikel());
            }
            if(recordsArrayList.get(i).getPolnih().contains(",")){
                String record1   = recordsArrayList.get(i).getPolnih().replace(",", ".");
                total2 = Integer.parseInt(total2 + record1);
            }else {
                total2 = Integer.parseInt(total2 + recordsArrayList.get(i).getPolnih());
            }
        }
        namet.setText(String.valueOf(total1));
        sizet.setText(String.valueOf(total2));*/

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recordsArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Inventory inventory = dataSnapshot.getValue(Inventory.class);
                    recordsArrayList.add(inventory);
                }
                itemListAdapter = new ItemListAdapter(recordsArrayList, getActivity());
                recyclerView.setAdapter(itemListAdapter);
                itemListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*void filter(String text) {
        ArrayList<Inventory> temp = new ArrayList();
        for (Inventory d : recordsArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getArtikel().contains(text)) {
                temp.add(d);
            }
        }
        //update recyclerview

        if (itemListAdapter != null){

            itemListAdapter.updateList(temp);
        }
    }*/
}