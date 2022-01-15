package net.wvffle.android.pb.schedule.util;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import net.wvffle.android.pb.schedule.BR;

import java.util.ArrayList;
import java.util.List;

public class GenericRecyclerViewAdapter<M> extends RecyclerView.Adapter<GenericRecyclerViewAdapter.GenericViewHolder> {
    private final ViewModel viewModel;
    private final int layoutId;
    private final List<M> data = new ArrayList<>();

    private ItemClickListener onItemClickListener = (View view, int position) -> {
    };

    public GenericRecyclerViewAdapter(ViewModel viewModel, int layoutId) {
        this.viewModel = viewModel;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        holder.bind(viewModel, position, v -> {
            onItemClickListener.onItemClick(v, position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutId;
    }

    public void setOnItemClickListener(ItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<M> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<M> getData() {
        return data;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ViewModel viewModel, int position, View.OnClickListener onClickListener) {
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();

            binding.getRoot().setOnClickListener(onClickListener);
        }
    }
}
