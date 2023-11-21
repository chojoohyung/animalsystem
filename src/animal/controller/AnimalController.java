package animal.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import animal.dao.AnimalDAO;
import animal.dao.JDBCConnector;
import animal.view.AnimalDeleteView;
import animal.view.AnimalInsertView;
import animal.view.AnimalSearchView;
import animal.view.AnimalUpdateView;
import animal.vo.AnimalVO;

public class AnimalController extends JFrame {
	Connection con;
	AnimalDAO dao;
	AnimalSearchView searchPan;
	ArrayList<AnimalVO> animalVOList;
	JComboBox<String> comboSearch;
	JTabbedPane tab;
	AnimalInsertView insertPan;
	AnimalUpdateView updatePan;
	AnimalDeleteView deletePan;
	JTable table;
	static final int YES = 0;

	static final int SEARCH_PAN = 0;
	static final int INSERT_PAN = 1;
	static final int UPDATE_PAN = 2;
	static final int DELETE_PAN = 3;

	public AnimalController() {
		tab = new JTabbedPane();
		con = JDBCConnector.getCon();
		dao = new AnimalDAO();

		// ******************상단탭 시작************************
		// 동물 검색
		searchPan = new AnimalSearchView();
		animalVOList = dao.select("", 0);
		searchPan.setAnimalVOList(animalVOList);
		searchPan.initView();

		comboSearch = searchPan.getComboSearch();
		JButton btnSearch = searchPan.getBtnSearch();
		btnSearch.addActionListener(btnL);
		// 데이터 백업
		JButton btnBackup = searchPan.getBtnBackup();
		btnBackup.addActionListener(btnBackL);

		// 동물 추가
		insertPan = new AnimalInsertView();
		animalVOList = dao.select("", 0);
		insertPan.setAnimalVOList(animalVOList);
		insertPan.initView();
		JButton btnAdd = insertPan.getBtnAdd();
		btnAdd.addActionListener(btnAddL);

		// 동물 수정
		updatePan = new AnimalUpdateView();
		renewView(UPDATE_PAN);

		table = updatePan.getTable();
		table.addMouseListener(tableL);

		JButton btnUpdate = updatePan.getBtnUpdate();
		btnUpdate.addActionListener(btnUpdateL);

		// 동물 삭제
		deletePan = new AnimalDeleteView();
		renewView(DELETE_PAN);

		table = deletePan.getTable();
		table.addMouseListener(tableL);

		JButton btnDelete = deletePan.getBtnDelete();
		btnDelete.addActionListener(btnDeleteL);

		tab.add("검색", searchPan);
		tab.add("추가", insertPan);
		tab.add("수정", updatePan);
		tab.add("삭제", deletePan);
		// ******************상단탭 끝************************

		tab.addMouseListener(tabL);

		add(tab);

		setTitle("동물 관리 시스템");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 500, 600, 500);
		setVisible(true);
	}

	ActionListener btnL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			animalVOList = dao.select(searchPan.getSearchWord(), comboSearch.getSelectedIndex());
			searchPan.setAnimalVOList(animalVOList);
			searchPan.putSearchResult();
		}
	};

	ActionListener btnBackL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				dao.backup();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	};

	ActionListener btnAddL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			AnimalVO vo = insertPan.neededInsertData();
			dao.insert(vo);
			animalVOList = dao.select("", 0);
			insertPan.setAnimalVOList(animalVOList);
			insertPan.putSearchResult();
		}
	};

	ActionListener btnDeleteL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(AnimalController.this, "정말로 삭제하시겠습니까?", "삭제여부",
					JOptionPane.WARNING_MESSAGE);
			if (result == YES) {
				AnimalVO vo = deletePan.neededDeleteData();
				dao.delete(vo);
			}
			animalVOList = dao.select("", 0);
			deletePan.setAnimalVOList(animalVOList);
			deletePan.putSearchResult();
		}
	};

	MouseAdapter tableL = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			if (e.getClickCount() == 1) {
				int row = table.getSelectedRow();
				updatePan.setTextField(row);
				deletePan.setTextField(row);
			}
		}
	};

	ActionListener btnUpdateL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			AnimalVO vo = updatePan.neededUpdateData();
			dao.update(vo);
			animalVOList = dao.select("", 0);
			updatePan.setAnimalVOList(animalVOList);
			updatePan.putSearchResult();
		}
	};

	MouseAdapter tabL = new MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent e) {
			animalVOList = dao.select("", 0);
			insertPan.setAnimalVOList(animalVOList);
			insertPan.putSearchResult();
			searchPan.setAnimalVOList(animalVOList);
			searchPan.putSearchResult();
			updatePan.setAnimalVOList(animalVOList);
			updatePan.putSearchResult();
			deletePan.setAnimalVOList(animalVOList);
			deletePan.putSearchResult();
		};
	};

	public void renewView(int flag) {
		animalVOList = dao.select("", 0);
		switch (flag) {
		case SEARCH_PAN:
			searchPan.setAnimalVOList(animalVOList);
			searchPan.initView();
			break;
		case INSERT_PAN:
			insertPan.setAnimalVOList(animalVOList);
			insertPan.initView();
			break;
		case DELETE_PAN:
			deletePan.setAnimalVOList(animalVOList);
			deletePan.initView();
			break;
		default:
			updatePan.setAnimalVOList(animalVOList);
			updatePan.initView();
			break;
		}
	}

	public static void main(String[] args) {
		new AnimalController();
	}
}
