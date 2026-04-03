package model;

import java.util.List;
import java.util.ArrayList;

public class HorarioDia{
	private int id;
	private String diaSemana;
    private List<AulaDisciplina> aulas = new ArrayList<>();
    private boolean faltouDiaInteiro;
    
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getDiaSemana(){return diaSemana;}
	public void setDiaSemana(String diaSemana){this.diaSemana=diaSemana;}
	
	public List<AulaDisciplina> getAulas(){return aulas;}
	public void setAulas(List<AulaDisciplina> aulas){this.aulas=aulas;}
	
	public boolean isFaltouDiaInteiro(){return faltouDiaInteiro;}
	public void setFaltouDiaInteiro(boolean faltouDiaInteiro){this.faltouDiaInteiro=faltouDiaInteiro;}

	public void marcarFaltaDia(){
		this.faltouDiaInteiro = true;
		if (aulas != null) {
			for(AulaDisciplina aula : aulas) {
				aula.marcarFalta();
			}
		}
	}

	public int getTotalFaltas(){
		int faltasDoDia = 0;
		if (aulas != null) {
			for(AulaDisciplina aula : aulas) {
				if(aula.isFaltouAula()) {
					faltasDoDia += aula.getQuantidadeTempos();
				}
			}
		}
		return faltasDoDia;
	}
}
