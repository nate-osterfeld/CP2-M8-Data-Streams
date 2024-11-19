import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Frame extends JFrame {

    JPanel mainPnl;
    JPanel searchPnl;
    JPanel displayPnl;
    JPanel controlPnl;

    JPanel leftDisplayPnl;
    JPanel rightDisplayPnl;

    JTextArea displayTA1;
    JScrollPane scroller1;
    JTextArea displayTA2;
    JScrollPane scroller2;

    JButton pickBtn;
    JButton quitBtn;
    JButton searchBtn;

    JTextField searchBar;

    JLabel searchLbl;

    File f;
    StringBuilder sb;

    public Frame() {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createSearchPanel();
        mainPnl.add(searchPnl, BorderLayout.NORTH);

        displayPnl = new JPanel();
        displayPnl.setLayout(new BorderLayout());
        mainPnl.add(displayPnl, BorderLayout.CENTER);

        createLeftDisplayPanel();
        displayPnl.add(leftDisplayPnl, BorderLayout.WEST);

        createRightDisplayPanel();
        displayPnl.add(rightDisplayPnl, BorderLayout.EAST);

        createControlPanel();
        mainPnl.add(controlPnl, BorderLayout.SOUTH);

        add(mainPnl);
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void createSearchPanel() {
        searchPnl = new JPanel();

        searchLbl = new JLabel("Search Bar");
        searchPnl.add(searchLbl);

        searchBar = new JTextField(10);
        searchPnl.add(searchBar);

        searchBtn = new JButton("Search");
        searchPnl.add(searchBtn);

        searchBtn.addActionListener((ActionEvent ae) -> {

            String sr = searchBar.getText();
            sb.delete(0, sb.length());
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = "";
                line = br.readLine();
                while(line!=null)
                {
                    String h[] = line.split(" ");
                    int len = h.length;

                    /*
                    List<String> words = Arrays.asList(h);
                    displayTA2.append(
                            String.valueOf(
                                    words.stream()
                                            .filter(i -> i.equalsIgnoreCase(sr))
                            )
                    );
                    */

                    for(int i=0;i<len;i++)
                    {
                        if(h[i].equalsIgnoreCase(sr))
                        {
                            sb.append(line);
                            sb.append("\n");
                        }
                    }
                    line = br.readLine();
                }
                displayTA2.append(sb.toString());
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }
        });
    }

    private void createLeftDisplayPanel() {
        leftDisplayPnl = new JPanel();
        displayTA1 = new JTextArea(60,40);
        displayTA1.setEditable(false);
        scroller1 = new JScrollPane(displayTA1);
        leftDisplayPnl.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        leftDisplayPnl.add(scroller1);
    }

    private void createRightDisplayPanel() {
        rightDisplayPnl = new JPanel();
        displayTA2 = new JTextArea(60,40);
        displayTA2.setEditable(false);
        scroller2 = new JScrollPane(displayTA2);
        rightDisplayPnl.setFont(new Font("Comic Sans MS", Font.PLAIN, 10));
        rightDisplayPnl.add(scroller2);
    }

    private void createControlPanel() {
        controlPnl = new JPanel();
        controlPnl.setLayout(new GridLayout(1,3));

        pickBtn = new JButton("Choose File");
        quitBtn = new JButton("Quit");

        controlPnl.add(pickBtn);
        controlPnl.add(quitBtn);

        pickBtn.addActionListener((ActionEvent ae) -> {

            JFileChooser chooser = new JFileChooser();

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                f = chooser.getSelectedFile();
                try
                {
                    BufferedReader br=new BufferedReader(new FileReader(f));
                    sb = new StringBuilder();
                    String line="";
                    line=br.readLine();
                    while(line!=null)
                    {
                        sb.append(line);
                        sb.append("\n");
                        line=br.readLine();
                    }
                    displayTA1.append(sb.toString());
                }
                catch(Exception e)
                {
                    System.err.println(e.toString());
                }
            }
            else {

            }
        });

        quitBtn.addActionListener((ActionEvent ae) -> System.exit(0));
    }
}