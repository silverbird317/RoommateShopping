package edu.uga.cs.roommateshopping;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

/*
 * DialogFragment to ask use to enter new shopping item
 */
public class AddToShoppingListDialogFragment extends DialogFragment {

    private EditText itemView;
    private EditText amountView;
    private EditText priceView;

    /*
     * overrides onCreateDialog, sets up components in alert dialog xml
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_shopping_list_dialog,
                getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        itemView = layout.findViewById( R.id.editText1 );
        amountView = layout.findViewById( R.id.editText2 );
        priceView = layout.findViewById( R.id.editText3 );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        // Set its view (inflated above).
        builder.setView( layout );



        // Set the title of the AlertDialog
        builder.setTitle( "New Shopping Item" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddItemListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    /*
     * listener to pass information to fragment
     */
    private class AddItemListener implements DialogInterface.OnClickListener {
        /*
         * button onclick override
         */
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Bundle result = new Bundle();
            //result.putString("bundleKey", "result");
            result.putString("item", itemView.getText().toString());
            result.putInt("amount", Integer.parseInt(amountView.getText().toString()));
            result.putDouble("price", Double.parseDouble(priceView.getText().toString()));
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
    }
}
