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
public class AlreadyBoughtListFragment extends Fragment {

    private static final String TAG = "AlreadyBoughtList";

    private ListView listView;

    public static final String ARG_OBJECT = "object";
    public static double balance = 0.0;
    public static TextView money;

    //private QuizHistoryData quizHistoryData = null;
    private ArrayList<BuyItem> alreadyBoughtList = new ArrayList<BuyItem>();
    BuyArrayAdapter itemsAdapter;

    private FirebaseDatabase database;

    /*
     * required empty public constructor
     */
    public AlreadyBoughtListFragment() {
        // Required empty public constructor
    }

    /*
     * overrides oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * creates new instance of fragment
     */
    public static AlreadyBoughtListFragment newInstance() {

        AlreadyBoughtListFragment fragment = new AlreadyBoughtListFragment();

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
        return inflater.inflate(R.layout.fragment_already_bought_list, container, false);
    }

    /*
     * overrides onviewcreated
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);

        itemsAdapter = new BuyArrayAdapter( getActivity(), alreadyBoughtList );

        money = getView().findViewById(R.id.textView);
        money.setText("Your total balance is " + balance);


        // initialize the list
        listView.setAdapter( itemsAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("checkout");

        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                alreadyBoughtList.clear();
                balance = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BuyItem buyItem = postSnapshot.getValue(BuyItem.class);
                    buyItem.setKey(postSnapshot.getKey());
                    Log.d("afirebase", "changed: " + buyItem);
                    alreadyBoughtList.add(buyItem);
                    balance += buyItem.getPrice();
                    money.setText("Your total balance is " + balance);
                    Log.d(TAG, "ValueEventListener: added: " + buyItem);
                    Log.d(TAG, "ValueEventListener: key: " + postSnapshot.getKey());
                }
                Log.d("new list", "" + alreadyBoughtList.size());
                itemsAdapter.clear();
                itemsAdapter.addAll(alreadyBoughtList);
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
        super.onResume();
        Log.d( TAG, "AlreadyBoughtListFragment.onResume(): length: " + alreadyBoughtList.size() );

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Log.d("receive", "gua");
                String item = bundle.getString("item");
                int amount = bundle.getInt("amount");
                double price = bundle.getDouble("price");
                ShoppingItem shoppingItem = new ShoppingItem(item, amount, price);
                addToCheckout(shoppingItem);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    /*
     * adds bought item to checkout database
     */
    public void addToCheckout(ShoppingItem shoppingItem) {
        Log.d("addPurchasedItem", "added");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("checkout").child(shoppingItem.getItem());

        myRef.setValue( shoppingItem )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        listView.post( new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition( alreadyBoughtList.size()-1 );
                            }
                        } );

                        Log.d( TAG, "Item saved: " + shoppingItem );
                        // Show a quick confirmation
                        Toast.makeText(getActivity().getApplicationContext(), "Item created for " + shoppingItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to create item for " + shoppingItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}