package com.beaconrep.libalgo.apps;

import java.io.IOException;



public class Asset implements java.io.Serializable  {

	/**
	 * 
	 */
	
	public  enum ASSET_TYPE {
			MODBUS_SERVER, MODBUS_CLIENT, TELNET, SNCP 
		};

	
	public ASSET_TYPE assetType;
	private long destinationPort;
	
	private long sourcePort;
	
	private long protocolID;
	
	private String assetName = "Don't Know";
	
	private static final long serialVersionUID = -7296169711991432833L;

	public Asset(long sourcePort, long destinationPort, long protocolID) {
		this.destinationPort = destinationPort;
		this.sourcePort = sourcePort;
		this.protocolID = protocolID;
	}
	
	
	public Asset() { 
	
	}
	
	public long getdestinationPort() {
		return this.destinationPort;
	}
	
	public void setdestinationPort(long destinationPort) {
		this.destinationPort = destinationPort;
	}
	
    public long  getsourcePort() {
    	return this.sourcePort;
    }
    
    public void setsourcePort(long  sourcePort) {
    	this.sourcePort = sourcePort;
    }
    
    public void setProtocolID(long protocolID) {
    	this.protocolID = protocolID;
    }
    
    public long getProtocolID() {
    	return this.protocolID;
    }
    
    public String getAssetName() {
		return assetName;
    	
    }
    
    public void result(ASSET_TYPE name) {	
    	switch(name) {
    	case MODBUS_SERVER: 
    		assetName = "MODBUS_SERVER";
    		break;
    	case MODBUS_CLIENT:
            assetName = "MODBUS_CLIENT";
            break;
    	case TELNET:
    		assetName = "TELNET";
    		break;
    	case SNCP:
    		assetName = "SNCP";
    		break;
      }
    }
}
