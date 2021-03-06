(ns noob2pro.rupesh.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))



;;;;; Project Euler problems



;;;;;; problem 1 Multiples of 3 and 5

(defn mul
  [n]
  (reduce +
          (filter #(zero? (min (mod % 3)
                               (mod % 5)))
                  (range n))))

;;;;;  fibo numbers


(defn fibo
  ([]
   (fibo 1 1))
  ([a b]
   (lazy-seq (cons a (fibo b (+ a b))))))


(def a (reduce +
               (take-while #(<= % 4000000)
                           (filter even? (fibo)))))


;;;;;; Largest prime factor

(defn prime-factors [n]
  (loop [n n
         div 2
         factors []]
    (if (< n 2)
      (last factors)
      (if (= 0 (rem n div))
        (recur (/ n div)
               div
               (conj factors div))
        (recur n
               (inc div)
               factors)))))


;;;;; Largest palindrome product

(defn palin? [s]
  (if (= (str s)
         (apply str
                (reverse (str s))))
    true
    false))

(apply max
       (filter palin?
               (for
                   [a (range 100 1000)
                    b (range 100 1000)]
                 (* a b))))


;;;;; smallest multiple


(defn gcd
  [x y]
  (if (zero? y)
    x
    (recur y (mod x y))))

(defn lcm [a b]
  (/ (* a b) (gcd a b)))

(reduce lcm (range 1 20))


;;;; sum square difference


(defn exp [x n]
  (if (zero? n) 1
      (* x (exp x (dec n)))))






