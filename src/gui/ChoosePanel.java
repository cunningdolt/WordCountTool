package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.EventObject;

interface ChoosePanelListener{
    void eventPerformed(ChoosePanelEvent e) throws IOException;
}

class ChoosePanelEvent extends EventObject {
    private JTextField pathField;
    ChoosePanelEvent(Object source, JTextField pathField) {
        super(source);
        this.pathField = pathField;
    }

    public void setPathField(String text) {
        pathField.setText(text);
    }
}

public class ChoosePanel extends JPanel implements ActionListener {
    private JLabel headLabel;
    private JTextField pathField;
    private JButton importBtn;
    private ChoosePanelListener choosePanelListener;

    ChoosePanel(){
        headLabel = new JLabel("Word Count Tool");
        headLabel.setForeground(Color.green);
        headLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headLabel.setFont(new Font("Serif",Font.BOLD,28));
        pathField = new JTextField(10);
        pathField.setBackground(Color.GRAY);
        pathField.setForeground(Color.BLACK);
        pathField.setFont(new Font("Serif",Font.BOLD,16));
        pathField.setBorder(BorderFactory.createEtchedBorder());
        pathField.setEditable(false);
        importBtn = new JButton("Import File");
        importBtn.setBackground(Color.DARK_GRAY);
        importBtn.setForeground(Color.green);
        importBtn.setActionCommand("Import");
        importBtn.addActionListener(this);

        setBorder(BorderFactory.createEtchedBorder());
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //FirstRow
        gc.gridy=0;

        gc.fill=GridBagConstraints.HORIZONTAL;

        gc.gridx=0;
        gc.weightx=1;
        gc.weighty=1;
        gc.insets = new Insets(0,115,0,0);
        add(headLabel,gc);

        //SecondRow
        gc.gridy=1;

        gc.gridx=0;
        gc.weightx=1;
        gc.weighty=1;
        gc.insets = new Insets(0,10,0,5);
        gc.anchor=GridBagConstraints.CENTER;
        gc.fill=GridBagConstraints.HORIZONTAL;
        add(pathField,gc);

        gc.gridx=1;
        gc.weightx=0.01;
        gc.weighty=1;
        gc.insets = new Insets(0,0,0,0);
        gc.anchor=GridBagConstraints.LINE_START;
        gc.fill=GridBagConstraints.NONE;
        add(importBtn,gc);
        importBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clicked = (JButton)actionEvent.getSource();
        ChoosePanelEvent e = new ChoosePanelEvent(clicked,this.pathField);
        try {
            choosePanelListener.eventPerformed(e);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public String getPathFieldText() {
        return pathField.getText();
    }

    public void setChoosePanelListener(ChoosePanelListener choosePanelListener) {
        this.choosePanelListener = choosePanelListener;
    }
}
