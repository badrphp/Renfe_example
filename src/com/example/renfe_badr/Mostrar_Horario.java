package com.example.renfe_badr;

import java.util.ArrayList;
import java.util.List;


import com.example.renfe_badr.Hora;
import com.example.renfe_badr.R;
import com.example.renfe_badr.Lista_adaptador;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
//Mostramos los horarios de los trenes de cercanias
public class Mostrar_Horario extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.Renfe_badr.MESSAGE";
	public String linea;
	private ListView lista;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar__horario);
		Intent intent=getIntent();
		linea=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		ArrayList<Hora> datos = new ArrayList<Hora>(); 
		List<String> HoraSortida = new ArrayList<String>();
		List<String> HoraArribada = new ArrayList<String>();
		List<String> Duració = new ArrayList<String>();
		
		
        
		/*Reccorro la lista de juegos que me ha enviado el servidor separando loas horas(/)
		 y la información(-) y lo voy añadiendo a la lista que se mostrará en el layout*/
		
		String[] listahoras = TimeTable.horas.split("/");
		System.out.println(listahoras.length);
		
		
		for(int i=0;i<listahoras.length;i++)
		{
			String[] separar=listahoras[i].split("-");
			HoraSortida.add(separar[0]);
			HoraArribada.add(separar[1]);
			Duració.add(separar[2]);
		}
		
		for(int j=0; j<listahoras.length; j++)
		{
			datos.add(new Hora(linea, HoraSortida.get(j), HoraArribada.get(j),Duració.get(j)));
			
		}
		
		lista = (ListView) findViewById(R.id.ListView_horarios);
        lista.setAdapter(new Lista_adaptador(this, R.layout.activity_fila, datos){
			@Override
			public void onEntrada(Object entrada, View view) {
		        if (entrada != null) {
		            TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_salida); 
		            if (texto_superior_entrada != null) 
		            	texto_superior_entrada.setText("Hora Salida:    "+((Hora) entrada).gehoraSalida()+" h"); 
		              
		            TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_llegada); 
		            if (texto_inferior_entrada != null)
		            	texto_inferior_entrada.setText("Hora Llegada:    "+((Hora) entrada).gethoraLlegada()+" h"); 
		              
		            TextView precio_entrada = (TextView) view.findViewById(R.id.textView_duracion); 
		            if (precio_entrada != null)
		            	precio_entrada.setText("Duracion:       "+((Hora) entrada).getduracion()+" minutos");
		            
		            TextView texto_medio_entrada = (TextView) view.findViewById(R.id.textView_linea); 
		            if (texto_medio_entrada != null)
		            	texto_medio_entrada.setText("Linea: "+((Hora) entrada).gelinea());
		        			
		        				}
		     			}
		     		});
		     		
		     	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar__horario, menu);
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
}
