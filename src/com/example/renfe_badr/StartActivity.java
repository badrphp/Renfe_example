package com.example.renfe_badr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.example.renfe_badr.GlobalArea;
import com.example.renfe_badr.MainActivity;
import com.example.renfe_badr.StationXmlParser.Station;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//Esta actividad es donde mostramos los 3 botones de Timetable comprar y mostrar planos
public class StartActivity extends Activity {
	
	private GlobalArea appState;
	private TextView testv;
	 public static String estaciones = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
				//Receive message by Singleton
				setContentView(R.layout.activity_start);	
				testv = (TextView)findViewById(R.id.text_username);
				//Intent intent=getIntent();
				//String user=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
				testv.setText("Usuario: "+Global.username);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Descargamos estaciones donde paran los trenes de larga distancia
		private class DescargarStationsTask extends AsyncTask<String, Void, String> {
		    @Override
		    
		    protected String doInBackground(String... urls) {
		    	//System.out.println(urls[0]);
		   
		    String response = "";
		   
		    try {
		    	 		  
		 		  
		  		HttpClient httpclient = new DefaultHttpClient();
		  		HttpGet httpget = new HttpGet(urls[0]);
		  		
		  		

		  		// Execute HTTP Post Request
		  		HttpResponse serverresponse = httpclient.execute(httpget);
	       
	        	System.out.println("LLamo parse");
	        	
	  		StationXmlParser stationXmlParser = new StationXmlParser();
	  		System.out.println("Hola parse???");
	  	// Execute HTTP Post Request
	  		
	  		
	  		
	  		List<Station> estaciones = stationXmlParser.parse(serverresponse.getEntity().getContent());
	  		System.out.println("Hola parse estas???");
	  		
	  		
			  for(Station estacion: estaciones)
			  {
				  System.out.println("bucle train???");
				  String e = estacion.estacion;
				  System.out.println(e);
				 
				  response += e+"/";
					  System.out.println(response);
				}
	        
			 
		        } catch (Exception e) {
		        	System.out.println("Error");
		          e.printStackTrace();
		        }
		    
		    
		    return response;
		    }

		    @Override
		    protected void onPostExecute(String result) {
		    	/*pasar datos a lista_entrada, si no he introducido nada o la busqueda no tiene resultados
		    	  muestro una ventana: Sin resultados!! */

		    	estaciones = result;
		    	if(result.equals("ACK")){
		  	        
		  	        Toast.makeText(getApplicationContext(), "Sin Resultados!",
		  	  	    Toast.LENGTH_LONG).show();
		  	    }
		  	    else
		  	    {
		  	    	/*Si hay resultados los muestro por pantalla*/
		  	    	
		  	    	Intent intent = new Intent(getApplicationContext(),Comprar.class); 
			        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			        startActivity(intent);
		  	    }
		    }
		    }
	
	public void Timetable(View view){
		Intent intent= new Intent(this,Escoger_linia.class);
		
		startActivity(intent);
		
	}
	public void BuyTickets(View view){
		DescargarStationsTask task= new DescargarStationsTask();
		task.execute(new String[] { "http://10.0.2.2:9000/Android/EstacionesLarga" });
	}
	
	public void VerPlanol(View view){
		Toast msg =Toast.makeText(getApplicationContext(),"Seccion no disponible. Disculpe las Molestias ",Toast.LENGTH_LONG);
		msg.show();
	}
}


