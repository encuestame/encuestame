package org.encuestame.mvc.test.comet;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.cometd.bayeux.server.ServerChannel;
import org.cometd.java.annotation.AnnotationCometdServlet;
import org.cometd.java.annotation.ServerAnnotationProcessor;
import org.cometd.server.BayeuxServerImpl;
import org.cometd.server.ServerChannelImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.encuestame.comet.server.CometBayeuxInitializer;
import org.encuestame.comet.services.TweetPollCometService;
import org.encuestame.mvc.test.config.AbstractCometBeans;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TweetPollCometServiceTestCase extends AbstractCometBeans{

    @Resource(name = "tweetPollCometService")
    public TweetPollCometService tweetPollCometService;
    @Autowired
    private CometBayeuxInitializer cometBayeuxInitializer;
    //private ServerAnnotationProcessor processor;



    @Before
    public void init() throws Exception {
        //bayeuxServer = new BayeuxServerImpl();
        //if (Boolean.getBoolean("debugTests"))
         //   bayeuxServer.setOption(BayeuxServerImpl.LOG_LEVEL, "3");
        //bayeuxServer.start();
        //processor = new ServerAnnotationProcessor(bayeuxServer);
    }

    public void setCometBayeuxInitializer(
            CometBayeuxInitializer cometBayeuxInitializer) {
        this.cometBayeuxInitializer = cometBayeuxInitializer;
    }

    @Test
    public void testInjectServerSessionOnMethod() throws Exception {
        System.out.println("--------- "+this.tweetPollCometService);
        System.out.println("--------- testInjectServerSessionOnMethod ----------");
        TweetPollCometService d = new TweetPollCometService();
        boolean processed = processor.process(d);
        Assert.assertTrue(processed);
        ServerChannel channel = bayeuxServer.getChannel("/service/tweetpoll/autosave");
        Assert.assertNotNull(channel);
        Assert.assertEquals(1, ((ServerChannelImpl)channel).getListeners().size());
        System.out.println("--------- testInjectServerSessionOnMethod ----------");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testLifecycle() throws Exception {
        Server server = new Server();
        HandlerCollection handlers = new HandlerCollection();
        server.setHandler(handlers);
        String contextPath = "/cometd";
        ServletContextHandler context = new ServletContextHandler(handlers,
                contextPath, ServletContextHandler.SESSIONS);
        AnnotationCometdServlet cometdServlet = new AnnotationCometdServlet();
        ServletHolder cometdServletHolder = new ServletHolder(cometdServlet);
        cometdServletHolder.setInitParameter("services",
                TweetPollCometService.class.getName());
        String cometdServletPath = "/cometd";
        context.addServlet(cometdServletHolder, cometdServletPath + "/*");
        server.start();
        //List<Object> services = cometdServlet.getS;
        // Assert.assertNotNull(services);
        // Assert.assertEquals(1, services.size());
        // TweetPollCometService service =
        // (TweetPollCometService)services.get(0);
        // Assert.assertTrue(service.init);
        server.stop();
        server.join();
        // Assert.assertTrue(service.destroy);
    }
}
