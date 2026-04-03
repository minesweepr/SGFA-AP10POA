package model;

import java.util.List;
import java.util.ArrayList;

public class GradeSemanal{
	private String semestre;
	private List<HorarioDia> horariosSemanais = new ArrayList<>();
	
	public String getSemestre(){return semestre;}
	public void setSemestre(String semestre){this.semestre=semestre;}
	
	public List<HorarioDia> getHorariosSemanais(){return horariosSemanais;}
	public void setHorariosSemanais(List<HorarioDia> horariosSemanais){this.horariosSemanais=horariosSemanais;}

	public void adicionarHorario(HorarioDia horario){
		if (horario != null) {
			horariosSemanais.add(horario);
		}
	}

	public String gerarPanorama(){
		int totalFaltasNaGrade = 0;
		for(HorarioDia dia : horariosSemanais){
			totalFaltasNaGrade += dia.getTotalFaltas();
		}
		return "Semestre: " + semestre + " | Total de Faltas na Grade: " + totalFaltasNaGrade;
	}
}
