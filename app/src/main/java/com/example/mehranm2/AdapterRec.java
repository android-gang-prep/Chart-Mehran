package com.example.mehranm2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterRec extends RecyclerView.Adapter<AdapterRec.ViewHolder> {

    List<ChartItemData> list;
    CallBack callBack;
    public AdapterRec(List<ChartItemData> list,CallBack callBack) {
        this.list = list;
        this.callBack = callBack;
    }

    public interface CallBack {
        void onClick(ChartItemData chartItemData);
    }

    @NonNull
    @Override
    public AdapterRec.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRec.ViewHolder holder, int position) {
        holder.chart4View.setColor(list.get(position).getColor());
        holder.chart4View.setPercent(list.get(position).getPercent());
        holder.itemView.setOnClickListener(v -> callBack.onClick(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemChart4View chart4View;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chart4View = itemView.findViewById(R.id.progress);
        }
    }
}
