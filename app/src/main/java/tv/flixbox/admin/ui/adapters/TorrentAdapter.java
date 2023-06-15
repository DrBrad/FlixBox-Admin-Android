package tv.flixbox.admin.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.handler.FToast;
import tv.flixbox.admin.libs.json.variables.JsonArray;

public class TorrentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    private JsonArray json;
    private boolean shimmer = true;

    public TorrentAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view;

        switch(viewType){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_portrait_preview, parent, false);
                return new PortraitViewAdapter(view);

            default:
                view = LayoutInflater.from(context).inflate(R.layout.loading_portrait_preview, parent, false);
                return new ShimmerViewAdapter(view);
        }
    }

    @Override
    public int getItemViewType(int position){
        return (position >= json.size()) ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position){
        if(holder.getItemViewType() == 0){
            PortraitViewAdapter adapter = (PortraitViewAdapter) holder;

            adapter.image.setImageResource(R.drawable.portrait_poster_loading);
            adapter.image.setTag(context.getString(R.string.image_uri)+"portrait/"+json.getJsonObject(position).getString("portrait")+".jpg");
            new LoadImage(adapter.image).execute();
        }
    }

    @Override
    public int getItemCount(){
        if(json == null){
            return 0;
        }

        if(shimmer){
            return json.size()+getDivisible(json.size());
        }

        return json.size();
    }

    public int size(){
        return (json == null) ? 0 : json.size();
    }

    private int getDivisible(int n){
        double z = (double)n/3;
        z = ((z-Math.floor(z))*10)/3;

        return 3-(int)z;
    }

    public void append(JsonArray json){
        if(json.size() < 50){
            shimmer = false;
        }
        if(this.json == null){
            this.json = json;
            notifyDataSetChanged();
            return;
        }

        for(int i = 0; i < json.size(); i++){
            this.json.add(json.get(i));
        }
        notifyItemInserted(this.json.size());
    }

    public void stopShimmer(){
        if(!shimmer){
            return;
        }

        shimmer = false;
        if(json == null){
            notifyDataSetChanged();
            return;
        }
        notifyItemRangeRemoved(json.size(), getDivisible(json.size()));
    }

    public static class PortraitViewAdapter extends RecyclerView.ViewHolder {

        public View view;
        public ImageView image;

        public PortraitViewAdapter(View view){
            super(view);
            this.view = view;
            image = view.findViewById(R.id.image);
        }
    }

    public static class ShimmerViewAdapter extends RecyclerView.ViewHolder {

        public View view;

        public ShimmerViewAdapter(View view){
            super(view);
            this.view = view;
        }
    }
}
