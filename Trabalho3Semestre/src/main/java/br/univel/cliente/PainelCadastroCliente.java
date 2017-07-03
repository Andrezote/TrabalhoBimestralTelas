package br.univel.cliente;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;

public class PainelCadastroCliente extends JPanel {
	private Cliente clienteSelecionado;
	private ClienteModel modeloCliente;
	private ClienteDao cd = new ClienteDao();
	
	protected JTable table;
	protected JTextField txfID;
	protected JTextField txfNome;
	protected JTextField txfIdade;
	protected JTextField txfCPF;
	protected JButton btnNovo;
	protected JButton btnSalvar;
	protected JButton btnExcluir;
	protected JButton btnOk;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public PainelCadastroCliente() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCadastroCliente = new JLabel("Cadastro Cliente");
		GridBagConstraints gbc_lblCadastroCliente = new GridBagConstraints();
		gbc_lblCadastroCliente.gridx = 0;
		gbc_lblCadastroCliente.gridy = 0;
		panel.add(lblCadastroCliente, gbc_lblCadastroCliente);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblId = new JLabel("ID");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.WEST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		panel_2.add(lblId, gbc_lblId);
		
		txfID = new JTextField();
		GridBagConstraints gbc_txfID = new GridBagConstraints();
		gbc_txfID.anchor = GridBagConstraints.WEST;
		gbc_txfID.insets = new Insets(0, 0, 5, 0);
		gbc_txfID.gridx = 1;
		gbc_txfID.gridy = 0;
		panel_2.add(txfID, gbc_txfID);
		txfID.setColumns(5);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panel_2.add(lblNome, gbc_lblNome);
		
		txfNome = new JTextField();
		GridBagConstraints gbc_txfNome = new GridBagConstraints();
		gbc_txfNome.insets = new Insets(0, 0, 5, 0);
		gbc_txfNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txfNome.gridx = 1;
		gbc_txfNome.gridy = 1;
		panel_2.add(txfNome, gbc_txfNome);
		txfNome.setColumns(20);
		
		JLabel lblIdade = new JLabel("Idade");
		GridBagConstraints gbc_lblIdade = new GridBagConstraints();
		gbc_lblIdade.anchor = GridBagConstraints.EAST;
		gbc_lblIdade.insets = new Insets(0, 0, 5, 5);
		gbc_lblIdade.gridx = 0;
		gbc_lblIdade.gridy = 2;
		panel_2.add(lblIdade, gbc_lblIdade);
		
		txfIdade = new JTextField();
		GridBagConstraints gbc_txfIdade = new GridBagConstraints();
		gbc_txfIdade.anchor = GridBagConstraints.WEST;
		gbc_txfIdade.insets = new Insets(0, 0, 5, 0);
		gbc_txfIdade.gridx = 1;
		gbc_txfIdade.gridy = 2;
		panel_2.add(txfIdade, gbc_txfIdade);
		txfIdade.setColumns(5);
		
		JLabel lblCpf = new JLabel("CPF");
		GridBagConstraints gbc_lblCpf = new GridBagConstraints();
		gbc_lblCpf.anchor = GridBagConstraints.WEST;
		gbc_lblCpf.insets = new Insets(0, 0, 0, 5);
		gbc_lblCpf.gridx = 0;
		gbc_lblCpf.gridy = 3;
		panel_2.add(lblCpf, gbc_lblCpf);
		
		txfCPF = new JTextField();
		GridBagConstraints gbc_txfCPF = new GridBagConstraints();
		gbc_txfCPF.anchor = GridBagConstraints.WEST;
		gbc_txfCPF.gridx = 1;
		gbc_txfCPF.gridy = 3;
		panel_2.add(txfCPF, gbc_txfCPF);
		txfCPF.setColumns(25);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		GridBagConstraints gbc_btnNovo = new GridBagConstraints();
		gbc_btnNovo.anchor = GridBagConstraints.EAST;
		gbc_btnNovo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNovo.gridx = 0;
		gbc_btnNovo.gridy = 0;
		panel_1.add(btnNovo, gbc_btnNovo);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvaCliente();
				configuraTabela();
				limparCampos();
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalvar.gridx = 1;
		gbc_btnSalvar.gridy = 0;
		panel_1.add(btnSalvar, gbc_btnSalvar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
				configuraTabela();
				limparCampos();
			}
		});
		GridBagConstraints gbc_btnExcluir = new GridBagConstraints();
		gbc_btnExcluir.insets = new Insets(0, 0, 0, 5);
		gbc_btnExcluir.gridx = 2;
		gbc_btnExcluir.gridy = 0;
		panel_1.add(btnExcluir, gbc_btnExcluir);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.gridwidth = 2;
		gbc_btnOk.insets = new Insets(0, 0, 0, 5);
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 0;
		panel_1.add(btnOk, gbc_btnOk);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		this.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int linha = table.getSelectedRow();
					carregarCampos(linha);
				}
			}
			
		});
		scrollPane.setViewportView(table);
		
		configuraTabela();
	}

	protected void excluirCliente() {
		if(clienteSelecionado == null){
			System.out.println("Nao ha cliente selecionado para ser excluido");
		}else{
			cd.exclui(clienteSelecionado.getId());
			limparCampos();
		}
		
	}

	protected void salvaCliente() {
		Cliente c = new Cliente();
		if(clienteSelecionado == null){
			c = insereBanco(c);
			cd.insere(c);
		}else{
			c = insereBanco(clienteSelecionado);
			cd.atualiza(c.getId(), c);
		}
	}

	private Cliente insereBanco(Cliente c) {
		c.setId(Long.parseLong(txfID.getText().trim()));
		c.setNome(txfNome.getText().trim());
		c.setIdade(Integer.parseInt(txfIdade.getText().trim()));
		c.setCpf(txfCPF.getText().trim());
		
		return c;
	}

	private void configuraTabela() {
		ClienteDao cd = new ClienteDao();
		List<Cliente> lista = cd.getTodos();
		
		this.modeloCliente = new ClienteModel(lista);
		
		table.setModel(modeloCliente);
		
	}

	protected void carregarCampos(int linha) {
		Cliente c =  this.modeloCliente.pegaCliente(linha);
		
		this.txfID.setText(String.valueOf(c.getId()));
		this.txfNome.setText(c.getNome());
		this.txfIdade.setText(String.valueOf(c.getIdade()));
		this.txfCPF.setText(c.getCpf());
		
		clienteSelecionado = c;
	}

	protected void limparCampos() {
		this.txfCPF.setText("");
		this.txfID.setText("");
		this.txfIdade.setText("");
		this.txfNome.setText("");
		
		this.clienteSelecionado = null;
	}

}
