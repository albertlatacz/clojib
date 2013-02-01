(ns clojib.encrypt_test
  (:use clojure.test
        clojib.encrypt))

(deftest encrypts-password
  (is (= "YZcBN1dWV0yDaQU63it8f7lMofo=" (String. (encode-base64 (encrypt-password (.getBytes "some satl") "some password")))))
  (is (= "eRzg3LBIEevCtQiHZ/8gNCLhOfw=" (String. (encode-base64 (encrypt-password "some password"))))))

(deftest generates-salt
  (let [previous-salt (generate-salt)]
    (is (= 8 (alength (generate-salt))))
    (is (not= previous-salt (generate-salt)))))

(deftest encodes-base64
  (is (= "dGV4dCB0byBlbmNvZGU=" (encode-base64 (.getBytes "text to encode")))))