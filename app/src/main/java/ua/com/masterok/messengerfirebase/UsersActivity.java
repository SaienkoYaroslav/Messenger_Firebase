package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private UsersViewModel usersViewModel;

    private RecyclerView rvUsers;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        init();
        viewModel();
        adapter();

        // перевірка
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            boolean random = new Random().nextBoolean();
            users.add(new User("id " + i, "Petro " + i, "Sraka " + i, 18 + i, random));
        }
        usersAdapter.setUsers(users);
        //

    }

    private void init() {
        rvUsers = findViewById(R.id.recycler_view_users);
    }

    private void adapter() {
        usersAdapter = new UsersAdapter();
        rvUsers.setAdapter(usersAdapter);
    }

    private void viewModel() {
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        usersViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = MainActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            usersViewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }
}