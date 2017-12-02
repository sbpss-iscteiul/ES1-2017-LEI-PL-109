package antiSpamFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import txtreader.Rule;
import txtreader.Writer;
import txtreader.Leitor;

public class Interface{
	
	private JFrame frame;
	private Leitor leitor;
	private TableModel ruleModel;
	private JTable ruleTable;
	private Box container;
	//cenas para controlo de configura��es
	private boolean rulesStatus;
	private boolean hamStatus;
	private boolean spamStatus;

	private Writer fileWriter;
	private JPanel centerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	
	public Interface(){
		//Adicionado por sergio
		//criar frame
		frame = new JFrame();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLayout(new BorderLayout());
		//----------------------------
		//inicializar e adicionar paineis
		//center
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1,2));
		//centerLeft
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		//centerright
		rightPanel = new JPanel();
		//--------------------------
		container = new Box(2);
		rulesStatus=false;
		hamStatus=false;
		spamStatus=false;
		leitor = new Leitor();
		leitor.ler_Regras("C:\\Users\\Sergio-PC\\Desktop\\Universidade\\Engenharia de Software\\Projecto\\Inputs\\rules.cf");
		addFrameContent();
		
	}
	
	public void open(){
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	public void addFrameContent (){
		/*
		//criar frame
		frame = new JFrame();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLayout(new BorderLayout());
		*/
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		container.setAlignmentX(Box.LEFT_ALIGNMENT);
		
		//lista de regras
		ArrayList<Rule> ruleList = leitor.get_Regras();
		
		/*
		//WHY IS THIS HERE
		Scanner ruleScanner;
		*/
		String[] columnNames = {"Rule", "Weight"};
		
		Object[][] data = new Object[ruleList.size()][2];
			for(int i=0; i<ruleList.size(); i++){
				data[i][0] = ruleList.get(i).getName();
				data[i][1] = ruleList.get(i).getPeso();
			}

		ruleModel = new DefaultTableModel(data, columnNames);
		ruleTable = new JTable(ruleModel);
		ruleTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		ruleModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				//possivelmente utilizar o event e
				for(int i=0; i<ruleModel.getRowCount(); i++){
					Rule tmpRule = new Rule(String.valueOf(ruleModel.getValueAt(i, 0)), Double.valueOf(String.valueOf(ruleModel.getValueAt(i, 1))));
					ruleList.set(i, tmpRule);
				}
			}
		});
		JScrollPane ruleScroll = new JScrollPane(ruleTable);
		

		fileWriter = new Writer(ruleList);
		JButton testButton = new JButton("test buttton");
		testButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rulesStatus)
					fileWriter.write();
				else
					//Teste by sergio
					JOptionPane.showMessageDialog(frame, "nao ha um ficheiro regras selecionado");

			}
		});
		
		//painel Norte (aka file chooser)
		JFileChooser fc = new JFileChooser();
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setBorder(border);
		frame.add(northPanel, BorderLayout.NORTH);
		
		//campos de texto 
		JTextField text1= new JTextField(25);
		JTextField text2= new JTextField(25);
		JTextField text3= new JTextField(25);
		
		//criar primeiro painel de selec��o
		//Teste by sergio
		JLabel label1= new JLabel("Rules.cf:");
		JPanel panelN1= new JPanel();
		panelN1.setBorder(border);
		northPanel.add(panelN1);
		panelN1.add(label1);
		panelN1.add(text1);
		
		//criar segundo painel de selec��o
		JLabel label2= new JLabel("label2:");
		JPanel panelN2= new JPanel();
		panelN2.setBorder(border);
		northPanel.add(panelN2);
		panelN2.add(label2);
		panelN2.add(text2);
		
		//criar terceiro painel de selec��o
		JLabel label3= new JLabel("label3:");
		JPanel panelN3= new JPanel();
		panelN3.setBorder(border);
		northPanel.add(panelN3);
		panelN3.add(label3);
		panelN3.add(text3);
		
		//criar os butoes para cada painel de selec��o
		JButton button1 = new JButton(new Action() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fc.showOpenDialog(text1);
				//Teste by sergio
				if(fc.getSelectedFile().toString().contains("rules.cf")) {
					text1.setText(fc.getSelectedFile().toString());
					rulesStatus=true;
				}else {
					JOptionPane.showMessageDialog(frame, "Path selecionado nao contem rules.cf");
				}
					
			}
			
			@Override
			public void setEnabled(boolean b) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void putValue(String key, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Object getValue(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
		});
		JButton button2 = new JButton(new Action() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fc.showOpenDialog(text2);
				text2.setText(fc.getSelectedFile().toString());
			}
			
			@Override
			public void setEnabled(boolean b) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void putValue(String key, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Object getValue(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
		});
		JButton button3 = new JButton(new Action() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fc.showOpenDialog(text3);
				text3.setText(fc.getSelectedFile().toString());
			}
			
			@Override
			public void setEnabled(boolean b) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removePropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void putValue(String key, Object value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isEnabled() {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public Object getValue(String key) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addPropertyChangeListener(PropertyChangeListener listener) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Colocar texto nos botoes 
		button1.setText("Browse");
		button2.setText("Browse");
		button3.setText("Browse");
		
		//Adicionar os botoes aos paineis de selec��o
		panelN1.add(button1);
		panelN2.add(button2);
		panelN3.add(button3);
		
		//Criar painel esquerda (Lista de Regras)
		leftPanel.add(ruleScroll);
		leftPanel.setBorder(border);
		
		//Criar painel da direita
		rightPanel = new JPanel();
		rightPanel.setBorder(border);
		rightPanel.add(testButton);
		
		//adicionar paineis esquerdo e direito ao painel central, e o central � frame
		centerPanel.add(leftPanel);
		centerPanel.add(rightPanel);
		frame.add(centerPanel);
	}
}
