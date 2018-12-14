package safeobject.view.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;

import safeobject.Manager.NotificationManager;
import safeobject.Manager.TokenManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import safeobject.app_interface.LoginAPI;
import safeobject.model.Auth;
import safeobject.model.NotificationDTO;
import safeobject.model.User;
import safeobject.network.RetrofitClientInstance;
import safeobject.view.R;

public class LoginActivity extends AppCompatActivity {
    TextView txtUsername, txtpass;
    Button btnLogin;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        FirebaseMessaging.getInstance().subscribeToTopic("SafeObser")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Success topic";
                        if (!task.isSuccessful()) {
                            msg = "Fail topic";
                        }
                        Log.d("AnhNTT", msg);
                    }
                });

        tokenManager = new TokenManager(getApplicationContext());
        txtUsername = findViewById(R.id.login_username);
        txtpass = findViewById(R.id.login_pass);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


        Log.d("AnhNTT, login", Calendar.getInstance().getTimeInMillis()+"");

//        NotificationDTO notification = new NotificationDTO();
//        notification.setImageURL("anhntt.jpg");
//        notification.setCameraID((long) 1);
//        notification.setTime(Calendar.getInstance().getTimeInMillis());
//        NotificationManager.createNotification(notification);

    }

    private void login() {
        final LoginAPI loginapi = RetrofitClientInstance.getRetrofitInstanc().create(LoginAPI.class);

        final String username = txtUsername.getText().toString();
        String pass = txtpass.getText().toString();

        Call<Auth> login = loginapi.login(new User(username, pass));
        login.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                if (response.code() == 200) {
                    Auth auth = response.body();
                    if (auth != null) {
                        tokenManager.createSession(username, auth.getToken().split("Authorization=")[1]);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(intent);
                    }
                } else if (response.code() == 401) {
                    showToast("Username or password was incorrect");
                } else {
                    showToast("Some errors have occurred");
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                showToast("" + t.getMessage());
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg + "", Toast.LENGTH_LONG).show();
    }
}
