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
        //holder.title.setText(json.getJsonObject(position).getString("title"));
        /*
        holder.description.setText((json.getJsonObject(position).getDouble("percentDone")*100)+"% complete");
        holder.status.setText(getStatusString(json.getJsonObject(position).getInteger("status")));

        holder.peers.setText(json.getJsonObject(position).getInteger("peersConnected")+" Peers");
        holder.eta.setText(formatTime(json.getJsonObject(position).getInteger("eta")));

        holder.progress.setProgress((int) (json.getJsonObject(position).getDouble("percentDone")*1000));
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
        public TextView name, email, role, date;

        public RecyclerViewAdapter(View view){
            super(view);
            this.view = view;
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            role = view.findViewById(R.id.role);
            date = view.findViewById(R.id.date);
        }
    }
}
