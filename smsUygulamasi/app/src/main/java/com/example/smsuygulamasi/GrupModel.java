package com.example.smsuygulamasi;

import java.util.List;

public class GrupModel {
    private String isim, aciklama, resim, uid;
    private List<String> numaralar;

    public GrupModel() {

    }

    public  GrupModel(String isim , String aciklama , String resim, List<String> numaralar , String uid ) {
        this.isim = isim;
        this.aciklama = aciklama;
        this.resim = resim;
        this.numaralar = numaralar;
        this.uid = uid;
    }

    public  String getName() {
        return isim;
    }
    public  void  setName(String isim) {
        this.isim = isim;
    }
    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getResim(){
        return resim;
    }

    public List<String> getNumaralar() {
        return numaralar;
    }

    public void setNumaralar(List<String> numaralar){
        this.numaralar =numaralar;
    }

    public  String getUid () {
        return uid;
    }
    public void setUid(String uid) {
        this.uid=uid;
    }
}
