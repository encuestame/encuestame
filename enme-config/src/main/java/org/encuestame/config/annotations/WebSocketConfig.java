package org.encuestame.config.annotations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@ImportResource({"classpath:/config/files/websocket-context.xml"})
public abstract class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    public WebSocketConfig() {
//        super();
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*************** WSSSSSS ******************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//        System.out.println(("*****************************************"));
//    }
//
//    @Override
//	public void configureClientInboundChannel(ChannelRegistration arg0) {
//	}
//
//	@Override
//	public void configureClientOutboundChannel(ChannelRegistration registration) {
//		registration.taskExecutor().corePoolSize(4).maxPoolSize(10);
//	}
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry configurer) {
//		configurer.enableSimpleBroker("/queue/", "/topic/");
//		configurer.setUserDestinationPrefix("/app");
//	}
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/enme-ws").withSockJS();
//	}

}
