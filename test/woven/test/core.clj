(ns woven.test.core
  (:use [woven.core :only
         [textile textile-new wrap-block heading-parse blockquote-parse
          acronym-parse link-parse]]
        [clojure.test])
  (:require [clojure.string :as str]))

;; There's no reason for the parser to reject whitespace in input.
;; (deftest empty-input
;;   (is (= "" (textile "")))
;;   (is (= "" (textile "\n\n\n")))
;;   (is (= "" (textile "\r\n\r\n")))
;;   (is (= "" (textile "\t\t\t"))))

(deftest headings
  (doseq [n (range 1 7)]
    (is (= (format "<h%d>Heading %d</h%d>" n n n) (textile-new (format "h%d. Heading %d" n n))))
    (is (= (format "<h%d>Heading %d</h%d>\n" n n n) (textile-new (format "h%d. Heading %d\n" n n))))
    (is (= (format "<h%d>Heading %d\nMore Stuff.</h%d>" n n n)
           (textile-new (format "h%d. Heading %d\nMore Stuff." n n))))))

;; (deftest heading-multiline
;;   (is (=
;;        "<h1>Heading 1</h1>\n\n<h2>Heading 2</h2>\n\n<h3>Heading 3</h3>"
;;        (textile-new
;;         "h1. Heading 1\n\nh2. Heading 2\n\nh3. Heading 3"))))

(deftest heading-block-test
  (is (= "<h1>bleh</h1>" (wrap-block "h1" "bleh")))
  (is (= "<h3>test</h3>" (wrap-block "h3" "test"))))

(deftest heading-parse-test
  (is (= "test" (heading-parse "test")))
  (is (= "<h1>test</h1>" (heading-parse "h1. test"))))

(deftest blockquotes
  (is (= "<blockquote>test</blockquote>" (textile "bq. test")))
  (is (= "<blockquote>something wicked this way comes</blockquote>"
         (textile "bq. something wicked this way comes"))))

(deftest blockquote-multiline
  (is (= "<blockquote>test</blockquote>\nthis\nout"
         (textile "bq. test\nthis\nout"))))

(deftest blockquote-block-test
  (is (= "<blockquote>test</blockquote>"
         (wrap-block "blockquote" "test")))
  (is (= "<blockquote>something with substance</blockquote>"
         (wrap-block "blockquote" "something with substance"))))

(deftest blockquote-parse-test
  (is (= "dummy text" (blockquote-parse "dummy text")))
  (is (= "something with substance"
         (blockquote-parse "something with substance"))))

(deftest acronym-block-test
  (is (= "<acronym title=\"Always Be Closing\">ABC</acronym>"
         (textile "ABC(Always Be Closing)"))))

(deftest acronym-parse-test
  (is (= "dummy text" (acronym-parse "dummy text")))
  (is (= "<acronym title=\"Always Be Closing\">ABC</acronym>"
         (acronym-parse "ABC(Always Be Closing)"))))

(deftest link-test
  (is (= "<a href=\"http://violentlymild.com\">Kushal</a>"
         (textile "\"Kushal\":http://violentlymild.com")))
  (is (= "<a href=\"mailto:joe@bloggs.com\">email</a>"
         (textile "\"email\":mailto:joe@bloggs.com")))
  (is (= "<a href=\"https://github.com\">GitHub</a>"
         (textile "\"GitHub\":https://github.com")))
  (is  (= "<a href=\"http://johnj.com\">John's</a> site"
         (textile "\"John's\":http://johnj.com site"))))


(deftest link-parse-test
  (is (= "dummy text" (acronym-parse "dummy text")))
  (is (= "<a href=\"http://violentlymild.com\">Kushal</a>"
         (link-parse "\"Kushal\":http://violentlymild.com")))
  (is (= "<a href=\"mailto:joe@bloggs.com\">email</a>"
         (link-parse "\"email\":mailto:joe@bloggs.com")))
  (is (= "<a href=\"https://github.com\">GitHub</a>"
         (link-parse "\"GitHub\":https://github.com")))
  (is (= "This is <a href=\"http://johnj.com\">John's</a> site"
         (link-parse "This is \"John's\":http://johnj.com site"))))
;; This fails: current method doesn't handle it yet; need something like re-seq:
;;  (is (= "Here're <a href=\"http://two.com\">two</a> small <a href=\"http://links.com\">links</a>."
;;         (link-parse "Here're \"two\":http://two.com small \"links\":http://links.com."))))
