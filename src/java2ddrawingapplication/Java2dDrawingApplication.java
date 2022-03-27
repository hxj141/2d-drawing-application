/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author acv
 */
public class Java2dDrawingApplication
{
 // Inititalizing Components
 
    

    public static void main(String[] args)
    {
        DrawingApplicationFrame frame = new DrawingApplicationFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,500);
        frame.setTitle("Java 2D Drawing");
        frame.setVisible(true);
    }
    
}
