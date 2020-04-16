from random import random as rn
from random import seed
import random
# For our simulation we are using a converted simulation from minutes to hours.
# This means that whereas in the real case we would find that the train would take on average 120 minutes to get to a bridge, in our example we are using 120 seconds to simulate.
print(random.randint(80, 120))

# generate some random numbers
print(rn(), rn(), rn())
# reset the seed
seed(1)
# generate some random numbers
print(rn(), rn(), rn())
