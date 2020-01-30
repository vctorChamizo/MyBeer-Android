package app.model.address;

import com.mapbox.geojson.Point;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {

    private String fullAddress;
    private Point point;

    public Address(String fullAddress, Point point){

        this.fullAddress = fullAddress;
        this.point = point;
    }

    public String getAddress(){
        return this.fullAddress;
    }

    public List<Double> getCoordinates(){return this.point.coordinates();}
}
