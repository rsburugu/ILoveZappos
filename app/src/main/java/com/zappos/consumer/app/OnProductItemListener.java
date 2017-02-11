package com.zappos.consumer.app;

import android.view.View;

import com.zappos.consumer.services.models.Result;

/**
 * Created by Ramya on 9/2/17.
 */
public interface OnProductItemListener {
    void onProductClick(View v, Result item);
}
