import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class GUI extends JFrame
{
    String searchRangeOptions[] = {"전체", "부서", "성별", "연봉", "생일", "부하직원"};
    //searchRangeOptions에서 선택된 옵션! 기본은 "전체"입니다.
    String selectedSearchRangeOptions = "전체";

    public void mainScreen()
    {
        setLayout(null);//절대 위치를 위한 설정(먼저 위치해있어야 함)

        //=================================================================
        //라벨: "검색 범위"
        JLabel labelSearchRange = new JLabel();
        //콤보박스: searchRangeOptions를 옵션으로 갖는 콤보박스 생성
        JComboBox<String> searchRangeComboBox = new JComboBox<String>(searchRangeOptions);
        //라벨: "검색 항목"
        JLabel labelSearchObject = new JLabel();
        //=================================================================

        //=================================================================
        //Component 내용물 설정
        labelSearchRange.setText("검색 범위");
        labelSearchObject.setText("검색 항목");

        //=================================================================
        //각종 Component 위치 조정
        labelSearchRange.setBounds(10, 10, 70, 25);
        searchRangeComboBox.setBounds(70, 7, 70, 25);

        //=================================================================

        //=================================================================
        //각종 Component를 main에 추가
        add(labelSearchRange);
        add(searchRangeComboBox);

        //=================================================================

        //=================================================================
        //각종 Action Listener 설정
        searchRangeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSearchRangeOptions = searchRangeComboBox.getSelectedItem().toString();
                System.out.println(selectedSearchRangeOptions);
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
