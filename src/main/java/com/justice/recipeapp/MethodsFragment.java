package com.justice.recipeapp;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import static android.webkit.WebSettings.PluginState.ON;


public class MethodsFragment extends Fragment {

    private Button submitBtn;
    private SubmitClicked submitClicked;
    Food food = new Food();

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


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////////SETTING METHODS///////////////////
                food.setMethod_1(methods_1.getEditText().getText().toString().trim());
                food.setMethod_2(methods_2.getEditText().getText().toString().trim());
                food.setMethod_3(methods_3.getEditText().getText().toString().trim());
                food.setMethod_4(methods_4.getEditText().getText().toString().trim());
                food.setMethod_5(methods_5.getEditText().getText().toString().trim());

                submitClicked.submitBtnTapped();
            }
        });

    }

    public interface SubmitClicked {
        void submitBtnTapped();
    }
}
