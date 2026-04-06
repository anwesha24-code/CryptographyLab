# CryptographyLab

## Elgamal
### Server Side
1. Take input from user ``(p,g,x)``
2. Compute Public Key ``y=g^x mod p``
3. Take input from user ``(k,m)``
4. Computer ``c1=g^k mod p`` and ``c2=m*y^k mod p``
5. Send to client ``(p,g,x,c1,c2)``

### Client Side
1. modinverse function find i so that a*i mod p == 1
2. Receive from Server ``(p,g,x,c1,c2)``
3. Compute shared key ``s=c1^x mod p``
4. find modinverse ``inv=s^-1 mod p``
5. find original msg ``m=c2*(c1^x)^-1 mod p``

## MITM
### Server Side
1. Take input from user for Alice ``(p,g,Xa)``
2. Compute Alice's Public Key ``Ya=g^Xa mod p``
3. Take input from user for Attacher ``(Xd1,Xd2)``
4. Computer Attacker's Public Key ``Yd1=g^Xd1 mod p, Yd2=g^Xd2 mod p``

5. send to client/Bob attacker's public key(instead to be Alice's) ``(p,g,Yd2)``
6. receive from client/Bob ``Yb``

7. Calculate Alice-Attacker Key ``Ka=Yd1^Xa mod p``
8. Calculate Attacker-Alice and Attacker-Bob Key
``Kd1=Ya^Xd1 mod p Kd2=Yb^Xd2 mod p``

### Client Side
1. Take input from Server ``(p,g,fakeYa(Yd2))``
2. Take input from user for Bob's pvt key ``(Xb)``
3. Compute Bob's Public Key ``Yb=g^Xa mod p``
4. Send Bob's public key to Server(Attacker)
5. Compute Bob-Attacker Key ``Kb=powerfakeYa^Xb mod p``