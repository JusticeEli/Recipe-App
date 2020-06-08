package com.justice.recipeapp;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import static android.webkit.WebSettings.PluginState.ON;


public class MethodsFragment extends Fragment {

    private Button submitBtn;
    private SubmitClicked submitClicked;

    private TextInputLayout methods_1;
    private TextInputLayout methods_2;
    private TextInputLayout methods_3;
    private TextInputLayout methods_4;
    private TextInputLayout methods_5;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_methods, container, false);

        submitBtn = view.findViewById(R.id.submitBtn);

        methods_1 = view.findViewById(R.id.method_1);
        methods_2 = view.findViewById(R.id.method_2);
        methods_3 = view.findViewById(R.id.method_3);
        methods_4 = view.findViewById(R.id.method_4);
        methods_5 = view.findViewById(R.id.method_5);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        submitClicked = (SubmitClicked) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

          setOnClickListeners();
        if (ApplicationClass.update) {
            setDefaultValues();
        }



    }

    private void setOnClickListeners() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ApplicationClass.update){
                    ApplicationClass.originalFood.setMethod_1(methods_1.getEditText().getText().toString().trim());
                    ApplicationClass.originalFood.setMethod_2(methods_2.getEditText().getText().toString().trim());
                    ApplicationClass.originalFood.setMethod_3(methods_3.getEditText().getText().toString().trim());
                    ApplicationClass.originalFood.setMethod_4(methods_4.getEditText().getText().toString().trim());
                    ApplicationClass.originalFood.setMethod_5(methods_5.getEditText().getText().toString().trim());

                }else {
                    AddFoodActivity.food.setMethod_1(methods_1.getEditText().getText().toString().trim());
                    AddFoodActivity.food.setMethod_2(methods_2.getEditText().getText().toString().trim());
                    AddFoodActivity.food.setMethod_3(methods_3.getEditText().getText().toString().trim());
                    AddFoodActivity.food.setMethod_4(methods_4.getEditText().getText().toString().trim());
                    AddFoodActivity.food.setMethod_5(methods_5.getEditText().getText().toString().trim());

                }

                submitClicked.submitBtnTapped();
            }
        });
    }

    private void setDefaultValues() {
        methods_1.getEditText().setText(ApplicationClass.originalFood.getMethod_1());
        methods_2.getEditText().setText(ApplicationClass.originalFood.getMethod_2());
        methods_3.getEditText().setText(ApplicationClass.originalFood.getMethod_3());
        methods_4.getEditText().setText(ApplicationClass.originalFood.getMethod_4());
        methods_5.getEditText().setText(ApplicationClass.originalFood.getMethod_5());

    }

    public interface SubmitClicked {
        void submitBtnTapped();
    }
}
