package edu.uga.cs.roommateshopping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/*
 * FragmentStateAdapter to handle tabs in the viewpager
 */
public class AllListsPagerAdapter extends FragmentStateAdapter {
    public AllListsPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    /*
     * creates the fragments under each tab using int position
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        String tag = "";
        if (position == 0) {
            fragment = new ShoppingListFragment();
            //Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ShoppingListFragment.ARG_OBJECT, position + 1);
            tag = "ShoppingList";
        } else if (position == 1) {
            fragment = new ShoppingBasketFragment();
            //Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ShoppingBasketFragment.ARG_OBJECT, position + 1);
            tag = "ShoppingBasket";
        } else {
            fragment = new AlreadyBoughtListFragment();
            //Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(AlreadyBoughtListFragment.ARG_OBJECT, position + 1);
            tag = "AlreadyBought";
        }
        fragment.setArguments(args);
        return fragment;
    }

    /*
     * returns the total number of tabs
     */
    @Override
    public int getItemCount() {
        return 3;
    }
}