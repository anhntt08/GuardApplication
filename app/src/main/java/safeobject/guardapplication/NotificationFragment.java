package safeobject.guardapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app_interface.RecyclerTouchListener;
import model.CameraDTO;
import model.Notification;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container,false);

        recyclerView = view.findViewById(R.id.notiList_recyclerView);

        List<Notification> listNoti = new ArrayList<>();

        Date currentTime = Calendar.getInstance().getTime();


        CameraDTO cameraDTO = new CameraDTO(1,"Camera lầu 1");
        listNoti.add(new Notification(cameraDTO,currentTime));
        listNoti.add(new Notification(cameraDTO,currentTime));
        listNoti.add(new Notification(cameraDTO,currentTime));
        NotificationItemApdater notificationItemApdater = new NotificationItemApdater(listNoti);
        recyclerView.setAdapter(notificationItemApdater);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getContext(), recyclerView,
                new RecyclerTouchListener() {
                    @Override
                    public void onClickItem(View view, int position) {
                        Log.d("AnhNTT", "On click item ");
                    }

                    @Override
                    public void onLongClickItem(View v, int position) {
                        Log.d("AnhNTT", "On long click item ");

                    }
                }));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Notification");
    }

}
