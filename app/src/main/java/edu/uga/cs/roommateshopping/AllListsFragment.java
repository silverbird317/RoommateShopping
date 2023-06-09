package edu.uga.cs.roommateshopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/*
 * fragment that holds shopping list, shopping basket, and list of already purchased items
 */
public class AllListsFragment extends Fragment {
    AllListsPagerAdapter allListsPagerAdapter;
    ViewPager2 viewPager;

    /*
     * onCreateView override, sets xml layout for fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_lists, container, false);
    }

    /*
     * onViewCreated override, sets up tabs for different lists
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        allListsPagerAdapter = new AllListsPagerAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(allListsPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
            if (position == 0) {
                tab.setText("Shopping List");
            } else  if (position == 1) {
              tab.setText("Shopping Basket");
            } else {
                tab.setText("Already Bought");
            }
        }).attach();
    }
}