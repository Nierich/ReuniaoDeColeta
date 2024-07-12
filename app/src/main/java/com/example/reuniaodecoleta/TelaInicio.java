package com.example.reuniaodecoleta;

import android.os.Bundle;

public class TelaInicio extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicio);

        setUpToolbar(R.id.myToolbar);

    }
}
