package edu.uga.cs.roommateshopping;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


/**
 * activity for shopping basket
 */
public class ShoppingBasketActivity extends AppCompatActivity {
    private static final String TAG = "ShoppingBasketActivity";

    /*
     * override onCreate
     */
    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        super.onCreate( savedInstanceState );

    }

    /*
     * called when item in list is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if( id == android.R.id.home ) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}
