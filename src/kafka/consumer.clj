(ns kafka.consumer
  (:gen-class)
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringDeserializer)
    (org.apache.kafka.common.errors WakeupException)))

(def consumer-properties
  {"client.id",         (.getHostName (InetAddress/getLocalHost))
   "group.id",          "group1"
   "bootstrap.servers", "localhost:9092"
   "key.deserializer"   StringDeserializer
   "value.deserializer" StringDeserializer})

(defn consumer-subscribe
  [consumer topic]
  (.subscribe consumer [topic])
  (.commitAsync consumer))

(defn receive-msg-single-thread
  [consumer-properties]
  (let [consumer (KafkaConsumer. consumer-properties)
        topic    "example-topic"]
    (consumer-subscribe consumer topic)
    (while true
      (let [records (.poll consumer (Duration/ofMillis 1))]
        (doseq [record records]
          (println "Message: " (.value record)))))))

(defn shutdown
  [state consumer]
  (reset! state true)
  (.wake consumer))

(defn receive-msg-multi-thread
  [consumer-properties]
  (let [consumer (KafkaConsumer. consumer-properties)
        topic    "example-topic"
        state    (atom false)]
    (try
      (consumer-subscribe consumer topic)
      (while (not @state)
        (let [records (.poll consumer (Duration/ofMillis 1))]
          (doseq [record records]
            (println "Message: " (.value record)))))
      (catch WakeupException e
        (when-not @state
          (throw e)))
      (finally (.close consumer)))))



(comment
  (receive-msg-single-thread consumer-properties)
  #_=> Message: Hello World


  (receive-msg-multi-thread consumer-properties)
  #_=> Message: Hello World
  )
