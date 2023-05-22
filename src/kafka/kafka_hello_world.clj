(ns kafka.kafka-hello-world
  (:gen-class)
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
    (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
    (org.apache.kafka.clients.consumer KafkaConsumer)))

(def properties
  (atom {:producer {"client.id",         (.getHostName (InetAddress/getLocalHost))
                    "group.id",          "group1"
                    "bootstrap.servers", "localhost:9092"
                    "key.serializer"     StringSerializer
                    "value.serializer"   StringSerializer}
         :consumer {"client.id",         (.getHostName (InetAddress/getLocalHost))
                    "group.id",          "group1"
                    "bootstrap.servers", "localhost:9092"
                    "key.deserializer"   StringDeserializer
                    "value.deserializer" StringDeserializer}}))



(defn build-producer
  []
  (KafkaProducer. (get @properties :producer)))

(defn build-consumer
  [topic]
  (let [consumer         (KafkaConsumer. (get @properties :consumer))
        _subscribe-topic (.subscribe consumer [topic])]
    consumer))

(def producer-consumer
  (let [producer          (build-producer)
        consumer          (build-consumer "example-topic")
        producer-consumer (atom [producer consumer])]
    producer-consumer))

(defn send-msg!
  [producer msg-value]
  (let [producer-configuration "example-topic"
        msg                    (ProducerRecord. producer-configuration "msg" msg-value)
        send-msg               (.send producer msg)]
    send-msg))

(defn receive-messages
  [consumer]
  (let [records  (.poll consumer (Duration/ofMillis 100000))
        messages (->> records
                      (mapv (fn [record]
                              (str (.value record)))))]
    messages))

(comment

  ;;------------------------------function evaluation----------------------------------
  (deref properties)
  #_=> {:producer {"client.id"         "m-p7p1q34c6p",
                   "group.id"          "group1",
                   "bootstrap.servers" "localhost:9092",
                   "key.serializer"    org.apache.kafka.common.serialization.StringSerializer,
                   "value.serializer"  org.apache.kafka.common.serialization.StringSerializer},
        :consumer {"client.id"          "m-p7p1q34c6p",
                   "group.id"           "group1",
                   "bootstrap.servers"  "localhost:9092",
                   "key.deserializer"   org.apache.kafka.common.serialization.StringDeserializer,
                   "value.deserializer" org.apache.kafka.common.serialization.StringDeserializer}}


  (build-producer)
  #_=> #_#object[org.apache.kafka.clients.producer.KafkaProducer
                 0xd7d9d44
                 "org.apache.kafka.clients.producer.KafkaProducer@d7d9d44"]

  (build-consumer "example-topic")
  #_=> #_#object[org.apache.kafka.clients.consumer.KafkaConsumer
                 0x5e1f9219
                 "org.apache.kafka.clients.consumer.KafkaConsumer@5e1f9219"]

  (deref producer-consumer)
  #_=> #_[#object[org.apache.kafka.clients.producer.KafkaProducer
                  0x301c93e1
                  "org.apache.kafka.clients.producer.KafkaProducer@301c93e1"]
          #object[org.apache.kafka.clients.consumer.KafkaConsumer
                  0x4f2f21e6
                  "org.apache.kafka.clients.consumer.KafkaConsumer@4f2f21e6"]]


  (send-msg! (build-producer) "Message content")
  #_=> #_#object[org.apache.kafka.clients.producer.internals.FutureRecordMetadata
                 0x4bd4dad9
                 "org.apache.kafka.clients.producer.internals.FutureRecordMetadata@4bd4dad9"]

  (receive-messages (build-consumer "example-topic"))
  #_=> ["Msg: Message content"]

  )
