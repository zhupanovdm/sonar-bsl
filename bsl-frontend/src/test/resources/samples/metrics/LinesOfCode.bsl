//
// File Header
//

#If Server Then

// Describing comment
Function foo()
    // NOSONAR comment
    return 5 + 17
EndFunction

#Region block
// Spare comment
#EndRegion

bar = foo() - 17

#EndIf