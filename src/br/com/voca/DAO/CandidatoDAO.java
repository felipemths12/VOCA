package br.com.voca.DAO;

import br.com.voca.database.Conexao;
import br.com.voca.modelos.Candidato;

import java.sql.*;

public class CandidatoDAO {

    public void inserir(Candidato candidato) {
        String sql = "INSERT INTO candidato (nome, data_nascimento, email, telefone, nacionalidade) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, candidato.getNome());
            stmt.setDate(2, Date.valueOf(candidato.getDataNascimento())); // LocalDate → java.sql.Date
            stmt.setString(3, candidato.getEmail());
            stmt.setString(4, candidato.getTelefone());
            stmt.setString(5, candidato.getNacionalidade());

            stmt.executeUpdate();
            System.out.println("Candidato inserido com sucesso!");

            // Recuperar ID gerado (opcional)
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                candidato.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir candidato: " + e.getMessage());
        }
    }

    public Candidato buscarPorId(int id) {
        String sql = "SELECT * FROM candidato WHERE id = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Candidato c = new Candidato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDataNascimento(String.valueOf(rs.getDate("data_nascimento").toLocalDate()));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                c.setNacionalidade(rs.getString("nacionalidade"));
                return c;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Apenas para teste, mas normalmente o método main fica em outra classe
    public static void main(String[] args) {
        CandidatoDAO dao = new CandidatoDAO();
        Candidato c = dao.buscarPorId(1);
        if (c != null) {
            System.out.println("Candidato encontrado: " + c.getNome());
        } else {
            System.out.println("Candidato não encontrado.");
        }
    }
}
