package animal.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import animal.vo.AnimalVO;

public class AnimalDeleteView extends JPanel {
	ArrayList<AnimalVO> animalVOList;
	JTable table;
	DefaultTableModel model;
	JPanel panS = new JPanel(new GridLayout(4, 4));
	String[] header = { "동물번호", "성별", "사육사", "나이", "종", "동물이름" };
	String[] comboStr = { "사자", "호랑이", "팬더", "곰", "기린" };
	JLabel[] lbls = new JLabel[header.length];
	JTextField[] tf = new JTextField[header.length - 1];
	JComboBox<String> comboCategory = new JComboBox<String>(comboStr);
	JButton btnDelete = new JButton("삭제");

	public AnimalDeleteView() {
		setLayout(new BorderLayout(10, 10));

		for (int i = 0; i < header.length; i++) {
			lbls[i] = new JLabel(header[i]);
			panS.add(lbls[i]);

			if (i < header.length - 1) {
				if(i != 4) {
					tf[i] = new JTextField();
					panS.add(tf[i]);
				} else if (i == 4) {
					panS.add(comboCategory);
				}
			}
		}
		tf[4] = new JTextField();
		panS.add(tf[4]);
		
		for (int i = 0; i < 3; i++) {
			panS.add(new JLabel(""));
		}
		panS.add(btnDelete);
		panS.setBackground(Color.orange);
	}

	public void setTextField(int row) {
		for (int i = 0; i < tf.length - 1; i++) {
			tf[i].setText(model.getValueAt(row, i) + "");
		}
		comboCategory.setSelectedItem(model.getValueAt(row, 4));
		tf[4].setText(model.getValueAt(row, 5) + "");
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
		model = new DefaultTableModel(header, animalVOList.size()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		putSearchResult();

		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(60);

		tableCellCenter(table);
		
		JScrollPane scroll = new JScrollPane(table);

		scroll.getViewport().setBackground(Color.CYAN);
		
		add("Center", scroll);
		add("South", panS);
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

	public JButton getBtnDelete() {
		return btnDelete;
	}

	public AnimalVO neededDeleteData() {
		AnimalVO vo = new AnimalVO();

		vo.setId(Integer.parseInt(tf[0].getText()));
		vo.setSex(tf[1].getText());
		vo.setKeeper(tf[2].getText());
		vo.setAge(Integer.parseInt(tf[3].getText()));
		vo.setspecName((String) comboCategory.getSelectedItem());
		vo.setName(tf[4].getText());
		
		return vo;
	}

	public void initData() {
		for (int i = 0; i < tf.length; i++) {
			tf[i].setText("");
		}
		comboCategory.setSelectedIndex(0);
	}

	public JTable getTable() {
		return table;
	}
}
