(ns kafka.producer-consumer-copy
  (:gen-class)
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
    (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
    (org.apache.kafka.clients.consumer KafkaConsumer)))

(defn build-producer
  [producer-properties]
  (KafkaProducer. producer-properties))

(defn build-consumer
  [consumer-properties topic]
  (let [consumer        (KafkaConsumer. consumer-properties)
        subscribe-topic (.subscribe consumer [topic])]
    consumer))

(defn new-msg
  [topic key msg]
  (ProducerRecord. topic key msg))

(defn send-msg!
  [producer msg-value]
  (let [producer-configuration "example-topic"
        msg                    (new-msg producer-configuration "msg" msg-value)
        sending-msg            (.send producer msg)]
    (println "Msg sent successfully!")
    sending-msg))

(defn receive-msg
  [consumer]
  (let [records (.poll consumer (Duration/ofMillis 100000))]
    (->> records
         (mapv (fn [record]
                 (str (.value record)))))))


(comment
  ;;--------------------Kafka producer/consumer properties------------------------
  (do (def producer-properties
        {"client.id",         (.getHostName (InetAddress/getLocalHost))
         "group.id",          "group1"
         "bootstrap.servers", "localhost:9092"
         "key.serializer"     StringSerializer
         "value.serializer"   StringSerializer})
      producer-properties)

  #_=> {"client.id"         "m-p7p1q34c6p",
        "group.id"          "group1",
        "bootstrap.servers" "localhost:9092",
        "key.serializer"    org.apache.kafka.common.serialization.StringSerializer,
        "value.serializer"  org.apache.kafka.common.serialization.StringSerializer}

  (do (def consumer-properties
        {"client.id",         (.getHostName (InetAddress/getLocalHost))
         "group.id",          "group1"
         "bootstrap.servers", "localhost:9092"
         "key.deserializer"   StringDeserializer
         "value.deserializer" StringDeserializer})
      consumer-properties)

  #_=> {"client.id"          "m-p7p1q34c6p",
        "group.id"           "group1",
        "bootstrap.servers"  "localhost:9092",
        "key.deserializer"   org.apache.kafka.common.serialization.StringDeserializer,
        "value.deserializer" org.apache.kafka.common.serialization.StringDeserializer}

  ;;------------------------------function evaluation----------------------------------
  (build-producer producer-properties)
  #_=> #object[org.apache.kafka.clients.producer.KafkaProducer
               0x6e7e1683
               "org.apache.kafka.clients.producer.KafkaProducer@6e7e1683"]

  (build-consumer consumer-properties "example-topic")
  #_=> nil

  (send-msg! (build-producer producer-properties) "Message content")
  #_=> #object[org.apache.kafka.clients.producer.internals.FutureRecordMetadata
               0x4bd4dad9
               "org.apache.kafka.clients.producer.internals.FutureRecordMetadata@4bd4dad9"]

  (receive-messages (build-consumer consumer-properties "example-topic"))
  #_=> ["Msg: Message content"]

  )