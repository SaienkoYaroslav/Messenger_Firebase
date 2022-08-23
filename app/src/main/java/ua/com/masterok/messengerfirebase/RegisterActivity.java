package ua.com.masterok.messengerfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etName, etLastName, etHowOld;
    private Button bSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

    }

    private void init() {
        etEmail = findViewById(R.id.edit_text_reg_email);
        etPassword = findViewById(R.id.edit_text_reg_password);
        etName = findViewById(R.id.edit_text_reg_name);
        etLastName = findViewById(R.id.edit_text_reg_last_name);
        etHowOld = findViewById(R.id.edit_text_reg_how_old);
        bSignUp = findViewById(R.id.button_reg_sign_up);
    }

}