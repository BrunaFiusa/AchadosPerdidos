package util;

import controller.GerenciamentoController;

class TesteDesativarObjeto {

    private static GerenciamentoController controller = new GerenciamentoController();

    public static void main(String[] args) {

        System.out.println("---Teste de Conexão: Desativar Objeto ---");

        int idDoObjetoParaDesativar = 3;

        boolean sucesso = controller.desativarObjetos(idDoObjetoParaDesativar);

        if (sucesso) {
            System.out.println("✅ SUCESSO: Objeto ID " + idDoObjetoParaDesativar + " desativado.");
        } else {
            System.out.println("❌ FALHA: Objeto ID " + idDoObjetoParaDesativar + " não foi desativado.");
        }
        System.out.println("--- Teste Concluído ---");
    }
}