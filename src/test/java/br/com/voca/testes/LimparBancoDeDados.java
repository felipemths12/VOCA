package br.com.voca.testes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utilitário para limpar e recriar o banco de dados.
 * ATENÇÃO: A execução desta classe apagará permanentemente todos os dados.
 * A propriedade "hibernate.hbm2ddl.auto" com valor "create-drop"
 * no `persistence.xml` faz com que o Hibernate apague e recrie o esquema do banco.
 */
public class LimparBancoDeDados {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO PROCESSO DE LIMPEZA DO BANCO DE DADOS ---");
        System.out.println("AVISO: Esta operação irá apagar todos os dados existentes.");

        EntityManagerFactory emf = null;
        try {
            // A criação da factory executa a limpeza.
            emf = Persistence.createEntityManagerFactory("voca-mysql");

            System.out.println("Banco de dados limpo e recriado com sucesso!");
            System.out.println("Todas as tabelas foram zeradas.");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao tentar limpar o banco de dados:");
            e.printStackTrace();
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close(); // Libera os recursos.
            }
            System.out.println("--- PROCESSO DE LIMPEZA FINALIZADO ---");
        }
    }
}