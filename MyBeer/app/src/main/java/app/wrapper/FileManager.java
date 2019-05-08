package app.wrapper;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import app.model.beer.Beer;
import app.model.brewery.Brewery;
import app.model.status.Status;

@SuppressWarnings("ALL")
public class FileManager {

    private static final FileManager ourInstance = new FileManager();
    private static Context context;

    private FileManager() {}

    public static FileManager getInstance(Context applicationContext) {

        context = applicationContext;

        return ourInstance;
    }

    /**
     * Find a beer with the id equals to parameter
     *
     * @param id to identifier the beer in data
     *
     * @return the object beer or null if have an exception
     */
    public Beer getBeerById(String id) {

        try {

            File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + "beers", String.valueOf(id));
            BufferedReader br = new BufferedReader(new FileReader(file));

            String name = br.readLine();
            String manufacturer = br.readLine();
            String style = br.readLine();
            Status status = Status.valueOf(br.readLine());

            br.close();

            return new Beer(name,manufacturer,style,status);
        }
        catch (IOException ignored){}

        return null;
    }

    /**
     * Find a brewery with the id equals to parameter
     *
     * @param id to identifier the brewery in data
     *
     * @return the object brewery or null if have an exception
     */
    public Brewery getBreweryById(String id){

        try {

            File file = new File(context.getFilesDir().getAbsolutePath() + File.separator + "breweries", String.valueOf(id));
            BufferedReader br = new BufferedReader(new FileReader(file));

            String name = br.readLine();

            Double[] coordinates = new Double[2];
            String[] coodrinatesString = br.readLine().split(",");
            coordinates[0] = Double.parseDouble(coodrinatesString[0]);
            coordinates[1] = Double.parseDouble(coodrinatesString[1]);
            String address = br.readLine();
            Status status = Status.valueOf(br.readLine());

            br.close();

            return new Brewery(name,coordinates,address,status);
        }
        catch (IOException ignored){}

        return null;
    }

    /**
     * Add new beer in data phone
     *
     * @param newBeer object beer necessary to create a new beer
     *
     * @return a verification to action
     */
    public boolean addBeer(Beer newBeer){

        try {

            checkDirectory("beers");

            File file = new File(context.getFilesDir().getCanonicalPath()+File.separator + "beers", newBeer.getId());
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(newBeer.toString());

            fw.close();

            return true;
        }
        catch (IOException e){ return false; }
    }

    /**
     * Add a new brewery in data phone
     *
     * @param newBrewery the object brewery necessary to create a new brewery
     *
     * @return a verification to action
     */
    public boolean addBrewery(Brewery newBrewery){

        try {

            checkDirectory("breweries");

            File file = new File(context.getFilesDir().getCanonicalPath()+File.separator + "breweries", newBrewery.getId());
            if(!file.exists()) file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(newBrewery.toString());

            fw.close();

            return true;
        }
        catch (IOException e){ return false; }
    }

    /**
     * Get a list with all beers in data phone
     *
     * @return a list of beers or null if have an exception
     */
    public ArrayList<Beer> getAllBeers(){

        try {

            checkDirectory("beers");
            ArrayList<Beer> result = new ArrayList<>();
            File file = new File(context.getFilesDir().getCanonicalPath(), "beers");

            for (String beer : file.list()) result.add(getBeerById(beer));

            return result;
        }
        catch (IOException e){return null;}
    }

    /**
     * Get a list with all breweries in data phone
     *
     * @return a list of breweries or null if have an exception
     */
    public ArrayList<Brewery> getAllBreweries(){

        try {

            checkDirectory("breweries");
            ArrayList<Brewery> result = new ArrayList<>();
            File file = new File(context.getFilesDir().getCanonicalPath(), "breweries");

            for (String breweries : file.list()) result.add(getBreweryById(breweries));

            return result;

        }
        catch (IOException e){ return null; }
    }

    /**
     * Get a list with all beer´s names in data phone
     *
     * @return a list of beer´s name or null if have an exception
     */
    public ArrayList<String> getAllbeersName(){

        try {

            checkDirectory("beers");
            ArrayList<String> result = new ArrayList<>();
            File file = new File(context.getFilesDir().getCanonicalPath(), "beers");

            for (String beer : file.list()) result.add(beer);

            return result;

        }
        catch (IOException e){return null;}
    }

    /**
     * Get a list with all brewery´s names in data phone
     *
     * @return a list of brewery´s name or null if have an exception
     */
    public  ArrayList<String> getAllBreweriesNames(){

        try {

            checkDirectory("breweries");
            ArrayList<String> result = new ArrayList<>();
            File file = new File(context.getFilesDir().getCanonicalPath(), "breweries");

            Collections.addAll(result, file.list());

            return result;

        }
        catch (IOException e){ return null; }
    }

    /**
     * Remove a brewery in data phone
     *
     * @param id the brewery´s id to remove
     *
     */
    public void removeBrewery(String id) {

        try {

            checkDirectory("breweries");
            File file = new File(context.getFilesDir().getCanonicalPath() + File.separator + "breweries", id);
            file.delete();

        }
        catch (IOException e) {}
    }

    /**
     * Remove a beer in data phone
     *
     * @param id id the beer´s id to remove
     *
     */
    public void removeBeer(String id) {

        try {

            checkDirectory("beers");
            File file = new File(context.getFilesDir().getCanonicalPath() + File.separator + "beers", id);
            file.delete();

        }
        catch (IOException e) {}
    }

    /**
     * Verificate the path with the correct path of data phone
     *
     * @param directory the path to try read or write in data phone
     *
     * @throws IOException
     */
    private void checkDirectory(String directory) throws IOException {

        File file = new File(context.getFilesDir().getCanonicalPath(), directory);

        if(!file.exists()) file.mkdir();
    }
}
