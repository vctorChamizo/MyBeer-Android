package app.model.brewery;

import android.content.Context;
import com.R;
import java.util.ArrayList;
import app.wrapper.FileManager;

@SuppressWarnings("JavaDoc")
public class BrewerySA {

    private Context context;

    public BrewerySA(Context applicationContext){
        context = applicationContext;
    }

    /**
     * Check the data to add a new brewery
     *
     * @param brewery object to add a new brewery
     *
     * @throws Exception
     */
    public void addBrewery(Brewery brewery) throws Exception {

        Brewery b = FileManager.getInstance(context).getBreweryById(brewery.getId());

        if(b != null) throw new Exception((context.getString(R.string.errBreweryAlreadyExists)));
        else FileManager.getInstance(context).addBrewery(brewery);
    }

    /**
     * Check data to get a list of breweries
     *
     * @return the list with breweries or an exception
     *
     * @throws Exception
     */
    public ArrayList<Brewery> getAllBreweries() throws Exception {

        ArrayList<Brewery> allBreweries = FileManager.getInstance(context).getAllBreweries();

        if(allBreweries == null) throw new Exception(context.getString(R.string.errDataAccess));
        else return allBreweries;
    }

    /**
     * Check data to get a brewery
     *
     * @param id the identifier to a brewery in the data phone
     *
     * @return a object brewery with id param
     *
     * @throws Exception
     */
    public Brewery getBreweryById(String id) throws Exception {

        Brewery result = FileManager.getInstance(context).getBreweryById(id);

        if (result == null) throw new Exception(String.valueOf(R.string.errBreweryNotFound));
        else return result;
    }

    /**
     * Remove a brewery
     *
     * @param id the identifier to a brewery in the data phone
     *
     * @throws Exception
     */
    public void removeBrewery(String id) throws Exception {

        if(FileManager.getInstance(context).getBreweryById(id) == null) throw new Exception(String.valueOf(R.string.errBreweryNotFound));
        else FileManager.getInstance(context).removeBrewery(id);
    }
}
