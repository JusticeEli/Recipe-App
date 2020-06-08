package com.justice.recipeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements Filterable {
    private List<Food> filteredList = new ArrayList<>();

    private List<Food> list;
    private Context context;

    public MainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.foodNameTxtView.setText(list.get(position).getFoodName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.update=true;
                ApplicationClass.originalFood = list.get(position);
                context.startActivity(new Intent(context, AddFoodActivity.class));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.toString().isEmpty()) {
                filterResults.values = new ArrayList<>(ApplicationClass.foodList);

                return filterResults;

            }

            filteredList.clear();
            for (Food food : ApplicationClass.foodList) {
                if (food.getFoodName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    filteredList.add(food);

                }

            }
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            list = (List) results.values;
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTxtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTxtView = itemView.findViewById(R.id.foodNameTxtView);
        }
    }

    public void setList(List<Food> foodList) {
        this.list = foodList;
    }

    public List<Food> getList() {
        return list;
    }

}
