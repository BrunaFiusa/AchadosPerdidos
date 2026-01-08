package br.com.pi.achadoseperdidos.controller;

import br.com.pi.achadoseperdidos.dao.ObjetoDao;
import br.com.pi.achadoseperdidos.dao.ObjetoEletronicoDao;
import br.com.pi.achadoseperdidos.model.Eletronico;
import br.com.pi.achadoseperdidos.model.Objeto;

public class GerenciamentoController {

    private final ObjetoDao objetoDao = new ObjetoDao();
    private final ObjetoEletronicoDao objetoEletronicoDao = new ObjetoEletronicoDao();

    public boolean inserirObjeto(Objeto objeto) {
        try {
            objetoDao.inserirObjeto(objeto);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); //Remover versão final
            return false;
        }
    }

    public Objeto alterarObjeto(Objeto objeto) {
        try {
            return objetoDao.alterarObjeto(objeto);
        } catch (Exception e) {
            e.printStackTrace(); //Remover versão final
            return null;
        }
    }

    public boolean desativarObjeto(Integer id) {
        try {
            objetoDao.desativarObjeto(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); //Remover versão final
            return false;
        }
    }
}