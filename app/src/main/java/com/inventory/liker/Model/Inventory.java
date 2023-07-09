package com.inventory.liker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Inventory implements Parcelable {
    String artikel, dodatek, polnih, odprtih, teza, dodatkov, dodatna, knjizno, popisano, enota, nabavna;

    public Inventory() {
    }

    public Inventory(String artikel, String dodatek, String polnih, String odprtih, String teza, String dodatkov, String dodatna, String knjizno, String popisano, String enota, String nabavna) {
        this.artikel = artikel;
        this.dodatek = dodatek;
        this.polnih = polnih;
        this.odprtih = odprtih;
        this.teza = teza;
        this.dodatkov = dodatkov;
        this.dodatna = dodatna;
        this.knjizno = knjizno;
        this.popisano = popisano;
        this.enota = enota;
        this.nabavna = nabavna;
    }

    protected Inventory(Parcel in) {
        artikel = in.readString();
        dodatek = in.readString();
        polnih = in.readString();
        odprtih = in.readString();
        teza = in.readString();
        dodatkov = in.readString();
        dodatna = in.readString();
        knjizno = in.readString();
        popisano = in.readString();
        enota = in.readString();
        nabavna = in.readString();
    }

    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel in) {
            return new Inventory(in);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };

    public String getArtikel() {
        return artikel;
    }

    public void setArtikel(String artikel) {
        this.artikel = artikel;
    }

    public String getDodatek() {
        return dodatek;
    }

    public void setDodatek(String dodatek) {
        this.dodatek = dodatek;
    }

    public String getPolnih() {
        return polnih;
    }

    public void setPolnih(String polnih) {
        this.polnih = polnih;
    }

    public String getOdprtih() {
        return odprtih;
    }

    public void setOdprtih(String odprtih) {
        this.odprtih = odprtih;
    }

    public String getTeza() {
        return teza;
    }

    public void setTeza(String teza) {
        this.teza = teza;
    }

    public String getDodatkov() {
        return dodatkov;
    }

    public void setDodatkov(String dodatkov) {
        this.dodatkov = dodatkov;
    }

    public String getDodatna() {
        return dodatna;
    }

    public void setDodatna(String dodatna) {
        this.dodatna = dodatna;
    }

    public String getKnjizno() {
        return knjizno;
    }

    public void setKnjizno(String knjizno) {
        this.knjizno = knjizno;
    }

    public String getPopisano() {
        return popisano;
    }

    public void setPopisano(String popisano) {
        this.popisano = popisano;
    }

    public String getEnota() {
        return enota;
    }

    public void setEnota(String enota) {
        this.enota = enota;
    }

    public String getNabavna() {
        return nabavna;
    }

    public void setNabavna(String nabavna) {
        this.nabavna = nabavna;
    }

    @NonNull
    @Override
    public String toString() {
        return "Items [artikel=" + artikel + ", dodatek=" + dodatek + ", polnih=" + polnih + ", odprtih=" + odprtih
                + ", teza=" + teza + ", dodatkov=" + dodatkov + ", dodatna=" + dodatna + ", knjizno=" + knjizno + ", popisano="
                + popisano + ", enota=" + enota + ", nabavna" + nabavna + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artikel);
        dest.writeString(dodatek);
        dest.writeString(polnih);
        dest.writeString(odprtih);
        dest.writeString(teza);
        dest.writeString(dodatkov);
        dest.writeString(dodatna);
        dest.writeString(knjizno);
        dest.writeString(popisano);
        dest.writeString(enota);
        dest.writeString(nabavna);
    }
}
