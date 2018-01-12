package com.pbtec.app.barbeariaclub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;
import com.pbtec.app.barbeariaclub.threds.ThreadAgenda;

public class Agenda extends Fragment {
    private final String MY_PREF = "PreferênciasApp";
    private FragmentListenner fragmentListenner;

    public Agenda(Context context){
        if(context instanceof FragmentListenner){
            fragmentListenner = (FragmentListenner) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        if(fragmentListenner.verificarInternet()) {
            ThreadAgenda thread = new ThreadAgenda(getContext());
            thread.execute(getContext().getSharedPreferences(MY_PREF, 0).getString("email_cliente", "nulo"));
            RecyclerView listView = (RecyclerView) view.findViewById(R.id.recyclerView_agenda);
        }

        return view;
    }
}
