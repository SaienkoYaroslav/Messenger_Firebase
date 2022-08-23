package ua.com.masterok.messengerfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button bResetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();

    }

    private void init() {
        etEmail = findViewById(R.id.edit_text_forgot_pass_email);
        bResetEmail = findViewById(R.id.button_forgot_pass_reset_password);
    }

}