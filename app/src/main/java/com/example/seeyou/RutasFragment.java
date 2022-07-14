package com.example.seeyou;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RutasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RutasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Toolbar navegadorperfil2;
    SharedPreferences preferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RutasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RutasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RutasFragment newInstance(String param1, String param2) {
        RutasFragment fragment = new RutasFragment();
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
        View view = inflater.inflate(R.layout.fragment_rutas, container, false);
        navegadorperfil2 = view.findViewById(R.id.navegador4);
        preferences = getActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        if (preferences.getBoolean("fondo2", false) == true){
            navegadorperfil2.setBackgroundResource(R.drawable.fondodegradado2);

        }else if(preferences.getBoolean("fondo", false) == true){
            navegadorperfil2.setBackgroundResource(R.drawable.fondodegradado);
        }else if(preferences.getBoolean("fondo3", false) == true){
            navegadorperfil2.setBackgroundResource(R.drawable.fondodegradado3);
        }
        else if(preferences.getBoolean("fondo4", false) == true){
            navegadorperfil2.setBackgroundResource(R.drawable.fondodegradado4);
        } else{
            navegadorperfil2.setBackgroundResource(R.drawable.fondodegradado);
        }

        return view;
    }
}