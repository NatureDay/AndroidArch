package com.qianmo.androidarch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qianmo.androidarch.arch.UserViewModel;
import com.qianmo.androidarch.model.User;

public class MainActivity extends AppCompatActivity {

    private TextView mViewId;
    private TextView mViewName;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewId = findViewById(R.id.textView2);
        mViewName = findViewById(R.id.textView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserViewModel.loadData();
            }
        });

        mUserViewModel = new ViewModelProvider.NewInstanceFactory().create(UserViewModel.class);
        mUserViewModel.getUserData().observe(this, new Observer<Resource<User>>() {
            @Override
            public void onChanged(Resource<User> userResource) {
                updateView(userResource.data);
            }
        });
    }

    private void updateView(User user) {
        if (user == null) return;
        mViewId.setText(user.getUserId());
        mViewName.setText(user.getUserName());
    }
}
