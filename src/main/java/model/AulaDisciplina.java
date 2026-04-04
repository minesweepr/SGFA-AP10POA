package model;

public class AulaDisciplina{
	private int id;
	private Disciplina disciplina;
    private int quantidadeTempos;
    private boolean faltouAula;
    private boolean professorAusente;
    private boolean naoAplicavel;
    
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public Disciplina getDisciplina(){return disciplina;}
	public void setDisciplina(Disciplina disciplina){this.disciplina=disciplina;}
	
	public int getQuantidadeTempos(){return quantidadeTempos;}
	public void setQuantidadeTempos(int quantidadeTempos){this.quantidadeTempos=quantidadeTempos;}
	
	public boolean isFaltouAula(){return faltouAula;}
	public void setFaltouAula(boolean faltouAula){this.faltouAula=faltouAula;}
	
	public boolean isProfessorAusente(){return professorAusente;}
	public void setProfessorAusente(boolean professorAusente){this.professorAusente=professorAusente;}
	
	public boolean isNaoAplicavel(){return naoAplicavel;}
	public void setNaoAplicavel(boolean naoAplicavel){this.naoAplicavel=naoAplicavel;}

	public void marcarFalta(){
		this.faltouAula = true;
	}
}
