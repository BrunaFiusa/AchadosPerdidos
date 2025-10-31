package util;

import controller.GerenciamentoController;
import java.util.Date;

class TesteUpdateObjeto {

    private static GerenciamentoController controller = new GerenciamentoController();

    public static void main(String[] args) {

        System.out.println("---Teste de Conexão ---");

        int id = 1;
        String Nome = "Teste12";
        int Lacre = 999999;
        String Condicao = "preservado";
        String Observacao = "Teste";
        int LocalId = 2;
        int statusId = 1;
        int funcionarioId = 1;
        int categoriaId = 5;
        String marca = "Teste";
        boolean ativado = false;
        String cor = "Teste";
        String tamanho = "GG";
        String modelo = "Teste";

        boolean sucesso = controller.alterarRoupas(
                id,
                Nome,
                Lacre,
                Condicao,
                Observacao,
                LocalId,
                statusId,
                funcionarioId,
                categoriaId,
                marca,
//                modelo,
                cor,
                tamanho
        );

        if (sucesso) {
            System.out.println("✅ SUCESSO");
        } else {
            System.out.println("❌ FALHA");
        }
        System.out.println("--- Teste Concluído ---");
    }
}