package com.zappos.consumer.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.zappos.consumer.R;
import com.zappos.consumer.utils.FontCache;


public class CustomEditText extends EditText {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontValue = a.getString(R.styleable.CustomTextView_font);

            try {
                if (fontValue == null) {
                    fontValue = "MavenPro-Regular.ttf";
                }
                setTypeface(FontCache.getTypeface(getContext(), fontValue));
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }

}