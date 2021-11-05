import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GUI extends JFrame
{
    String searchRangeOptions[] = {"전체", "부서", "성별", "연봉", "생일", "부하직원"};
    String checkBoxOptions[] = {"Name", "Ssn", "Bdate", "Address", "Sex", "Salary", "Supervisor", "Department"};
    //searchRangeOptions에서 선택된 옵션! 기본은 "전체"입니다.
    String selectedSearchRangeOptions = "전체";

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 이 부분은 DB와 연결 필요 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String genderOptions[] ={"호랑이", "기린"};
    JComboBox<String> genderComboBox = new JComboBox<String>(genderOptions);
    String departmentOptions[] ={"소학", "전정", "항우기", "경영", "자전"};
    JComboBox<String> departmentComboBox = new JComboBox<String>(departmentOptions);
    String birthOptions[] ={"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    JComboBox<String> birthComboBox = new JComboBox<String>(birthOptions);
    JTextField salaryTextField = new JTextField();
    JTextField subordinateTextField = new JTextField();


    public void mainScreen()
    {
        setLayout(null);//절대 위치를 위한 설정(먼저 위치해있어야 함.
        //=================================================================
        //라벨: "검색 범위"
        JLabel labelSearchRange = new JLabel();
        //콤보박스: searchRangeOptions를 옵션으로 갖는 콤보박스 생성
        JComboBox<String> searchRangeComboBox = new JComboBox<String>(searchRangeOptions);
        //라벨: "검색 항목"
        JLabel labelSearchObject = new JLabel();
        //체크박스
        JCheckBox[] checkBoxesAttributes = new JCheckBox[checkBoxOptions.length];
        int oldXposition = 80;
        for(int i=0; i<checkBoxOptions.length; i++)
        {
            checkBoxesAttributes[i] = new JCheckBox(checkBoxOptions[i], true);

            if(i==0) checkBoxesAttributes[i].setBounds(80, 40, 120, 20);
            else
            {
                System.out.println(oldXposition + " : " + checkBoxOptions[i-1] +" : "+ checkBoxOptions[i-1].length()*15);
                checkBoxesAttributes[i].setBounds(oldXposition + checkBoxOptions[i-1].length()*15, 40, 120, 20);
                oldXposition = oldXposition + checkBoxOptions[i-1].length()*15;
                add(checkBoxesAttributes[i]);
                revalidate();
                repaint();
            }

        }
        //=================================================================

        //=================================================================
        //Component 내용물 설정
        labelSearchRange.setText("검색 범위");
        labelSearchObject.setText("검색 항목");

        //=================================================================
        //각종 Component 위치 조정
        labelSearchRange.setBounds(10, 10, 70, 25);
        labelSearchObject.setBounds(10, 40, 70, 25);
        searchRangeComboBox.setBounds(70, 7, 100, 25);

        //=================================================================

        //=================================================================
        //각종 Component를 main에 추가
        add(labelSearchRange);
        add(labelSearchObject);
        add(searchRangeComboBox);



        //=================================================================

        //=================================================================
        //각종 Action Listener 설정
        searchRangeComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedSearchRangeOptions = searchRangeComboBox.getSelectedItem().toString();
                Component subComponentSet[] = {departmentComboBox, genderComboBox, salaryTextField, birthComboBox, subordinateTextField};
                System.out.println(selectedSearchRangeOptions);
                if (selectedSearchRangeOptions == "전체")
                {
                    for (int i = 0; i < subComponentSet.length; i++) remove(subComponentSet[i]);
                    repaint();
                }
                else if(selectedSearchRangeOptions == "부서")
                {
                    //ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
                    for(int i=0; i<subComponentSet.length; i++)
                    {
                        if(subComponentSet[i]==departmentComboBox) continue;
                        remove(subComponentSet[i]);
                    }
                    //위치 설정 및 추가
                    departmentComboBox.setBounds(180, 7, 100, 25);
                    add(departmentComboBox);
                    revalidate();
                    repaint();
                }
                else if(selectedSearchRangeOptions == "성별")
                {
                    //ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
                    for(int i=0; i<subComponentSet.length; i++)
                    {
                        if(subComponentSet[i]==genderComboBox) continue;
                        remove(subComponentSet[i]);
                    }
                    genderComboBox.setBounds(180, 7, 120, 25);
                    add(genderComboBox);
                    revalidate();
                    repaint();
                }
                else if(selectedSearchRangeOptions == "연봉")
                {
                    //ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
                    for(int i=0; i<subComponentSet.length; i++)
                    {
                        if(subComponentSet[i]==salaryTextField) continue;
                        remove(subComponentSet[i]);
                    }
                    salaryTextField.setBounds(180, 7, 120, 25);
                    add(salaryTextField);
                    salaryTextField.setVisible(true);
                    repaint();
                }
                else if(selectedSearchRangeOptions == "생일")
                {
                    //ComboBox 옵션을 바꿀때를 대비하기 위해 다른 Component를 지우는 코드
                    for(int i=0; i<subComponentSet.length; i++)
                    {
                        if(subComponentSet[i]==birthComboBox) continue;
                        remove(subComponentSet[i]);
                    }
                    birthComboBox.setBounds(180, 7, 60, 25);
                    add(birthComboBox);
                    revalidate();
                    repaint();
                }
                else if(selectedSearchRangeOptions == "부하직원")
                {
                    for(int i=0; i<subComponentSet.length; i++)
                    {
                        if(subComponentSet[i]==subordinateTextField) continue;
                        remove(subComponentSet[i]);
                    }
                    subordinateTextField.setBounds(180, 7, 120, 25);
                    add(subordinateTextField);
                    repaint();
                }
            }
        });

        //=================================================================

        //=================================================================
        //프레임 설정
        setVisible(true);
        setSize(1200, 920);
        setTitle("DB후반기 5조 팀플");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
