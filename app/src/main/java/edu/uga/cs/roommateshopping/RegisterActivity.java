package edu.uga.cs.roommateshopping;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
 * activity to register for new accounts
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText nameText;
    private EditText phoneText;

    private Button Btn;
    private FirebaseAuth mAuth;

    /*
     * onCreate override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // initialising all views through id defined above
        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phone);
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        Btn = findViewById(R.id.btnregister);

        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }

    /*
     * method to take user input and register new account
     */
    private void registerNewUser()
    {
        // Take the value of two edit texts in Strings
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String name = nameText.getText().toString();
        String phone = phoneText.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Roommate roommate = new Roommate(name, email, phone);
                            addRoommate(roommate);

                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(RegisterActivity.this,
                                    MainActivity.class);
                            intent.putExtra("Message", email);
                            startActivity(intent);
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }

    /*
     * add newly registered user to roommates list
     */
    public void addRoommate(Roommate roommate) {
        Log.d("addJobLead", "added");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roommates").child(roommate.getName());

        myRef.setValue( roommate )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d( "roommate save", "Job lead saved: " + roommate );
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Job lead created for " + roommate.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getApplicationContext(), "Failed to create a Job lead for " + roommate.getName(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}