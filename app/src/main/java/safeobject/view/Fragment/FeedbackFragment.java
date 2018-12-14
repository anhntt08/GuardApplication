package safeobject.view.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import safeobject.Manager.TokenManager;
import safeobject.app_interface.SendFeedbackAPI;
import safeobject.app_interface.UploadImageAPI;
import safeobject.model.FeedbackDTO;
import safeobject.model.FeedbackPhotoDTO;
import safeobject.network.RetrofitClientInstance;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import safeobject.view.Activity.CameraFeedbackActivity;
import safeobject.view.Apdater.ImagesAdapter;
import safeobject.view.R;


public class FeedbackFragment extends Fragment implements View.OnClickListener{
    private static final int CAMERA_REQUEST_CODE = 3110;
    private static final int TAKE_IMAGE = 1;

    private ImageButton imgButtonChoosePhoto, imgButtonCamera;
    private LinearLayout lnLayoutImageView,layoutVideoTutorial ;

    private EditText edtFeedbackDescription;
    private ImagesAdapter imagesAdapter;
    protected RecyclerView recyclerView;
    private ImageView imageLorem;

    ProgressDialog pd;


    private List<File> listImageSelected = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);



        View view = inflater.inflate(R.layout.fragment_feedback,container,false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Gửi phản hồi");

        imgButtonCamera = view.findViewById(R.id.imgButtonCamera);
        imgButtonChoosePhoto = view.findViewById(R.id.imgButtonChooseImage);
        lnLayoutImageView = view.findViewById(R.id.lnlayoutImageView);
        edtFeedbackDescription = view.findViewById(R.id.edtFeedbackDescription);
        recyclerView = view.findViewById(R.id.recycler_view);
        imageLorem = view.findViewById(R.id.image_lorem);
        layoutVideoTutorial = view.findViewById(R.id.layout_video_tutorial);



        imagesAdapter = new ImagesAdapter(this.getContext(), listImageSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(imagesAdapter);

        imgButtonChoosePhoto.setOnClickListener(this);
        imgButtonCamera.setOnClickListener(this);
        layoutVideoTutorial.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Feedback");
        Log.d("AnhNTT", "onViewCreated");

    }


    private   void watchYoutubeVideo(String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == TAKE_IMAGE && resultCode == getActivity().RESULT_OK) {


            Bundle extras=data.getExtras();

            String img_path=extras.getString("img_path");

            File img = new File(img_path);

            listImageSelected.add(img);
            imagesAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(listImageSelected.size() - 1);
        }
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(getActivity(), "Đã có lỗi xảy ra, xin thử lại!", Toast.LENGTH_LONG).show();
                System.out.println("=======ERROR while picking photo=======");
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imagesFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imagesFiles);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_bar_sendFeedback)
        {
            Log.d("AnhNTT", "action_bar_sendFeedback");
            sendFeedback();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFeedback() {
        if (listImageSelected.size() < 4) {
            Toast.makeText(getActivity(), "Chọn ít nhất 4 hình về vật thể!", Toast.LENGTH_LONG).show();
            return;
        } else if (edtFeedbackDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Mô tả chi tiết không được để trống!", Toast.LENGTH_LONG).show();
            return;
        }
        pd = new ProgressDialog(getContext()); // API <26
        pd.setMessage("Sending Feedback");
        pd.show();
        FeedbackPhotoDTO[] listFeedbackPhotoDTO = new FeedbackPhotoDTO[listImageSelected.size()];

        for (int i = 0; i < listImageSelected.size(); i++) {
            FeedbackPhotoDTO feedbackPhotoDTO = new FeedbackPhotoDTO();
            feedbackPhotoDTO.setPhotoName(listImageSelected.get(i).getName());
            listFeedbackPhotoDTO[i] = feedbackPhotoDTO;
        }

        TokenManager tokenManager = new TokenManager(getContext());
        String username = tokenManager.getUsernameFromSession();

        FeedbackDTO feedbackDTO = new FeedbackDTO();
        feedbackDTO.setFeedbackDescription(edtFeedbackDescription.getText().toString().trim());
        feedbackDTO.setFeedbackPhotoList(listFeedbackPhotoDTO);
        feedbackDTO.setTime(Calendar.getInstance().getTimeInMillis());



        SendFeedbackAPI serviceFeedbackAPI = RetrofitClientInstance.getRetrofitInstanc().create(SendFeedbackAPI.class);
        Call<String> call = serviceFeedbackAPI.sendFeedback(tokenManager.getTokenFromSession(),feedbackDTO);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.body()) {
                    case "empty_list_image":
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Đã có lỗi xảy ra. Danh sách hình RỖNG", Toast.LENGTH_LONG).show();
                        break;
                    case "exist_image":
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Đã có lỗi xảy ra. Hình đã có trên hệ thống", Toast.LENGTH_LONG).show();
                        break;
                    case "send_feedback_successfully":
                        //upload file
                        for (File img : listImageSelected) {
                            uploadImage(img);
                        }
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Gửi phản hồi thành công", Toast.LENGTH_LONG).show();
                        //Clear
                        lnLayoutImageView.setVisibility(View.GONE);
                        edtFeedbackDescription.setText("");
                        listImageSelected.clear();
                        onPhotosReturned(listImageSelected);
                        break;
                }

            }

            private void uploadImage(final File fileImage) {

                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), fileImage);

                MultipartBody.Part body = MultipartBody.Part.createFormData("img", fileImage.getName(), requestBody);

                UploadImageAPI serviceUploadImgAPI = RetrofitClientInstance.getRetrofitInstanc().create(UploadImageAPI.class);

                Call<String> callUpload = serviceUploadImgAPI.uploadImage(body);

                callUpload.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        System.out.println("Upload file is successfully");
                        System.out.println(response.body());

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println("Upload file is failed");
                        Log.e("ERROR: ", t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                pd.dismiss();

                Toast.makeText(getActivity(), "Đã có lỗi xảy ra.", Toast.LENGTH_LONG).show();

                Log.e("main", "on error is called and the error is  ----> " + t.getMessage());

            }
        });
    }

    private void onPhotosReturned(List<File> returnedPhotos) {
        listImageSelected.addAll(returnedPhotos);
        imagesAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(listImageSelected.size() - 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}

    @Override
    public void onClick(View v) {
        EasyImage.configuration(getActivity().getApplication()).setAllowMultiplePickInGallery(true);

        switch (v.getId()) {
            case R.id.imgButtonChooseImage:
                listImageSelected.clear();


                EasyImage.openGallery(FeedbackFragment.this, 0);
                imageLorem.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;

            case R.id.imgButtonCamera:
                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(getContext(), CameraFeedbackActivity.class);
                    startActivityForResult(intent, TAKE_IMAGE);
                    imageLorem.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            CAMERA_REQUEST_CODE);
                }
                break;
            case R.id.layout_video_tutorial:
                watchYoutubeVideo("E06kgYBftak");
                break;
        }
    }


}
