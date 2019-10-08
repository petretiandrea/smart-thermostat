package gui

import java.awt.BorderLayout

import javax.swing.JFrame

class ThermostatGUI extends JFrame {

  private val borderLayout = new BorderLayout()
  val regulatorPanel = new ThermostatRegulatorPanel()

  setLayout(borderLayout)
  this.add(regulatorPanel, BorderLayout.CENTER)
  this.setSize(500, 300)
  this.setVisible(true)
}
