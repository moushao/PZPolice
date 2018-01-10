package com.pvirtech.pzpolice.test.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseActivity;

public class CollapsingToolbarLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        initTitleView("aaaaaaaaa");
//        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * 共享元素的动画
     * <ImageView
     * …
     * android:transitionName="@string/transition_album_cover" />
     * <p>
     * <p>
     * Intent intent = new Intent();
     * String transitionName = getString(R.string.transition_album_cover);
     * …
     * ActivityOptionsCompat options =
     * ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
     * albumCoverImageView,   // The view which starts the transition
     * transitionName    // The transitionName of the view we’re transitioning to
     * );
     * ActivityCompat.startActivity(activity, intent, options.toBundle());
     */


}
