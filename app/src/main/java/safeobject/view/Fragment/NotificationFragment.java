package safeobject.view.Fragment;

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
import android.widget.ImageView;

import java.util.List;

import safeobject.Manager.TokenManager;
import safeobject.app_interface.RecyclerTouchListener;
import safeobject.app_interface.SendNotificationAPI;
import safeobject.model.NotificationMobileDTO;
import safeobject.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import safeobject.view.Activity.NotificationDetailActivity;
import safeobject.view.Apdater.NotificationItemApdater;
import safeobject.view.R;
import safeobject.view.RecyclerItemListener;

public class NotificationFragment extends Fragment {

    private TokenManager tokenManager;

    RecyclerView recyclerView;
    final int status_not_read = 0;
    View view;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification_list, container, false);

        recyclerView = view.findViewById(R.id.notiList_recyclerView);

//        NotificationDTO notificationDTO = new NotificationDTO();
//        notificationDTO.setImage_url("073a1e44-4eae-4c61-8210-9328cd53a14f.png");
//        Long camera = Long.valueOf(1);
//        notificationDTO.setCameraID(camera);
//        notificationDTO.setDatetime(Calendar.getInstance().getTimeInMillis());


//        List<NotificationMobileDTO> listNoti = createListNoti();
//        showNotificationInRecyclerView(listNoti);
        getList();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("NotificationMobileDTO");
    }

    private void showNotificationInRecyclerView(final List<NotificationMobileDTO> list) {
        NotificationItemApdater notificationItemApdater = new NotificationItemApdater(list);
        recyclerView.setAdapter(notificationItemApdater);

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getContext(), recyclerView,
                new RecyclerTouchListener() {
                    @Override
                    public void onClickItem(View view, int position) {

                        Intent intent = new Intent(getContext(), NotificationDetailActivity.class);
                        intent.putExtra("Notification_detail", list.get(position));
                        startActivity(intent);

                    }

                    @Override
                    public void onLongClickItem(View v, int position) {
                        Log.d("AnhNTT", "On long click item ");

                    }
                }));


    }



    private void getList(){
        final SendNotificationAPI apiCall = RetrofitClientInstance.getRetrofitInstanc()
                .create( SendNotificationAPI.class );
        final TokenManager tokenManager = new TokenManager( getContext() );
        final String accessToken = tokenManager.getTokenFromSession();
        Log.d("AnhNTT, accessToken", accessToken);

        List<NotificationMobileDTO> notificationMobileDTOList;

        Call<List<NotificationMobileDTO>> getAccess = apiCall.getListNotification( accessToken );
        getAccess.enqueue(new Callback<List<NotificationMobileDTO>>() {
            @Override
            public void onResponse(Call<List<NotificationMobileDTO>> call, Response<List<NotificationMobileDTO>> response) {
                List<NotificationMobileDTO> notificationMobileDTOList = response.body();
                if(notificationMobileDTOList.size() > 0){
                    showNotificationInRecyclerView(notificationMobileDTOList);
                }
            }

            @Override
            public void onFailure(Call<List<NotificationMobileDTO>> call, Throwable t) {
                Log.d("Anhntt, fail ", t.getMessage());
            }
        });
    }
}
