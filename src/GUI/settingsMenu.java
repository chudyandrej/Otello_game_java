package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 21/04/16.
 */
public class settingsMenu{

    JPanel p;

    JLabel p1Name = new JLabel("Player1 name");//, JLabel.TRAILING ?
    JTextField p1Field = new JTextField("Player1", 10);
    public String player1Name = "Player1";

    JLabel p2Name = new JLabel("Player2 name");
    JTextField p2Field = new JTextField("Player2", 10);
    public String player2Name = "Player2";

    JLabel botName = new JLabel("Bot name");
    JTextField botField = new JTextField("Computer", 10);
    public String computerName = "Computer";

    JLabel fields = new JLabel("Discs (%)");
    JTextField fieldsT = new JTextField("0", 10);
    public double FDisc = 0;

    JLabel timeChange = new JLabel("Time to change (sec)");
    JTextField timeChangeT = new JTextField("0", 10);
    public int CHTime = 0;

    JLabel frozeTime = new JLabel("Froze time (sec)");
    JTextField frozeTimeT = new JTextField("0", 10);
    public int FTime = 0;

    JButton back = new JButton("Confirm");

    private JFrame frame;
    private GUI.OthelloGUI.BackgroundPane bg;
    private Box mainMenu;

    public JPanel getMenu(JFrame frame, GUI.OthelloGUI.BackgroundPane bg, Box mainMenu){
        this.frame = frame;
        this.bg = bg;
        this.mainMenu = mainMenu;

        p = new JPanel(new GridBagLayout());
        p.setOpaque(false);
        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        p1Name.setForeground(Color.white);
        p1Name.setPreferredSize(new Dimension(50,20));
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        p.add(p1Name, cs);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        p.add(p1Field, cs);

        p2Name.setForeground(Color.white);
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        p.add(p2Name, cs);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        p.add(p2Field, cs);

        botName.setForeground(Color.white);
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        p.add(botName, cs);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        p.add(botField, cs);

        fields.setForeground(Color.white);
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        p.add(fields, cs);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        p.add(fieldsT, cs);

        timeChange.setForeground(Color.white);
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        p.add(timeChange, cs);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        p.add(timeChangeT, cs);

        frozeTime.setForeground(Color.white);
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 1;
        p.add(frozeTime, cs);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 2;
        p.add(frozeTimeT, cs);

        cs.gridx = 0;
        cs.gridy = 6;
        cs.gridwidth = 4;
        p.add(new JLabel(" "), cs);

        cs.gridx = 0;
        cs.gridy = 7;
        cs.gridwidth = 4;
        p.add(back, cs);
        back.addActionListener(new settingsBackClicked());


        return p;
    }

    private class settingsBackClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            player1Name = p1Field.getText();
            player2Name = p2Field.getText();
            computerName = botField.getText();

            FDisc = Double.parseDouble(fieldsT.getText());
            CHTime = Integer.parseInt(timeChangeT.getText());
            FTime = Integer.parseInt(frozeTimeT.getText());

            if(FDisc > 100 || FDisc < 0 || CHTime > 100 || CHTime < 0 || FTime > 100 || FTime < 0){
                JOptionPane.showMessageDialog(frame, "WRONG value, time and percents have to be in interval <0, 100>");
            }else{
                bg.remove(p);
                bg.add(mainMenu);
                bg.validate();
                bg.repaint();
            }
        }
    }
}
