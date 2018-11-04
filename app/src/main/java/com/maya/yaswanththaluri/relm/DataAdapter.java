package com.maya.yaswanththaluri.relm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.maya.yaswanththaluri.relm.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> implements SwipeListener{
    private RealmResults<User> dataList;
    private Realm myRealm;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, term, tgpa;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.namelist);
            term = (TextView) view.findViewById(R.id.termlist);
            tgpa = (TextView) view.findViewById(R.id.tgpalist);
        }
    }

    public DataAdapter(RealmResults<User> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = dataList.get(position);

        holder.name.setText(user.getName());
        holder.term.setText(user.getTermid());
        holder.tgpa.setText(user.getTgpa());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onSwipe(final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are You sure You want to Delete It?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myRealm=Realm.getDefaultInstance();
                        myRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                dataList.deleteFromRealm(pos);
                                notifyDataSetChanged();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        notifyDataSetChanged();
                    }
                });
        builder.show();




    }
}
