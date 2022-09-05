package ir.nilva.abotorab.view.page.operation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.nilva.abotorab.R;
import ir.nilva.abotorab.helper.CountryModel;

public class CountryAdapter extends ArrayAdapter<CountryModel> {
    private Context context;
    private int resourceId;
    private List<CountryModel> items, tempItems, suggestions;

    public CountryAdapter(@NonNull Context context, int resourceId, ArrayList<CountryModel> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(items);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            CountryModel country = getItem(position);
            TextView persianTextView = view.findViewById(R.id.persian_text_view);
            TextView englishTextView = view.findViewById(R.id.english_text_view);
            persianTextView.setText(country.getName());
            englishTextView.setText(country.getEnName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }

    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            CountryModel country = (CountryModel) resultValue;
            return country.getEnName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (CountryModel country : tempItems) {
                    if (country.getEnName().toLowerCase().startsWith(charSequence.toString().toLowerCase())
                    || country.getName().startsWith(charSequence.toString())) {
                        suggestions.add(country);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<CountryModel> tempValues = (ArrayList<CountryModel>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (CountryModel countryObj : tempValues) {
                    add(countryObj);
                }
                notifyDataSetChanged();
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
