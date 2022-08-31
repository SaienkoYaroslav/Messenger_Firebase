package ua.com.masterok.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// Цей клас потрібен щоб можна було передати параметри в конструктор ChatViewModel
public class ChatViewModelFactory implements ViewModelProvider.Factory {

    private String currentUserId;
    private String otherUserId;

    public ChatViewModelFactory(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
    }

    // перевизначаємо метод, який повертає екземпляр ВьюМоделі
    // Тут створюємо новий об'єкт і повертаємо його
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserId, otherUserId);
    }
}
