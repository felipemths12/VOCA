# VOCA - Sistema de Gerenciamento de Currículos

VOCA é uma aplicação de desktop desenvolvida em Java com JavaFX, projetada para gerenciar um banco de dados de currículos de candidatos. O sistema permite o cadastro detalhado de candidatos, incluindo informações pessoais, acadêmicas, experiências profissionais, habilidades e idiomas. Além disso, oferece uma funcionalidade de busca e filtragem para encontrar candidatos com base em critérios específicos, como área de atuação e anos de experiência, e permite a exportação dos resultados para os formatos PDF e CSV.

## Funcionalidades Principais

* **Cadastro Completo de Candidatos:**
    * Informações Pessoais (Nome, data de nascimento, contato, etc.).
    * Endereço com busca automática de CEP via API ViaCEP.
    * Formação Acadêmica.
    * Experiência Profissional.
    * Habilidades e Idiomas com níveis de proficiência.

* **Busca Avançada:**
    * Filtre candidatos por área de atuação e tempo de experiência.
    * Visualize os resultados em uma tabela de fácil leitura.

* **Exportação de Dados:**
    * Exporte a lista de candidatos filtrados para um relatório em **PDF**.
    * Exporte todos os candidatos cadastrados para um arquivo **CSV**.

* **Persistência de Dados:**
    * Utiliza o Hibernate como framework ORM para mapear os objetos Java para um banco de dados relacional.
    * A conexão com o banco de dados é configurada para MySQL.

## Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Interface Gráfica:** JavaFX
* **Persistência:** JPA / Hibernate ORM
* **Banco de Dados:** MySQL
* **Geração de PDF:** iTextPDF
* **Requisições HTTP:** `java.net.http.HttpClient` (para a API ViaCEP)
* **Manipulação de JSON:** Google Gson
* **Gerenciador de Dependências:** Apache Maven

## Como Executar o Projeto

1.  **Pré-requisitos:**
    * Java Development Kit (JDK) 17 ou superior.
    * Apache Maven.
    * Um servidor de banco de dados MySQL acessível.

2.  **Configuração do Banco de Dados:**
    * Abra o arquivo `src/main/resources/META-INF/persistence.xml`.
    * Altere as propriedades `jakarta.persistence.jdbc.url`, `jakarta.persistence.jdbc.user`, e `jakarta.persistence.jdbc.password` com as credenciais do seu banco de dados MySQL.

3.  **Execução:**
    * Clone este repositório.
    * Navegue até a pasta raiz do projeto (`VOCA`).
    * Compile e execute o projeto utilizando o Maven:
        ```bash
        mvn clean javafx:run
        ```

## Estrutura do Projeto

O projeto é organizado nos seguintes pacotes principais:

* `br.com.voca.gui`: Contém as classes controladoras da interface gráfica (JavaFX) e a classe principal `MainApp`.
* `br.com.voca.modelos`: Contém as classes de entidade JPA que modelam os dados da aplicação (ex: `Candidato`, `Curriculo`).
* `br.com.voca.dao`: Contém as classes de Acesso a Dados (DAO) responsáveis pela comunicação com o banco de dados, incluindo uma classe `GenericDAO` para operações CRUD.
* `br.com.voca.service`: Contém as classes de serviço que encapsulam a lógica de negócio, como a exportação de arquivos e a consulta a APIs externas.
* `br.com.voca.testes`: Inclui classes para testes unitários e de integração, como o `TesteGeralCRUD` que demonstra o ciclo de vida completo de um candidato no sistema.