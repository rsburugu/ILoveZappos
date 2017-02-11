package com.zappos.consumer.app;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.zappos.consumer.R;
import com.zappos.consumer.services.ZapposAPIService;
import com.zappos.consumer.services.models.ProductsResponse;
import com.zappos.consumer.services.models.Result;
import com.zappos.consumer.utils.AppConstants;
import com.zappos.consumer.utils.NetworkStateReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ramya on 9/2/17.
 */
public class ProductsFragment extends Fragment implements AppConstants, NetworkStateReceiver.NetworkStateReceiverListener {


    @BindView(R.id.products_recycler_view)
    public RecyclerView mProductsRecyclerView;
    @BindView(R.id.search_product)
    public EditText mSearchProduct;
    private View rootView;
    private GridLayoutManager gridLayoutManager;
    private ProductsAdapter productsAdapter;
    private Handler handler = new Handler();

    private String titleName;

    private List<Result> productsList = new ArrayList<>();

    private OnProductItemListener onProductItemListener;
    private NetworkStateReceiver networkStateReceiver;

    private boolean isNetworkFailure = false;
    private String searchTerm;


    @Override
    public void onNetworkAvailable() {
        if (isNetworkFailure) {
            getProductsData(searchTerm);
        }
    }

    @Override
    public void onNetworkUnavailable() {
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        networkStateReceiver = new NetworkStateReceiver(getActivity());
        networkStateReceiver.addListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Set Toolbar Title
     *
     * @param title
     */
    private void setToolbarTitle(String title) {
        ((ProductsActivity) getActivity()).setToolbarTitle(title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_products, container, false);
            init();
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onProductItemListener = (OnProductItemListener) getActivity();

        } catch (ClassCastException ex) {
            Log.v("", "Casting the activity as a OnProductItemListener  failed"
                    + ex);
            onProductItemListener = null;
        }
    }


    public void init() {
        ButterKnife.bind(this, rootView);
        initViews();
        initListeners();
        initObjects();
    }

    /**
     * Initializing views
     */
    private void initViews() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mProductsRecyclerView.setLayoutManager(gridLayoutManager);
        mProductsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Initializing listeners
     */
    private void initListeners() {
        mSearchProduct.addTextChangedListener(textWatcher);
    }

    /**
     * Initializing objects
     */
    private void initObjects() {
        productsAdapter = new ProductsAdapter(getActivity(), onProductItemListener);
        mProductsRecyclerView.setAdapter(productsAdapter);
    }

    /**
     * getProductsData : GET method - get product data from api
     */
    private void getProductsData(String query) {
        if (query != null) {
            isNetworkFailure = false;
            ZapposAPIService zapposAPIService = ZapposApplication.getInstance().getNetworkService().getZapposAPIService();
            Call<ProductsResponse> call = zapposAPIService.getSearchResult(query, "b743e26728e16b81da139182bb2094357c31d331");
            call.enqueue(new Callback<ProductsResponse>() {
                @Override
                public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                    Log.v("Data", "" + response.body());
                    ProductsResponse productsResponse = response.body();
                    productsAdapter.updateData(productsResponse.getResults());
                }

                @Override
                public void onFailure(Call<ProductsResponse> call, Throwable t) {

                }
            });
        }
    }

    TextWatcher textWatcher = new TextWatcher() {

        public void onTextChanged(CharSequence c, int start, int before, int count) {
            if (c.toString().length() > 2) {
                searchTerm = c.toString();
                getProductsData(searchTerm);
            }
            if (c.toString().length() == 0) {
                productsAdapter.updateData(new ArrayList<Result>());
            }
        }

        public void beforeTextChanged(CharSequence c, int start, int count, int after) {
        }

        public void afterTextChanged(Editable c) {
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mProductsRecyclerView.setAdapter(null);
    }


}
