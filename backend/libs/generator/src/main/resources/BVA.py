print("Computing tests for logical expressions connecting with AND ")
print("Usage: 1 for <, 2 for <=, 3 for >, 4 for >=, 5 for =, 6 for <>, 7 for boolTrue, 8 for boolFalse, 9 for intervals")
# number of elements
n = int(input("\nEnter number of atomic conditions: ")) 
# Below line read inputs from user using map() function  
a = list(map(int,input("Enter the numbers [1-8], space sepatared: ").strip().split()))[:n] 
#Init
L = set(); l= []; l2 = []; interNr = 0; interV = []
for counter, value in enumerate(a):
    if value == 9:
        interNr+=1; interV.append(counter)

#IN/ON patterns
for counter, value in enumerate(a):
    if value == 5:
        l.append('ON'); l2.append('ON');
    elif value == 6:
        l.append('IN2'); l2.append('IN');
    elif value == 7:
        l.append('TRUE'); l2.append('TRUE')
    elif value == 8:
        l.append('FALSE'); l2.append('FALSE')
    elif value == 9:
        l.append('ON1'); l2.append('IN')
    else:
        l.append('ININ'); l2.append('IN')
L.add(tuple(l))

l = []
for counter, value in enumerate(a):
    if value == 6:
        l.append('IN1')
    elif value == 7:
        l.append('TRUE')
    elif value == 8:
        l.append('FALSE')
    elif value == 9:
        l.append('ON2')
    else:
        l.append('ON')
L.add(tuple(l))

#OFF and OUT patterns
for counter, value in enumerate(a):
    if  value <= 4:
        l = l2.copy(); l[counter] = 'OFF'; L.add(tuple(l))
        l = l2.copy(); l[counter] = 'OUT'; L.add(tuple(l))
    elif value == 5:
        l = l2.copy(); l[counter] = 'OUT1'; L.add(tuple(l))
        l = l2.copy(); l[counter] = 'OUT2'; L.add(tuple(l))
    elif value == 6:
        l = l2.copy(); l[counter] = 'OFF'; L.add(tuple(l))
    elif value == 7:
        l = l2.copy(); l[counter] = 'FALSE'; L.add(tuple(l))
    elif value == 8:
        l = l2.copy(); l[counter] = 'TRUE'; L.add(tuple(l))
if interNr > 0:
    for counter, value in enumerate(interV):
        l = l2.copy(); l[value] = 'OUT1'; L.add(tuple(l))
        l = l2.copy(); l[value] = 'OUT2'; L.add(tuple(l))
        l = l2.copy(); l[value] = 'OFF1'; L.add(tuple(l))
        l = l2.copy(); l[value] = 'OFF2'; L.add(tuple(l))

print("\nThe tests: ", L) 
