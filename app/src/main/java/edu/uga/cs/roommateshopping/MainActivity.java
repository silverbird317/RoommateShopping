package edu.uga.cs.roommateshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * activity that holds all fragments
 */
public class MainActivity extends AppCompatActivity {

    private int fragmentTracker= -1;
    public static String email;

    /*
     * onCreate override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shoppingList = findViewById(R.id.shopping);
        Button roommates = findViewById(R.id.roommates);
        Button user = findViewById(R.id.user);

        Intent intent = getIntent();
        email = intent.getStringExtra("Message");

        shoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentTracker != 0) {
                    fragmentTracker = 0;
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.shoppingList, AllListsFragment.class, savedInstanceState, "ShoppingFragment");

                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
            }
        });
        roommates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentTracker != 1) {
                    fragmentTracker = 1;
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.shoppingList, RoommatesFragment.class, savedInstanceState, "Roommate_Fragment");

                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentTracker != 2) {
                    fragmentTracker = 2;
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.shoppingList, UsersFragment.class, savedInstanceState, "Users_Fragment");

                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}