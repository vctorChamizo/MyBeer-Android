package app.activities.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.R;
import java.util.ArrayList;
import app.activities.beer.InformationBeer;
import app.activities.brewery.InformationBrewery;
import app.model.beer.Beer;
import app.model.beer.BeerSA;
import app.model.brewery.Brewery;
import app.model.brewery.BrewerySA;
import app.model.status.Status;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String BEER = "beer";
    private static final String BREWERY = "brewery";

    private ArrayList<Beer> list_beers;
    private ArrayList<Brewery> list_breweries;
    private String type;

    public RecyclerViewAdapter(ArrayList<?> list) {

        if (list.get(0) instanceof Beer){

            this.list_beers = (ArrayList<Beer>) list;
            this.type = BEER;
        }
        else {

            this.list_breweries = (ArrayList<Brewery>) list;
            this.type = BREWERY;
        }
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private CardView card;
        private TextView name;
        private ImageView img;
        private String type;
        private ImageButton exitButton;

        TextView id;

        public abstract void removeItem(String id);

        ViewHolder(View v, String type) {

            super(v);
            this.context = v.getContext();
            this.type = type;

            this.card = v.findViewById(R.id.cardview_card);
            this.name = v.findViewById(R.id.cardview_name);
            this.img = v.findViewById(R.id.cardview_img);
            this.id = v.findViewById(R.id.cardview_id);
            this.exitButton = v.findViewById(R.id.closeButton);

        }

        void setOnClickListeners() {

            this.exitButton.setOnClickListener(v -> {

                @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> at = new AsyncTask<Void, Void, Void>() {

                    private String error = null;

                    @Override
                    protected Void doInBackground(Void... voids) {

                        try {

                            if (type.equals(BEER)) (new BeerSA(context)).removeBeer(id.getText().toString());
                            else (new BrewerySA(context)).removeBrewery(id.getText().toString());
                        }
                        catch (Exception e){ error = e.getMessage(); }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void v){

                        if(error!=null)Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        else removeItem(id.getText().toString());

                        notifyDataSetChanged();
                    }
                };

                at.execute();
            });

            this.card.setOnClickListener(v -> {

                Intent intent;

                if (this.type.equals(BEER)){

                    intent = new Intent(this.context, InformationBeer.class);
                    intent.putExtra("id", this.id.getText());
                }
                else {

                    intent = new Intent(this.context, InformationBrewery.class);
                    intent.putExtra("id", this.id.getText());
                }

                this.context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false), this.type) {

            @Override
            public void removeItem(String id) {

                int i;

                if(type.equals(BEER)) {

                    for(i = 0; i < list_beers.size() && !list_beers.get(i).getId().equals(this.id.getText().toString()); i++){}

                    list_beers.remove(i);

                }
                else {

                    for(i = 0; i < list_breweries.size() && !list_breweries.get(i).getId().equals(this.id.getText().toString()); i++){}

                    list_breweries.remove(i);
                }

                notifyDataSetChanged();
            }
        };
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (this.type.equals(BEER)) {

            holder.name.setText(this.list_beers.get(position).getName());
            holder.id.setText(this.list_beers.get(position).getId());

            if (this.list_beers.get(position).getStatus() == Status.LIKED) holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.visited));
            else if (this.list_beers.get(position).getStatus() == Status.DISLIKED) holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.disliked));
            else holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.no_visited));
        }
        else {

            holder.name.setText(this.list_breweries.get(position).getName());
            holder.id.setText(this.list_breweries.get(position).getId());

            if (this.list_breweries.get(position).getStatus() == Status.LIKED) holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.visited));
            else if (this.list_breweries.get(position).getStatus() == Status.DISLIKED) holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.disliked));
            else holder.img.setImageDrawable(holder.context.getDrawable(R.drawable.no_visited));
        }

        holder.setOnClickListeners();
    }


    @Override
    public int getItemCount() {

        if (this.type.equals(BEER)) return this.list_beers.size();
        else return this.list_breweries.size();
    }
}
