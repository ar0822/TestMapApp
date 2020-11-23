package com.example.testmapapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationList: ArrayList<LatLng> = ArrayList()
    private var polygonList: ArrayList<Polygon> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(35.6724487, 139.769739)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnMapClickListener { latlng ->
            val location = LatLng(latlng.latitude, latlng.longitude)

            if (locationList.size < 4) {
                locationList.add(location)
                mMap.addMarker(MarkerOptions().position(location))
                if(locationList.size > 2) {
                    if(polygonList.size > 0 && locationList.size == 4) polygonList[polygonList.size-1].remove()

                    val polygon: Polygon = mMap.addPolygon(
                            PolygonOptions()
                                    .addAll(locationList)
                                    .strokeColor(Color.RED)
                                    .strokeWidth(3f)
                                    .fillColor(R.color.polygonFill)
                    )
                    polygonList.add(polygon)
                }
                if(locationList.size == 4) locationList.clear()
            }
        }
        locationList.clear()
        polygonList.clear()
    }
}