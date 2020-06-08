package com.justice.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity implements MethodsFragment.SubmitClicked {


    ///////////////////////
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static List<Fragment> fragmentList = new ArrayList<>();
    public static List<String> fragmentNames = new ArrayList<>();

    public static Food food = new Food();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        initWidgets();
        setUpViewPager();
        initFragmentViews();
    }

    private void initFragmentViews() {

    }

    private void setUpViewPager() {
        fragmentNames.clear();
        fragmentList.clear();

        fragmentList.add(new IngredientsFragment(viewPager));
        fragmentList.add(new MethodsFragment());

        fragmentNames.add("Ingredients");
        fragmentNames.add("Method");

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);


    }

    private void initWidgets() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void submitBtnTapped() {

        if (ApplicationClass.update) {
            updateFood();
        } else {
            addFood();

        }

    }

    private void addFood() {
        progressDialog.setTitle("Add");
        progressDialog.setMessage("adding food");
        progressDialog.show();
        FirebaseFirestore.getInstance().collection("food").add(food).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Snackbar.make(coordinatorLayout, "added", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AddFoodActivity.this, "added", Toast.LENGTH_SHORT).show();
                } else {

                    Snackbar.make(coordinatorLayout, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AddFoodActivity.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }
                progressDialog.dismiss();
                onBackPressed();
            }
        });


    }

    private void updateFood() {
        progressDialog.setTitle("Update");
        progressDialog.setMessage("updating food");

        progressDialog.show();
        FirebaseFirestore.getInstance().collection("food").document(ApplicationClass.originalFood.getId()).set(ApplicationClass.originalFood).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Snackbar.make(coordinatorLayout, "updated", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AddFoodActivity.this, "updated :", Toast.LENGTH_SHORT).show();


                } else {

                    Snackbar.make(coordinatorLayout, "Error: " + task.getException().getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(AddFoodActivity.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                }
                progressDialog.dismiss();
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ApplicationClass.update = false;
        ApplicationClass.originalFood = null;
    }
}
