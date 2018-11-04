package com.maya.yaswanththaluri.relm;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maya.yaswanththaluri.relm.model.User;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm myRealm;
    private EditText name;
    private EditText id;
    private EditText tgpa;
    private RealmAsyncTask asyncTask;
    private LinearLayout l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRealm = Realm.getDefaultInstance();


        Button b = (Button)findViewById(R.id.add);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = (EditText)findViewById(R.id.name);
                id = (EditText)findViewById(R.id.term);
                tgpa = (EditText)findViewById(R.id.tgpa);
                insertData();
            }
        });

        ImageView i =(ImageView)findViewById(R.id.img);
        Glide.with(this).load(R.drawable.correct).into(i);
        l=(LinearLayout)findViewById(R.id.gif);
        Button y = (Button)findViewById(R.id.ok);
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.setVisibility(View.INVISIBLE);
            }
        });

        FloatingActionButton f =(FloatingActionButton) findViewById(R.id.show);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(MainActivity.this, Datadisplay.class);
                startActivity(i);
            }
        });
    }

    public void insertData()
    {
        final String na = name.getText().toString();
        final String ter = id.getText().toString();
        final String gpa = tgpa.getText().toString();


        asyncTask = myRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.createObject(User.class);
                user.setName("Name : " + na);
                user.setTermid("Term id : " + ter);
                user.setTgpa("TGPA : " + gpa );
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                l.setVisibility(View.VISIBLE);
                name.setText("");
                id.setText("");
                tgpa.setText("");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(MainActivity.this, "Error on Adding Data to Database", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (asyncTask!=null&&!asyncTask.isCancelled())
        {
            asyncTask.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRealm.close();
    }
}
