package com.example.renfe_badr;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

import com.example.renfe_badr.Registrar_Android;
import com.example.renfe_badr.R;
import com.example.renfe_badr.StartActivity;
import com.example.renfe_badr.GlobalArea;



import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*Esta es la clase donde logeamos el usuario clickando a Loguear. 
Aparte tambien podemos darle click a registarr donde iniciamos una 
nueva avtividad para insertar el usuario la contraseña y el email*/
public class MainActivity extends Activity {
	
	String[] info;
	private EditText user;
	private EditText password;
	
	
	
	
	public final static String EXTRA_MESSAGE = "com.example.Renfe_badr.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Asignamos los edti's Texts a los elementos del layout
        user = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		info = new String[2];
    }
    //Passamos los parametros para ver si se ha logueado correctamente
    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    String str = "";
	    try {
	    	 		  
  		HttpClient httpclient = new DefaultHttpClient();

  		HttpPost httppost = new HttpPost(urls[0]);
		
		// Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("fullname", urls[1]));
		nameValuePairs.add(new BasicNameValuePair("password", urls[2]));

		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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

	    if(result.equals("ACK")){
	        
	        //Hemos recibido el ack porque se ha logueado correctamente
	        
	       
	    	
			Intent intent = new Intent(getApplicationContext(),StartActivity.class);
			//Asignamos el usuario a la variable global
			Global.username=user.getText().toString();
			 
			startActivity(intent);
 }
	    else
	    {
	    	//No se ha podido loguear correctamente por lo tanto mostramos el error
	    	Toast.makeText(getApplicationContext(), "Error al logearse!",
	    	Toast.LENGTH_LONG).show();
	    }
	    }
	  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    //Abrimos la activiad registrar
    public void abrirRegistrar(View view)
	  {
		 Intent intent = new Intent (this, Registrar_Android.class);
		 startActivity(intent);
	  }
    
    
  //Metodo del onClick del button de Loguear
    public void Login(View view){
    	 info[0] = user.getText().toString();
		 info[1] = password.getText().toString();

    	//Lammamos al Asyntask
    	DownloadWebPageTask task = new DownloadWebPageTask();
    	task.execute(new String[] {"http://10.0.2.2:9000/Android/login_android",info[0],info[1]});
    	}

    	
}

