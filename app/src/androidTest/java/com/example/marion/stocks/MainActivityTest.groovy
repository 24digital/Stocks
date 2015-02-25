package com.example.marion.stocks

/**
 * Created by Marion on 2/25/2015.
 */
class MainActivityTest extends spock.lang.Specification {
    def "OnCreate"() {
  MainActivity activity = new MainActivity();
    expect:
   activity != null
    }

    def "OnCreateOptionsMenu"() {

    }

    def "OnOptionsItemSelected"() {

    }
}
