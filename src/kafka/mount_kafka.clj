(ns kafka.mount-kafka
  (:gen-class)
  (:require
    [mount.core :as mount :refer [defstate]])
  (:import
    (java.time Duration)
    (java.net InetAddress)
    (org.apache.kafka.common.serialization StringSerializer StringDeserializer)
    (org.apache.kafka.clients.producer KafkaProducer ProducerRecord)
    (org.apache.kafka.clients.consumer KafkaConsumer)))

(defstate properties
          :start {:producer-config {"client.id",         (.getHostName (InetAddress/getLocalHost))
                                    "group.id",          "group1"
                                    "bootstrap.servers", "localhost:9092"
                                    "key.serializer"     StringSerializer
                                    "value.serializer"   StringSerializer}
                  :producer-topic  "example-topic"
                  :consumer-config {"client.id",         (.getHostName (InetAddress/getLocalHost))
                                    "group.id",          "group1"
                                    "bootstrap.servers", "localhost:9092"
                                    "key.deserializer"   StringDeserializer
                                    "value.deserializer" StringDeserializer}
                  :consumer-topic  "example-topic"})

(defn build-producer
  [producer-config]
  (KafkaProducer. producer-config))

(defn build-consumer
  [consumer-config]
  (let [consumer       (KafkaConsumer. consumer-config)
        consumer-topic (:consumer-topic properties)
        _              (.subscribe consumer [consumer-topic])]
    consumer))

(defstate clients
          :start {:producer (build-producer (:producer-config properties))
                  :consumer (build-consumer (:consumer-config properties))})

(defn send-msg!
  [producer msg-value]
  (let [producer-topic (:producer-topic properties)
        msg            (ProducerRecord. producer-topic "msg" msg-value)
        send-msg       (.send producer msg)]
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
  (mount/start "#'kafka.mount-kafka/properties")
  #_=> {:started ["#'kafka.mount-kafka/properties"]}

  properties
  #_=> {:producer-config {"client.id"         "m-p7p1q34c6p",
                          "group.id"          "group1",
                          "bootstrap.servers" "localhost:9092",
                          "key.serializer"    org.apache.kafka.common.serialization.StringSerializer,
                          "value.serializer"  org.apache.kafka.common.serialization.StringSerializer},
        :producer-topic  "example-topic",
        :consumer-config {"client.id"          "m-p7p1q34c6p",
                          "group.id"           "group1",
                          "bootstrap.servers"  "localhost:9092",
                          "key.deserializer"   org.apache.kafka.common.serialization.StringDeserializer,
                          "value.deserializer" org.apache.kafka.common.serialization.StringDeserializer},
        :consumer-topic  "example-topic"}

  (mount/start "#'kafka.mount-kafka/clients")
  #_ => {:started ["#'kafka.mount-kafka/clients"]}

  clients
  #_=> #_{:producer #object[org.apache.kafka.clients.producer.KafkaProducer
                            0x485eb97d
                            "org.apache.kafka.clients.producer.KafkaProducer@485eb97d"],
          :consumer #object[org.apache.kafka.clients.consumer.KafkaConsumer
                            0xba1a025
                            "org.apache.kafka.clients.consumer.KafkaConsumer@ba1a025"]}

  (build-producer (:producer-config properties))
  #_=> #_#object[org.apache.kafka.clients.producer.KafkaProducer
                 0x7b8aebb5
                 "org.apache.kafka.clients.producer.KafkaProducer@7b8aebb5"]

  (build-consumer (:consumer-config properties))
  #_=> #_#object[org.apache.kafka.clients.consumer.KafkaConsumer
                 0x64be53b9
                 "org.apache.kafka.clients.consumer.KafkaConsumer@64be53b9"]

  (send-msg! (:producer clients) "Message content")
  #_=> #_#object[org.apache.kafka.clients.producer.internals.FutureRecordMetadata
                 0x4bd4dad9
                 "org.apache.kafka.clients.producer.internals.FutureRecordMetadata@4bd4dad9"]

  (receive-messages (:consumer clients))
  #_=> ["Message content"]

  )
