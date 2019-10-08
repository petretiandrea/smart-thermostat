## Thermostat Digital Twin
Application written in nodejs:
- folder: thermostat-digitaltwin
- npm start

##### GET /thermostat/properties/temperature

> 200 OK
> ```
> {
> 	temperature: 20.3
> }
> ```


##### GET /thermostat/properties/status
> 200 OK
> ```
> {
> 	state: "heat" //or "cold", "off"
> }
> ```


##### POST /thermostat/actions/heat
> 202 Accepted

##### POST /thermostat/actions/cold
> 202 Accepted


##### POST /thermostat/actions/off
> 202 Accepted
