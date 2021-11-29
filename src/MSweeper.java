import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class MSweeper {
	
	private Game game;
    private JPanel panel;
    private JLabel label;
    private JFrame jframe;
    private JMenuBar menuBar;
    private int colsField; 
    private int rowsField; 
    private int bombs; 
    private final int IMAGE_SIZE = 30;
    
    MSweeper () {
    	
    	setAndStartGame(9, 9, 10);
    } 
    
    private void setAndStartGame (int colsField, int rowsField, int bombs) {
    	
    	this.colsField = colsField;
    	this.rowsField = rowsField;
    	this.bombs = bombs;  		
    	game = new Game (colsField, rowsField, bombs);
    	game.start();
    	setImages();
    	initFrame();
    }
       
    private void initMenuBar() {
    	
    	menuBar = new JMenuBar();
        JMenu options = new JMenu("Options");
        menuBar.add(options);
        JMenuItem beginner = new JMenuItem("Beginner");
        beginner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setAndStartGame(9, 9, 10);
			}
		});
        
        options.add(beginner);
        
        JMenuItem middle = new JMenuItem("Middle");
        middle.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		setAndStartGame(16, 16, 40);
        	}
        });
        options.add(middle);
        
        JMenuItem advanced = new JMenuItem("Advanced");
        advanced.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		setAndStartGame(30, 16, 100);
        	}
        });
        options.add(advanced);
        
        
        JMenuItem custom = new JMenuItem("Custom");
        custom.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		
        		JDialog cgame = new JDialog(jframe, "Custom", true);
        		cgame.setSize(270, 220);
        		cgame.setIconImage(getImage("icon"));
        		cgame.setLocation(jframe.getX() + 20, 
        				jframe.getY() + 20);JPanel panel = new JPanel();
             
                SpringLayout layout = new SpringLayout();
                cgame.setLayout(layout);
                Font font = new Font(Font.DIALOG, Font.PLAIN, 14);
                Font font1 = new Font(Font.DIALOG, Font.PLAIN, 20);
                
                JLabel label = new JLabel("Set Width field  (5 - 35)");
                JTextField field = new JTextField(3);
                
                JLabel label1 = new JLabel("Set Height field  (5 - 25)");
                JTextField field1 = new JTextField(3);
                
                JLabel label2 = new JLabel("Set Bombs  (5 - 700)");
                JTextField field2 = new JTextField(3);
                
                JButton butCancel = new JButton("Cancel");
                JButton butOk = new JButton("Set");
                
                label.setFont(font);
                field.setFont(font1);
                label1.setFont(font);
                field1.setFont(font1);
                label2.setFont(font);
                field2.setFont(font1);
                
                cgame.add(label);
                cgame.add(field);
                cgame.add(label1);
                cgame.add(field1);
                cgame.add(label2);
                cgame.add(field2);
                cgame.add(butCancel);
                cgame.add(butOk);
                
                layout.putConstraint(SpringLayout.WEST , label, 20, 
                		SpringLayout.WEST , cgame);
                layout.putConstraint(SpringLayout.NORTH, label, 20, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.NORTH, field, 15, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.WEST , field, 20, 
                		SpringLayout.EAST , label);
                
                layout.putConstraint(SpringLayout.WEST , label1, 20, 
                		SpringLayout.WEST , cgame);
                layout.putConstraint(SpringLayout.NORTH, label1, 60, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.NORTH, field1, 55, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.WEST , field1, 20, 
                		SpringLayout.EAST , label);
                
                layout.putConstraint(SpringLayout.WEST , label2, 20, 
                		SpringLayout.WEST , cgame);
                layout.putConstraint(SpringLayout.NORTH, label2, 100, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.NORTH, field2, 95, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.WEST , field2, 20, 
                		SpringLayout.EAST , label);
                
                layout.putConstraint(SpringLayout.WEST , butCancel, 92, 
                		SpringLayout.WEST , cgame);
                layout.putConstraint(SpringLayout.NORTH, butCancel, 140, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.NORTH, butOk, 140, 
                		SpringLayout.NORTH, cgame);
                layout.putConstraint(SpringLayout.WEST , butOk, 20, 
                		SpringLayout.EAST , butCancel);
                
                butOk.addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	String sfield = field.getText().replaceAll("[^0-9]", "");
                    	String sfield1 = field1.getText().replaceAll("[^0-9]", "");
                    	String sfield2 = field2.getText().replaceAll("[^0-9]", "");
                    	
                    	int ia = 0;
                    	int ib = 0;
                    	int ic = 0;
                    	
                    	if (sfield.length() * sfield1.length() * sfield2.length() != 0) {
                    		ia = Integer.parseInt(sfield);
                    		if (ia < 5) ia = 5;
                    		if (ia > 25) ia = 35;
                    		field.setText("" + ia);
                    	
                    		ib = Integer.parseInt(sfield1);
                    		if (ib < 5) ib = 5;
                    		if (ib > 35) ib = 35;
                    		field1.setText("" + ib);
                    	
                    		ic = Integer.parseInt(sfield2);
                    		if (ic < 5) ic = 5;
                    		if (ic > ia * ib) ic = ia * ib / 3 * 2;
                    		if (ic > 700) ic = 700;
                    		field2.setText("" + ic);
                    	}

                    	if(ia * ib * ic != 0) {
                    		
                    		setAndStartGame(ia, ib, ic);
                    	}
                    }
                });
                
                butCancel.addActionListener(new ActionListener() {
                	
                	@Override
                	public void actionPerformed(ActionEvent e) {
                		
                		cgame.dispose();
                	}
                });

                cgame.repaint();
                cgame.setVisible(true); 
        	}
        });
        
        options.add(custom);
    }
    
    private void initPanel () {
    	
        panel = new JPanel() 	
        {
            @Override
            protected void paintComponent(Graphics g) {
            	
                super.paintComponent(g);
                for(Coord coord : Ranges.getAllCoords()) {
                	
                	g.drawImage((Image) game.getBox(coord).image,
                			coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };
        
        panel.setPreferredSize(new Dimension(colsField * IMAGE_SIZE,
        		rowsField * IMAGE_SIZE));
        
        panel.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
        		
        		int x = e.getX() / IMAGE_SIZE;
        		int y = e.getY() / IMAGE_SIZE;
        		
        		//
        		System.out.println("ML " + x + " " + y);
        		Coord coord = new Coord(x, y);
        		
        		if (e.getButton() == MouseEvent.BUTTON1) { // left mouse button
        			
        			game.pressLeftButton (coord);
        		}
        		
        		if (e.getButton() == MouseEvent.BUTTON3) { // right mouse button
                	
        			game.pressRightButton (coord);
        		}
        		
        		if (e.getButton() == MouseEvent.BUTTON2) { // middle mouse button
                	
        			game.start (); 		// restart game
        		}
        		
        		label.setText(getMessage());
        		
        		panel.repaint(); 	// after each mouse action, redraw the game panel
        	}
		});
    }

    private String getMessage() {
    	
        switch (game.getState())
        {
        	case PLAYED: return " " + (game.getTotalBombs() - game.getCountOfFlags()) + " bombs left!";
            case BOMBED:return "BOOOOOOM!!!";
            case WINNER: return "Congratulations!!!";
            default: return "";
        }
    }

    private void initFrame () {
		
		int xPosition = 0;
    	int yPosition = 0;
    	// Get Display resolution
    	int wScreen = Toolkit.getDefaultToolkit().getScreenSize().width;
    	int hScreen = Toolkit.getDefaultToolkit().getScreenSize().height; 

    	if(jframe != null) {
    		
    		xPosition = jframe.getX();
        	yPosition = jframe.getY();
    		
    		jframe.dispose();
    	}
    	
    	jframe = new JFrame();
    	jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	jframe.setTitle("MSweeper (remake)");
    	jframe.setSize(colsField * IMAGE_SIZE + 20, rowsField * IMAGE_SIZE + 90);
    	jframe.setIconImage(getImage("icon"));
    	jframe.setLayout(new FlowLayout());
    	initMenuBar();
    	initPanel();
    	jframe.setJMenuBar(menuBar);
    	jframe.add(panel);
    	
    	label = new JLabel(getMessage());
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(colsField * IMAGE_SIZE, 17));
    	jframe.add(label);
    	
    	if(wScreen - xPosition < colsField * IMAGE_SIZE) {
    		xPosition = wScreen - (colsField * IMAGE_SIZE + 50);
    	}
    	if(hScreen - yPosition < rowsField * IMAGE_SIZE + 200) {
    		yPosition = hScreen - (rowsField * IMAGE_SIZE + 150);
    	} 
    	
    	if (xPosition + yPosition != 0) {
    		jframe.setLocation(xPosition, yPosition);
    	}
    	else {
    		jframe.setLocationRelativeTo(null);
    	}
    	
    	jframe.setResizable(false);
    	jframe.setVisible(true);
    }

    private void setImages () {				// Initialize Enum Box
    	
        for (Box box : Box.values())		// Box.values() return Box[]
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name) { 
    	
        String filename = "/img30/" + name + ".png";
        ImageIcon icon = new ImageIcon(MSweeper.class.getResource(filename));  // use resources (set resource folder)
        return icon.getImage();
	}
}