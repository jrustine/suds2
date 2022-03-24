# SUDS2, Sample Java REST application with PostgreSQL And Spring Boot

Another version of the dog grooming application, but this time with a relational database.

## Endpoints

| Method | Path | Description |
|------|-------------------------------|----------------------------------------|
| GET  | `/customer/`                | Retrieves all customers with their pets |
| GET  | `/customer/{phone number}` | Retrieves single customer |

## Notes

* Left the address in a JSON field, would probably break this into its own table or fields in the main one.
