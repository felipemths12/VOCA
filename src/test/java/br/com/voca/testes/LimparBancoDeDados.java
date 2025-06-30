package br.com.voca.testes;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitária para limpar e recriar o banco de dados.
 * <p>
 * ATENÇÃO: A execução desta classe é DESTRUTIVA. Ela irá apagar permanentemente
 * todos os dados contidos nas tabelas gerenciadas pelo sistema VOCA.
 * <p>
 * Isso é possível porque a unidade de persistência "voca-mysql" está configurada
 * com a propriedade "hibernate.hbm2ddl.auto" com o valor "create-drop".
 * A simples inicialização da EntityManagerFactory com essa configuração é suficiente
 * para que o Hibernate apague e recrie todo o esquema do banco de dados.
 */
public class LimparBancoDeDados {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO PROCESSO DE LIMPEZA DO BANCO DE DADOS ---");
        System.out.println("AVISO: Esta operação irá apagar todos os dados existentes.");

        EntityManagerFactory emf = null;
        try {
            // Apenas criar a factory com a configuração 'create-drop' já executa a limpeza.
            emf = Persistence.createEntityManagerFactory("voca-mysql");

            System.out.println("Banco de dados limpo e recriado com sucesso!");
            System.out.println("Todas as tabelas foram zeradas.");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao tentar limpar o banco de dados:");
            e.printStackTrace();
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close(); // Fecha a factory para liberar os recursos.
            }
            System.out.println("--- PROCESSO DE LIMPEZA FINALIZADO ---");
        }
    }
}