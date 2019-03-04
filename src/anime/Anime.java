package anime;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Anime extends JPanel {
    private static final String[] AUDIO = new String[] {
        "/filmfight.wav",
        "/attack.wav",
        "/applause2.wav"
    };
    private static final String[] IMAGES = new String[] {
        "/1.png",
        "/2.png",
        "/3.png",
        "/4.png",
        "/5.png",
        "/6.png",
        "/7.png",
        "/8.png",
        "/9.png"
    };
    
    private final URL[] PATHS_1 = new URL[] {
        this.getClass().getResource(Anime.IMAGES[0]),
        this.getClass().getResource(Anime.IMAGES[1]),
        this.getClass().getResource(Anime.IMAGES[2]),
        this.getClass().getResource(Anime.IMAGES[3]),
        this.getClass().getResource(Anime.IMAGES[4]),
        this.getClass().getResource(Anime.IMAGES[5]),
        this.getClass().getResource(Anime.IMAGES[6]),
        this.getClass().getResource(Anime.IMAGES[7]),
        this.getClass().getResource(Anime.IMAGES[8])
    };
    private final URL[] PATHS_3 = new URL[] {
        this.getClass().getResource(Anime.AUDIO[0]),
        this.getClass().getResource(Anime.AUDIO[1]),
        this.getClass().getResource(Anime.AUDIO[2])
    };
    
    private static AudioFormat format;
    private static AudioInputStream sound;
    private static BufferedImage image;
    private static Clip audioClip;
    private static DataLine.Info info;
    private static Image frame;
    private static int pointer1 = 1;
    private static int pointer2 = 1;
    private static String[] paths2 = new String[9];
    private static String[] paths4 = new String[3];
    private static Timer player1;
    private static Timer player2;

    public Anime() {
        this.setPreferredSize(new Dimension(700, 700));
        
        try {
            for(int x = 0; x < Anime.paths2.length; ++x) {
                Anime.paths2[x] = URLDecoder.decode(this.PATHS_1[x].getPath(), "ASCII");
            }
            
            for(int x = 0; x < Anime.paths4.length; ++x) {
                Anime.paths4[x] = URLDecoder.decode(this.PATHS_3[x].getPath(), "ASCII");
            }
        } catch (UnsupportedEncodingException err) {
            System.out.println(err);
        }
                
        try {
            Anime.image = ImageIO.read(new File(Anime.paths2[0]));
            Anime.frame = new ImageIcon(Anime.image).getImage();
        } catch (IOException err) {
            System.out.println(err);
        }
                
        try {
            Anime.sound = AudioSystem.getAudioInputStream(new File(Anime.paths4[0]));
            Anime.format = Anime.sound.getFormat();
            Anime.info = new DataLine.Info(Clip.class, Anime.format);
            Anime.audioClip = (Clip) AudioSystem.getLine(Anime.info);
            
            Anime.audioClip.open(sound);
            Anime.audioClip.start();
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException err) {
            System.out.println(err);
        }
        
        Anime.player2 = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ++Anime.pointer2;
                
                if(Anime.pointer2 >= Anime.paths4.length) {
                    Anime.pointer2 = 0;
                }
                
                try {
                    Anime.sound = AudioSystem.getAudioInputStream(new File(Anime.paths4[Anime.pointer2]));
                    Anime.format = Anime.sound.getFormat();
                    Anime.info = new DataLine.Info(Clip.class, Anime.format);
                    Anime.audioClip = (Clip) AudioSystem.getLine(Anime.info);
                    
                    Anime.audioClip.open(sound);
                    Anime.audioClip.start();
                    
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException err) {
                    System.out.println(err);
                }
            }
        });
        
        Anime.player1 = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ++Anime.pointer1;
                
                if(Anime.pointer1 >= Anime.paths2.length) {
                    Anime.pointer1 = 0;
                }
                
                try {
                    Anime.image = ImageIO.read(new File(Anime.paths2[Anime.pointer1]));
                    Anime.frame = new ImageIcon(Anime.image).getImage();
                } catch (IOException err) {
                    System.out.println(err);
                }
                
                repaint();
            }
        });
        
        Anime.player1.start();
        Anime.player2.start();
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D painter = (Graphics2D) g;
        
        painter.drawImage(Anime.frame, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        
        window.setContentPane(new Anime());
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }    
}
