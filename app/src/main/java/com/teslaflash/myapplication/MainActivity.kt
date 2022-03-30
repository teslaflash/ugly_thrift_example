package com.teslaflash.myapplication

import android.R.attr
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.TextView
import com.rabbitmq.client.*
import java.lang.Exception
import java.net.URISyntaxException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import android.view.View
import com.google.flatbuffers.FlatBufferBuilder
import java.text.SimpleDateFormat
import java.util.*

import com.rabbitmq.client.AMQP

import com.rabbitmq.client.Envelope

import com.rabbitmq.client.DefaultConsumer
import java.io.IOException
import java.nio.ByteBuffer
import android.R.attr.data
import com.microsoft.thrifty.protocol.BinaryProtocol
import com.microsoft.thrifty.protocol.JsonProtocol
import com.microsoft.thrifty.protocol.SimpleJsonProtocol
import com.microsoft.thrifty.transport.BufferTransport
import com.microsoft.thrifty.transport.Transport
import org.apache.avro.io.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    private var subscribeThread: Thread? = null
    private lateinit var text: TextView
    var factory: ConnectionFactory = ConnectionFactory()
    lateinit var conn: Connection;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.hello_text)

        setupConnectionFactory()



        val incomingMessageHandler: Handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                val message = msg.data.getString("msg")
                val tv = findViewById<TextView>(R.id.hello_text)
                val now = Date()
                val ft = SimpleDateFormat("hh:mm:ss")
                tv.append(ft.format(now) + ' ' + message + '\n')
            }
        }

        subscribe(incomingMessageHandler);
    }

    private fun setupConnectionFactory() {
        val uri = "amqp://inspector:123@localhost:5672/pushes"
        try {

            factory.host = "10.0.2.2"
            factory.port = 5672
            factory.username = "1"
            factory.password = "1"
            factory.virtualHost = "pushes"
        } catch (e1: KeyManagementException) {
            e1.printStackTrace()
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        } catch (e1: URISyntaxException) {
            e1.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribeThread!!.interrupt()
    }

    fun subscribe(handler: Handler) {
        println("hello")



        subscribeThread = Thread {
//            var factory:ConnectionFactory = ConnectionFactory()
//            factory.host = "10.0.2.2"
//            factory.port = 5672
//            factory.username = "inspector"
//            factory.password = "123"
//            factory.virtualHost = "pushes"

            while (true) {
                val queueName = "666"
                val autoAck = false
                val connection: Connection = this.factory.newConnection()
                val channel: Channel = connection.createChannel()
                    try {

                        channel.basicQos(1)
//                    channel.queueBind("pusher-queue-2", "amq.direct", "my key")

//                    var response: GetResponse = channel.basicGet("pusher-queue-2", true);
//                    Log.d("respo", response.messageCount.toString())
                    channel.basicConsume(queueName, autoAck, "myConsumerTag",
                        object : DefaultConsumer(channel) {
                            @Throws(IOException::class)
                            override fun handleDelivery(
                                consumerTag: String,
                                envelope: Envelope,
                                properties: AMQP.BasicProperties,
                                body: ByteArray
                            ) {
                                val routingKey = envelope.routingKey
                                val contentType = properties.contentType
                                val deliveryTag = envelope.deliveryTag

                                val message: String = body.decodeToString()
                                Log.d("baby", message)


                                val t = BufferTransport()

                                t.write(body)

                                val p = JsonProtocol(t, true)

                                try {
                                    val text = PushMsget.ADAPTER.read(p)

                                    val c = text.context

                                    when (c) {
                                        is Contextual.Text -> Log.d("class text: ", c.value.text.toString())
                                        is Contextual.Task -> Log.d("class task: ", c.value.uuidString.toString())
                                        else -> Log.d("unknown", "class")
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                                t.flush()
                                t.close()

                                // (process the message components here ...)
                                channel.basicAck(deliveryTag, false)
                            }
                        })
                    Thread.sleep(10000)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Thread.sleep(10000
                    )
                } finally {
                        connection.close()
                }
            }
        }
        subscribeThread!!.start()
    }


}