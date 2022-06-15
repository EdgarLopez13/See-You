package com.example.seeyou;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    public static GoogleMap mMap;
    LocationManager locManager;
    private ImageView ubicacion, location,vermarkers;
    private Button cancelar, enviar;
    public static double LatitudDialogo, LongitudDialogo;
    private LinearLayout contenedor;
    int tiempo = 5000;
    int bucleubicacion = 0;

    private com.google.android.gms.location.LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */


    private OnMapReadyCallback callback = new OnMapReadyCallback() {



        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {

            try {


            mMap = googleMap;

            //startLocationUpdates();

            getLastLocation();

            LatLng sydney = new LatLng(31.233544, -110.979941);
            LatLng seguro = new LatLng(31.240626, -110.970661);

            mMap.addMarker(new MarkerOptions().position(sydney).title("Ubicacion de la escuela"));
            mMap.addMarker(new MarkerOptions().position(seguro).title("IMMS"));

            //Habilita el ver la ubicacion actual
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);


            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {


                    //asigna el contenedor y el layout donde se mostrara el cuadro de dialogo
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog
                            (getContext(), R.style.BottomSheetDialog);
                    View bottomSheetView = LayoutInflater.from(getContext()).inflate(
                            R.layout.marker_layout, (LinearLayout) contenedor
                    );
                    bottomSheetDialog.setContentView(bottomSheetView);

                    bottomSheetDialog.show();


                    //Asigna los valores a los objetos dentro el bottomsheetdialog
                    TextView nombre = bottomSheetDialog.findViewById(R.id.TVnombreubicacion);
                    TextView ubicacion = bottomSheetDialog.findViewById(R.id.TVubicacion);
                    TextView coordenada = bottomSheetDialog.findViewById(R.id.TVmasinformacion);
                    TextView descripcion = bottomSheetDialog.findViewById(R.id.TVdescripcion);
                    LatLng latLng = marker.getPosition();
                    double Latitud,logitud;
                    Latitud = latLng.latitude;
                    logitud = latLng.longitude;

                    coordenada.setText("" + Latitud + " : " + logitud);
                    nombre.setText(marker.getTitle());
                    ubicacion.setText("Aun Nose Como le pondre esta info xd");
                    descripcion.setText("AQUI PONDRE UNA DESCRIPCION CUANDO SE CONECTE A LA BD :D");

                    bottomSheetDialog.findViewById(R.id.BTNviajar).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "ESTE BOTON HARA ALGO LUEGO :D",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    bottomSheetDialog.findViewById(R.id.BTNeliminar).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "ESTE BOTON BORRARA EL MARCADOR ALGUN DIA :D",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                    return true;
                }
            });

            }catch (Exception e){
                Toast.makeText(getContext(), "NECESITAS ACTIVAR LA UBICACION Y " +
                        "DAR PERMISOS A LA APP", Toast.LENGTH_SHORT).show();
            }
        }




    };


    //PROBANDO CODIGO DE UBICACION VIA GOOGLE





    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(getContext());

        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            LatLng UbicacionActualo = new LatLng(location.getLatitude(), location.getLongitude());

                            //Mueve la camara al punto proporcionado, osea la ubicacion del usuario
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UbicacionActualo, 14));


                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "NO SE A PODIDO OBTENER TU ULTIMA UBICACION",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }



    //HASTA AQUI TERMINA EL CODIGO DE UBICACION VIA GOOGLE




    public void obtenerubicacion(double lat,double lon){

        locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);



        LatLng UbicacionActualo = new LatLng(lat, lon);

        //Mueve la camara al punto proporcionado, osea la ubicacion del usuario
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UbicacionActualo, 14));

        //Optiene la ultima localizacion conocida del usuario
       /* @SuppressLint("MissingPermission")
        Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc != null) {
            //Guarda los valores de longitud y latitud en variables
             lat = loc.getLatitude();
             lon = loc.getLongitude();

            //Crea el punto donde se marcara
            LatLng UbicacionActualo = new LatLng(lat, lon);

            //Mueve la camara al punto proporcionado, osea la ubicacion del usuario
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UbicacionActualo, 14));
        }else{
            Toast.makeText(getContext(), "NO SE A PODIDO OBTENER LA UBICACION", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void Marcador() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {


                //Guarda los valores de longitud y latitud en variables
                double latitud = latLng.latitude;
                double longitud = latLng.longitude;


                //Le da a las variables globales los valores que necesitan
                LatitudDialogo = latitud;
                LongitudDialogo = longitud;


                //Guarda Todas las opciones necesarias para hacer el marcador
                MarkerOptions markerOptions = new MarkerOptions();

                //Crea el punto donde se marcara
                markerOptions.position(latLng);

                //le da nombre al punto
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);


                //Mueve la camara al punto proporcionado, osea la ubicacion del usuario
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));


                //llama al fragmento dialogo
                Dialogo_MensajeFragment dialogofragment = new Dialogo_MensajeFragment();
                dialogofragment.show(getFragmentManager(), "MyFragment");


            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        ubicacion = view.findViewById(R.id.IVubicacion);
        cancelar = view.findViewById(R.id.BTNcancelar);
        enviar = view.findViewById(R.id.BTNenviarmensaje);
        location = view.findViewById(R.id.IVlocation);
        contenedor = view.findViewById(R.id.Contenedormarker);
        vermarkers = view.findViewById(R.id.IVvermarkers);


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();

            }
        });


        vermarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog
                        (getContext(), R.style.BottomSheetDialog);
                View bottomSheetView = LayoutInflater.from(getContext()).inflate(
                        R.layout.markersbottomshet, (LinearLayout) view.findViewById(R.id.ContenedorTarjeta)
                );
                bottomSheetDialog.setContentView(bottomSheetView);

                bottomSheetDialog.show();

                RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.RVmarkersbottomsheet);


                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                List<Markers> markersList = new ArrayList<>();

                for (int i = 0; i <20; i++)
                {
                    markersList.add(new Markers(i,"CASITA NO. " + i,"San Carlos #" + i,
                            "1244232122, -12421322"
                            ,"ES LA COLONIA DONDE E VIVIDO TODA MI VIDA Y AQUI EN NOGALES UWU","MARCADOR NO. " + i));
                }

                recyclerView.setAdapter(new MakersAdapters(markersList,getContext()));

            }
        });


        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //desactiva la funcion de marcar puntos al hacer click
                mMap.setOnMapClickListener(null);

                //vuelve invisible el boton de cancelar
                cancelar.setVisibility(View.INVISIBLE);
                vermarkers.setVisibility(View.INVISIBLE);

            }
        });

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //vuelve visible el boton de cancelar
                cancelar.setVisibility(View.VISIBLE);
                vermarkers.setVisibility(View.VISIBLE);

                    /*hacia una prueba donde bajaba el brillo a la pantalla cuando hacia clic a esta imagen,
                     funciona pero no tiene utilidad de momento
                     */

                    /*WindowManager.LayoutParams brillo = getActivity().getWindow().getAttributes();
                    brillo.screenBrightness= 0.05F;
                    getActivity().getWindow().setAttributes(brillo);
                    */

                Marcador();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }
}


