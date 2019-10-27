package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import java.util.Scanner;
/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

    public static final void main(String[] args) {
        try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
        	Scanner in = new Scanner(System.in);
            System.out.println("请输入需要评级的分数：（输入-1退出循环）");
            int data = in.nextInt();
            while(data!=-1) {
                Score s = new Score(data);
                kSession.insert(s);
                kSession.fireAllRules();
                System.out.println("请输入需要评级的分数：（输入-1退出循环）");
                data = in.nextInt();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static class Score {
    	public int score;
        Score(int Score){
            score = Score;
        }
        public int getScore() {
            return score;
        }

    }

}
