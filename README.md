11/10/2018

Project developed by Guilherme Rodrigues Ludescher, Computer Engineering graduate at Escola Politécnica da USP.

Contact me at guilherme.ludescher@usp.br or guiludescher@gmail.com

This app is part of a programming challenge and is meant to implement a version of the classic game of Checkers for 2 players.

=================================================================================

1. The Rules:

The rules applied are the rules from the World Checkers Draughts Federation, as available for download in the link below. The main part, important for the app itself, is specified below:

Offical rules: http://www.usacheckers.com/downloads/WCDF_Revised_Rules.doc

2. Specific names in the code:

    2.1. "Checker": Any piece in the game is called a Checker;

    2.2. "Pawn": Any regular Checker, described as "Man" in the official rules;

    2.3. "Queen": When Pawns reach the farthest row forward, they become Queens, described as "King" in the official rules;

    2.4. "Space": Any square in the board that can be occupied by a Checker;
    
    2.5. "Hunter": A Checker that can capture an opposing Checker in the current turn;
    
    2.6. "Prey": A Checker that can be captured by an opposing Checker in the current turn;
    
3. Code structure:

    3.1. The classes: 
    
        3.1.1. MainAcitvity: Prompts when the user starts the app. It asks for the players' names and awaits 
        for the user to start the game.
      
        3.1.2. BoardActivity: Displays the board and allow users to select and move checkers, the game is 
        effectively played here.
      
        3.1.3. GameManager: This activity controls the flow of the game, checking if moves are correct and 
        going through the steps of each round.
      
   3.2. The board: The board is a 8x8 matrix of integers in which only the spaces with either an even line and odd column or an odd line and even column can be used. E.g.: the spaces [0][1] and [6][5] can be used, but the spaces [0][0] or [3][7] cannot.
   
        3.2.1. The Checkers: Every Checker is represented by an integer in a space of the board. Empty spaces 
        are "0", pawns are "1" and queens are "2". Dark pieces are of negative value and light pieces are of 
        positive value. E.g: a dark queen is represented by -2. It is easy, then, to find out the color of a 
        Checker by using the Math.signum() function and its value by using the Math.abs() function.
        
        3.2.2. The buttons: every space in the board is a button. Therefore, the user doesn't effectively selects
        a Checker, rather selecting the space the Checker is in.
        
   3.3. Notable methods:
        
        3.3.1. GameManager.nextStepInRound(int space): This is the method that really controls the flow of the game. It
        is managed by states in the round: WAITING_ORIGIN_INPUT and WAITING_END_INPUT. Also controlled by the
        results of the methods selectedOriginOK() and selectedEndOK(). It is called everytime a button is pressed
        on the BoardActivity and responds with an error code or correctness confirmation.
        
        3.3.2. GameManager.lookForHunters(): As it is in the rules of the game, if a Checker may capture an 
        opponent's Checker, it MUST capture it. Therefore, at the start of each round, this functions verifies
        if any of the Checkers of the current player is a "Hunter", that is, it can capture another Checker.
        If so, the player won't be able to do any move besides a capture. 
        
        3.3.3 GameManager.isHunter(int space): This is a complement of the function above. It checks if there are any 
        adjacent opposing Checkers to the one that is passed and, if so, it is verified whether the space right
        "behind" the checker that can be captured is empty. Being those conditions true, it returns true.
        
        3.3.4. GameManager.colorOf(int space) and GameManager.valueOf(space): These methods return, respectively, the 
        values of Math.abs() and Math.signum(), as those functions can be used to find information about a Checker.
        The used of those methods is purely for cleanliness of code.
        
4. Possible future upgrades and new functionalities:

    4.1. Different Checkers/spaces colors: Possible functionality where the user can pick from predefined colors which to play with.
    
    4.2. Sounds: Possible implementation of sounds for each different action, such as a move, a capture or the end of game.
    
    4.3. Different languages: Although the written parts are minimal, different languages could be implemented.
    
    4.4. Undo: possibility for the player to undo its last move.

5. Final Considerations: This is a project for a programming challenge. Yet, I tried to make it look good and be smooth and intuitive to play. Even though I don't intend to deploy the app in the Play Store, I'd be glad with feedback and any possible bug reports!
