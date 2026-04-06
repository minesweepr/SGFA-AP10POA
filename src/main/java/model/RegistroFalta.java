package model;

import java.sql.Date;

public class RegistroFalta {
    private int id;
    private int aulaDisciplinaId;
    private Date dataFalta;
    private int quantidadeTemposPerdidos;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAulaDisciplinaId() { return aulaDisciplinaId; }
    public void setAulaDisciplinaId(int aulaDisciplinaId) { this.aulaDisciplinaId = aulaDisciplinaId; }

    public Date getDataFalta() { return dataFalta; }
    public void setDataFalta(Date dataFalta) { this.dataFalta = dataFalta; }

    public int getQuantidadeTemposPerdidos() { return quantidadeTemposPerdidos; }
    public void setQuantidadeTemposPerdidos(int quantidade) { this.quantidadeTemposPerdidos = quantidade; }
}
