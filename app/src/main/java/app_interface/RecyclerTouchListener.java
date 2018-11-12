package app_interface;

import android.view.View;

public interface RecyclerTouchListener {
    public void onClickItem(View view, int position);
    public  void onLongClickItem(View v, int position);
}
