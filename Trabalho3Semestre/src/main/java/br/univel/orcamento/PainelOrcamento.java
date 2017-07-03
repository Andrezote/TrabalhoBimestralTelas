package br.univel.orcamento;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.lowagie.text.Table;

import br.univel.cliente.Cliente;
import br.univel.cliente.ClienteDao;
import br.univel.cliente.ClienteModel;
import br.univel.produto.Produto;
import br.univel.produto.ProdutoDao;
import br.univel.produto.ProdutoModel;
import br.univel.produto.TelaPrincipal;

import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class PainelOrcamento extends JPanel {
	protected JTable tbItem;
	protected JTable tbCliente;
	protected JTextField txfItemBusca;
	protected JTextField txfClienteBusca;
	protected JTextField txfIdOrc;
	protected JTextField txfDescOrc;
	protected JTextField txfClienteOrc;
	protected JTextField txfID_clienteOrc;
	protected JTextField txfCpfOrc;
	protected JTable tbOrc;
	protected ClienteModel modeloCliente;
	protected JScrollPane scrPcliente;
	protected JScrollPane scrPitem;
	protected JScrollPane scrPorc;
	protected Produto produtoSelecionado;
	protected Cliente clienteSelecionado;
	protected ProdutoModel pmodelo;
	protected ClienteModel cmodelo;
	protected ProdutoDao pdao = new ProdutoDao();
	protected OrcamentoModel oModel;
	List<Produto> listaProdutos = new ArrayList<>();
	private JTextField txfQtd;
	private JTable tblOrcamentoProd;
	protected Orcamento orcamentoSelecionado;
	protected ProdOrcamentoModel poM;
	private OrcamentoDao oDao = new OrcamentoDao();
	private JScrollPane scrOrcamentoProd;

	public PainelOrcamento() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblBuscaritemf = new JLabel("BuscarItem(f2)");
		GridBagConstraints gbc_lblBuscaritemf = new GridBagConstraints();
		gbc_lblBuscaritemf.anchor = GridBagConstraints.WEST;
		gbc_lblBuscaritemf.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscaritemf.gridx = 0;
		gbc_lblBuscaritemf.gridy = 0;
		panel.add(lblBuscaritemf, gbc_lblBuscaritemf);
		
		txfItemBusca = new JTextField();
		GridBagConstraints gbc_txfItemBusca = new GridBagConstraints();
		gbc_txfItemBusca.insets = new Insets(0, 0, 5, 0);
		gbc_txfItemBusca.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfItemBusca.gridx = 1;
		gbc_txfItemBusca.gridy = 0;
		panel.add(txfItemBusca, gbc_txfItemBusca);
		txfItemBusca.setColumns(10);
		
		JButton btnBuscaritem = new JButton("BuscarItem");
		btnBuscaritem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarItens(txfItemBusca.getText().trim());
			}
		});
		
		JLabel lblQuantidade = new JLabel("Quantidade");
		GridBagConstraints gbc_lblQuantidade = new GridBagConstraints();
		gbc_lblQuantidade.anchor = GridBagConstraints.EAST;
		gbc_lblQuantidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantidade.gridx = 0;
		gbc_lblQuantidade.gridy = 1;
		panel.add(lblQuantidade, gbc_lblQuantidade);
		
		txfQtd = new JTextField();
		txfQtd.setMinimumSize(new Dimension(50, 20));
		GridBagConstraints gbc_txfQtd = new GridBagConstraints();
		gbc_txfQtd.anchor = GridBagConstraints.WEST;
		gbc_txfQtd.insets = new Insets(0, 0, 5, 0);
		gbc_txfQtd.gridx = 1;
		gbc_txfQtd.gridy = 1;
		panel.add(txfQtd, gbc_txfQtd);
		txfQtd.setColumns(10);
		GridBagConstraints gbc_btnBuscaritem = new GridBagConstraints();
		gbc_btnBuscaritem.anchor = GridBagConstraints.EAST;
		gbc_btnBuscaritem.insets = new Insets(0, 0, 5, 0);
		gbc_btnBuscaritem.gridx = 1;
		gbc_btnBuscaritem.gridy = 2;
		panel.add(btnBuscaritem, gbc_btnBuscaritem);
		
		scrPitem = new JScrollPane();
		GridBagConstraints gbc_scrPitem = new GridBagConstraints();
		gbc_scrPitem.gridheight = 2;
		gbc_scrPitem.gridwidth = 2;
		gbc_scrPitem.fill = GridBagConstraints.BOTH;
		gbc_scrPitem.gridx = 0;
		gbc_scrPitem.gridy = 3;
		panel.add(scrPitem, gbc_scrPitem);
		
		tbItem = new JTable();
		scrPitem.setViewportView(tbItem);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.gridheight = 4;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 0;
		add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblId = new JLabel("ID");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panel_2.add(lblId, gbc_lblId);
		
		txfIdOrc = new JTextField();
		txfIdOrc.setMinimumSize(new Dimension(50, 20));
		GridBagConstraints gbc_txfIdOrc = new GridBagConstraints();
		gbc_txfIdOrc.anchor = GridBagConstraints.WEST;
		gbc_txfIdOrc.insets = new Insets(0, 0, 5, 0);
		gbc_txfIdOrc.gridx = 1;
		gbc_txfIdOrc.gridy = 0;
		panel_2.add(txfIdOrc, gbc_txfIdOrc);
		txfIdOrc.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descricao");
		GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
		gbc_lblDescricao.anchor = GridBagConstraints.WEST;
		gbc_lblDescricao.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescricao.gridx = 0;
		gbc_lblDescricao.gridy = 1;
		panel_2.add(lblDescricao, gbc_lblDescricao);
		
		txfDescOrc = new JTextField();
		GridBagConstraints gbc_txfDescOrc = new GridBagConstraints();
		gbc_txfDescOrc.insets = new Insets(0, 0, 5, 0);
		gbc_txfDescOrc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfDescOrc.gridx = 1;
		gbc_txfDescOrc.gridy = 1;
		panel_2.add(txfDescOrc, gbc_txfDescOrc);
		txfDescOrc.setColumns(10);
		
		JLabel lblCliente = new JLabel("Cliente");
		GridBagConstraints gbc_lblCliente = new GridBagConstraints();
		gbc_lblCliente.anchor = GridBagConstraints.WEST;
		gbc_lblCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblCliente.gridx = 0;
		gbc_lblCliente.gridy = 2;
		panel_2.add(lblCliente, gbc_lblCliente);
		
		txfClienteOrc = new JTextField();
		GridBagConstraints gbc_txfClienteOrc = new GridBagConstraints();
		gbc_txfClienteOrc.insets = new Insets(0, 0, 5, 0);
		gbc_txfClienteOrc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfClienteOrc.gridx = 1;
		gbc_txfClienteOrc.gridy = 2;
		panel_2.add(txfClienteOrc, gbc_txfClienteOrc);
		txfClienteOrc.setColumns(10);
		
		JLabel lblIdcliente = new JLabel("id_cliente");
		GridBagConstraints gbc_lblIdcliente = new GridBagConstraints();
		gbc_lblIdcliente.anchor = GridBagConstraints.WEST;
		gbc_lblIdcliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdcliente.gridx = 0;
		gbc_lblIdcliente.gridy = 3;
		panel_2.add(lblIdcliente, gbc_lblIdcliente);
		
		txfID_clienteOrc = new JTextField();
		txfID_clienteOrc.setMinimumSize(new Dimension(50, 20));
		GridBagConstraints gbc_txfID_clienteOrc = new GridBagConstraints();
		gbc_txfID_clienteOrc.insets = new Insets(0, 0, 5, 0);
		gbc_txfID_clienteOrc.anchor = GridBagConstraints.WEST;
		gbc_txfID_clienteOrc.gridx = 1;
		gbc_txfID_clienteOrc.gridy = 3;
		panel_2.add(txfID_clienteOrc, gbc_txfID_clienteOrc);
		txfID_clienteOrc.setColumns(10);
		
		JLabel lblCpf = new JLabel("cpf");
		GridBagConstraints gbc_lblCpf = new GridBagConstraints();
		gbc_lblCpf.anchor = GridBagConstraints.WEST;
		gbc_lblCpf.insets = new Insets(0, 0, 5, 5);
		gbc_lblCpf.gridx = 0;
		gbc_lblCpf.gridy = 4;
		panel_2.add(lblCpf, gbc_lblCpf);
		
		txfCpfOrc = new JTextField();
		GridBagConstraints gbc_txfCpfOrc = new GridBagConstraints();
		gbc_txfCpfOrc.insets = new Insets(0, 0, 5, 0);
		gbc_txfCpfOrc.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfCpfOrc.gridx = 1;
		gbc_txfCpfOrc.gridy = 4;
		panel_2.add(txfCpfOrc, gbc_txfCpfOrc);
		txfCpfOrc.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 6;
		panel_2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarOrcamento();
				limparCampos();
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.anchor = GridBagConstraints.EAST;
		gbc_btnSalvar.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvar.gridx = 0;
		gbc_btnSalvar.gridy = 0;
		panel_3.add(btnSalvar, gbc_btnSalvar);
		
		JButton btnRemoverproduto = new JButton("RemoverProduto");
		btnRemoverproduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listaProdutos.remove(produtoSelecionado);
				configuraTabelaOrc();
			}
		});
		
		JButton btnRemoverorcamento = new JButton("RemoverOrcamento");
		btnRemoverorcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(orcamentoSelecionado != null){
					oDao.excluiProdOrc(orcamentoSelecionado);
					oDao.exclui(orcamentoSelecionado);
					configuraTabelaOrcamentos();
				}else{
					JOptionPane.showMessageDialog(null, "Clique duas vezes para selecionar um orcamento");
				}
				
			}
		});
		GridBagConstraints gbc_btnRemoverorcamento = new GridBagConstraints();
		gbc_btnRemoverorcamento.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverorcamento.gridx = 1;
		gbc_btnRemoverorcamento.gridy = 0;
		panel_3.add(btnRemoverorcamento, gbc_btnRemoverorcamento);
		GridBagConstraints gbc_btnRemoverproduto = new GridBagConstraints();
		gbc_btnRemoverproduto.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverproduto.gridx = 2;
		gbc_btnRemoverproduto.gridy = 0;
		panel_3.add(btnRemoverproduto, gbc_btnRemoverproduto);
		
		JButton btnExportarpdf = new JButton("ExportarPDF");
		btnExportarpdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(orcamentoSelecionado == null){
					JOptionPane.showMessageDialog(null, "Selecione um orcamento");
				}else{
					ReportManager rm = new ReportManager();
					rm.exportarCustom(orcamentoSelecionado);
				}
			}
		});
		GridBagConstraints gbc_btnExportarpdf = new GridBagConstraints();
		gbc_btnExportarpdf.anchor = GridBagConstraints.EAST;
		gbc_btnExportarpdf.gridx = 3;
		gbc_btnExportarpdf.gridy = 0;
		panel_3.add(btnExportarpdf, gbc_btnExportarpdf);
		
		scrOrcamentoProd = new JScrollPane();
		GridBagConstraints gbc_scrOrcamentoProd = new GridBagConstraints();
		gbc_scrOrcamentoProd.gridwidth = 2;
		gbc_scrOrcamentoProd.insets = new Insets(0, 0, 5, 0);
		gbc_scrOrcamentoProd.fill = GridBagConstraints.BOTH;
		gbc_scrOrcamentoProd.gridx = 0;
		gbc_scrOrcamentoProd.gridy = 7;
		panel_2.add(scrOrcamentoProd, gbc_scrOrcamentoProd);
		
		tblOrcamentoProd = new JTable();
		scrOrcamentoProd.setViewportView(tblOrcamentoProd);
		
		scrPorc = new JScrollPane();
		GridBagConstraints gbc_scrPorc = new GridBagConstraints();
		gbc_scrPorc.gridwidth = 2;
		gbc_scrPorc.fill = GridBagConstraints.BOTH;
		gbc_scrPorc.gridx = 0;
		gbc_scrPorc.gridy = 8;
		panel_2.add(scrPorc, gbc_scrPorc);
		
		tbOrc = new JTable();
		scrPorc.setViewportView(tbOrc);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblBuscarclientef = new JLabel("BuscarCliente(f2)");
		GridBagConstraints gbc_lblBuscarclientef = new GridBagConstraints();
		gbc_lblBuscarclientef.anchor = GridBagConstraints.WEST;
		gbc_lblBuscarclientef.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscarclientef.gridx = 0;
		gbc_lblBuscarclientef.gridy = 0;
		panel_1.add(lblBuscarclientef, gbc_lblBuscarclientef);
		
		txfClienteBusca = new JTextField();
		GridBagConstraints gbc_txfClienteBusca = new GridBagConstraints();
		gbc_txfClienteBusca.insets = new Insets(0, 0, 5, 0);
		gbc_txfClienteBusca.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfClienteBusca.gridx = 1;
		gbc_txfClienteBusca.gridy = 0;
		panel_1.add(txfClienteBusca, gbc_txfClienteBusca);
		txfClienteBusca.setColumns(10);
		
		JButton btnBuscarcliente = new JButton("BuscarCliente");
		btnBuscarcliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarClientes();
			}
		});
		GridBagConstraints gbc_btnBuscarcliente = new GridBagConstraints();
		gbc_btnBuscarcliente.anchor = GridBagConstraints.EAST;
		gbc_btnBuscarcliente.insets = new Insets(0, 0, 5, 0);
		gbc_btnBuscarcliente.gridx = 1;
		gbc_btnBuscarcliente.gridy = 1;
		panel_1.add(btnBuscarcliente, gbc_btnBuscarcliente);
		
		scrPcliente = new JScrollPane();
		GridBagConstraints gbc_scrPcliente = new GridBagConstraints();
		gbc_scrPcliente.gridheight = 2;
		gbc_scrPcliente.gridwidth = 2;
		gbc_scrPcliente.fill = GridBagConstraints.BOTH;
		gbc_scrPcliente.gridx = 0;
		gbc_scrPcliente.gridy = 2;
		panel_1.add(scrPcliente, gbc_scrPcliente);
		
		tbCliente = new JTable();
		scrPcliente.setViewportView(tbCliente);
		configuraTabelaCliente();
		configuraTabelaProduto();
		configuraTabelaOrc();
		configuraTabelaOrcamentos();
	}
	
	protected void limparCampos() {
		this.txfClienteBusca.setText("");
		this.txfClienteOrc.setText("");
		this.txfCpfOrc.setText("");
		this.txfID_clienteOrc.setText("");
		this.txfIdOrc.setText("");
		this.txfDescOrc.setText("");
		
		this.orcamentoSelecionado = null;
		this.clienteSelecionado = null;
		this.produtoSelecionado = null;
		
		this.listaProdutos = new ArrayList<>();
		configuraTabelaProduto();
	}

	protected void salvarOrcamento() {
		Orcamento o = new Orcamento();
		if(orcamentoSelecionado == null){
			insereBanco(o);
		}else{
			JOptionPane.showMessageDialog(null, "Crie outro orcamento");
		}
	}
	

