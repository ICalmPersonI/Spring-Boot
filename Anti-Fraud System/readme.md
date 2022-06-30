# About
This project demonstrates (in a simplified form) the principles of anti-fraud systems in the financial sector. For this project, we will work on a system with an expanded role model, a set of REST endpoints responsible for interacting with users, and an internal transaction validation logic based on a set of heuristic rules.
# Endpoints
## Transaction
```POST /api/antifraud/transaction``` \
Request body:
```
{
  "transactionId": <Long>,
  "amount": <Long>,
  "ip": "<String value, not empty>",
  "number": "<String value, not empty>",
  "region": "<String value, not empty>",
  "date": "yyyy-MM-ddTHH:mm:ss",
  "result": "<String>",
  "feedback": "<String>"
}
```
```PUT /api/antifraud/transaction``` adds feedback for a transaction.\
Request body:
```
{
   "transactionId": <Long>,
   "feedback": "<String>"
}
```
If the feedback for a specified transaction is already in the database, respond with the HTTP Conflict status (409).\
If the feedback has the wrong format (other than ALLOWED, MANUAL_PROCESSING, PROHIBITED), respond with the HTTP Bad Request status (400).\
If the feedback throws an Exception following the table, respond with the HTTP Unprocessable Entity status (422).\
If the transaction is not found in history, respond with the HTTP Not Found status (404).\
```GET /api/antifraud/history``` shows the transaction history.\
```GET /api/antifraud/history/{(String) number}``` ransaction history for a specified card number.
## Blacklist controller
```POST /api/antifraud/suspicious-ip``` saves suspicious IP addresses to the database\
Request body:
```
{
   "ip": "<String value, not empty>"
}
```
Responses:
```200```
```
{
   "id": "<Long value, not empty>",
   "ip": "<String value, not empty>"
}
```
If an IP is already in the database, respond with the ```HTTP Conflict status (409)```.\
If an IP address has the wrong format, respond with the ```HTTP Bad Request status (400)```.
```GET /api/antifraud/suspicious-ip``` shows IP addresses in the database.\
Response:
```
[
    {
        "id": 1,
        "ip": "192.168.1.1"
    },
     ...
    {
        "id": 100,
        "ip": "192.168.1.254"
    }
]
```
```DELETE /api/antifraud/suspicious-ip/{(String) ip}``` deletes IP addresses from the database.\
Response:
```
{
   "status": "IP <ip address> successfully removed!"
}
```
If an IP is not found in the database, respond with the HTTP Not Found status (404).
If an IP address has the wrong format (not following the Description section rules), respond with the HTTP Bad Request status (400)
```POST /api/antifraud/stolencard``` saves stolen card data in the database.\
Request body:
```
{
   "number": "<String value, not empty>"
}
```
Response:
200
```
{
   "id": "<Long value, not empty>",
   "number": "<String value, not empty>"
}
```
If the card number is already in the database, respond with the HTTP Conflict status (409).
If a card number has the wrong format, respond with the HTTP Bad Request status (400).
```GET /api/antifraud/stolencard``` shows card numbers stored in the database.\
Response:
```
[
    {
        "id": 1,
        "number": "4000008449433403"
    },
     ...
    {
        "id": 100,
        "number": "4000009455296122"
    }
]
```
```DELETE /api/antifraud/stolencard/{(String) number}``` deletes a card number from the database.
Response:
200
```
{
   "status": "Card <number> successfully removed!"
}
```
If a card number is not found in the database, respond with the HTTP Not Found status (404).
If a card number follows the wrong format (look at the Description section), respond with HTTP Bad Request status (400).
## User controller
```POST /api/auth/user``` @RequestBody User user\
```GET /api/auth/list```\
```DELETE /api/auth/user/{(String) username}```\
```PUT /api/auth/role``` @RequestBody UserRoleRequest request\
```PUT /api/auth/access``` @RequestBody UserAccessRequest request
