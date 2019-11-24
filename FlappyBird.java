package javaapplication14;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, KeyListener {

    public static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 600;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public int yMotion;
    public boolean game_over, game_started;
    public Random rand;
    int space_between_col = 200;
    int col_width = 100;

    Timer timer;

    public FlappyBird() {
        JFrame jframe = new JFrame();
        timer = new Timer(20, this);
        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setVisible(true);

        bird = new Rectangle(WIDTH - 500, HEIGHT - 300, 30, 20);
        columns = new ArrayList<Rectangle>();
     
        for (int i =0; i<2; i++)
        {
            int col_height = 50 + rand.nextInt(300);
            columns.add(new Rectangle(WIDTH + columns.size() * 300, HEIGHT - col_height, col_width, col_height));
            columns.add(new Rectangle(WIDTH + (columns.size() - 1) * 300, 0, col_width, HEIGHT - col_height - space_between_col));
        }
        timer.start();
    }

    public void jump()
    {
        if (game_over) 
        {
            bird = new Rectangle(WIDTH - 500, HEIGHT - 300, 30, 20);
            columns.clear();
            yMotion = 0;

        for (int i =0; i<2; i++)
        {
            int col_height = 50 + rand.nextInt(300);
            columns.add(new Rectangle(WIDTH + columns.size() * 300, HEIGHT - col_height, col_width, col_height));
            columns.add(new Rectangle(WIDTH + (columns.size() - 1) * 300, 0, col_width, HEIGHT - col_height - space_between_col));
        }
            game_over = false;
        }

        if (!game_started)
        {
            game_started = true;
        }
        if (!game_over)
        {
            yMotion = 0;
            yMotion = yMotion - 14;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int col_speed = 4;

        if (game_started) {
            for (int i = 0; i < columns.size(); i++)
            {
                Rectangle column = columns.get(i);
                column.x = column.x - col_speed;

                if (column.x + column.width < 0) 
                {
                    columns.remove(column);
                        int col_height = 50 + rand.nextInt(300);
                        columns.add(new Rectangle(columns.get(columns.size() - 1).x + 750, HEIGHT - col_height, col_width, col_height));
                        columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, col_width, HEIGHT - col_height - space_between_col));
                }
            }

            yMotion += 1;
            bird.y = bird.y + yMotion;
            for (Rectangle column : columns)
            {
                if (column.intersects(bird))
                {
                    game_over = true;
                }
            }
            if (bird.y > HEIGHT || bird.y < 0) 
            {
                game_over = true;
            }
        }

        renderer.repaint();
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.magenta);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column : columns) 
        {
            g.setColor(Color.pink);
            g.fillRect(column.x, column.y, column.width, column.height);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 2, 80));

        if (!game_started) 
        {
            g.drawString("PRESS SPACE", 100, 200);
        }

        if (game_over) 
        {
            g.drawString("GAME OVER", 100, 200);
        }
    }

    public static void main(String[] args) 
    {
        flappyBird = new FlappyBird();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

}
