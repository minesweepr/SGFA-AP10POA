package model;

public class Aluno{
	private String matricula;
	private String nome;
	private String email;
	private String senha;
	private GradeSemanal grade;
	
	public String getMatricula(){return matricula;}
	public void setMatricula(String matricula){this.matricula=matricula;}
	
	public String getNome(){return nome;}
	public void setNome(String nome){this.nome=nome;}
	
	public String getEmail(){return email;}
	public void setEmail(String email){this.email=email;}

	public String getSenha(){return senha;}
	public void setSenha(String senha){this.senha=senha;}

	public GradeSemanal getGrade(){return grade;}
	public void setGrade(GradeSemanal grade){this.grade=grade;}

	public String visualizarPanorama(){
		if(grade != null) {
			return grade.gerarPanorama();
		}
		return "Nenhuma grade vinculada ao aluno.";
	}
}
