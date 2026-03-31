package model;

public class AulaDisciplina{
	private Disciplina disciplina;
    private int quantidadeTempos;
    private boolean faltouAula;
    private boolean naoAplicavel;
    
	public Disciplina getDisciplina(){return disciplina;}
	public void setDisciplina(Disciplina disciplina){this.disciplina=disciplina;}
	
	public int getQuantidadeTempos(){return quantidadeTempos;}
	public void setQuantidadeTempos(int quantidadeTempos){this.quantidadeTempos=quantidadeTempos;}
	
	public boolean isFaltouAula(){return faltouAula;}
	public void setFaltouAula(boolean faltouAula){this.faltouAula=faltouAula;}
	
	public boolean isNaoAplicavel(){return naoAplicavel;}
	public void setNaoAplicavel(boolean naoAplicavel){this.naoAplicavel=naoAplicavel;}
}
