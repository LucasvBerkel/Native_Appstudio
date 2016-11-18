package nl.yrck.mprog_watchlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import nl.yrck.mprog_watchlist.api.Movie;


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static ClickListener clickListener;
    private List<Movie> movies;
    private Context context;

    // Provide a suitable constructor (depends on the kind of dataset)
    RecyclerAdapter(List<Movie> myDataset, Context myContext) {
        movies = myDataset;
        context = myContext;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapter.clickListener = clickListener;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie movie = movies.get(position);

        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.no_poster)
                .into(holder.cardPoster);

        holder.cardTitle.setText(movie.getTitle());
        holder.cardYear.setText(movie.getYear());
        holder.cardType.setText(movie.getType());

        holder.itemView.setTag(movie.getImdbID());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        ImageView cardPoster;
        TextView cardTitle;
        TextView cardYear;
        TextView cardType;

        ViewHolder(View v) {
            super(v);
            cardPoster = (ImageView) v.findViewById(R.id.card_poster);
            cardTitle = (TextView) v.findViewById(R.id.card_title);
            cardYear = (TextView) v.findViewById(R.id.card_year);
            cardType = (TextView) v.findViewById(R.id.card_type);

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
