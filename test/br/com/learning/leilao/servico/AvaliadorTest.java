package br.com.learning.leilao.servico;


import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.learning.leilao.dominio.Lance;
import br.com.learning.leilao.dominio.Leilao;
import br.com.learning.leilao.dominio.Usuario;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;
    
    @BeforeClass
    public static void testandoBeforeClass() {
      System.out.println("before class");
    }

    @AfterClass
    public static void testandoAfterClass() {
      System.out.println("after class");
    }

    @Before
    public void criaAvaliador() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {

		Leilao leilao = new CriadorDeLeilao()
	            .para("Playstation 3 Novo")
	            .lance(joao, 250)
	            .lance(jose, 300)
	            .lance(maria, 400)
	            .constroi();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
	    assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
	    
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
	
		Leilao leilao = new CriadorDeLeilao()
		        .para("Playstation 3 Novo")
		        .lance(joao, 1000)
		        .constroi();

		leiloeiro.avalia(leilao);

        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
    
	}
	
	@Test
	public void deveEncontrarOsTresMaiores() {

		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

		leiloeiro.avalia(leilao);

		List<Lance> maiores= leiloeiro.getTresMaiores();

        assertEquals(3,maiores.size());
        assertThat(maiores,hasItems(
        		new Lance(maria,400),
        		new Lance(joao,300),
        		new Lance(maria,200)
        		));
    
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
	    Leilao leilao = new CriadorDeLeilao()
	        .para("Playstation 3 Novo")
	        .constroi();

	    leiloeiro.avalia(leilao);
	}
	
	 @Test
	    public void deveCalcularAMedia() {

	        Leilao leilao = new CriadorDeLeilao()
	                .para("Playstation 3 Novo")
	                .lance(maria, 300)
	                .lance(joao, 400)
	                .lance(jose, 500)
	                .constroi();

	        leilao.propoe(new Lance(maria,300.0));
	        leilao.propoe(new Lance(joao,400.0));
	        leilao.propoe(new Lance(jose,500.0));

	        leiloeiro.avalia(leilao);

	        assertEquals(400, leiloeiro.getMedia(), 0.0001);
	    }

}
