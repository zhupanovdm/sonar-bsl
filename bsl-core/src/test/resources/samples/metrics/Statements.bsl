Function foo()
    ; // +1 empty statement

    a = 1 + 1; // +1 assignment statement

    While a < 100 Do // +1 while statement
        If a = 10 Then // +1 if statement
            Break // +1 break statement
        EndIf
    EndDo;

    For Each e In collection Do // +1 foreach statement
        bar() // +1 call statement
    EndDo;

    For i = 0 To 10 Do // +1 for statement
        Continue // +1 continue statement
    EndDo;

    Try // +1 try statement
        Execute("a = 100") // +1 execute statement
    Except
        Raise "Exception message!" // +1 raise statement
    EndTry;

    AddHandler expr.Event, expr.Handler; // +1 add handler statement
    RemoveHandler expr.Event, expr.Handler; // +1 remove handler statement

    Goto ~label; // +1 goto statement

    a = 1 + 1; b = 7; // +2 assignment statement

    return a // +1 return statement
EndFunction