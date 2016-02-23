package com.pmdm.maps;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Ud6_ControlarMapa extends FragmentActivity {

	private GoogleMap mapa = null;
	private int vista = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ud6__controlar_mapa);
		// asigna el mapa para interaccionar con él
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
	}

	/**
	 * carga el menú
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ud6__controlar_mapa, menu);
		return true;
	}

	/**
	 * gestiona la pulsación de un elemento del menú
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// variables locales
		CameraUpdate camUpd;
		CameraPosition camPos;
		// según el elemento pulsado
		switch (item.getItemId()) {
		case R.id.menu_spain:
			// centra España en vista satelite
			camUpd = CameraUpdateFactory.newLatLng(new LatLng(40.41, -3.69));
			mapa.moveCamera(camUpd);
			mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			vista = 0;
			break;
		case R.id.menu_spain_zoom:
			// centra España con zoom x5 en vista normal
			camUpd = CameraUpdateFactory.newLatLngZoom(
					new LatLng(40.41, -3.69), 5f);
			// (el cambio de zoom dura hasta que se cambia x19 en el
			// menú de abajo)
			mapa.animateCamera(camUpd);
			mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			vista = 1;
			break;
		case R.id.menu_alcazaba:
			// centra la Alcazaba de Almería en vista híbrida
			LatLng alcazaba = new LatLng(36.840835, -2.471172);

			// Manhattan New York (en 3d se pone MAP_TYPE_NORMAL)
			// LatLng alcazaba = new LatLng(40.721545, -74.004773);
			camPos = new CameraPosition.Builder().target(alcazaba)
			// con zoom x19
					.zoom(19)
					// orientación noreste (o giro e 45 grados sexagesimales
					// norte -ver la brújula arriba a la izquierda)
					.bearing(45)
					// punto de vista bajo para ver más suelo (80 grados
					// sexagesimales con respecto a la vertical)
					.tilt(80).build();
			// (los cambios de orientación y punto de vista se mantienen
			// hasta que la aplicación se destruye, mientras que el
			// zoom dura hasta que se cambia x5 en el menú de arriba)
			camUpd = CameraUpdateFactory.newCameraPosition(camPos);
			mapa.animateCamera(camUpd);
			mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			vista = 2;
			break;
		case R.id.menu_posicion:
			// muestra las coordenadas de la posición actual de la cámara
			camPos = mapa.getCameraPosition();
			LatLng pos = camPos.target;
			Toast.makeText(this,
					"(Lat: " + pos.latitude + ", Lng: " + pos.longitude + ")",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.menu_vista:
			// alterna la vista del mapa
			alternarVista();
			break;
		case R.id.menu_torre:
			// Torre en 3D
			// centra la Alcazaba de Almería en vista híbrida
			LatLng torre = new LatLng(48.85837, 2.294481);

			// Manhattan New York (en 3d se pone MAP_TYPE_NORMAL)
			// LatLng alcazaba = new LatLng(40.721545, -74.004773);
			camPos = new CameraPosition.Builder().target(torre)
			// con zoom x19
					.zoom(19)
					// orientacion noreste (o giro e 45 grados sexagesimales
					// norte -ver la brújula arriba a la izquierda)
					.bearing(45)
					// punto de vista bajo para ver más suelo (80 grados
					// sexagesimales con respecto a la vertical)
					.tilt(80).build();
			// (los cambios de orientación y punto de vista se mantienen
			// hasta que la aplicación se destruye, mientras que el
			// zoom dura hasta que se cambia x5 en el menú de arriba)
			camUpd = CameraUpdateFactory.newCameraPosition(camPos);
			mapa.animateCamera(camUpd);
			mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			vista = 2;
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * alterna entre las distintas vistas del mapa
	 */
	private void alternarVista() {
		// muestra la vista siguiente a la actual
		vista = (vista + 1) % 4;

		switch (vista) {
		case 0:
			mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case 1:
			mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case 2:
			mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case 3:
			mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		}
	}

}
