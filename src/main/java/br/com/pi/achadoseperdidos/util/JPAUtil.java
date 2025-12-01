package br.com.pi.achadoseperdidos.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    //Recipiente para a FÁBRICA (A Instância Única)
    private static EntityManagerFactory factory;

    //Garantia que o EntityManager será criado somente UMA vez
    static {

        //Tratemento de Try-Catch para tentar capturar o erro na inicialização da aplicação
        try {
            factory = Persistence.createEntityManagerFactory("ap_iguatemi");
            //aqui passamos o mesmo parametro que passamos no - persistence-unit(nome do banco)
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o EntityManagerFactory" + e.getMessage());
            e.printStackTrace(); //Rastreio do erro detalhado
            throw new RuntimeException("Falha na inicialização do JPA/Hibernate", e); //Lnaça uma exceção para parar o programa e não quebrar o codigo
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null) {
            // Se a fábrica for nula (raro, mas possível), recria
            factory = Persistence.createEntityManagerFactory("ap_iguatemi");
        }
        return factory.createEntityManager(); //Retorna o  EntityManager(trabalhador)
    }

    //Boas praticas de fechamento de conexão com o EntityManagerFactory no fim do programa
    public static void closeEntityManagerFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
            System.out.println("Conexão do EntityManagerFactory fechada com sucesso!");
        }
    }
}
