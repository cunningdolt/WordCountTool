package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class TextPanel extends JPanel {
    private JTextArea textArea;

    TextPanel(){
        textArea = new JTextArea();
        textArea.setBackground(Color.LIGHT_GRAY.brighter());
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Serif",Font.PLAIN,18));
        textArea.setEditable(false);
        textArea.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
        setBackground(Color.BLACK);

        Border outerBorder = BorderFactory.createTitledBorder("Text");
        ((TitledBorder) outerBorder).setTitleColor(Color.green);
        Border innerBorder = BorderFactory.createEmptyBorder(2,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));

        setLayout(new BorderLayout());

        add(new JScrollPane(textArea),BorderLayout.CENTER);
        
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
