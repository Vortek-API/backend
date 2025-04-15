package vortek.sistponto.vortekponto.dto;

public class AssociarColaboradorEmpresaDto {
    private Integer colaboradorId;
    private Integer empresaId;

    // Getters e Setters
    public Integer getColaboradorId() {
        return colaboradorId;
    }

    public void setColaboradorId(Integer colaboradorId) {
        this.colaboradorId = colaboradorId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
}
