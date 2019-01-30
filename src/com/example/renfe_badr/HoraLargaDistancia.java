package com.example.renfe_badr;

public class HoraLargaDistancia {

	
	public String linea;
	public String horaSalida;
	public String horaLlegada;
	public String duracion;
	public Double precio;
	
	public HoraLargaDistancia(String horaSalida,String horaLlegada,String duracion,Double precio){
		this.linea="Larga Distancia";
		this.horaSalida=horaSalida;
		this.horaLlegada=horaLlegada;
		this.duracion=duracion;
		this.precio=precio;
		
		
	}
	
	public String getlinea(){
		
		return linea;
	}
	

	public String gethoraSalida() { 
	    return horaSalida; 
	}
	
	public String gethoraLlegada() { 
	    return horaLlegada; 
	}
	public String getduracion(){
		return duracion;
	}
	public Double getprecio(){
		return precio;
	}



}
