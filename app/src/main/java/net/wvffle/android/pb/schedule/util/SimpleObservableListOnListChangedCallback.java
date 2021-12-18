package net.wvffle.android.pb.schedule.util;

import androidx.databinding.ObservableList;

public class SimpleObservableListOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
    private final Executor executor;

    public SimpleObservableListOnListChangedCallback(Executor executor) {
        super();
        this.executor = executor;
    }

    @Override
    public void onChanged(ObservableList sender) {
        executor.execute(sender);
    }

    @Override
    public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
        executor.execute(sender);
    }

    @Override
    public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
        executor.execute(sender);
    }

    @Override
    public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
        executor.execute(sender);
    }

    @Override
    public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
        executor.execute(sender);
    }

    public interface Executor {
        void execute(ObservableList sender);
    }
}
