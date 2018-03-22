package com.example.jakub.zadanie3b;

public class Pogoda {

    private String miasto;
    private String temperatura;
    private String wilgotnosc;
    private String cisnienie;
    private String zachmurzenie;
    private String opad;

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public void setWilgotnosc(String wilgotnosc) {
        this.wilgotnosc = wilgotnosc;
    }

    public void setCisnienie(String cisnienie) {
        this.cisnienie = cisnienie;
    }

    public void setZachmurzenie(String zachmurzenie) {
        this.zachmurzenie = zachmurzenie;
    }

    public void setOpad(String opad) {
        this.opad = opad;
    }

    public Pogoda() {
    }


    public String getMiasto() {
        return miasto;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public String getWilgotnosc() {
        return wilgotnosc;
    }

    public String getCisnienie() {
        return cisnienie;
    }

    public String getZachmurzenie() {
        return zachmurzenie;
    }

    public String getOpad() {
        return opad;
    }
}
