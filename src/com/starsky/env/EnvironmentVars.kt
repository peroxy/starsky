package com.starsky.env

object EnvironmentVars {

    /**
     * Backend application's environment. Will default to Environment.DEV if no env variable is found.
     */
    val environment: Environment =
        Environment.values().firstOrNull { it.name == System.getenv("STARSKY_ENVIRONMENT") } ?: Environment.DEV

    /**
     * Connection string with username and password appended.
     * This is always present in Heroku container and is only used if environment is production.
     **/
    val connectionString: String = System.getenv("JDBC_DATABASE_URL")
        ?: throw java.lang.IllegalStateException("JDBC_DATABASE_URL environment variable is missing!")

    /**
     * Postgres password that is used to connect to local development docker database.
     * This is only used for development purposes and is not used in production.
     **/
    val postgresPassword: String = System.getenv("POSTGRES_PASSWORD")
        ?: throw java.lang.IllegalStateException("POSTGRES_PASSWORD environment variable is missing!")

    /**
     * JWT secret value used for generating authentication bearer tokens.
     **/
    val jwtSecret: String = System.getenv("STARSKY_JWT_SECRET")
        ?: throw java.lang.IllegalStateException("STARSKY_JWT_SECRET environment variable is missing, API authentication will not work!")
}