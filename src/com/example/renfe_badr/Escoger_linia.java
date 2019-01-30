package com.example.renfe_badr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.renfe_badr.StationXmlParser.Station;



//Escogemos la linea de cercanias y nos devuelve una lista de etacciones de cercanias con la line seleccionada
public class Escoger_linia extends Activity {
	Spinner lista_linias;
	String[] Linias = { "R1", "R2", "R3", "R4", "R5"};
	public static String estaciones = ""; 
	public final static String EXTRA_MESSAGE = "com.example.Renfe_badr.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_escoger);
		lista_linias=(Spinner) findViewById(R.id.escoge_lin);
		//Plenem l'spinner mitjançant l'adaptador
		ArrayAdapter<String> adaptador = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item,Linias);
		//enllacem l'adaptador amb l'Spinner
		lista_linias.setAdapter(adaptador);
}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.escoger_linia, menu);
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
	
	//Descargamos estaciones dada una linea
	private class DescargarStationsTask extends AsyncTask<String, Void, String> {
	    @Override
	    
	    protected String doInBackground(String... urls) {
	    	//System.out.println(urls[0]);
	   
	    String response = "";
	    String str = "";
	    try {
	    	 		  
  		HttpClient httpclient = new DefaultHttpClient();
  		System.out.println(urls[0]);
  		HttpPost httppost = new HttpPost(urls[0]);
  		
  	// Añado los parametros de busqueda(platforma, genero o titulo)
  		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
  		Spinner spinner = (Spinner) findViewById(R.id.escoge_lin);
  		String linea=spinner.getSelectedItem().toString();
  		nameValuePairs.add(new BasicNameValuePair("linea", linea));
  		System.out.println(linea);
  		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
  		System.out.println("SetEntity");

  		/* Execute HTTP Post Request
  		   Recibo la respuesta y la guardo en serverresponse y responseACK
  		   si no hay resultados en la busqueda guardo la respuesta del servidor (ACK) en responseACK 
  		   y lo envio al onPostexecute para que muestre un Toast
  		   Si la respuesta es una lista la guardo en serverresponse y hago el parser*/
  		
  		HttpResponse serverresponse = httpclient.execute(httppost);
  		System.out.println("enviamos peticion: "+urls[0]);
  		HttpResponse responseACK = httpclient.execute(httppost);
  		BufferedReader reader = new BufferedReader(new InputStreamReader(responseACK.getEntity().getContent()));
        str = reader.readLine();
        System.out.println("Estaciones recibidass  "+ str);
        if(str.equals("ACK")) {

         System.out.println("ACK recibido no ha nada....");	
         System.out.println(str);
        return str;
           
        }
        else{
        	System.out.println("LLamo parse");
        	
  		StationXmlParser stationXmlParser = new StationXmlParser();
  		System.out.println("Hola parse???");
  	// Execute HTTP Post Request
  		//System.out.println(serverresponse.getEntity().getContent());
  		
  		
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
	  	    	Spinner spinner = (Spinner) findViewById(R.id.escoge_lin);
	  	    	String linea=spinner.getSelectedItem().toString();
	  	    	Intent intent = new Intent(getApplicationContext(),TimeTable.class); 
	  	    	intent.putExtra(EXTRA_MESSAGE, linea);
		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		        startActivity(intent);
	  	    }
	    }
	    }
	public void Buscar_linia(View view){
		DescargarStationsTask task = new DescargarStationsTask();
		task.execute(new String[] { "http://10.0.2.2:9000/Android/cargarStation_porLinea_android" });
		
	}
	public void Comprar_T10(View view){
		Intent intent= new Intent(this,T_10.class); 
		
		startActivity(intent);
		
	}
	

}
