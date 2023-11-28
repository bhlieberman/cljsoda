(ns org.bartleby.cljsoda.impl.macros)

(defmacro do-create-query
  "Transforms provided query parameters into a
   transducer."
  [select _where limit offset & body]
  `(eduction
    ~@(cond->> '()
        limit (cons `(take ~limit))
        offset (cons `(drop ~offset))
        select (cons `(keep ~(fn [item] item)))) ~@body))

(defn create-query
  "Calls `do-create-query` macro with provided query parameters."
  [{:keys [select where limit offset]} body]
  (do-create-query select where limit offset body))

(comment)