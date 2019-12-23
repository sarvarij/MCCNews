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

import app.mccnews.MainActivityVM;
import app.mccnews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsPageFragment extends Fragment {

    public static final String TAG = "FRAGMENT_NEWS_PAGE";
    public static final String ARG_LINK = "ARG_LINK";

    @BindView(R.id.web_view)
    protected WebView webView;

    private MainActivityVM mainActivityVM;

    public NewsPageFragment() { }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_news_page, container, false);

        ButterKnife.bind(this, rootView);

        setupWebView();

        webView.loadUrl(getArguments().getString(ARG_LINK));

        return rootView;
    }

    private void setupWebView(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mainActivityVM != null) {
                    mainActivityVM.setPageTitle(webView.getTitle());
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivityVM = ViewModelProviders.of(getActivity()).get(MainActivityVM.class);
    }

    @Override
    public void onDetach() {
        mainActivityVM.setPageTitle("");
        super.onDetach();
    }
}
