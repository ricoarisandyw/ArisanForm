package com.github.arisanform.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.arisan.helper.PreferenceHelper;
import com.github.arisanform.R;
import com.github.arisanform.model.DB;
import com.github.arisanform.model.Order;

import java.util.List;

/**
 * Created by wijaya on 5/5/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Order> orderList;
    Context context;
    PreferenceHelper preference;
    OnEditListener onEditListener;

    MyAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orderList = orders;
        preference = new PreferenceHelper(context);
    }

    void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Order order = orderList.get(position);
        holder.title.setText(order.getOrderer());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditListener.onEdit(order);
            }
        });
        holder.note.setText(order.getMenu());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.remove(position);
                preference.saveObj(DB.ORDER, orderList);
                MyAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orderList != null)
            return orderList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title, note;
        ImageView delete;

        public ViewHolder(View v) {
            super(v);
            System.out.println("ViewHolder");
            title = v.findViewById(R.id.todo_title);
            note = v.findViewById(R.id.todo_description);
            delete = v.findViewById(R.id.todo_checkbox);
        }
    }

    interface OnEditListener{
        void onEdit(Order order);
    }
}
