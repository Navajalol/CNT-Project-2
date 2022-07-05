// Display the results of queries against the bikes table in the bikedb database.
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.x.protobuf.MysqlxPrepare.Execute;

import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import java.sql.*;


public class DisplayQueryResults extends JPanel
{
   // default query retrieves all data from bikes table
   static final String DEFAULT_QUERY = "SELECT * FROM bikes";
   private String propertyItems[] = {"root.properties", "client.properties"};
   private String[] UrlItems = {"jdbc:mysql://localhost:3306/project2?useTimezone=true&serverTimezone=UTC",
                                 "jdbc:mysql://localhost:3306/project2?useTimezone=true&serverTimezone=UTC"};
   private ResultSetTableModel tableModel;
   private JTextArea queryArea;
   private JButton dataButton, clearQuery, ExecuteButton, ClearWindow; 
   private JLabel  StatusLabel, WindowLabel; 
   private JTextArea textQuery; 
   private JComboBox UrlCombo; 
   private JComboBox propertiesCombo;
   private JPasswordField passwordText; 
   private Connection connect; 
   private DefaultTableModel Empty; 
   private JTable resultTable; 
   private JLabel queryLabel, dbInfoLabel, jbdcLabel, UrlLabel, UserLabel, passwordLabel; 
   private JTextField userText;
   private Font font;  
   
