(ns org.bartleby.cljsoda.impl.macros)

(defmacro create-query [select _where limit offset]
  `(comp
    (drop ~offset)
    (take ~limit)
    ~(if (= select :all)
       nil
       `(filter ~(fn [v] v)))))

(comment
  (into [] (create-query :* nil 20 20) (range 100))
  (macroexpand '(create-query :* nil 1 20)))