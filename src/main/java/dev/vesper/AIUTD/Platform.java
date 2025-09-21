package dev.vesper.AIUTD;

//? fabric {
import dev.vesper.AIUTD.fabric.FabricPlatformImpl;
//?}
//? neoforge {
<<<<<<< Updated upstream
/*import dev.spagurder.modtemplate.neoforge.NeoforgePlatformImpl;
*///?}

=======
/*import dev.vesper.AIUTD.neoforge.NeoforgePlatformImpl;
*///?}



>>>>>>> Stashed changes
public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}


    boolean isModLoaded(String modid);
    String loader();

}
