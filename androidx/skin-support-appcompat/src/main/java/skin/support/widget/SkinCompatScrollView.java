package skin.support.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import androidx.annotation.DrawableRes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import skin.support.content.res.SkinCompatResources;
import skin.support.utils.Slog;

/**
 * Created by Jungle68 on 2017/6/27.
 */
public class SkinCompatScrollView extends ScrollView implements SkinCompatSupportable {
    private static final String TAG = SkinCompatScrollView.class.getSimpleName();

    private final SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatScrollView(Context context) {
        this(context, null);
    }

    public SkinCompatScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }

    /**
     * 设置滚动条背景
     *
     * @param verticalThumbDrawableResId
     * @param verticalTrackDrawableResId
     */
    private void setVerticalScrollBarDrawable(int verticalThumbDrawableResId,
                                              int verticalTrackDrawableResId) {
        try {
            // 滚动条相关属性定义在父类View中
            Field field = View.class.getDeclaredField("mScrollCache");
            field.setAccessible(true);
            Object scrollCache = field.get(this);
            if (scrollCache != null) {
                Class<?> scrollCacheCls = scrollCache.getClass();
                Field scrollBarField = scrollCacheCls.getDeclaredField("scrollBar");
                Object scrollBar = scrollBarField.get(scrollCache);
                if (scrollBar != null) {
                    Method setThumbMethod =
                            scrollBar.getClass().getDeclaredMethod("setVerticalThumbDrawable",
                                    Drawable.class);
                    setThumbMethod.setAccessible(true);
                    Drawable thumbVertical =
                            SkinCompatResources.getDrawable(getContext(), verticalThumbDrawableResId);

                    setThumbMethod.invoke(scrollBar, thumbVertical);

                    Method setTrackMethod =
                            scrollBar.getClass().getDeclaredMethod("setVerticalTrackDrawable",
                                    Drawable.class);
                    setTrackMethod.setAccessible(true);
                    Drawable trackVertical =
                            SkinCompatResources.getDrawable(getContext(), verticalTrackDrawableResId);
                    setTrackMethod.invoke(scrollBar, trackVertical);
                }
            }
        } catch (Exception e) {
            Slog.i(TAG, "setVerticalScrollBarDrawable failed! Exception e : " + e);
        }
    }

}
