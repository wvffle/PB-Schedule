package net.wvffle.android.pb.schedule.util;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class GenericSuggestionAdapter<T> extends SuggestionsAdapter<T, GenericRecyclerViewAdapter.GenericViewHolder> {
    private final int bindingVariableName;
    private final int layoutId;

    public GenericSuggestionAdapter(LayoutInflater inflater, int bindingVariableName, @LayoutRes int layoutId) {
        super(inflater);
        this.bindingVariableName = bindingVariableName;
        this.layoutId = layoutId;
    }

    @Override
    public void onBindSuggestionHolder(T suggestion, GenericRecyclerViewAdapter.GenericViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(bindingVariableName, suggestion);
        binding.executePendingBindings();
    }

    public int getLayoutId() {
        return layoutId;
    }

    @NonNull
    @Override
    public GenericRecyclerViewAdapter.GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(getLayoutId(), parent, false);
        ViewDataBinding binding = DataBindingUtil.bind(view);
        assert binding != null;

        return new GenericRecyclerViewAdapter.GenericViewHolder(binding);
    }

    public abstract Predicate<T> getPredicate(String term);

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String term = constraint.toString().toLowerCase();

                suggestions = term.length() < 3
                        ? Collections.emptyList()
                        : suggestions_clone.stream()
                        .filter(getPredicate(term))
                        .collect(Collectors.toList());

                results.values = suggestions;
                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                suggestions = (ArrayList<T>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
