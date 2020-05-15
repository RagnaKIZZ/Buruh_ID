package ahmedt.buruhid.utils;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogMessage extends Dialog {


    public DialogMessage(@NonNull Context context) {
        super(context);
    }

    public DialogMessage(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogMessage(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
