package com.mohammedabdullah3296.roomexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.List;

public class UsersList extends AppCompatActivity {

    private RecyclerView rvUsers;
    private Button fabAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        rvUsers = findViewById(R.id.rv_users);
        fabAddUser = findViewById(R.id.fab_add_user);

        LinearLayoutManager llm = new LinearLayoutManager(UsersList.this);
        rvUsers.setLayoutManager(llm);

        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UsersList.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetAllUsers(UsersList.this).execute();
    }

    public static class GetAllUsers extends AsyncTask<Void, Void, List<User>> {
        private WeakReference<Context> c;

        public GetAllUsers(Context c) {
            this.c = new WeakReference<>(c);
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            UserDatabase ud = UserDatabase.getAppDatabase(c.get());
            return ud.userDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            RecyclerView rv = ((Activity) c.get()).findViewById(R.id.rv_users);

            UserAdapter ua = new UserAdapter(c.get(), users);
            rv.setAdapter(ua);
        }
    }
}
