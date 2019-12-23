package app.mccnews.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.mccnews.R;
import app.mccnews.models.NewsItemModel;

public class NewsListAdapter extends RecyclerView.Adapter<NewsItemViewHolder>
    implements NewsItemViewHolder.NewsItemClickListener
{

    private List<NewsItemModel> newsList;
    private NewsItemViewHolder.NewsItemClickListener listener;

    public NewsListAdapter(List<NewsItemModel> newsList, @NonNull NewsItemViewHolder.NewsItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new NewsItemViewHolder(newsItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        holder.attachNews(newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void newsItemSelected(String externalLink) {
        listener.newsItemSelected(externalLink);
    }
}