(defn sum-of-square
  [n]
  (reduce + (map #(* % %) (range 1 (inc n)))))

(defn final
  [n]
  (- (exp (reduce + (range 1 (+ n 1))) 2) (sum-of-square n)))


;;;;;; 10001st prime


(defn prime? [n]
  (if (even? n)
    false
    (let [root (num (int (Math/sqrt n)))]
      (loop [i 3]
        (if (> i root)
          true
          (if (zero? (mod n i))
            false
            (recur (+ i 2))))))))


(defn nth-prime
  [n]
  (last (take n
              (filter #(prime? %)
                      (range)))))



;;;;; Special pythagoras Triplet

(defn my-triplet
  [s]
  (first (for [a (range (/ s 2))
               b (range (/ s 2))
               :let [c (- s a b)]
               :when (= (* c c) (+ (* a a) (* b b)))]
           (* a b c))))



;;;;;; summation of primes


(defn prime? [n]
  (if (even? n)
    false
    (let [root (num (int (Math/sqrt n)))]
      (loop [i 3]
        (if (> i root)
          true
          (if (zero? (mod n i))
            false
            (recur (+ i 1))))))))


(defn sum-of-primes
  [n]
  (reduce + (conj  (filter #(prime? %)
                           (range 2 n)) 2)))


;;;;;; Highly divisible triangular number



(defn tri*
  "Generates lazy sequence of triangular numbers"
  ([] (tri* 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (tri* new-sum (inc n)))))))

(def tri (tri*))

(defn divisors
  [n]
  (filter #(= 0 (rem n %))
          (range 1 (inc n))))


(defn triangular?
  [n]
  (= n (last (take-while #(>= n %) tri))) n)


(defn div-tri-num
  []
  (loop [sum 0
         num 1]
    (if (> (count (divisors (triangular? sum))) 50)
      sum
      (recur (+ sum


                num) (inc num)))))



;;;;; Largest product in a grid
;;; Bhavesh helped me in solving this problem


(def grid (list 8 2 22 97 38 15 0 40 0 75 4 5 7 78 52 12 50 77 91 8 49 49 99 40 17 81 18 57 60 87 17 40 98 43 69 48 4 56 62 0 81 49 31 73 55 79 14 29 93 71 40 67 53 88 30 3 49 13 36 65 52 70 95 23 4 60 11 42 69 24 68 56 1 32 56 71 37 2 36 91 22 31 16 71 51 67 63 89 41 92 36 54 22 40 40 28 66 33 13 80 24 47 32 60 99 3 45 2 44 75 33 53 78 36 84 20 35 17 12 50 32 98 81 28 64 23 67 10 26 38 40 67 59 54 70 66 18 38 64 70 67 26 20 68 2 62 12 20 95 63 94 39 63 8 40 91 66 49 94 21 24 55 58 5 66 73 99 26 97 17 78 78 96 83 14 88 34 89 63 72 21 36 23 9 75 0 76 44 20 45 35 14 0 61 33 97 34 31 33 95 78 17 53 28 22 75 31 67 15 94 3 80 4 62 16 14 9 53 56 92 16 39 5 42 96 35 31 47 55 58 88 24 0 17 54 24 36 29 85 57 86 56 0 48 35 71 89 7 5 44 44 37 44 60 21 58 51 54 17 58 19 80 81 68 5 94 47 69 28 73 92 13 86 52 17 77 4 89 55 40 4 52 8 83 97 35 99 16 7 97 57 32 16 26 26 79 33 27 98 66 88 36 68 87 57 62 20 72 3 46 33 67 46 55 12 32 63 93 53 69 4 42 16 73 38 25 39 11 24 94 72 18 8 46 29 32 40 62 76 36 20 69 36 41 72 30 23 88 34 62 99 69 82 67 59 85 74 4 36 16 20 73 35 29 78 31 90 1 74 31 49 71 48 86 81 16 23 57 5 54 1 70 54 71 83 51 54 69 16 92 33 48 61 43 52 1 89 19 67 48))

(defn largest-product-at-right
  [size]
  (apply max
         (map #(reduce * (take 4
                               (drop % grid)))
              (range 0 400))))



(defn largest-product-at-bottom
  [size]
  (apply max
         (map #(* (first (drop % grid))
                  (first (drop (+ % size) grid))
                  (first (drop (+ % (* 2 size)) grid))
                  (first (drop (+ % (* 3 size)) grid)))
              (range 0 340))))




(defn diag-index
  [size]
  (filter #(> 17 (rem % size)) (range 0 (* (- size 3) size))))

(defn diag-sum
  [size]
  (apply max (map #(* (first (drop % grid))
                      (first (drop (+ % 1 size) grid))
                      (first (drop (+ % 2 (* 2 size)) grid))
                      (first (drop (+ % 3 (* 3 size)) grid)))
                  (diag-index size))))


(defn rev-diag-index
  [size]
  (filter #(< 2 (rem % size)) (range 0 (* (- size 3) size))))

(defn rev-diag-sum
  [size]
  (apply max (map #(* (first (drop % grid))
                      (first (drop (+ % (- size 1)) grid))
                      (first (drop (+ % (- (* 2 size) 2)) grid))
                      (first (drop (+ % (- (* 3 size) 3)) grid)))
                  (rev-diag-index size))))




(defn largest-product-of-all
  [size]
  (max (largest-product-at-right size) (largest-product-at-bottom size) (diag-sum size) (rev-diag-sum size)))



;;;;;;;;; Codechef problems  Begineer level

;;;;; ATM
;;https://www.codechef.com/problems/HS08TEST

(defn take-input
  [filename]
  (def file (slurp (str "resources/" filename)))
  (def r (map #(Integer/parseInt %)
              (str/split file #"[\s]"))) r)


(defn atm
  "Reading input from file which is amount to withdraw and balance."
  [filename]
  (let [withdraw (first (take-input filename))
        balance (second (take-input filename))]
    (if (< balance
           withdraw)
      balance
      (if (= 0 (rem withdraw 5))
        (- balance withdraw 0.5)
        balance))))



;;;; Enormous Input Test
;; https://www.codechef.com/problems/INTEST
(defn enormousInputTest
  "print count of numbers which are divisible by k from n elements"
  [filename]
  (def k (second (take-input filename)))
  (count (filter #(= 0
                     (rem % k)) (drop 2
                                      (take-input filename)))))


;;;; Factorial
;;https://www.codechef.com/problems/FCTRL

(defn f
  [n]
  (if (= n 1)
    n
    (* n (f (dec n)))))

(defn fact
  "It prints factorial of a number"
  [filename]
  (map #(f %)
       (drop 1
             (take-input filename))))


;;;;; 4clojure problems


;; Nth Element
;;http://www.4clojure.com/problem/21

(defn nth-element
  [col n]
  (if (zero? n)
    (first col)
    (recur (rest col) (dec n))))


;;; count a sequence
;;http://www.4clojure.com/problem/22

(defn cnt-a-seq
  ([col]
   (cnt-a-seq col 0))
  ([col out]
   (if (empty? col)
     out
     (recur (rest col) (inc out)))))



;;; Reverse a sequence
;;http://www.4clojure.com/problem/23

(defn rev-a-seq
  [col]
  (into () col))


;;; Sum it all up
;;http://www.4clojure.com/problem/24

(defn sum-it-all-up
  [col]
  (reduce + col))


;;; find the odd no
;;http://www.4clojure.com/problem/25

(defn odd-no
  [col]
  (filter odd? col))



;;; Interleave 2 seq
;;http://www.4clojure.com/problem/39

(defn interleave-seq
  [a b]
  (mapcat vector a b))


;;; Interpose 2 seq
;;http://www.4clojure.com/problem/40

(defn interpose-seq
  [v coll]
  (butlast (mapcat #(vector % v) coll)))


;;; drop every nth item
;;http://www.4clojure.com/problem/41

(defn drop-nth
  [col n]
  #(mapcat (fn [a]
             (take (- %2 1) a)
             (partition-all %2 %1))) col n)



;;http://www.4clojure.com/problem/19

(defn last-element
  [col]
  (nth col (dec (count col))))


;;http://www.4clojure.com/problem/20

(defn second-last-element
  [col]
  #(first (take-last 2 %)col))


;;http://www.4clojure.com/problem/22

(defn count-seq
  [col]
  (let [b col]
    (alength (to-array b))))






;;; clock-hand problem

(defn take-ip
  "Reads colan separated strings from file, returns a list of [hh mm] vectors"
  [fname]
  (let [content (slurp (str "resources/" fname))
        input (str/split content #"[\s]")]
    (map #(str/split % #":") input)))



(defn angles-in-rad
  [hh mm]
  (let [hr-angle-d (- (+
                       (* hh 30)
                       (/ mm 12))
                      90)
        mm-angle-d  (- (* mm 6) 90)]
    (list  (Math/toRadians (- hr-angle-d)) (Math/toRadians (-  mm-angle-d)))))


(defn co-ordinates
  [angle len]
  (let [x  (+ 10 (* len  (Math/cos angle)))
        y  (+ 10 (* len  (Math/sin angle)))]
    [(Math/round x) (Math/round y)]))


(defn time-to-angle
  [time]
  (let [ [hh mm] (map #(Integer/parseInt %) time)
        angles  (angles-in-rad hh mm)]
    (map co-ordinates angles [6 9])))



(first (take-ip "clock-hand"))

(defn main-clock
  [fname]
  (let [input (take-ip fname)]
   #_ (print input)
    (map time-to-angle input)))



;;;  Game of life



(defn create-board
  ([n] (create-board [] []  n) )
  ([arr sub-arr n]
   (if (= n (count arr))
     arr
     (if (= n  (count sub-arr))
       (recur (conj arr sub-arr) []  n)
       (recur arr (conj sub-arr
                        (rand-int 2)) n)))))


(defn get-neighbours
  "Returns the sum of neighbours"
  [arr x y]
  (reduce +
          (map (fn [i j]
                 (get-in arr [i j] 0))
               [(- x 1) x (+ x 1)
                (- x 1) (+ x 1)
                (- x 1) x (+ x 1)]
               [(- y 1) (- y 1) (- y 1)
                 y  y
                (+ y 1) (+ y 1) (+ y 1)])))



(defn upd-board
  ([arr n] (upd-board arr n 0 0 [] []))
  ([arr n x y sub-arr new-arr]
   (if (= n (count new-arr))
     new-arr
     (if (= n (count sub-arr))
       (upd-board arr n (inc x) 0 [] (conj new-arr sub-arr))
       (let [sum-nbr (get-neighbours arr x y)]
         (if (or (= sum-nbr 2) (= sum-nbr 3))
           (upd-board arr n x (inc y)
                      (conj sub-arr 1)
                      new-arr)
           (upd-board arr n x (inc  y)
                      (conj sub-arr 0)
                      new-arr)))))))


(defn print-arr
  [arr]
  (when (not (empty? arr))
    (println (first arr))
    (recur (rest arr))))



;;; encryption problem

(defn encrypt-help
  [plain key sort-key cipher]
  (if (empty? key)
    cipher
    (let [next-pos (.indexOf sort-key
                             (first key))
          next-ele (nth plain next-pos)]
      (recur plain (rest key) sort-key (str cipher (apply str  next-ele))))))



(defn encrypt
  [plain key]
  (let [sort-key (sort key)
        key-len (count key)]
    (encrypt-help (partition key-len plain) key  sort-key "")))



