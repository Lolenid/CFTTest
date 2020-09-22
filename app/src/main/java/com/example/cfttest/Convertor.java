package com.example.cfttest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Convertor extends Fragment {



    String button;
    Valute valute;
    TextView textName;
    TextView textName2;
    public Convertor() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("myTag", "onCreate");
        if (savedInstanceState != null){
            Log.v("myTag", "savedInstanceState != null");
            textName.setText(savedInstanceState.getString("left"));
            textName2.setText(savedInstanceState.getString("right"));
        }
            ConvertorArgs fragment2Args = ConvertorArgs.fromBundle(getArguments());
            button = fragment2Args.getSelectButton();
            valute = fragment2Args.getSelectValute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("myTag", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_convertor, container, false);
        textName = view.findViewById(R.id.textView4);
        textName2 = view.findViewById(R.id.textView5);
        if (button.equals("left")) {
            textName.setText(valute.getName());
        } else if (button.equals("right")) {
            textName2.setText(valute.getName());
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v("myTag", "onSaveInstanceState");
        outState.putString("left", textName.getText().toString());
        outState.putString("right", textName2.getText().toString());
    }
}