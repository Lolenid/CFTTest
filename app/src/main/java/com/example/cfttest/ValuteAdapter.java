package com.example.cfttest;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.ViewHolder> {
    private List<Valute> valutes;
    private Listener listener;

    public interface Listener{
        void onValuteSelected(Valute valute, View view);
    }
    public ValuteAdapter(List<Valute> valutes, Listener listener) {
        this.valutes = valutes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.valute_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Valute valute = valutes.get(position);
        holder.nameText.setText(valute.getName());
        holder.charCodeText.setText(valute.getCharCode());
    }

    @Override
    public int getItemCount() {
        return valutes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView charCodeText;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            charCodeText = itemView.findViewById(R.id.charCodeText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("myTag", "size : " + valutes.size());
                    Log.v("myTag", "getAdapterPosition : " + getAdapterPosition());
                    listener.onValuteSelected(valutes.get(getAdapterPosition()), itemView);
                }
            });
        }
    }
}
