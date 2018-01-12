package com.pbtec.app.barbeariaclub.telafuncionario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.pbtec.app.barbeariaclub.R;

public class MainActivityFuncionario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_funcionario);

        TabHost host = (TabHost)findViewById(R.id.tabhost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("HOJE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("HOJE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("AGENDA");
        spec.setContent(R.id.tab2);
        spec.setIndicator("AGENDA");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("PESQUISAR");
        spec.setContent(R.id.tab3);
        spec.setIndicator("PESQUISAR");
        host.addTab(spec);
    }
}
