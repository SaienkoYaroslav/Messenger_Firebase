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

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button bResetPassword;
    private ResetPasswordViewModel resetPasswordViewModel;

    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
        viewModel();
        intent();
        onClickReset();

    }

    private void viewModel() {
        resetPasswordViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        resetPasswordViewModel.getIsSendSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSendSuccess) {
                if (isSendSuccess) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            R.string.reset_link_send,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        resetPasswordViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(ResetPasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void intent() {
        String email = getIntent().getStringExtra(KEY_EMAIL);
        etEmail.setText(email);
    }

    private void init() {
        etEmail = findViewById(R.id.edit_text_forgot_pass_email);
        bResetPassword = findViewById(R.id.button_forgot_pass_reset_password);
    }

    private void onClickReset() {
        bResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eMail = etEmail.getText().toString();
                if (!eMail.isEmpty()) {
                    resetPasswordViewModel.sendPasswordResetEmail(eMail);
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String eMail) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(KEY_EMAIL, eMail);
        return intent;
    }

}