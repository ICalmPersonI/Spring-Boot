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
If the feedback for a specified transaction is already in the database, respond with the HTTP Conflict status (409).
If the feedback has the wrong format (other than ALLOWED, MANUAL_PROCESSING, PROHIBITED), respond with the HTTP Bad Request status (400).
If the feedback throws an Exception following the table, respond with the HTTP Unprocessable Entity status (422).
If the transaction is not found in history, respond with the HTTP Not Found status (404).
```GET /api/antifraud/history``` shows the transaction history.\
```GET /api/antifraud/history/{(String) number}``` ransaction history for a specified card number.
## Blacklist controller
```POST /api/antifraud/suspicious-ip``` @RequestBody IP ip\
```GET /api/antifraud/suspicious-ip```\
```DELETE /api/antifraud/suspicious-ip/{(String) ip} ```\
```POST /api/antifraud/stolencard``` @RequestBody Card card\
```GET /api/antifraud/stolencard```\
```DELETE /api/antifraud/stolencard/{(String) number}```
## User controller
```POST /api/auth/user``` @RequestBody User user\
```GET /api/auth/list```\
```DELETE /api/auth/user/{(String) username}```\
```PUT /api/auth/role``` @RequestBody UserRoleRequest request\
```PUT /api/auth/access``` @RequestBody UserAccessRequest request
