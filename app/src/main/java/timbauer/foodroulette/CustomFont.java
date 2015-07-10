package timbauer.foodroulette;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by timbauer on 7/9/15.
 */
public class CustomFont extends TextView {
    public CustomFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Gorditas-Regular.ttf"));
    }
}
