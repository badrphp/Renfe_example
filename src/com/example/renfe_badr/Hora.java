package com.example.renfe_badr;
//Clase donde definimos las horas que visualizaremos
public class Hora {
	
	public String linea;
	public String horaSalida;
	public String horaLlegada;
	public String duracion;
	
	public Hora(String linea,String horaSalida,String horaLlegada,String duracion){
		this.linea=linea;
		this.horaSalida=horaSalida;
		this.horaLlegada=horaLlegada;
		this.duracion=duracion;
		
		
	}
	
	public String gelinea() { 
	    return linea; 
	}
	public String gehoraSalida() { 
	    return horaSalida; 
	}
	
	public String gethoraLlegada() { 
	    return horaLlegada; 
	}
	public String getduracion(){
		return duracion;
	}

}
