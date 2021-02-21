This folder contains a `docker-compose` file capable of creating a PostgreSQL container with PGAdmin.

Before the first execution of the docker-compose, run the following command on the local-database folder:

`mkdir pgadmin-data && sudo chown -R 5050:5050 pgadmin-data`

This command changes the owner of the volume data so pgadmin can access it.

You can access PGAdmin at [http://localhost:5050](http://localhost:5050), using:
```
User: admin@burger-builder.com
Password: admin
```

To access the `Burger Builder` server, use password `burger-builder`.
