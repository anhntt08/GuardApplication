package safeobject.guardapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import app_interface.RecyclerTouchListener;

public class RecyclerItemListener implements RecyclerView.OnItemTouchListener {

    private RecyclerTouchListener listener;
    private GestureDetector gd;

    public RecyclerItemListener(Context context, final RecyclerView recyclerView,
                                final RecyclerTouchListener listener){
        this.listener = listener;
        gd = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public void onLongPress(MotionEvent e) {
                View v = recyclerView.findChildViewUnder(e.getX(),e.getY());
                listener.onLongClickItem(v, recyclerView.getChildAdapterPosition(v));

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View v = recyclerView.findChildViewUnder(e.getX(),e.getY());

                listener.onClickItem(v,recyclerView.getChildAdapterPosition(v));
                return true;
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        return (child != null && gd.onTouchEvent(motionEvent));
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
