(ns clj-training.Practice2-keyboard-shortcuts)
;This namespace is purely for practicing keyboard shortcuts.

;;barf forward -> pop out first element (control + comm + ,)
;;barf backward -> pop out last element (control + comm + .)
(def func1 [x y z])

;;slurp forward -> add first element (control + shift + comm +,)
;; slurp backward -> add last element (control + shift + comm +.)
(def func2 [x y z])


;;select tab
;;next tab (control + tab)
;;prev tab (control + shift + tab)
(get (get [1 2 3] 2) 2 "nil")

