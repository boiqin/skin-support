package skin.support.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.ContentFrameLayout;
/**
 * @author littlefogcat
 * @email littlefogcat@foxmail.com
 */
@SuppressLint("RestrictedApi")
public class SkinCompatContentFrameLayout extends ContentFrameLayout implements SkinCompatSupportable {
    private final SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatContentFrameLayout(Context context) {
        this(context, null);
    }

    public SkinCompatContentFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatContentFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
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

}
