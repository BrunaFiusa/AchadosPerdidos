package br.com.pi.achadoseperdidos.dao;

import br.com.pi.achadoseperdidos.controller.GerenciamentoController;
import br.com.pi.achadoseperdidos.model.*;

public class GerenciamentoControllerTest {

    public static void main(String[] args) {
        GerenciamentoController controller = new GerenciamentoController();

        System.out.println("--- Iniciando Testes da Controller ---");

        System.out.println("\n[1] Testando Inserção...");
        Objeto novoObjeto = new Objeto();
        novoObjeto.setNome("Notebook");
        novoObjeto.setMarca("Dell");
        novoObjeto.setLacre(1247);
        novoObjeto.setCondicao("preservado");
        novoObjeto.setObservacao("Teste gerenciamento Controller");

        //Local Fake
        Local localFake = new Local();
        localFake.setId(1);
        novoObjeto.setLocal(localFake);

        //Status Fake
        Status statusFake = new Status();
        statusFake.setId(1);
        novoObjeto.setStatus(statusFake);

        //Funcionario Fake
        Funcionario funcFake = new Funcionario();
        funcFake.setId(1);
        novoObjeto.setFuncionario(funcFake);

        //Categoria Fake
        Categoria categoriaFake = new Categoria();
        categoriaFake.setId(1);
        novoObjeto.setCategoria(categoriaFake);

        novoObjeto.setAtivado(true);

        boolean salvou = controller.inserirObjeto(novoObjeto);
        if (salvou) {
            System.out.println("x----------------------------------------------------------------x");
            System.out.println("Sucesso! Objeto salvo com ID: " + novoObjeto.getId());
            System.out.println("x----------------------------------------------------------------x");
        } else {
            System.out.println("x----------------------------------------------------------------x");
            System.err.println("Falha ao salvar objeto.");
            System.out.println("x----------------------------------------------------------------x");
            System.exit(1);
        }

        if (salvou) {
            System.out.println("\n[2] Testando Atualização...");
            novoObjeto.setNome("Notebook Dell G15 - Atualizado");

            Objeto atualizado = controller.alterarObjeto(novoObjeto);

            if (atualizado != null) {
                System.out.println("x----------------------------------------------------------------x");
                System.out.println("Sucesso! Nome atualizado para: " + atualizado.getNome());
                System.out.println("x----------------------------------------------------------------x");
            } else {
                System.out.println("x----------------------------------------------------------------x");
                System.err.println("Falha na atualização (retornou null).");
                System.out.println("x----------------------------------------------------------------x");
            }
        }

        if (salvou) {
            System.out.println("\n[3] Testando Desativação...");
            boolean desativou = controller.desativarObjeto(novoObjeto.getId());
            if (desativou) {
                System.out.println("x----------------------------------------------------------------x");
                System.out.println("Sucesso! Item desativado com id: " + novoObjeto.getId());
                System.out.println("x----------------------------------------------------------------x");
            } else {
                System.out.println("x----------------------------------------------------------------x");
                System.err.println("Falha ao desativar.");
                System.out.println("x----------------------------------------------------------------x");
            }
        }

        System.out.println("\n--- Testes Finalizados ---");
        System.exit(0);
    }
}