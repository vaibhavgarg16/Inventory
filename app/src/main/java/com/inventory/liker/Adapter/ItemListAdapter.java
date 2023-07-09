package com.inventory.liker.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inventory.liker.Api.SharePrefrancClass;
import com.inventory.liker.MainViewModel;
import com.inventory.liker.Model.Inventory;
import com.inventory.liker.R;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> implements Filterable {

    private ArrayList<Inventory> recordsArrayList;
    private ArrayList<Inventory> recordsArrayListfilter;
    private Context mContext;
    private DatabaseReference myRef;
    int pos = 0;
    MainViewModel viewModel;
    LinearLayout artikelLay, tezaLay, dodatkovLay, dodatnaLay, knjiznoLay, enotaLay, nabavnaLay, odprtihLay;

    ImageView btnleft, btnRight, imageset;

    public ItemListAdapter(ArrayList<Inventory> recordsArrayList, Context mContext) {
        this.recordsArrayList = recordsArrayList;
        this.recordsArrayListfilter = recordsArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_item, parent, false);
        viewModel = ViewModelProviders.of((FragmentActivity) mContext).get(MainViewModel.class);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        String firebasekey = SharePrefrancClass.getInstance(mContext).getPref("firebasekey");

        myRef = FirebaseDatabase.getInstance().getReference().child("Location").child(firebasekey).child("Records");

        Inventory inventory = recordsArrayListfilter.get(position);


        holder.artikel.setText(inventory.getArtikel());
        if (inventory.getDodatek() != null) {
            holder.dodatek.setText(calculate(inventory.getDodatek()));
        } else {
            holder.dodatek.setText(inventory.getDodatek());
        }

        if (inventory.getPolnih() != null) {
            holder.polnih.setText(calculate(inventory.getPolnih()));
        } else {
            holder.polnih.setText(inventory.getPolnih());
        }
        holder.odprtih.setText(inventory.getOdprtih());
        holder.teza.setText(inventory.getTeza());
        holder.dodatkov.setText(inventory.getDodatkov());
        holder.dodatna.setText(inventory.getDodatna());
        holder.knjizno.setText(inventory.getKnjizno());

        if (inventory.getPopisano() != null) {
            holder.popisano.setText(calculate(inventory.getPopisano()));
        } else {
            holder.popisano.setText(inventory.getPopisano());
        }
        holder.enota.setText(inventory.getEnota());
        holder.nabavna.setText(inventory.getNabavna());

//adapter default position
//        pos = recordsArrayList.indexOf(recordsArrayListfilter.get(holder.getAdapterPosition()));

        holder.dodatek.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.polnih.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.odprtih.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.teza.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.dodatkov.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.dodatna.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.knjizno.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.popisano.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.enota.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.nabavna.setRawInputType(Configuration.KEYBOARD_QWERTY);

        if (recordsArrayListfilter.size() < 150) {
            holder.artikel.setVisibility(View.VISIBLE);
            holder.dodatek.setVisibility(View.VISIBLE);
            holder.polnih.setVisibility(View.VISIBLE);
            holder.odprtih.setVisibility(View.VISIBLE);
            holder.teza.setVisibility(View.VISIBLE);
            holder.dodatkov.setVisibility(View.VISIBLE);
            holder.dodatna.setVisibility(View.VISIBLE);
            holder.knjizno.setVisibility(View.VISIBLE);
            holder.popisano.setVisibility(View.VISIBLE);
            holder.enota.setVisibility(View.VISIBLE);
            holder.nabavna.setVisibility(View.VISIBLE);

            holder.dodatek.setEnabled(false);
            holder.polnih.setEnabled(false);
            holder.odprtih.setEnabled(false);
            holder.teza.setEnabled(false);
            holder.dodatkov.setEnabled(false);
            holder.dodatna.setEnabled(false);
            holder.knjizno.setEnabled(false);
            holder.popisano.setEnabled(false);
            holder.enota.setEnabled(false);
            holder.nabavna.setEnabled(false);

        } else {
            holder.dodatek.setEnabled(true);
            holder.polnih.setEnabled(true);
            holder.odprtih.setEnabled(true);
            holder.teza.setEnabled(true);
            holder.dodatkov.setEnabled(true);
            holder.dodatna.setEnabled(true);
            holder.knjizno.setEnabled(true);
            holder.popisano.setEnabled(true);
            holder.enota.setEnabled(true);
            holder.nabavna.setEnabled(true);

            if (holder.getAdapterPosition() == 0) {
                holder.artikel.setVisibility(View.GONE);
                holder.dodatek.setVisibility(View.GONE);
                holder.polnih.setVisibility(View.GONE);
                holder.odprtih.setVisibility(View.GONE);
                holder.teza.setVisibility(View.GONE);
                holder.dodatkov.setVisibility(View.GONE);
                holder.dodatna.setVisibility(View.GONE);
                holder.knjizno.setVisibility(View.GONE);
                holder.popisano.setVisibility(View.GONE);
                holder.enota.setVisibility(View.GONE);
                holder.nabavna.setVisibility(View.GONE);

            } else {
                holder.artikel.setVisibility(View.VISIBLE);
                holder.dodatek.setVisibility(View.VISIBLE);
                holder.polnih.setVisibility(View.VISIBLE);
                holder.odprtih.setVisibility(View.VISIBLE);
                holder.teza.setVisibility(View.VISIBLE);
                holder.dodatkov.setVisibility(View.VISIBLE);
                holder.dodatna.setVisibility(View.VISIBLE);
                holder.knjizno.setVisibility(View.VISIBLE);
                holder.popisano.setVisibility(View.VISIBLE);
                holder.enota.setVisibility(View.VISIBLE);
                holder.nabavna.setVisibility(View.VISIBLE);
            }
        }

        String artikelCheck = SharePrefrancClass.getInstance(mContext).getPref("artikelCheck");
        String dodatekCheck = SharePrefrancClass.getInstance(mContext).getPref("dodatekCheck");
        String polnihCheck = SharePrefrancClass.getInstance(mContext).getPref("polnihCheck");
        String odprtihCheck = SharePrefrancClass.getInstance(mContext).getPref("odprtihCheck");
        String tezaCheck = SharePrefrancClass.getInstance(mContext).getPref("tezaCheck");
        String dodatkovCheck = SharePrefrancClass.getInstance(mContext).getPref("dodatkovCheck");
        String dodatnaCheck = SharePrefrancClass.getInstance(mContext).getPref("dodatnaCheck");
        String knjiznoCheck = SharePrefrancClass.getInstance(mContext).getPref("knjiznoCheck");
        String popisanoCheck = SharePrefrancClass.getInstance(mContext).getPref("popisanoCheck");
        String enotaCheck = SharePrefrancClass.getInstance(mContext).getPref("enotaCheck");
        String nabavnaCheck = SharePrefrancClass.getInstance(mContext).getPref("nabavnaCheck");

        if (artikelCheck != null) {

            if (artikelCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.artikel.setVisibility(View.VISIBLE);
            } else {
                holder.artikel.setVisibility(View.GONE);
            }
            if (dodatekCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.dodatek.setVisibility(View.VISIBLE);
            } else {
                holder.dodatek.setVisibility(View.GONE);
            }
            if (polnihCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.polnih.setVisibility(View.VISIBLE);
            } else {
                holder.polnih.setVisibility(View.GONE);
            }
            if (odprtihCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.odprtih.setVisibility(View.VISIBLE);
            } else {
                holder.odprtih.setVisibility(View.GONE);
            }
            if (tezaCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.teza.setVisibility(View.VISIBLE);
            } else {
                holder.teza.setVisibility(View.GONE);
            }
            if (dodatkovCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.dodatkov.setVisibility(View.VISIBLE);
            } else {
                holder.dodatkov.setVisibility(View.GONE);
            }
            if (dodatnaCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.dodatna.setVisibility(View.VISIBLE);
            } else {
                holder.dodatna.setVisibility(View.GONE);
            }
            if (knjiznoCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.knjizno.setVisibility(View.VISIBLE);
            } else {
                holder.knjizno.setVisibility(View.GONE);
            }
            if (popisanoCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.popisano.setVisibility(View.VISIBLE);
            } else {
                holder.popisano.setVisibility(View.GONE);
            }
            if (enotaCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.enota.setVisibility(View.VISIBLE);
            } else {
                holder.enota.setVisibility(View.GONE);
            }
            if (nabavnaCheck.equals("1") && holder.getAdapterPosition() != 0) {
                holder.nabavna.setVisibility(View.VISIBLE);
            } else {
                holder.nabavna.setVisibility(View.GONE);
            }
        }

        holder.artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "You Clicked " + recordsArrayListfilter.get(holder.getAdapterPosition()).getArtikel(),
                        Toast.LENGTH_SHORT).show();

                pos = recordsArrayList.indexOf(recordsArrayListfilter.get(holder.getAdapterPosition()));

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Details");

                final View customLayout = LayoutInflater.from(mContext).inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);
                TextView artikel, teza, dodatkov, dodatna, knjizno, enota, nabavna, odprtih;
                EditText polnih, dodatek, popisano;


                imageset = customLayout.findViewById(R.id.imageset);

                artikel = customLayout.findViewById(R.id.artikel);
                dodatek = customLayout.findViewById(R.id.dodatek);
                polnih = customLayout.findViewById(R.id.polnih);
                odprtih = customLayout.findViewById(R.id.odprtih);
                teza = customLayout.findViewById(R.id.teza);
                dodatkov = customLayout.findViewById(R.id.dodatkov);
                dodatna = customLayout.findViewById(R.id.dodatna);
                knjizno = customLayout.findViewById(R.id.knjizno);
                popisano = customLayout.findViewById(R.id.popisano);
                enota = customLayout.findViewById(R.id.enota);
                nabavna = customLayout.findViewById(R.id.nabavna);
                btnRight = customLayout.findViewById(R.id.btnRight);
                btnleft = customLayout.findViewById(R.id.btnleft);

                polnih.setRawInputType(Configuration.KEYBOARD_QWERTY);
                dodatek.setRawInputType(Configuration.KEYBOARD_QWERTY);
                popisano.setRawInputType(Configuration.KEYBOARD_QWERTY);

                artikelLay = customLayout.findViewById(R.id.artikelLay);
                tezaLay = customLayout.findViewById(R.id.tezaLay);
                dodatkovLay = customLayout.findViewById(R.id.dodatkovLay);
                dodatnaLay = customLayout.findViewById(R.id.dodatnaLay);
                knjiznoLay = customLayout.findViewById(R.id.knjiznoLay);
                enotaLay = customLayout.findViewById(R.id.enotaLay);
                nabavnaLay = customLayout.findViewById(R.id.nabavnaLay);
                odprtihLay = customLayout.findViewById(R.id.odprtihLay);


                if (inventory.getDodatek() != null) {
                    dodatek.setText(calculate(inventory.getDodatek()));
                } else {
                    dodatek.setText(inventory.getDodatek());
                }

                if (inventory.getPolnih() != null) {
                    polnih.setText(calculate(inventory.getPolnih()));
                } else {
                    polnih.setText(inventory.getPolnih());
                }

                if (inventory.getPopisano() != null) {
                    popisano.setText(calculate(inventory.getPopisano()));
                } else {
                    popisano.setText(inventory.getPopisano());
                }

                artikel.setText(inventory.getArtikel());
