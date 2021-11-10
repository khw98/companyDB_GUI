import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;

public class JDBCGUI extends JFrame implements ActionListener {

	String searchRangeOptions[] = { "전체", "부서", "성별", "연봉", "생일", "부하직원" };
	String checkBoxOptions[] = { "Name", "Ssn", "Bdate", "Address", "Sex", "Salary", "Supervisor", "Department" };
	String selectedSearchRangeOptions = "전체";
	String genderOptions[] = { "M", "F" };
	JComboBox<String> genderComboBox = new JComboBox<String>(genderOptions);
	String departmentOptions[] = { "Headquarters", "Administration", "Research" };
	JComboBox<String> departmentComboBox = new JComboBox<String>(departmentOptions);
	String birthOptions[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
	JComboBox<String> birthComboBox = new JComboBox<String>(birthOptions);
	JTextField salaryTextField = new JTextField();
	JTextField subordinateTextField = new JTextField();
	JCheckBox[] checkBoxesAttributes = new JCheckBox[checkBoxOptions.length];
	ArrayList<Integer> selectedCheckBoxList = new ArrayList<Integer>(); // 테이블에서 클릭한 row를 저장하기 위한 리스트
	JLabel orderLabel = new JLabel("정렬:");
	JComboBox<String> searchRangeComboBox = new JComboBox<String>(searchRangeOptions); // searchRangeComboBOx
	String orderOptions[] = { "정렬 없음", "오름차순", "내림차순" };
	JComboBox<String> orderComboBox = new JComboBox<String>(orderOptions);

	String[] attribute = {"NAME", "SSN", "BDATE", "ADDRESS", "SEX", "SALARY", "SUPERVISOR", "DEPARTMENT", "선택"}; // 출력할 attribute들
	//String[] attribute = new String[10];
	DefaultTableModel dft = new DefaultTableModel(attribute, 0); // DefaultTableModel 이용하여 jtable에 데이터 저장
	JTable jt = new JTable(dft);
	JScrollPane jsp = new JScrollPane(jt);

	JCheckBox jc = new JCheckBox();
	JLabel selectperson;

	String comboOptions[] = { "Address", "Sex", "Salary" };

	JComboBox combo = new JComboBox(comboOptions);
	JTextField upText = new JTextField(20);
	JButton update = new JButton("UPDATE");
	JButton insert = new JButton("직원 추가");
	JButton delete = new JButton("선택한 데이터 삭제");
	JButton search = new JButton("검색");

	DAO dao = new DAO();

	public void mainscreen() // GUI ( JComboBox는 GUI 코드 그대로 가져와서 이벤트 등록도 이안에 있습니다!)
	{
		setLayout(null);// 절대 위치를 위한 설정(먼저 위치해있어야 함.
		// =================================================================
		// 라벨: "검색 범위"
		JLabel labelSearchRange = new JLabel();
		// 콤보박스: searchRangeOptions를 옵션으로 갖는 콤보박스 생성

		searchRangeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSearchRangeOptions = searchRangeComboBox.getSelectedItem().toString();
				Component subComponentSet[] = { departmentComboBox, genderComboBox, salaryTextField, birthComboBox,
						subordinateTextField };
				//System.out.println(selectedSearchRangeOptions);

				if (selectedSearchRangeOptions == "전체") {
					for (int i = 0; i < subComponentSet.length; i++)
						remove(subComponentSet[i]);
					repaint();
				} else if (selectedSearchRangeOptions == "부서") {
					// ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == departmentComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					// 위치 설정 및 추가
					departmentComboBox.setBounds(180, 7, 100, 25);
					add(departmentComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "성별") {
					// ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == genderComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					genderComboBox.setBounds(180, 7, 120, 25);
					add(genderComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "연봉") {
					// ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == salaryTextField)
							continue;
						remove(subComponentSet[i]);
					}
					salaryTextField.setBounds(180, 7, 120, 25);
					add(salaryTextField);
					salaryTextField.setVisible(true);
					repaint();
				} else if (selectedSearchRangeOptions == "생일") {
					// ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == birthComboBox)
							continue;
						remove(subComponentSet[i]);
					}
					birthComboBox.setBounds(180, 7, 60, 25);
					add(birthComboBox);
					revalidate();
					repaint();
				} else if (selectedSearchRangeOptions == "부하직원") {
					for (int i = 0; i < subComponentSet.length; i++) {
						if (subComponentSet[i] == subordinateTextField)
							continue;
						remove(subComponentSet[i]);
					}
					subordinateTextField.setBounds(180, 7, 120, 25);
					add(subordinateTextField);
					repaint();
				}
			}
		});
		// 라벨: "검색 항목"
		JLabel labelSearchObject = new JLabel();
		// 체크박스

		int oldXposition = 80;
		for (int i = 0; i < checkBoxOptions.length; i++) {
			checkBoxesAttributes[i] = new JCheckBox(checkBoxOptions[i], true);
			if(i == 0) {
				checkBoxesAttributes[i].setBounds(80, 40, 90, 20);
				add(checkBoxesAttributes[i]);
			} else {
				oldXposition += 95;
				checkBoxesAttributes[i].setBounds(oldXposition, 40, 95, 20);
				add(checkBoxesAttributes[i]);
				setVisible(true);
			}
		}
		//체크박스 선택 항목 확인하기


		// =================================================================
		// Component 내용물 설정
		JLabel selectperson = new JLabel("선택된 사람:");
		selectperson.setBounds(10, 650, 100, 25);
		add(selectperson);

		labelSearchRange.setText("검색 범위");
		labelSearchObject.setText("검색 항목");

		labelSearchRange.setBounds(10, 10, 70, 25);
		labelSearchObject.setBounds(10, 40, 70, 25);
		searchRangeComboBox.setBounds(70, 7, 100, 25);

		add(labelSearchRange);
		add(labelSearchObject);
		add(searchRangeComboBox);

		/* 여기서부터 추가내용 */
		jsp.setBounds(0, 75, 1000, 500);
		combo.setBounds(300, 600, 75, 30);
		insert.setBounds(700, 600, 90, 30);
		upText.setBounds(380, 600, 180, 30);
		update.setBounds(565, 600, 85, 30);
		delete.setBounds(800, 600, 150, 30);
		search.setBounds(880, 40, 100, 25);
		orderLabel.setBounds(330, 10, 40, 25);
		orderComboBox.setBounds(380, 10, 90, 25);

		add(jsp); // 데이터 출력
		add(combo); // 수정항목
		add(insert); // 직원추가 버튼
		add(upText); // 갱신내용 적을 텍스트박스
		add(update); // UPDATE 버튼
		add(delete); // 삭제버 튼
		add(search); // 검색 버튼
		add(orderLabel);//정렬 라벨
		add(orderComboBox);//정렬 콤보박스




		super.setSize(1000, 800);
		super.setVisible(true);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		insert.addActionListener(this);
		update.addActionListener(this);
		search.addActionListener(this);
		delete.addActionListener(this);
		combo.addActionListener(this);

	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new JDBCGUI();