//	private Orcamento atualizaBanco(Orcamento o) {
//		if(txfIdOrc.getText().isEmpty() || txfDescOrc.getText().isEmpty() || clienteSelecionado == null || listaProdutos.size() == 0){
//			JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
//		}else{
//			o.setId(Long.parseLong(txfIdOrc.getText()));
//			o.setCliente(clienteSelecionado);
//			o.setDescricao(txfDescOrc.getText());
//			o.setLista(listaProdutos);
//		}
//		return o;
//	}
	
	private void insereBanco(Orcamento o) {
		if(txfIdOrc.getText().isEmpty() || txfDescOrc.getText().isEmpty() || clienteSelecionado == null || listaProdutos.size() == 0){
			JOptionPane.showMessageDialog(null, "Preencha os campos corretamente");
		}else{
			o.setId(Long.parseLong(txfIdOrc.getText()));
			o.setCliente(clienteSelecionado);
			o.setDescricao(txfDescOrc.getText());
			for(int i = 0; i < listaProdutos.size(); i ++){
				for(int j = 0; j < listaProdutos.size() -1; j++){
					if(listaProdutos.get(j).getId().equals(listaProdutos.get(j+1).getId())){
						listaProdutos.remove(j);
						listaProdutos.get(j).setQtd(listaProdutos.get(j).getQtd().add(new BigDecimal(1)));
					}
				}
			}
			o.setLista(listaProdutos);
			
			oDao.insere(o);
			oDao.insereProdOrc(o, o.getLista());
			configuraTabelaOrcamentos();
		}
	}
	
	private void configuraTabelaOrcamentos() {
		List<Orcamento> lista = new ArrayList<>();
		this.tblOrcamentoProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int idx = tblOrcamentoProd.getSelectedRow();
					orcamentoSelecionado = poM.getOrcamento(idx);
					preencheCampos();
					configuraTabelaOrc();
				}
			}
		});
		
		lista = oDao.getTodos();
		
		poM = new ProdOrcamentoModel(lista);
		tblOrcamentoProd.setModel(poM);
		
	}

	protected void preencheCampos() {
		txfClienteOrc.setText(orcamentoSelecionado.getCliente().getNome());
		txfCpfOrc.setText(orcamentoSelecionado.getCliente().getCpf());
		txfDescOrc.setText(orcamentoSelecionado.getDescricao());
		txfID_clienteOrc.setText(String.valueOf(orcamentoSelecionado.getCliente().getId()));
		txfIdOrc.setText(String.valueOf(orcamentoSelecionado.getId()));
		
	}

	protected void buscarClientes() {
		List<Cliente> list = ClienteDao.buscaBanco(txfClienteBusca.getText()); 
		
		cmodelo = new ClienteModel(list);
		this.tbCliente.setModel(cmodelo);
		
	}

	private void configuraTabelaOrc() {
		TelaPrincipal tp = new TelaPrincipal();
		this.tbOrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 1){
					int idx = tbOrc.getSelectedRow();
					produtoSelecionado = oModel.getProduto(idx);
				}
			}
		});
		if(orcamentoSelecionado!= null){
			BigDecimal bd = somar(orcamentoSelecionado.getLista());
			oModel = new OrcamentoModel(orcamentoSelecionado.getLista(), tp.getValorDolar());
			oModel.setValorTotal(bd);
			tbOrc.setModel(oModel);
		}else{
			BigDecimal bd = somar(listaProdutos);
			oModel = new OrcamentoModel(listaProdutos, tp.getValorDolar());
			oModel.setValorTotal(bd);
			tbOrc.setModel(oModel);
		}
		
	}

	public BigDecimal somar(List<Produto> listaProdutos) {
		BigDecimal qtd = new BigDecimal(0);
		if(listaProdutos.isEmpty()){
			return new BigDecimal(0);
		}else{
			BigDecimal valorTotal = new BigDecimal(0);
			for (Produto produto : listaProdutos) {
				qtd = produto.getQtd();
				BigDecimal a = valorTotal;
				BigDecimal b = produto.getValorDolar().multiply(qtd);
				valorTotal = a.add(b);
			}
			return valorTotal;
		}
		
	}

	private void configuraTabelaProduto() {
		TelaPrincipal tp = new TelaPrincipal();
		Orcamento orc = new Orcamento();
		this.tbItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					if(txfQtd.getText().isEmpty()){
						JOptionPane.showMessageDialog(null, "Informe uma quantidade");
					}else{
						Produto p = new Produto();
						produtoSelecionado = pmodelo.getProduto(tbItem.getSelectedRow());
						p.setDescricao(produtoSelecionado.getDescricao());
						p.setId(produtoSelecionado.getId());
						p.setQtd(BigDecimal.valueOf(Long.parseLong(txfQtd.getText())));
						p.setValorDolar(produtoSelecionado.getValorDolar());
						
						listaProdutos.add(p);
						configuraTabelaOrc();
					}
				}
			}
		});
		
		txfItemBusca.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F2 || e.getKeyCode() == KeyEvent.VK_ENTER){
					buscarItens(txfItemBusca.getText().trim());
				}
				super.keyPressed(e);
			}
			
		});
			
		pmodelo = new ProdutoModel(pdao.getTodos(), tp.getValorDolar());
		this.tbItem.setModel(pmodelo);
		
		
		orc.setLista(listaProdutos);
	}



	protected void buscarItens(String palavra) {
		TelaPrincipal tp = new TelaPrincipal();
		List<Produto> list = pdao.buscaBanco(palavra); 
		
		pmodelo = new ProdutoModel(list, tp.getValorDolar());
		this.tbItem.setModel(pmodelo);
		
	}

	private void configuraTabelaCliente() {
		this.tbCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int linha = tbCliente.getSelectedRow();
					carregarCamposCliente(linha);
				}
			}
			
		});
		
		this.txfClienteBusca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		scrPcliente.setViewportView(tbCliente);
		
		ClienteDao cd = new ClienteDao();
		List<Cliente> lista = cd.getTodos();
			
		this.modeloCliente = new ClienteModel(lista);
		tbCliente.setModel(modeloCliente);
	}

	protected void carregarCamposCliente(int linha) {
		clienteSelecionado = modeloCliente.pegaCliente(linha);
		
		this.txfClienteBusca.setText(clienteSelecionado.getNome().trim());
		this.txfClienteOrc.setText(clienteSelecionado.getNome());
		this.txfID_clienteOrc.setText(String.valueOf(clienteSelecionado.getId()));
		this.txfCpfOrc.setText(clienteSelecionado.getCpf());
	}
	
}
