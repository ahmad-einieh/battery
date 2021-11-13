import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {
    public Kernel32 INSTANCE = Native.load("Kernel32", Kernel32.class);

    public class SYSTEM_POWER_STATUS extends Structure {
        public byte ACLineStatus;
        public byte BatteryFlag;
        public byte BatteryLifePercent;
        public int BatteryLifeTime;
        public int BatteryFullLifeTime;

        @Override
        protected List<String> getFieldOrder() {
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("ACLineStatus");
            fields.add("BatteryFlag");
            fields.add("BatteryLifePercent");
            fields.add("BatteryLifeTime");
            fields.add("BatteryFullLifeTime");
            return fields;
        }


        public String getACLineStatusString() {
            switch (ACLineStatus) {
                case (0):
                    return "Offline";
                case (1):
                    return "Online";
                default:
                    return "Unknown";
            }
        }


        public String getBatteryFlagString() {
            switch (BatteryFlag) {
                case (1):
                    return "High, more than 66 percent";
                case (2):
                    return "Low, less than 33 percent";
                case (4):
                    return "Critical, less than five percent";
                case (8):
                    return "Charging";
                case ((byte) 128):
                    return "No system battery";
                default:
                    return "Unknown";
            }
        }


        public String getBatteryLifePercent() {
            return (BatteryLifePercent == (byte) 255) ? "Unknown" : BatteryLifePercent + "%";
        }

        public String getIsFull() {
            if(getBatteryLifePercent().equalsIgnoreCase("Unknown")) return "Unknown";
            return BatteryLifePercent == 100 ? "full" : "not full";
        }


        public String getBatteryLifeTime() {
            return (BatteryLifeTime == -1) ? "Unknown" : BatteryLifeTime + " seconds";
        }


        public String getBatteryFullLifeTime() {
            return (BatteryFullLifeTime == -1) ? "Unknown" : BatteryFullLifeTime + " seconds";
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ACLineStatus: " + getACLineStatusString() + "\n");
            sb.append("Battery Flag: " + getBatteryFlagString() + "\n");
            sb.append("Battery Life: " + getBatteryLifePercent() + "\n");
            sb.append("Battery Left: " + getBatteryLifeTime() + "\n");
            sb.append("Battery Full: " + getBatteryFullLifeTime() + "\n");
            sb.append("full? "+ getIsFull()+"\n");
            return sb.toString();
        }
    }


    public int GetSystemPowerStatus(SYSTEM_POWER_STATUS result);

}
