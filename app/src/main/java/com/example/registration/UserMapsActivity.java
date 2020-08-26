package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ListViewAutoScrollHelper;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // private GoogleMap mMap;
    private GoogleMap mmap;
    FirebaseAuth fauth;




    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng sydney=new LatLng(-34,151);
    LatLng Teamworth=new LatLng(-31.083332,150.916672);
    LatLng Newcastle=new LatLng(-32.916668,151.750000);
    LatLng Brisbane=new LatLng(-27.470125,153.021072);
    LatLng Dubbo=new LatLng(-32.256943,148.601105);

    ArrayList<String> title=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_maps);
        fauth = FirebaseAuth.getInstance();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        arrayList.add(sydney);
        arrayList.add(Teamworth);
        arrayList.add(Newcastle);
        arrayList.add(Brisbane);
        arrayList.add(Dubbo);

        //   myListView=findViewById(R.id.);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        title.add("sydney");
        title.add("Teamworth");
        title.add("Newcastle");
        title.add("Brisbane");
        title.add("Dubbo");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;

        LatLng cnTower = new LatLng(43.6426, -79.3871);
        mmap.addMarker(new MarkerOptions().position(cnTower).title("CN Tower"));
        //  mmap.moveCamera(CameraUpdateFactory.newLatLng(cnTower));

        LatLng lambtonCollege = new LatLng(43.7733, -79.3359);
        mmap.addMarker(new MarkerOptions().position(lambtonCollege).title("Lambton College"));
        //  mmap.moveCamera(CameraUpdateFactory.newLatLng(lambtonCollege));

        LatLng uniToronto = new LatLng(43.6629, -79.3957);
        mmap.addMarker(new MarkerOptions().position(uniToronto).title("University of Toronto"));
        //  mmap.moveCamera(CameraUpdateFactory.newLatLng(uniToronto));

        LatLng etonCenter = new LatLng(-43.6544, -79.3807);
        mmap.addMarker(new MarkerOptions().position(etonCenter).title("Eton Center"));
        //  mmap.moveCamera(CameraUpdateFactory.newLatLng(etonCenter));

        LatLng museum = new LatLng(43.6677, -79.3948);
        mmap.addMarker(new MarkerOptions().position(museum).title("Royal Ontario Museum"));
      //  mmap.moveCamera(CameraUpdateFactory.newLatLng(museum));

        LatLng me = new LatLng(43.670670, -79.791290);
        mmap.addMarker(new MarkerOptions().position(me).title("Ajit Pal"));
          mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,10));

        LatLng mankirat = new LatLng(43.729814, -79.629299);
        mmap.addMarker(new MarkerOptions().position(mankirat).title("Mankirat"));
        //mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,10));

        LatLng ankit = new LatLng(43.652090 , -79.745300);
        mmap.addMarker(new MarkerOptions().position(ankit).title("Ankit Jain"));
      //  mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(me,10));
        DatabaseReference DatabaseReference = FirebaseDatabase.getInstance().getReference("Users/GofpxXVvwheviRL8Os1fHf7ZiYk2/Location");
        ValueEventListener list = DatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Double latitude = dataSnapshot.child("latitude").getValue(Double.class);
                Double longitude = dataSnapshot.child("longitude").getValue(Double.class);

                LatLng location = new LatLng(latitude, longitude);
                mmap.addMarker(new MarkerOptions().position(location).title("Arshdeep"));
             //   mmap.moveCamera(CameraUpdateFactory.newLatLng(location));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //  "users/"+fauth.getCurrentUser().getUid()+"/longitude").setValue(Longitude).addOnCompleteListener(new OnCompleteListener<Void>() {

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users/"+fauth.getCurrentUser().getUid()+"/Location");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        ValueEventListener listner= databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshots=dataSnapshot.getChildren();
                for(DataSnapshot item:snapshots){
                    Double latitude =item.child("Location").child("latitude").getValue(Double.class);
                    Double longitude=item.child("Location").child("longitude").getValue(Double.class);
//                    Log.i("lat",""+item.child("Location").child("latitude").getValue(Double.class));
//                    Log.i("lon",""+item.child("Location").child("longitude").getValue(Double.class));
                    LatLng location =new LatLng(-31.083332,150.916672);
                    LatLng location1 = new LatLng(-32.916668,151.750000);
                    LatLng location2 = new LatLng(-27.470125,153.021072);
                    LatLng location3 = new LatLng(-32.256943,148.601105);
                    mmap.addMarker(new MarkerOptions().position(location).title("Marker Location"));
                    mmap.addMarker(new MarkerOptions().position(location1).title("Marker Location"));
                    mmap.addMarker(new MarkerOptions().position(location2).title("Marker Location"));
                    mmap.addMarker(new MarkerOptions().position(location3).title("Marker Location"));
                 //   mmap.moveCamera(CameraUpdateFactory.newLatLng(location));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String markertitle=marker.getTitle();
                //    Toast.makeText(UserMapsActivity.this, "waheguru "+markertitle, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(UserMapsActivity.this,UserTime.class);
                i.putExtra("title",markertitle);
                startActivity(i);
                return false;
            }
        });

        //  mmap = googleMap;
        //

    }


}
