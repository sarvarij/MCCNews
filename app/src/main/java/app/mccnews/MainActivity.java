package app.mccnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.FrameLayout;

import app.mccnews.fragments.NewsListFragment;
import app.mccnews.fragments.NewsPageFragment;
import app.mccnews.views.NewsItemViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NewsItemViewHolder.NewsItemClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.main_frame)
    protected FrameLayout mainFrame;

    private MainActivityVM mainActivityVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mainActivityVM = ViewModelProviders.of(this).get(MainActivityVM.class);

        setupToolbar();

        if (!isListFragmentCreated()){
            createListFragment();
            mainActivityVM.requestNews();
        }
    }

    private void setupToolbar(){
        mainActivityVM.observePageTitle(this, pageTitle -> {
            toolbar.setTitle(pageTitle.isEmpty() ? getString(R.string.app_name) : pageTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(!pageTitle.isEmpty());
        });
    }

    private void createListFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame, new NewsListFragment(), NewsListFragment.TAG)
                .commit();
    }

    private boolean isListFragmentCreated(){
        return getSupportFragmentManager()
                .findFragmentByTag(NewsListFragment.TAG) != null;
    }

    @Override
    public void newsItemSelected(String externalLink) {
        NewsPageFragment newsPageFragment = new NewsPageFragment();
        Bundle arguments = new Bundle();
        arguments.putString(NewsPageFragment.ARG_LINK, externalLink);
        newsPageFragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_frame, newsPageFragment, NewsPageFragment.TAG)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
