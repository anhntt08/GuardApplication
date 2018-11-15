package safeobject.guardapplication;

import android.content.Intent;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app_interface.RecyclerTouchListener;
import app_interface.SendNotificationAPI;
import model.CameraDTO;
import model.Notification;
import model.NotificationDTO;
import model.NotificationList;
import model.UserDTO;
import network.RetrofitClientInstance;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    final int status_not_read = 0;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        recyclerView = view.findViewById(R.id.notiList_recyclerView);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setImage_url("073a1e44-4eae-4c61-8210-9328cd53a14f.png");
        Long camera = Long.valueOf(1);
        notificationDTO.setCameraID(camera);
        notificationDTO.setDatetime(Calendar.getInstance().getTimeInMillis());

//        uploadNotification(notificationDTO);
//        getListNotification();

        List<Notification> listNoti = createListNoti();
        showNotificationInRecyclerView(listNoti);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Notification");
    }

    private void uploadNotification(NotificationDTO notification) {
        Notification noti = null;
        SendNotificationAPI sendNotificationAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendNotificationAPI.class);
        Call<Notification> call = sendNotificationAPI.uploadNotification(notification);

        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                Notification noti = response.body();
                if (noti != null) {
                    Log.d("AnhNTT, success uploadNotification", noti.toString());
                }

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Toast.makeText(getActivity(), "Đã có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                Log.d("AnhNTT, error uploadNotification ", t.getMessage());
            }
        });
    }

    private void getListNotification() {

        SendNotificationAPI sendNotificationAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendNotificationAPI.class);
        Call<List<Notification>> call = sendNotificationAPI.getListNotificationByStatus();

        call.enqueue(new Callback<List<Notification>>() {


            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                Log.d("AnhNTT", response.body().size() + "");
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.d("AnhNTT", t.getMessage());
            }
        });
    }

    private void showNotificationInRecyclerView(final List<Notification> list) {
        NotificationItemApdater notificationItemApdater = new NotificationItemApdater(list);
        recyclerView.setAdapter(notificationItemApdater);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getContext(), recyclerView,
                new RecyclerTouchListener() {
                    @Override
                    public void onClickItem(View view, int position) {

                        Intent intent = new Intent(getContext(), Notification_Detail.class);
                        intent.putExtra("Notification_detail", list.get(position));
                        startActivity(intent);

                    }

                    @Override
                    public void onLongClickItem(View v, int position) {
                        Log.d("AnhNTT", "On long click item ");

                    }
                }));
    }

    private List<Notification> createListNoti(){
        List<Notification> listNoti = new ArrayList<>();
        CameraDTO cameraDTO = new CameraDTO(1, "Camera lau 1");
        listNoti.add(new Notification(1, "073a1e44-4eae-4c61-8210-9328cd53a14f.jpg",
                Calendar.getInstance().getTimeInMillis(), 0, cameraDTO));
        listNoti.add(new Notification(1, "024f8cbb-5065-44f1-85c5-cef7d5f5d8ac.jpg",
                Calendar.getInstance().getTimeInMillis(), 0, cameraDTO));
        listNoti.add(new Notification(1, "071619b1-3f51-4b94-9fc2-0212d16c5196.jpg",
                Calendar.getInstance().getTimeInMillis(), 0, cameraDTO));
        return listNoti;
    }
}
