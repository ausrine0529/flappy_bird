package javaapplication14;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Renderer extends JPanel
{
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		FlappyBird.flappyBird.repaint(g);
	}
	
}