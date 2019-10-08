var express = require('express');
var router = express.Router();

var thremostatController = require("../controllers/thermostatController")

// hvac ???
router.route('/thermostat/actions/heat')
  .post(thremostatController.heat)

router.route('/thermostat/actions/cold')
  .post(thremostatController.cold)

router.route('/thermostat/actions/off')
  .post(thremostatController.off)

router.route('/thermostat/properties/temperature')
  .get(thremostatController.temperature)

router.route('/thermostat/properties/state')
  .get(thremostatController.state)

module.exports = router;
