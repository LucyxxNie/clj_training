(ns kafka.producer-consumer
  (:gen-class)
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.errors WakeupException)
    (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
    (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
    (org.apache.kafka.clients.consumer KafkaConsumer)))

(def producer-properties
  {"client.id",         (.getHostName (InetAddress/getLocalHost))
   "group.id",          "group1"
   "bootstrap.servers", "localhost:9092"
   "key.serializer"     StringSerializer
   "value.serializer"   StringSerializer})

(def consumer-properties
  {"client.id",         (.getHostName (InetAddress/getLocalHost))
   "group.id",          "group1"
   "bootstrap.servers", "localhost:9092"
   "key.deserializer"   StringDeserializer
   "value.deserializer" StringDeserializer})

(defn build-producer
  [producer-properties]
  (KafkaProducer. producer-properties))

(defn build-consumer
  [consumer-properties]
  (KafkaConsumer. consumer-properties))

(defn new-msg
  [topic key msg]
  (println "Msg created successfully.")
  (ProducerRecord. topic key msg))

(defn send-msg!
  [producer msg-value]
  (let [producer-topic "example-topic"
        msg            (new-msg producer-topic "msg" msg-value)]
    (println "Msg sent successfully!")
    (.send producer msg)
    (.close producer)))

(defn consumer-subscribe!
  [consumer topic]
  (.subscribe consumer [topic])
  (.commitAsync consumer))

(defn receive-msg-single-thread
  [consumer topic]
  (consumer-subscribe! consumer topic)
  (let [records (.poll consumer (Duration/ofMillis 100000))]
    (mapv #(str "Msg: " (.value %)) records)))

(defn receive-msg-multi-thread
  [consumer]
  (let [topic "example-topic"
        state (atom false)]
    (try
      (consumer-subscribe! consumer topic)
      (while (not @state)
        (let [records (.poll consumer (Duration/ofMillis 1))]
          (mapv #(str "Message: " (.value %)) records)))
      (catch WakeupException e
        (when-not @state
          (throw e)))
      (finally (.close consumer)))))

(defn shutdown
  [state consumer]
  (reset! state true)
  (.wake consumer))

(comment
  (do producer-properties)
  #_=> {"client.id"         "m-p7p1q34c6p",
        "group.id"          "group1",
        "bootstrap.servers" "localhost:9092",
        "key.serializer"    org.apache.kafka.common.serialization.StringSerializer,
        "value.serializer"  org.apache.kafka.common.serialization.StringSerializer}

  (do consumer-properties)
  #_=> {"client.id"          "m-p7p1q34c6p",
        "group.id"           "group1",
        "bootstrap.servers"  "localhost:9092",
        "key.deserializer"   org.apache.kafka.common.serialization.StringDeserializer,
        "value.deserializer" org.apache.kafka.common.serialization.StringDeserializer}

  (build-producer producer-properties)
  #_=> #object[org.apache.kafka.clients.producer.KafkaProducer
               0x6e7e1683
               "org.apache.kafka.clients.producer.KafkaProducer@6e7e1683"]

  (build-consumer consumer-properties)
  #_=> #object[org.apache.kafka.clients.consumer.KafkaConsumer
               0x476b779f
               "org.apache.kafka.clients.consumer.KafkaConsumer@476b779f"]

  (new-msg "topic" "key" "value-msg")
  ;Msg created successfully.
  #_=> #object[org.apache.kafka.clients.producer.ProducerRecord
               0x4a7a2ec1
               "ProducerRecord(topic=topic, partition=null, headers=RecordHeaders(headers = [], isReadOnly = false), key=key, value=value-msg, timestamp=null)"]

  (send-msg! (build-producer producer-properties) "Message content")
  ;Msg created successfully.
  ;Msg sent successfully!
  #_=> nil

  (consumer-subscribe! (build-consumer consumer-properties)
                       "Parse the topic consumer wants to listen")
  #_=> nil

  (receive-msg-single-thread (build-consumer consumer-properties) "example-topic")
  #_=> ["Msg: Message content"]

  )