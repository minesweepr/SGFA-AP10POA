package model;

import java.util.List;

public class HorarioDia{
	private String diaSemana;
    private List<AulaDisciplina> aulas;
    private boolean faltouDiaInteiro;
    
	public String getDiaSemana(){return diaSemana;}
	public void setDiaSemana(String diaSemana){this.diaSemana=diaSemana;}
	
	public List<AulaDisciplina> getAulas(){return aulas;}
	public void setAulas(List<AulaDisciplina> aulas){this.aulas=aulas;}
	
	public boolean isFaltouDiaInteiro(){return faltouDiaInteiro;}
	public void setFaltouDiaInteiro(boolean faltouDiaInteiro){this.faltouDiaInteiro=faltouDiaInteiro;}
}
