**Infix to Postfix Converter** -  converts mathematical expressions from infix notation (e.g., `A + B * C`) to postfix notation (e.g., `A B C * +`).

### Key Features
- Supports operators: `+`, `-`, `*`, `/`, `^`
- Handles parentheses and nested expressions
- Proper operator precedence (^ highest, then * /, then + -)
- Left associativity (except ^ which is right associative)
- Multi-digit numbers and multi-character variables
- Postfix expression evaluation for numeric expressions
- Error handling for invalid expressions

### Algorithm Overview
1. Scan expression **left to right**
2. **Operands** → add directly to output
3. **`(`** → push to stack
4. **`)`** → pop stack to output until `(`
5. **Operators** → pop higher/precedence operators to output, then push current
6. **End** → pop remaining operators to output.