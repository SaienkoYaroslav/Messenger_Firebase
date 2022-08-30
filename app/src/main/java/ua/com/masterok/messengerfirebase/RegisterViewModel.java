package ua.com.masterok.messengerfirebase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterViewModel extends AndroidViewModel {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<FirebaseUser> getMutableLiveDataUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    // Створення екземпляра РілТаймБД
    private final FirebaseDatabase firebaseDatabase;
    // Створення екземпляру DatabaseReference, через який можна записувати дані в БД
    private final DatabaseReference databaseReference;


    public RegisterViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });

        // ініціалізація екземпляра БД
        firebaseDatabase = FirebaseDatabase.getInstance(
                "https://messenger-cd2fa-default-rtdb.europe-west1.firebasedatabase.app"
        );
        // ініціалізація DatabaseReference
        databaseReference = firebaseDatabase.getReference("Users");

    }

    public void createNewUser(String email, String password, String name, String lastName, int age) {
        // додавання нового користувача
        // .createUserWithEmailAndPassword(eMail, password) - метод який добавляє нового користувача
        // сам реалізую багатопоточність, нам не потрібно додатково нічого робити
        // .addOnSuccessListener() - слухач, виконує дії в методі onSuccess, коли все пройшло успішно
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // потрібно для ІД
                        FirebaseUser firebaseUser = authResult.getUser();
                        if (firebaseUser == null) {
                            return;
                        }
                        User user = new User(
                                firebaseUser.getUid(),
                                name,
                                lastName,
                                age,
                                false

                        );
                        // пишемо дані в БД
                        // .push() - по ключу, який вказано в .getReference("Users") створюється таблиця і
                        // всі дані додаються в цю таблицю. Без цього методу дані будуть перезаписуватись до даному ключу
                        // Тобто не буде таблиці, а буде пара ключ - значення
                        // .child(user.getId()) - робить те ж саме, але дозволяє самостійно вказати ІД
                        databaseReference.child(user.getId()).setValue(user);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        error.setValue(e.getMessage());
                    }
                });

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

}