   // create ResultSetTableModel and GUI
   /**
    * 
    */
   public DisplayQueryResults() 
   {   
      //super( "Displaying Query Results" );
        
      // create ResultSetTableModel and display database table
    
         // create TableModel for results of query SELECT * FROM bikes
         dataButton = new JButton("Connect to Database");
         clearQuery = new JButton("Clear SQL command"); 
         ExecuteButton = new JButton("Execute SQL command");
         ClearWindow = new JButton("Clear Result Window");
         queryLabel = new JLabel();
         queryLabel.setForeground(Color.BLUE); 
         dbInfoLabel = new JLabel(); 
         jbdcLabel = new JLabel("Propertie File"); 
         jbdcLabel.setOpaque(true);
         jbdcLabel.setBackground(Color.LIGHT_GRAY);
         jbdcLabel.setForeground(Color.BLACK);
      
         UserLabel = new JLabel("Username");
         UserLabel.setOpaque(true);
         UserLabel.setBackground(Color.LIGHT_GRAY);
         UserLabel.setForeground(Color.BLACK); 

         passwordLabel = new JLabel ("Password"); 
         passwordLabel.setOpaque(true);
         passwordLabel.setBackground(Color.LIGHT_GRAY);
         passwordLabel.setForeground(Color.BLACK);

         textQuery = new JTextArea(5, 5);
         propertiesCombo = new JComboBox(propertyItems);

         userText = new JTextField("", 10); 
         passwordText = new JPasswordField("", 10);

         StatusLabel = new JLabel("No connection Now"); 
         StatusLabel.setOpaque(true);
         StatusLabel.setBackground(Color.RED);
         StatusLabel.setForeground(Color.BLACK);

         WindowLabel = new JLabel(); 
         WindowLabel.setForeground(Color.BLUE);

         WindowLabel.setText("SQL Exceution Result Window");

         resultTable = new JTable(); 
         Empty = new DefaultTableModel(); 
         setPreferredSize(new DimensionUIResource(905, 520));
         setLayout(null);
         final Box square = Box.createHorizontalBox();
         square.add(new JScrollPane(resultTable)); 
         Box sqlSquare = Box.createHorizontalBox();
         sqlSquare.add(new JScrollPane(textQuery));
         sqlSquare.setOpaque(true);
         resultTable.setEnabled(false);
         resultTable.setGridColor(Color.BLACK);
         
      

         dataButton.setBounds(20,150,165,25); 
         dataButton.setBackground(Color.BLUE);
         dataButton.setForeground(Color.YELLOW);
         dataButton.setBorderPainted(false);
         dataButton.setOpaque(true);

         clearQuery.setBounds(480, 150, 175, 25); 
         clearQuery.setForeground(Color.RED);
         clearQuery.setBackground(Color.WHITE);
         clearQuery.setOpaque(true);
         clearQuery.setBorderPainted(false);

         ExecuteButton.setBounds(695, 150, 175, 25);
         ExecuteButton.setBackground(Color.GREEN);
         ExecuteButton.setBorderPainted(false);
         ExecuteButton.setOpaque(true);

         ClearWindow.setBounds(25, 480, 168, 25);
         ClearWindow.setBackground(Color.YELLOW);
         ClearWindow.setBorderPainted(false);
         ClearWindow.setOpaque(true);

         
         dbInfoLabel.setBounds(10, 0, 300, 30); 
         dbInfoLabel.setForeground(Color.BLUE);
         jbdcLabel.setBounds(10,30,125,30); 

         UserLabel.setBounds(10,68,125,30);
         passwordLabel.setBounds(10,106,125,30);

         StatusLabel.setBounds(20,187,850,25);
         WindowLabel.setBounds(45,231,220,25); 

         square.setBounds(45, 224, 841, 220);
         queryLabel.setBounds(450,0, 215, 25);

         sqlSquare.setBounds(450, 22, 438, 125);
         propertiesCombo.setBounds(135, 30, 290, 30); 
         userText.setBounds(135, 68, 290, 30); 
         passwordText.setBounds(135, 106, 290, 30);

         add(dataButton); 
         add (clearQuery); 
         add (ExecuteButton); 
         add (ClearWindow); 
         add (queryLabel);
         add (sqlSquare); 
         add (dbInfoLabel); 
         add (jbdcLabel); 
         add (UserLabel); 
         add (passwordLabel);
         add (StatusLabel); 
         add (WindowLabel); 
         add (userText);
         add (passwordText);
         add (square); 
         add (propertiesCombo);
         //add(textQuery);

         //Window size and layout
      
         //setting up components with boudns 
         // ConnectButton.setBounds(20,187,165,25); 
         //ConnectButton.setBounds()

/* 
         //Define user entry areas and result return areas
         // set up JTextArea in which user types queries
		//	queryArea = new JTextArea( 3, 100);
         queryArea = new JTextArea( DEFAULT_QUERY, 3, 100 );
         queryArea.setBounds(100,100,600,420); 
         queryArea.setWrapStyleWord( true );
         queryArea.setLineWrap( true );
         
         JScrollPane scrollPane = new JScrollPane( queryArea,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
         
         // set up JButton for submitting queries
         JButton submitButton = new JButton( "Submit Query" );
         submitButton.setBackground(Color.BLUE);
         submitButton.setForeground(Color.YELLOW);
         submitButton.setBorderPainted(false);
         submitButton.setOpaque(true);
         submitButton.setBounds(300,300,100,100);


         dataButton = new JButton("Connect to Database");
         dataButton.setBackground(Color.BLUE);
         dataButton.setForeground(Color.YELLOW);
         dataButton.setBorderPainted(false);
         dataButton.setOpaque(true);
         //dataButton.setBounds();
         
         // create Box to manage placement of queryArea and 
         // submitButton in GUI
         Box box = Box.createHorizontalBox();
         box.add( scrollPane );
         box.add( submitButton );
         box.add(dataButton); 

         // create JTable delegate for tableModel 
         JTable resultTable = new JTable( tableModel );
         resultTable.setGridColor(Color.BLACK);
         
         // place GUI components on content pane
         add( box, BorderLayout.NORTH);
         add( new JScrollPane( resultTable ), BorderLayout.CENTER );
         // create event listener for submitButton
         */

        dataButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent event){
            boolean usernameMismatch;
            boolean passwordMismatch; 
            boolean userCredentialsOK = false; 
           try{
           
            if(connect != null){
               connect.close(); 
            }
            StatusLabel.setText("No connection Now"); 
             
            Properties properties = new Properties();
            FileInputStream filein = null;
            MysqlDataSource dataSource = null; 
            try{
               //set details for properties object and file objet. ALso MysqlDataSource object. e
               //open properties file
               filein = new FileInputStream((String) propertiesCombo.getSelectedItem()); 
               properties.load(filein);
               dataSource = new MysqlDataSource();
               dataSource.setUrl(properties.getProperty("MYSQL_DB_URL"));
               if(userText.getText().equals((String)properties.getProperty("MYSQL_DB_USERNAME"))){
                  usernameMismatch = false;
                  userCredentialsOK = true; 
               }
               if(passwordText.getPassword().equals((String)properties.getProperty("MYSQL_DB_PASSWORD"))){
                  passwordMismatch = false;
                  userCredentialsOK = true; 
               }
               if(userCredentialsOK){
                  dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
                  dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));
                  connect = dataSource.getConnection();
                  StatusLabel.setText("Connected to " + (String)properties.getProperty("MYSQL_DB_URL"));


               }
               else{
                  StatusLabel.setText("NOT CONNECTED - User Credentials Do No Match Properties File!");
               }
               
            } //end try
            catch(SQLException e){
               JOptionPane.showMessageDialog(null, e.getMessage(), "Databse error", JOptionPane.ERROR_MESSAGE);
            }//end catch
         }//end try
         catch(IOException e ){
            JOptionPane.showMessageDialog(null, e.getMessage(), "databse error", JOptionPane.ERROR_MESSAGE);

         }
         catch(SQLException e ){
            JOptionPane.showMessageDialog(null, e.getMessage(), "databse error", JOptionPane.ERROR_MESSAGE); 
         }
         }
        });

        ClearWindow.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent event){
            resultTable.setModel(Empty); 
         }
        });

        clearQuery.addActionListener(new ActionListener(){
         public void actionPerformed( ActionEvent event){
            textQuery.setText("");
         }
        });


        ExecuteButton.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent event){
            try{
              // actiave result table
               resultTable.setEnabled(true); 
               //scrolling
                resultTable.setAutoscrolls(true);

                tableModel = new ResultSetTableModel(connect, textQuery.getText()); 

                //IF select statement is used, use ExecuteQuery()
                //All other command types will use ExecuteUpdate() from the ResultsetTableMode
                //New window will pop up with message for user that does not have perission

                if(textQuery.getText().toUpperCase().contains("SELECT")){
                  tableModel.setQuery(textQuery.getText());
                  resultTable.setModel(tableModel);
                }
                else{
                  tableModel.setUpdate(textQuery.getText());

                }
               }
            catch(SQLException e){
               JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error" , JOptionPane.ERROR_MESSAGE );
            }
            catch(ClassNotFoundException NotFound) {
               JOptionPane.showMessageDialog(null, "Mysql Driver not found","DriverNot found", JOptionPane.ERROR_MESSAGE );
            }
         }
        }
        );
      } 
