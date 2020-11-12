
function isPrime(num) {
    for (let i = 2; i < num / 2; i++) {
        if (num % i === 0) {
            return false;
        }
    }
    return true;
}

function generateNthPrime(n) {
    let count = 0;

    let result = -1;
    for (let i = 2; count <= n; i++) {
        if (isPrime(i)) {
            result = i;
            count++;
        }
    }

    return result;
}

// findExponentialModulo() - fast exponentiation recursive algorithm
function findExponentialModulo(a, N, M) {
    a = a % M;
    let res = 1;
    let temp = a;
  
    while(N > 0){
      var leastSignificantBit = N % 2;
      N = Math.floor(N / 2);
  
      if (leastSignificantBit == 1) {
        res = res * temp;
        res = res % M;
      }
  
      temp = temp * temp;
      temp = temp % M;
    }
    return res;
  }

function gcd(num1, num2) {
    let c;
    while (true) {
        c = num1 % num2;
        if (c === 0)
            return num2;
        num1 = num2;
        num2 = c;
    }
}

function encrypt(){
    if(document.getElementById("message").value == ""){
        alert("Message field can't be blank");
    } else if (document.getElementById("p").value == "" || 
                document.getElementById("q").value == "") {
         alert("Either generate P and Q or fill it manually");
     } else {
        computeValues();

        let plainText = document.getElementById("message").value;
        let e = document.getElementById("e").value;
        let n = document.getElementById("n").value;

        let cipherText = findExponentialModulo(plainText, e, n);

        document.getElementById("plain-text").value = "";
        document.getElementById("cipher-text").value = cipherText;
     }
}


function decrypt(){
    if(document.getElementById("message").value == ""){
        alert("Message field can't be blank");
    } else if (document.getElementById("p").value == "" || 
                document.getElementById("q").value == "") {
         alert("Either generate P and Q or fill it manually");
     } else {
        computeValues();

        let cipherText = document.getElementById("message").value;
        let d = document.getElementById("d").value;
        let n = document.getElementById("n").value;


        let plainText = findExponentialModulo(cipherText, d, n);

        document.getElementById("plain-text").value = plainText;
        document.getElementById("cipher-text").value = "";
     }
}


function computeValues(){

    const p = document.getElementById("p").value;
    const q = document.getElementById("q").value;

    // STEP 2
    let n = p * q;

    // STEP 3
    let phi = (p - 1) * (q - 1);

    // STEP 4
    let e = 2;
    while (e < phi) {
        if (gcd(e, phi) === 1)
            break;
        else
            e++;
    }

    // STEP 5
    let d = 0;
    while ((d * e) % phi != 1) {
        d++;
    }

    document.getElementById("n").value = n;
    document.getElementById("phi").value = phi;
    document.getElementById("e").value = e;
    document.getElementById("d").value = d;
    
}

function generatePandQ() {
    let p = generateNthPrime(Math.floor(Math.random() * 101) + 99);
    let q = generateNthPrime(Math.floor(Math.random() * 101) + 98);
    // make sure p and q are not the same
    while (p === q) {
        q = generateNthPrime(rand.nextInt(100) + 98);
    }

    document.getElementById("p").value = p;
    document.getElementById("q").value = q;

}
