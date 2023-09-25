package sample.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {


        public void shaker(Node node){
            TranslateTransition TT = new TranslateTransition(Duration.millis(50),node);
            TT.setFromX(0f);
            TT.setByX(20f);
            TT.setCycleCount(2);
            TT.setAutoReverse(true);
            TT.playFromStart();

        }


}
