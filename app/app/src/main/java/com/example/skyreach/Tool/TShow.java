package com.example.skyreach.Tool;

import android.widget.Toast;

import static com.example.skyreach.YL.getContext;

public class TShow {
    public static void s(Object o){
        Toast.makeText(getContext(),o.toString(),Toast.LENGTH_SHORT).show();
    }
}
