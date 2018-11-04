package com.maya.yaswanththaluri.relm;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maya.yaswanththaluri.relm.model.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class Datadisplay extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private Realm myRealm;
    private RealmResults<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datadisplay);

        myRealm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accessData();

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        TouchListener t = new TouchListener(dataAdapter);
        ItemTouchHelper i =new ItemTouchHelper(t);
        i.attachToRecyclerView(recyclerView);




    }

    private void accessData()
    {
         list = myRealm.where(User.class).findAll();
        dataAdapter = new DataAdapter(list, this);
        recyclerView.setAdapter(dataAdapter);
        int l = list.size();
        if (l==0)
        {
            ImageView i = (ImageView)findViewById(R.id.imgnodata);
            i.setVisibility(View.VISIBLE);
        }
        else
        {
            ImageView i2 = (ImageView)findViewById(R.id.imgnodata);
            i2.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        dataAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onStop() {
        super.onStop();
        myRealm.close();
    }
}
