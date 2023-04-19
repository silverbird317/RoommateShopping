package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

/**
 * fragment for list of options
 */
public class ShoppingListFragment extends ListFragment {

    private static final String TAG = "CountryVersions";

    // countries list
    private final String[] countries = {
            "one ducky", "two ducky", "three ducky", "four ducky", "five ducky", "six ducky"
    };

    // indicate if this is a landscape mode with both fragments present or not
    boolean twoFragmentsActivity = false;

    // list selection index
    int index = 0;


    /*
     * Required default empty constructor
     */
    public ShoppingListFragment()
    {
    }

    /*
     * onViewCreated override, set up orientation handling, restore data if any
     */
    @Override
    public void onViewCreated( @NonNull View view, Bundle savedInstanceState ) {

        super.onViewCreated(view, savedInstanceState);

        // create a new ArrayAdapter for the list
        setListAdapter( new ArrayAdapter<>( getActivity(), android.R.layout.simple_list_item_activated_1, countries ) );

        // set the twoFragmentsActivity variable to true iff landscape view
        View detailsFrame = getActivity().findViewById(R.id.shoppingList);

        //twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        // restore the saved list index selection (Android version no), if available
        if( savedInstanceState != null ) {
            index = savedInstanceState.getInt("countrySelection", 0 );
        }

        getListView().setChoiceMode( ListView.CHOICE_MODE_SINGLE );
        getListView().setItemChecked( index, true );

    }


    /*
     * save the index of the selected item
     */
    @Override
    public void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);
        outState.putInt( "countrySelection", index);
    }
}