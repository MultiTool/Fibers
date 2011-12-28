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
public class Graph_Panel extends javax.swing.JPanel implements Runnable {

  /* *************************************************************************************************** */
  public Graph_Panel() {
    this.setBackground(Color.red);
  }

  /* *************************************************************************************************** */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setBackground(Color.yellow);
  }
  /* *************************************************************************************************** */

  public void start() {
  }
  /* *************************************************************************************************** */

  public void stop() {
  }
  /* *************************************************************************************************** */

  @Override
  public void run() {
  }
}
