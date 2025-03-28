// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.Game;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_Controller = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final Game m_game = new Game();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_Controller.a().onTrue(new InstantCommand(() -> m_game.start()));
    m_Controller.pov(90).onTrue(new InstantCommand(() -> m_game.setDirection('R')));
    m_Controller.pov(0).onTrue(new InstantCommand(() -> m_game.setDirection('U')));
    m_Controller.pov(270).onTrue(new InstantCommand(() -> m_game.setDirection('L')));
    m_Controller.pov(180).onTrue(new InstantCommand(() -> m_game.setDirection('D')));

    m_Controller.axisGreaterThan(1, -.1).onTrue(new InstantCommand(() -> m_game.setDirection('U')));
    m_Controller.axisGreaterThan(0, .1).onTrue(new InstantCommand(() -> m_game.setDirection('R')));
    m_Controller.axisGreaterThan(1, .1).onTrue(new InstantCommand(() -> m_game.setDirection('D')));
    m_Controller.axisGreaterThan(0, -.1).onTrue(new InstantCommand(() -> m_game.setDirection('L')));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
