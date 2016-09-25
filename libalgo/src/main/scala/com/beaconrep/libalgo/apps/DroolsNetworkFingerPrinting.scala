package com.beaconrep.libalgo.apps

object DroolsNetworkFingerPrinting extends App {
  
     //setup rules executor

    val rulesExecutor = new RulesExecutor("resources/Asset.xls")

     //Protocol Object(source port, destination port, protocol id)
   val it = Iterator(new Asset(50400, 504, 1),
                     new Asset(504, 50400, 1),
                     new Asset(5666, 66666, 1))
   val protocol_return = rulesExecutor.evalRules(it) 
   protocol_return.foreach { x => println(x.getAssetName) }
}