package br.com.pi.achadoseperdidos.controller;

import br.com.pi.achadoseperdidos.model.Eletronico;
import br.com.pi.achadoseperdidos.model.Objeto;
import br.com.pi.achadoseperdidos.model.Roupa;
import br.com.pi.achadoseperdidos.dao.ObjetosDao;
import br.com.pi.achadoseperdidos.dao.ObjetosEletronicosDao;
import br.com.pi.achadoseperdidos.dao.ObjetosRoupasDao;

public class GerenciamentoController {

    private ObjetosDao objetosDao;
    private ObjetosRoupasDao objetosRoupasDao;
    private ObjetosEletronicosDao objetosEletronicosDao;

    public GerenciamentoController() {
        this.objetosDao = new ObjetosDao();
        this.objetosRoupasDao = new ObjetosRoupasDao();
        this.objetosEletronicosDao = new ObjetosEletronicosDao();
    }

    private boolean isStringValida(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private boolean isIdValido(int id) {
        return id > 0;
    }

    //Pronto ✅
    public int inserirObjetos(String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca) {
        if (!isStringValida(nome) || !isStringValida(marca)) {
            System.out.println("❌ ERRO: Nome e Marca são obrigatórios.");
            return -1;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO: IDs de relacionamento (Local, Status, Funcionário, Categoria) devem ser positivos.");
            return -1;
        }

        Objeto objeto = new Objeto();

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
        if (!isStringValida(nome) || !isStringValida(marca) || !isStringValida(modelo)) {
            System.out.println("❌ ERRO: Nome, Marca e Modelo são obrigatórios.");
            return false;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO: IDs de relacionamento (Local, Status, Funcionário, Categoria) devem ser positivos.");
            return false;
        }

        Eletronico eletronico = new Eletronico();

        eletronico.setNome(nome);
        eletronico.setLacre(lacre);
        eletronico.setCondicao(condicao);
        eletronico.setObservacao(observacao);
        eletronico.setLocal_id(localId);
        eletronico.setStatus_id(statusId);
        eletronico.setFuncionario_id(funcionarioId);
        eletronico.setCategoria_id(categoriaId);
        eletronico.setMarca(marca);
        eletronico.setModelo(modelo);

        return objetosEletronicosDao.inserirEletronicos(eletronico);
    }

    //Pronto ✅
    public boolean inserirRoupas(String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca, String cor, String tamanho) {
        if (!isStringValida(nome) || !isStringValida(marca) || !isStringValida(cor) || !isStringValida(tamanho)) {
            System.out.println("❌ ERRO: Nome, Marca, Cor e Tamanho são obrigatórios.");
            return false;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO: IDs de relacionamento (Local, Status, Funcionário, Categoria) devem ser positivos.");
            return false;
        }

        Roupa roupa = new Roupa();

        roupa.setNome(nome);
        roupa.setLacre(lacre);
        roupa.setCondicao(condicao);
        roupa.setObservacao(observacao);
        roupa.setLocal_id(localId);
        roupa.setStatus_id(statusId);
        roupa.setFuncionario_id(funcionarioId);
        roupa.setCategoria_id(categoriaId);
        roupa.setMarca(marca);
        roupa.setCor(cor);
        roupa.setTamanho(tamanho);

        return objetosRoupasDao.inserirRoupas(roupa);
    }

    //Pronto ✅
    public boolean alterarObjetos(int id, String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId, String marca) {
        if (!isIdValido(id)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: ID do objeto inválido para alteração.");
            return false;
        }
        if (!isStringValida(nome) || !isStringValida(marca)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: Nome e Marca são obrigatórios.");
            return false;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: IDs de relacionamento devem ser positivos.");
            return false;
        }

        Objeto objeto = new Objeto();

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
        if (!isIdValido(id)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: ID do objeto inválido para alteração.");
            return false;
        }
        if (!isStringValida(nome) || !isStringValida(marca) || !isStringValida(modelo)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: Nome, Marca e Modelo são obrigatórios.");
            return false;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: IDs de relacionamento devem ser positivos.");
            return false;
        }

        Eletronico eletronico = new Eletronico();

        eletronico.setId(id);
        eletronico.setNome(nome);
        eletronico.setLacre(lacre);
        eletronico.setCondicao(condicao);
        eletronico.setObservacao(observacao);
        eletronico.setLocal_id(localId);
        eletronico.setStatus_id(statusId);
        eletronico.setFuncionario_id(funcionarioId);
        eletronico.setCategoria_id(categoriaId);
        eletronico.setMarca(marca);
        eletronico.setModelo(modelo);

        return objetosEletronicosDao.alterarEletronicos(eletronico);
    }

    //Pronto ✅
    public boolean alterarRoupas(int id, String nome, int lacre, String condicao, String observacao, int localId, int statusId, int funcionarioId, int categoriaId,String marca, String cor, String tamanho) {
        if (!isIdValido(id)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: ID do objeto inválido para alteração.");
            return false;
        }
        if (!isStringValida(nome) || !isStringValida(marca) || !isStringValida(cor) || !isStringValida(tamanho)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: Nome, Marca, Cor e Tamanho são obrigatórios.");
            return false;
        }
        if (!isIdValido(localId) || !isIdValido(statusId) || !isIdValido(funcionarioId) || !isIdValido(categoriaId)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: IDs de relacionamento devem ser positivos.");
            return false;
        }

        Roupa roupa = new Roupa();

        roupa.setId(id);
        roupa.setNome(nome);
        roupa.setLacre(lacre);
        roupa.setCondicao(condicao);
        roupa.setObservacao(observacao);
        roupa.setLocal_id(localId);
        roupa.setStatus_id(statusId);
        roupa.setFuncionario_id(funcionarioId);
        roupa.setCategoria_id(categoriaId);
        roupa.setMarca(marca);
        roupa.setCor(cor);
        roupa.setTamanho(tamanho);

        return objetosRoupasDao.alterarRoupas(roupa);
    }

    public boolean desativarObjetos(int id) {
        if (!isIdValido(id)) {
            System.out.println("❌ ERRO DE VALIDAÇÃO: ID do objeto inválido para desativação.");
            return false;
        }

        Objeto objeto = new Objeto();

        objeto.setId(id);

        return objetosDao.desativarObjetos(objeto);
    }
}
