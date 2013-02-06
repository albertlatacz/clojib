(ns clojib.encrypt_test
  (:use clojure.test
        clojib.encrypt))

(println (encrypt-password "mamaiadas"))

(deftest encrypts-password
  (is (= "YZcBN1dWV0yDaQU63it8f7lMofpB9Rry3QJDNZj9PONzb21lIHNhdGw=" (encrypt-password (.getBytes "some satl") "some password")))
  (is (passwords= "some password" (encrypt-password "some password")))
  (is (not= (encrypt-password "some password") (encrypt-password "some password"))))

(deftest generates-salt
  (let [previous-salt (generate-salt)]
    (is (= 16 (alength (generate-salt))))
    (is (not= previous-salt (generate-salt)))))