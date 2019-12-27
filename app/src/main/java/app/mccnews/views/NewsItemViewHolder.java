package app.mccnews.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.mccnews.R;
import app.mccnews.models.NewsItemModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private NewsItemClickListener listener;

    public interface NewsItemClickListener {
        void newsItemSelected(NewsItemModel newsItem);
    }

    @BindView(R.id.tv_news_title)
    protected TextView tvTitle;
    @BindView(R.id.tv_news_subtitle)
    protected TextView tvSubtitle;
    @BindView(R.id.iv_thumbnail)
    protected ImageView ivThumbnail;

    private final Context context;
    private NewsItemModel newsItem;

    NewsItemViewHolder(@NonNull View itemView, @NonNull NewsItemClickListener listener) {
        super(itemView);
        this.listener = listener;

        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);

        context = itemView.getContext();
    }

    public void attachNews(NewsItemModel newsItemModel) {
        tvTitle.setText(newsItemModel.getName());
        tvSubtitle.setText(newsItemModel.getSubtitle());
        Glide.with(context)
                .load(newsItemModel.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .centerCrop()
                .into(ivThumbnail);

        this.newsItem = newsItemModel;
    }


    @Override
    public void onClick(View view) {
        if (newsItem != null){
            listener.newsItemSelected(newsItem);
        }
    }
}
