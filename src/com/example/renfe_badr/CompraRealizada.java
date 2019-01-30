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
import android.widget.TextView;
import android.widget.Toast;


//Actividad donde confirmamos la compra seleccionada
//En esta parte pasamos el usuario que a echo la compra 


public class CompraRealizada extends Activity {
	
	private TextView usuario;
	private TextView origen;
	private TextView destino;
	private TextView precio;
	private TextView fecha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compra_realizada);
		
		usuario=(TextView)findViewById(R.id.textViewUser);
		usuario.setText(Global.username);
		origen=(TextView)findViewById(R.id.textViewOrigen);
		origen.setText(Global.origen);
		destino=(TextView)findViewById(R.id.textViewDestino);
		destino.setText(Global.destino);
		precio=(TextView)findViewById(R.id.textViewprecio);
		precio.setText(Global.precio);
		fecha=(TextView)findViewById(R.id.textViewfecha);
		fecha.setText(Global.fecha);
		
		
	}
	
	private class EnviarDatosCompra extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    String str = "";
	    try {
	    	  
  		HttpClient httpclient = new DefaultHttpClient();

  		HttpPost httppost = new HttpPost(urls[0]);
  		
  		String u= Global.username;
  		String origen= Global.origen;
  		String destino =Global.destino;
  		String precio= Global.precio;
  		String fecha= Global.fecha;
  		
  		System.out.println("Parametros"+u+origen+destino+precio+fecha);
		
		// Add your data
  		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
  		  
  		 
  		nameValuePairs.add(new BasicNameValuePair("usuario", u));
  		nameValuePairs.add(new BasicNameValuePair("origen", origen));
  		nameValuePairs.add(new BasicNameValuePair("destino", destino));
  		nameValuePairs.add(new BasicNameValuePair("precio", precio));
  		nameValuePairs.add(new BasicNameValuePair("fecha", fecha));
  		
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		System.out.println("Hola task");
  		// Execute HTTP Post Request
  		HttpResponse response = httpclient.execute(httppost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        str = reader.readLine();
		 
	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	      System.out.println(str);
	      return str;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    System.out.println(result);
	    System.out.println("Hola task1");
	    if(result.equals("ACK")){
	    	CharSequence texto = "La compra se ha realizado correctamente";
	    	Toast toast = Toast.makeText(CompraRealizada.this, texto, Toast.LENGTH_LONG);
            toast.show();
			Intent intent = new Intent(getApplicationContext(),StartActivity.class);
			startActivity(intent);
 }
	    else
	    {
	    	Toast.makeText(getApplicationContext(), "Error al logearse!",
	    	Toast.LENGTH_LONG).show();
	    }
	    }
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compra_realizada, menu);
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
	
	
	 
	 public void Compra(View view){
			
			
			EnviarDatosCompra task = new EnviarDatosCompra();
			task.execute(new String[] { "http://10.0.2.2:9000/Android/GuardarTravel" });
			}
}
