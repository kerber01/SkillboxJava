import static java.time.temporal.ChronoUnit.HOURS;

import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Flight.Type;
import com.skillbox.airport.Terminal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

public class Loader {

  //private static final long TWO_HOUR_TIME_GAP = 7200000;

  public static void main(String[] args) {

/* Старая версия без map() и до того, как я узнал о flatMap(Collection::stream) для избавления от вложенности

   Airport.getInstance().getTerminals()
        .forEach(t -> t.getFlights().stream().sorted(Comparator.comparing(Flight::getDate))
            .filter(f -> f.getType().equals(Type.DEPARTURE)
                && f.getDate().getTime() - new Date().getTime() <= TWO_HOUR_TIME_GAP
                && f.getDate().compareTo(new Date()) > 0)
            .forEach(f -> System.out
                .println(new SimpleDateFormat().format(f.getDate()) + " " + f.getAircraft()
                    .getModel())));
*/

    Airport.getInstance().getTerminals().stream()
        .map(Terminal::getFlights)
        .flatMap(Collection::stream).sorted(Comparator.comparing(Flight::getDate))
        .filter(f -> f.getType().equals(Type.DEPARTURE)
            && f.getDate().getTime() <= Instant.now().plus(2, HOURS).toEpochMilli()
            && f.getDate().compareTo(new Date()) > 0)
        .forEach(f -> System.out.println(new SimpleDateFormat()
            .format(f.getDate()) + " " + f.getAircraft()
            .getModel()));
  }
}
