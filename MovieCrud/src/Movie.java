import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.*;
public class Movie {
    private JPanel Main;
    private JTextField txtTitle;
    private JTextField txtLanguage;
    private JTextField txtSubtitle;
    private JTextField txtGenre;
    private JTextField txtSinopse;
    private JTextField txtAudience;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtid;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Movie");
        frame.setContentPane(new Movie().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public void connect()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/mscompany", "root","");
            System.out.println("Successs");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    void table_load()
    {
        try
        {
            pst = con.prepareStatement("select * from movie");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Movie() {

        connect();
        table_load();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String title,language,subtitle,genre,sinopse,audience;

                title = txtTitle.getText();
                language = txtLanguage.getText();
                subtitle = txtSubtitle.getText();
                genre = txtGenre.getText();
                sinopse = txtSinopse.getText();
                audience = txtAudience.getText();

                try {
                    pst = con.prepareStatement("insert into movie(title,language,subtitle,genre,sinopse,audience)values(?,?,?,?,?,?)");
                    pst.setString(1, title);
                    pst.setString(2, language);
                    pst.setString(3, subtitle);
                    pst.setString(4, genre);
                    pst.setString(5, sinopse);
                    pst.setString(6, audience);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    table_load();
                    txtTitle.setText("");
                    txtLanguage.setText("");
                    txtSubtitle.setText("");
                    txtGenre.setText("");
                    txtSinopse.setText("");
                    txtAudience.setText("");
                    txtTitle.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {

                    String msid = txtid.getText();

                    pst = con.prepareStatement("select title,language,subtitle,genre,sinopse,audience from movie where id = ?");
                    pst.setString(1, msid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String title = rs.getString(1);
                        String language = rs.getString(2);
                        String subtitle = rs.getString(3);
                        String genre = rs.getString(4);
                        String sinopse = rs.getString(5);
                        String audience = rs.getString(6);

                        txtTitle.setText(title);
                        txtLanguage.setText(language);
                        txtSubtitle.setText(subtitle);
                        txtGenre.setText(genre);
                        txtSinopse.setText(sinopse);
                        txtAudience.setText(audience);

                    }
                    else
                    {
                        txtTitle.setText("");
                        txtLanguage.setText("");
                        txtSubtitle.setText("");
                        txtGenre.setText("");
                        txtSinopse.setText("");
                        txtAudience.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Movie ID Number");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msid,title,language,subtitle,genre,sinopse,audience;
                title = txtTitle.getText();
                language = txtLanguage.getText();
                subtitle = txtSubtitle.getText();
                genre = txtGenre.getText();
                sinopse = txtSinopse.getText();
                audience = txtAudience.getText();
                msid = txtid.getText();

                try {
                    pst = con.prepareStatement("update movie set title = ?,language = ?,subtitle = ? ,genre = ?,sinopse = ?,audience = ?where id = ?");
                    pst.setString(1, title);
                    pst.setString(2, language);
                    pst.setString(3, subtitle);
                    pst.setString(4, genre);
                    pst.setString(5, sinopse);
                    pst.setString(6, audience);
                    pst.setString(7, msid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!");
                    table_load();
                    txtTitle.setText("");
                    txtLanguage.setText("");
                    txtSubtitle.setText("");
                    txtGenre.setText("");
                    txtSinopse.setText("");
                    txtAudience.setText("");
                    txtTitle.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String msid;
                msid = txtid.getText();

                try {
                    pst = con.prepareStatement("delete from movie  where id = ?");

                    pst.setString(1, msid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete!");
                    table_load();
                    txtTitle.setText("");
                    txtLanguage.setText("");
                    txtSubtitle.setText("");
                    txtGenre.setText("");
                    txtSinopse.setText("");
                    txtAudience.setText("");
                    txtTitle.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
