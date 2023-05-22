package com.example.mynotes.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mynotes.Places;
import com.example.mynotes.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacesListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PlacesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlacesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlacesListFragment newInstance(String param1, String param2) {
        PlacesListFragment fragment = new PlacesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    List<Places> placesList=new ArrayList<>();
    Places places;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        listView = view.findViewById(R.id.Listview);
        firebaseDatabase=FirebaseDatabase.getInstance("https://mynotes-4df90-default-rtdb.firebaseio.com/");
        databaseReference=firebaseDatabase.getReference("placesinfo");
        placesList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, String> map=(Map<String, String >) snapshot.getValue();
                places =new Places();
                places.setStreetAddress(map.get("streetAddress"));
                places.setState(map.get("state"));
                places.setCountry(map.get("country"));
                places.setImage(map.get("image"));

                placesList.add(places);

                MyAdapter myAdapter =new MyAdapter(getActivity(), placesList);
                listView.setAdapter(myAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;}

    public class MyAdapter extends BaseAdapter {
        Context context;
        List<Places> stringList;
        TextView txtPlace;
        ImageView imgPlace;

        public MyAdapter(Context context, List<Places> stringList) {
            this.context = context;
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 1;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view=LayoutInflater.from(context).inflate(R.layout.places_layout, viewGroup, false);
            txtPlace=view.findViewById(R.id.txtCity);
            imgPlace=view.findViewById(R.id.imgPlace);

            txtPlace.setText("City: "+ stringList.get(position).getStreetAddress() +
                    "\n State: " + stringList.get(position).getState() +
                    "\n Country: " + stringList.get(position).getCountry()
            );

            byte[] imageAsByte = Base64.decode(placesList.get(position).getImage().getBytes(), Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length);
            imgPlace.setImageBitmap(bitmap);

            return view;
        }
    }
}