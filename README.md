# Algorithms_Assignment_1
The repo for algorithms assignment 1 by Andrew cox and Joshua corrigan.


# Key for Movements.
 - we only store if we can move right or down
   - 0 - Both Right and Down are closed
   - 1 - Only Right is open
   - 2 - Only Below is open
   - 3 - Both are open
 - How to move left or below? 
   - Check those cells to see if they are open on the right or bleow
   - This is done as The left wall of the current cell is the right wall of the neighboring cell the  left
     - Dumb idea on how it could work if(Both Closed) -> Check Left then Check Right.

# How to generate the maze
   - We will be using a random walk method as denoted in the specs. 
   - Example in python: https://www.geeksforgeeks.org/random-walk-implementation-python/
   - I think what it wants is for us to move paths going from a random point a random number of steps until the maze is completed