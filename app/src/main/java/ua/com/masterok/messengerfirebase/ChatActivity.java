package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView tvTitle;
    private View onlineStatus;
    private RecyclerView rvMessages;
    private EditText etMessage;
    private ImageView ivSendMessage;

    private MessagesAdapter messagesAdapter;

    private String currentUserId;
    private String otherUserId;

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        intent();
        adapter();

        // тест
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "Text " + i,
                    currentUserId,
                    otherUserId
            );
            messages.add(message);
        }
        for (int i = 0; i < 10; i++) {
            Message message = new Message(
                    "Text " + i + 10,
                    otherUserId,
                    currentUserId
            );
            messages.add(message);
        }
        messagesAdapter.setMessages(messages);
        //

    }

    private void initViews() {
        tvTitle = findViewById(R.id.text_view_title);
        onlineStatus = findViewById(R.id.online_status_chat);
        rvMessages = findViewById(R.id.recycler_view_chat);
        etMessage = findViewById(R.id.edit_text_message);
        ivSendMessage = findViewById(R.id.image_view_send_message);
    }

    private void adapter() {
        messagesAdapter = new MessagesAdapter(currentUserId);
        rvMessages.setAdapter(messagesAdapter);
    }

    private void intent() {
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

}