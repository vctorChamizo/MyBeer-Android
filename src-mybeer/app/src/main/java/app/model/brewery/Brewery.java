package app.model.brewery;

import android.support.annotation.NonNull;
import app.model.status.Status;

public class Brewery {

    private String name;
    private Double[] coordinates;
    private String address;
    private Status status;

    public Brewery(String name, Double[] coordinates, String address, Status status) {

        this.name = name;
        this.coordinates = coordinates;
        this.address = address;
        this.status = status;
    }

    public String getId() { return name.replace(" ","-").toLowerCase(); }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Double[] getCoordinates() { return coordinates; }

    public void setCoordinates(Double[] coordinates) { this.coordinates = coordinates; }

    public Status getStatus() {
        return status;
    }

    public String getAddress() { return address; }

    @NonNull
    @Override
    public String toString(){

        return  name + System.lineSeparator()
                + coordinates[0]+","+ coordinates[1] + System.lineSeparator()
                + address + System.lineSeparator()
                + status.toString();
    }
}
