package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView tvRegister, tvForgotPassword;
    private EditText etEmail, etPassword;
    private Button bLogin;

    private FirebaseAuth firebaseAuth;
    private static final String TAG = "MainActivity";
    private String eMail = "email@gmail.com";
    private String password = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        firebase();
    }

    private void init() {
        tvRegister = findViewById(R.id.text_view_main_go_to_registration);
        tvForgotPassword = findViewById(R.id.text_view_main_go_to_forgot_password);
        etEmail = findViewById(R.id.edit_text_main_email);
        etPassword = findViewById(R.id.edit_text_main_password);
        bLogin = findViewById(R.id.button_main_login);
    }

    private void firebase() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Log.d(TAG, "Not Authorized");
                } else {
                    Log.d(TAG, "Authorized " + user.getUid());
                }
            }
        });
        firebaseAuth.signInWithEmailAndPassword("Masterok92@gmail.com", "123456789")
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "e contact " + authResult.toString());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Vse Huinya " + e.getMessage());
                    }
                });
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

        // додавання нового користувача
        // .createUserWithEmailAndPassword(eMail, password) - метод який добавляє нового користувача
        // сам реалізую багатопоточність, нам не потрібно додатково нічого робити
        // .addOnSuccessListener() - слухач, виконує дії в методі onSuccess, коли все пройшло успішно
//        firebaseAuth.createUserWithEmailAndPassword("Masterok92@gmail.com", "111111")
//                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

}