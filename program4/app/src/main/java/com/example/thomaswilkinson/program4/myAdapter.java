package com.example.thomaswilkinson.program4;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {

    private List<container> myList;
    private int rowLayout;
    private Context mContext;

    public myAdapter(List<container> myList, int rowLayout, Context context) {
        this.myList = myList;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return myList == null ? 0 : myList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;

        public ViewHolder(View itemView) {
            super(itemView);
            myName = (TextView) itemView.findViewById(R.id.Name);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i){
        final container entry = myList.get(i);
        viewHolder.myName.setText(entry.cName);
        viewHolder.myName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TextView tv = (TextView)v;
                customDialog(tv.getText().toString(), entry.cCategory, entry.cDate, entry.cAmount, entry.cNote, entry._id);
            }
        });
    }

    //Dialog box for details on entrys
    private void customDialog(String name, String category, String date, float amount, String note, final long id ){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(mContext);
        builderSingle.setTitle(name);
        builderSingle.setMessage("Category: " + category + "\n" + "Date: " + date + "\n" + "Amount: " + amount + "\n" + "Note: " + note);

        builderSingle.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builderSingle.setNegativeButton("edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit(id);
            }
        });
        builderSingle.setNeutralButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(id);
            }
        });
        builderSingle.show();
    }


    EditText name;
    EditText date;
    EditText category;
    EditText amount;
    EditText note;
    TextView title;
    AlertDialog.Builder mBuilder;
        public void edit(long id){
            final long pID = id;
            int x=-1;
            for(int i = 0; i < myList.size(); i++)
            {
                if(myList.get(i)._id == pID){
                    x = i;
                }
            }
            mBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View mView = inflater.inflate(R.layout.fieldentreedialog, null);
            title = (TextView) mView.findViewById(R.id.textView);
            title.setText("Edit Expense");
            name = (EditText) mView.findViewById(R.id.name);
            name.setText(myList.get(x).cName);
            date = (EditText) mView.findViewById(R.id.date);
            date.setText(myList.get(x).cDate);
            category = (EditText) mView.findViewById(R.id.category);
            category.setText(myList.get(x).cCategory);
            amount = (EditText) mView.findViewById(R.id.amount);
            amount.setText(myList.get(x).cAmount.toString());
            note = (EditText) mView.findViewById(R.id.note);
            note.setText(myList.get(x).cNote);
            Button mCreate = (Button) mView.findViewById(R.id.createBtn);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            mCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    if(name.getText().toString().equals("") || date.getText().toString().equals("") || category.getText().toString().equals("") || amount.getText().toString().equals(""))
                    {
                        Toast toast = Toast.makeText(mContext, "Fill in required feilds", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        int location=-1;
                        for(int i = 0; i < myList.size(); i++) {
                            if (myList.get(i)._id == pID) {
                                location = i;
                            }
                        }
                            container cont = new container(name.getText().toString(), category.getText().toString(), date.getText().toString(), MainActivity.getFloatFrom(amount), note.getText().toString(), pID);
                            myList.set(location,cont);
                            final Cont tempCont = new Cont(cont.cName,cont.cCategory,cont.cDate,cont.cAmount,cont.cNote);
                            tempCont.id = pID;
                            MainActivity.editDb(tempCont);
                            dialog.dismiss();
                            notifyDataSetChanged();
                    }
                }
            });
        dialog.show();

    }

    void delete(long id){
        int location=-1;
        for(int i = 0; i < myList.size(); i++)
        {
            if(myList.get(i)._id == id){
                location = i;
            }
        }
        MainActivity.deleteDb(myList.get(location)._id);
        myList.remove(location);
        notifyDataSetChanged();
    }

}