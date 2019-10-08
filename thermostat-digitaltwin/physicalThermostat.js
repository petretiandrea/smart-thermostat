const MAX_TEMP = 45
const MIN_TEMP = -10
const STEP_RATE = 0.10

const STATES = ["heat", "cold", "off"]

class PhysicalThermostat {

    constructor() {
        this.action = undefined
        this.temperature = Math.floor(Math.random() * (MAX_TEMP - MIN_TEMP + 1)) + MIN_TEMP;
        this.changeState("off")
    }

    changeState(newState) {
        let validState = STATES.find(state => state == newState)
        if(!validState) return;
        switch(validState) {
            case "heat": this.startHeating(); break
            case "cold": this.startCold(); break
            case "off": this.stop(); break
        }
        this.state = validState
    }

    startHeating() {
        this.action && this.action.cancel()
        this.action = new RepationAction(() => {
            this.temperature += STEP_RATE
        })
    }

    startCooling() {
        this.action && this.action.cancel()
        this.action = new RepationAction(() => {
            this.temperature -= STEP_RATE
        })
    }

    stop() {
        this.action && this.action.cancel()
    }
}

function RepationAction(action, period = 500) {
    let actionId = setInterval(() => action(), period)
    this.cancel = function() {
        clearInterval(actionId)
    }
}

module.exports = PhysicalThermostat