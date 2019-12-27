package app.mccnews.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.parceler.Parcels;

import app.mccnews.MainActivityVM;
import app.mccnews.R;
import app.mccnews.models.NewsItemModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsPageFragment extends Fragment {

    public static final String TAG = "FRAGMENT_NEWS_PAGE";
    public static final String ARG_NEWS_ITEM = "ARG_NEWS_ITEM";

    private NewsItemModel newsItem;

    @BindView(R.id.web_view)
    protected WebView webView;

    private MainActivityVM mainActivityVM;

    public NewsPageFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parseArguments();

        View rootView = inflater.inflate(R.layout.fragment_news_page, container, false);

        ButterKnife.bind(this, rootView);

        setupWebView();

        return rootView;
    }

    private void parseArguments(){
        newsItem = Parcels.unwrap(getArguments().getParcelable(ARG_NEWS_ITEM));
    }

    private void setupWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivityVM = ViewModelProviders.of(getActivity()).get(MainActivityVM.class);

        mainActivityVM.setPageTitle(newsItem.getSubtitle());
        webView.loadUrl(newsItem.getExternalLink());
    }

    @Override
    public void onDetach() {
        mainActivityVM.setPageTitle("");
        super.onDetach();
    }
}
