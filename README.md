# CryptographyLab

## Caeser
### Server Side
1. Take input of ``(msg,key)`` from user
2. Loop over every char
2.1 ``Character.isUpperCase(c)?'A':'a'`` store in base
2.2 ``(char)((c-base+key)%26+base)`` encrypt each character if ``Character.isLetter(c)``
3. Print and Send to client ``(res,key)``

### Client Side
1. Receive ``(msg,key)`` from Server
2. Loop over every char
2.1 ``Character.isUpperCase(c)?'A':'a'`` store in base
2.2 ``(char)((c-base-key)%26+base)`` decrypt each 
character if ``Character.isLetter(c)``
3. Print ``res``

## Vigenere
### Server Side
1. Take input of ``(msg,key)`` from user
2. Loop over every char in msg ``i=0,j=0``
2.1 ``Character.isUpperCase(c)?'A':'a'`` store in base
2.2 Calculate shift in key ``key.charAt(j%key.length())-'a' ``
2.3 Shift the char of msg ``(char)((c-base+shift)%26+base)`` encrypt
3. Print and Send to client ``(res,key)``

### Client Side
1. Receive ``(res,key)``
2. Loop over every char in res ``i=0,j=0``
2.1 ``Character.isUpperCase(c)?'A':'a'`` store in base
2.2 Calculate shift in key ``key.charAt(j%key.length())-'a' ``
2.3 Shift the char of msg ``(char)((c-base-shift)%26+base)`` decrypt
3. Print ``(res)``

## Vernam
### Server Side
1. Take input of ``(msg,key)`` from user
2. Loop over every char in msg
2.1 Encrypt ``(char)(msg.charAt(i)^key.charAt(i))``
3. Print and Send to client ``(res,key)``

### Client Side
1. Receive ``(res,key)``
2. Loop over every char in res
2.1 Decrypt ``(char)(res.charAt(i)^key.charAt(i))``
3. Print ``(res)``

## Hill Cipher
### Server Side
1. Take input ``msg`` from user convert to UpperCase and fill all spaces
2. Add ``X`` if length of msg not multiple of 2
3. Take input of key as matrix
4. Traverse over msg ``i+=2`` take ``a=msg.charAt(i)+'A',b=msg.charAt(i+1)+'A'``
5. Encrypt ``c1=(key[0][0]*a+key[0][1]*b)%26`` and ``c2=(key[1][0]*a+key[1][1]*b)%26``
6. Add to c1 and c2 to res ``(char)(c1+'A') (char)(c2+'A')``
7. Send to client ``(res, key values)``