//	}

	// 직원 선택 체크 박스
	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
													   int row, int column) {
			JCheckBox jc = new JCheckBox();
			jc.setSelected(((Boolean) value).booleanValue());
			jc.setHorizontalAlignment(JLabel.CENTER);
			return jc;
		}
	};


	public void tableColumnSize(int a) {
		jt.getColumnModel().getColumn(a).setWidth(0);
	}
	public String[] getAttribute(){ return attribute;}

	@Override
	public void actionPerformed(ActionEvent e) {

		jc.setHorizontalAlignment(JLabel.CENTER);


		if (e.getSource() == insert) // 직원추가 버튼 클릭시 직원추가 다이얼로그 창 나옴
		{
			new InsertDialog(this, "직원 추가");
		}

		else if (e.getSource() == search) // 검색버튼 클릭시 attribute 출력
		{
			String range = searchRangeComboBox.getSelectedItem().toString();
			int selectedAttributeCnt=0;
			List<String> dynamicAttributes = new ArrayList<String>();
			for(int i=0; i<checkBoxesAttributes.length; i++)
			{
				if(checkBoxesAttributes[i].isSelected()==true)
				{
					dynamicAttributes.add(checkBoxesAttributes[i].getText());
					//System.out.println(checkBoxesAttributes[i].getText());
					selectedAttributeCnt++;
				}
			}
			dynamicAttributes.add("선택");
			attribute = new String[dynamicAttributes.size()];
			for(int i=0; i < attribute.length; i++)
			{
				attribute[i] = dynamicAttributes.get(i);
			}
			//attribute[dynamicAttributes.size()+1] = "선택";

			remove(jsp);//새로운 checkbox Attribute 기반의 테이블을 추가하기 위한 제거
			dft = new DefaultTableModel(attribute, 0); // DefaultTableModel 이용하여 jtable에 데이터 저장
			JTable jt = new JTable(dft);
			JScrollPane jsp = new JScrollPane(jt);

			jsp.setBounds(0, 75, 1000, 500);
			add(jsp); // 데이터 출력
			
			jt.getColumn("선택").setCellRenderer(dcr);
			jt.getColumn("선택").setCellEditor(new DefaultCellEditor(jc));    // false 체크박스 되도록 수정

			if (range.equals("전체")) { // 이름 순 정렬 가능
				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("부서")) { // 이름 순 정렬 가능
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						departmentComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("성별")) { // 이름 순 정렬 가능
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						genderComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("연봉")) { // 연봉 순 정렬 가능
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(), salaryTextField.getText(),
						orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("생일")) { // 생일 순 정렬 가능
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(),
						birthComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (range.equals("부하직원")) { // 직원 번호 순 정렬 가능
				dao.userSelect(dft, searchRangeComboBox.getSelectedItem().toString(), subordinateTextField.getText(),
						orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			}

		}

		else if (e.getSource() == delete) {// 종료 메뉴아이템 클릭
			//int[] rows = jt.getSelectedRows(); // 여러명 동시 삭제
			for (int i : selectedCheckBoxList) {
				Object str = jt.getValueAt(i, 0).toString();
				dao.userDelete(str.toString());
			} // 기존의 jt.getSelectedRow 메소드를 대체하여 리스트의 행들로 삭제

			dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute); // 직원정보 삭제 후 테이블 다시 출력
			if (dft.getRowCount() > 0)
				jt.setRowSelectionInterval(0, 0);
		}

		else if (e.getSource() == update) {

			String Item = combo.getSelectedItem().toString(); // 주소, 성별, 급여 중에 선택된 값 저장

			if (Item.equals("Address")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_add(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute); // 직원정보 갱신 후 테이블 다시 출력
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (Item.equals("Sex")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_sex(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			} else if (Item.equals("Salary")) {
				int row = jt.getSelectedRow();
				Object value = jt.getValueAt(row, 0);
				dao.userUpdate_sal(value.toString(), upText.getText());

				dao.userSelectAll(dft, orderComboBox.getSelectedItem().toString(), attribute);
				if (dft.getRowCount() > 0)
					jt.setRowSelectionInterval(0, 0);
			}

		} // update 이벤트 끝
		jt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jt.getSelectedRow();
				boolean isSelected = (boolean)jt.getValueAt(row, 8);
				if(isSelected) {
					selectedCheckBoxList.add(row);
				}
				else {
					selectedCheckBoxList.remove(row);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		/* 2번문제(검색범위, 검색항목) 위한 이벤트 필요 */

	}




}
