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
public class ListArrayAdapter extends ArrayAdapter<ShoppingItem> {

    public static final String DEBUG_TAG = "QuizResultArrayAdapter";

    private final Context context;
    private List<ShoppingItem> values;
    private final List<ShoppingItem> originalValues;

    /*
     * public constructor
     */
    public ListArrayAdapter(Context context, ArrayList<ShoppingItem> values) {
        super(context, 0, new ArrayList<ShoppingItem>( values ));
        this.context = context;
        this.values = values;
        this.originalValues = new ArrayList<ShoppingItem>( values );
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
        View itemView = inflater.inflate(R.layout.shopping_item, parent, false);

        ShoppingItem shoppingItem = values.get( position );

        TextView item = itemView.findViewById(R.id.item);
        TextView amount = itemView.findViewById(R.id.amount);
        TextView details = itemView.findViewById(R.id.details);

        item.setText(shoppingItem.getItem());
        amount.setText("" + shoppingItem.getAmount());
        details.setText(shoppingItem.getDetails());

        Button delete = itemView.findViewById(R.id.button);
        CheckBox checkBox = itemView.findViewById(R.id.checkBox);

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
