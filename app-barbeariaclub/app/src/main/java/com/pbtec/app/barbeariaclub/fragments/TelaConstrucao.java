package com.pbtec.app.barbeariaclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbtec.app.barbeariaclub.R;

public class TelaConstrucao extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_const, container, false);


        TextView telaconst = (TextView)view.findViewById(R.id.telaconst);

        return view;
    }



}
