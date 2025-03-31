package vortek.sistponto.VortekPonto.Dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import vortek.sistponto.VortekPonto.Models.Empresa;

public class EmpresaDto {

    @NotBlank
    @Column(name = "id_emp")
    private Integer id_emp;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome_emp", nullable = false)
    private String nome;

    @NotBlank(message = "O cnpj é obrigatório")
    @Column(name = "cnpj_emp", nullable = false, unique = true)
    private String cnpj;


    public EmpresaDto() {
    }

    public EmpresaDto( String nome, String cnpj) {
        this.nome = nome;
        this.cnpj = cnpj;

    }

    public EmpresaDto(Empresa empresa) {
        this.id_emp = empresa.getId();
        this.nome = empresa.getNome();
        this.cnpj = empresa.getCnpj();
    }

    @NotBlank
    public Integer getId() {
        return id_emp;
    }

    public void setId(@NotBlank Integer id_emp) {
        this.id_emp = id_emp;
    }

    public @NotBlank(message = "O nome é obrigatório") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public @NotBlank(message = "O cnpj é obrigatório") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotBlank(message = "O cnpj é obrigatório") String cnpj) {
        this.cnpj = cnpj;
    }


}
