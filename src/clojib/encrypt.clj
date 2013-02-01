(ns clojib.encrypt
  (:import (java.security.spec KeySpec InvalidKeySpecException)
           (java.security SecureRandom)
           (javax.crypto.spec PBEKeySpec)
           (javax.crypto SecretKeyFactory)
           (sun.misc BASE64Encoder)))

(defn generate-salt []
  (let [random (SecureRandom/getInstance "SHA1PRNG")
        salt (byte-array 8)]
    (.nextBytes random salt)
    salt))

(defn encrypt-password
  ([passwd] (encrypt-password (.getBytes "kj3hd9ojakLjLJ800H*0J9UY009J") passwd))
  ([salt passwd]
    (let [spec (PBEKeySpec. (.toCharArray passwd), salt, 20000, 160)
          secret-factory (SecretKeyFactory/getInstance "PBKDF2WithHmacSHA1")]
      (.. secret-factory
        (generateSecret spec)
        (getEncoded)))))


(defn encode-base64 [xs]
  (.encode (BASE64Encoder.) xs))


