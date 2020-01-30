package app.model.beer;

import android.support.annotation.NonNull;
import app.model.status.Status;

public class Beer {

    private String name;
    private String manufacturer;
    private String style;
    private Status status;

    public Beer(String name, String manufacturer, String style, Status status) {

        this.name = name;
        this.manufacturer = manufacturer;
        this.style = style;
        this.status = status;
    }

    public String getId() { return name.replace(" ","-").toLowerCase(); }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getStyle() { return style; }

    public void setStyle(String style) { this.style = style; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public String getManufacturer() {return this.manufacturer; }

    @NonNull
    @Override
    public String toString(){

        return  name + System.lineSeparator() +
                style+System.lineSeparator() +
                manufacturer+System.lineSeparator() +
                status.toString();
    }
}
