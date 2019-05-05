
# Type System

Types are associated with constants, signals, registers, module ports and instance ports. Each type belongs to one
of the type families: bit, vector, matrix, integer, text and clock.

The following combinations are possible:
* a constant can have any type except clock.
* signals, module ports and instance ports can have bit, vector or clock type.
* registers can have bit, vector or matrix type.

The bit and vector types are what actual hardware deals with all the time.

Integers cannot exist at run-time since they are unbounded. However, *constant* integers are used to specify the
behvaiour of some expressions; constant *folding* makes compile-time operations on integers possible, and
*conversion* allows to use constant integers as a helper notation for vector literals.

Clocks exist *only* at run-time. No operations are possible on clock-typed sigals. They can only be assigned,
unchanged, to route a clock signal to sub-modules.

Matrix types can be used by constants (i.e. look-up tables) and registers (synchronous memory). The only possible
operation is index selection, both when reading and writing from/to the matrix. Such index selection follows the
same rules with respect to the index expressions as for vector-typed containers.

The text type exists as a compile-time helper type. The only operations possible for the text type is conversion of
any type to text, as well as concatenation.

## Type Rules for Expressions

### Literals

The type of a literal is explicit. MaHDL supports bit, vector, integer and text literals.

### References

A reference to a constant, signal, register, module port or instance port has the same type as the referenced entity.

### Unary Operators

The *plus* and *minus* operators expect an operand of vector or integer type and yield the same type.

The *not* operator expects and operand of bit, vector or integer type and yields the same type.

### Binary Operators

TODO

### Conditional Operator

The conditional operator converts the condition to type bit.


TODO

### Switch Expression

TODO

### Index Selection

TODO

### Range Selection

TODO

### Function Call

The type rules for function calls depend on the function being called.

## Type Rules for Statements

## Other Type Rules

## Conversion

### Text

Any type can be converted to text implicitly. This happens in the initializer of a text-type constant, for text-typed
function arguments and for text concatenation. Implicit conversion to text is usually used for simple debugging
output and does not produce a well-defined output format.

Clocks cannot be converted to text. This does not contradict the above statement that *any* type can be
converted to text because text only exists at compile-time, while clocks only exist at run-time.

Text cannot be converted to any other type. To achieve effects such as integer parsing, functions must be used.

