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

import java.util.ArrayList;
import java.util.List;

// this class is a custom ArrayAdapter, holding QuizResults
public class RoommatesArrayAdapter extends ArrayAdapter<Roommate> {

    public static final String DEBUG_TAG = "QuizResultArrayAdapter";

    private final Context context;
    private List<Roommate> values;
    private final List<Roommate> originalValues;

    /*
     * public constructor
     */
    public RoommatesArrayAdapter(Context context, List<Roommate> values) {
        super(context, 0, new ArrayList<Roommate>( values ));
        this.context = context;
        this.values = new ArrayList<Roommate>( values );
        this.originalValues = new ArrayList<Roommate>( values );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.values: object: " + values );
        Log.d( DEBUG_TAG, "JobLeadArrayAdapter.originalValues: object: " + originalValues );
    }

    /*
     * this overridden method creates a single item's view, to be used in a ListView.
     * position is supplied by Android and indicates which item on the list should be rendered.
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

        Button delete = itemView.findViewById(R.id.button);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(DEBUG_TAG, "Deleting item");
                //values.remove(position);
            }
        });

        return itemView;
    }
}
