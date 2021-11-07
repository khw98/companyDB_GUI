import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
public class InsertDialog extends JDialog implements ActionListener   // 새로운 직원의 정보 추가할 다이얼로그
{ 
	  JPanel pw = new JPanel(new GridLayout(10,1));
	    JPanel pc = new JPanel(new GridLayout(10,1));
	    JPanel ps = new JPanel();
	    
	    JLabel label_Fname = new JLabel("First Name: ");
	    JLabel label_Minit = new JLabel("Middle init: ");
	    JLabel label_Lname = new JLabel("Last Name: ");
	    JLabel label_Ssn = new JLabel("Ssn: ");
	    JLabel label_Bdate = new JLabel("Birthdate: ");
	    JLabel label_Address = new JLabel("Address: ");
	    JLabel label_Sex = new JLabel("Sex: ");
	    JLabel label_Salary = new JLabel("Salary: ");
	    JLabel label_Super_ssn =new JLabel("Super_ssn: ");
	    JLabel label_Dno = new JLabel("Dno: ");
	 
	 
	    JTextField Fname = new JTextField();
	    JTextField Minit = new JTextField();
	    JTextField Lname = new JTextField();
	    JTextField Ssn = new JTextField();
	    JTextField Bdate = new JTextField();
	    JTextField Address = new JTextField();
	    //JTextField Sex = new JTextField();
	    String genderOptions[] ={"M", "F"};
	    JComboBox<String> Sex = new JComboBox<String>(genderOptions);
	   
	    JTextField Salary = new JTextField();
	    JTextField Super_ssn =new JTextField();
	    JTextField Dno = new JTextField();
	   
	    JButton insert;
	    JButton reset= new JButton("취소");
	 
	   DAO dao =new DAO();
	   JDBCGUI me;
	 
	    public InsertDialog(JDBCGUI me, String index)
	    {
	        super(me,"새로운 직원정보 추가");
	        this.me=me;
	        if(index.equals("직원 추가"))
	        {
	            insert =new JButton(index);
	        }
	        
	       
	        pw.add(label_Fname);    // 라벨 추가
	        pw.add(label_Minit);
	        pw.add(label_Lname);
	        pw.add(label_Ssn);
	        pw.add(label_Bdate);
	        pw.add(label_Address);
	        pw.add(label_Sex);
	        pw.add(label_Salary);
	        pw.add(label_Super_ssn);
	        pw.add(label_Dno);
	        
	        
	   
	        pc.add(Fname);     //  텍스트박스 추가
	        pc.add(Minit);
	        pc.add(Lname);
	        pc.add(Ssn);
	        pc.add(Bdate);
	        pc.add(Address);
	        pc.add(Sex);
	        pc.add(Salary);
	        pc.add(Super_ssn);
	        pc.add(Dno);
	        
	        
	       
	       
	        ps.add(insert);   
	        ps.add(reset);
	   
	        add(pw,"West");
	        add(pc,"Center");
	        add(ps,"South");
	       
	        setSize(400,400);
	        setVisible(true);
	 
	        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);   // 종료시 다이얼로그 창만 닫음
	       
	        insert.addActionListener(this); //추가 이벤트등록
	        reset.addActionListener(this); //취소 이벤트등록     
	    }
	   
	    
	    public static void messageBox(Object obj , String message)     // 메세지박스 출력
	    {
	        JOptionPane.showMessageDialog( (Component)obj , message);
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e)
	    {
	       String Bt =e.getActionCommand(); // 어느 버튼 누르냐 판단
	      
	        if(Bt.equals("직원 추가"))
	       {
	        	
	        	 if(dao.userListInsert(this) > 0 )
	        	 {
	               messageBox(this , "새 직원 " + Fname.getText()+"의 정보 추가됨");
	               dispose();         // 다이얼로그 창 닫기
	             }
	        	 else 
	           {
	               messageBox(this,"직원정보 입력 실패");
	           }
	       }
	        else if(Bt.equals("취소"))
	        {
	            dispose();
	        }
	        
	       
	    }
	   
	    
	    

}
