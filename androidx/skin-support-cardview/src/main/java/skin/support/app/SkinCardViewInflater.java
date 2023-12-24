package skin.support.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import skin.support.widget.SkinCompatCardView;

/**
 * Created by ximsf on 2017/3/5.
 */

public class SkinCardViewInflater implements SkinLayoutInflater {
    @Override
    public View createView(@NonNull Context context, final String name, @NonNull AttributeSet attrs) {
        View view = null;
        if (name.equals("androidx.cardview.widget.CardView")) {
            view = new SkinCompatCardView(context, attrs);
        }
        return view;
    }
}
