package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

interface ProcessPanelListener{
    void eventPerformed(ProcessPanelEvent e);
}

class ProcessPanelEvent extends EventObject {
    private JTextField countField;
    private String algo;

    public ProcessPanelEvent(Object source,JTextField countField,String algo) {
        super(source);
        this.countField=countField;
        this.algo=algo;
    }

    public void setCountField(int count) {
        this.countField.setText(Integer.toString(count));
    }

    public String getAlgo() {
        return algo;
    }
}

public class ProcessPanel extends JPanel implements ActionListener {
    private JLabel countLabel;
    private JTextField countField;
    private JButton countBtn;
    private JLabel searchLabel;
    private JComboBox searchCombo;
    private JButton searchBtn;
    private ProcessPanelListener processPanelListener;

    ProcessPanel(){
        countLabel = new JLabel("Total Words:");
        countLabel.setForeground(Color.green);
        countLabel.setFont(new Font("Serif",Font.BOLD,14));
        countField = new JTextField(5);
        countField.setBackground(Color.GRAY);
        countField.setForeground(Color.BLACK);
        countField.setPreferredSize(new Dimension(10,25));
        countField.setFont(new Font("Serif",Font.BOLD,12));
        countField.setEditable(false);
        countBtn = new JButton("Count");
        countBtn.setActionCommand("Count");
        countBtn.addActionListener(this);
        countBtn.setForeground(Color.green);
        countBtn.setBackground(Color.DARK_GRAY);
        searchLabel = new JLabel("Search Most Frequent Words:");
        searchLabel.setFont(new Font("Serif",Font.BOLD,14));
        searchLabel.setForeground(Color.green);
        searchCombo = new JComboBox();
        searchCombo.setBackground(Color.GRAY);
        searchCombo.setForeground(Color.BLACK);
        searchBtn = new JButton("Result");
        searchBtn.addActionListener(this);
        searchBtn.setActionCommand("Result");
        searchBtn.setBackground(Color.DARK_GRAY);
        searchBtn.setForeground(Color.green);

        //ComboBox
        DefaultComboBoxModel searchModel = new DefaultComboBoxModel();
        searchModel.addElement("By ArrayList");
        searchModel.addElement("By PriorityQueue");
        searchModel.addElement("By TreeMap");
        searchCombo.setModel(searchModel);

        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEtchedBorder());

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(Box.createRigidArea(new Dimension(SwingConstants.HORIZONTAL,140)));
        add(countLabel);
        add(countField);
        add(countBtn);
        add(Box.createRigidArea(new Dimension(115,0)));
        add(searchLabel);
        add(searchCombo);
        add(searchBtn);
        countBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton clicked = (JButton)actionEvent.getSource();
        String algo = (String)searchCombo.getSelectedItem();

        ProcessPanelEvent e = new ProcessPanelEvent(clicked,this.countField,algo);

        this.processPanelListener.eventPerformed(e);
        
    }

    public void setProcessPanelListener(ProcessPanelListener processPanelListener) {
        this.processPanelListener = processPanelListener;
    }

    public void setCountField(String Text) {
        this.countField.setText(Text);
    }
}
