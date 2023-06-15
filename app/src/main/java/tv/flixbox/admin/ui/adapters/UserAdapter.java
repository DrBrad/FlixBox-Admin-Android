package tv.flixbox.admin.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tv.flixbox.admin.R;
import tv.flixbox.admin.libs.json.variables.JsonArray;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.RecyclerViewAdapter> {

    public Context context;
    private JsonArray json;

    public UserAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new RecyclerViewAdapter(view);
    }

    @Override
    public int getItemViewType(int position){
        return (position >= json.size()) ? 1 : 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, @SuppressLint("RecyclerView") int position){
        holder.title.setText(json.getJsonObject(position).getString("title"));
        /*
        holder.description.setText((json.getJsonObject(position).getDouble("percentDone")*100)+"% complete");
        holder.status.setText(getStatusString(json.getJsonObject(position).getInteger("status")));

        holder.peers.setText(json.getJsonObject(position).getInteger("peersConnected")+" Peers");
        holder.eta.setText(formatTime(json.getJsonObject(position).getInteger("eta")));

        holder.progress.setProgress((int) (json.getJsonObject(position).getDouble("percentDone")*1000));
        */
    }

    private String formatTime(int seconds){
        if(seconds < 1){
            return "Unknown";
        }
        double hours = Math.floor(seconds/3600);
        double mins = Math.floor((seconds%3600)/60);
        int remainingSeconds = seconds%60;

        return hours+":"+mins+":"+remainingSeconds;
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
        public TextView title, description, status, peers, eta;
        public ProgressBar progress;

        public RecyclerViewAdapter(View view){
            super(view);
            this.view = view;
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            status = view.findViewById(R.id.status);
            peers = view.findViewById(R.id.peers);
            eta = view.findViewById(R.id.eta);

            progress = view.findViewById(R.id.progress);
        }
    }
}
