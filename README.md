# Meals Calendar Backend
Simple calorie tracking CRUD web app with social login

## Technologies
* Spring Boot
* Spring Data JPA
* Spring Security
* OAuth2
* JUnit
* Mockito
* MS SQL Server
* H2
* Maven
* Lombok
* Docker
* Jenkins

## Usage

### Environment Variables
* `DB_URL`: Full database connection url (including `jdbc:sqlserver://`)
* `DB_USER`: Database username
* `DB_PASS`: Database password
* `GOOGLE_CLIENT_ID`: Google OAuth 2.0 Client ID
* `GOOGLE_CLIENT_SECRET`: Google OAuth 2.0 Client Secret
* `TOKEN_SECRET`: JWT Secret

### API
* Default context: `/meals-calendar/api`
* Google account required for social login to obtain Bearer token
* Tokens expire in 24 hours

### Companion Projects
* [tgcoding/meals-calendar-ui](https://github.com/tgcoding/meals-calendar-ui): React frontend
* [tgcoding/meals-calendar-infrastructure](https://github.com/tgcoding/meals-calendar-infrastructure): Kubernetes configuration for entire Meals Calendar project
