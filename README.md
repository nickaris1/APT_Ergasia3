# Proigmenes texnikes programatismou - Ergasia 3 

## Issaris Nikolaos
## AM: 1066471
***
Ergasia3Application: To programma pou apantaei ta erwtimata tis 3hs ergasias
	To programma dexete HTTP POST Request sto directory /temp me dedomena se morfh JSON thn timi tis 8ermokasias se dekadiko ari8mo 
eg 
```JSON
{temperature : 19.2}
``` 

kai epita kanei publish tin 8ermokrasia mesw MQTT message.

***
MQTTSubscriber: Application to test if the MQTT message is published
***
HttpPostRequest: Application that sends an POST Request to server in order to test the functionality