//                dodatek.setText(inventory.getDodatek());
//                polnih.setText(inventory.getPolnih());
                odprtih.setText(inventory.getOdprtih());
                teza.setText(inventory.getTeza());
                dodatkov.setText(inventory.getDodatkov());
                dodatna.setText(inventory.getDodatna());
                knjizno.setText(inventory.getKnjizno());
//                popisano.setText(inventory.getPopisano());
                enota.setText(inventory.getEnota());
                nabavna.setText(inventory.getNabavna());

                imgSet(pos);

                if (!TextUtils.isEmpty(artikel.getText())) {
                    artikelLay.setVisibility(View.VISIBLE);
                } else {
                    artikelLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(teza.getText())) {
                    tezaLay.setVisibility(View.VISIBLE);
                } else {
                    tezaLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(dodatkov.getText())) {
                    dodatkovLay.setVisibility(View.VISIBLE);
                } else {
                    dodatkovLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(dodatna.getText())) {
                    dodatnaLay.setVisibility(View.VISIBLE);
                } else {
                    dodatnaLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(knjizno.getText())) {
                    knjiznoLay.setVisibility(View.VISIBLE);
                } else {
                    knjiznoLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(enota.getText())) {
                    enotaLay.setVisibility(View.VISIBLE);
                } else {
                    enotaLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(nabavna.getText())) {
                    nabavnaLay.setVisibility(View.VISIBLE);
                } else {
                    nabavnaLay.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(odprtih.getText())) {
                    odprtihLay.setVisibility(View.VISIBLE);
                } else {
                    odprtihLay.setVisibility(View.GONE);
                }


                if (recordsArrayListfilter.size() < 150) {
                    btnleft.setVisibility(View.GONE);
                    btnRight.setVisibility(View.GONE);
                } else {
                    btnleft.setVisibility(View.VISIBLE);
                    btnRight.setVisibility(View.VISIBLE);
                }

                btnleft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pos > 1) {
                            pos = pos - 1;

                            artikel.setText(recordsArrayListfilter.get(pos).getArtikel());
                            dodatek.setText(recordsArrayListfilter.get(pos).getDodatek());
                            polnih.setText(recordsArrayListfilter.get(pos).getPolnih());
                            odprtih.setText(recordsArrayListfilter.get(pos).getOdprtih());
                            teza.setText(recordsArrayListfilter.get(pos).getTeza());
                            dodatkov.setText(recordsArrayListfilter.get(pos).getDodatkov());
                            dodatna.setText(recordsArrayListfilter.get(pos).getDodatna());
                            knjizno.setText(recordsArrayListfilter.get(pos).getKnjizno());
                            popisano.setText(recordsArrayListfilter.get(pos).getPopisano());
                            enota.setText(recordsArrayListfilter.get(pos).getEnota());
                            nabavna.setText(recordsArrayListfilter.get(pos).getNabavna());

                            imgSet(pos);

                            if (!TextUtils.isEmpty(artikel.getText())) {
                                artikelLay.setVisibility(View.VISIBLE);
                            } else {
                                artikelLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(teza.getText())) {
                                tezaLay.setVisibility(View.VISIBLE);
                            } else {
                                tezaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(dodatkov.getText())) {
                                dodatkovLay.setVisibility(View.VISIBLE);
                            } else {
                                dodatkovLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(dodatna.getText())) {
                                dodatnaLay.setVisibility(View.VISIBLE);
                            } else {
                                dodatnaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(knjizno.getText())) {
                                knjiznoLay.setVisibility(View.VISIBLE);
                            } else {
                                knjiznoLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(enota.getText())) {
                                enotaLay.setVisibility(View.VISIBLE);
                            } else {
                                enotaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(nabavna.getText())) {
                                nabavnaLay.setVisibility(View.VISIBLE);
                            } else {
                                nabavnaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(odprtih.getText())) {
                                odprtihLay.setVisibility(View.VISIBLE);
                            } else {
                                odprtihLay.setVisibility(View.GONE);
                            }

                        }
                    }
                });

                btnRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pos >= 1 && pos < 170) {
                            pos = pos + 1;

                            artikel.setText(recordsArrayListfilter.get(pos).getArtikel());
                            dodatek.setText(recordsArrayListfilter.get(pos).getDodatek());
                            polnih.setText(recordsArrayListfilter.get(pos).getPolnih());
                            odprtih.setText(recordsArrayListfilter.get(pos).getOdprtih());
                            teza.setText(recordsArrayListfilter.get(pos).getTeza());
                            dodatkov.setText(recordsArrayListfilter.get(pos).getDodatkov());
                            dodatna.setText(recordsArrayListfilter.get(pos).getDodatna());
                            knjizno.setText(recordsArrayListfilter.get(pos).getKnjizno());
                            popisano.setText(recordsArrayListfilter.get(pos).getPopisano());
                            enota.setText(recordsArrayListfilter.get(pos).getEnota());
                            nabavna.setText(recordsArrayListfilter.get(pos).getNabavna());

                            imgSet(pos);

                            if (!TextUtils.isEmpty(artikel.getText())) {
                                artikelLay.setVisibility(View.VISIBLE);
                            } else {
                                artikelLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(teza.getText())) {
                                tezaLay.setVisibility(View.VISIBLE);
                            } else {
                                tezaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(dodatkov.getText())) {
                                dodatkovLay.setVisibility(View.VISIBLE);
                            } else {
                                dodatkovLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(dodatna.getText())) {
                                dodatnaLay.setVisibility(View.VISIBLE);
                            } else {
                                dodatnaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(knjizno.getText())) {
                                knjiznoLay.setVisibility(View.VISIBLE);
                            } else {
                                knjiznoLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(enota.getText())) {
                                enotaLay.setVisibility(View.VISIBLE);
                            } else {
                                enotaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(nabavna.getText())) {
                                nabavnaLay.setVisibility(View.VISIBLE);
                            } else {
                                nabavnaLay.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(odprtih.getText())) {
                                odprtihLay.setVisibility(View.VISIBLE);
                            } else {
                                odprtihLay.setVisibility(View.GONE);
                            }

                        }
                    }
                });


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if(!TextUtils.isEmpty(dodatek.getText()))
                        String dodateks = dodatek.getText().toString();
                        String polnihs = polnih.getText().toString();
                        String popisanos = popisano.getText().toString();
                        if (recordsArrayListfilter.size() < 150) {
                            myRef.child(String.valueOf(pos)).child("polnih").setValue(polnihs);
                            myRef.child(String.valueOf(pos)).child("popisano").setValue(popisanos);
                            myRef.child(String.valueOf(pos)).child("dodatek").setValue(dodateks);

                        } else {
                            myRef.child(String.valueOf(holder.getAdapterPosition())).child("polnih").setValue(polnihs);
                            myRef.child(String.valueOf(holder.getAdapterPosition())).child("popisano").setValue(popisanos);
                            myRef.child(String.valueOf(holder.getAdapterPosition())).child("dodatek").setValue(dodateks);
                        }
                        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


        holder.dodatek.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("dodatek").setValue(holder.dodatek.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("dodatek").setValue(holder.dodatek.getText().toString());
                    }
                }
            }
        });
        holder.polnih.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("polnih").setValue(holder.polnih.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("polnih").setValue(holder.polnih.getText().toString());
                    }
                }
            }
        });
        holder.odprtih.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("odprtih").setValue(holder.odprtih.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("odprtih").setValue(holder.odprtih.getText().toString());
                    }
                }
            }
        });
        holder.teza.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("teza").setValue(holder.teza.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("teza").setValue(holder.teza.getText().toString());
                    }
                }
            }
        });
        holder.dodatkov.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("dodatkov").setValue(holder.dodatkov.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("dodatkov").setValue(holder.dodatkov.getText().toString());
                    }
                }
            }
        });
        holder.dodatna.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("dodatna").setValue(holder.dodatna.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("dodatna").setValue(holder.dodatna.getText().toString());
                    }
                }
            }
        });
        holder.knjizno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("knjizno").setValue(holder.knjizno.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("knjizno").setValue(holder.knjizno.getText().toString());
                    }
                }
            }
        });
        holder.popisano.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("popisano").setValue(holder.popisano.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("popisano").setValue(holder.popisano.getText().toString());
                    }
                }
            }
        });
        holder.enota.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("enota").setValue(holder.enota.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("enota").setValue(holder.enota.getText().toString());
                    }
                }
            }
        });
        holder.nabavna.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (recordsArrayListfilter.size() < 150) {
                        myRef.child(String.valueOf(pos)).child("nabavna").setValue(holder.nabavna.getText().toString());
                    } else {
                        myRef.child(String.valueOf(holder.getAdapterPosition())).child("nabavna").setValue(holder.nabavna.getText().toString());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordsArrayListfilter.size();
    }

    public void imgSet(int pos) {
        if (pos == 0) {
            imageset.setImageResource(R.drawable.aperol);
        } else if (pos == 1) {
            imageset.setImageResource(R.drawable.bailey);
        } else if (pos == 2) {
            imageset.setImageResource(R.drawable.bensonhedgesblue);
        } else if (pos == 3) {
            imageset.setImageResource(R.drawable.brezkofeinskakavavrecke);
        } else if (pos == 4) {
            imageset.setImageResource(R.drawable.brutprestigebijana075lit);
        } else if (pos == 5) {
            imageset.setImageResource(R.drawable.scurekcabernetsauvignon);
        } else if (pos == 6) {
            imageset.setImageResource(R.drawable.scurekcuvenanardece);
        } else if (pos == 7) {
            imageset.setImageResource(R.drawable.scurekmerlot075lit);
        } else if (pos == 8) {
            imageset.setImageResource(R.drawable.camelcompact);
        } else if (pos == 9) {
            imageset.setImageResource(R.drawable.camelessential);
        } else if (pos == 10) {
            imageset.setImageResource(R.drawable.camelessential2);
        } else if (pos == 11) {
            imageset.setImageResource(R.drawable.canadianclub);
        } else if (pos == 12) {
            imageset.setImageResource(R.drawable.canamineralnavodo033l);
        } else if (pos == 13) {
            imageset.setImageResource(R.drawable.cedevitavrecke);
        } else if (pos == 14) {
            imageset.setImageResource(R.drawable.gaubechardonay);
        } else if (pos == 15) {
            imageset.setImageResource(R.drawable.gaubechardonay075lit);
        } else if (pos == 16) {
            imageset.setImageResource(R.drawable.chivasregal12let);
        } else if (pos == 17) {
            imageset.setImageResource(R.drawable.cocacola);
        } else if (pos == 18) {
            imageset.setImageResource(R.drawable.cocacola025lit);
        } else if (pos == 19) {
            imageset.setImageResource(R.drawable.cocacolazero025lit);
        } else if (pos == 20) {
            imageset.setImageResource(R.drawable.cockta025lit);
        } else if (pos == 21) {
            imageset.setImageResource(R.drawable.corona033lit);
        } else if (pos == 22) {
            imageset.setImageResource(R.drawable.cuttysark);
        } else if (pos == 23) {
            imageset.setImageResource(R.drawable.fresercuvenanabelo);
        } else if (pos == 24) {
            imageset.setImageResource(R.drawable.scurekcuvenanardece);
        } else if (pos == 25) {
            imageset.setImageResource(R.drawable.cvicek);
        } else if (pos == 26) {
            imageset.setImageResource(R.drawable.cynar);
        } else if (pos == 27) {
            imageset.setImageResource(R.drawable.dimple15let);
        } else if (pos == 28) {
            imageset.setImageResource(R.drawable.borovnicevec);
        } else if (pos == 29) {
            imageset.setImageResource(R.drawable.rumdomaci);
        } else if (pos == 30) {
            imageset.setImageResource(R.drawable.dongonzalezchurchill);
        } else if (pos == 31) {
            imageset.setImageResource(R.drawable.dopplerefektrose);
        } else if (pos == 32) {
            imageset.setImageResource(R.drawable.dopplerefektrose075lit);
        } else if (pos == 33) {
            imageset.setImageResource(R.drawable.energijskapijaca025lit);
        } else if (pos == 34) {
            imageset.setImageResource(R.drawable.fanta);
        } else if (pos == 35) {
            imageset.setImageResource(R.drawable.fanta025lit);
        } else if (pos == 36) {
            imageset.setImageResource(R.drawable.fevertreetonic);
        } else if (pos == 37) {
            imageset.setImageResource(R.drawable.freserritoznojcan);
        } else if (pos == 38) {
            imageset.setImageResource(R.drawable.gaubezelenisilvanec);
        } else if (pos == 39) {
            imageset.setImageResource(R.drawable.gaubezelenisilvanec2);
        } else if (pos == 40) {
            imageset.setImageResource(R.drawable.gaubechardonay075lit);
        } else if (pos == 41) {
            imageset.setImageResource(R.drawable.ginbombay);
        } else if (pos == 42) {
            imageset.setImageResource(R.drawable.ginbrin);
        } else if (pos == 43) {
            imageset.setImageResource(R.drawable.gincooperhead);
        } else if (pos == 44) {
            imageset.setImageResource(R.drawable.ginhendrick);
        } else if (pos == 45) {
            imageset.setImageResource(R.drawable.ginlimbayspecialedition);
        } else if (pos == 46) {
            imageset.setImageResource(R.drawable.ginmaister);
        } else if (pos == 47) {
            imageset.setImageResource(R.drawable.ginmare);
        } else if (pos == 48) {
            imageset.setImageResource(R.drawable.ginmonkey47);
        } else if (pos == 49) {
            imageset.setImageResource(R.drawable.ginmonologue);
        } else if (pos == 50) {
            imageset.setImageResource(R.drawable.ginnikkacoffey);
        } else if (pos == 51) {
            imageset.setImageResource(R.drawable.ginnordes);
        } else if (pos == 52) {
            imageset.setImageResource(R.drawable.ginstin);
        } else if (pos == 53) {
            imageset.setImageResource(R.drawable.gintanqueray);
        } else if (pos == 54) {
            imageset.setImageResource(R.drawable.ginungava);
        } else if (pos == 55) {
            imageset.setImageResource(R.drawable.glamour);
        } else if (pos == 56) {
            imageset.setImageResource(R.drawable.glenmorange);
        } else if (pos == 57) {
            imageset.setImageResource(R.drawable.glenmorangelasanta);
        } else if (pos == 58) {
            imageset.setImageResource(R.drawable.glenmornagequinta);
        } else if (pos == 59) {
            imageset.setImageResource(R.drawable.goldberg02stk);
        } else if (pos == 60) {
            imageset.setImageResource(R.drawable.guiness);
        } else if (pos == 61) {
            imageset.setImageResource(R.drawable.heineken);
        } else if (pos == 62) {
            imageset.setImageResource(R.drawable.heinekenstk00);
        } else if (pos == 63) {
            imageset.setImageResource(R.drawable.heinekenstk025l);
        } else if (pos == 64) {
            imageset.setImageResource(R.drawable.heinekenstk04l);
        } else if (pos == 65) {
            imageset.setImageResource(R.drawable.hennessyvs);
        } else if (pos == 66) {
            imageset.setImageResource(R.drawable.hennessyvsop);
        } else if (pos == 67) {
            imageset.setImageResource(R.drawable.hennessyxo);
        } else if (pos == 68) {
            imageset.setImageResource(R.drawable.hisnapenina075lit);
        } else if (pos == 69) {
            imageset.setImageResource(R.drawable.hopsbrew05lit);
        } else if (pos == 70) {
            imageset.setImageResource(R.drawable.idealiststout05l);
        } else if (pos == 71) {
            imageset.setImageResource(R.drawable.jabolcnisok100);
        } else if (pos == 72) {
            imageset.setImageResource(R.drawable.jabolko);
        } else if (pos == 73) {
            imageset.setImageResource(R.drawable.jackdaniels);
        } else if (pos == 74) {
            imageset.setImageResource(R.drawable.jagermeister);
        } else if (pos == 75) {
            imageset.setImageResource(R.drawable.jameson);
        } else if (pos == 76) {
            imageset.setImageResource(R.drawable.johnniewalkerredlabel);
        } else if (pos == 77) {
            imageset.setImageResource(R.drawable.kahlua);
        } else if (pos == 78) {
            imageset.setImageResource(R.drawable.gaubekaspar);
        } else if (pos == 79) {
            imageset.setImageResource(R.drawable.gaubekaspar2);
        } else if (pos == 80) {
            imageset.setImageResource(R.drawable.gaubekaspar075lit);
        } else if (pos == 81) {
            imageset.setImageResource(R.drawable.kava);
        } else if (pos == 82) {
            imageset.setImageResource(R.drawable.freserlaskirizling);
        } else if (pos == 83) {
            imageset.setImageResource(R.drawable.freserlaskirizling075lit);
        } else if (pos == 84) {
            imageset.setImageResource(R.drawable.laskoipa05lit);
        } else if (pos == 85) {
            imageset.setImageResource(R.drawable.laskostk033lit);
        } else if (pos == 86) {
            imageset.setImageResource(R.drawable.spritestk025l);
        } else if (pos == 87) {
            imageset.setImageResource(R.drawable.limbaytonicwater);
        } else if (pos == 88) {
            imageset.setImageResource(R.drawable.macallanamber);
        } else if (pos == 89) {
            imageset.setImageResource(R.drawable.macanudacaffelords);
        } else if (pos == 90) {
            imageset.setImageResource(R.drawable.macanudoinspiradorobusto);
        } else if (pos == 91) {
            imageset.setImageResource(R.drawable.macanudocaffeascot);
        } else if (pos == 92) {
            imageset.setImageResource(R.drawable.macanudoinspiradorobusto);
        } else if (pos == 93) {
            imageset.setImageResource(R.drawable.tequilamandljeva);
        } else if (pos == 94) {
            imageset.setImageResource(R.drawable.martinibianco);
        } else if (pos == 95) {
            imageset.setImageResource(R.drawable.martinirossato);
        } else if (pos == 96) {
            imageset.setImageResource(R.drawable.martinirosso);
        } else if (pos == 97) {
            imageset.setImageResource(R.drawable.martinirosso2);
        } else if (pos == 98) {
            imageset.setImageResource(R.drawable.martinirosso075lit);
        } else if (pos == 99) {
            imageset.setImageResource(R.drawable.scurekmerlot);
        } else if (pos == 100) {
            imageset.setImageResource(R.drawable.scurekmerlot2);
        } else if (pos == 101) {
            imageset.setImageResource(R.drawable.scurekmerlot075lit);
        } else if (pos == 102) {
            imageset.setImageResource(R.drawable.mineralnavoda);
        } else if (pos == 103) {
            imageset.setImageResource(R.drawable.mineralnavoda025lit);
        } else if (pos == 104) {
            imageset.setImageResource(R.drawable.mineralnavoda05l);
        } else if (pos == 105) {
            imageset.setImageResource(R.drawable.fresermladovino);
        } else if (pos == 106) {
            imageset.setImageResource(R.drawable.fresermladovino075lit);
        } else if (pos == 107) {
            imageset.setImageResource(R.drawable.gaubemladovino);
        } else if (pos == 108) {
            imageset.setImageResource(R.drawable.gaubemladovino075lit);
        } else if (pos == 109) {
            imageset.setImageResource(R.drawable.fresermodripinot);
        } else if (pos == 110) {
            imageset.setImageResource(R.drawable.fresermodripinot075lit);
        } else if (pos == 111) {
            imageset.setImageResource(R.drawable.vinakoperpeninamuskatna);
        } else if (pos == 112) {
            imageset.setImageResource(R.drawable.vinakoperpeninamuskatna2);
        } else if (pos == 113) {
            imageset.setImageResource(R.drawable.vinakoperpeninamuskatna075lit);
        } else if (pos == 114) {
            imageset.setImageResource(R.drawable.pelinkovec);
        } else if (pos == 115) {
            imageset.setImageResource(R.drawable.dopplerefektrose075lit);
        } else if (pos == 116) {
            imageset.setImageResource(R.drawable.vinakoperpeninabrut);
        } else if (pos == 117) {
            imageset.setImageResource(R.drawable.vinakoperpeninabrut075lit);
        } else if (pos == 118) {
            imageset.setImageResource(R.drawable.vinakoperpeninabrut075lit2);
        } else if (pos == 119) {
            imageset.setImageResource(R.drawable.puklavecpeninarose);
        } else if (pos == 120) {
            imageset.setImageResource(R.drawable.peninananacatering);
        } else if (pos == 121) {
            imageset.setImageResource(R.drawable.peninananacatering1);
        } else if (pos == 122) {
            imageset.setImageResource(R.drawable.peninananacatering2);
        } else if (pos == 123) {
            imageset.setImageResource(R.drawable.pitu);
        } else if (pos == 124) {
            imageset.setImageResource(R.drawable.pomaranca);
        } else if (pos == 125) {
            imageset.setImageResource(R.drawable.psenicnolasko05l);
        } else if (pos == 126) {
            imageset.setImageResource(R.drawable.freserrenskirizling);
        } else if (pos == 127) {
            imageset.setImageResource(R.drawable.freserrenskirizling075lit);
        } else if (pos == 128) {
            imageset.setImageResource(R.drawable.romeoandjulietamillefleurs);
        } else if (pos == 129) {
            imageset.setImageResource(R.drawable.gauberose);
        } else if (pos == 130) {
            imageset.setImageResource(R.drawable.gauberose2);
        } else if (pos == 131) {
            imageset.setImageResource(R.drawable.gauberose075lit);
        } else if (pos == 132) {
            imageset.setImageResource(R.drawable.rumenimuskat075litcatering);
        } else if (pos == 133) {
            imageset.setImageResource(R.drawable.rumenimusukatcatering);
        } else if (pos == 134) {
            imageset.setImageResource(R.drawable.freserrumenimuskat);
        } else if (pos == 135) {
            imageset.setImageResource(R.drawable.freserrumenimuskat075lit);
        } else if (pos == 136) {
            imageset.setImageResource(R.drawable.gauberumenimuskat);
        } else if (pos == 137) {
            imageset.setImageResource(R.drawable.gauberumenimuskat075lit);
        } else if (pos == 138) {
            imageset.setImageResource(R.drawable.sambuca);
        } else if (pos == 139) {
            imageset.setImageResource(R.drawable.schweppes);
        } else if (pos == 140) {
            imageset.setImageResource(R.drawable.schweppesbitterlemon025lit);
        } else if (pos == 141) {
            imageset.setImageResource(R.drawable.schweppestangerina025lit);
        } else if (pos == 142) {
            imageset.setImageResource(R.drawable.schweppestonic025lit);
        } else if (pos == 143) {
            imageset.setImageResource(R.drawable.fresersivipinot);
        } else if (pos == 144) {
            imageset.setImageResource(R.drawable.fresersivipinot075lit);
        } else if (pos == 145) {
            imageset.setImageResource(R.drawable.vinakoperlsladkimuskat);
        } else if (pos == 146) {
            imageset.setImageResource(R.drawable.vinakopersladkimuskat075lit);
        } else if (pos == 147) {
            imageset.setImageResource(R.drawable.social1st05lit);
        } else if (pos == 148) {
            imageset.setImageResource(R.drawable.sokcatering);
        } else if (pos == 149) {
            imageset.setImageResource(R.drawable.soksteklenicka02l);
        } else if (pos == 150) {
            imageset.setImageResource(R.drawable.spritestk025l);
        } else if (pos == 151) {
            imageset.setImageResource(R.drawable.tequilasrebrna);
        } else if (pos == 152) {
            imageset.setImageResource(R.drawable.sampanjecveuveclicquot075lit);
        } else if (pos == 153) {
            imageset.setImageResource(R.drawable.scurekstarabrajda);
        } else if (pos == 154) {
            imageset.setImageResource(R.drawable.scurekup);
        } else if (pos == 155) {
            imageset.setImageResource(R.drawable.tonicthomashenry02lit);
        } else if (pos == 156) {
            imageset.setImageResource(R.drawable.travarica);
        } else if (pos == 157) {
            imageset.setImageResource(R.drawable.union033lit);
        } else if (pos == 158) {
            imageset.setImageResource(R.drawable.unionnefiltriran);
        } else if (pos == 159) {
            imageset.setImageResource(R.drawable.unionradler05l);
        } else if (pos == 160) {
            imageset.setImageResource(R.drawable.viljamovka);
        } else if (pos == 161) {
            imageset.setImageResource(R.drawable.voda050lit);
        } else if (pos == 162) {
            imageset.setImageResource(R.drawable.vodazokusomstk);
        } else if (pos == 163) {
            imageset.setImageResource(R.drawable.vodkaeristoff);
        } else if (pos == 164) {
            imageset.setImageResource(R.drawable.vrocacokolada);
        } else if (pos == 165) {
            imageset.setImageResource(R.drawable.vrocacokoladabela);
        } else if (pos == 166) {
            imageset.setImageResource(R.drawable.winstonxstyle);
        } else if (pos == 167) {
            imageset.setImageResource(R.drawable.zlataradgonskapenina);
        } else if (pos == 168) {
            imageset.setImageResource(R.drawable.zlataradgonskapenina075lit);
        } else if (pos == 169) {
            imageset.setImageResource(R.drawable.tequilazlata);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView artikel;
        EditText dodatek, polnih, odprtih, teza, dodatkov, dodatna, knjizno, popisano, enota, nabavna;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            artikel = itemView.findViewById(R.id.artikel);
            dodatek = itemView.findViewById(R.id.dodatek);
            polnih = itemView.findViewById(R.id.polnih);
            odprtih = itemView.findViewById(R.id.odprtih);
            teza = itemView.findViewById(R.id.teza);
            dodatkov = itemView.findViewById(R.id.dodatkov);
            dodatna = itemView.findViewById(R.id.dodatna);
            knjizno = itemView.findViewById(R.id.knjizno);
            popisano = itemView.findViewById(R.id.popisano);
            enota = itemView.findViewById(R.id.enota);
            nabavna = itemView.findViewById(R.id.nabavna);
        }
    }

    /*private boolean validate() {
        if (TextUtils.isEmpty(polnih.getText())) {
            polnih.setError("Please enter polnih");
            polnih.requestFocus();
            return false;
        }
        return true;
    }*/

    public String calculate(String val) {
        if (val != null && val.contains("*")) {
            String currentString;
            String calculate;
            if (val.contains(",")) {
                currentString = val.replace(",", ".");
                ;
                String[] separated = currentString.split("\\*");
                float a = Float.valueOf(separated[0]);
                float b = Float.valueOf(separated[1]);
                String temp = String.valueOf(a * b);
                calculate = temp.replace(".", ",");

            } else {
                currentString = val;
                String[] separated = currentString.split("\\*");
                int a = Integer.parseInt(separated[0]);
                int b = Integer.parseInt(separated[1]);
                calculate = String.valueOf(a * b);
            }

            return calculate;
        } else if (val != null && val.contains("+")) {
            String currentString;
            String calculate;
            if (val.contains(",")) {
                currentString = val.replace(",", ".");
                ;
                String[] separated = currentString.split("\\+");
                float a = Float.valueOf(separated[0]);
                float b = Float.valueOf(separated[1]);
                calculate = String.valueOf(a + b);
            } else {
                currentString = val;
                String[] separated = currentString.split("\\+");
                int a = Integer.parseInt(separated[0]);
                int b = Integer.parseInt(separated[1]);
                calculate = String.valueOf(a + b);
            }

            return calculate;
        } else if (val != null && val.contains("-")) {
            String currentString;
            String calculate;
            if (val.contains(",")) {
                currentString = val.replace(",", ".");
                ;
                String[] separated = currentString.split("\\-");
                float a = Float.valueOf(separated[0]);
                float b = Float.valueOf(separated[1]);
                calculate = String.valueOf(a - b);
            } else {
                currentString = val;
                String[] separated = currentString.split("\\-");
                int a = Integer.parseInt(separated[0]);
                int b = Integer.parseInt(separated[1]);
                calculate = String.valueOf(a - b);
            }

            return calculate;
        } else if (val != null && val.contains("/")) {
            String currentString;
            String calculate;
            if (val.contains(",")) {
                currentString = val.replace(",", ".");
                ;
                String[] separated = currentString.split("\\/");
                float a = Float.valueOf(separated[0]);
                float b = Float.valueOf(separated[1]);
                calculate = String.valueOf(a / b);
            } else {
                currentString = val;
                String[] separated = currentString.split("\\/");
                int a = Integer.parseInt(separated[0]);
                int b = Integer.parseInt(separated[1]);
                calculate = String.valueOf(a / b);
            }
            return calculate;
        }
        return val;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String chracter = constraint.toString();
                if (chracter.isEmpty()) {
                    recordsArrayListfilter = recordsArrayList;
                } else {
                    ArrayList<Inventory> filterList = new ArrayList<>();
                    for (Inventory inventory : recordsArrayList) {
                        if (inventory.getArtikel().toLowerCase().contains(chracter.toLowerCase())||inventory.getArtikel().toLowerCase().equalsIgnoreCase(chracter.toLowerCase())) {
                            filterList.add(inventory);
                        }
                    }
                    recordsArrayListfilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = recordsArrayListfilter;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                recordsArrayListfilter = (ArrayList<Inventory>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
