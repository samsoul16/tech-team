(ns noob2pro.radhakrishna.test
  (:require[expectations :as expect]
           [noob2pro.radhakrishna.core :refer :all]))

(expect/expect true (mod-3-5 3))

(expect/expect true (mod-3-5 5))

(expect/expect false (mod-3-5 7))

(expect/expect 23 (mult-3-5 10))

(expect/expect 78 (mult-3-5 20))

(expect/expect 195 (mult-3-5 30))

(expect/expect 2 (fibo 2))

(expect/expect 8 (fibo 5))

(expect/expect 89 (fibo 10))

(expect/expect 2 (sum-even-fibo 4))

(expect/expect 10 (sum-even-fibo 5))

(expect/expect 44 (sum-even-fibo 10))

(expect/expect true (prime? 2))

(expect/expect true (prime? 13))

(expect/expect true (prime? 29))

(expect/expect false (prime? 12))

(expect/expect [] (get-factors 29 (range 2 29)))

(expect/expect [2 3 5 6 10 15] (get-factors 30 (range 2 30)))

(expect/expect [3 5] (get-factors 15 (range 2 15)))

(expect/expect [2 3 5] (prime-factors 30))

(expect/expect [] (prime-factors 29))

(expect/expect [5 7 13 29] (prime-factors 13195))

(expect/expect [3 5] (prime-factors 15))

(expect/expect 5 (largest-prime-factor 30))

(expect/expect 29 (largest-prime-factor 13195))

(expect/expect false (palindrome? 13195))

(expect/expect true (palindrome? 13131))

(expect/expect false (palindrome? 9000))

(expect/expect false (palindrome? 13195 ))

(expect/expect false (evenly-divisible? 131900 10))

(expect/expect true (evenly-divisible? 2432902008176640000 20))

;(expect/expect 232792560 (check 20))

(expect/expect 385 (sum-of-square 10))

(expect/expect 3025 (square-of-sum 10))

(expect/expect 2640 (diff 10))

(expect/expect 13 (prime-at-pos 0 0 2 6))

(expect/expect 17 (sum-prime 0 2 10))

(expect/expect '(1 2 3) (digits 123))

(expect/expect 24 (great-prod 0 '(1 2 3 4) 4))

(expect/expect 120 (great-prod 0 '(1 2 3 4 5) 4))

(expect/expect 28 (get-tri 7))

(expect/expect 21 (get-tri 6))

(expect/expect [1 3 7 21] (get-factors-new 21))

(expect/expect [1 3 7 21] (get-factors-helper 21 1 []))

(expect/expect 4 (get-factors-count 21))

(expect/expect 6 (get-factors-count 28))

(expect/expect 6 (factor-of-triangle 7))

(expect/expect 28 (give-triangle-number 5))

(expect/expect [3 6 9] (mod-no 3 10))

