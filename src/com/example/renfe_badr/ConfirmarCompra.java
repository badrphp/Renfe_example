package com.example.renfe_badr;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

//Elegimos la compra y visualizamos la lista de trenes de larga distancia
public class ConfirmarCompra extends Activity {
	
	private TextView hLlegada;
	private TextView hSalida;
	private TextView duracion;
	
	
	private TextView origen;
	private TextView destino;
	private ListView lista; 
	private TextView precio;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmar_compra);
		
		

	 


	System.out.println("Hemos entrado en comprando1");

	 
	ArrayList<HoraLargaDistancia> datos = new ArrayList<HoraLargaDistancia>(); 
	List<String> HoraSortida = new ArrayList<String>();
	List<String> HoraArribada = new ArrayList<String>();
	List<String> Duracio = new ArrayList<String>();
	List<Double> Precio = new ArrayList<Double>();


	/*Reccorro la lista de juegos que me ha enviado el servidor separando los juegos(/)
	 y la información(-) y lo voy añadiendo a la lista que se mostrará en el layout*/

	String[] listahoras = Comprar.horas.split("/");
	System.out.println(listahoras.length);


		for(int i=0;i<listahoras.length;i++)
		{
			String[] separar=listahoras[i].split("-");
			HoraSortida.add(separar[0]);
			HoraArribada.add(separar[1]);
			Duracio.add(separar[2]);
			Precio.add(Double.parseDouble(separar[3]));
			
		}

		for(int j=0; j<listahoras.length; j++)
		{
			
			
			datos.add(new HoraLargaDistancia(HoraSortida.get(j), HoraArribada.get(j),Duracio.get(j), Precio.get(j)));
			
		}
		
		
		
		//Mostramos los horarios
		
		
		lista = (ListView) findViewById(R.id.ListView_horarios_larga); 
	    lista.setAdapter(new Lista_adaptador(this, R.layout.activity_fila_larga, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
		        if (entrada != null) {
		        		hSalida = (TextView) view.findViewById(R.id.textView_salida1); 
		            if (hSalida != null) 
		            	hSalida.setText("Hora Llegada "+((HoraLargaDistancia) entrada).gethoraSalida()+" h"); 
		              
		            	hLlegada = (TextView) view.findViewById(R.id.textView_llegada1); 
		            if (hLlegada != null)
		            	hLlegada.setText("Hora Salida "+((HoraLargaDistancia) entrada).gethoraLlegada()+" h"); 
		              
		            	duracion = (TextView) view.findViewById(R.id.textView_duracion1); 
		            if (duracion != null)
		            	duracion.setText("Hora Duracion "+((HoraLargaDistancia) entrada).getduracion()+" min");
		            
		             	precio = (TextView) view.findViewById(R.id.textView_precio1); 
		            if (precio != null)
		            	precio.setText("Hora Precio "+((HoraLargaDistancia) entrada).getprecio().toString());
		                Global.precio=((HoraLargaDistancia) entrada).getprecio().toString();
		      }
		 }
			
		     		
	    });

		// Creo el Toast que muestra la descripción cuando pulsas sobre el juego
		lista.setOnItemClickListener(new OnItemClickListener() { 
			@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				HoraLargaDistancia elegido = (HoraLargaDistancia) pariente.getItemAtPosition(posicion); 
				
				Global.hSalida=elegido.horaSalida;
				Global.hLlegada=elegido.horaLlegada;
				Global.precio=elegido.precio.toString();
				
	           
				
				
	            
	            Intent intent= new Intent(getApplicationContext(),CompraRealizada.class);
	    		startActivity(intent);
			}
	    });
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_compra, menu);
		return true;
	}
	
	@Override
    protected void onPause() {
        super.onPause();
       
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
	
	
				//Enviamos parametros de compra al server
		
			
			
		
	
	
	
}
