package tv.flixbox.admin.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.json.variables.JsonArray;

public class TorrentAdapter extends RecyclerView.Adapter<TorrentAdapter.RecyclerViewAdapter> {

    public Context context;
    private JsonArray json;

    public TorrentAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_torrent, parent, false);
        return new RecyclerViewAdapter(view);
    }

    @Override
    public int getItemViewType(int position){
        return (position >= json.size()) ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, @SuppressLint("RecyclerView") int position){
        holder.title.setText(json.getJsonObject(position).getString("name"));
        /*
        if(holder.getItemViewType() == 0){
            PortraitViewAdapter adapter = (PortraitViewAdapter) holder;

            adapter.image.setImageResource(R.drawable.portrait_poster_loading);
            adapter.image.setTag(context.getString(R.string.image_uri)+"portrait/"+json.getJsonObject(position).getString("portrait")+".jpg");
            new LoadImage(adapter.image).execute();
        }
        */
    }

    @Override
    public int getItemCount(){
        return (json == null) ? 0 : json.size();
    }

    public void setJson(JsonArray json){
        this.json = json;
        notifyDataSetChanged();
    }

    public static class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        public View view;
        public ImageView image;
        public TextView title;

        public RecyclerViewAdapter(View view){
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
        }
    }
}
