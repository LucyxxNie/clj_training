(ns kafka.kafka-hello-world
  (:gen-class)
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
    (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
    (org.apache.kafka.clients.consumer KafkaConsumer)))

(def properties
  (atom {:producer-config {"client.id",         (.getHostName (InetAddress/getLocalHost))
                           "group.id",          "group1"
                           "bootstrap.servers", "localhost:9092"
                           "key.serializer"     StringSerializer
                           "value.serializer"   StringSerializer}
         :consumer-config {"client.id",         (.getHostName (InetAddress/getLocalHost))
                           "group.id",          "group1"
                           "bootstrap.servers", "localhost:9092"
                           "key.deserializer"   StringDeserializer
                           "value.deserializer" StringDeserializer}}))

(def clients (atom {:producer [] :consumer []}))

(defn build-producer
  []
  (let [producer (KafkaProducer. (get @properties :producer-config))
        _        (swap! clients update :producer conj producer)]
    producer))

(defn build-consumer
  []
  (let [consumer (KafkaConsumer. (get @properties :consumer-config))
        _        (.subscribe consumer ["example-topic"])
        _        (swap! clients update :consumer conj consumer)]
    consumer))

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
  #_=> {:producer-config {"client.id"         "m-p7p1q34c6p",
                          "group.id"          "group1",
                          "bootstrap.servers" "localhost:9092",
                          "key.serializer"    org.apache.kafka.common.serialization.StringSerializer,
                          "value.serializer"  org.apache.kafka.common.serialization.StringSerializer},
        :consumer-config {"client.id"          "m-p7p1q34c6p",
                          "group.id"           "group1",
                          "bootstrap.servers"  "localhost:9092",
                          "key.deserializer"   org.apache.kafka.common.serialization.StringDeserializer,
                          "value.deserializer" org.apache.kafka.common.serialization.StringDeserializer}}



  (build-producer)
  #_=> #_#object[org.apache.kafka.clients.producer.KafkaProducer
                 0x7b8aebb5
                 "org.apache.kafka.clients.producer.KafkaProducer@7b8aebb5"]
  (build-consumer)
  #_=> #_#object[org.apache.kafka.clients.consumer.KafkaConsumer
                 0x64be53b9
                 "org.apache.kafka.clients.consumer.KafkaConsumer@64be53b9"]
  (deref clients)
  #_=> #_{:producer [#object[org.apache.kafka.clients.producer.KafkaProducer
                             0xf5c23a0
                             "org.apache.kafka.clients.producer.KafkaProducer@f5c23a0"]],
          :consumer [#object[org.apache.kafka.clients.consumer.KafkaConsumer
                             0xf4f0817
                             "org.apache.kafka.clients.consumer.KafkaConsumer@f4f0817"]]}

  (send-msg! (get-in @clients [:producer 0]) "Message content")
  #_=> #_#object[org.apache.kafka.clients.producer.internals.FutureRecordMetadata
                 0x4bd4dad9
                 "org.apache.kafka.clients.producer.internals.FutureRecordMetadata@4bd4dad9"]

  (receive-messages (get-in @clients [:consumer 0]))
  #_=> ["Msg: Message content"]

  )
