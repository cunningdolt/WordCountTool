package gui;
import controller.Controller;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
public class MainFrame extends JFrame {
    private ChoosePanel choosePanel;
    private ProcessPanel processPanel;
    private TextPanel textPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    MainFrame(){
        setTitle("Word Count Tool");
        choosePanel = new ChoosePanel();
        choosePanel.setPreferredSize(new Dimension(900,80));
        processPanel = new ProcessPanel();
        processPanel.setPreferredSize(new Dimension(900,172));
        textPanel = new TextPanel();
        fileChooser = new JFileChooser();
        controller = new Controller();
        setLayout(new BorderLayout());

        add(choosePanel,BorderLayout.NORTH);
        add(processPanel,BorderLayout.SOUTH);
        add(textPanel,BorderLayout.CENTER);

        choosePanel.setChoosePanelListener(new ChoosePanelListener() {
            @Override
            public void eventPerformed(ChoosePanelEvent e) throws IOException {
                JButton clicked = (JButton)e.getSource();
                if ((clicked.getActionCommand()).equals("Import")) {
                    if(fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION) {
                        e.setPathField(fileChooser.getSelectedFile().toString());
                        processPanel.setCountField("");
                        controller.removeHighlights(textPanel.getTextArea());
                        if (controller.loadFromFile(fileChooser.getSelectedFile(),MainFrame.this))
                            controller.displayPage(fileChooser.getSelectedFile().toString(),textPanel.getTextArea());
                    }
                }
            }
        });

        processPanel.setProcessPanelListener(new ProcessPanelListener() {
            @Override
            public void eventPerformed(ProcessPanelEvent e) {
                JButton clicked = (JButton)e.getSource();
                if ((clicked.getActionCommand()).equals("Count")){
                    if (!choosePanel.getPathFieldText().equals(""))
                        e.setCountField(controller.getCount());
                    else
                        JOptionPane.showMessageDialog(MainFrame.this,"Please select a text file.");
                }
                else if((clicked.getActionCommand()).equals("Result")) {
                    String algo = e.getAlgo();
                    if (algo.equals("By ArrayList")){
                        JOptionPane.showMessageDialog(MainFrame.this,controller.byArrayList());
                        ArrayList<String> topTen = controller.getTopTen();
                        controller.highlight(textPanel.getTextArea(), topTen,textPanel.getTextArea().getText());
                    }
                    else if(algo.equals("By PriorityQueue")){
                        JOptionPane.showMessageDialog(MainFrame.this,controller.byPriorityQueue());
                        ArrayList<String> topTen = controller.getTopTen();
                        controller.highlight(textPanel.getTextArea(), topTen,textPanel.getTextArea().getText());
                    }
                    else if(algo.equals("By TreeMap")){
                        JOptionPane.showMessageDialog(MainFrame.this,controller.byPriorityQueue());
                        ArrayList<String> topTen = controller.getTopTen();
                        controller.highlight(textPanel.getTextArea(), topTen,textPanel.getTextArea().getText());
                    }
                    else
                        JOptionPane.showMessageDialog(MainFrame.this,"?");
                }
            }
        });

        setSize(new Dimension(900,900));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
