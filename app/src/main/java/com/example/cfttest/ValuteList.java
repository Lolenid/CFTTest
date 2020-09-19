package com.example.cfttest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValuteList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValuteList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private List<Valute> valutes;

    public ValuteList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ValuteList.
     */
    // TODO: Rename and change types and number of parameters
    public static ValuteList newInstance(String param1, String param2) {
        ValuteList fragment = new ValuteList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_valute_list, container, false);

        recyclerView = view.findViewById(R.id.valuteRecycler);
        valutes = new ArrayList<>();
        valutes.add(new Valute("id","numCode", "charCode", 10, "name", 3.4d));
        valutes.add(new Valute("id2","numCode2", "charCode2", 11, "name2", 3.5d));
        ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(valuteAdapter);
        return view;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        loadData();
//        Log.v("myTag", "size : " + valutes.size());
//        ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
//        recyclerView.setAdapter(valuteAdapter);
//    }
//    private void loadData(){
//        valutes = new ArrayList<>();
//        valutes.add(new Valute("id","numCode", "charCode", 10, "name", 3.4d));
//        valutes.add(new Valute("id2","numCode2", "charCode2", 11, "name2", 3.5d));
////        recyclerView.getAdapter().notifyDataSetChanged();
//    }
}