package com.zappos.consumer.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zappos.consumer.R;
import com.zappos.consumer.services.models.Result;
import com.zappos.consumer.utils.Animations;
import com.zappos.consumer.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ramya on 9/2/17.
 */
public class ProductDetailsFragment extends Fragment implements IOnFocusListenable {

    private View rootView;
    @BindView(R.id.product_image)
    ImageView mMovieImage;
    @BindView(R.id.product_name)
    TextView mProductName;
    @BindView(R.id.product_price)
    TextView mProductPrice;
    @BindView(R.id.add_to_cart_view)
    TextView mCartView;
    @BindView(R.id.add_to_cart)
    LinearLayout mAddToCart;
    @BindView(R.id.product_image_banner)
    ImageView mMovieBannerImage;
    @BindView(R.id.content_product_details_header)
    LinearLayout mHeader;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Result product;
    private OnProductItemListener onProductItemListener;

    float startX;
    float startY;
    float destX;
    float destY;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
            init();
        } else {
            if (rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
        return rootView;
    }


    void init() {
        ButterKnife.bind(this, rootView);
        setDataToViews();
    }


    void setDataToViews() {
        Bundle bundle = getArguments();
        product = (Result) bundle.getParcelable(AppConstants.IBundleParams.RESULT_OBJ);
        mHeader.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).load(product.getThumbnailImageUrl()).into(mMovieBannerImage);
        mProductName.setText(product.getProductName());
        mProductPrice.setText(product.getPrice());

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


    Animation.AnimationListener animL = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            clearAnimation();
        }
    };

    private void clearAnimation() {
        mCartView.setVisibility(View.GONE);
        mCartView.invalidate();
        Toast.makeText(getActivity(), "Product Added To Cart.", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.add_to_cart, R.id.fab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                mCartView.setVisibility(View.VISIBLE);
                Animations anim = new Animations();
                Animation a = anim.fromAtoB(startX, startY, (float) (destX-(1.5*fab.getWidth())), (float) (destY+(1.5*fab.getHeight())), animL, 2000);
                a.setStartTime(AnimationUtils.currentAnimationTimeMillis());
                mCartView.startAnimation(a);
                // a.startNow();
                break;
            default:
                break;
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        int fromLoc[] = new int[2];
        mCartView.getLocationOnScreen(fromLoc);
        startX = fromLoc[0];
        startY = fromLoc[1];

        int toLoc[] = new int[2];
        fab.getLocationOnScreen(toLoc);
        destX = toLoc[0];
        destY = toLoc[1];

    }
}
