(ns clj-training.basic-types)

;Types

;long -> can store 64 bits of binary code, Max value is 2^64-1, as the first digit is used to store sign
;By default, the number w/o casting will be recognized as long
(long/MAX_VALUE)

;biging -> bigger than long
(type 5N)

;int -> 32 bits
(type (int 0))
Integer/MIN_VALUE

;short -> 16 bits
(type (short 0))
Short/MAX_VALUE

;byte -> 8 bits
(type (byte 0))
Byte/MAX_VALUE


;;Fractional number

;double
(type 1.1)

;float
(float 1.1)

;ratio
(type 2/3)

;;Math operation

;basic operation

;clj will auto cast number to double if you add long and double
(+ 1 2.0) -> 3.0

;boolean

; = checks on everything including value and the type of the value
;== checks the value itself
(= 3 3.0) -> false
(= 3 3) -> true
(== 3 3.0) -> true

;;Strings

"cat"

(str "cat")
(str 'cat)
(str 1)
(str true)
(str '(1 2 3))
(str 1 2 3)
(str 1 "cat")
(.toLowerCase "MAX")
(.charAt "I have a cat" 3)
(.length "I have a cat")

;regex -> for patterns in text
(re-find #"cat" "I have a cat")
(re-matches #"[1-9]+" "1234")
(rest (re-matches #"(.+):(.+)" "mouse:treat"))

;;boolean
(boolean 1)
(boolean true)
(boolean "cat")

(and true true false)
(or true true false)
(not 2)
(not nil)
(and 1 2 3)

;;Data structure

; list
(list 1 2 3)
'(1 2 3)
(conj '(1 2 3) 4) => (4 1 2 3)
(nth '(1 2 3) 2) ->3
(nth '(1 2 3) 10 nil) -> nil
(concat '(1 2) '(3 4))
(drop 3 '(1 2 3 4 5)) -> (4 5)

;vectors
[1 2 3]
(vector 1 2 3)
(vec '(1 2 3))
(conj [1 2 3] 4) -> [1 2 3 4]
(into [1 2 3] [4 5])
(rest [1 2 3])
(next [1 2 3])
(last [1 2 3])
(count [1 2 3])
(get [:a :b :c] 3 "nil")

(= '(1 2 3) [1 2 3]) -> true

;sets
#{1 2 3}
(sort #{3 2 1})
(conj #{:a :b :c} :d) =># {:a :b :c :d}
(conj #{:a :b :c} :a) -> #{:a :b :c}
(disj #{"a" "b"} "a")
(contains? #{1 2 3} 3) ->true
(#{1 2 3} 3) -> 3
(set [1 2 3]) -># {1 2 3}

;maps
{:a 1 :b 2 :c 3}

;access map, you can use get or just key itself, you can set default value for all of them
(get {:a 1 :b 2 :c 3} :a) ;you can set default value for get
({:a 1 :b 2 :c 3} :a)
({:a 1 :b 2 :c 3} :d "not found")
(:d {:a 1 :b 2 :c 3} "not found")

(assoc {:a 1} :b 2)
(merge {:a 1} {:b 2})
(dissoc {:a 1 :b 2} :a)

