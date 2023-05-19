(ns kafka.producer
  (:gen-class)
  (:import
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringSerializer)
    (org.apache.kafka.clients.producer ProducerRecord)))

(def producer-properties
  {"client.id",         (.getHostName (InetAddress/getLocalHost))
   "group.id",          "group1"
   "bootstrap.servers", "localhost:9092"
   "key.serializer"     StringSerializer
   "value.serializer"   StringSerializer})

(defn new-msg
  [topic key msg]
  (println "Msg created successfully.")
  (ProducerRecord. topic key msg))

(defn send-msg
  [producer-properties]
  (let [producer-topic "example-topic"
        producer       (KafkaProducer. producer-properties)
        msg            (new-msg producer-topic "msg" "Hello World")]
    (println "msg sent successfully!")
    (.send producer msg)
    (.close producer)))

(send-msg producer-properties)

