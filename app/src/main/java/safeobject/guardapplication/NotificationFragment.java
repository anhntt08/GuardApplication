package safeobject.guardapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import app_interface.SendNotificationAPI;
import model.Notification;
import model.NotificationDTO;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container,false);

        recyclerView = view.findViewById(R.id.notiList_recyclerView);

        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setImage_url("image.png");
        notificationDTO.setCameraID((long) 1);
        notificationDTO.setDatetime(Calendar.getInstance().getTimeInMillis());

        uploadNotification(notificationDTO);
//        final List<Notification> listNoti = new ArrayList<>();
//
//        Date currentTime = Calendar.getInstance().getTime();
//
//
//        CameraDTO cameraDTO = new CameraDTO(1,"Camera lầu 1");
//
//        NotificationItemApdater notificationItemApdater = new NotificationItemApdater(listNoti);
//        recyclerView.setAdapter(notificationItemApdater);
//
//        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(llm);
//
//        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getContext(), recyclerView,
//                new RecyclerTouchListener() {
//                    @Override
//                    public void onClickItem(View view, int position) {
//
//                        Intent intent = new Intent(getContext(), Notification_Detail.class);
//                        intent.putExtra("Notification_detail",listNoti.get(position));
//                        startActivity(intent);
//
//                    }
//
//                    @Override
//                    public void onLongClickItem(View v, int position) {
//                        Log.d("AnhNTT", "On long click item ");
//
//                    }
//                }));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Notification");
    }

    private void uploadNotification(NotificationDTO notification){
        Notification noti = null;
        SendNotificationAPI sendNotificationAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendNotificationAPI.class);
        Call<Notification> call = sendNotificationAPI.uploadNotification(notification);

        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                Notification noti = response.body();
                if(noti != null){
                    Log.d("AnhNTT", noti.toString());
                }

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Toast.makeText(getActivity(), "Đã có lỗi xảy ra.", Toast.LENGTH_LONG).show();
                Log.d("AnhNTT, error: ", t.getMessage());
            }
        });
    }

}
