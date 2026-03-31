package model;

import java.util.List;

public class GradeSemanal{
	private String semestre;
	private List<HorarioDia> horariosSemanais;
	
	public String getSemestre(){return semestre;}
	public void setSemestre(String semestre){this.semestre=semestre;}
	
	public List<HorarioDia> getHorariosSemanais(){return horariosSemanais;}
	public void setHorariosSemanais(List<HorarioDia> horariosSemanais){this.horariosSemanais=horariosSemanais;}
}
