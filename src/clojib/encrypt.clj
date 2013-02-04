(ns clojib.encrypt
  (:import (java.security.spec KeySpec InvalidKeySpecException)
           (java.security SecureRandom)
           (javax.crypto.spec PBEKeySpec)
           (javax.crypto SecretKeyFactory)
           (sun.misc BASE64Encoder BASE64Decoder)))

(defn- encode-base64 [xs]
  (.encode (BASE64Encoder.) xs))

(defn- decode-base64 [s]
  (.decodeBuffer (BASE64Decoder.) s))

(defn generate-salt []
  (let [salt (byte-array 16)]
    (.nextBytes (SecureRandom/getInstance "SHA1PRNG") salt)
    salt))

(defn encrypt-password
  ([passwd] (encrypt-password (generate-salt) passwd))
  ([salt passwd]
    (encode-base64
      (let [spec (PBEKeySpec. (.toCharArray passwd), salt, 20000, 256)
            secret-factory (SecretKeyFactory/getInstance "PBKDF2WithHmacSHA1")]
        (byte-array
          (concat
            (.. secret-factory (generateSecret spec) (getEncoded))
            salt))))))

(defn extract-salt [encrypted]
  (let [encrypted-bytes (decode-base64 encrypted)]
    (byte-array (drop 32 encrypted-bytes))))

(defn passwords= [raw encrypted]
  (= encrypted (encrypt-password (extract-salt encrypted) raw)))



