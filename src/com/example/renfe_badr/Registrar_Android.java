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

import com.example.renfe_badr.MainActivity;
import com.example.renfe_badr.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
//Esta actividad es donde introducimos el usuario, la contraseña y el email
public class Registrar_Android extends Activity {
	EditText usuario_R, password_R, password_R2,email;
	String[] info;
	public final static String EXTRA_MESSAGE = "com.example.Renfe_badr.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar__android);
		usuario_R = (EditText) findViewById(R.id.usuario_R);
		password_R = (EditText) findViewById(R.id.password_R1);
		password_R2 = (EditText) findViewById(R.id.password_R2);
		email = (EditText) findViewById(R.id.email);
		
		info = new String[5];
	}
	private class Registrar extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    String str = "";
	    try {
	    	 		  
  		HttpClient httpclient = new DefaultHttpClient();

  		HttpPost httppost = new HttpPost(urls[0]);
		
		// Add your data
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", urls[1]));
		nameValuePairs.add(new BasicNameValuePair("password", urls[2]));
		nameValuePairs.add(new BasicNameValuePair("verifypassword", urls[3]));
		nameValuePairs.add(new BasicNameValuePair("email", urls[4]));

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
	    	Intent intent = new Intent(getApplicationContext(),StartActivity.class);
	    	Global.username=usuario_R.getText().toString();
	    	CharSequence texto = "El usuario se ha registrado correctamente correctamente";
	    	Toast toast = Toast.makeText(Registrar_Android.this, texto, Toast.LENGTH_LONG);
            toast.show();
	        startActivity(intent);
	        finish();
	    
	    }
	    else
	    {
	    	Toast.makeText(getApplicationContext(), "Error al registrar!",
	    	Toast.LENGTH_LONG).show();
	    }
	    }
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registrar__android, menu);
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
	
	public void userRegister(View view) {
		System.out.println("Aqui regustrando");
		 Registrar task = new Registrar();
		 info[0] = usuario_R.getText().toString();
		 info[1] = password_R.getText().toString();
		 info[2] = password_R2.getText().toString();
		 info[3] = email.getText().toString();
		

	    task.execute(new String[] {"http://10.0.2.2:9000/Android/saveUser_Android", info[0], info[1], info[2], info[3], info[4]});
	    
	  }

}
