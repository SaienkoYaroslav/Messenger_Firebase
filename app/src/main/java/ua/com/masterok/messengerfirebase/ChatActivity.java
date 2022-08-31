package ua.com.masterok.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private TextView tvTitle;
    private View onlineStatus;
    private RecyclerView rvMessages;
    private EditText etMessage;
    private ImageView ivSendMessage;

    private MessagesAdapter messagesAdapter;
    private ChatViewModel chatViewModel;
    private ChatViewModelFactory viewModelFactory;

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
        viewModel();
        adapter();
        onClickSendMessage();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.text_view_title);
        onlineStatus = findViewById(R.id.online_status_chat);
        rvMessages = findViewById(R.id.recycler_view_chat);
        etMessage = findViewById(R.id.edit_text_message);
        ivSendMessage = findViewById(R.id.image_view_send_message);
    }

    private void viewModel() {
        viewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        // в конструктор ViewModelProvider другим параметром передається viewModelFactory, так як
        // наша ChatViewModel має конструктор з параметрами, а парметри ініціалізуються в ChatViewModelFactory,
        // тому екземпляр цього класу потрібно передати в конструктор ViewModelProvider
        chatViewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);

        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });
        chatViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null) {
                    Toast.makeText(ChatActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sent) {
                if (sent) {
                    etMessage.setText("");
                }
            }
        });
        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getLastName());
                tvTitle.setText(userInfo);
            }
        });
    }

    private void adapter() {
        messagesAdapter = new MessagesAdapter(currentUserId);
        rvMessages.setAdapter(messagesAdapter);
    }

    private void onClickSendMessage() {
        ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(
                        etMessage.getText().toString().trim(),
                        currentUserId,
                        otherUserId
                );
                chatViewModel.sendMessage(message);
            }
        });
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