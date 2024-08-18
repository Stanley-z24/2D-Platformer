/*
 *  Stanley Zhang
 * ICS3U
 * Jan 24 2023
 * Mr. Do
 * Program runs game similar to maple story in which players are fighting monsters, the objective is to get through all the levels
 */
import java.io.IOException; //Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.sound.sampled.*;

public class GameAssignment{ //The buffered Images that need to be imported are in the canvas
    static JFrame gameFrame; //Declaring variables
    static GraphicsPanel canvas;
    static BufferedImage background, floor, walkRight1, walkRight2,walkLeft1,walkLeft2,attack1,attack2,monster1,monster2, animation, monsteranimation, monsteranimation2, monsteranimation3, monsteranimation4, monsteranimation5, monsteranimation6, monsteranimation7, monsteranimation8, monsteranimation9, monsteranimation10;
    static JPanel gamePanel;
    static MyKeyListener keyListener = new MyKeyListener();  
    static MyMouseListener mouseListener = new MyMouseListener();
    static final int pWidth=20,pHeight=60,attackRange= 75, JUMP = 50,enemyWidth=50,enemyHeight=80;
    static int pX = 100,pY=600, Vx = 0,Vy=0, gravity =3, stepX = 20, stepY= 10, currentHP;
    static final int plat1X=50,plat1Y=500, platWidth=250,platHeight=40;
    static final int plat2X=700,plat2Y=500, GROUND = 700 ;
    static int floorX = 0, floorY=700, floorW = 1800, floorH=200;
    static int enemyX = 900, enemyY = 600,enemyVx=0, enemyVy=0, enemyStepX=5,enemyCurrentHP=3; //enemy values
    static int enemyX2 = 900, enemyY2 = 600,enemyVx2=0, enemyVy2=0, enemyStepX2=3, enemyCurrentHP2=8;
    static int enemyX3 = 900, enemyY3 = 600,enemyVx3=0, enemyVy3=0, enemyStepX3=14, enemyCurrentHP3=2;
    static int enemyX4 = 900, enemyY4 = 600,enemyVx4=0, enemyVy4=0, enemyStepX4=3, enemyCurrentHP4=4;
    static int enemyX5 = 900, enemyY5 = 600,enemyVx5=0, enemyVy5=0, enemyStepX5=20, enemyCurrentHP5=1;
    static int enemyX6 = 900, enemyY6 = 600,enemyVx6=0, enemyVy6=0, enemyStepX6=7, enemyCurrentHP6=2;
    static int enemyX7 = 900, enemyY7 = 600,enemyVx7=0, enemyVy7=0, enemyStepX7=9, enemyCurrentHP7=3;
    static int enemyX8 = 900, enemyY8 = 600,enemyVx8=0, enemyVy8=0, enemyStepX8=11, enemyCurrentHP8=3;
    static int enemyX9 = 900, enemyY9 = 600,enemyVx9=0, enemyVy9=0, enemyStepX9=13, enemyCurrentHP9=2;
    static int enemyX10 = 900, enemyY10 = 600,enemyVx10=0, enemyVy10=0, enemyStepX10=16, enemyCurrentHP10=10;
    static final int enemyMaxHP=3,enemyMaxHP2=6,enemyMaxHP3=2,enemyMaxHP4=4,enemyMaxHP5=1,enemyMaxHP6=2,enemyMaxHP7=3,enemyMaxHP8=3,enemyMaxHP9=2,enemyMaxHP10=10, maxHP = 100;
    static boolean []  enemys = new boolean [10];
    static Rectangle Floor = new Rectangle(floorX, floorY, floorW, floorH); //Hitboxes
    static Rectangle player = new Rectangle(pX, pY, pWidth, pHeight);
    static Rectangle plat1Rectangle = new Rectangle(plat1X,plat1Y,platWidth,platHeight);
    static Rectangle plat3Rectangle = new Rectangle(plat2X,plat2Y,platWidth,platHeight);
    static Rectangle enemyRectangle = new Rectangle(enemyX,enemyY,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle2 = new Rectangle(enemyX2,enemyY2,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle3 = new Rectangle(enemyX3,enemyY3,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle4 = new Rectangle(enemyX4,enemyY4,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle5 = new Rectangle(enemyX5,enemyY5,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle6 = new Rectangle(enemyX6,enemyY6,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle7 = new Rectangle(enemyX7,enemyY7,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle8 = new Rectangle(enemyX8,enemyY8,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle9 = new Rectangle(enemyX9,enemyY9,enemyWidth,enemyHeight);
    static Rectangle enemyRectangle10 = new Rectangle(enemyX10,enemyY10,enemyWidth,enemyHeight);
    static Rectangle playAgain = new Rectangle(0,0,300,200);
    static int bulletX = 0; //Sword swing properties (make bullet invisible so it looks like a sword swing)
    static int bulletY = 0;
    static boolean bullet = false;
    static int bulletSpeed;
    static int currentBullet = 0;
    static final int knockback = 200, bulletHeight = 50, bulletWidth = 20 ;
    static Rectangle bulletRectangle = new Rectangle(bulletX,bulletY,bulletWidth,bulletHeight);
    static int direction = 1, level = 1, dead=0;
    static boolean loop = true, startingAnimation = true;
    static String title = " ";
    static AudioInputStream musicAudio, swordAudio; //Audios
    static Clip music, sword;
    public static void main(String[]args) throws IOException{ //Main class
        animation = walkRight1;  //Initialize starting animation so it wont be null
        gameFrame = new JFrame("Maple Story...kind of?"); //Frame
        gameFrame.setSize(1000,900);
        gamePanel = new JPanel(); //Main Panel
        gameFrame.add(gamePanel);
        canvas = new GraphicsPanel();
        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
        gameFrame.add(canvas);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try { //Plays background music
            File audioMusic = new File("C:/Users/zhang/OneDrive/Desktop/Game photos/MapleStorybg.wav");
            musicAudio = AudioSystem.getAudioInputStream(audioMusic);
            music = AudioSystem.getClip();
            music.open(musicAudio);
        } catch (Exception ex){} 
        music.start();
        music.loop(Clip.LOOP_CONTINUOUSLY);

        try { //Plays sword sound effect
            File audioSword = new File("C:/Users/zhang/OneDrive/Desktop/Game photos/SwordSound.wav");
            swordAudio = AudioSystem.getAudioInputStream(audioSword);
            sword = AudioSystem.getClip();
            sword.open(swordAudio);
        } catch (Exception ex){} 

        Mechanics();
       
    }

    public static void Levels(){
        enemyX = 900; //restarts the values
        enemyY = 600;
        enemyX2 = 900;
        enemyY2 = 600;
        enemyX3 = 900;
        enemyY3 = 600;
        enemyX4 = 900;
        enemyY4 = 600;
        enemyX5 = 900;
        enemyY5 = 600;
        enemyX6 = 900;
        enemyY6 = 600;
        enemyX7 = 900;
        enemyY7 = 600;
        enemyX8 = 900;
        enemyY8 = 600;
        enemyX9 = 900;
        enemyY9 = 600;
        enemyX10 = 900;
        enemyY10 = 600;
        pX = 100;
        pY=600;
        animation = walkRight1;
        currentHP = maxHP;
        enemyCurrentHP = enemyMaxHP;
        enemyCurrentHP2 = enemyMaxHP2;
        enemyCurrentHP3 = enemyMaxHP3;
        enemyCurrentHP4 = enemyMaxHP4;
        enemyCurrentHP5 = enemyMaxHP5;
        enemyCurrentHP6 = enemyMaxHP6;
        enemyCurrentHP7 = enemyMaxHP7;
        enemyCurrentHP8 = enemyMaxHP8;
        enemyCurrentHP9 = enemyMaxHP9;
        enemyCurrentHP10 = enemyMaxHP10;
        for (int i=0; i <10; i++){ //Sets all enemys to false
            enemys[i] = false;
        }

        try{Thread.sleep(500);} catch(Exception e){}
        dead = 0;
        if (level <=10){ //if have not completed all levels
            for (int i=0; i <level; i++){ //Spawns the monsters that are equivalent to the current levels
                enemys[i] = true;
            }
            
        }else{  //if completed all levels set the title to win and close the game loop
            title = "WIN!";
            loop = false;
        }
    }

    public static void Mechanics(){
        while(true){
            Levels(); //Starts the first level
            gameFrame.repaint();
            try{Thread.sleep(20);} catch(Exception e){}
            while(loop){
                title = "Level " + level; //Changes the level number
                gameFrame.repaint();
                try{Thread.sleep(20);} catch(Exception e){}
                dead = 0; //restart dead value
                player.setLocation(pX,pY); //Check current players location 

                if (player.intersects(Floor)){//Floor boundaries for player
                    pY = pY- stepY;
                }

                pX = pX + Vx; //Player Gravity physics
                Vy = Vy + gravity;
                pY = pY + Vy;
                if (pY+pHeight >= GROUND){
                    pY = GROUND - pHeight;
                    Vy = 0;
                }
           
                if (currentHP < 0){ //If player dies
                    title = "You Died";
                    loop = false;
                    System.out.println(currentHP);
                }

                for (int b=0; b<level; b++){ //check how many enemys are dead
                    if (enemys[b] == false){
                        dead = dead + 1;
                    }else{
                        dead=0;
                    }
                }


                if (dead == level){ //If all enemy dead then goes to next level
                    dead = 0;
                    level = level + 1;
                    Levels();
                }
                Bullet(); //Sword hitbox
                PlatformBounds();//platforms
                EnemyMove();//Enemy movement

            }
        }

    }

    public static void Bullet(){
        player.setLocation(pX,pY); //Check current location of player
        bulletRectangle.setLocation(bulletX,bulletY); //Check current Location of "Bullet" (Sword Slash)
        for (int i=0; i<1; i++){ //Bullet properties
            if (bullet){
                bulletX = bulletX + bulletSpeed;
                if (bulletX>(pX-pWidth-attackRange) && bulletX< (pX+pWidth+attackRange)){ //Bound bullet to range of character
                 if(bulletRectangle.intersects(enemyRectangle)){ //Kill enemy
                    bullet = false; //makes bullet dissapear so it won't hit again 
                    enemyCurrentHP = enemyCurrentHP - 1; //take away HP from enemy
                    if (direction == -1){ //Checks which direction player is facing then knocks the enemy back in same direction
                        enemyVx = -(enemyStepX+knockback);
                    }else{
                        enemyVx = (enemyStepX+knockback);
                    }
                    if(enemyCurrentHP == 0){ //If enemy HP is 0 then make enemy dissapear
                        enemys[0] = false;
                    }
                   
                 }
                 if(bulletRectangle.intersects(enemyRectangle2)){ //Kill enemy2
                    bullet = false;
                    enemyCurrentHP2 = enemyCurrentHP2 - 1;
                    if (direction == -1){
                        enemyVx2 = -(enemyStepX2+knockback);
                    }else{
                        enemyVx2 = (enemyStepX2+knockback);
                    }
                    if(enemyCurrentHP2 == 0){
                        enemys[1] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle3)){ //Kill enemy3
                    bullet = false;
                    enemyCurrentHP3 = enemyCurrentHP3 - 1;
                    if (direction == -1){
                        enemyVx3 = -(enemyStepX3+knockback);
                    }else{
                        enemyVx3 = (enemyStepX3+knockback);
                    }
                    if(enemyCurrentHP3 == 0){
                        enemys[2] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle4)){ //Kill enemy4
                    bullet = false;
                    enemyCurrentHP4 = enemyCurrentHP4 - 1;
                    if (direction == -1){
                        enemyVx4 = -(enemyStepX4+knockback);
                    }else{
                        enemyVx4 = (enemyStepX4+knockback);
                    }
                    if(enemyCurrentHP4 == 0){
                        enemys[3] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle5)){ //Kill enemy5
                    bullet = false;
                    enemyCurrentHP5 = enemyCurrentHP5 - 1;
                    if (direction == -1){
                        enemyVx5 = -(enemyStepX5+knockback);
                    }else{
                        enemyVx5 = (enemyStepX5+knockback);
                    }
                    if(enemyCurrentHP5 == 0){
                        enemys[4] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle6)){ //Kill enemy6
                    bullet = false;
                    enemyCurrentHP6 = enemyCurrentHP6 - 1;
                    if (direction == -1){
                        enemyVx6 = -(enemyStepX6+knockback);
                    }else{
                        enemyVx6 = (enemyStepX6+knockback);
                    }
                    if(enemyCurrentHP6 == 0){
                        enemys[5] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle7)){ //Kill enemy7
                    bullet = false;
                    enemyCurrentHP7 = enemyCurrentHP7 - 1;
                    if (direction == -1){
                        enemyVx7 = -(enemyStepX7+knockback);
                    }else{
                        enemyVx7 = (enemyStepX7+knockback);
                    }
                    if(enemyCurrentHP7 == 0){
                        enemys[6] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle8)){ //Kill enemy8
                    bullet = false;
                    enemyCurrentHP8 = enemyCurrentHP8 - 1;
                    if (direction == -1){
                        enemyVx8 = -(enemyStepX8+knockback);
                    }else{
                        enemyVx8 = (enemyStepX8+knockback);
                    }
                    if(enemyCurrentHP8 == 0){
                        enemys[7] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle9)){ //Kill enemy9
                    bullet = false;
                    enemyCurrentHP9 = enemyCurrentHP9 - 1;
                    if (direction == -1){
                        enemyVx9 = -(enemyStepX9+knockback);
                    }else{
                        enemyVx9 = (enemyStepX9+knockback);
                    }
                    if(enemyCurrentHP9 == 0){
                        enemys[8] = false;
                    }
                 }
                 if(bulletRectangle.intersects(enemyRectangle10)){ //Kill enemy10
                    bullet = false;
                    enemyCurrentHP10 = enemyCurrentHP10 - 1;
                    if (direction == -1){
                        enemyVx10 = -(enemyStepX10+knockback);
                    }else{
                        enemyVx10 = (enemyStepX10+knockback);
                    }
                    if(enemyCurrentHP10 == 0){
                        enemys[9] = false;
                    }
                 }

                }
                else{
                    bullet = false; //If bullet is not within the range of player
                }
            }
        }      
    }
    public static void EnemyMove(){
        if (enemys[0]){
            enemyX = enemyX + enemyVx; //Enemy1 Gravity physics
            enemyVy = enemyVy + gravity;
            enemyY = enemyY + enemyVy;
            if (enemyY+enemyHeight >= GROUND){
                enemyY = GROUND - enemyHeight;
                enemyVy = 0;
            }

            player.setLocation(pX,pY); //Checks for player and enemy current location
            enemyRectangle.setLocation(enemyX,enemyY);
            if (enemyRectangle.intersects(player) == true){//if enemy touches player take away HP from player
                currentHP = currentHP - 1;
            }
       
            else { //Move enemy towards player
                if(enemyY+enemyHeight<pY+pHeight){
                    enemyVx = -enemyStepX;
                    monsteranimation2 = monster2;
                }
                else if(enemyX < pX){
                    enemyVx = enemyStepX;
                    monsteranimation = monster1;
                }
                else if (enemyX > pX){
                    enemyVx = -enemyStepX;
                    monsteranimation = monster2;
                }
                else if (enemyY > pY && enemyVy == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy = -(JUMP);
                }
            }
        }else{
            enemyRectangle.setLocation(0,0); //if enemy is dead then set hitbox to 0,0
        }

        if (enemys[1]){
            enemyX2 = enemyX2 + enemyVx2; //Enemy2
            enemyVy2 = enemyVy2 + gravity;
            enemyY2 = enemyY2 + enemyVy2;
            if (enemyY2+enemyHeight >= GROUND){
                enemyY2 = GROUND - enemyHeight;
                enemyVy2 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle2.setLocation(enemyX2,enemyY2);
            if (enemyRectangle2.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY2+enemyHeight<pY+pHeight){
                    enemyVx2 = -enemyStepX2;
                    monsteranimation2 = monster2;
                }
                else if(enemyX2 < pX){
                    enemyVx2 = enemyStepX2;
                    monsteranimation2 = monster1;
                }
                else if (enemyX2 > pX){
                    enemyVx2 = -enemyStepX2;
                    monsteranimation2 = monster2;
                }
                else if (enemyY2 > pY && enemyVy2 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy2 = -(JUMP);
                }
            }
        }else{
            enemyRectangle2.setLocation(0,0);
        }
   

        if (enemys[2]){
            enemyX3 = enemyX3 + enemyVx3; //Enemy3
            enemyVy3 = enemyVy3 + gravity;
            enemyY3 = enemyY3 + enemyVy3;
            if (enemyY3+enemyHeight >= GROUND){
                enemyY3 = GROUND - enemyHeight;
                enemyVy3 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle3.setLocation(enemyX3,enemyY3);
            if (enemyRectangle3.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY3+enemyHeight<pY+pHeight){
                    enemyVx3 = -enemyStepX3;
                    monsteranimation3 = monster2;
                }
                else if(enemyX3 < pX){
                    enemyVx3 = enemyStepX3;
                    monsteranimation3 = monster1;
                }
                else if (enemyX3 > pX){
                    enemyVx3 = -enemyStepX3;
                    monsteranimation3 = monster2;
                }
                else if (enemyY3 > pY && enemyVy3 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy3= -(JUMP);
                }
            }
        }else{
            enemyRectangle3.setLocation(0,0);
        }

        if (enemys[3]){
            enemyX4 = enemyX4 + enemyVx4; //Enemy4
            enemyVy4 = enemyVy4 + gravity;
            enemyY4 = enemyY4 + enemyVy4;
            if (enemyY4+enemyHeight >= GROUND){
                enemyY4 = GROUND - enemyHeight;
                enemyVy4 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle4.setLocation(enemyX4,enemyY4);
            if (enemyRectangle4.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY4+enemyHeight<pY+pHeight){
                    enemyVx4 = -enemyStepX4;
                    monsteranimation4 = monster2;
                }
                else if(enemyX4 < pX){
                    enemyVx4 = enemyStepX4;
                    monsteranimation4 = monster1;
                }
                else if (enemyX4 > pX){
                    enemyVx4 = -enemyStepX4;
                    monsteranimation4 = monster2;
                }
                else if (enemyY4 > pY && enemyVy4 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy4 = -(JUMP);
                }
            }
        }else{
            enemyRectangle4.setLocation(0,0);
        }


        if (enemys[4]){
            enemyX5 = enemyX5 + enemyVx5; //Enemy5
            enemyVy5 = enemyVy5 + gravity;
            enemyY5 = enemyY5 + enemyVy5;
            if (enemyY5+enemyHeight >= GROUND){
                enemyY5 = GROUND - enemyHeight;
                enemyVy5 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle5.setLocation(enemyX5,enemyY5);
            if (enemyRectangle5.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY5+enemyHeight<pY+pHeight){
                    enemyVx5 = -enemyStepX5;
                    monsteranimation5 = monster2;
                }
                else if(enemyX5 < pX){
                    enemyVx5 = enemyStepX5;
                    monsteranimation5 = monster1;
                }
                else if (enemyX5 > pX){
                    enemyVx5 = -enemyStepX5;
                    monsteranimation5 = monster2;
                }
                else if (enemyY5 > pY && enemyVy5 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy5 = -(JUMP);
                }
            }
        }else{
            enemyRectangle5.setLocation(0,0);
        }


        if (enemys[5]){
            enemyX6 = enemyX6 + enemyVx6; //Enemy6
            enemyVy6 = enemyVy6 + gravity;
            enemyY6 = enemyY6 + enemyVy6;
            if (enemyY6+enemyHeight >= GROUND){
                enemyY6 = GROUND - enemyHeight;
                enemyVy6 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle6.setLocation(enemyX6,enemyY6);
            if (enemyRectangle6.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY6+enemyHeight<pY+pHeight){
                    enemyVx6 = -enemyStepX6;
                    monsteranimation6 = monster2;
                }
                else if(enemyX6 < pX){
                    enemyVx6 = enemyStepX6;
                    monsteranimation6 = monster1;
                }
                else if (enemyX6 > pX){
                    enemyVx6 = -enemyStepX6;
                    monsteranimation6 = monster2;
                }
                else if (enemyY6 > pY && enemyVy6 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy6= -(JUMP);
                }
            }
        }else{
            enemyRectangle6.setLocation(0,0);
        }


        if (enemys[6]){
            enemyX7 = enemyX7 + enemyVx7; //Enemy7
            enemyVy7 = enemyVy7 + gravity;
            enemyY7 = enemyY7 + enemyVy7;
            if (enemyY7+enemyHeight >= GROUND){
                enemyY7 = GROUND - enemyHeight;
                enemyVy7 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle7.setLocation(enemyX7,enemyY7);
            if (enemyRectangle7.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY7+enemyHeight<pY+pHeight){
                    enemyVx7 = -enemyStepX7;
                    monsteranimation7 = monster2;
                }
                else if(enemyX7 < pX){
                    enemyVx7 = enemyStepX7;
                    monsteranimation7 = monster1;
                }
                else if (enemyX7 > pX){
                    enemyVx7 = -enemyStepX7;
                    monsteranimation7 = monster2;
                }
                else if (enemyY7 > pY && enemyVy7 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy7 = -(JUMP);
                }
            }
        }else{
            enemyRectangle7.setLocation(0,0);
        }


        if (enemys[7]){
            enemyX8 = enemyX8 + enemyVx8; //Enemy8
            enemyVy8 = enemyVy8 + gravity;
            enemyY8 = enemyY8 + enemyVy8;
            if (enemyY8+enemyHeight >= GROUND){
                enemyY8 = GROUND - enemyHeight;
                enemyVy8 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle8.setLocation(enemyX8,enemyY8);
            if (enemyRectangle8.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY8+enemyHeight<pY+pHeight){
                    enemyVx8 = -enemyStepX8;
                    monsteranimation8 = monster2;
                }
                else if(enemyX8 < pX){
                    enemyVx8 = enemyStepX8;
                    monsteranimation8 = monster1;
                }
                else if (enemyX8 > pX){
                    enemyVx8 = -enemyStepX8;
                    monsteranimation8 = monster2;
                }
                else if (enemyY8 > pY && enemyVy8 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy8 = -(JUMP);
                }
            }
        }else{
            enemyRectangle8.setLocation(0,0);
        }


        if (enemys[8]){
            enemyX9 = enemyX9 + enemyVx9; //Enemy9
            enemyVy9 = enemyVy9 + gravity;
            enemyY9 = enemyY9 + enemyVy9;
            if (enemyY9+enemyHeight >= GROUND){
                enemyY9 = GROUND - enemyHeight;
                enemyVy9 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle9.setLocation(enemyX9,enemyY9);
            if (enemyRectangle9.intersects(player) == true){
                currentHP = currentHP - 1;
            }
       
            else {
                if(enemyY9+enemyHeight<pY+pHeight){
                    enemyVx9 = -enemyStepX9;
                    monsteranimation9 = monster2;
                }
                else if(enemyX9 < pX){
                    enemyVx9 = enemyStepX9;
                    monsteranimation9 = monster1;
                }
                else if (enemyX9 > pX){
                    enemyVx9 = -enemyStepX9;
                    monsteranimation9 = monster2;
                }
                else if (enemyY9 > pY && enemyVy9 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy9 = -(JUMP);
                }
            }
        }else{
            enemyRectangle9.setLocation(0,0);
        }

        if (enemys[9]){
            enemyX10 = enemyX10 + enemyVx10; //Enemy10
            enemyVy10 = enemyVy10 + gravity;
            enemyY10 = enemyY10 + enemyVy10;
            if (enemyY10+enemyHeight >= GROUND){
                enemyY10 = GROUND - enemyHeight;
                enemyVy10 = 0;
            }

            player.setLocation(pX,pY);
            enemyRectangle10.setLocation(enemyX10,enemyY10);
            if (enemyRectangle10.intersects(player) == true){
                currentHP = currentHP - 1;
            }
            else {
                if(enemyY10+enemyHeight<pY+pHeight){
                    enemyVx10 = -enemyStepX10;
                    monsteranimation10 = monster2;
                }
                else if(enemyX10 < pX){
                    enemyVx10 = enemyStepX10;
                    monsteranimation10 = monster1;
                }
                else if (enemyX10 > pX){
                    enemyVx10 = -enemyStepX10;
                    monsteranimation10 = monster2;
                }
                else if (enemyY10 > pY && enemyVy10 == 0){
                    try{Thread.sleep(90);} catch(Exception e){}
                    enemyVy10 = -(JUMP);
                }
            }
        }else{
            enemyRectangle10.setLocation(0,0);
        }
    }

    public static void PlatformBounds(){
        if ((pX+pWidth>plat1X && pX< plat1X+platWidth) && (pY+pHeight)>plat1Y && Vy>0){ //platform ounds for player
            pY = plat1Y - pHeight;
            Vy = 0;
        }

        if ((pX+pWidth>plat2X && pX< plat2X+platWidth) && (pY+pHeight)>plat2Y && Vy>0){
            pY = plat2Y - pHeight;
            Vy = 0;
        }
        if(pX<0){
            pX = -stepX;
        }else if(pX+pWidth>1000){
            pX = pX-stepX;
        }
        //Enemy
       
        if (enemyX+enemyWidth>plat1X && enemyX< plat1X+platWidth && enemyY+enemyHeight>plat1Y && enemyVy>0){ //Platform bounds for enemy1
            enemyY = plat1Y - enemyHeight;
            enemyVy = 0;
        }

        if (enemyX+enemyWidth>plat2X && enemyX< plat2X+platWidth && enemyY+enemyHeight>plat2Y && enemyVy>0){
            enemyY = plat2Y - enemyHeight;
            enemyVy = 0;
        }
        if(enemyX<0){
            enemyX = -enemyStepX;
        }else if(enemyX+pWidth>1000){
            enemyX = enemyX-enemyStepX;
        }

        if (enemyX2+enemyWidth>plat1X && enemyX2< plat1X+platWidth && enemyY2+enemyHeight>plat1Y && enemyVy2>0){ //Platform bounds for enemy2
            enemyY2 = plat1Y - enemyHeight;
            enemyVy2 = 0;
        }

        if (enemyX2+enemyWidth>plat2X && enemyX2< plat2X+platWidth && enemyY2+enemyHeight>plat2Y && enemyVy2>0){
            enemyY2 = plat2Y - enemyHeight;
            enemyVy2 = 0;
        }
        if(enemyX2<0){
            enemyX2 = -enemyStepX2;
        }else if(enemyX2+pWidth>1000){
            enemyX2 = enemyX2-enemyStepX2;
        }


        if (enemyX3+enemyWidth>plat1X && enemyX3< plat1X+platWidth && enemyY3+enemyHeight>plat1Y && enemyVy3>0){ //Platform bounds for enemy3
            enemyY3 = plat1Y - enemyHeight;
            enemyVy3 = 0;
        }

        if (enemyX3+enemyWidth>plat2X && enemyX3< plat2X+platWidth && enemyY3+enemyHeight>plat2Y && enemyVy3>0){
            enemyY3 = plat2Y - enemyHeight;
            enemyVy3 = 0;
        }
        if(enemyX3<0){
            enemyX3 = -enemyStepX3;
        }else if(enemyX3+pWidth>1000){
            enemyX3 = enemyX3-enemyStepX3;
        }

        if (enemyX4+enemyWidth>plat1X && enemyX4< plat1X+platWidth && enemyY4+enemyHeight>plat1Y && enemyVy4>0){ //Platform bounds for enemy4
            enemyY4 = plat1Y - enemyHeight;
            enemyVy4 = 0;
        }

        if (enemyX4+enemyWidth>plat2X && enemyX4< plat2X+platWidth && enemyY4+enemyHeight>plat2Y && enemyVy4>0){
            enemyY4 = plat2Y - enemyHeight;
            enemyVy4 = 0;
        }
        if(enemyX4<0){
            enemyX4 = -enemyStepX4;
        }else if(enemyX4+pWidth>1000){
            enemyX4 = enemyX4-enemyStepX4;
        }

        if (enemyX5+enemyWidth>plat1X && enemyX5< plat1X+platWidth && enemyY5+enemyHeight>plat1Y && enemyVy5>0){ //Platform bounds for enemy5
            enemyY5 = plat1Y - enemyHeight;
            enemyVy5 = 0;
        }

        if (enemyX5+enemyWidth>plat2X && enemyX5< plat2X+platWidth && enemyY5+enemyHeight>plat2Y && enemyVy5>0){
            enemyY5 = plat2Y - enemyHeight;
            enemyVy5 = 0;
        }
        if(enemyX5<0){
            enemyX5 = -enemyStepX5;
        }else if(enemyX5+pWidth>1000){
            enemyX5 = enemyX5-enemyStepX5;
        }

        if (enemyX6+enemyWidth>plat1X && enemyX6< plat1X+platWidth && enemyY6+enemyHeight>plat1Y && enemyVy6>0){ //Platform bounds for enemy6
            enemyY6 = plat1Y - enemyHeight;
            enemyVy6 = 0;
        }

        if (enemyX6+enemyWidth>plat2X && enemyX6< plat2X+platWidth && enemyY6+enemyHeight>plat2Y && enemyVy6>0){
            enemyY6 = plat2Y - enemyHeight;
            enemyVy6 = 0;
        }
        if(enemyX6<0){
            enemyX6 = -enemyStepX6;
        }else if(enemyX6+pWidth>1000){
            enemyX6 = enemyX6-enemyStepX6;
        }

        if (enemyX7+enemyWidth>plat1X && enemyX7< plat1X+platWidth && enemyY7+enemyHeight>plat1Y && enemyVy7>0){ //Platform bounds for enemy7
            enemyY7 = plat1Y - enemyHeight;
            enemyVy7 = 0;
        }

        if (enemyX7+enemyWidth>plat2X && enemyX7< plat2X+platWidth && enemyY7+enemyHeight>plat2Y && enemyVy7>0){
            enemyY7 = plat2Y - enemyHeight;
            enemyVy7 = 0;
        }
        if(enemyX7<0){
            enemyX7 = -enemyStepX7;
        }else if(enemyX7+pWidth>1000){
            enemyX7 = enemyX7-enemyStepX7;
        }

        if (enemyX8+enemyWidth>plat1X && enemyX8< plat1X+platWidth && enemyY8+enemyHeight>plat1Y && enemyVy8>0){ //Platform bounds for enemy8
            enemyY8 = plat1Y - enemyHeight;
            enemyVy8= 0;
        }

        if (enemyX8+enemyWidth>plat2X && enemyX8< plat2X+platWidth && enemyY8+enemyHeight>plat2Y && enemyVy8>0){
            enemyY8 = plat2Y - enemyHeight;
            enemyVy8 = 0;
        }
        if(enemyX8<0){
            enemyX8 = -enemyStepX8;
        }else if(enemyX8+pWidth>1000){
            enemyX8 = enemyX8-enemyStepX8;
        }

        if (enemyX9+enemyWidth>plat1X && enemyX9< plat1X+platWidth && enemyY9+enemyHeight>plat1Y && enemyVy9>0){ //Platform bounds for enemy9
            enemyY9 = plat1Y - enemyHeight;
            enemyVy9 = 0;
        }

        if (enemyX9+enemyWidth>plat2X && enemyX9< plat2X+platWidth && enemyY9+enemyHeight>plat2Y && enemyVy9>0){
            enemyY9 = plat2Y - enemyHeight;
            enemyVy9 = 0;
        }
        if(enemyX9<0){
            enemyX9 = -enemyStepX9;
        }else if(enemyX9+pWidth>1000){
            enemyX9 = enemyX9-enemyStepX9;
        }

        if (enemyX10+enemyWidth>plat1X && enemyX10< plat1X+platWidth && enemyY10+enemyHeight>plat1Y && enemyVy10>0){ //Platform bounds for enemy10
            enemyY10 = plat1Y - enemyHeight;
            enemyVy10 = 0;
        }

        if (enemyX10+enemyWidth>plat2X && enemyX10< plat2X+platWidth && enemyY10+enemyHeight>plat2Y && enemyVy10>0){
            enemyY10 = plat2Y - enemyHeight;
            enemyVy10 = 0;
        }
        if(enemyX10<0){
            enemyX10 = -enemyStepX10;
        }else if(enemyX10+pWidth>1000){
            enemyX10 = enemyX10-enemyStepX10;
        }
    }

    static class GraphicsPanel extends JPanel{ 
        public GraphicsPanel(){
        setFocusable(true);
             requestFocusInWindow();
        }

        public void paintComponent(Graphics g){ 
            super.paintComponent(g);
         
            try{ //Background image
                background = ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Background.png"));
            } catch (IOException ex){}

            g.drawImage(background, 0, 0, this);

            try{ //Floor image
                floor = ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Floor.png"));
            } catch (IOException ex){}

            g.drawImage(floor,floorX,floorY,this);
           
            //player image/animation
            try{
                walkRight1 = ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Walk right/sprite_10.png"));
                walkRight2 =  ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Walk right/sprite_16.png"));


                walkLeft1 =  ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Walk left/sprite_18.png"));
                walkLeft2 =  ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Walk left/sprite_24.png"));

                attack1 =  ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Attack/attack0.png"));
                attack2 =  ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Attack/attack1.png"));
            }
            catch(IOException ex){}
            if(startingAnimation){
                animation = walkRight1;
            }
            g.setColor(Color.black); //LEVEL label
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString(title, 450, 50);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("ArrowKeys to move, Spacebar to Attack!", 350, 75);

            if(currentHP > 0){ //player image
            g.drawImage(animation, pX,pY,this);
            g.setColor(Color.black); //healthbar for player
            g.fillRect(pX, pY, 2*pWidth, 5);
            g.setColor(Color.yellow);
            g.fillRect(pX, pY,  2*((pWidth * (currentHP)) / maxHP), 5);
            }
           
            g.setColor(Color.GREEN); //Platforms
            g.fillRect(plat1X,plat1Y,platWidth,platHeight);
           
            g.fillRect(plat2X,plat2Y,platWidth,platHeight);

            g.setColor(new Color(0,0,0,100));
            g.fillRect(0, 0, 200, 75);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("play again?", 30, 50);

            try{ //get files for monster
                monster1 = ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Monster/monster0.png"));
                monster2 = ImageIO.read(new File("C:/Users/zhang/OneDrive/Desktop/Game photos/Monster/monster1.png"));
            } catch (IOException ex){}

   
            if(enemys[0]){ //enemy image/animation
            g.drawImage(monsteranimation, enemyX,enemyY, this);
            g.setColor(Color.black); //enemy healthbar
            g.fillRect(enemyX, enemyY, enemyWidth, 5);
            g.setColor(Color.red);
            g.fillRect(enemyX, enemyY,  (enemyWidth * enemyCurrentHP) / enemyMaxHP, 5);

            }

            if(enemys[1]){//enemy2 animation
                g.drawImage(monsteranimation2, enemyX2,enemyY2, this);
                g.setColor(Color.black);
                g.fillRect(enemyX2, enemyY2, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX2, enemyY2,  (enemyWidth * enemyCurrentHP2) / enemyMaxHP2, 5);
   
                }
            if(enemys[2]){//enemy3 animation
                g.drawImage(monsteranimation3, enemyX3,enemyY3, this);
                g.setColor(Color.black);
                g.fillRect(enemyX3, enemyY3, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX3, enemyY3,  (enemyWidth * enemyCurrentHP3) / enemyMaxHP3, 5);
   
                }
            if(enemys[3]){//enemy4 animation
                g.drawImage(monsteranimation4, enemyX4,enemyY4, this);
                g.setColor(Color.black);
                g.fillRect(enemyX4, enemyY4, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX4, enemyY4,  (enemyWidth * enemyCurrentHP4) / enemyMaxHP4, 5);
   
                }
            if(enemys[4]){//enemy5 animation
                g.drawImage(monsteranimation5, enemyX5,enemyY5, this);
                g.setColor(Color.black);
                g.fillRect(enemyX5, enemyY5, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX5, enemyY5,  (enemyWidth * enemyCurrentHP5) / enemyMaxHP5, 5);
   
                }
            if(enemys[5]){//enemy6 animation
                g.drawImage(monsteranimation6, enemyX6,enemyY6, this);
                g.setColor(Color.black);
                g.fillRect(enemyX6, enemyY6, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX6, enemyY6,  (enemyWidth * enemyCurrentHP6) / enemyMaxHP6, 5);
   
                }
            if(enemys[6]){//enemy7 animation
                g.drawImage(monsteranimation7, enemyX7,enemyY7, this);
                g.setColor(Color.black);
                g.fillRect(enemyX7, enemyY7, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX7, enemyY7,  (enemyWidth * enemyCurrentHP7) / enemyMaxHP7, 5);
   
                }
            if(enemys[7]){//enemy8 animation
                g.drawImage(monsteranimation8, enemyX8,enemyY8, this);
                g.setColor(Color.black);
                g.fillRect(enemyX8, enemyY8, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX8, enemyY8,  (enemyWidth * enemyCurrentHP8) / enemyMaxHP8, 5);
   
                }
            if(enemys[8]){//enemy9 animation
                g.drawImage(monsteranimation9, enemyX9,enemyY9, this);
                g.setColor(Color.black);
                g.fillRect(enemyX9, enemyY9, enemyWidth, 5);
                g.setColor(Color.red);
                g.fillRect(enemyX9, enemyY9,  (enemyWidth * enemyCurrentHP9) / enemyMaxHP9, 5);
   
                }
            if(enemys[9]){//enemy10 animation
                g.drawImage(monsteranimation10, enemyX10,enemyY10, this);
                g.setColor(Color.black);
                g.fillRect(enemyX10, enemyY10, enemyWidth, 5);
                g.setFont(new Font("Arial", Font.PLAIN, 25));
                g.drawString("BOSS", enemyX10, (enemyY10-10));
                g.setColor(Color.red);
                g.fillRect(enemyX10, enemyY10,  (enemyWidth * enemyCurrentHP10) / enemyMaxHP10, 5);
   
                }
            g.setColor(new Color(0,0,0,0));//bullet animation
            for (int i=0; i<1; i++){
                if (bullet)
                    g.fillOval(bulletX,bulletY,bulletWidth,bulletHeight);
            }
           
        }
    }

   

    static class MyKeyListener implements KeyListener{   
        public void keyPressed (KeyEvent e){
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT){//if left arrow key is pressed make player walk left and change bullet direction to left
                animation = walkLeft1;
                animation = walkLeft2;
                Vx = -stepX;
                bulletSpeed = -35;
                direction = -1;
                startingAnimation = false;
               
            } else if (key == KeyEvent.VK_RIGHT){//if right arrow key is pressed make player walk right and change bullet direction to right
                animation = walkRight1;
                animation = walkRight2;
                Vx= stepX;
                bulletSpeed = 35;
                direction = 1;
                startingAnimation = false;

            } else if (key == KeyEvent.VK_UP && Vy== 0){//if press up arrow key player jumps
                Vy = -JUMP;
                startingAnimation = false;

            } else if (key == KeyEvent.VK_SPACE && direction <0){// if press spacebar and player is facing left shoot bullet left
                sword.setFramePosition(0);  //resets the soundeffect time
                sword.start(); //starts the sword sound effect
                bulletX = pX + pWidth/2 - bulletWidth/2;
                bulletY = pY;
                animation = attack1; //change animation into attack
                bullet = true;
                currentBullet = (currentBullet + 1);

            }else if (key == KeyEvent.VK_SPACE && direction >0){ // if press spacebar and player is facing right shoot bullet right
                sword.setFramePosition(0); 
                sword.start();
                bulletX = pX + pWidth/2 - bulletWidth/2;
                bulletY = pY;
                animation = attack2;
                bullet = true;
                currentBullet = (currentBullet + 1);
            }
           
        }
       
        public void keyReleased(KeyEvent e){  
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_LEFT){ //if let go of key make player/animation stop
                Vx = 0;
                animation = walkLeft1;  
            } else if (key == KeyEvent.VK_RIGHT){
                Vx= 0;
                animation = walkRight1;  
            }
            if (key == KeyEvent.VK_ESCAPE){ //if press esc key then game closes and stops music
                gameFrame.dispose();
                music.stop();
            }
        }      
       
        public void keyTyped(KeyEvent e){    
        }      
    }
    static class MyMouseListener implements MouseListener{
        public void mouseClicked(MouseEvent e){   // checks if play again box is clicked
            int mouseX = e.getX();
            int mouseY = e.getY();
           
            if((mouseX>0 && mouseX<200) && (mouseY>0 && mouseY<75)){ // if play again is clicked then reset values and start from beginning level
                for (int a=0; a<level;a++){
                    enemys[a] = false;
                }
                dead = 0;
                level = 0;
                loop = true;
            }
        }
        public void mousePressed(MouseEvent e){  
        }
        public void mouseReleased(MouseEvent e){ 
        }
        public void mouseEntered(MouseEvent e){  
        }
        public void mouseExited(MouseEvent e){   
        }
    }   
   
}