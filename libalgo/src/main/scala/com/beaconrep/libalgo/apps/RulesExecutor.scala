package com.beaconrep.libalgo.apps

class RulesExecutor (xlsFileName : String)  extends Serializable {
 
  //evaluate all the rules and send the result back to 
  def evalRules (incomingEvents : Iterator[Asset]) : Iterator[Asset] ={
        val ksession = KieSessionFactory.getKieSession(xlsFileName)
        val assets = incomingEvents.map(asset => {
          ksession.execute(asset)
          asset
        })
        assets
  }
}