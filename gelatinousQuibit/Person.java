package edu.temple.datastructures.dyee.oop.assignment.episode1;
public class Person extends Creature {
    //becomes true if person sees monster
    private boolean haveSeenMonster = false;
    //becomes true if person can currently see monster. false otherwise
    private boolean canSeeMonsterNow = false;
    //used for while loop to determine which direction person runs from monster
    private boolean canMove = false;
    //saves distance to monster
    private int distanceToMonster;
    //savevs opposite direction of monster
    private int oppositeDirectionOfMonster;
    //current direction is the statement returned and is used to determine if
    //person can continue in current direction;
    private int currentDirection = -1;
    //used to increment direction in while loop
    private int direction;
    //saves the last move every time a return statement is made
//helps to avoid feedback loop
    private int lastMove;
    //saves the move before every time a return statement is made
//helps to avoid triangulation
    private int moveBeforeLast;
    //increments every turn we cannot See Monster Now
    private int turnsWithoutSeeMonster;
//used for while statement, mainly so the code is easier to read
//is the converted value of oppositeDirectionOfMonster + incrementing direction value;
    private int awayFromMonsterPlus;
//used for while statement, mainly so the code is easier to read
//is the converted value of oppositeDirectionOfMonster - incrementing direction value;
    private int awayFromMonsterMinus;
    //saves longest distance to determine direction of longest distance person is able to see
    private int longestDistance=0;
    //saves direction of longest distance
    private int longestDistDirection;
    private static String name;
    /**
     * Constructs a Person in the given model, at the given position
     * in the field.
     *
     * @param model the model that controls this person.
     * @param row the row of the field containing this person.
     * @param column the column of the field containing this person.
     */
    public Person(Model model, int row, int column) {
        super(model, row, column);
    }
    //Convert function converts direction values back to the range 0-7
// when the direction value is incremented or decremented
    private int convert(int directionValue){
        if (directionValue > 7){
            return (directionValue-8);
        }else if (directionValue <0){
            return(7+directionValue);
        }else{
            return directionValue;
        }}
    int decideMove() {
//every move set canSeeMonsterNow to false and longest distance to zero
        canSeeMonsterNow = false;
        longestDistance = 0;
//For loop Designed for looking in all 8 directions
        for (int i = Model.MIN_DIRECTION; i <= Model.MAX_DIRECTION; i++) {
//look for monster. Saves variables for ability to see monster ever and currently,
//distance to monster, and the opposite direction to monster
            if (look(i) == Model.MONSTER) {
                canSeeMonsterNow = true;
                haveSeenMonster = true;
                distanceToMonster = distance(i);
                oppositeDirectionOfMonster = convert(i + 4);
//determines the direction with the longest distance (not including direction to monster)
            }else if (distance(i) > longestDistance) {
                longestDistance = distance(i);
                longestDistDirection = i;
            }
        }
//statement increments num turns without seeing monster
//set to 0 if canSeeMonsterNow
        if (!canSeeMonsterNow) {
            turnsWithoutSeeMonster++;
        } else {
            turnsWithoutSeeMonster = 0;
        }
//if havent seen monster in 5 moves, stay put.
        if (turnsWithoutSeeMonster > 5 && turnsWithoutSeeMonster!=15) {
            moveBeforeLast = lastMove;
            lastMove = currentDirection;
            currentDirection = Model.STAY;
            return currentDirection;
        }else if(turnsWithoutSeeMonster ==15){
            turnsWithoutSeeMonster = 5;
            moveBeforeLast = lastMove;
            lastMove = currentDirection;
            currentDirection = longestDistDirection;
            return currentDirection;
        }
//if haveSeenMonster (and hasn't entered earlier if statements that returna value)
        if (haveSeenMonster) {
// 1. if not canSeeMonsterNow and we can keep moving current direction, move current direction
            if (!canSeeMonsterNow && canMove(currentDirection)) {
                moveBeforeLast = lastMove;
                lastMove = currentDirection;
                return currentDirection;
            }
// 2. if the distance of the opposite direction of monster+1 is the same as
// opposite direction of monster -1,
// and the monster is more than two moves away
// return the direction of the longest distance
            if((distance(convert(oppositeDirectionOfMonster + 1)) ==
                    distance(convert(oppositeDirectionOfMonster - 1))) && (distanceToMonster>3) &&
                    distance(convert(oppositeDirectionOfMonster - 1)) < 5){
                moveBeforeLast = lastMove;
                lastMove = currentDirection;
                currentDirection = longestDistDirection;
                return currentDirection;
            }
            canMove = false;
            direction = 1;
//we want to move at a diagonal away from monster.
            while (!canMove) {
                awayFromMonsterPlus = convert(oppositeDirectionOfMonster +
                        direction);
                awayFromMonsterMinus = convert(oppositeDirectionOfMonster -
                        direction);
//check which way has the greater distance to run, awayFromMonsterPlus direction (starts at 1)
//or awayFromMonsterMinus direction
                if(distance(awayFromMonsterPlus) > distance(awayFromMonsterMinus)){
                    currentDirection = convert(oppositeDirectionOfMonster + direction);
//sets opposite direction of monster back to what it was before incrementing
//if we don't do this the person will zigzag
                    oppositeDirectionOfMonster = convert(currentDirection - 1);
                }else{
                    currentDirection = convert(oppositeDirectionOfMonster -
                            direction);
//sets opposite direction of monster back to what it was before incrementing
//if we don't person will zigzag
                    oppositeDirectionOfMonster = convert(currentDirection + 1);
                }
//increment direction before restarting loop. starts at 1
                direction++;
//checks we can move this direction and that we aren't in a feedback loop (lastMove+4 is going back to the place we were a move before)
                if ((currentDirection != convert(lastMove + 4)) && currentDirection
                        != (convert(moveBeforeLast + 4)) && canMove(currentDirection)) {
//terminate loop
                    canMove = true;
                } //if this statement doesn't terminate, the while loop restarts, next with direction = 2.
            }
//values after while statement
            moveBeforeLast = lastMove;
            lastMove = currentDirection;
            return currentDirection;
        }
//necessary return statement. unlikely to ever reach.
        moveBeforeLast = lastMove;
        lastMove = currentDirection;
        currentDirection = random(Model.MIN_DIRECTION, Model.MAX_DIRECTION);
        return currentDirection;
    }}
