package model;

public class ControladorFaltas{
	private int totalFaltas;
	private double percentualMinimo=0.75; 
	
	public int getTotalFaltas(){return totalFaltas;}
	public void setTotalFaltas(int totalFaltas){this.totalFaltas=totalFaltas;}
	
	public double getPercentualMinimo(){return percentualMinimo;}
	public void setPercentualMinimo(double percentualMinimo){this.percentualMinimo=percentualMinimo;}

	// calcula qual a porcentagem de faltas relativas à carga da disciplina
	public double getPercentualFaltas(Disciplina disciplina){
		if (disciplina == null || disciplina.getCargaHorariaTotal() == 0) return 0.0;
		return (double) this.totalFaltas / disciplina.getCargaHorariaTotal();
	}

	// calcula quantas faltas ainda pode ter antes de reprovar
	public int getSaldoSeguranca(Disciplina disciplina){
		if (disciplina == null) return 0;
		int limite = disciplina.getLimiteFaltas();
		return limite - this.totalFaltas;
	}

	// verifica se o aluno esta em risco quando o saldo de faltas for baixo
	public boolean estaEmRisco(Disciplina disciplina){
		return getSaldoSeguranca(disciplina) <= 4;
	}
}