/* 
         submitButton.addActionListener( 
         
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery( queryArea.getText() );
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery( DEFAULT_QUERY );
                        queryArea.setText( DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener

         setSize( 1000, 500 ); // set window size
         setVisible( true ); // display window  
      } // end try
      catch ( ClassNotFoundException classNotFound ) 
      {
         JOptionPane.showMessageDialog( null, 
            "MySQL driver not found", "Driver not found",
            JOptionPane.ERROR_MESSAGE );
         
         System.exit( 1 ); // terminate application
      } // end catch
      catch ( SQLException sqlException ) 
      {
         JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
            "Database error", JOptionPane.ERROR_MESSAGE );
               
         // ensure database connection is closed
         tableModel.disconnectFromDatabase();
         
         System.exit( 1 );   // terminate application
      } // end catch
      
      // dispose of window when user quits application (this overrides
      // the default of HIDE_ON_CLOSE)
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      // ensure database connection is closed when user quits application
      addWindowListener(new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end DisplayQueryResults constructor
   */
   // execute application
   public static void main( String args[] ) 
   {
      new DisplayQueryResults();    
      JFrame frame = new JFrame("SQL CLIENT APP VERSION 5.0 - (mjl - CNT 4714 - SUMMER 2022 - PROJECT 2 ");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(new DisplayQueryResults(), BorderLayout.CENTER);
      frame.pack(); 
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   } // end main
} // end class DisplayQueryResults




