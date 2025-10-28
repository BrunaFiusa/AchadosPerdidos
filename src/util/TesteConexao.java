package util;

import controller.GerenciamentoController;
import java.util.Random;

class TesteUpdateObjeto {

    private static GerenciamentoController controller = new GerenciamentoController();

    public static void main(String[] args) {

        System.out.println("--- Iniciando Teste de ALTERAÇÃO de Objeto Genérico ---");

        int id = 1;
        String Nome = "Teste";
        int Lacre = 213411;
        String Condicao = "preservado";
        String Observacao = "Teste";
        int LocalId = 1;
        int statusId = 1;
        int funcionarioId = 1;
        int categoriaId = 1;
        String cor = "Teste";
        String tamanho = "GG";

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
                cor,
                tamanho
        );

        System.out.println("\n----------------------------------------");
        if (sucesso) {
            System.out.println("✅ SUCESSO: Objeto ID alterado com êxito!");;
        } else {
            System.out.println("❌ FALHA: A alteração falhou.");
        }
        System.out.println("--- Teste Concluído ---");
    }
}