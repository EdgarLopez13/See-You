package com.example.seeyou;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dialogo_MensajeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dialogo_MensajeFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Toma las variables de otro fragmento
    public GoogleMap Mapa = MapsFragment.mMap;
    double LatitudDialogo = MapsFragment.LatitudDialogo;
    double LongitudDialogo = MapsFragment.LongitudDialogo;
    //

    //variables normales
    private NotificationManagerCompat notificationManagerCompat;
    private EditText  mensaje,titulo1;
    private Button Registrar,cancelar;




    public Dialogo_MensajeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dialogo_MensajeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Dialogo_MensajeFragment newInstance(String param1, String param2) {
        Dialogo_MensajeFragment fragment = new Dialogo_MensajeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationManagerCompat = NotificationManagerCompat.from(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialogo__mensaje, container, false);
        Registrar = view.findViewById(R.id.BTNenviarmensaje);
        cancelar = view.findViewById(R.id.BTNenviarcancelar);
        titulo1 = view.findViewById(R.id.ETtituloubicacion);


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cierra el fragmento
                getFragmentManager().beginTransaction().remove(Dialogo_MensajeFragment.this).commit();

            }
        });

        Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Crea la ubicacion con los valores de longitud y latitud que se le proporciona
                LatLng ubicacion1 = new LatLng(LatitudDialogo, LongitudDialogo);

                //Crea todos los valores que lleva el punto
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(ubicacion1);
                markerOptions.title(titulo1.getText().toString());

                //añade el punto
                Mapa.addMarker(markerOptions);

                //cierra el fragmento
                getFragmentManager().beginTransaction().remove(Dialogo_MensajeFragment.this).commit();


                //Codigo de notificaciones por si lo necesitamos

               /* String message = mensaje.getText().toString();
                String titulo = titulo1.getText().toString();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                        .setContentTitle(titulo)
                        .setContentText(message)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setDefaults(Notification.DEFAULT_ALL);
                Intent intent = new Intent(getContext(),NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",message);

                PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                        0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager)getActivity().getSystemService
                        (Context.NOTIFICATION_SERVICE
                        );
                notificationManager.notify(3,builder.build());
                getFragmentManager().beginTransaction().remove(Dialogo_MensajeFragment.this).commit();*/
            }
        });
        return view;

    }
}