# Bridge Logistics Issues

1. Incompatibility GSON with records

```java
org.springframework.amqp.rabbit.support.ListenerExecutionFailedException: Listener method 'receiveMessage' threw exception
        at org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter.invokeListenerMethod(MessageListenerAdapter.java:374) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter.onMessage(MessageListenerAdapter.java:293) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doInvokeListener(AbstractMessageListenerContainer.java:1656) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.actualInvokeListener(AbstractMessageListenerContainer.java:1575) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.invokeListener(AbstractMessageListenerContainer.java:1563) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.doExecuteListener(AbstractMessageListenerContainer.java:1554) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer.executeListener(AbstractMessageListenerContainer.java:1498) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.doReceiveAndExecute(SimpleMessageListenerContainer.java:968) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.receiveAndExecute(SimpleMessageListenerContainer.java:914) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.access$1600(SimpleMessageListenerContainer.java:83) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.mainLoop(SimpleMessageListenerContainer.java:1289) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1195) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        at java.base/java.lang.Thread.run(Thread.java:831) ~[na:na]
Caused by: java.lang.AssertionError: AssertionError (GSON 2.8.8): java.lang.IllegalAccessException: Can not set final java.lang.Long field org.jesperancinha.logistics.sensor.collector.data.VehicleLogDto.timestamp to java.lang.Long
        at com.google.gson.Gson.fromJson(Gson.java:949) ~[gson-2.8.8.jar!/:na]
        at com.google.gson.Gson.fromJson(Gson.java:897) ~[gson-2.8.8.jar!/:na]
        at com.google.gson.Gson.fromJson(Gson.java:846) ~[gson-2.8.8.jar!/:na]
        at com.google.gson.Gson.fromJson(Gson.java:817) ~[gson-2.8.8.jar!/:na]
        at org.jesperancinha.logistics.sensor.collector.rabbitmq.VehicleSensorReceiver.receiveMessage(VehicleSensorReceiver.java:38) ~[classes!/:na]
        at jdk.internal.reflect.GeneratedMethodAccessor72.invoke(Unknown Source) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:567) ~[na:na]
        at org.springframework.util.MethodInvoker.invoke(MethodInvoker.java:283) ~[spring-core-5.3.9.jar!/:5.3.9]
        at org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter.invokeListenerMethod(MessageListenerAdapter.java:366) ~[spring-rabbit-2.3.10.jar!/:2.3.10]
        ... 12 common frames omitted
Caused by: java.lang.AssertionError: java.lang.IllegalAccessException: Can not set final java.lang.Long field org.jesperancinha.logistics.sensor.collector.data.VehicleLogDto.timestamp to java.lang.Long
        at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.read(ReflectiveTypeAdapterFactory.java:228) ~[gson-2.8.8.jar!/:na]
        at com.google.gson.Gson.fromJson(Gson.java:932) ~[gson-2.8.8.jar!/:na]
        ... 21 common frames omitted
Caused by: java.lang.IllegalAccessException: Can not set final java.lang.Long field org.jesperancinha.logistics.sensor.collector.data.VehicleLogDto.timestamp to java.lang.Long
        at java.base/jdk.internal.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:76) ~[na:na]
        at java.base/jdk.internal.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessException(UnsafeFieldAccessorImpl.java:80) ~[na:na]
        at java.base/jdk.internal.reflect.UnsafeQualifiedObjectFieldAccessorImpl.set(UnsafeQualifiedObjectFieldAccessorImpl.java:79) ~[na:na]
        at java.base/java.lang.reflect.Field.set(Field.java:793) ~[na:na]
        at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$1.read(ReflectiveTypeAdapterFactory.java:133) ~[gson-2.8.8.jar!/:na]
        at com.google.gson.internal.bind.ReflectiveTypeAdapterFactory$Adapter.read(ReflectiveTypeAdapterFactory.java:222) ~[gson-2.8.8.jar!/:na]
        ... 22 common frames omitted
```