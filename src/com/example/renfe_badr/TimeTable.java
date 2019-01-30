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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.renfe_badr.HorasXmlParser.Hora;

//Elegimos origen y destino donde después mostraremos los horarios en la siguiente actividad
public class TimeTable extends Activity {
	
	Spinner listaP_O; 
	Spinner listaP_D;
	public static String horas = ""; 
	String[] listaestaciones;
	public int posDestino;
	public int posOrigen;
	public String linea;
	public final static String EXTRA_MESSAGE = "com.example.Renfe_badr.MESSAGE";
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		ArrayList<Station> datos = new ArrayList<Station>(); 
		/*Reccorro la lista de trenes que me ha enviado el servidor separando los trenes(/)
		 y la información(-) y lo voy añadiendo al spinner que se mostrará en el layout*/
		
		
		listaestaciones = Escoger_linia.estaciones.split("/");
		System.out.println(listaestaciones.length);
		int i;
		
		
		for(i=0;i<listaestaciones.length;i++)
		{
			datos.add(new Station(listaestaciones[i]));
			
		}
		listaP_D = (Spinner) findViewById(R.id.spinner2);
		listaP_O = (Spinner) findViewById(R.id.spinner1);//Enllacem llista1a amb l'Spinner creat
		//Plenem l'spinner mitjançant l'adaptador
		ArrayAdapter<String> adaptador = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item,listaestaciones);
		//enllacem l'adaptador amb l'Spinner
		listaP_O.setAdapter(adaptador);
		listaP_D.setAdapter(adaptador);
		//En seleccionar un item de la llista
		
		
		
		listaP_O.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			//En fer click en l'element
			//Enllacem llista1a amb l'Spinner creat

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
			{
				String Origin_sel=listaestaciones[position];
				
				posOrigen=position;
				//Per recuperar la opció seleccionada haurem de comprobar l'index de la selecció (i)
				Toast msg =Toast.makeText(getApplicationContext(),"Has escogido la parada de origen: "+Origin_sel,Toast.LENGTH_LONG);
				//Toast mostra els missatges d'avis /error pasem els parametres:
				//On es vol mostrar, que es vol mostrar, de manera curta o llarga
				msg.show();//mostrem el missatge;
		
				}
			
			public String duracion(){
				
				int duracio = Math.abs((posOrigen-posDestino)*5);
				
				return (Integer.toString(duracio));
				
			}
			
			
			
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
		});
		//para el destino
		listaP_D.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
			//En fer click en l'element
			//Enllacem llista1a amb l'Spinner creat

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
			{
				String Desti_sel=listaestaciones[position];
				posDestino=position;
				
				System.out.println("Posicion:"+position);
				//Per recuperar la opció seleccionada haurem de comprobar l'index de la selecció (i)
				Toast msg =Toast.makeText(getApplicationContext(),"Has escogido la parada de Desti: "+Desti_sel,Toast.LENGTH_LONG);
				//Toast mostra els missatges d'avis /error pasem els parametres:
				//On es vol mostrar, que es vol mostrar, de manera curta o llarga
				msg.show();//mostrem el missatge;
		
				}
			//
			
			
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
		});
	}
	//Descargamos trenes dado un origen i un destino
		private class DescargarHorarios extends AsyncTask<String, Void, String> {
		    @Override
		    
		    protected String doInBackground(String... urls) {
		    	//System.out.println(urls[0]);
		   
		    String response = "";
		    String str = "";
		    try {
		    	 		  
	  		HttpClient httpclient = new DefaultHttpClient();
	  		System.out.println(urls[0]);
	  		HttpPost httppost = new HttpPost(urls[0]);
	  		
	  	// Añado los parametros de posOrigen posDestino, linea 
	  		
			Intent intent=getIntent();
			linea=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	        
	  		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	  		nameValuePairs.add(new BasicNameValuePair("posOrigen", Integer.toString(posOrigen)));
	  		nameValuePairs.add(new BasicNameValuePair("posDestino", Integer.toString(posDestino)));
	  		nameValuePairs.add(new BasicNameValuePair("linea", linea));
	  		
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
	        System.out.println("Trenes recibidoss  "+ str);
	        if(str.equals("ACK")) {

	         System.out.println("ACK recibido no ha nada....");	
	         System.out.println(str);
	        return str;
	           
	        }
	        else{
	        	System.out.println("LLamo parse");
	        	
	        	HorasXmlParser horasXmlParser = new HorasXmlParser();
	  		System.out.println("Hola parse???");
	  	// Execute HTTP Post Request
	  		
	  		List<Hora> horas = horasXmlParser.parse(serverresponse.getEntity().getContent());
	  		System.out.println("Hola parse estas???");
	  		
	  		
			  for(Hora hora: horas)
			  {
				  System.out.println("bucle horassss???");
				  if(hora.horaLlegada==null){
					  
					  System.out.println("Es nula la hora de llegada");
					  
				  }
				  //Obtenemos la duracion del viage
				  int duracio = Math.abs((posOrigen-posDestino)*5);
				  String l = hora.horaLlegada;
				  String s = hora.horaSalida;
				  String d= Integer.toString(duracio); 
				  
				  System.out.println(l);
				  System.out.println(s);
				  
				  
				 
				  response += s+"-"+l+"-"+d+"/";
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
		    	 System.out.println("On post execute");
		    	horas = result;
		    	if(result.equals("ACK")){
		  	        
		  	        Toast.makeText(getApplicationContext(), "Sin Resultados!",
		  	  	    Toast.LENGTH_LONG).show();
		  	    }
		  	    else
		  	    {
		  	    	/*Si hay resultados los muestro por pantalla*/
		  	    	Intent intent = new Intent(getApplicationContext(),Mostrar_Horario.class); 
			        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			       
			        intent.putExtra(EXTRA_MESSAGE, linea);
			        startActivity(intent);
		  	    }
		    }
		  }
	public void Consulta_horario(View view){
		DescargarHorarios task = new DescargarHorarios();
		task.execute(new String[] { "http://10.0.2.2:9000/Android/devolverTrenes" });
		
	}
}
