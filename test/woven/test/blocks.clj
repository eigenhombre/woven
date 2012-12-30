(ns woven.test.blocks
  (:use [woven.core :only [textile-new]])
  (:use [clojure.test]))

(deftest strong
  (is (= "<strong>text</strong>" (textile-new "*text*")))
  (is (= "some <strong>text</strong>" (textile-new "some *text*")))
  (is (= "some <strong>bounded</strong> text"
         (textile-new "some *bounded* text")))
  (is (= (textile-new "some *strong* and *stronger* text")
         "some <strong>strong</strong> and <strong>stronger</strong> text"))
  (is (= (textile-new "some *strong*\nand *stronger* text")
         "some <strong>strong</strong>\nand <strong>stronger</strong> text"))
  (is (= (textile-new "some *even\nstronger* text")
         "some <strong>even\nstronger</strong> text")))

(deftest italics
  (is (= "<em>text</em>" (textile-new "_text_")))
  (is (= "some <em>text</em>" (textile-new "some _text_")))
  (is (= "some <em>bounded</em> text" (textile-new "some _bounded_ text"))))

(deftest citation
  (is (= "<cite>text</cite>" (textile-new "??text??")))
  (is (= "some <cite>text</cite>" (textile-new "some ??text??")))
  (is (= "some <cite>bounded</cite> text"
         (textile-new "some ??bounded?? text"))))

(deftest deletion
  (is (= "<del>text</del>" (textile-new "-text-")))
  (is (= "some <del>text</del>" (textile-new "some -text-")))
  (is (= "some <del>bounded</del> text" (textile-new "some -bounded- text"))))

(deftest superscript
  (is (= "<sup>text</sup>" (textile-new "^text^")))
  (is (= "some <sup>text</sup>" (textile-new "some ^text^")))
  (is (= "some <sup>bounded</sup> text" (textile-new "some ^bounded^ text"))))

(deftest subscript
  (is (= "<sub>text</sub>" (textile-new "~text~")))
  (is (= "some <sub>text</sub>" (textile-new "some ~text~")))
  (is (= "some <sub>bounded</sub> text" (textile-new "some ~bounded~ text"))))

(deftest span
  (is (= "<span>text</span>" (textile-new "%text%")))
  (is (= "some <span>text</span>" (textile-new "some %text%")))
  (is (= "some <span>bounded</span> text"
         (textile-new "some %bounded% text"))))
