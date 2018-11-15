import java.awt.*;
import java.util.Formatter;
import javax.swing.*;
/**
 * One ball bouncing inside a rectangular box. 
 * All codes in one file. Poor design!
 */
// Extends JPanel, so as to override the paintComponent() for custom rendering codes. 
public class BouncingBallSimple extends JPanel {
   // Container box's width and height
   private static final int BOX_WIDTH = 640;
   private static final int BOX_HEIGHT = 480;
  
   // Ball's properties
   private float ballRadius = 100; // Ball's radius
   private float ballX = ballRadius + 50; // Ball's center (x, y)
   private float ballY = ballRadius + 20; 
   private float ballSpeedX = 3;   // Ball's speed for x and y
   private float ballSpeedY = 2;
   
   //Color properties
   
   private int R = 0;
   private int G = 0;
   private int B = 0;
   private Color bColor= new Color(R,G,B);
   private double rPhase = 0;
   private double gPhase = 0;
   private double bPhase = 0;
   private int bColorTiming = 0;
   private String s="";
   
  
   private static final int UPDATE_RATE = 30; // Number of refresh per second
  
   /** Constructor to create the UI components and init game objects. */
   public BouncingBallSimple() {
      this.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
  
      // Start the ball bouncing (in its own thread)
      Thread gameThread = new Thread() {
         public void run() {
            while (true) { // Execute one update step
            	if ((ballX % 1) == 0) {
            		makeColor();
            		bColor = new Color(R,G,B);
            	}
            	// Calculate the ball's new position
               ballX += ballSpeedX;
               ballY += ballSpeedY;
               // Check if the ball moves over the bounds
               // If so, adjust the position and speed.
               if (ballX - ballRadius < 0) {
                  ballSpeedX = -ballSpeedX; // Reflect along normal
                  ballX = ballRadius; // Re-position the ball at the edge
               } else if (ballX + ballRadius > BOX_WIDTH) {
                  ballSpeedX = -ballSpeedX;
                  ballX = BOX_WIDTH - ballRadius;
               }
               // May cross both x and y bounds
               if (ballY - ballRadius < 0) {
                  ballSpeedY = -ballSpeedY;
                  ballY = ballRadius;
               } else if (ballY + ballRadius > BOX_HEIGHT) {
                  ballSpeedY = -ballSpeedY;
                  ballY = BOX_HEIGHT - ballRadius;
               }
               // Refresh the display
               repaint(); // Callback paintComponent()
               // Delay for timing control and give other threads a chance
               try {
                  Thread.sleep(1000 / UPDATE_RATE);  // milliseconds
               } catch (InterruptedException ex) { }
            }
         }
      };
      gameThread.start();  // Callback run()
   }
  
   /** Custom rendering codes for drawing the JPanel */
   @Override
   public void paintComponent(Graphics g) {
      super.paintComponent(g);    // Paint background
  
      // Draw the box
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);
  
      // Draw the ball
      g.setColor(bColor);
      g.fillOval((int) (ballX - ballRadius), (int) (ballY - ballRadius),
            (int)(2 * ballRadius), (int)(2 * ballRadius));
  
      // Display the ball's information
      g.setColor(Color.WHITE);
      g.setFont(new Font("Courier New", Font.PLAIN, 12));
      StringBuilder sb = new StringBuilder();
      Formatter formatter = new Formatter(sb);
      formatter.format("Ball @(%3.0f,%3.0f) Speed=(%2.0f,%2d)"+s, ballX, ballY,
            ballSpeedX, /*ballSpeedY*/bColorTiming);
      g.drawString(sb.toString(), 20, 30);
   }
   
   private void makeColor() {
   	if (R<255)  
   		rPhase=rPhase+0.025;
   	else 
   		rPhase=0;
   	
   	if ((R>=125) && (G<255)) 
   		gPhase=gPhase+0.025;
   	else
   		if (G>254) gPhase=0;
   	
   	System.out.println(R+" "+G+" "+B);
   	R=(int) (Math.abs((Math.sin(rPhase)*255)));
   	G=(int) (Math.abs((Math.sin(gPhase)*255)));
   	B=(int) (Math.abs((Math.sin(bPhase)*255)));
    }
   
   /** main program (entry point) */
   public static void main(String[] args) {
      // Run GUI in the Event Dispatcher Thread (EDT) instead of main thread.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            // Set up main window (using Swing's Jframe)
            JFrame frame = new JFrame("A Bouncing Ball");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new BouncingBallSimple());
            frame.pack();
            frame.setVisible(true);
         }
      });
   }
}
