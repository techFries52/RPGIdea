package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Coin extends SuperObject{
    public OBJ_Coin() {
        objectName = "Coin";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/coin_bronze.png"));
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
