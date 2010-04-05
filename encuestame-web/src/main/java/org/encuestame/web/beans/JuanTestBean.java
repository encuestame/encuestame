package org.encuestame.web.beans;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JuanTestBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6858794663328935054L;
    private String proOne = "Juan2";
    private String proTwo = "Parte 2";

    protected Log log = LogFactory.getLog(this.getClass());

    public String getProOne() {
        log.debug("getProOne");
        return proOne;
    }

    public void setProOne(String proOne) {
        log.debug("setProOne");
        this.proOne = proOne;
    }

    public String getProTwo() {
        log.debug("getProTwo");
        return proTwo;
    }

    public void setProTwo(String proTwo) {
        log.debug("setProTwo");
        this.proTwo = proTwo;
    }

    public void copyProp(){
        log.debug("COPY");
        System.out.println("JUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANNNNNNNNNNNNNNNNNNNNN");
        setProTwo("Prop1");
    }


    public void copyProp2(){
        log.debug("COPY");
        System.out.println("JUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANNNNNNNNNNNNNNNNNNNNN");
        setProOne("PropOne");
    }

}
