import core.Line;
import core.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;


public class RouteCalculatorTest extends TestCase {

  List<Station> redStations;
  List<Station> blueStations;
  List<Station> whiteStations;
  RouteCalculator routeCalculator;
  ArrayList<Station> expectedOnTheLineRoute;
  ArrayList<Station> expectedOneConnectionRoute;
  ArrayList<Station> expectedTwoConnectionsRoute;

  @Before
  @Override
  protected void setUp() throws Exception {

    Line redLine = new Line(1, "red");

    redLine.addStation(new Station("Уручье", redLine));
    redLine.addStation(new Station("Борисовский тракт", redLine));
    redLine.addStation(new Station("Восток", redLine));
    redLine.addStation(
        new Station("Октябрьская", redLine)); //connects to blue line's "Купаловская" station

    redStations = redLine.getStations();

    //-----------------------------------------

    Line blueLine = new Line(2, "blue");

    blueLine.addStation(new Station("Автозаводская", blueLine));
    blueLine.addStation(
        new Station("Купаловская", blueLine)); //connects to red line's "Октябрьская" station
    blueLine.addStation(new Station("Тракторный завод", blueLine));
    blueLine.addStation(
        new Station("Могилевская", blueLine)); //connects to white line's "Космодесантская" station

    blueStations = blueLine.getStations();

    //-----------------------------------------

    Line whiteLine = new Line(3, "white");

    whiteLine.addStation(
        new Station("Космодесантская", whiteLine)); //connects to blue line's "Могилевская" station
    whiteLine.addStation(new Station("Навохо-доносорово", whiteLine));
    whiteLine.addStation(new Station("Площадь Ильича", whiteLine));
    whiteLine.addStation(new Station("Белорусский вокзал", whiteLine));

    whiteStations = whiteLine.getStations();

    //-----------------------------------------

    StationIndex stationIndex = new StationIndex();
    stationIndex.addLine(redLine);
    stationIndex.addLine(blueLine);
    stationIndex.addLine(whiteLine);
    stationIndex.stations.addAll(redLine.getStations());
    stationIndex.stations.addAll(blueLine.getStations());
    stationIndex.stations.addAll(whiteLine.getStations());

    TreeSet<Station> redBlueConnection = new TreeSet<>();
    redBlueConnection.add(blueStations.get(1));
    TreeSet<Station> blueRedConnection = new TreeSet<>();
    blueRedConnection.add(redStations.get(3));
    TreeSet<Station> whiteBlueConnection = new TreeSet<>();
    whiteBlueConnection.add(blueStations.get(3));
    TreeSet<Station> blueWhiteConnection = new TreeSet<>();
    blueWhiteConnection.add(whiteStations.get(0));

    stationIndex.connections.put(redStations.get(3), redBlueConnection);
    stationIndex.connections.put(blueStations.get(1), blueRedConnection);
    stationIndex.connections.put(whiteStations.get(0), whiteBlueConnection);
    stationIndex.connections.put(blueStations.get(3), blueWhiteConnection);

    routeCalculator = new RouteCalculator(stationIndex);

    expectedOnTheLineRoute = new ArrayList<>();
    expectedOnTheLineRoute.add(blueStations.get(3));
    expectedOnTheLineRoute.add(blueStations.get(2));
    expectedOnTheLineRoute.add(blueStations.get(1));
    expectedOnTheLineRoute.add(blueStations.get(0));

    expectedOneConnectionRoute = new ArrayList<>();
    expectedOneConnectionRoute.add(redStations.get(2));
    expectedOneConnectionRoute.add(redStations.get(3));
    expectedOneConnectionRoute.add(blueStations.get(1));
    expectedOneConnectionRoute.add(blueStations.get(0));

    expectedTwoConnectionsRoute = new ArrayList<>();
    expectedTwoConnectionsRoute.add(whiteStations.get(1));
    expectedTwoConnectionsRoute.add(whiteStations.get(0));
    expectedTwoConnectionsRoute.add(blueStations.get(3));
    expectedTwoConnectionsRoute.add(blueStations.get(2));
    expectedTwoConnectionsRoute.add(blueStations.get(1));
    expectedTwoConnectionsRoute.add(redStations.get(3));
    expectedTwoConnectionsRoute.add(redStations.get(2));
    expectedTwoConnectionsRoute.add(redStations.get(1));

  }

  @After
  @Override
  protected void tearDown() throws Exception {
    redStations = null;
    blueStations = null;
    whiteStations = null;
    routeCalculator = null;
    expectedOnTheLineRoute = null;
    expectedOneConnectionRoute = null;
    expectedTwoConnectionsRoute = null;
  }

  public void test_calculate_duration_on_the_line() {
    assertEquals(7.5, RouteCalculator.calculateDuration(expectedOnTheLineRoute));
  }

  public void test_calculate_duration_one_connection() {
    assertEquals(8.5, RouteCalculator.calculateDuration(expectedOneConnectionRoute));
  }

  public void test_calculate_duration_two_connections() {
    assertEquals(19.5, RouteCalculator.calculateDuration(expectedTwoConnectionsRoute));
  }


  public void test_get_shortest_route_on_the_line() {
    assertEquals(expectedOnTheLineRoute,
        routeCalculator.getShortestRoute(blueStations.get(3), blueStations.get(0)));
  }

  public void test_get_shortest_route_with_one_connection() {
    assertEquals(expectedOneConnectionRoute,
        routeCalculator.getShortestRoute(redStations.get(2), blueStations.get(0)));
  }

  public void test_get_shortest_route_with_two_connections() {
    assertEquals(expectedTwoConnectionsRoute,
        routeCalculator.getShortestRoute(whiteStations.get(1), redStations.get(1)));
  }


}

