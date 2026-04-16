FUNCTION infixToPostfix(infixExpression)
    CREATE empty stack
    CREATE empty string postfix
    
    FOR each character ch in infixExpression:
        IF ch is letter OR digit THEN
            ADD ch to postfix
            
        ELSE IF ch equals '(' THEN
            PUSH ch onto stack
            
        ELSE IF ch equals ')' THEN
            WHILE stack is NOT empty AND top of stack NOT EQUAL '('
                POP from stack and ADD to postfix
            END WHILE
            IF stack is NOT empty AND top of stack equals '(' THEN
                POP and discard '('
            END IF
            
        ELSE IF ch is operator THEN
            WHILE stack is NOT empty AND top of stack NOT EQUAL '('
                AND precedence(top of stack) >= precedence(ch)
                POP from stack and ADD to postfix
            END WHILE
            PUSH ch onto stack
        END IF
    END FOR
    
    WHILE stack is NOT empty
        POP from stack and ADD to postfix
    END WHILE
    
    RETURN postfix
END FUNCTION