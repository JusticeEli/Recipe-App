package com.justice.recipeapp;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;



public class IngredientsFragment extends Fragment {
    private TextInputLayout foodName;

    private TextInputLayout ingredients_1;
    private TextInputLayout ingredients_2;
    private TextInputLayout ingredients_3;
    private TextInputLayout ingredients_4;
    private TextInputLayout ingredients_5;

    public IngredientsFragment(final ViewPager viewPager) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                /////SETTING INGREDIENTS////////////

                if (foodName.getEditText().getText().toString().trim().isEmpty()){

                    Toast.makeText(getActivity(), "Please enter Food name", Toast.LENGTH_SHORT).show();

                    viewPager.setCurrentItem(0);
                    foodName.getEditText().requestFocus();

                    return;
                }

                if (position==1){

                    if (ApplicationClass.update){
                       ApplicationClass.originalFood.setFoodName(foodName.getEditText().getText().toString().trim());
                       ApplicationClass.originalFood.setIngredient_1(ingredients_1.getEditText().getText().toString().trim());
                       ApplicationClass.originalFood.setIngredient_2(ingredients_2.getEditText().getText().toString().trim());
                       ApplicationClass.originalFood.setIngredient_3(ingredients_3.getEditText().getText().toString().trim());
                       ApplicationClass.originalFood.setIngredient_4(ingredients_4.getEditText().getText().toString().trim());
                       ApplicationClass.originalFood.setIngredient_5(ingredients_5.getEditText().getText().toString().trim());

                    }else {
                        AddFoodActivity.food.setFoodName(foodName.getEditText().getText().toString().trim());
                        AddFoodActivity.food.setIngredient_1(ingredients_1.getEditText().getText().toString().trim());
                        AddFoodActivity.food.setIngredient_2(ingredients_2.getEditText().getText().toString().trim());
                        AddFoodActivity.food.setIngredient_3(ingredients_3.getEditText().getText().toString().trim());
                        AddFoodActivity.food.setIngredient_4(ingredients_4.getEditText().getText().toString().trim());
                        AddFoodActivity.food.setIngredient_5(ingredients_5.getEditText().getText().toString().trim());

                    }





                }


            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getActivity(), "page selected"+position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);


        foodName = view.findViewById(R.id.foodName);

        ingredients_1 = view.findViewById(R.id.ingredient_1);
        ingredients_2 = view.findViewById(R.id.ingredient_2);
        ingredients_3 = view.findViewById(R.id.ingredient_3);
        ingredients_4 = view.findViewById(R.id.ingredient_4);
        ingredients_5 = view.findViewById(R.id.ingredient_5);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (ApplicationClass.update){

            setDefaultValues();

        }


    }

    private void setDefaultValues() {
        foodName.getEditText().setText(ApplicationClass.originalFood.getFoodName());
        ingredients_1.getEditText().setText(ApplicationClass.originalFood.getIngredient_1());
        ingredients_2.getEditText().setText(ApplicationClass.originalFood.getIngredient_2());
        ingredients_3.getEditText().setText(ApplicationClass.originalFood.getIngredient_3());
        ingredients_4.getEditText().setText(ApplicationClass.originalFood.getIngredient_4());
        ingredients_5.getEditText().setText(ApplicationClass.originalFood.getIngredient_5());

    }
}
