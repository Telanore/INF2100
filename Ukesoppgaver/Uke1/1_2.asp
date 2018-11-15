def w (v, n):
    vs = str(v)
    if len(vs) < n:
        return " " + w(v,n-1)
    else:
        return vs

i = 0
p = 1
n = 10

while i <= n:
    print("2^"+w(i,2), "=", w(p,4))
    i = i+1
    p = 2*p
