package gui

import java.awt.Dimension

import digitaltwin.ThermostatDT.State
import javax.swing.{Box, JLabel, JPanel, JSlider}

class ThermostatRegulatorPanel() extends JPanel {

  private val statusLabel = new JLabel("Status: ")
  private val temperatureLabel = new JLabel("Current temperature: ")
  private val temperatureSlider = new JSlider(-10, 45)
  private val verticalBox = Box.createVerticalBox()

  temperatureSlider.setMinimumSize(new Dimension(this.getWidth, temperatureSlider.getHeight))
  temperatureSlider.setMajorTickSpacing(5)
  temperatureSlider.setMinorTickSpacing(1)
  temperatureSlider.setPaintLabels(true)
  temperatureSlider.setPaintTicks(true)

  verticalBox.add(statusLabel)
  verticalBox.add(temperatureLabel)
  verticalBox.add(temperatureSlider)

  add(verticalBox)

  def updateCurrentTemperature(temperature: Double): Unit = {
    temperatureLabel.setText(s"Current temperature: $temperature")
  }

  def updateCurrentStatus(state: State): Unit = {
    statusLabel.setText(s"Status: $state")
  }

  def addChangeTargetTemperatureListener(listener: Double => Unit): Unit = {
    temperatureSlider.addChangeListener(e => {
      listener(temperatureSlider.getValue)
    })
  }
}
