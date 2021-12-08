(ns advent-2021.day8-test
  (:require [advent-2021.day8 :as sut]
            [clojure.test :refer :all]))

(deftest test-parse-input
  (testing "Parses input correctly"
    (is (= {:signals ["be" "cfbegad" "cbdgef" "fgaecd" "cgeb" "fdcge" "agebfd" "fecdb" "fabcd" "edb"]
            :digits ["fdgacbe" "cefdb" "cefbgd" "gcbe"]}
           (first (sut/parse-input "day8-sample"))))))

(deftest test-part-1
  (testing "Counts number of easy digits in sample input"
    (is (= 26
           (sut/count-easy-digits (sut/parse-input "day8-sample"))))))

(deftest test-part-2
  (let [sample-display {:signals ["acedgfb" "cdfbe" "gcdfa" "fbcad" "dab" "cefabd" "cdfgeb" "eafb" "cagedb" "ab"]
                        :digits ["cdfeb" "fcadb" "cdfeb" "cdbaf"]}]
    (testing "Easy digit lookup produced from input"
      (is (= {1 #{\a \b}
              4 #{\e \a \f \b}
              7 #{\d \a \b}
              8 #{\a \c \e \d \g \f \b}}
             (sut/easy-digits-lookup (:signals sample-display)))))
    (testing "Other digits are added to the lookup"
      (let [lookups {:digit-lookup (sut/easy-digits-lookup (:signals sample-display))
                     :character-lookup {}}
            signals (->> sample-display :signals (map set))
            with-nine (sut/add-nine lookups signals)]
        (testing "nine"
          (testing "digit is added to lookup"
            (is (= {1 #{\a \b}
                    4 #{\e \a \f \b}
                    7 #{\d \a \b}
                    8 #{\a \c \e \d \g \f \b}
                    9 #{\d \e \a \f \b \c}}
                   (:digit-lookup with-nine))))
          (testing "known wires added to lookup"
            (is (= {\a \d \g \c \e \g}
                   (:character-lookup with-nine)))))
        (testing "zero and six"
          (let [new (sut/add-zero-and-six with-nine signals)]
            (testing "digit is added to lookup"
              (is (= {0 #{\a \b \c \d \e \g}
                      1 #{\a \b}
                      4 #{\e \a \f \b}
                      6 #{\c \d \f \g \e \b}
                      7 #{\d \a \b}
                      8 #{\a \c \e \d \g \f \b}
                      9 #{\d \e \a \f \b \c}}
                     (:digit-lookup new))))
            (testing "known wires added"
              (is (= {\a \d
                      \g \c
                      \e \g
                      \d \f
                      \c \a}
                     (:character-lookup new))))
            (testing "complete digit lookup"
              (is (= {0 #{\a \b \c \d \e \g}
                      1 #{\a \b}
                      2 #{\g \c \d \f \a}
                      3 #{\f \b \c \a \d}
                      4 #{\e \a \f \b}
                      5 #{\c \d \f \b \e}
                      6 #{\c \d \f \g \e \b}
                      7 #{\d \a \b}
                      8 #{\a \c \e \d \g \f \b}
                      9 #{\d \e \a \f \b \c}}
                     (sut/finish-digit-lookup new signals))))))))
    (testing "Decode output digits"
      (is (= 5353
             (sut/decode-display sample-display)))))
  (testing "correct sum for sample input"
    (is (= 61229
           (sut/sum-digits (sut/parse-input "day8-sample"))))))
