
var PhysicalThermostat = require("../physicalThermostat")

// it could be a bridge to real thermostat.
let thermostat = new PhysicalThermostat()

exports.heat = function(req, res) {
    thermostat.changeState("heat")
    setResponseAccepted(res)
}

exports.cold = function(req, res) {
    thermostat.changeState("cold")
    setResponseAccepted(res)
}

exports.off = function(req, res) {
    thermostat.changeState("off")
    setResponseAccepted(res)
}

exports.state = function(req, res) {
    setReponseOk(res, {
        state: thermostat.state
    })
}

exports.temperature = function(req, res) {
    let temperature = thermostat.temperature
    setReponseOk(res, {
        temperature: temperature
    })
}

function setResponseAccepted(res, data=null) {
    res.status(202).json(data)
}

function setReponseOk(res, data) {
    res.status(200).json(data)
}
