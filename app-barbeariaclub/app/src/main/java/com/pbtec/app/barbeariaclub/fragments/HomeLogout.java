package com.pbtec.app.barbeariaclub.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.pbtec.app.barbeariaclub.R;
import com.pbtec.app.barbeariaclub.interfaces.FragmentListenner;


public class HomeLogout extends Fragment {
    private Button quero_cadastrar;
    private FragmentListenner mListener; //interface como objeto para comandar a activity

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListenner) {
            mListener = (FragmentListenner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar FragmentListenner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_logout, container, false);

        WebView web = (WebView)view.findViewById(R.id.web_view);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "A Barbearia Club se faz presente no mercado com objetivo de oferecer " +
                "um trabalho diferenciado, no que diz respeito a beleza masculina, dentro de um ambiente confortavel " +
                "e pensado para o homem dos tempos atuais.";
        text+= "</p></body></html>";
        web.loadData(text, "text/html", "utf-8");

        quero_cadastrar = (Button)view.findViewById(R.id.bt_quero_cadastrar);
        quero_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    mListener.chamarFragment(1);
                }
            }
        });


        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
