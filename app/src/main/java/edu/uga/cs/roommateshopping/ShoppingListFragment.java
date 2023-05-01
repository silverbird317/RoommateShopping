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
 * A fragment representing a list of Items.
 */
public class ShoppingListFragment extends Fragment {

    private static final String TAG = "ResultsHistoryFragment";

    private ListView listView;

    public static final String ARG_OBJECT = "object";

    //private QuizHistoryData quizHistoryData = null;
    private List<ShoppingItem> quizResultList;
    ListArrayAdapter itemsAdapter;
    private int versionNum;

    private FirebaseDatabase database;

    /*
     * required empty public constructor
     */
    public ShoppingListFragment() {
        // Required empty public constructor
    }

    /*
     * overrides oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ShoppingListFragment newInstance() {

        ShoppingListFragment fragment = new ShoppingListFragment();

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
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    /*
     * overrides onviewcreated
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);
        //quizHistoryData = new QuizHistoryData(getActivity());
        quizResultList = new ArrayList<ShoppingItem>(); //QuizHistoryData.quizHistory;
        quizResultList.add(new ShoppingItem("Ducky", 3));
        quizResultList.add(new ShoppingItem("Candy", 200));
        quizResultList.add(new ShoppingItem("Ga", 3));
        itemsAdapter = new ListArrayAdapter( getActivity(), quizResultList );

        // set headers
        //TextView titleView = view.findViewById( R.id.questionNum );
        //TextView question = view.findViewById( R.id.question );

        FloatingActionButton floatingButton = getView().findViewById(R.id.floatingActionButton);
        floatingButton.bringToFront();
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("harf", "gua");
                DialogFragment newFragment = new AddToShoppingListDialogFragment();
                newFragment.show( getParentFragmentManager(), null);
            }
        });


        // initialize the Job Lead list
        quizResultList = new ArrayList<ShoppingItem>();
        listView.setAdapter( itemsAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppinglist");

        // Set up a listener (event handler) to receive a value for the database reference.
        // This type of listener is called by Firebase once by immediately executing its onDataChange method
        // and then each time the value at Firebase changes.
        //
        // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
        // to maintain job leads.
        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                quizResultList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    ShoppingItem jobLead = postSnapshot.getValue(ShoppingItem.class);
                    jobLead.setKey( postSnapshot.getKey() );
                    quizResultList.add( jobLead );
                    Log.d( TAG, "ValueEventListener: added: " + jobLead );
                    Log.d( TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                Log.d( TAG, "ValueEventListener: notifying recyclerAdapter" );
                itemsAdapter.notifyDataSetChanged();
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
        //Log.d( TAG, "Flow2_A.onResume()"  );
        super.onResume();
        //if( quizHistoryData != null ) {
          //  quizHistoryData.open();
            //quizHistoryData.restorelJobLeads();
            quizResultList = new ArrayList<ShoppingItem>(); //QuizHistoryData.quizHistory;
            //quizHistoryData.retrieveQuizResults();
            quizResultList.add(new ShoppingItem("Ducky", 3));
            quizResultList.add(new ShoppingItem("Candy", 200));
            quizResultList.add(new ShoppingItem("Ga", 3));

            Log.d( TAG, "ReviewJobLeadsFragment.onResume(): length: " + quizResultList.size() );

            itemsAdapter = new ListArrayAdapter(getActivity(), quizResultList );
            listView.setAdapter(itemsAdapter);
        //}

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                //String result = bundle.getString("bundleKey");
                String item = bundle.getString("item");
                int amount = bundle.getInt("amount");
                String details = bundle.getString("details");
                // Do something with the result
                ShoppingItem shoppingItem = new ShoppingItem(item, amount, details);
                addJobLead(shoppingItem);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    // this is our own callback for a AddJobLeadDialogFragment which adds a new job lead.
    public void addJobLead(ShoppingItem shoppingItem) {
        Log.d("addJobLead", "added");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppinglist");

        myRef.push().setValue( shoppingItem )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        listView.post( new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition( quizResultList.size()-1 );
                            }
                        } );

                        Log.d( TAG, "Job lead saved: " + shoppingItem );
                        // Show a quick confirmation
                        Toast.makeText(getActivity().getApplicationContext(), "Job lead created for " + shoppingItem.getItem(),
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to create a Job lead for " + shoppingItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // This is our own callback for a DialogFragment which edits an existing JobLead.
    // The edit may be an update or a deletion of this JobLead.
    // It is called from the EditJobLeadDialogFragment.
    /*public void updateJobLead( int position, JobLead jobLead, int action ) {
        if( action == EditJobLeadDialogFragment.SAVE ) {
            Log.d( DEBUG_TAG, "Updating job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );

            // Update the recycler view to show the changes in the updated job lead in that view
            recyclerAdapter.notifyItemChanged( position );

            // Update this job lead in Firebase
            // Note that we are using a specific key (one child in the list)
            DatabaseReference ref = database
                    .getReference()
                    .child( "jobleads" )
                    .child( jobLead.getKey() );

            // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
            // to maintain job leads.
            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().setValue( jobLead ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "updated job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );
                            Toast.makeText(getApplicationContext(), "Job lead updated for " + jobLead.getCompanyName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to update job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );
                    Toast.makeText(getApplicationContext(), "Failed to update " + jobLead.getCompanyName(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if( action == EditJobLeadDialogFragment.DELETE ) {
            Log.d( DEBUG_TAG, "Deleting job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );

            // remove the deleted job lead from the list (internal list in the App)
            jobLeadsList.remove( position );

            // Update the recycler view to remove the deleted job lead from that view
            recyclerAdapter.notifyItemRemoved( position );

            // Delete this job lead in Firebase.
            // Note that we are using a specific key (one child in the list)
            DatabaseReference ref = database
                    .getReference()
                    .child( "jobleads" )
                    .child( jobLead.getKey() );

            // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
            // to maintain job leads.
            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "deleted job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );
                            Toast.makeText(getApplicationContext(), "Job lead deleted for " + jobLead.getCompanyName(),
                                    Toast.LENGTH_SHORT).show();                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to delete job lead at: " + position + "(" + jobLead.getCompanyName() + ")" );
                    Toast.makeText(getApplicationContext(), "Failed to delete " + jobLead.getCompanyName(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/
}