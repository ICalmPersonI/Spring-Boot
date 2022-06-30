## About
This project demonstrates (in a simplified form) the principles of anti-fraud systems in the financial sector. For this project, we will work on a system with an expanded role model, a set of REST endpoints responsible for interacting with users, and an internal transaction validation logic based on a set of heuristic rules.
## Transaction
```POST /api/antifraud/transaction``` @RequestBody Transaction transaction\
```PUT /api/antifraud/transaction``` @RequestBody TransactionFeedback feedback\
```GET /api/antifraud/history```\
```GET /api/antifraud/history/{(String) number}```\
## Blacklist
```POST /api/antifraud/suspicious-ip``` @RequestBody IP ip\
```GET /api/antifraud/suspicious-ip```\
```DELETE /api/antifraud/suspicious-ip/{(String) ip} ```\
```POST /api/antifraud/stolencard``` @RequestBody Card card\
```GET /api/antifraud/stolencard```\
```DELETE /api/antifraud/stolencard/{(String) number}```\
## User
```POST /api/auth/user``` @RequestBody User user\
```GET /api/auth/list```\
```DELETE /api/auth/user/{(String) username}```\
```PUT /api/auth/role``` @RequestBody UserRoleRequest request\
```PUT /api/auth/access``` @RequestBody UserAccessRequest request\

## Examples

Example 1: ```POST /api/code/new``` with the following body:
```
{
    "code": "class Code { ...",
    "time": 0,
    "views": 0
}
```
Response: ```{ "id" : "7dc53df5-703e-49b3-8670-b1c468f47f1f" }```.
