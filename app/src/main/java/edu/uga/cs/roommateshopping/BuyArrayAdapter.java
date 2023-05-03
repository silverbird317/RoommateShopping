package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/*
 * this class is a custom ArrayAdapter, holding BuyItems
 */
public class BuyArrayAdapter extends ArrayAdapter<BuyItem> {

    public static final String DEBUG_TAG = "BuyArrayAdapter";

    private final Context context;
    private List<BuyItem> values;
    private final List<BuyItem> originalValues;

    /*
     * public constructor
     */
    public BuyArrayAdapter(Context context, ArrayList<BuyItem> values) {
        super(context, 0, new ArrayList<BuyItem>( values ));
        this.context = context;
        this.values = values;
        this.originalValues = new ArrayList<>( values );
        Log.d( DEBUG_TAG, "BuyArrayAdapter.values: object: " + values );
        Log.d( DEBUG_TAG, "BuyArrayAdapter.originalValues: object: " + originalValues );
    }

    /*
     * creates the view of one item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d( DEBUG_TAG, "BuyArrayAdapter.getView: position: " + position );
        Log.d( DEBUG_TAG, "BuyArrayAdapter.getView: values size: " + values.size() );

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.bought_item, parent, false);

        BuyItem buyItem = values.get( position );

        TextView item = itemView.findViewById(R.id.item);
        TextView amount = itemView.findViewById(R.id.amount);
        TextView price = itemView.findViewById(R.id.details);
        TextView buyer = itemView.findViewById(R.id.buyer);

        item.setText(buyItem.getItem());
        amount.setText("" + buyItem.getAmount());
        price.setText("" + buyItem.getPrice());
        buyer.setText(buyItem.getBuyer());

        Button delete = itemView.findViewById(R.id.button);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("checkout");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(DEBUG_TAG, "Deleting item");
                AlreadyBoughtListFragment.balance -= buyItem.getPrice();
                AlreadyBoughtListFragment.money.setText("Your total balance is " + AlreadyBoughtListFragment.balance);
                myRef.child(buyItem.getItem()).removeValue();
            }
        });

        return itemView;
    }
}
