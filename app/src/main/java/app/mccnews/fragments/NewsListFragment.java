package app.mccnews.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import app.mccnews.MainActivityVM;
import app.mccnews.R;
import app.mccnews.views.NewsItemViewHolder;
import app.mccnews.views.NewsListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListFragment extends Fragment implements NewsItemViewHolder.NewsItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "FRAGMENT_NEWS";

    @BindView(R.id.rv_news_list)
    protected RecyclerView newsListView;
    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeContainer;

    private MainActivityVM mainActivityVM;
    private NewsItemViewHolder.NewsItemClickListener newsItemClickListener;

    public NewsListFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        ButterKnife.bind(this, rootView);

        setupNewsList(inflater.getContext());

        return rootView;
    }

    private void setupNewsList(Context context) {
        swipeContainer.setOnRefreshListener(this);

        newsListView.setHasFixedSize(true);
        newsListView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsItemClickListener = (NewsItemViewHolder.NewsItemClickListener) getActivity();

        mainActivityVM = ViewModelProviders.of(getActivity()).get(MainActivityVM.class);

        observeNewsList();
    }

    private void observeNewsList(){
        mainActivityVM.observeNewsList(this, newsList -> {
            swipeContainer.setRefreshing(false);
            if (newsList != null){
                newsListView.setAdapter(new NewsListAdapter(newsList, this));
            }else{
                Toast.makeText(getActivity(), R.string.news_fetch_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDetach() {
        newsItemClickListener = null;
        super.onDetach();
    }

    @Override
    public void newsItemSelected(String externalLink) {
        if (newsItemClickListener != null){
            newsItemClickListener.newsItemSelected(externalLink);
        }
    }

    @Override
    public void onRefresh() {
        mainActivityVM.requestNews();
    }
}
