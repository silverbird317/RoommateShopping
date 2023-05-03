package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A fragment representing a list of Roommates.
 */
public class RoommatesFragment extends Fragment {

    private static final String TAG = "RoommatesFragment";

    private ListView listView;

    public static final String ARG_OBJECT = "object";
    private ArrayList<Roommate> roommatesList = new ArrayList<Roommate>();
    RoommatesArrayAdapter roommatesArrayAdapter;
    private int versionNum;

    private FirebaseDatabase database;

    private boolean fetched = false;

    /*
     * required empty public constructor
     */
    public RoommatesFragment() {
        // Required empty public constructor
    }

    /*
     * overrides oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RoommatesFragment newInstance() {

        RoommatesFragment fragment = new RoommatesFragment();

        Bundle args = new Bundle();
        //args.putInt( "index", index );
        fragment.setArguments( args );

        return fragment;
    }

    /*
     * overrides onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roommates, container, false);
    }

    /*
     * overrides onviewcreated
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);

        roommatesArrayAdapter = new RoommatesArrayAdapter( getActivity(), roommatesList );


        // initialize the Job Lead list
        listView.setAdapter( roommatesArrayAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roommates");

        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                roommatesList.clear(); // clear the current content; this is inefficient!
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Roommate shoppingItem = postSnapshot.getValue(Roommate.class);
                    shoppingItem.setKey(postSnapshot.getKey());
                    Log.d("firebase", "changed: " + shoppingItem);
                    roommatesList.add(shoppingItem);
                    Log.d(TAG, "ValueEventListener: added: " + shoppingItem);
                    Log.d(TAG, "ValueEventListener: key: " + postSnapshot.getKey());
                }
                Log.d("new quiz", "" + roommatesList.size());
                roommatesArrayAdapter.clear();
                roommatesArrayAdapter.addAll(roommatesList);
                roommatesArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );

    }

    /*
     * overrides onresume, loads quiz results back
     */
    public void onResume() {
        super.onResume();
        Log.d( TAG, "RoommatesFragment.onResume(): length: " + roommatesList.size() );

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Log.d("receive", "gua");
                String name = bundle.getString("name");
                String email = bundle.getString("email");
                String phone = bundle.getString("phone");
                Roommate roommate = new Roommate(name, email, phone);
                addJobLead(roommate);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    /*
     * adds roommate to roommates database
     */
    public void addJobLead(Roommate roommate) {
        Log.d("addJobLead", "added");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppinglist").child(roommate.getName());

        myRef.setValue( roommate )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        listView.post( new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition( roommatesList.size()-1 );
                            }
                        } );

                        Log.d( TAG, "Roommate saved: " + roommate );
                        fetched = true;
                        // Show a quick confirmation
                        Toast.makeText(getActivity().getApplicationContext(), "Roommate created for " + roommate.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to create a Roommate for " + roommate.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}