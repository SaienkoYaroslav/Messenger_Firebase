package ua.com.masterok.messengerfirebase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends AndroidViewModel {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<List<User>> usersList = new MutableLiveData<>();

    // Створення екземпляра РілТаймБД
    private final FirebaseDatabase firebaseDatabase;
    // Створення екземпляру DatabaseReference, через який можна записувати дані в БД
    private final DatabaseReference databaseReference;

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<List<User>> getUsersList() {
        return usersList;
    }

    public UsersViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
        // ініціалізація екземпляра БД
        firebaseDatabase = FirebaseDatabase.getInstance(
                "https://messenger-cd2fa-default-rtdb.europe-west1.firebasedatabase.app"
        );
        // ініціалізація DatabaseReference
        databaseReference = firebaseDatabase.getReference("Users");


        // Зчитуємо дані з БД (В конструкторі це робити зручніше ніж в окремому методі)

        // Для зчитування даних, на екземпляр DatabaseReference потрібно навісити слухач .addValueEventListener
        // в метод onDataChange() прилітає екземпляр DataSnapshot який в собі має всі дані по ключу,
        // який вказано тут firebaseDatabase.getReference("Messages"); якщо по ключі у нас таблиця,
        // тоді кожен запис з таблиці також являється типом DataSnapshot
        // Щоб перебрати всі елементи цієї колекції, то в DataSnapshot snapshot, який прилітає в
        // onDataChange() потрібно викликати метод getChildren(), який повертає колекцію
        // DataSnapshot'ів яку можна перебирати в циклі
        // Потім в кожного елемента можна викликати метод getValue(), аргументом передавши клас
        // в який потрібно перетворити значення
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> list = new ArrayList<>();
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    return;
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        return;
                    }
                    // Перевірка щоб у списку не відображався поточний користувач
                    if (!user.getId().equals(currentUser.getUid())) {
                        list.add(user);
                    }
                }
                usersList.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void logout() {
        firebaseAuth.signOut();
    }


}
