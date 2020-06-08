package com.justice.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static com.justice.recipeapp.ApplicationClass.foodList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton fob;
    private CoordinatorLayout coordinatorLayout;
    private MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setOnClickListeners();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this);
        adapter.setList(foodList);
        recyclerView.setAdapter(adapter);


        FirebaseFirestore.getInstance().collection("food").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (queryDocumentSnapshots.isEmpty()) {
                    return;
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {


                        Food food = doc.getDocument().toObject(Food.class);
                        food.setId(doc.getDocument().getId());
                        foodList.add(food);

                    }


                }
                adapter.notifyDataSetChanged();

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Food food = adapter.getList().get(viewHolder.getAdapterPosition());

                FirebaseFirestore.getInstance().collection("food").document(food.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Snackbar.make(bottomAppBar, "deleted: " + food.getFoodName(), Snackbar.LENGTH_LONG).show();
                            foodList.remove(food);
                            adapter.getList().remove(food);
                            adapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(bottomAppBar, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });

            }
        }).attachToRecyclerView(recyclerView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.searchItem);
        SearchView searchview = (SearchView) searchItem.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }

    private void setOnClickListeners() {
        fob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationClass.update = false;
                startActivity(new Intent(MainActivity.this, AddFoodActivity.class));
            }
        });
    }

    private void initWidgets() {
        recyclerView = findViewById(R.id.recyclerView);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        fob = findViewById(R.id.fob);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        setSupportActionBar(bottomAppBar);
    }
}
