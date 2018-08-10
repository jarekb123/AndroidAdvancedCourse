package pl.butajlo.androidadvanced.trending;

import android.support.v7.util.DiffUtil;

import java.util.List;

import pl.butajlo.androidadvanced.models.Repo;

class RepoDiffCallback extends DiffUtil.Callback {


    private final List<Repo> oldData;
    private final List<Repo> newData;

    public RepoDiffCallback(List<Repo> oldData, List<Repo> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).id().equals(newData.get(newItemPosition).id());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).equals(newData.get(newItemPosition));
    }
}
