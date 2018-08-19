package pl.butajlo.androidadvanced.trending;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.butajlo.androidadvanced.R;
import pl.butajlo.androidadvanced.models.Repo;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {

    private final RepoClickedListener listener;
    private final List<Repo> data = new ArrayList<>();

    RepoAdapter(RepoClickedListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_repo_list_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    public void setData(List<Repo> repos) {
        if(repos != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new RepoDiffCallback(data, repos));
            data.clear();
            data.addAll(repos);
            diffResult.dispatchUpdatesTo(this);
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_repo_name) TextView tvRepoName;
        @BindView(R.id.tv_repo_description) TextView tvRepoDescription;
        @BindView(R.id.tv_star_count) TextView tvStarCount;
        @BindView(R.id.tv_fork_count) TextView tvForkCount;

        private Repo repo;

        public ViewHolder(View itemView, RepoClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                if(repo != null) {
                    listener.onRepoClicked(repo);
                }
            });

        }

        void bind(Repo repo) {
            this.repo = repo;

            tvRepoName.setText(repo.getName());
            tvRepoDescription.setText(repo.getDescription());
            tvForkCount.setText(String.valueOf(repo.getForksCount()));
            tvStarCount.setText(String.valueOf(repo.getStargazersCount()));
        }
    }

    interface RepoClickedListener {
        void onRepoClicked(Repo repo);
    }
}
