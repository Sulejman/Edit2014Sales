package com.comtrade.edit2014sales;

public class Artikal {
	private long id;
	private String naziv;
	private int barkod;
	private String opis;
	private float cijena;
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return id;
	}
	
	public void setCijena(float cijena){
		this.cijena = cijena;
	}
	
	public float getCijena(){
		return cijena;
	}
	
	public void setNaziv(String naziv){
		this.naziv = naziv;
	}
	
	public String getNaziv(){
		return naziv;
	}
	
	public void setOpis(String opis){
		this.opis = opis;
	}
	
	public String getOpis(){
		return opis;
	}
	
	public void setBarkod(int barkod){
		this.barkod = barkod;
	}
	
	public int getBarkod(){
		return barkod;
	}
	
	public Artikal(long id, String naziv, int barkod, String opis, float cijena){
		this.id = id;
		this.naziv = naziv;
		this.barkod = barkod;
		this.opis = opis;
		this.cijena = cijena;
	}
	
	public Artikal(){
		
	}
}
