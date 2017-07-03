package br.univel.produto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.univel.cliente.PainelBuscaCliente;
import br.univel.cliente.PainelCadastroCliente;
import br.univel.orcamento.PainelOrcamento;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	
	private ProdutoModel pmodelo;
	private BigDecimal valorDolar = new BigDecimal("3.3129");
	private ProdutoDao pdao = new ProdutoDao();
	private Produto produtoSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblValor = new JLabel("Valor");
		GridBagConstraints gbc_lblValor = new GridBagConstraints();
		gbc_lblValor.insets = new Insets(0, 0, 0, 5);
		gbc_lblValor.anchor = GridBagConstraints.EAST;
		gbc_lblValor.gridx = 0;
		gbc_lblValor.gridy = 0;
		panel.add(lblValor, gbc_lblValor);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(15);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Adicione um valor ao dolar");
				}else{
					valorDolar = new BigDecimal(textField.getText());
				}
				buscar();
			}
		});
		GridBagConstraints gbc_btnConsultar = new GridBagConstraints();
		gbc_btnConsultar.anchor = GridBagConstraints.WEST;
		gbc_btnConsultar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConsultar.gridx = 0;
		gbc_btnConsultar.gridy = 1;
		contentPane.add(btnConsultar, gbc_btnConsultar);
		
		JButton btnOrcamento = new JButton("Orcamento");
		btnOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abreTelaOrcamento();
			}
		});
		GridBagConstraints gbc_btnOrcamento = new GridBagConstraints();
		gbc_btnOrcamento.insets = new Insets(0, 0, 5, 5);
		gbc_btnOrcamento.gridx = 1;
		gbc_btnOrcamento.gridy = 1;
		contentPane.add(btnOrcamento, gbc_btnOrcamento);
		
		JButton btnCadastrarcliente = new JButton("CadastrarCliente");
		btnCadastrarcliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exibePainelCadastroCliente();
			}
		});
		GridBagConstraints gbc_btnCadastrarcliente = new GridBagConstraints();
		gbc_btnCadastrarcliente.insets = new Insets(0, 0, 5, 0);
		gbc_btnCadastrarcliente.gridx = 2;
		gbc_btnCadastrarcliente.gridy = 1;
		contentPane.add(btnCadastrarcliente, gbc_btnCadastrarcliente);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		configuraTabela();
	}

	protected void exibePainelCadastroCliente() {
		PainelCadastroCliente pcc = new PainelCadastroCliente();
		setGlassPane(pcc);
		pcc.setVisible(true);
		
	}

	protected void abreTelaOrcamento() {
		PainelOrcamento to = new PainelOrcamento();
		setGlassPane(to);
		to.setVisible(true);
	}

	private void configuraTabela() {
		this.table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					produtoSelecionado = pmodelo.getProduto(table.getSelectedRow());
				}
			}
		});
	}



	protected void buscar() {
		List<Produto> minhalista = new ArrayList<>();
		String url = "http://www.master10.com.py/lista-txt/download";
		LeitorProdutoUrl lpu = new LeitorProdutoUrl();
		try {
			minhalista = lpu.lerProdutos(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Produto produto : minhalista) {
			//System.out.println(produto.getDescricao());
		}
		pdao.insere(minhalista);
		pmodelo = new ProdutoModel(pdao.getTodos(),valorDolar);
		this.table.setModel(pmodelo);
	}
	
	public BigDecimal getValorDolar(){
		return this.valorDolar;
	}

}
