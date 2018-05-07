package com.javacreativeapps.arisanform;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wijaya on 5/5/2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    List<Todo> todoList;
    Context context;
    PreferenceHelper preference;

    TodoAdapter(Context context, List<Todo> todos) {
        this.context = context;
        this.todoList = todos;
        preference = new PreferenceHelper(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Todo todo = todoList.get(position);
        holder.title.setText(todo.getTitle());
        holder.note.setText(todo.getNote());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    todoList.remove(position);
                    preference.update("todo",todoList);
                    ((Activity)context).finish();
                    context.startActivity(((Activity) context).getIntent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (todoList != null)
            return todoList.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView title, note;
        CheckBox checkBox;

        public ViewHolder(View v) {
            super(v);
            System.out.println("ViewHolder");
            title = v.findViewById(R.id.todo_title);
            note = v.findViewById(R.id.todo_description);
            checkBox = v.findViewById(R.id.todo_checkbox);
        }
    }
}
