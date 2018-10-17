package com.example.thomaswilkinson.program4;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.FloatingActionButton;
import static java.lang.Math.toIntExact;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    static AppDatabase db;
    static List<container> value = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("help", "help");
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        Log.v("help", "help1");

        Thread myThread = new Thread(){
            public void run() {
                List<Cont> conts = db.ContDao().selectAll();
                if(conts != null)
                {
                    for(Cont cont : conts){
                        container tempCont = new container(cont.name, cont.category, cont.date, cont.amount, cont.note, cont.id);
                        value.add(tempCont);
                    }
                }
            }
        };
        myThread.start();
        updateRecycler();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });


        updateRecycler();
    }
    EditText name;
    EditText date;
    EditText category;
    EditText amount;
    EditText note;
    AlertDialog.Builder mBuilder;
    public void add(){
        mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.fieldentreedialog, null);
        name = (EditText) mView.findViewById(R.id.name);
        date = (EditText) mView.findViewById(R.id.date);
        category = (EditText) mView.findViewById(R.id.category);
        amount = (EditText) mView.findViewById(R.id.amount);
        note = (EditText) mView.findViewById(R.id.note);
        Button mCreate = (Button) mView.findViewById(R.id.createBtn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(name.getText().toString().equals("") || date.getText().toString().equals("") || category.getText().toString().equals("") || amount.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Fill in required feilds", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    container cont = new container(name.getText().toString(), category.getText().toString(), date.getText().toString(), getFloatFrom(amount), note.getText().toString(), 444444);
                    value.add(cont);
                    dialog.dismiss();
                    final Cont newCont = new Cont(cont.cName, cont.cCategory, cont.cDate, cont.cAmount, cont.cNote);
                    Thread myThread = new Thread(){
                        public void run() {
                            long tempId = db.ContDao().insert(newCont);
                            value.get(value.size()-1)._id = tempId;
                        }
                    };
                    myThread.start();
                    updateRecycler();
                }
            }
        });
        dialog.show();

    }

    static float getFloatFrom(EditText txt) {
        try {
            return NumberFormat.getInstance().parse(txt.getText().toString()).floatValue();
        } catch (ParseException e) {
            return 0.0f;
        }
    }
    void updateRecycler(){
        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myAdapter mAdapter = new myAdapter(value, R.layout.my_row, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }
    static void deleteDb(final long id)
    {
        Thread myThread = new Thread(){
            public void run() {
                db.ContDao().deleteById(id);
            }
        };
        myThread.start();
    }
    static void editDb(final Cont cont)
    {

        Thread myThread = new Thread(){
            public void run() {
                db.ContDao().update(cont);
            }
        };
        myThread.start();
    }
}

