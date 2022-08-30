package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView tvRegister, tvForgotPassword;
    private EditText etEmail, etPassword;
    private Button bLogin;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        viewModel();
        onClickLogin();
        onClickRegister();
        onClickForgotPassword();
    }

    private void viewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getMutableLiveDataUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = new Intent(UsersActivity.newIntent(getApplicationContext()));
                    startActivity(intent);
                    finish();
                }
            }
        });
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        tvRegister = findViewById(R.id.text_view_main_go_to_registration);
        tvForgotPassword = findViewById(R.id.text_view_main_go_to_forgot_password);
        etEmail = findViewById(R.id.edit_text_main_email);
        etPassword = findViewById(R.id.edit_text_main_password);
        bLogin = findViewById(R.id.button_main_login);
    }

    private void onClickLogin() {
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (!eMail.isEmpty() && !password.isEmpty()) {
                    mainViewModel.signIn(eMail, password);
                } else {
                    Toast.makeText(MainActivity.this, "Enter login and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickRegister() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.newIntent(getApplicationContext()));
                startActivity(intent);
            }
        });
    }

    private void onClickForgotPassword() {
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmail.getText().toString();
                Intent intent = new Intent(ResetPasswordActivity.newIntent(getApplicationContext(), eMail));
                startActivity(intent);
            }
        });
    }

//        firebaseAuth.sendPasswordResetEmail("Masterok92@gmail.com")
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MainActivity.this, "check your email", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

}