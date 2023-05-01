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

// A DialogFragment class to handle job lead additions from the job lead review activity
// It uses a DialogFragment to allow the input of a new job lead.
public class AddToShoppingListDialogFragment extends DialogFragment {

    private EditText itemView;
    private EditText amountView;
    private EditText detailsView;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddToShoppingListDialogListener {
        void addJobLead(ShoppingItem shoppingItem);
    }

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
        detailsView = layout.findViewById( R.id.editText3 );

        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        // Set its view (inflated above).
        builder.setView( layout );



        // Set the title of the AlertDialog
        builder.setTitle( "New Job Lead" );
        // Provide the negative button listener
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });
        // Provide the positive button listener
        builder.setPositiveButton( android.R.string.ok, new AddJobLeadListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class AddJobLeadListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Bundle result = new Bundle();
            //result.putString("bundleKey", "result");
            result.putString("item", itemView.getText().toString());
            result.putInt("amount", Integer.parseInt(amountView.getText().toString()));
            result.putString("details", detailsView.getText().toString());
            getParentFragmentManager().setFragmentResult("requestKey", result);
        }
        /*@Override
        public void onClick(DialogInterface dialog, int which) {
            // get the new job lead data from the user
            String item = itemView.getText().toString();
            int amount = Integer.parseInt(amountView.getText().toString());
            String details = detailsView.getText().toString();

            // create a new JobLead object
            ShoppingItem shoppingItem = new ShoppingItem( item, amount, details );

            // get the Activity's listener to add the new job lead
            AddToShoppingListDialogListener listener = (AddToShoppingListDialogListener) getActivity();//.
                    //getSupportFragmentManager().findFragmentByTag("ShoppingFragment").getChildFragmentManager().findFragmentByTag("f0");

            // add the new job lead
            listener.addJobLead( shoppingItem );

            // close the dialog
            dismiss();
        }*/
    }
}
