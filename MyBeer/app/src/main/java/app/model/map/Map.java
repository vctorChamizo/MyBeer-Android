package app.model.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import com.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.mapboxsdk.Mapbox;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import app.model.address.Address;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class Map {

    private static Map ourInstance = null;
    private static String accessToken;

    private Map() {}

    public static Map getInstance(Context applicationContext) {

        if(ourInstance == null) {

            Mapbox.getInstance(applicationContext, applicationContext.getString(R.string.access_token));

            accessToken =applicationContext.getString(R.string.access_token);
            ourInstance = new Map();
        }

        return ourInstance;
    }

    /**
     * Return the full address with the postcode that have been found
     *
     * @param address the address to search
     *
     * @return a task with the found address list or null if there is an error
     */
    public Task<List<Address>> getFullAddress(String address){

        TaskCompletionSource<List<Address>> taskCompletionSource = new TaskCompletionSource<>();
        MapboxGeocoding query = MapboxGeocoding.builder().accessToken(Map.accessToken).query(address).languages("es").build();

        query.enqueueCall(new Callback<GeocodingResponse>() {

            @Override
            public void onResponse(@NonNull Call<GeocodingResponse> call, @NonNull Response<GeocodingResponse> response) {

                List<CarmenFeature> results = Objects.requireNonNull(response.body()).features();
                List<Address> addresses = new ArrayList<>();

                for(CarmenFeature result : results) {

                    for (int i = 0; result.context() != null && i < Objects.requireNonNull(result.context()).size(); i++)
                        if (Objects.requireNonNull(Objects.requireNonNull(result.context()).get(i).id()).matches("postcode.*"))
                            addresses.add(new Address(result.placeName(), result.center()));
                }

                taskCompletionSource.setResult(addresses);
            }

            @Override
            public void onFailure(@NonNull Call<GeocodingResponse> call, @NonNull Throwable t) { taskCompletionSource.setResult(null); }
        });

        return taskCompletionSource.getTask();
    }
}
