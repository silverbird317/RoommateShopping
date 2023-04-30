package edu.uga.cs.roommateshopping;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AllListsPagerAdapter extends FragmentStateAdapter {
    public AllListsPagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ShoppingListFragment();
        Bundle args = new Bundle();
        if (position == 0) {
            fragment = new ShoppingListFragment();
            //Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ShoppingListFragment.ARG_OBJECT, position + 1);
        } else {
            fragment = new AlreadyBoughtListFragment();
            //Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(AlreadyBoughtListFragment.ARG_OBJECT, position + 1);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}