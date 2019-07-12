package gui;

public class WordCountTool {
    public static void main(String[] args)
    {
        Splash splash = new Splash();
        splash.setVisible(true);
        try {
            for(int i = 0;i<=100;i++)
            {
                Thread.sleep(35);
                splash.percentDisplayLabel.setText(Integer.toString(i) + "%");
                splash.progressBar.setValue(i);
                if(i == 100)
                {
                    splash.setVisible(false);
                    MainFrame mainframe = new MainFrame();
                    mainframe.setVisible(true);
                    //return;
                }
            }
        } catch (Exception e) {
        }
    }
}
