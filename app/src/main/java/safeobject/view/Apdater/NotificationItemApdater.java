package safeobject.view.Apdater;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import safeobject.model.NotificationMobileDTO;
import safeobject.view.R;

public class NotificationItemApdater extends RecyclerView.Adapter<NotificationItemApdater.ViewHolder> {

    private List<NotificationMobileDTO> arrayNoti;
    private View view;

    public NotificationItemApdater(List<NotificationMobileDTO> arrayNoti) {
        this.arrayNoti = arrayNoti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notification_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        NotificationMobileDTO noti = arrayNoti.get(i);

        Calendar current = Calendar.getInstance();
        current.set(Calendar.HOUR_OF_DAY, 0);
        current.set(Calendar.MINUTE, 0);
        current.set(Calendar.SECOND, 0);

        Calendar notiCalendar = Calendar.getInstance();
//        notiCalendar.setTimeInMillis(noti.getDateTime());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(current.before(notiCalendar)){
            sdf = new SimpleDateFormat("HH:mm");
        }

        String currentDateandTime = sdf.format(noti.getDatetime());
        viewHolder.txtDateTime.setText(currentDateandTime);

        viewHolder.txtCameraLocation.setText(noti.getCamera_location());

        if(noti.getStatus() != 0){
            viewHolder.imageView.setImageResource(R.drawable.ic_check_24dp);
        }
        else if (noti.getStatus() == 0){
            viewHolder.imageView.setImageResource(R.drawable.ic_priority_high_red_24dp);
        }
    }



    @Override
    public int getItemCount() {
        return arrayNoti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCameraLocation, txtDateTime;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCameraLocation = itemView.findViewById(R.id.notiDetail_camera);
            txtDateTime = itemView.findViewById(R.id.notiDetail_time);
            imageView = itemView.findViewById(R.id.notiDetail_imageView);
        }
    }


}