### Client Side
1. Calculate ``det=key[0][0]*key[1][1]-key[0][1]*key[1][0]`
2. Find inverse if det invdet``det=(det%26+26)%26 , (det*i)%26=1``
3. ``inv[0][0]=key[1][1] inv[1][1]=key[0][0] inv[0][1]=-key[1][0] inv[1][0]=-key[0][1]``
4. traverse over inv matrix and ``inv[i][j]=(inv[i][j]*invdet)%26``

5. Traverse over the msg ``i+=2`` take ``a=msg.charAt(i)-'A',b=msg.charAt(i+1)-'A'``
6. Decrypt ``p1=(inv[0][0]*a+key[0][1]*b)%26`` and ``p2=(key[1][0]*a+key[1][1]*b)%26``
7. Add to p1 and p2 to res ``(char)(p1+'A') (char)(p2+'A')``
7. Send to client ``(res, key values)``

## Playfair 
### Server Side
1. Define 5*5 matrix and used Hashset and make key uppercase and replace J with I and remove spaces
2. Loop over key if not used `` mat[k/5][k%5]=c  k++``
3. Fill remaining letter loop over A->Z skip J `` mat[k/5][k%5]=c  k++``

4. Make msg uppercase and replace J with I and remove spaces
5. Traverse over msg ``i+=2`` pair (a,b) if b is null replace with X
6. Loop r= 0->5 and c= 0->5
6.1 if ``mat[r][c]=a`` store ``r in r1 and c in c1`` if =b store in r2,c2
6.2 if r1==r2 ``res+=mat[r1][(c1+1)%5],res+=mat[r2][(c2+1)%5]``
6.3 else if c1==c2 ``res+=mat[(r1+1)%5][c1],res+=mat[(r2+1)%5][c2]``
6.4 else ``res+=mat[r1][c1],res+=mat[r2][c2]``
7. Send ``res,key`` to client

### Client Side
1. Traverse over msg ``i+=2`` pair (a,b) if b is null replace with X
2. Loop r= 0->5 and c= 0->5
2.1 if ``mat[r][c]=a`` store ``r in r1 and c in c1`` if =b store in r2,c2
2.2 if r1==r2 ``res+=mat[r1][(c1+4)%5],res+=mat[r2][(c2+4)%5]``
2.3 else if c1==c2 ``res+=mat[(r1+4)%5][c1],res+=mat[(r2+4)%5][c2]``
2.4 else ``res+=mat[r1][c1],res+=mat[r2][c2]``
3. Print res

## Row-Column
### Server Side
1. Take user input msg and key
2. Calculate number of rows=msg.len/key.len and mat[rows][col]
3. Fill the matrix ``k=0``,``mat[i][j]=(k<msg.len)?msg.charAt(k++):'X'``
4. Read col wise
5. Send to Client

### Client Side
1. Receive msg
2. Fill matrix column wise
3. Read row wise

## Railfence
### Server Side
1. Take input of key and msg from user
2. Take ``StringBuilder[] rail=new StringBuilder[key]`` and fill each ith index with new StringBuilder
3. Take `dir=1, row=0`
4. Take each char of msg
4.1 place character `rail[row].append(c)`
4.2 if `row==0 then dir=1` else if `row==key-1 then dir=-1`
4.3 move row `row+=dir`
5. Loop over and add to res `rail[i].toString()`

### Client Side
1. Create matrix ``rail[key][cipher.len]`` row=0 dir=1

2. Traverse over cipher.len
2.1 ``rail[row][i]=*``
2.2 if row=0 then dir=1
2.3 else if row==key-1 dir=-1
2.4 ``row+=dir`` move row

3. ``i=0``
4. Loop over key length Loop over cipher.len
4.1 if i<cipher.len && rail[i][j]=* then fill with charAt(i) and inc i

5. Loop over 0->cipher.len
5.1 Add rail[row][i] to res
5.2 if i==0 dir=1 else if i==key-1 dir=-1
5.3 `row+=dir` 

## DES
### Server Side
1. Take user input `msg,key`
2. Create `byte[]keybytes=new byte[8]` , `byte[]inputkey=key.getBytes()`
3. Fill key array `keybytes[i]=i<key.len?inputbytes[i]:0` if key is short pad with 0
4. secret key `SecretKey key=new SecretKeySpec(keybytes,"DES")`
5. convert msg to bytes `byte[]data=msg.getBytes()`

6. Loop over 16 times print `i, bytesToHex(data)`

7. Cipher `cipher=Cipher.getInstance("DES")`
8. encrypt `cipher.init(Cipher.ENCRYPT_MODE,key)`
9. byte[] encrypted=cipher.doFinal(data)
10. Convert to Hex `cipher=bytesToHex(encrypt)` --> sb.append(String.format("%02X",b)) where b is bytes 
11. send to client cipher,new String(keybytes)

### Client Side
1. Receive from server
2. convert key to bytes and create SecretKey
3. convert cipher hex to bytes
4. Loop over 16 times print `i,cipherText`
5. decrypt `cipher.init(Cipher.DECRYPT_MODE,key)`
6. byte[] decrypted=cipher.doFinal(encrypted)
## AES
### Server Side
### Client Side
## RSA
### Server Side
1. Take user input of msg
2. Convert msg to number
3. generate random prime `p,q`
4. Compute ``n=p*q``
5. Compute ``phi=(p-1)*(q-1)``
6. Set `e`
7. Compute Private Key ``d=e^-1 mod phi``
8. Encrypt ``c=m^e mod n``
9. Send data to client ``(c,d,n)``

### Client Side
1. Receive from Server ``(c,d,n)``
2. Decrypt ``m=c^d mod n``
3. Convert back to text

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