package br.com.resvut42.marvin.servico;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.resvut42.marvin.entidade.Conta;
import br.com.resvut42.marvin.enums.Natureza;
import br.com.resvut42.marvin.enums.AtivaItativa;
import br.com.resvut42.marvin.enums.AnaliticaSintetica;
import br.com.resvut42.marvin.repositorio.RepConta;

/****************************************************************************
 * Classe Serviço Regras de negócio da Conta Contábil Desenvolvido por :
 * 
 * @author Bob-Odin - 31/01/2017
 ****************************************************************************/
@Service
public class SerConta {

	/****************************************************************************
	 * Variaveis e Dependências
	 ****************************************************************************/
	@Autowired
	RepConta repConta;

	private TreeNode raiz;

	/****************************************************************************
	 * Retorna se existe alguma conta cadastrada
	 ****************************************************************************/	
	public boolean exiteConta(){
		if(repConta.count() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/****************************************************************************
	 * Validar e salvar a Conta
	 ****************************************************************************/
	public void salvar(Conta conta) throws Exception {
		try {
			// Se está inserindoo nova conta
			// adiciona a conta, como conta filho
			// processo bidirecional
			if (conta.getIdConta() == null) {
				if (conta.getContaPai() != null) {
					conta.getContaPai().getSubConta().add(conta);
					conta.setChave(conta.getContaPai().getSubConta().size());
				}
			}

			repConta.save(conta);

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar e Excluir a Conta
	 ****************************************************************************/
	public void excluir(Conta conta) throws Exception {
		try {
			repConta.delete(conta);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Validar e Excluir todas as contas
	 ****************************************************************************/
	public void excluirTodas() throws Exception {
		try {
			repConta.deleteAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/****************************************************************************
	 * Retorna a lista em forma de Árvore das Contas
	 ****************************************************************************/
	public TreeNode listarTodos() {

		List<Conta> listaConta = repConta.buscarContaRaiz();

		// se não tem nenhuma conta cadastrada, gerar as 4 contas raizes
		if (listaConta.isEmpty()) {
			criarContaRaiz(listaConta);
			repConta.save(listaConta);
			listaConta = repConta.buscarContaRaiz();
		}

		// Montar TreeNode
		criarTreeNode(listaConta);
		return raiz;
	}

	/****************************************************************************
	 * Retorna uma conta pela Reduzida
	 ****************************************************************************/
	public Conta buscarPorReduzida(String reduzida) {
		return repConta.findByReduzida(reduzida);
	}

	/****************************************************************************/
	//
	//
	// Metodos privados
	//
	//
	/****************************************************************************/

	/****************************************************************************
	 * Cria as contas Raizes caso não tenha
	 ****************************************************************************/
	private void criarContaRaiz(List<Conta> listaConta) {

		Conta conta = new Conta();
		conta.setChave(1);
		conta.setDescricao("ATIVO");
		conta.setTipoConta(AnaliticaSintetica.SINTETICA);
		conta.setStatus(AtivaItativa.ATIVA);
		conta.setNatureza(Natureza.ATIVO);
		conta.setReduzida("1");
		conta.setContaPai(null);
		listaConta.add(conta);

		conta = new Conta();
		conta.setChave(2);
		conta.setDescricao("PASSIVO");
		conta.setTipoConta(AnaliticaSintetica.SINTETICA);
		conta.setStatus(AtivaItativa.ATIVA);
		conta.setNatureza(Natureza.PASSIVO);
		conta.setReduzida("2");
		conta.setContaPai(null);
		listaConta.add(conta);

		conta = new Conta();
		conta.setChave(3);
		conta.setDescricao("DESPESAS");
		conta.setTipoConta(AnaliticaSintetica.SINTETICA);
		conta.setStatus(AtivaItativa.ATIVA);
		conta.setNatureza(Natureza.DESPESA);
		conta.setReduzida("3");
		conta.setContaPai(null);
		listaConta.add(conta);

		conta = new Conta();
		conta.setChave(4);
		conta.setDescricao("RECEITAS");
		conta.setTipoConta(AnaliticaSintetica.SINTETICA);
		conta.setStatus(AtivaItativa.ATIVA);
		conta.setNatureza(Natureza.RECEITA);
		conta.setReduzida("4");
		conta.setContaPai(null);
		listaConta.add(conta);

	}

	/****************************************************************************
	 * Monta TreeNode das contas
	 ****************************************************************************/
	private void criarTreeNode(List<Conta> contaRaiz) {
		this.raiz = new DefaultTreeNode("RAIZ", null);
		adicionarNos(contaRaiz, this.raiz);
	}

	/****************************************************************************
	 * Adiciona os registros filhos no Pai
	 ****************************************************************************/
	private void adicionarNos(List<Conta> contas, TreeNode pai) {

		for (Conta conta : contas) {
			TreeNode no = new DefaultTreeNode(conta, pai);
			adicionarNos(conta.getSubConta(), no);
		}
	}

}
