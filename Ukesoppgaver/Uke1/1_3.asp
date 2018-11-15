n = 20

def fib1(x):
  f1 = 0
  f2 = 1

  for i in range(0, x):
    f3 = f1 + f2
    f1 = f2
    f2 = f3

  return f1

def fib2(x):
  if x <= 2:
    return 1
  else:
    return fib2(x-2) + fib2(x-1)

print('fib1(', n, ') = ', fib1(n))
print('fib2(', n, ') = ', fib2(n))
