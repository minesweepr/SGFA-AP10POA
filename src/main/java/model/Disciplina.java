package model;

public class Disciplina{
	private String codigo;
	private String nome;
	private int cargaHorariaTotal;
	
	public String getCodigo(){return codigo;}
	public void setCodigo(String codigo){this.codigo=codigo;}
	
	public String getNome(){return nome;}
	public void setNome(String nome){this.nome=nome;}

	public int getCargaHorariaTotal(){return cargaHorariaTotal;}
	public void setCargaHorariaTotal(int cargaHorariaTotal){this.cargaHorariaTotal=cargaHorariaTotal;}

	public int getLimiteFaltas(){
		return (int) (this.cargaHorariaTotal * 0.25);
	}
}
