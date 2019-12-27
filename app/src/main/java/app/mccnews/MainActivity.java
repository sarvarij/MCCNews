package app.mccnews;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import org.parceler.Parcels;

import app.mccnews.fragments.NewsListFragment;
import app.mccnews.fragments.NewsPageFragment;
import app.mccnews.models.NewsItemModel;
import app.mccnews.views.NewsItemViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NewsItemViewHolder.NewsItemClickListener {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    protected TextView tvToolbarTitle;
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
        getSupportActionBar().setTitle("");

        mainActivityVM.observePageTitle(this, pageTitle -> {
            tvToolbarTitle.setText(pageTitle.isEmpty() ? getString(R.string.app_title) : pageTitle);
            tvToolbarTitle.setGravity(pageTitle.isEmpty() ? Gravity.CENTER_HORIZONTAL : Gravity.START);
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
    public void newsItemSelected(NewsItemModel newsItem) {
        NewsPageFragment newsPageFragment = new NewsPageFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(NewsPageFragment.ARG_NEWS_ITEM, Parcels.wrap(newsItem));
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
