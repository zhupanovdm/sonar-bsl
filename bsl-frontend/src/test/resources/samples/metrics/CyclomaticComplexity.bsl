If a = 10 Then // +1
    If b > c Then // +1
        a = b
    ElsIf b = 11 Then // +1
        a = 5
    Else // +0
        a = c
    EndIf
EndIf;

print(a);
print(b);
print(c)