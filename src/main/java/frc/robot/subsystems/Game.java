// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Random;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Game extends SubsystemBase {
  /** Creates a new Game. */
  double tick = .5;
  boolean started = false;

  static final int height = 13;
  static final int width = 24;
  private int length = 3;
  private int HighScore = 3;
  private char direction = 'R';
  private int headX = 5;
  private int headY = height / 2;
  public int tailX[] = new int[width * height];
  public int tailY[] = new int[width * height];
  public int fruitX;
  public int fruitY;
  public int board[][] = new int[height + 1][width + 1];

  Timer timer = new Timer();

  public Game() {
    tailX[0] = 5;
    tailY[0] = headY;
    tailX[1] = 4;
    tailY[1] = headY;
    tailX[2] = 3;
    tailY[2] = headY;
    
    timer.start();
    createFruit(board);
    updateGrid();
    updateGraphics(board);

    SmartDashboard.putNumber("High Score", 3);
  }

  @Override
  public void periodic() {
    if (timer.get() > tick && started) {
      timer.reset();
      tick();
    }
  }

  public void start() {
    started = true;
  }

  public void tick() {
    moveHead();
    moveTail();
    checkCollision(board);
    checkFruit(board);
    updateGrid();
    updateGraphics(board);
  }

  public void moveHead() {
    if (direction == 'D') {
      headY--;
    } else if (direction == 'U') {
      headY++;
    } else if (direction == 'L') {
      headX--;
    } else if (direction == 'R') {
      headX++;
    } else {
      System.out.println("ERROR: Invalid direction");
    }
  }

  public void moveTail() {
    for (int i = length - 1; i > 0; i--) {
      tailX[i] = tailX[i - 1];
      tailY[i] = tailY[i - 1];
    }
    tailX[0] = headX;
    tailY[0] = headY;
  }

  public void reset() {
    length = 3;
    direction = 'R';
    
    headX = 5;
    headY = height / 2;
    tailX[0] = 5;
    tailY[0] = headY;
    tailX[1] = 4;
    tailY[1] = headY;
    tailX[2] = 3;
    tailY[2] = headY;
    
    createFruit(board);
    updateGrid();
    updateGraphics(board);
  }
  
  public void checkCollision(int[][] board) {
    if ((headX < 0 || headX >= width || headY < 0 || headY >= height) || board[headY][headX] == 1) {
      reset();
    }
  }
  
  public void checkFruit(int[][] board) {
    if (headX == fruitX && headY == fruitY) {
      length++;
      if (length > HighScore) {
        HighScore = length;
        SmartDashboard.putNumber("High Score", HighScore);
      }
      createFruit(board);
    }
  }

  public void updateGrid() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        board[y][x] = 0;
      }
    }
    for (int i = length - 1; i > -1; i--) {
      board[tailY[i]][tailX[i]] = 1;
    }
    board[fruitY][fruitX] = 2;
  }
  
  public void updateGraphics(int[][] board) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (board[y][x] == 1) {
          SmartDashboard.putBoolean(x + ", " + y, true);
        } else if (board[y][x] == 2) {
          SmartDashboard.putBoolean(x + ", " + y, true);
        } else {
          SmartDashboard.putBoolean(x + ", " + y, false);
        }
      }
    }
  }
  
  public void createFruit(int[][] board) {
    Random rand = new Random();
    int x = rand.nextInt(width-1);
    int y = rand.nextInt(height-1);
    while (board[y][x] != 0) {
      x = rand.nextInt(width-1);
      y = rand.nextInt(height-1);
    }
    board[y][x] = 2;
    fruitX = x;
    fruitY = y;
  }
  
  //Controls
  public void setDirection(char direction) {
    if (this.direction == 'D' && direction != 'U') {
      this.direction = direction;
    } else if (this.direction == 'U' && direction != 'D') {
      this.direction = direction;
    } else if (this.direction == 'L' && direction != 'R') {
      this.direction = direction;
    } else if (this.direction == 'R' && direction != 'L') {
      this.direction = direction;
    }
    System.out.println("Direction: " + direction);
  }
}
