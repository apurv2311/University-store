package com.example.thaparstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

public class Home extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private Button mAccount;
    private Button mSell;
    private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mReference=FirebaseDatabase.getInstance().getReference().child("all_adds");

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(this));
// Create an adapter and supply the data to be displayed.
        FirebaseRecyclerOptions<Get_add> options
                = new FirebaseRecyclerOptions.Builder<Get_add>()
                .setQuery(mReference, Get_add.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        mAdapter = new ListAdapter(options);
        // Connecting Adapter class with the Recycler view*/
        mRecyclerView.setAdapter(mAdapter);
        //if(bundle.getString("name")!=null)
            //mAccount.setText(name);
        mSell=findViewById(R.id.sell_anything);
        mAdapter.notifyDataSetChanged();

    }

    public void account_page(View view) {
        Intent intent=new Intent(Home.this,Login.class);
        startActivity(intent);
    }

    public void sell_page(View view) {
        Intent intent=new Intent(Home.this,sell_info.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {

        
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            mAccount=findViewById(R.id.my_account);
            mAccount.setText(user.getDisplayName());
            mSell.setEnabled(true);
        }
        super.onResume();
    }
    @Override protected void onStart()
    {
        mAdapter.startListening();
        super.onStart();

    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        mAdapter.stopListening();
        super.onStop();

    }

    @Override
    protected void onPause() {


        super.onPause();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.sign_out_menu) {
            AuthUI.getInstance().signOut(this);
            mSell.setEnabled(false);
            mAccount.setText(R.string.account);
            return true;
        }

        else
            return super.onOptionsItemSelected(item);
    }
}