//Name: Nguyen Thanh Vinh
//Net ID: 31810937
//Student ID: 659824967
//I work alone

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JTextArea;

public class TTTGame extends JFrame implements ChangeListener, ActionListener {
	private JButton newGame;
	private JTextArea turn, stats;
	private Board board; //creating an object of Board.
	private Color oColor=Color.BLUE, xColor=Color.RED;
	static final char blank=' ', O='O', X='X';
	private char[] position = {  // Board position (blank, O, or X)
		blank, blank, blank,
		blank, blank, blank,
		blank, blank, blank};
	private int P1wins=0, P2wins=0, draws=0;  // game count by user
	private int count = 0; //Tracker (odd is P1's turn, even is P2's turn)
	private boolean active = true;
	// Start the game
	public static void main(String args[]) {
		new TTTGame();
	}

  	// Initialize
	public TTTGame() {
	    super("Tic Tac Toe");
	    JPanel topPanel=new JPanel();
	    topPanel.setLayout(new FlowLayout());
	    topPanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(10, 50, 10, 50);
	    c.gridheight = GridBagConstraints.REMAINDER;
	    c.fill = GridBagConstraints.BOTH;
	    newGame = new JButton( new AbstractAction("newGame") {
	        @Override
	        public void actionPerformed( ActionEvent e ) {
	        	active = true;
	        	for (int j=0; j<9; ++j) {
	        		position[j]=blank;
	        	}
	        	turn.setText("P1's turn!" + "\nP1: X");
	        	count = 0;
	        	stats.setText("P1 wins: "+ P1wins+ "\nP2 wins: "+ P2wins + "\ndraws: " + draws);
	        	repaint();
	        }
	    });
	    topPanel.add(newGame, c);
	    topPanel.add(turn = new JTextArea(), c);
	    topPanel.add(stats = new JTextArea(), c);
	    add(topPanel, BorderLayout.NORTH);
	    add(board=new Board(), BorderLayout.CENTER);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(500, 500);
	    setVisible(true);
	    stats.setEditable(false);
	    turn.setEditable(false);
	    stats.setText("P1 wins: "+ P1wins+ "\nP2 wins: "+ P2wins + "\ndraws: " + draws);
	    turn.setText("P1's turn!" + "\nP1: X");
	}
	    
// Board overlays JFrame and display the game
private class Board extends JPanel implements MouseListener {
	private int[][] rows = {{0,2},{3,5},{6,8},{0,6},{1,7},{2,8},{0,8},{2,6}};
	// Endpoints of the 8 rows in position[] (across, down, diagonally)

    public Board() {
    	addMouseListener(this);
    }

    // Draw the board
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
	    int w=getWidth();
	    int h=getHeight();
	    Graphics2D g2d = (Graphics2D) g;

	    // Draw the grid
	    g2d.setPaint(Color.WHITE);
	    g2d.fill(new Rectangle2D.Double(0, 0, w, h));
	    g2d.setPaint(Color.BLACK);
	    g2d.draw(new Line2D.Double(0, h/3, w, h/3));
	    g2d.draw(new Line2D.Double(0, h*2/3, w, h*2/3));
	    g2d.draw(new Line2D.Double(w/3, 0, w/3, h));
	    g2d.draw(new Line2D.Double(w*2/3, 0, w*2/3, h));

	    // X and O design
	    for (int i=0; i<9; ++i) {
	    	double xpos=(i%3+0.5)*w/3.0;
	    	double ypos=(i/3+0.5)*h/3.0;
	    	double xr=w/8.0;
	    	double yr=h/8.0;
	    	if (position[i]==O) {
	    		g2d.setPaint(oColor);
	    		g2d.draw(new Ellipse2D.Double(xpos-xr, ypos-yr, xr*2, yr*2));
	    	}
	    	else if (position[i]==X) {
	    		g2d.setPaint(xColor);
	    		g2d.draw(new Line2D.Double(xpos-xr, ypos-yr, xpos+xr, ypos+yr));
	    		g2d.draw(new Line2D.Double(xpos-xr, ypos+yr, xpos+xr, ypos-yr));
	    	}
	    }
    }
    
    // Draw X or O where clicked
    public void mouseClicked(MouseEvent e) {
    	if(active) {
	    	int xpos=e.getX()*3/getWidth();
	    	int ypos=e.getY()*3/getHeight();
	    	int pos=xpos+3*ypos;
	    	if (pos>=0 && pos<9 && position[pos]==blank) {
	    		count++;
	    		if(count%2==0){
	    			position[pos]=O;
	    			turn.setText("P1's turn!" + "\nP1: X");
	    			if (won(O))
	    				newGame(O);
	    			else if (isDraw())
	    				newGame(blank);
	    		}
	    		else{
	    			position[pos]=X;
	    			turn.setText("P2's turn!"+ "\nP2: O");
	    			if (won(X))
	    				newGame(X);
	    			else if (isDraw())
	    	        	newGame(blank);
	    		}
	    		repaint();
	    	}
    	}
    }
    

    // Ignore other events
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}


    // Return true if won
    boolean won(char player) {
    	for (int i=0; i<8; ++i)
    		if (testRow(player, rows[i][0], rows[i][1]))
    			return true;
    	return false;
    }

    // check row for win
    boolean testRow(char player, int a, int b) {
    	return position[a]==player && position[b]==player 
    			&& position[(a+b)/2]==player;
    }

    // Check if the board is full
    boolean isDraw() {
    	for (int i=0; i<9; ++i)
    		if (position[i]==blank)
    			return false;
    	return true;
    }

    // Start a new game
    void newGame(char winner) {
    	String result;
    	active = false;
    	if (winner==O) {
    		++P2wins;
    		result = "P2 wins!" + "\nP2: O";
    		turn.setText(result);
    	}
    	else if (winner==X) {
    		++P1wins;
    		result = "P1 wins!" + "\nP1: X";
    		turn.setText(result);
    	 }
	      else {
	        result = "Tie" + "\nP1: X" + "\nP2: O";
	        ++draws;
	        turn.setText(result);
	      }
    	
    	stats.setText("P1 wins: "+ P1wins+ "\nP2 wins: "+ P2wins + "\ndraws: " + draws);
    	repaint();
    
    	}
  	} // end class Board
	
	//These two stub methods are to avoid eclipse warning.
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
} // end class TicTacToe