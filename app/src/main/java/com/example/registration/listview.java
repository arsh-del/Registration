package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listview extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> myArrayList= new ArrayList<>();
    DatabaseReference mRef;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        txt1=findViewById(R.id.txt4);
    //    final ArrayAdapter<String> myArrayAdpter= new ArrayAdapter<String>(listview.this,android.R.layout.simple_list_item_1,myArrayList);
       myListView=findViewById(R.id.list);
       // myListView.setAdapter(myArrayAdpter);

        final ArrayList<Double> list =new ArrayList<>();
        final ArrayAdapter adapter= new ArrayAdapter<Double>(this,R.layout.support_simple_spinner_dropdown_item,list);
        myListView.setAdapter(adapter);
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    User info = snapshot.getValue(User.class);
                          list.clear();
                    Double txt= info.getLocation();
                          list.add(txt);
                         // txt1.setText(info.getLatitude());
                    Toast.makeText(listview.this, "latitude"+info.getLocation(), Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}