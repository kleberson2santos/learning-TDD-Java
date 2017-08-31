package br.com.learning.leilao.servico;

import br.com.learning.leilao.dominio.Lance;
import br.com.learning.leilao.dominio.Leilao;
import br.com.learning.leilao.dominio.Usuario;

public class CriadorDeLeilao {
	
	private Leilao leilao;

    public CriadorDeLeilao() { }

    public CriadorDeLeilao para(String descricao) {
        this.leilao = new Leilao(descricao);
        return this;
    }

    public CriadorDeLeilao lance(Usuario usuario, double valor) {
        leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public Leilao constroi() { 
        return leilao;
    }

}
