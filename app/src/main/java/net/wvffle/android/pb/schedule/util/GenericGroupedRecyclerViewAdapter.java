package net.wvffle.android.pb.schedule.util;


import androidx.annotation.LayoutRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class GenericGroupedRecyclerViewAdapter<T> extends GenericRecyclerViewAdapter<GroupedItem<T>> {
    LiveData<List<GroupedItem<T>>> dataset;
    int groupLayoutId;
    int layoutId;

    public GenericGroupedRecyclerViewAdapter(ViewModel viewModel, @LayoutRes int groupLayoutId, @LayoutRes int layoutId) {
        super(viewModel, layoutId);
        this.groupLayoutId = groupLayoutId;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        if (getData().get(position).isGroup()) {
            return groupLayoutId;
        }

        return layoutId;
    }
}
