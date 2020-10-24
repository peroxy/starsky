# Starsky backend
Starsky backend represents the API and database portion of starsky application for employee scheduling.

It uses Kotlin and OpenJDK for REST API operations and PostgreSQL for data storage.

### Requirements 
- [docker](https://docs.docker.com/get-docker/) 
- [docker-compose](https://docs.docker.com/compose/install/) (at least 3.3 version support)

### Development
Please note that this has only been tested with docker on Ubuntu 20.04.
1. Download source files:
 
```properties
git clone https://github.com/peroxy/starsky-backend.git
```

2. Go to root directory:
 
```properties
cd starsky-backend
```

3. You must specify PostgreSQL password for `starsky` user and JWT secret for API authentication. 

Create a `.env` file and specify environment variables `POSTGRES_PASSWORD` and `STARSKY_JWT_SECRET`:
 
```properties
echo "POSTGRES_PASSWORD=password" > .env
echo "STARSKY_JWT_SECRET=secret" >> .env
```
    
   JWT secret will be used to generate bearer tokens for clients. Easy way to generate a strong JWT secret by using OpenSSL:
   
```properties
openssl rand --base64 64
```
 
   Environment variables specified in `.env` file will be automatically used by `docker-compose`.
4. Build and run the database and API:
 
```properties
docker-compose up
```
   
5. You will now be able to access:
- API at http://localhost:8080/
- database at http://localhost:5432/ 

 
You can login to `starsky` database with username `starsky` and password specified in `.env` file.
