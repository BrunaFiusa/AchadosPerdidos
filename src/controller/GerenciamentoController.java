package controller;

import model.Eletronicos;
import model.Objetos;
import model.Roupas;
import dao.ObjetosDao;
import dao.ObjetosEletronicosDao;
import dao.ObjetosRoupasDao;

import java.sql.Connection;

public class GerenciamentoController {

    private ObjetosDao objetosDao;
    private ObjetosRoupasDao objetosRoupasDao;
    private ObjetosEletronicosDao objetosEletronicosDao;

    public GerenciamentoController() {
        this.objetosDao = new ObjetosDao();
        this.objetosRoupasDao = new ObjetosRoupasDao();
        this.objetosEletronicosDao = new ObjetosEletronicosDao();
    }

    public int inserirObjeto(Objetos objeto) {
        try (Connection connDb = ConnectionFactory.getConnection()) {

            return objetosDao.inserirObjetos(objeto, connDb);

        } catch (Exception e) {
            System.err.println("Erro ao inserir o objeto: " + e.getMessage());
            return -1;
        }
    }

    public boolean inserirEletronico(Eletronicos eletronicos) {
        return objetosEletronicosDao.inserirEletronicos(eletronicos);
    }

    public boolean inserirRoupa(Roupas roupas) {
        return objetosRoupasDao.inserirRoupas(roupas);
    }
}
