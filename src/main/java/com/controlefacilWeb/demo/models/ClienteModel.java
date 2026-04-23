package com.controlefacilWeb.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClienteModel extends PessoaModel {

    public ClienteModel(int codigoCliente2, String string, int codigoCliente3, String string2) {
        //TODO Auto-generated constructor stub
    }
    private int codigoCliente;
    private String cpf;
    @Override
    public int getCodigoPessoa() {
        // TODO Auto-generated method stub
        return super.getCodigoPessoa();
    }
    @Override
    public String getNome() {
        // TODO Auto-generated method stub
        return super.getNome();
    }
    @Override
    public void setCodigoPessoa(int codigoPessoa) {
        // TODO Auto-generated method stub
        super.setCodigoPessoa(codigoPessoa);
    }
    @Override
    public void setNome(String nome) {
        // TODO Auto-generated method stub
        super.setNome(nome);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }
}
