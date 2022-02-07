//Name: Nguyen Thanh Vinh
//Net ID: 31810937
//Student ID: 659824967
//I work alone

Program status: This program works perfectly even though you may experience lag (I have no idea why).

Program design:
	- I make a JFrame then creates two JPanels like the design you suggested.
	- The top panel is called "topPanel" and the bottom panel is called "Board".
	- topPanel adds a button called "New Game", 2 JTextArea called "turn" and "stats".
	- I use insets to make spaces between buttons to make it look better.
	- You have to press "New Game" to start a new game if it ends and even if it has not ended, pressing
	  "New Game" will also clear the "board" panel but does not clear the "stats" box.
	- The "turn" text area displays player's turn and what the player is using (X or O). It also shows the 
	  result of the game (win, lose, or tie).
	- The "stats" text area displays total wins of each player and the number of match tie.
	- I set both text fields to not editable so no one can change it. The frame is also nor resizable.
	- The "board" panel overlays the JFrame which will have black grids on it. If you click on a box, depends 
	  on who's turn it is, it will draw a blue O or a red X.
Outer class function:
	- This program has 2 classes. The public class is used to declare all variables and creating a JFrame called "frame".
	- First, I initialize the game by layout the panels and the frame. (like the design above).
	- The board is actually an array of char called "position". Assign everything to empty char.
	- Then, I will create action for the button "New Game". If action is detected, I will assign the "position" back to empty
	  char so it only reset animation but not the stats.
Inner class function:
	- The row array is to indicate ways for a player to win. By this i mean we had an array "position" to display all variables 
	  in the board so it is numbered from 0 to 8.   
	  Visual: 0 1 2
	          3 4 5
	          6 7 8
	  So when I list {0,2} it is the first row.
	- Next, I draw the grids one by one in each third of the panel so it creates a 3x3 table.
	- Next, I draw the X and O at the box with the respective char(X or O).
	- The X and O are filled in the array "position" by action listener. If mouse is clicked in a box, the function will
	  calculate which position in the array fits it. The function will get coordinate of x and yof the mouse pressed and
	  times it by 3 and devide the length of each side. This will return a number from 0 to 2. After that, it will take 
	  the x plus the y times 3 because of the way we number elements in our array. This will return 0 to 8 which corresponse
	  to the right element in our "position" array.
	- Then, it will fill that position in the array with either X or O.
	- After each click, I check the won method and the isDraw method.
	- If someone wins or the board is full, the program will call the newGame() method which will stop you from drawing 
	  anymore on the board. This is achieved by the boolean variable "active" which turn false if the newGame() is called.
	- The won method uses the array "rows". This array is a 8x2 array which contains every row of the board in each row of
	  its array. This method will call another method called "testRow" which will take in the content of the row in the
	  "rows" array and a character representing a player. It will test if in that row, every element is the same as the
	  player's symbol(X or O).
