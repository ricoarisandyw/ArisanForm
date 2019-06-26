package com.github.arisan.adapter;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class AutocompleteAdapter extends ArrayAdapter {

    List<String> allCodes;
    List<String> originalCodes;
    StringFilter filter;

    AutocompleteAdapter(Context context, int resource, List<String> keys){
        super(context,resource,keys);
        allCodes=keys;
        originalCodes=keys;
    }

    public int getCount() {
        return allCodes.size();
    }

    public String getItem(int position) {
        return allCodes.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    private class StringFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<String> list = originalCodes;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<>(count);
            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            allCodes = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }

    @Override
    public Filter getFilter()
    {
        return new StringFilter();
    }
}
