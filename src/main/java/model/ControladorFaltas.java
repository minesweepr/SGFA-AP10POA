package model;

public class ControladorFaltas{
	private int totalFaltas;
	private double percentualMinimo=0.75;
	
	public int getTotalFaltas(){return totalFaltas;}
	public void setTotalFaltas(int totalFaltas){this.totalFaltas=totalFaltas;}
	
	public double getPercentualMinimo(){return percentualMinimo;}
	public void setPercentualMinimo(double percentualMinimo){this.percentualMinimo=percentualMinimo;}
}
