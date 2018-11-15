package safeobject.guardapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import model.Notification;

public class NotificationItemApdater extends RecyclerView.Adapter<NotificationItemApdater.ViewHolder> {

    private List<Notification> arrayNoti;

    public NotificationItemApdater(List<Notification> arrayNoti) {
        this.arrayNoti = arrayNoti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notification_list,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Notification noti = arrayNoti.get(i);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentDateandTime = sdf.format(noti.getDateTime());
        viewHolder.txtDateTime.setText(currentDateandTime);
    }

    @Override
    public int getItemCount() {
        return arrayNoti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCameraLocation, txtDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCameraLocation = itemView.findViewById(R.id.notiDetail_camera);
            txtDateTime = itemView.findViewById(R.id.notiDetail_time);
        }
    }
}
