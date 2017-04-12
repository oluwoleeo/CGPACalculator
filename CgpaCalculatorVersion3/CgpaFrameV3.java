import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JColorChooser;
import java.sql.Timestamp;
import java.io.File;
import java.io.PrintWriter;

public class CgpaFrameV3 extends JFrame
 {
  String candName;
  Timestamp time;
  PrintWriter output;
  JPanel p;
  JButton b[];
  JLabel l[];
  JTextField t[];
  JTextField t1;
  JTextField t2;
  
  int u[], g[]; //Arrays of course unit and grade
  String c[]; //Array of course code
  
  int x = 0; //Previous numerator to be added to current numerator for Cgpa calculation
  
  int y = 0; //Previous denominator to be added to current denominator for Cgpa calculation
  
  int sem = 1; //Current semester
  int ses = 1; //Current session
  int counter= 0; //Used for incrementing the session
  
  public void openFile() throws Exception
	{
	  long s = System.currentTimeMillis();
	  
	  time = new Timestamp(s);
	  String ti = time.toString().replaceAll("[.:-]","");
	  
	  File fileName = new File("Results Summary- " + candName + " " + ti + ".txt");
	  
	  output = new PrintWriter(fileName);
	  
	  fileName.setReadOnly();
	}
	
   public void head()
	{
	 output.println("Results Summary for " + candName);
	 output.println("Timestamp:\t" + time);
	}
	
   public void closeFile()
	 {
	  if ( output != null )
	   output.close();
	 }
  
  public CgpaFrameV3()
   {
    super ("Ogundele Oluwole's CGPA Calculator");
	p = new JPanel();
	p.setLayout(null);
	
	p.setBackground(JColorChooser.showDialog(CgpaFrameV3.this, "Select favourite color", getBackground()));
	
	candName = JOptionPane.showInputDialog(CgpaFrameV3.this, "Enter candidate's name", "Candidate name", JOptionPane.QUESTION_MESSAGE);
	
	t = new JTextField[30];
	l = new JLabel[5];
	b = new JButton[3];
	
    for (int i = 0; i < t.length; ++i)
	 t[i] = new JTextField();
	
	l[0] = new JLabel("Course");
	l[1] = new JLabel("Unit");
	l[2] = new JLabel("Grade");
	
	//Positioning the components on the GUI
	l[0].setBounds(100,0,50,25);
	l[1].setBounds(175,0,50,25);
	l[2].setBounds(250,0,50,25);
	
	int a = 0, k = 0;
	
	for (int i = 0; i < 10; ++i)
	 {
	  int j = 0;
	  
	  for (int b = a; b < a + 3;++b)
	   {
	    t[b].setBounds(100+j,25+k,50,25);
	    j = 75*((b%3)+1);
	   }
	   
	   k += 50;
	   a += 3;
	 } 
	 
	l[3] = new JLabel("Semester GPA");
	l[3].setBounds(100,525,100,25);
	l[4] = new JLabel("Cumulative GPA");
	l[4].setBounds(300,525,125,25);
	t1 = new JTextField();
	t1.setBounds(185,525,50,25);
	t2 = new JTextField();
	t2.setBounds(405,525,50,25);
	b[0] = new JButton("Calculate");
	b[0].setBounds(250,550,100,25);
	b[1] = new JButton("Continue");
	b[1].setBounds(100,600,100,25);
	b[2] = new JButton("Exit");
	b[2].setBounds(350,600,100,25);
	
	t1.setEditable(false);
	t2.setEditable(false);
	
	b[0].addActionListener(
	 new ActionListener()
	  {
	   public void actionPerformed( ActionEvent e )
	    {
		 output.println();
		 output.println();
		 output.println();
		 output.println("Session: " + ses + "\t\tSemester: " + sem);
		 output.println("Course Code\t\t\tUnit\t\tGrade");
		 
		 c = new String[10];
		 u = new int[10];
		 g = new int[10];
		 
		 
		   for (int i = 0; i < u.length; ++i)
		    {
			 try
		      {
		       u[i] = 0;
		       g[i] = 0;
		   
		       c[i] = t[3*i].getText();
		   
		       u[i] = Integer.parseInt(t[1 + (3*i)].getText());
			
		       g[i] = Integer.parseInt(t[2 + (3*i)].getText());
			
		       if (t[1 + (3*i)].getText() != "")
			   output.println("\t" + c[i] +"\t\t\t\t" + u[i] + "\t\t\t" + g[i]);
		      }
			 catch (NumberFormatException nFE)
		      {
			  }
		    }
		 
		 CgpaCalcV3 cal = new CgpaCalcV3(u,g,x,y);
		 
		 t1.setText(String.format("%.2f",cal.calcSgpa()));
		 t2.setText(String.format("%.2f",cal.calcCgpa()));
		 	 
		 String sgpa = String.format("%.2f", cal.calcSgpa());
		 String cgpa = String.format("%.2f", cal.calcCgpa());
		 
	     output.println();
		 output.println("Semester Grade Point Average: " + sgpa);
		 output.println("Cumulative Grade Point Average: " + cgpa);
		 output.println();
		 
		 x = cal.getNum();
		 y = cal.getDen();
		 
		 for (int i = 0; i < t.length; ++i)
		  t[i].setEditable(false);
		  
		 b[0].setEnabled(false);
	    }
	  }
	 );
   
   b[1].addActionListener(
	 new ActionListener()
	  {
	   public void actionPerformed( ActionEvent e )
	    {
		 b[0].setEnabled(true);
		 
		 for (int i = 0; i < t.length; ++i)
		  t[i].setEditable(true);
		 
		 for (int i = 0; i < t.length; ++i)
		  {
		   t[i].selectAll();
		   t[i].cut();
		  }
		  
		  if (sem%2==0)
		   sem=1;
		  else
		  ++ sem;
		  
		  ++ counter;
		  if (counter%2==0)
		  ++ses;
		}
	  }
	 );
   
   b[2].addActionListener(
	 new ActionListener()
	  {
	   public void actionPerformed( ActionEvent e )
	    {
		 if ( JOptionPane.showConfirmDialog(CgpaFrameV3.this, "Do you want to exit?", "Exit Option",
		 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION )
		  {
		  closeFile();
		  System.exit(0);
		  }
		}
	  }
	 );
	 
    for (int i = 0; i < 3; ++i)
	 p.add(l[i]);
	 
	for (int i = 0; i < t.length; ++i)
	 p.add(t[i]);
	 
	p.add(l[3]);
	p.add(t1);
	p.add(l[4]);
	p.add(t2);
	
	for (int i = 0; i < b.length; ++i)
	 p.add(b[i]);
	 
	add(p);
   }
   
   public static void main( String args[] ) throws Exception
	{
	 CgpaFrameV3 cgpa = new CgpaFrameV3();
	 cgpa.openFile();
	 cgpa.head();
	 
	 cgpa.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	 cgpa.setSize( 600, 700 ); 
	 cgpa.setVisible( true );
	}
 }