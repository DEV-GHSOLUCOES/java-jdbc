package conexao.jdbc;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import dao.UserJavaJdbcDAO;
import model.BeanUserFone;
import model.Telefone;
import model.UserJavaJdbc;

public class TesteBancoJdbc {
	
	@Test
	public void initBanco() {
		UserJavaJdbcDAO javaJdbcDAO = new UserJavaJdbcDAO();
		UserJavaJdbc userJavaJdbc = new UserJavaJdbc();

		userJavaJdbc.setNome("isabella alencastro");
		userJavaJdbc.setEmail("isabella@gmail.com");
		
		javaJdbcDAO.salvar(userJavaJdbc);
		
		
	}
	
	@Test
	public void initListar() {
		UserJavaJdbcDAO dao =  new UserJavaJdbcDAO();
		try {
		  List<UserJavaJdbc> list = dao.listar();
		  
		  for (UserJavaJdbc userJavaJdbc : list) {
			  
			  System.out.println(userJavaJdbc);
			  System.out.println("--------------------------");
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initBuscar() {
		UserJavaJdbcDAO dao =  new UserJavaJdbcDAO();
		
		try {
			UserJavaJdbc userJavaJdbc  = dao.buscar(2L);
			System.out.println(userJavaJdbc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	  @Test 
	  public void initatualizar() {
	  
	  try {
		UserJavaJdbcDAO dao = new UserJavaJdbcDAO(); 
		UserJavaJdbc userJavaJdbc =  dao.buscar(2L);
		userJavaJdbc.setNome("Isabella Alencastro");
		dao.atualizar(userJavaJdbc);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	  
	  }
	  
	  @Test
	  public void initDeletar() {
		  try {
			  UserJavaJdbcDAO dao  = new UserJavaJdbcDAO();
			  dao.deletar(4L);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		  
	  }
	  
	  @Test
	  public void TesteinsertTelefone() {
		  Telefone telefone =  new Telefone();
		  telefone.setNumero("(81) 98594-7573");
		  telefone.setTipo("Celular");
		  telefone.setUsuario(2L);
		  
		  UserJavaJdbcDAO dao =  new UserJavaJdbcDAO();
		  dao.salvarTelefone(telefone);
		  
	  }
	 
	 @Test
	 public void testeListarUserFones() {
		 UserJavaJdbcDAO dao = new UserJavaJdbcDAO();
		 try {
			 List<BeanUserFone> beanUserFones =  dao.listarUserFone(2L);
			for (BeanUserFone beanUserFone : beanUserFones) {
				System.out.println(beanUserFone);
				System.out.println("--------------------------------");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}		 
	 }
	 
	 @Test
	 public void testeDeletarUserFones() {
		 UserJavaJdbcDAO dao = new UserJavaJdbcDAO();
		 dao.deleteFonesPorUser(2L);
	 }
	 
}
