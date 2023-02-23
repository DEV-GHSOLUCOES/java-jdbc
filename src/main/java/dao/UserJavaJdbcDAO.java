package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.jdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.UserJavaJdbc;

public class UserJavaJdbcDAO {
	
	private Connection connection;
	
	public UserJavaJdbcDAO() {
		connection  = SingleConnection.getConnection();
	}

	//insert no banco
	public void salvar(UserJavaJdbc userJavaJdbc) {
		try {
			String sql  = "insert into userjdbc ( nome, email) values( ?, ?)";
			PreparedStatement insert  =  connection.prepareStatement(sql);
		
			insert.setString(1, userJavaJdbc.getNome());
			insert.setString(2, userJavaJdbc.getEmail());
			
			insert.execute();
			connection.commit();//salva no banco
			
		} catch (Exception e) {
			try {
				connection.rollback();//reverte a operacao
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
			
		}
	}
	
	// salvar telefone
	public void salvarTelefone(Telefone telefone) {
		try {
			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES ( ?, ?, ?);";
			PreparedStatement insert  = connection.prepareStatement(sql);
			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());
			
			insert.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();

		}

	}

	//recupera a lista
	public List<UserJavaJdbc> listar() throws Exception {
		List<UserJavaJdbc> list =  new ArrayList<UserJavaJdbc>();
		
		String sql = "select * from userjdbc";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet =  statement.executeQuery();
		
		while (resultSet.next()) {
			UserJavaJdbc userJavaJdbc = new UserJavaJdbc();
			
			userJavaJdbc.setId(resultSet.getLong("id"));
			userJavaJdbc.setNome(resultSet.getString("nome"));
			userJavaJdbc.setEmail(resultSet.getString("email"));
			
			list.add(userJavaJdbc);
			
		}
		
		return list;
		
	}
	
	
	
	
	
	//recupera o obj
		public UserJavaJdbc buscar(Long id) throws Exception {
			UserJavaJdbc retorno =  new UserJavaJdbc();
			
			String sql = "select * from userjdbc where id = " + id;
			
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet =  statement.executeQuery();
			
			while (resultSet.next()) {
				
				
				retorno.setId(resultSet.getLong("id"));
				retorno.setNome(resultSet.getString("nome"));
				retorno.setEmail(resultSet.getString("email"));
				
				
				
			}
			
			return retorno;
			
		}
		
		// listando usarios e seus telefone usando inner join
		public List<BeanUserFone> listarUserFone(Long idUser) {
			List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
			String sql = " select nome, numero, email from telefoneuser as fone ";
			sql += " INNER JOIN  userjdbc as userp ";
			sql += " on fone.usuariopessoa  =  userp.id ";
			sql += " where userp.id =  " + idUser;
			
			try {
				PreparedStatement preparedStatement  =  connection.prepareStatement(sql);
				ResultSet resultSet  =  preparedStatement.executeQuery();
				
				while (resultSet.next()) {
					BeanUserFone userFone  =  new BeanUserFone();
					userFone.setNome(resultSet.getString("nome"));
					userFone.setNome(resultSet.getString("numero"));
					userFone.setNome(resultSet.getString("email"));
					beanUserFones.add(userFone);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return beanUserFones;

		}
	
	///atualiza um registro
	public void atualizar(UserJavaJdbc userJavaJdbc) {
		try {
			String sql = "update userjdbc set nome = ? where id  = " + userJavaJdbc.getId();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userJavaJdbc.getNome());
			preparedStatement.execute();
			
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		
		
		
	}
	
	public void deletar(Long id ) {
		try {
			
			String sql =  "delete from userjdbc  where id = " + id;
			PreparedStatement preparedStatement =  connection.prepareStatement(sql);
			
			preparedStatement.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void deleteFonesPorUser(Long idUser) {
		
		String sqlFone =  "delete from telefoneuser where usuariopessoa = " + idUser;
		String sqlUser =  "delete from userjdbc where id = " + idUser;
		try {
			PreparedStatement preparedStatement =  connection.prepareStatement(sqlFone);
			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement =  connection.prepareStatement(sqlUser);
			preparedStatement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
}
