package dev.vesper.AIUTD;

//? fabric {
/*import dev.vesper.AIUTD.fabric.FabricPlatformImpl;
*///?}
//? neoforge {
//?}

public interface Platform {

    //? fabric {
    /*Platform INSTANCE = new FabricPlatformImpl();
    *///?}
    //? neoforge {
    Platform INSTANCE = new dev.vesper.AIUTD.neoforge.NeoforgePlatformImpl();
    //?}


    boolean isModLoaded(String modid);
    String loader();

}
