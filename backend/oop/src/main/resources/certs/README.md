# OpenSSL have to be downloaded and set in %PATH first

keypair:
- $ openssl genrsa -out keypair.pem 2048 
PUkey
- $ openssl rsa -in keypair.pem -pubout -out public.pem
<!-- prkey must be in pkcs8 format, following command have to be run -->
PRKey
- $ openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

Download OPENSSL
- https://slproweb.com/products/Win32OpenSSL.html
- 2nd option win64 openssl v3.0.5 (or choose whichever is suitable for your device)