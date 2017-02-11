package com.zappos.consumer.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zappos.consumer.R;
import com.zappos.consumer.services.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramya on 9/2/17.
 */
public class ProductsActivity extends BaseActivity implements OnProductItemListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
    }

    /**
     * Method used to set the toolbar title
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }


    public String getToolbarTitle() {
        return mToolbarTitle.getText().toString();
    }

    @Override
    public void onProductClick(View v, Result item) {
        String transitionName = getString(R.string.transition_string);
        ImageView viewStart = null;
        if (v != null) {
            viewStart = (ImageView) v.findViewById(R.id.product_image);
        }
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(IBundleParams.RESULT_OBJ, (Parcelable) item);
        if (viewStart != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options =

                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            viewStart,
                            transitionName
                    );
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

}
