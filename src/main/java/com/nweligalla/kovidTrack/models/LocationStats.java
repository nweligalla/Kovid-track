package com.nweligalla.kovidTrack.models;

public class LocationStats {

    private String country;
    private int latestTotalCases;
    private int differentFromPrevDays;




    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }


    public int getDifferentFromPrevDays() {
        return differentFromPrevDays;
    }

    public void setDifferentFromPrevDays(int differentFromPrevDays) {
        this.differentFromPrevDays = differentFromPrevDays;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                ", country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
