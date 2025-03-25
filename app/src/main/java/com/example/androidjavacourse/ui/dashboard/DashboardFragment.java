package com.example.androidjavacourse.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidjavacourse.R;
import com.example.androidjavacourse.databinding.FragmentDashboardBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements LocationListener{

    public static final String TAG ="LocationFrag";
    private FragmentDashboardBinding binding;
    public String currentLocation;
    public String geoUriString;
    LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        // alla oleva line aiheuttaa NullPointerException
        Button openMap = (Button) getView().findViewById(R.id.openMapBtn);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });*/

        locationMethod();

        // Tämä viimeisenä
        return root;
    }

    @Override
    public void onDestroyView() {
        locationManager.removeUpdates(this);
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d(TAG, location.toString());

        // Fields --- KOSKA FRAGMENT NIIN PITÄÄ LISÄTÄ getView().
        TextInputEditText longitudeField = (TextInputEditText) getView().findViewById(R.id.longitudeField);
        TextInputEditText latitudeField = (TextInputEditText) getView().findViewById(R.id.latitudeField);
        TextInputEditText addressField = (TextInputEditText) getView().findViewById(R.id.addressField);

        try{
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addresses.get(0);
            currentLocation = address.getAddressLine(0);
        } catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        longitudeField.setText(String.valueOf(location.getLongitude()));
        latitudeField.setText(String.valueOf(location.getLatitude()));
        addressField.setText(currentLocation);
        geoUriString = "geo:" + latitudeField.getText() + "," + longitudeField.getText();
    }

    public void locationMethod(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // MUISTA ETTÄ FRAGMENTISSA this MUUTTUU getContext()
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Log.d(TAG,"Location method permission if completed");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000L, (float) 0, (LocationListener) this);
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void openMap(){
        Uri gmmIntentUri = Uri.parse(geoUriString);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        // getActivity() LISÄTÄÄN KOSKA FRAGMENT
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(mapIntent);
        }
    }
}