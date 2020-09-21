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
    private static final String URL_CONNECTION = "https://www.cbr.ru/scripts/XML_daily.asp";

    private RecyclerView recyclerView;
    private List<Valute> valutes;

    public ValuteList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_valute_list, container, false);
        recyclerView = view.findViewById(R.id.valuteRecycler);

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
                valutes.addAll(xmlParser.parse(stream));

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
}

