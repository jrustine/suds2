# SUDS2, Sample Java REST application with PostgreSQL And Spring Boot

Another version of the dog grooming application, but this time with a relational database and a service layer.

## Endpoints

| Method | Path | Description |
|------|-------------------------------|----------------------------------------|
| GET  | `/customer/`                | Retrieves all customers with their pets |
| GET  | `/customer/pets`             | Retrieves just pets |
| GET  | `/customer/{phone number}` | Retrieves single customer |
| POST | `/customer/`                 | Saves customer with pets |
| GET  | `/groomer/`                    | Retrieves all groomers |
| GET  | `/groomer/{employee number}` | Retrieves single groomer |
| POST | `/groomer/`                    | Saves groomer |

## Notes

* Left the customer address in a JSON field, would probably break this into its own table or fields in the main one.
