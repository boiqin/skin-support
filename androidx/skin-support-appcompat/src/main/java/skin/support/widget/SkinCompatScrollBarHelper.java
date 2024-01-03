package skin.support.widget;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import skin.support.appcompat.R;
import skin.support.content.res.SkinCompatVectorResources;
import skin.support.utils.Slog;

/**
 * Created by boiqin on 2024/01/02.
 */
public class SkinCompatScrollBarHelper extends SkinCompatHelper {
    private static final String TAG = SkinCompatScrollBarHelper.class.getSimpleName();

    private static Method sVerticalThumbMethod;
    private static Method sVerticalTrackMethod;
    private static Method sHorizontalThumbMethod;
    private static Method sHorizontalTrackMethod;

    private Object mScrollBar;

    private final View mView;

    private int mThumbVerticalResId = INVALID_ID;
    private int mTrackVerticalResId = INVALID_ID;
    private int mThumbHorizontalResId = INVALID_ID;
    private int mTrackHorizontalResId = INVALID_ID;

    public SkinCompatScrollBarHelper(View view) {
        mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = mView.getContext().obtainStyledAttributes(attrs, R.styleable.SkinCompatScrollBarHelper, defStyleAttr, 0);
        try {
            mThumbVerticalResId = initDrawableResId(a, R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbVertical);
            mTrackVerticalResId = initDrawableResId(a, R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackVertical);
            mThumbHorizontalResId = initDrawableResId(a, R.styleable.SkinCompatScrollBarHelper_android_scrollbarThumbHorizontal);
            mTrackHorizontalResId = initDrawableResId(a, R.styleable.SkinCompatScrollBarHelper_android_scrollbarTrackHorizontal);
        } finally {
            a.recycle();
        }
        applySkin();
    }

    private int initDrawableResId(TypedArray a, int typeValueId) {
        if (a.hasValue(typeValueId)) {
            return a.getResourceId(typeValueId, INVALID_ID);
        }
        return INVALID_ID;
    }

    @Override
    public void applySkin() {
        mThumbVerticalResId = checkResourceId(mThumbVerticalResId);
        mTrackVerticalResId = checkResourceId(mTrackVerticalResId);
        mThumbHorizontalResId = checkResourceId(mThumbHorizontalResId);
        mTrackHorizontalResId = checkResourceId(mTrackHorizontalResId);
        if (mScrollBar == null) {
            reflectScrollBar();
        }
        if (mScrollBar == null) {
            return;
        }
        try {
            invokeSetDrawableMethod(sVerticalThumbMethod, mThumbVerticalResId);
            invokeSetDrawableMethod(sVerticalTrackMethod, mTrackVerticalResId);
            invokeSetDrawableMethod(sHorizontalThumbMethod, mThumbHorizontalResId);
            invokeSetDrawableMethod(sHorizontalTrackMethod, mTrackHorizontalResId);
        } catch (Exception e) {
            Slog.i(TAG, "invoke setDrawableMethod failed: " + e);
        }
    }

    private void invokeSetDrawableMethod(@Nullable Method method, int resId)
            throws InvocationTargetException, IllegalAccessException {
        if (method != null && resId != INVALID_ID) {
            Drawable drawable = SkinCompatVectorResources.getDrawableCompat(mView.getContext(), resId);
            if (drawable != null) {
                method.invoke(mScrollBar, drawable);
            }
        }
    }

    // 注意：在target version >= 24失效
    private void reflectScrollBar() {
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
                    sVerticalThumbMethod = reflectDrawableMethod("setVerticalThumbDrawable");
                    sVerticalTrackMethod = reflectDrawableMethod("setVerticalTrackDrawable");
                    sHorizontalThumbMethod = reflectDrawableMethod("setHorizontalThumbDrawable");
                    sHorizontalTrackMethod = reflectDrawableMethod("setHorizontalTrackDrawable");
                }
            }
        } catch (Exception e) {
            Slog.i(TAG, "setVerticalScrollBarDrawable failed! Exception e : " + e);
        }
    }

    private Method reflectDrawableMethod(String methodName) throws NoSuchMethodException {
        final Method method =
                mScrollBar.getClass().getDeclaredMethod(methodName, Drawable.class);
        method.setAccessible(true);
        return method;
    }
}
