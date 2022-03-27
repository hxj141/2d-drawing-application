/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author acv
 */


public class DrawingApplicationFrame extends JFrame
{
    //Top row components
    JPanel menu = new JPanel();
    JPanel menu1 = new JPanel();
    JLabel shapeLabel = new JLabel("Shape:");
    JComboBox shapeSelect = new JComboBox();
    JButton colorPick1 = new JButton("1st Color...");
    JButton colorPick2 = new JButton("2nd Color...");
    JButton undo = new JButton("Undo");
    JButton clear = new JButton("Clear");
    
    //Second row components
    JPanel menu2 = new JPanel();
    JLabel optionsLabel = new JLabel("Options:");
    JCheckBox filled = new JCheckBox("Filled");
    JCheckBox gradient = new JCheckBox("Use Gradient");
    JCheckBox dashed = new JCheckBox("Dashed");
    JSpinner width = new JSpinner();
    JLabel widthLabel = new JLabel("Line Width");
    JSpinner length = new JSpinner();
    JLabel lengthLabel = new JLabel("Line Length");
    
    //Lower components
    DrawPanel canvas = new DrawPanel();
    JPanel statusBar = new JPanel();
    JLabel pointer = new JLabel();  
    
    
    //Shapes history and colors
    private ArrayList<MyShapes> history = new ArrayList<>();
    private Color color1 = Color.BLACK;
    private Color color2 = Color.WHITE;
    public DrawingApplicationFrame() {
        
    //Event handling for color popups
        ActionListener colorDialog1 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "1st Color", Color.BLACK);
                color1 = color;
                }
            };

        ActionListener colorDialog2 = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "2nd Color", Color.BLACK);
                color2 = color;
                }
            };
        
    //Event Handling for Undo/Clear
        ActionListener undoAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                history.remove(history.size() - 1);
                repaint();
                }
            };    
        ActionListener clearAction = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                history.clear();
                repaint();
                }
            };    
        //Basic Layout
        setLayout(new BorderLayout());
        
        menu.setLayout(new GridLayout(2,1));
        add(menu, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);
        
        menu.add(menu1);
        menu.add(menu2);
        
        //Control rows
        menu1.add(shapeLabel);
        menu1.add(shapeSelect);
        
        // Add and set button events
        menu1.add(colorPick1);
        colorPick1.addActionListener(colorDialog1);
        menu1.add(colorPick2);
        colorPick2.addActionListener(colorDialog2);
        menu1.add(undo);
        undo.addActionListener(undoAction);
        menu1.add(clear);
        clear.addActionListener(clearAction);
        
        // Add rest
        menu2.add(optionsLabel);
        menu2.add(filled);
        menu2.add(gradient);
        menu2.add(dashed);
        menu2.add(widthLabel);
        width.setValue(5);
        menu2.add(width);
        menu2.add(lengthLabel);
        length.setValue(5);
        menu2.add(length);
        
        //Rest of setup
        statusBar.add(pointer);
        
        menu.setBackground(Color.cyan);
        menu1.setBackground(Color.cyan);
        menu2.setBackground(Color.cyan);
        statusBar.setBackground(Color.LIGHT_GRAY);
        
        shapeSelect.addItem("Rectangle");
        shapeSelect.addItem("Oval");
        shapeSelect.addItem("Line");
        
     

    }



    // Create event handlers, if needed
     
    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        // Listeners
        public DrawPanel()
        {
          addMouseMotionListener(new MouseHandler());
          addMouseListener(new MouseHandler());
        }
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            for (MyShapes shape:history) {
                shape.draw(g2d);
            } 

            //loop through and draw each shape in the shapes arraylist

        }

        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {

///isSelected
         // ferSelectedItem
            public void mousePressed(MouseEvent event)
            {
            // Parse needed options for canvas
                Stroke stroke;
                Paint paint;
                // Initialize one-element array with float value parsed from JSpinner output
                float dashLength[] = new float[] {Float.parseFloat(length.getValue().toString())};
                // Set up paint depending on gradient selection
                if (gradient.isSelected()) {
                    paint = new GradientPaint(0, 0, color1, 50, 50, color2, true);
                }
                else {
                    paint = color1;
                }
                //Set up stroke depending on dash selection
                if (dashed.isSelected()) {
                    stroke = new BasicStroke(Integer.parseInt(width.getValue().toString()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashLength, 0);
                } 
                else {
                    stroke = new BasicStroke(Integer.parseInt(width.getValue().toString()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }

            // Add shape to arraylist
                switch (shapeSelect.getSelectedItem().toString())
                {
                    case "Rectangle":
                        history.add(new MyRectangle(event.getPoint(), event.getPoint(), paint, stroke, filled.isSelected()));
                        break;
                    case "Oval":
                        history.add(new MyOval(event.getPoint(), event.getPoint(), paint, stroke, filled.isSelected()));
                        break;
                    case "Line":
                        history.add(new MyLine(event.getPoint(), event.getPoint(), paint, stroke));
                        break;
                    
                }                  
                repaint();
            }

            public void mouseReleased(MouseEvent event)
            {
            
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
               // update endpoint then redraw
               history.get(history.size()-1).setEndPoint(event.getPoint());
               pointer.setText("(" + event.getX() + "," + event.getY() + ")");
               repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                //Update pointer label
                pointer.setText("(" + event.getX() + "," + event.getY() + ")");
                
            }
        }
    }

}