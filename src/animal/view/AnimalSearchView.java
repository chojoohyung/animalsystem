package animal.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import animal.vo.AnimalVO;

public class AnimalSearchView extends JPanel {
	JLabel lbl;
	JTable table;
	DefaultTableModel model;
	JTextField tf;
	JButton btnSearch;
	JButton btnBackup;
	ArrayList<AnimalVO> animalVOList;
	JPanel panN;
	String searchWord;
	String[] comboStr = { "동물번호", "성별", "사육사", "나이", "종", "동물이름" };
	JComboBox<String> comboSearch;

	public AnimalSearchView() {
		setLayout(new BorderLayout(10, 10));
		comboSearch = new JComboBox<String>(comboStr);
		JLabel lbl = new JLabel("검색어");
		tf = new JTextField(30);
		comboSearch.setBackground(Color.CYAN);
		comboSearch.setForeground(Color.YELLOW);
		btnSearch = new JButton("검색");
		btnBackup = new JButton("백업");
		panN = new JPanel();
		panN.add(comboSearch);
		panN.add(lbl);
		panN.add(tf);
		panN.add(btnSearch);
		panN.add(btnBackup);
		
		panN.setBackground(Color.CYAN);
	}

	// 테이블 내용 가운데 정렬하기
	public void tableCellCenter(JTable t) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); // 디폴트테이블셀렌더러를 생성
		dtcr.setHorizontalAlignment(SwingConstants.CENTER); // 렌더러의 가로정렬을 CENTER로

		TableColumnModel tcm = t.getColumnModel(); // 정렬할 테이블의 컬럼모델을 가져옴

		// 전체 열에 지정
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}

	public void initView() {
		String[] header = { "동물번호", "성별", "사육사", "나이", "종", "동물이름" };
		model = new DefaultTableModel(header, animalVOList.size()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		putSearchResult();
		
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(80);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);

		tableCellCenter(table);

		JScrollPane scroll = new JScrollPane(table);
		
		scroll.getViewport().setBackground(Color.ORANGE);
		
		add("North", panN);
		add("Center", scroll);
		add("South", btnBackup);
	}

	public void putSearchResult() {
		model.setRowCount(animalVOList.size());
		AnimalVO vo = null;
		for (int i = 0; i < animalVOList.size(); i++) {
			vo = animalVOList.get(i);
			model.setValueAt(vo.getId(), i, 0);
			model.setValueAt(vo.getSex(), i, 1);
			model.setValueAt(vo.getKeeper(), i, 2);
			model.setValueAt(vo.getAge(), i, 3);
			model.setValueAt(vo.getspecName(), i, 4);
			model.setValueAt(vo.getName(), i, 5);
		}
	}

	public void setAnimalVOList(ArrayList<AnimalVO> animalVOList) {
		this.animalVOList = animalVOList;
	}

	public String getSearchWord() {
		searchWord = tf.getText();
		return searchWord;
	}

	public JButton getBtnSearch() {
		return btnSearch;
	}

	public JButton getBtnBackup() {
		return btnBackup;
	}
	
	public JComboBox<String> getComboSearch() {
		return comboSearch;
	}
}
