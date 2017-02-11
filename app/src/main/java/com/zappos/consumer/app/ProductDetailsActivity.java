package com.zappos.consumer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zappos.consumer.R;
import com.zappos.consumer.services.models.Result;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramya on 9/2/17.
 */
public class ProductDetailsActivity extends BaseActivity implements OnProductItemListener {

    ProductDetailsFragment productDetailsFragment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Result product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setBackNavigationIcon();
        if (savedInstanceState == null) {
            productDetailsFragment = new ProductDetailsFragment();
            Bundle bundle = new Bundle();
            productDetailsFragment.setArguments(bundle);
            product = getIntent().getParcelableExtra(IBundleParams.RESULT_OBJ);
            bundle.putParcelable(IBundleParams.RESULT_OBJ, getIntent().getParcelableExtra(IBundleParams.RESULT_OBJ));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.product_details_container, productDetailsFragment)
                    .commit();
        }
    }


    @Override
    public void onProductClick(View v, Result item) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                shareTextUrl(product.getProductName(), product.getProductUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (productDetailsFragment instanceof IOnFocusListenable) {
            ((IOnFocusListenable) productDetailsFragment).onWindowFocusChanged(hasFocus);
        }
    }

    private void shareTextUrl(String title, String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link to..."));
    }
}
