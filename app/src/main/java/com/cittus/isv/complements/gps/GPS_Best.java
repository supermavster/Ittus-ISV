package com.cittus.isv.complements.gps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GPS_Best {

    LocationManager locationManager;
    double longitudeBest, latitudeBest, altitudeBest;
    double longitudeGPS, latitudeGPS, altitudeGPS;
    double longitudeNetwork, latitudeNetwork, altitudeNetwork;
    TextView longitudeValueBest, latitudeValueBest, altitudeValueBest;
    TextView longitudeValueGPS, latitudeValueGPS, altitudeValueGPS;
    Activity mainActivity;


    public GPS_Best(Activity mainActivity, TextView longitudeValueBest, TextView altitudeValueBest, TextView latitudeValueBest) {
        this.mainActivity = mainActivity;
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        this.longitudeValueBest = longitudeValueBest;
        this.latitudeValueBest = latitudeValueBest;
        this.altitudeValueBest = altitudeValueBest;
        checkLocation();
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mainActivity);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mainActivity.startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    public void toggleBestUpdates(View view) {

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        Log.e("Provider", provider);
        if (provider != null) {
            if (checkLocation()
            ) {
                locationManager.requestLocationUpdates(provider, 2 * 20 * 1000, 10, locationListenerBest);
                Log.e("Inside", locationManager + "<->" + locationListenerBest);
            }
        }
        locationManager.removeUpdates(locationListenerBest);
    }

    private final LocationListener locationListenerBest = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();
            altitudeBest = location.getAltitude();

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueBest.setText(String.valueOf(longitudeBest));
                    latitudeValueBest.setText(String.valueOf(latitudeBest));
                    altitudeValueBest.setText(String.valueOf(altitudeBest));
                    Toast.makeText(mainActivity, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();
            altitudeNetwork = location.getAltitude();

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueBest.setText(String.valueOf(longitudeNetwork));
                    latitudeValueBest.setText(String.valueOf(latitudeNetwork));
                    altitudeValueBest.setText(String.valueOf(altitudeNetwork));

                    Toast.makeText(mainActivity, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            altitudeGPS = location.getAltitude();

            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueGPS.setText(longitudeGPS + "");
                    latitudeValueGPS.setText(latitudeGPS + "");
                    altitudeValueGPS.setText(altitudeGPS + "");

                    Toast.makeText(mainActivity, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };


}