package skin.support.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import skin.support.appcompat.R;
import skin.support.content.res.SkinCompatVectorResources;
import skin.support.utils.Slog;

/**
 * Created by boiqin on 2024/01/02.
 */

public class SkinCompatScrollBarHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatScrollBarHelper.class.getSimpleName();

    private static Method sVerticalThumbDrawableMethod;
    private static Method sVerticalTrackDrawableMethod;
    private static Method sHorizontalThumbDrawableMethod;
    private static Method sHorizontalTrackDrawableMethod;

    private Object mScrollBar;

    private final View mView;

    private int mThumbVerticalResId = INVALID_ID;
    private int mTrackVerticalResId = INVALID_ID;
    private int mThumbHorizontalResId = INVALID_ID;
    private int mTrackHorizontalResId = INVALID_ID;

    public SkinCompatScrollBarHelper(View view) {
        mView = view;
        init();
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinCompatScrollBarHelper, defStyleAttr, 0);
        try {
            if (a.hasValue(R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbVertical)) {
                mThumbVerticalResId = a.getResourceId(
                        R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbVertical, INVALID_ID);
            }
            if (a.hasValue(R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackVertical)) {
                mTrackVerticalResId = a.getResourceId(
                        R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackVertical, INVALID_ID);
            }
            if (a.hasValue(R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbHorizontal)) {
                mThumbHorizontalResId = a.getResourceId(
                        R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbHorizontal, INVALID_ID);
            }
            if (a.hasValue(R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackHorizontal)) {
                mTrackHorizontalResId = a.getResourceId(
                        R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackHorizontal, INVALID_ID);
            }
        } finally {
            a.recycle();
        }
        applySkin();
    }

    @Override
    public void applySkin() {
        mThumbVerticalResId = checkResourceId(mThumbVerticalResId);
        mTrackVerticalResId = checkResourceId(mTrackVerticalResId);
        mThumbHorizontalResId = checkResourceId(mThumbHorizontalResId);
        mTrackHorizontalResId = checkResourceId(mTrackHorizontalResId);
        if (mScrollBar == null) {
            return;
        }
        try {
            if (sVerticalThumbDrawableMethod != null && mThumbVerticalResId != INVALID_ID) {
                Drawable drawable = SkinCompatVectorResources.getDrawableCompat(mView.getContext(), mThumbVerticalResId);
                if (drawable != null) {
                    sVerticalThumbDrawableMethod.invoke(mScrollBar, drawable);
                }
            }
            if (sVerticalTrackDrawableMethod != null && mTrackVerticalResId != INVALID_ID) {
                Drawable drawable = SkinCompatVectorResources.getDrawableCompat(mView.getContext(), mTrackVerticalResId);
                if (drawable != null) {
                    sVerticalTrackDrawableMethod.invoke(mScrollBar, drawable);
                }
            }
            if (sHorizontalThumbDrawableMethod != null && mThumbHorizontalResId != INVALID_ID) {
                Drawable drawable = SkinCompatVectorResources.getDrawableCompat(mView.getContext(), mThumbHorizontalResId);
                if (drawable != null) {
                    sHorizontalThumbDrawableMethod.invoke(mScrollBar, drawable);
                }
            }
            if (sHorizontalTrackDrawableMethod != null && mTrackHorizontalResId != INVALID_ID) {
                Drawable drawable = SkinCompatVectorResources.getDrawableCompat(mView.getContext(), mTrackHorizontalResId);
                if (drawable != null) {
                    sHorizontalTrackDrawableMethod.invoke(mScrollBar, drawable);
                }
            }
        } catch (Exception e) {
            Slog.i(TAG, e.getMessage());
        }
    }

    // 注意：在target version >= 24失效
    private void init() {
        try {
            // 滚动条相关属性定义在父类View中
            Field field = View.class.getDeclaredField("mScrollCache");
            field.setAccessible(true);
            Object scrollCache = field.get(mView);
            if (scrollCache != null) {
                Class<?> scrollCacheCls = scrollCache.getClass();
                Field scrollBarField = scrollCacheCls.getDeclaredField("scrollBar");
                mScrollBar = scrollBarField.get(scrollCache);
                if (mScrollBar != null) {
                    sVerticalThumbDrawableMethod =
                            mScrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable",
                                    Drawable.class);
                    sVerticalThumbDrawableMethod.setAccessible(true);

                    sVerticalTrackDrawableMethod =
                            mScrollBar.getClass().getDeclaredMethod("setVerticalTrackDrawable",
                                    Drawable.class);
                    sVerticalTrackDrawableMethod.setAccessible(true);

                    sHorizontalThumbDrawableMethod =
                            mScrollBar.getClass().getDeclaredMethod("setHorizontalThumbDrawable",
                                    Drawable.class);
                    sHorizontalThumbDrawableMethod.setAccessible(true);

                    sHorizontalTrackDrawableMethod =
                            mScrollBar.getClass().getDeclaredMethod("setHorizontalTrackDrawable",
                                    Drawable.class);
                    sHorizontalTrackDrawableMethod.setAccessible(true);

                }
            }
        } catch (Exception e) {
            Slog.i(TAG, "setVerticalScrollBarDrawable failed! Exception e : " + e);
        }
    }

}
