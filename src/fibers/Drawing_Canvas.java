/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fibers;

import java.util.Vector;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author human
 */
public class Drawing_Canvas extends javax.swing.JPanel implements Runnable {

  Thread mythread;
  boolean keeprunning = true;
  Things.Layers lay = null;
  /* *************************************************************************************************** */

  public Drawing_Canvas() {
    onShutdown();
    this.setBackground(Color.red);
    lay = new Things.Layers();
    lay.Make_Layers(9);
  }

  /* *************************************************************************************************** */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setBackground(Color.yellow);
    g2.clearRect(0, 0, this.getWidth(), this.getHeight());
    Things.TransForm tr = new Things.TransForm();
    lay.Draw_Me(tr, g2);
    if (false) {
      lay.Pass_Back_Corrector();
    }
    this.repaint();// flickery animation. probably need thread locking
  }
  /* *************************************************************************************************** */
  public void onShutdown() {
    // attempting to find a way to grab window closing event
    JFrame mainFrame = FibersApp.getApplication().getMainFrame();
    mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {

      @Override
      public void windowClosing(java.awt.event.WindowEvent e) {
        System.out.println("good bye");
        //dispose() ;  
        System.exit(0);
      }
    });
    /*
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
    @Override
    public void run() {
    //Do the onShutdown stuff here.
    boolean nop = true;
    }
    }));
     */
  }
  /* *************************************************************************************************** */

  public void start() {
    mythread = new Thread(this);
    keeprunning = true;
    mythread.start();
  }
  /* *************************************************************************************************** */

  public void stop() {
    keeprunning = false;
    mythread = null;
  }
  /* *************************************************************************************************** */

  @Override
  public void run() {
    while (keeprunning) {
      boolean nop = true;
      lay.RunCycle();
      System.out.println(nop);
      try{
        Thread.sleep(20);
      }catch(Exception ex){
        System.out.println("Sleep crash!");
      }
    }
  }
}
