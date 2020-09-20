package com.example.cfttest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String URL_CONNECTION = "http://www.cbr.ru/scripts/XML_daily.asp";
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

//        recyclerView.setAdapter(valuteAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ValuteAdapter valuteAdapter = new ValuteAdapter(valutes, new ValuteListener());
        recyclerView.setAdapter(valuteAdapter);
    }
    private void loadData(){
        valutes = new ArrayList<>();
        new DowlandXml().execute(URL_CONNECTION);
    }

    private class ValuteListener implements ValuteAdapter.Listener{
        @Override
        public void onValuteSelected(Valute valute, View view) {
            NavDirections action = ValuteListDirections.actionValuteListToConvertor(valute);
            Navigation.findNavController(view).navigate(action);
        }

    }
    private class DowlandXml extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                XmlParser xmlParser = new XmlParser();
                InputStream stream = downloadUrl(URL_CONNECTION);

                valutes = xmlParser.parse(stream);
              //  recyclerView.getAdapter().notifyDataSetChanged();
                Log.d("myTag", "Size : " + valutes.size());
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

        private InputStream downloadUrl(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            return conn.getInputStream();
        }
    }
//    private class DowlandXml extends AsyncTask<String, Void, String> {
//        private List<Valute> valutes;
//        private Context context;
//
//        public DowlandXml(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            try {
//                XmlParser xmlParser = new XmlParser();
//                InputStream stream = downloadUrl(URL_CONNECTION);
//
//                valutes = xmlParser.parse(stream);
//                Log.d("myTag", "Size : " + valutes.size());
//
//                return valutes.get(0).getId();
//
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        private InputStream downloadUrl(String urlString) throws IOException {
//            URL url = new URL(urlString);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            // Starts the query
//            conn.connect();
//            return conn.getInputStream();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            setContentView(R.layout.activity_valute_detail);
//            RecyclerView recyclerView = findViewById(R.id.recyclerView);
//            //Log.d("myTag", "Size2 : " + valutes.size());
//            ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
////
//            valuteAdapter.setListener(new ValuteAdapter.Listener() {
//                @Override
//                public void onClick(int position) {
//                    Log.v("myTag", "click on + " + position);
//                    Intent intent = new Intent(context, MainActivity.class);
//                    intent.putExtra(MainActivity.EXTRA_VALUTE, valutes.get(position));
//                    context.startActivity(intent);
//                }
//            });
////            recyclerView.getAdapter().notifyDataSetChanged();
//            recyclerView.setAdapter(valuteAdapter);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            //recyclerView.getAdapter().notifyDataSetChanged();
//        }
//
//    }
}

