import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Flight.Type;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Loader {

  private static final long TWO_HOUR_TIME_GAP = 7200000;

  public static void main(String[] args) {

    Airport.getInstance().getTerminals().stream()
        .forEach(t -> t.getFlights().stream().sorted(Comparator.comparing(Flight::getDate))
            .filter(f -> f.getType().equals(Type.DEPARTURE)
                && f.getDate().getTime() - new Date().getTime() <= TWO_HOUR_TIME_GAP && f.getDate().compareTo(new Date()) > 0)
            .forEach(f -> System.out.println(new SimpleDateFormat().format(f.getDate()) + " " + f.getAircraft()
            .getModel())));


  }
}
