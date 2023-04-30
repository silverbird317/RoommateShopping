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
public class BuyArrayAdapter extends ArrayAdapter<BuyItem> {

    public static final String DEBUG_TAG = "QuizResultArrayAdapter";

    private final Context context;
    private List<BuyItem> values;
    private final List<BuyItem> originalValues;

    /*
     * public constructor
     */
    public BuyArrayAdapter(Context context, List<BuyItem> values) {
        super(context, 0, new ArrayList<BuyItem>( values ));
        this.context = context;
        this.values = new ArrayList<BuyItem>( values );
        this.originalValues = new ArrayList<BuyItem>( values );
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

        BuyItem shoppingItem = values.get( position );

        TextView item = itemView.findViewById(R.id.item);
        TextView amount = itemView.findViewById(R.id.amount);
        TextView details = itemView.findViewById(R.id.details);

        item.setText(shoppingItem.getItem());
        amount.setText("" + shoppingItem.getPrice());
        details.setText("" + shoppingItem.getQuantity());

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
