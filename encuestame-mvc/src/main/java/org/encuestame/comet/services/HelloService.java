package org.encuestame.comet.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;

@Named
@Singleton
@Service("helloService")
public class HelloService extends AbstractCometService {

    @PostConstruct
    public void init() {
    }

    @Listener("/service/hello")
    public void processHello(ServerSession remote, ServerMessage.Mutable message) {
        Map<String, Object> input = message.getDataAsMap();
        String name = (String) input.get("name");

        Map<String, Object> output = new HashMap<String, Object>();
        output.put("greeting", "Hello, " + name);
        remote.deliver(getServerSession(), "/hello", output, null);
    }
}
