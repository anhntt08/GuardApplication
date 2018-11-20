package safeobject.guardapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    TextView txtUsername, txtpass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        txtUsername = findViewById(R.id.login_username);
        txtpass = findViewById(R.id.login_pass);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        String username = txtUsername.getText().toString();
        String pass = txtpass.getText().toString();
        if (username.equalsIgnoreCase("anhntt")) {
            if (pass.equalsIgnoreCase("anhntt")) {
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid username", Toast.LENGTH_SHORT).show();
        }
    }
}
