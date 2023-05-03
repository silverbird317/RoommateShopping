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
 * A fragment representing a shoppingbasket
 */
public class ShoppingBasketFragment extends Fragment {

    private static final String TAG = "ShoppingBasketFragment";

    private ListView listView;

    public static final String ARG_OBJECT = "object";

    //private QuizHistoryData quizHistoryData = null;
    private ArrayList<ShoppingItem> basketList = new ArrayList<ShoppingItem>();
    ShoppingBasketArrayAdapter itemsAdapter;
    private int versionNum;

    private FirebaseDatabase database;
    public static ArrayList<ShoppingItem> checkoutList = new ArrayList<>();

    /*
     * required empty public constructor
     */
    public ShoppingBasketFragment() {
        // Required empty public constructor
    }

    /*
     * overrides oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ShoppingBasketFragment newInstance() {

        ShoppingBasketFragment fragment = new ShoppingBasketFragment();

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
        return inflater.inflate(R.layout.fragment_shopping_basket, container, false);
    }

    /*
     * overrides onviewcreated
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);

        itemsAdapter = new ShoppingBasketArrayAdapter( getActivity(), basketList );

        Button checkout = getView().findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ShoppingItem item : checkoutList) {
                    checkoutItem(item);
                }
                checkoutList.clear();
            }
        });


        // initialize the list
        listView.setAdapter( itemsAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingbasket");

        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                basketList.clear(); // clear the current content; this is inefficient!
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ShoppingItem shoppingItem = postSnapshot.getValue(ShoppingItem.class);
                    shoppingItem.setKey(postSnapshot.getKey());
                    Log.d("firebase", "changed: " + shoppingItem);
                    basketList.add(shoppingItem);
                    Log.d(TAG, "ValueEventListener: added: " + shoppingItem);
                    Log.d(TAG, "ValueEventListener: key: " + postSnapshot.getKey());
                }
                Log.d("new quiz", "" + basketList.size());
                itemsAdapter.clear();
                itemsAdapter.addAll(basketList);
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
        Log.d( TAG, "ShoppingBasketFragment.onResume(): length: " + basketList.size() );

        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                Log.d("receive", "gua");
                String item = bundle.getString("item");
                int amount = bundle.getInt("amount");
                double price = bundle.getDouble("price");
                ShoppingItem shoppingItem = new ShoppingItem(item, amount, price);
                addBasketItem(shoppingItem);
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }

    /*
     * adds item to shoppingbasket database
     */
    public void addBasketItem(ShoppingItem shoppingItem) {
        Log.d("addBasket", "added");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("shoppingbasket").child(shoppingItem.getItem());

        myRef.setValue( shoppingItem )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        listView.post( new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition( basketList.size()-1 );
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
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to create a item for " + shoppingItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
     * add the item to checkout database and remove from basket database
     */
    public void checkoutItem(ShoppingItem shoppingItem) {
        Log.d("checkoutItem", "added");

        BuyItem buyItem = new BuyItem(shoppingItem.getItem(), shoppingItem.getAmount(), shoppingItem.getPrice());
        buyItem.setBuyer(MainActivity.email);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("checkout").child(shoppingItem.getItem());

        myRef.setValue( buyItem )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        listView.post( new Runnable() {
                            @Override
                            public void run() {
                                listView.smoothScrollToPosition( basketList.size()-1 );
                            }
                        } );

                        Log.d( TAG, "Checked out: " + buyItem );
                        // Show a quick confirmation
                        Toast.makeText(getActivity().getApplicationContext(), "Checked out: " + buyItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getActivity().getApplicationContext(), "Failed to checkout " + buyItem.getItem(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        database.getReference("shoppingbasket").child(shoppingItem.getItem()).removeValue();
    }
}