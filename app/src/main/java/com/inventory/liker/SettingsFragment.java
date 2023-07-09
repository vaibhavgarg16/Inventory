package com.inventory.liker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.inventory.liker.Api.SharePrefrancClass;


public class SettingsFragment extends Fragment {

    CheckBox Artikel, Dodatek, polnih, odprtih, teza, dodatkov, dodatna, knjizno, popisano, enota, nabavna;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        nabavna = root.findViewById(R.id.nabavna);
        enota = root.findViewById(R.id.enota);
        popisano = root.findViewById(R.id.popisano);
        knjizno = root.findViewById(R.id.knjizno);
        dodatna = root.findViewById(R.id.dodatna);
        dodatkov = root.findViewById(R.id.dodatkov);
        teza = root.findViewById(R.id.teza);
        odprtih = root.findViewById(R.id.odprtih);
        polnih = root.findViewById(R.id.polnih);
        Dodatek = root.findViewById(R.id.Dodatek);
        Artikel = root.findViewById(R.id.Artikel);

        Artikel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Artikel.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("artikelCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("artikelCheck", "0");
                }
            }
        });

        Dodatek.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (Dodatek.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatekCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatekCheck", "0");
                }
            }
        });

        polnih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (polnih.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("polnihCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("polnihCheck", "0");
                }
            }
        });

        odprtih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (odprtih.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("odprtihCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("odprtihCheck", "0");
                }
            }
        });

        teza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (teza.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("tezaCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("tezaCheck", "0");
                }
            }
        });

        dodatkov.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dodatkov.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatkovCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatkovCheck", "0");
                }
            }
        });

        dodatna.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dodatna.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatnaCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("dodatnaCheck", "0");
                }
            }
        });

        knjizno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (knjizno.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("knjiznoCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("knjiznoCheck", "0");
                }
            }
        });

        popisano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (popisano.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("popisanoCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("popisanoCheck", "0");
                }
            }
        });

        enota.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (enota.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("enotaCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("enotaCheck", "0");
                }
            }
        });

        nabavna.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (nabavna.isChecked()) {
                    SharePrefrancClass.getInstance(getActivity()).savePref("nabavnaCheck", "1");
                } else {
                    SharePrefrancClass.getInstance(getActivity()).savePref("nabavnaCheck", "0");
                }
            }
        });

        /*if (Artikel.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("artikelCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("artikelCheck", "0");
        }
        if (Dodatek.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatekCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatekCheck", "0");
        }
        if (polnih.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("polnihCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("polnihCheck", "0");
        }
        if (odprtih.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("odprtihCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("odprtihCheck", "0");
        }
        if (teza.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("tezaCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("tezaCheck", "0");
        }
        if (dodatkov.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatkovCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatkovCheck", "0");
        }
        if (dodatna.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatnaCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("dodatnaCheck", "0");
        }
        if (knjizno.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("knjiznoCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("knjiznoCheck", "0");
        }
        if (popisano.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("popisanoCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("popisanoCheck", "0");
        }
        if (enota.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("enotaCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("enotaCheck", "0");
        }
        if (nabavna.isChecked()) {
            SharePrefrancClass.getInstance(getActivity()).savePref("nabavnaCheck", "1");
        } else {
            SharePrefrancClass.getInstance(getActivity()).savePref("nabavnaCheck", "0");
        }
*/


        if(SharePrefrancClass.getInstance(getActivity()).getPref("artikelCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("artikelCheck").equals("1")){
            Artikel.setChecked(true);
        }else {
            Artikel.setChecked(false);
        }

        if(SharePrefrancClass.getInstance(getActivity()).getPref("dodatekCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("dodatekCheck").equals("1")){
            Dodatek.setChecked(true);
        }else {
            Dodatek.setChecked(false);
        }

        if(SharePrefrancClass.getInstance(getActivity()).getPref("polnihCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("polnihCheck").equals("1")){
            polnih.setChecked(true);
        }else {
            polnih.setChecked(false);
        }
        if(SharePrefrancClass.getInstance(getActivity()).getPref("odprtihCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("odprtihCheck").equals("1")){
            odprtih.setChecked(true);
        }else {
            odprtih.setChecked(false);
        }

        if(SharePrefrancClass.getInstance(getActivity()).getPref("tezaCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("tezaCheck").equals("1")){
            teza.setChecked(true);
        }else {
            teza.setChecked(false);
        }

        if(SharePrefrancClass.getInstance(getActivity()).getPref("dodatkovCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("dodatkovCheck").equals("1")){
            dodatkov.setChecked(true);
        }else {
            dodatkov.setChecked(false);
        }

        if(SharePrefrancClass.getInstance(getActivity()).getPref("dodatnaCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("dodatnaCheck").equals("1")){
            dodatna.setChecked(true);
        }else {
            dodatna.setChecked(false);
        }


        if(SharePrefrancClass.getInstance(getActivity()).getPref("knjiznoCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("knjiznoCheck").equals("1")){
            knjizno.setChecked(true);
        }else {
            knjizno.setChecked(false);
        }


        if(SharePrefrancClass.getInstance(getActivity()).getPref("popisanoCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("popisanoCheck").equals("1")){
            popisano.setChecked(true);
        }else {
            popisano.setChecked(false);
        }


        if(SharePrefrancClass.getInstance(getActivity()).getPref("enotaCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("enotaCheck").equals("1")){
            enota.setChecked(true);
        }else {
            enota.setChecked(false);
        }


        if(SharePrefrancClass.getInstance(getActivity()).getPref("nabavnaCheck")!=null
                && SharePrefrancClass.getInstance(getActivity()).getPref("nabavnaCheck").equals("1")){
            nabavna.setChecked(true);
        }else {
            nabavna.setChecked(false);
        }

        return root;
    }
}