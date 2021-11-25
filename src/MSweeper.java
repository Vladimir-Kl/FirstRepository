import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class MSweeper extends JFrame {
	
	private Game game;
    private JPanel panel;
    private JLabel label;

    private final int COLS = 15; 
    private final int ROWS = 15; 
    private final int BOMBS = 40; 
    private final int IMAGE_SIZE = 30; // image size equals to x and to y


    public static void main(String[] args) {
    	
        new MSweeper();
    	
    }

    private MSweeper () {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImages();        
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel () {
 
        label = new JLabel("Find ALL Bombs!!!");  // 2nd arguments, set label to center position in field
        																// default position is left, !!! I find!!
        																// "Find ALL Bombs!!!" - the init inscription, before first action
        add (label, BorderLayout.SOUTH);
    }

    private void initPanel () {
    	
        panel = new JPanel() 		// display images during initialization
        {
            @Override
            protected void paintComponent(Graphics g) {
            	
                super.paintComponent(g);
                
                for(Coord coord : Ranges.getAllCoords()) {
                	
                	g.drawImage((Image) game.getBox(coord).image,
                			coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE , this);		// cast to Image
                }
            }
        };
        
        panel.addMouseListener(new MouseAdapter() {
        	
        	@Override
        	public void mousePressed(MouseEvent e) {
        		
        		int x = e.getX() / IMAGE_SIZE;
        		int y = e.getY() / IMAGE_SIZE;
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

               	panel.setPreferredSize(new Dimension(Ranges.getSize().x * IMAGE_SIZE,
        		Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getMessage() {
    	
        switch (game.getState())
        {
            case PLAYED: return "Dengerous! Minen!";
            case BOMBED:return "BOOOOOOM!!!";
            case WINNER: return "Congratulations!!!";
            default: return "";
        }
    }

    private void initFrame () {
    	
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("MSweeper (remake MineSweeper2000)");
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack();  // !!!!! need to call after set dimensions and attributes!
        		// method JFrame set window size, sufficient (enough) for visualization
        setLocationRelativeTo(null);
    }

    private void setImages () {				// Initialize Enum Box
    	
        for (Box box : Box.values())		// Box.values() return Box[]
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name) {
    	
        String filename = "img30/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));  // use resources (set resource folder)
        return icon.getImage();
	}

}
