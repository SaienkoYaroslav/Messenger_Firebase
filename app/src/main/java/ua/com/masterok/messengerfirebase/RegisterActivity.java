package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etName, etLastName, etHowOld;
    private Button bSignUp;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        onClickSignUp();
        viewModel();
    }

    private void init() {
        etEmail = findViewById(R.id.edit_text_reg_email);
        etPassword = findViewById(R.id.edit_text_reg_password);
        etName = findViewById(R.id.edit_text_reg_name);
        etLastName = findViewById(R.id.edit_text_reg_last_name);
        etHowOld = findViewById(R.id.edit_text_reg_how_old);
        bSignUp = findViewById(R.id.button_reg_sign_up);
    }

    private void viewModel() {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registerViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerViewModel.getMutableLiveDataUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(getApplicationContext(), firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void onClickSignUp() {
        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String name = etName.getText().toString();
                String lastName = etLastName.getText().toString();
                int age = 0;
                if (!etHowOld.getText().toString().isEmpty()) {
                    age = Integer.parseInt(etHowOld.getText().toString());
                }
                if (!eMail.isEmpty() && !password.isEmpty() && !name.isEmpty()
                        && !lastName.isEmpty() && age > 0) {
                    registerViewModel.createNewUser(eMail, password, name, lastName, age);
                } else {
                    Toast.makeText(RegisterActivity.this, "All fields must be fill", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

}