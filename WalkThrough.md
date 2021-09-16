# Bridge Logistics Walkthrough

1. After you finish running the demo and as a recap, after following these steps:
   1. `make docker`
   2. wait
   3. `make start-readers`
   4. wait
   5. `source venv/bin/activate`
   6. `make demo`

You get this:

![make demo](./docs/images/Screenshot%202021-09-16%20at%2020.40.21.png "make demo")

You are now ready to continue your investigatio

2. Connect to PostgreSQL

Connection details:

```properties
port=5432
host=localhost
username=postgres
password=admin
```

Check table `trains_log` with filter:

```sql
check_in_out='CHECKIN' or check_in_out='CHECKOUT'
```

![postgresql first read](./docs/images/Screenshot%202021-09-16%20at%2020.40.39.png "PostgreSQL first read")

3. Calculate the weight of the special `agent + clothes + suitcase`:

![special agent](./docs/images/Screenshot%202021-09-16%20at%2020.41.03.png "special agent")

This 93 is calculated as `434` - `341` = `93`.

The agent changed carriage to chase the `spy`. Since the `spy` jumped off the moving train, the weight contribution is not seen.

4. Calculate the weight of the `spy + clothes + special equipment`

![secret spy](./docs/images/Screenshot%202021-09-16%20at%2020.41.38.png "secret spy")

This 162 is calculated as `1072` - `817` - `93` = `162`

The `spy` and the `agent` both left the first carriage where they were. This means that the carriage lost the weight contributions of the `spy` and the `agent`.

5. Connect to Cassandra

```properties
port=9042
host=localhost
username=cassandra
password=cassandra
```

6. Finding out who the special agent is

We are not supposed to do this in the story, but we can any way.
We just need to filter for 93 in the weight column

![special agent name](./docs/images/Screenshot%202021-09-16%20at%2020.43.12.png "special agent name")

In our example `Monica Avocado` is the `Special Agent`.

7. Finding out who the secret spy is

This is the goal of our game. Essentially we repeat the step above.

![secret spy name](./docs/images/Screenshot%202021-09-16%20at%2020.42.54.png "secret spy name")

Our secret spy is `Katy Pear`.

8. Result

Finally, by typing `Katy Pear` (case-sensitive), we win the game!

![Game Over](./docs/images/Screenshot%202021-09-16%20at%2020.43.55.png "Game Over")


9. A Fail case

Should you fail, you'll get something different.

![Game Over](./docs/images/Screenshot%202021-09-16%20at%2021.15.11.png "Game Over")
