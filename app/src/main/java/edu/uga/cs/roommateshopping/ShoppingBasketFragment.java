package edu.uga.cs.roommateshopping;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A fragment representing a list of Items.
 */
public class ShoppingBasketFragment extends Fragment {

    private static final String TAG = "ShoppingBasketFragment";

    private ListView listView;

    public static final String ARG_OBJECT = "object";

    //private QuizHistoryData quizHistoryData = null;
    private List<ShoppingItem> quizResultList;
    ListArrayAdapter itemsAdapter;
    private int versionNum;

    /*
     * required empty public constructor
     */
    public ShoppingBasketFragment() {
        // Required empty public constructor
    }

    /*
     * overrides oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ShoppingListFragment newInstance() {

        ShoppingListFragment fragment = new ShoppingListFragment();

        Bundle args = new Bundle();
        //args.putInt( "index", index );
        fragment.setArguments( args );

        return fragment;
    }

    /*
     * overrides onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }

    /*
     * overrides onviewcreated
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        listView = getView().findViewById(R.id.listView);
        //quizHistoryData = new QuizHistoryData(getActivity());
        quizResultList = new ArrayList<ShoppingItem>(); //QuizHistoryData.quizHistory;
        quizResultList.add(new ShoppingItem("Ducky", 3));
        quizResultList.add(new ShoppingItem("Candy", 200));
        quizResultList.add(new ShoppingItem("Ga", 3));
        itemsAdapter = new ListArrayAdapter( getActivity(), quizResultList );

        // set headers
        //TextView titleView = view.findViewById( R.id.questionNum );
        //TextView question = view.findViewById( R.id.question );

    }

    /*
     * overrides onresume, loads quiz results back
     */
    public void onResume() {
        //Log.d( TAG, "Flow2_A.onResume()"  );
        super.onResume();
        //if( quizHistoryData != null ) {
        //  quizHistoryData.open();
        //quizHistoryData.restorelJobLeads();
        quizResultList = new ArrayList<ShoppingItem>(); //QuizHistoryData.quizHistory;
        //quizHistoryData.retrieveQuizResults();
        quizResultList.add(new ShoppingItem("Ducky", 3));
        quizResultList.add(new ShoppingItem("Candy", 200));
        quizResultList.add(new ShoppingItem("Ga", 3));

        Log.d( TAG, "ReviewJobLeadsFragment.onResume(): length: " + quizResultList.size() );

        itemsAdapter = new ListArrayAdapter(getActivity(), quizResultList );
        listView.setAdapter(itemsAdapter);
        //}
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle( getResources().getString( R.string.app_name ) );
    }
}