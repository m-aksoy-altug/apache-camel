package com.camel.pojo;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "shiporder")
@XmlAccessorType(XmlAccessType.FIELD)
public class PojoDummy {
	
	 	@XmlAttribute(name = "pojoid")
	    private String pojoId;

	    @XmlElement(name = "discription")
	    private String discription; 
	    
	    public PojoDummy(){  }

		public String getPojoId() {
			return pojoId;
		}

		public void setPojoId(String pojoId) {
			this.pojoId = pojoId;
		}

		public String getDiscription() {
			return discription;
		}

		public void setDiscription(String discription) {
			this.discription = discription;
		}
	    
	    
}
