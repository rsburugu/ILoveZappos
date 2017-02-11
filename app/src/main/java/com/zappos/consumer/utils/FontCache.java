package com.zappos.consumer.utils;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by PurpleTalk India Pvt Ltd. on 12/1/17.
 */

public class FontCache {

    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(Context context, String fontValue) {
        Typeface typeface = null;
        String fontName = fontValue;
        if(fontName!=null) {
            if (fontCache.containsKey(fontName)) {
                typeface = fontCache.get(fontName);
            } else {
                typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
                fontCache.put(fontName, typeface);
            }
        }
        return typeface;
    }


}
