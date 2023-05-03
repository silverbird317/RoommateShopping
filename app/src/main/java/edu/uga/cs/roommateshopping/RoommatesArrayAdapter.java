package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

// this class is a custom ArrayAdapter, holding Roommates
public class RoommatesArrayAdapter extends ArrayAdapter<Roommate> {

    public static final String DEBUG_TAG = "RoommatesArrayAdapter";

    private final Context context;
    private List<Roommate> values;
    private final List<Roommate> originalValues;

    /*
     * public constructor
     */
    public RoommatesArrayAdapter(Context context, ArrayList<Roommate> values) {
        super(context, 0, new ArrayList<Roommate>( values ));
        this.context = context;
        this.values = values;
        this.originalValues = new ArrayList<Roommate>( values );
        Log.d( DEBUG_TAG, "RoommatesArrayAdapter.values: object: " + values );
        Log.d( DEBUG_TAG, "RoommatesArrayAdapter.originalValues: object: " + originalValues );
    }

    /*
     * creates a single instance of Roommate xml
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.getView: position: " + position );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.getView: values size: " + values.size() );

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.roommate, parent, false);

        Roommate roommate = values.get( position );

        TextView name = itemView.findViewById(R.id.name);
        TextView email = itemView.findViewById(R.id.email);
        TextView phone = itemView.findViewById(R.id.phone);

        name.setText(roommate.getName());
        email.setText("" + roommate.getEmail());
        phone.setText(roommate.getPhone());

        Button delete = itemView.findViewById(R.id.remove);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roommates");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(DEBUG_TAG, "Deleting item");
                myRef.child(roommate.getName()).removeValue();
            }
        });

        Button pay = itemView.findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(DEBUG_TAG, "pay up");
            }
        });

        return itemView;
    }
}
