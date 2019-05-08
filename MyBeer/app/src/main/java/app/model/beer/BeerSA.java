package app.model.beer;

import android.content.Context;
import com.R;
import java.util.ArrayList;
import app.wrapper.FileManager;

@SuppressWarnings("JavaDoc")
public class BeerSA {

    private Context context;

    public BeerSA(Context applicationContext) { context = applicationContext; }

    /**
     * Check the data to add a new brewery
     *
     * @param new_beer object to add a new beer
     *
     * @throws Exception
     */
    public void addBeer(Beer new_beer) throws Exception {

        if(FileManager.getInstance(context).getBeerById(new_beer.getId()) != null) throw new Exception(String.valueOf(R.string.errBeerAlreadyExists));
        else FileManager.getInstance(context).addBeer(new_beer);
    }

    /**
     * Check data to get a list of beers
     *
     * @return the list with beers or an exception
     *
     * @throws Exception
     */
    public ArrayList<Beer> getAllBeers () throws Exception {

        ArrayList<Beer> allBeers = FileManager.getInstance(context).getAllBeers();

        if (allBeers == null) throw new Exception(context.getString(R.string.errDataAccess));
        else return allBeers;
    }

    /**
     * Check data to get a beer
     *
     * @param id the identifier to a beer in the data phone
     *
     * @return a object beer with id param
     *
     * @throws Exception
     */
    public Beer getBeerById(String id) throws Exception {

        Beer result = FileManager.getInstance(context).getBeerById(id);

        if (result == null) throw new Exception(String.valueOf(R.string.errBeerDoesntExists));
        else return result;
    }

    /**
     * Remove a beer
     *
     * @param id the identifier to a beer in the data phone
     *
     * @throws Exception
     */
    public void removeBeer(String id) throws Exception {

        if(FileManager.getInstance(context).getBeerById(id) == null) throw new Exception(String.valueOf(R.string.errBeerDoesntExists));
        else FileManager.getInstance(context).removeBeer(id);
    }
}
