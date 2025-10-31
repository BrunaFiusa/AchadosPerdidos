package controller;

import model.Eletronicos;
import model.Objetos;
import model.Roupas;
import dao.ObjetosDao;
import dao.ObjetosEletronicosDao;
import dao.ObjetosRoupasDao;

public class GerenciamentoController {

    private ObjetosDao objetosDao;
    private ObjetosRoupasDao objetosRoupasDao;
    private ObjetosEletronicosDao objetosEletronicosDao;

    public GerenciamentoController() {
        this.objetosDao = new ObjetosDao();
        this.objetosRoupasDao = new ObjetosRoupasDao();
        this.objetosEletronicosDao = new ObjetosEletronicosDao();
    }

    //Pronto ✅
    public int inserirObjetos(String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca) {
        Objetos objeto = new Objetos();

        objeto.setNome(nome);
        objeto.setLacre(lacre);
        objeto.setCondicao(condicao);
        objeto.setObservacao(observacao);
        objeto.setLocal_id(localId);
        objeto.setStatus_id(statusId);
        objeto.setFuncionario_id(funcionarioId);
        objeto.setCategoria_id(categoriaId);
        objeto.setMarca(marca);

        return objetosDao.inserirObjetos(objeto);
    }

    //Pronto ✅
    public boolean inserirEletronicos(String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca, String modelo) {
        Eletronicos eletronicos = new Eletronicos();

        eletronicos.setNome(nome);
        eletronicos.setLacre(lacre);
        eletronicos.setCondicao(condicao);
        eletronicos.setObservacao(observacao);
        eletronicos.setLocal_id(localId);
        eletronicos.setStatus_id(statusId);
        eletronicos.setFuncionario_id(funcionarioId);
        eletronicos.setCategoria_id(categoriaId);
        eletronicos.setMarca(marca);
        eletronicos.setModelo(modelo);

        return objetosEletronicosDao.inserirEletronicos(eletronicos);
    }

    //Pronto ✅
    public boolean inserirRoupas(String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca, String cor, String tamanho) {
        Roupas roupas = new Roupas();

        roupas.setNome(nome);
        roupas.setLacre(lacre);
        roupas.setCondicao(condicao);
        roupas.setObservacao(observacao);
        roupas.setLocal_id(localId);
        roupas.setStatus_id(statusId);
        roupas.setFuncionario_id(funcionarioId);
        roupas.setCategoria_id(categoriaId);
        roupas.setMarca(marca);
        roupas.setCor(cor);
        roupas.setTamanho(tamanho);

        return objetosRoupasDao.inserirRoupas(roupas);
    }

    //Pronto ✅
    public boolean alterarObjetos(int id, String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca) {
        Objetos objeto = new Objetos();

        objeto.setId(id);
        objeto.setNome(nome);
        objeto.setLacre(lacre);
        objeto.setCondicao(condicao);
        objeto.setObservacao(observacao);
        objeto.setLocal_id(localId);
        objeto.setStatus_id(statusId);
        objeto.setFuncionario_id(funcionarioId);
        objeto.setCategoria_id(categoriaId);
        objeto.setMarca(marca);

        return objetosDao.alterarObjetos(objeto);
    }

    //Pronto ✅
    public boolean alterarEletronicos(int id, String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca, String modelo) {
        Eletronicos eletronicos = new Eletronicos();

        eletronicos.setId(id);
        eletronicos.setNome(nome);
        eletronicos.setLacre(lacre);
        eletronicos.setCondicao(condicao);
        eletronicos.setObservacao(observacao);
        eletronicos.setLocal_id(localId);
        eletronicos.setStatus_id(statusId);
        eletronicos.setFuncionario_id(funcionarioId);
        eletronicos.setCategoria_id(categoriaId);
        eletronicos.setMarca(marca);
        eletronicos.setModelo(modelo);

        return objetosEletronicosDao.alterarEletronicos(eletronicos);
    }

    //Pronto ✅
    public boolean alterarRoupas(int id, String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId,String marca, String cor, String tamanho) {
        Roupas roupas = new Roupas();

        roupas.setId(id);
        roupas.setNome(nome);
        roupas.setLacre(lacre);
        roupas.setCondicao(condicao);
        roupas.setObservacao(observacao);
        roupas.setLocal_id(localId);
        roupas.setStatus_id(statusId);
        roupas.setFuncionario_id(funcionarioId);
        roupas.setCategoria_id(categoriaId);
        roupas.setMarca(marca);
        roupas.setCor(cor);
        roupas.setTamanho(tamanho);

        return objetosRoupasDao.alterarRoupas(roupas);
    }

    public boolean deletarObjetos(int id) {
        Objetos objeto = new Objetos();
        objeto.setId(id);
        return objetosDao.deletarObjetos(objeto);
    }
}
