To use, follow ThePDA syntax and the Notes to make your text file that will be turned into a PDA. 
    Then, please respond with strings to test your automation or reply with "exit" to immediately exit the program.
    If you get a please exception, check your text file throughly to make sure no essiental parts are missing.
    If you get a bug, report it please.
    No other interations then sending a string for a long-winded test is present.

ThePDA syntax:
        first line: the maximum number of states. That string is parsed into an int, 
            so just leave a number for this (that is your total number of states).
        Second line: the language of the pda, signa. 
            That string is turned into a char array so no need for commas.
            Make it like this "ab" instead of putting "a, b".
        Third line and on:
            These are strings that are split with the regex "; " become either states or transitions for states.

        States start with a number, "INITAL", or "FINAL" to signify their position. 
            In the latter case, the first number in the State.
            Other numbers after the first one are adjacent.

        transitions start with "transition".
            first two numbers after are the start and end of the transition. The three symbols are the read, pop, and push.
Notes:
    Note 1: start with your pda's inital state and make sure a final state does exist.
    Note 2: Also, make sure your pda's inital state has adjacent states.
    Note 3: reads are chars, so don't use "" or null for "", instead use LAMBDA to indicate empty spots.
    Note 4: Please make sure reads are in the language and either the symbols or LAMBDA.
    Note 5: No need to have duplicate adjacent states as adjacent states for ant given state. 
        eg: this is no need (5; 5; 5; 3) as (5; 5; 3) is a good state initalization already.
    Note 6: writing exit anywhere in your reply ends the program instantly.
    Note 7: "end" ends your text file; this will cause bugs if you don't have it.
Known issues:
    None at the moment from what I know. Please report any issues you experience.
Expected inputs are just strings that test your automata everything else will be given a "NO" unless you reply with "exit" no matter the caps.